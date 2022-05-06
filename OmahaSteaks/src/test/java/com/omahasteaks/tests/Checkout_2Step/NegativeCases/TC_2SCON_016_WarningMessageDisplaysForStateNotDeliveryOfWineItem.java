package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_016_WarningMessageDisplaysForStateNotDeliveryOfWineItem extends TestBase_2SC {
	CustomerAddress shippingAddress;

	String[] stateCodeList;

	@Inject
	Item myItem;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Test
	public void TC_2SCON_016_Warning_Message_Appears_For_State_Not_Delivery_Of_Wine_Item() {
		initTestCaseData();

		searchAndAddItem(myItem);

		checkOutFromAddedToCart();

		checkOutFromShoppingCart();

		fillShippingAddressAndVerifyErrorMessageDisplaysCorrectly();

//		clickRemoveItemsLinkAndClickCancel();

//		verifyUserIsStayedAtShippingAddressPage();

		clickRemoveItemLinkAndClickConfirmUpdate();

		verifyYourCartIsCurrentlyEmptyIsDisplayed();
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	private void fillShippingAddressAndVerifyErrorMessageDisplaysCorrectly() {
		for (int i = 0; i < stateCodeList.length; i++) {
			Logger.info("Filling Shipping Address with state code:" + stateCodeList[i].trim());
			fillShippingAddressAndClickContinue(stateCodeList[i].trim());
			verifyErrorMessageIsDisplayed(shippingAddress.state);
			verifyShippingErrorSectionDisplaysWine(shippingAddress.state);
		}
	}

	private void verifyYourCartIsCurrentlyEmptyIsDisplayed() {
		Logger.verify("In Shopping Cart page:\r\n" + "Verify that 'Your Cart Is Currently Empty' appears");
		assertEquals(shoppingCartPage.getEmptyMessageText().trim(), Constants.EMPTY_MESSAGE);
	}

	private void clickRemoveItemLinkAndClickConfirmUpdate() {
		clickRemoveItemLink();
		Logger.info("In 'Remove This Cart?' modal popup:\r\n" + "- Click \"CONFIRM UPDATE & CONTINUE\"");
		shippingAddressPage2SC.clickConfirmUpdateButtonInRemoveThisCartModal();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_016 - Warning Message Appears For State Not Delivery Of Wine Item");
		Logger.to("TO_2SCON_45 - USA Address - A Warning message appears for invalid State for delivery of Wine items");
		stateCodeList = Messages.WINE_RESTRICTION_MESSAGE.split(": ", 2)[1].split(",");
		myItem.initRandom(Recipient.MYSELF, true);
	}

	private void verifyUserIsStayedAtShippingAddressPage() {
		Logger.verify("Verify user is stayed at Shipping Address Page");
		assertTrue(Common.getCurrentUrl().contains(Constants.URL_CHECKOUT_DOMAIN) && Common.getTitlePage().trim().equals(Constants.CHECKOUT_SHIPPING_PAGE_TITLE));
	}

	private void clickRemoveItemsLinkAndClickCancel() {
		clickRemoveItemLink();
		Logger.info("In 'Remove This Cart?' modal popup:\r\n" + "- Click \"CANCEL\"");
		shippingAddressPage2SC.clickCancelButtonInRemoveThisCartModal();
	}

	private void clickRemoveItemLink() {
		Logger.info("In Shipping Address Page:\r\n" + "- Click \"Remove Items\" link\r\n");
		shippingAddressPage2SC.clickRemoveItemsLink();
	}

	private void verifyShippingErrorSectionDisplaysWine(String state) {
		Logger.verify("In Shipping Address Page:\r\n" + "Verify the [" + myItem.getName() + "] which cannot be shipped to [" + state + "] is displayed");
		assertTrue(shippingAddressPage2SC.isSKUExistedInShippingError(myItem));
	}

	private void verifyErrorMessageIsDisplayed(String state) {
		Common.waitForDOMChange();
		Logger.verify("In Shipping Address Page:\r\n" + "- The Warning message [" + String.format(Messages.SHIPPING_WINE_RESTRICTION_MESSAGE, state) + "] is displayed");
		assertEquals(shippingAddressPage2SC.getShippingErrorMessage(), String.format(Messages.SHIPPING_WINE_RESTRICTION_MESSAGE, state));
	}

	private void fillShippingAddressAndClickContinue(String stateCode) {
		Logger.info("In Shipping Address Page:\r\n" + "- Fill valid US Address into Shipping Address section\r\n");
		shippingAddress = lstAddresses.getShippingAddressByState(Common.convertStateCodeToFullStateName(stateCode));
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		Logger.info("Click \"Continue\"");
		shippingAddressPage2SC.clickContinueButton();
		DriverUtils.delay(Constants.SLEEP_TIME);
	}

	private void checkOutFromShoppingCart() {
		Logger.info("In Shopping Cart page:\r\n" + "- Click \"CHECKOUT\"");
		shoppingCartPage.checkOut();
	}

	private void checkOutFromAddedToCart() {
		Logger.info("In \"Added To Cart\" popup: \r\n" + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void searchAndAddItem(Item item) {
		Logger.info("From Homepage, Search a " + item.getName() + " with ID: " + item.getId());
		generalPage.search(Common.
				getNumberFromText(item.getId()));

		Logger.info("In Search page\n" + "- Select the first Item and add it to Cart");
		generalPage.addFirstSKUToCart(item);
	}
}
