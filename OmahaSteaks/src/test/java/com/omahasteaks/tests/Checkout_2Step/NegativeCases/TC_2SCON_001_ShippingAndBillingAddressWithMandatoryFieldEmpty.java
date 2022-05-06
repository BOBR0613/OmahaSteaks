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
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_001_ShippingAndBillingAddressWithMandatoryFieldEmpty extends TestBase_2SC {
	CustomerAddress customerAddress, invalidAddress;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	Item item;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Test
	public void TC_2SCON_001_Shipping_And_Billing_Address_With_Mandatory_Field_Empty() {
		initTestCaseData();

		searchAndAddItem(item);

		checkOutFromShoppingCartPage();

		leaveShippingAddressEmptyAndContinueCheckout();

		verifyWarningMessageDisplaysCorrectlyInShippingAddress();

		fillInformationIntoShippingAddress();

		leaveBillingAddressEmptyAndContinueCheckout();

		verifyWarningMessageDisplaysCorrectlyInBillingAddress();
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	private void checkOutFromShoppingCartPage() {
		Logger.info("In My Cart, click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void fillInformationIntoShippingAddress() {
		shippingAddressPage.fillShippingAddress(customerAddress);
		Common.waitForPageLoad();
		shippingAddressPage.clickContinueButton();
		Common.waitForPageLoad();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_001 - Shipping And Billing Address With Mandatory Field Empty");
		Logger.to("TO_2SCON_5 - Warning message when leaving all mandatory fields in Billing address empty");
		Logger.to("TO_2SCON_11 - Warning message when leaving all mandatory fields empty in Shipping Address");
		item.initRandom(Recipient.MYSELF);
		customerAddress = lstAddresses.getDefaultShippingAddress();
	}

	private void leaveBillingAddressEmptyAndContinueCheckout() {
		Logger.info("In Payment & Review page" + " - Fill \" 4111111111111111\" number at Credit / Debit section" + " - Card Expiration: we will generate randomly a date in future (MM/YY)" + " - Leave every fields  in Billing Address empty (if there are any fields having default value, then delete them excluding Country field) " + " - Click \"Place Order\"");
		paymentAndReviewPage.fillCreditCardNumber();
		paymentAndReviewPage.placeOrder();
	}

	private void leaveShippingAddressEmptyAndContinueCheckout() {
		Logger.info("In Shipping Address form: \n" + "-  Leave every fields empty (if there are any fields having default value, then delete them excluding Country field)n" + "- Click \"CONTINUE CHECKOUT\"");
		shippingAddressPage.clickContinueButton();
		Common.waitForPageLoad();
	}

	private void searchAndAddItem(Item item) {
		String keyword = Common.getNumberFromText(item.getId());
		Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
		generalPage.search(keyword);
		Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		productPage.addSKUToCart(item, false);
		productPage.checkOut();
	}

	private void verifyWarningMessageDisplaysCorrectlyInBillingAddress() {
		Logger.verify("\"Verify a warning message with below information:\n" + "Please Enter Your First Name\n" + "Please Enter Your Last Name\n" + "Please Enter Your Street Address\n" + "Please Enter Your Zip\n" + "Please Select Your State\n" + "Please Enter Your City\n");
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.FIRST_NAME));
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.LAST_NAME));
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.STREET_ADDRESS), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.STREET_ADDRESS));
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.STATE), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.STATE));
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.CITY), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.CITY));
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.PHONE), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.PHONE));
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.ZIP_CODE), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.ZIP_CODE));
	}

	private void verifyWarningMessageDisplaysCorrectlyInShippingAddress() {
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE)) {
			Common.modalDialog.closeModalDialog();
		}
		Logger.verify("\"Verify a warning message with below information:\n" + "Please Enter A First Name\n" + "Please Enter A Last Name\n" + "Please Enter A Street Address\n" + "Please Enter A Zip Code\n" + "Please Select A State\n" + "Please Enter A City\n");
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getEmptyErrorMessageByShippingField(AddressFields.FIRST_NAME));
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getEmptyErrorMessageByShippingField(AddressFields.LAST_NAME));
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.STREET_ADDRESS), Messages.getEmptyErrorMessageByShippingField(AddressFields.STREET_ADDRESS));
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.ZIP_CODE), Messages.getEmptyErrorMessageByShippingField(AddressFields.ZIP_CODE));
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.STATE), Messages.getEmptyErrorMessageByShippingField(AddressFields.STATE));
		assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.CITY), Messages.getEmptyErrorMessageByShippingField(AddressFields.CITY));
	}
}
