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
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_012_FoodItemsCannotBeDeliveredToAddressPOBox extends TestBase_2SC {
	CustomerAddress recipientShippingAddressCA, recipientShippingAddressUS;

	@Inject
	Item recipientItem;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	SearchResultPage searchResultPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Test
	public void TC_2SCON_012_Food_Items_Cannot_Be_Delivered_To_Address_PO_Box() {
		initTestCaseData();

		searchAndAddItem(recipientItem);

		checkoutFromAddedToCart();

		checkoutFromShoppingCart();

		fillShippingAddressAndClickContinue(recipientShippingAddressCA);

		verifyWarningMessageDisplaysCorrectlyInShippingAddress();

		fillShippingAddressAndClickContinue(recipientShippingAddressUS);

		verifyWarningMessageDisplaysCorrectlyInShippingAddress();
		
		fillShippingAddressAndClickContinue();
		
		goToMyCartFromPaymentAndReviewPage();
		
		goToSignInPageFromShoppingCartPage();

		signIn();

		goToMyCartFromMyAccountPage();

		checkoutFromShoppingCart();

		clickEditThisAddressLinkFromShippingAddressPage();

		fillShippingAddressInModalAndClickContinue(recipientShippingAddressUS);

		verifyErrorMessageDisplaysCorrectlyInShippingAddress();

		clickEditThisAddressLinkFromShippingAddressPage();

		fillShippingAddressInModalAndClickContinue(recipientShippingAddressCA);

		verifyErrorMessageDisplaysCorrectlyInShippingAddress();

	}

	// ============================================================================
	// Test Methods
	// ============================================================================

	private void fillShippingAddressInModalAndClickContinue(CustomerAddress customerAddress) {
		fillShippingAddressInModalAndClickUpdate(customerAddress);
		Common.waitForDOMChange();
		clickContinuebutton(); 
	}


	private void fillShippingAddressInModalAndClickUpdate(CustomerAddress custormerAddress) {
		Logger.info("In \"Edit Contact\" modal\r\n" + "- Select Canada on Country ComboBox\r\n" + "- Select Province ComboBox\r\n" + "- Fill valid information into Postal Code textbox\r\n" + "- Click the \"UPDATE CONTACT\"");
		custormerAddress.setStreetAdress1(Constants.STREETADDRESS_PO_BOX);
		shippingAddressPage.fillShippingAddressInModal(custormerAddress);
		shippingAddressPage.updateContactInModal();
	}

	private void signIn() {
		Logger.info("In SignIn Page:\r\n" + "- Login with valid account");
		signInPage.signIn(Constants.OMAHA_EMAIL, Constants.OMAHA_PASSWORD);
	}

	private void fillShippingAddressAndClickContinue() {
		Logger.info("In Shipping Address Page:\r\n" + "- Fill valid street address into Stress Address textbox\r\n");
		recipientShippingAddressUS.setStreetAdress1(Constants.STREETADDRESS_US_VALID);
		shippingAddressPage.fillShippingAddress(recipientShippingAddressUS);
		clickContinuebutton();
	}

	private void fillShippingAddressAndClickContinue(CustomerAddress custormerAddress) {
		Logger.info("In Shipping Address Page:\r\n" + "- In Shipping Address section, fill valid Canada Address which has \"P.O. Box 757\" in \"Street Address\" \r\n");
		custormerAddress.setStreetAdress1(Constants.STREETADDRESS_PO_BOX);
		shippingAddressPage.fillShippingAddress(custormerAddress);
		clickContinuebutton();
	}

	private void clickContinuebutton() {
		Logger.info("Click  \"Continue \"");
		shippingAddressPage.clickContinueButton();
	}

	private void verifyErrorMessageDisplaysCorrectlyInShippingAddress() {
		Common.waitForDOMChange();
		Logger.verify("Verify Error message \"Address - Delivery to PO Boxes is not supported.\" is appeared");
		assertEquals(shippingAddressPage.getErrorMessage(), Messages.SADDRESS_PO_BOX_DELIVERY_ERROR_MESSAGE);
	}

	private void clickEditThisAddressLinkFromShippingAddressPage() {
		Logger.info("In Shipping Address page\r\n" + "- Click the \"Edit this address\" link.");
		shippingAddressPage.clickEditThisAddressLink();
	}

	private void goToMyCartFromMyAccountPage() {
		Logger.info("In My Account Page:\r\n" + "- Click \"My Cart\"");
		generalPage.goToMyCart();
	}

	private void goToSignInPageFromShoppingCartPage() {
		Logger.info("In ShoppingCart page:\r\n" + "- Click Sign in");
//		shoppingCartPage.goToSignInPage();
		generalPage.goToSignInPage();
	}

	private void goToMyCartFromPaymentAndReviewPage() {
		Logger.info("\"In Payment & Review page:\r\n" + "- Click My Cart Icon\"\r\n");
		generalPage.goToMyCart();
	}

	private void verifyWarningMessageDisplaysCorrectlyInShippingAddress() {
		Common.waitForDOMChange();
		Logger.verify("Verify Error message \"We Cannot Ship Food To P.O. Boxes\" is appeared");
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.STREET_ADDRESS), Messages.getInvalidMessageByField(AddressFields.ADDRESS1));
	}

	private void checkoutFromShoppingCart() {
		Logger.info("In Shopping Cart page: \r\n" + "- Click \"CHECKOUT\"");
		Common.delay(5);
		shoppingCartPage.checkOut();
	}

	private void checkoutFromAddedToCart() {
		Logger.info("In \"Added To Cart\" popup: \r\n" + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void searchAndAddItem(Item item) {
		Logger.info("From Homepage, Search a SKU " + Common.getNumberFromText(item.getId()) + "- Select this SKU\r\n" + "- Select \"Ship To Someone Else\"\r\n" + "- Fill to the \"Recipient's Name\" textbox\r\n" + "- Click \"ADD TO CART\" button");
		generalPage.search(Common.getNumberFromText(item.getId()));
		generalPage.addFirstSKUToCart(item);

		Logger.info("In Exclusive Offer (Upsell offer) modal:\r\n" + "- Click \"No Thanks\"");
		generalPage.selectExclusiveOffer(false);
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_012 - Food Items cannot be delivered to address P.O.Box");
		Logger.to("TO_2SCON_37 - Canada Address - Food items cannot be delivered to P.O.Box  (Sign In and without Sign In)");
		Logger.to("TO_2SCON_38 - USA Address - Food items cannot be delivered to P.O.Box  (Sign In and without Sign In)");
		recipientShippingAddressCA = lstAddresses.getRandomShippingAddressCanada();
		recipientItem.initRandom(Recipient.NEW_RECIPIENT, false);
		recipientShippingAddressUS = lstAddresses.getDefaultShippingAddress();
	}

}
