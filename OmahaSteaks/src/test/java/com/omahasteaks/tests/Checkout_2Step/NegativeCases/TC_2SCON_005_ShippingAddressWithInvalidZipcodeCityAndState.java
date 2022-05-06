package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_005_ShippingAddressWithInvalidZipcodeCityAndState extends TestBase_2SC {
	CustomerAddress invalidAddress;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	Item item;
	@Inject
	GeneralPage generalPage;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Test
	public void TC_2SCON_005_Shipping_Address_With_Invalid_ZipCode_City_And_State() {
		initTestCaseData();

		searchAndAddItem(item);
		
		checkOutFromShoppingCartPage(); 
		
		fillNotMatchZipCode();
		verifyWarningMessageIsDisplayed(Messages.BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_1); 

		fillInvalidZipCode();
		verifyWarningMessageIsDisplayed(Messages.BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_2);
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In My Cart, click CHECKOUT button");
		Common.delay(5);
		shoppingCartPage.checkOut();
	}

	private void fillInvalidZipCode() {
		Logger.info("In Shipping page: \n" + "- Fill valid all information except Zip Code fields in Shipping Address");
		invalidAddress.updateFieldValueBy(AddressFields.STATE, "California");
		invalidAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "00000");
		shippingAddressPage.fillShippingAddress(invalidAddress);
		Common.waitForDOMChange();
	}

	private void fillNotMatchZipCode() {
		Logger.info("In Shipping page: \n" + "- Fill valid all information but Zip Code City, State and Country code does not match together in Billing address");
		invalidAddress.updateFieldValueBy(AddressFields.STATE, "New York");
		invalidAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "95129");
		shippingAddressPage.fillShippingAddress(invalidAddress);
		Common.waitForDOMChange();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_005 Shipping Address With Invalid Zipcode City And State");
		Logger.to("TO_2SCON_14 Warning message when filling invalid zip code in Shipping address");
		Logger.to("TO_2SCON_15 Warning message when filling Zip Code, City, State and Country code does not match together in Shipping address");
		item.initRandom(Recipient.MYSELF);
		invalidAddress = lstAddresses.getDefaultShippingAddress();
	}

	private void searchAndAddItem(Item item) {
		String keyword = Common.getNumberFromText(item.getId());
		Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
		generalPage.search(keyword);
		Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		productPage.addSKUToCart(item, false);
		productPage.checkOut();
	}

	private void verifyWarningMessageIsDisplayed(String message) {
		Logger.verify("The warning messages are displayed:\n" + "- " + message);
		assertEquals(shippingAddressPage.getErrorMessageAtBottom(), message);
	}
}
