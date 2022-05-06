package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
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
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_002_ShippingAndBillingAddressWithEachMandatoryFieldEmpty extends TestBase_2SC {
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
	public void TC_2SCON_002_Shipping_And_Billing_Address_With_Each_Mandatory_Field_Empty() {
		initTestCaseData();

		searchAndAddItem(item);

		checkOutFromShoppingCartPage();

		fillShippingAddressExceptField(AddressFields.FIRST_NAME);

		verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields.FIRST_NAME);

		fillShippingAddressExceptField(AddressFields.LAST_NAME);

		verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields.LAST_NAME);

		fillShippingAddressExceptField(AddressFields.STREET_ADDRESS);

		verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields.STREET_ADDRESS);
		fillShippingAddressExceptField(AddressFields.ZIP_CODE);
		verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields.ZIP_CODE);
		fillValidInformation();

		fillBillingAddressExceptField(AddressFields.FIRST_NAME);

		verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.FIRST_NAME);

		fillBillingAddressExceptField(AddressFields.LAST_NAME);

		verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.LAST_NAME);

		fillBillingAddressExceptField(AddressFields.PHONE);

		verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.PHONE);

		fillBillingAddressExceptField(AddressFields.EMAIL);

		verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.EMAIL);

		fillBillingAddressExceptField(AddressFields.STREET_ADDRESS);

		verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.STREET_ADDRESS);

		fillBillingAddressExceptField(AddressFields.ZIP_CODE);

		verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.ZIP_CODE);
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	private void checkOutFromShoppingCartPage() {
		Logger.info("In My Cart, click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void fillBillingAddressExceptField(AddressFields field) {
		Logger.info("In Payment and Review page: \n" + "- Fill valid all information and leave " + field.getValue2SC() + " fields in Billing Address empty\n" + "- Click \"PLACE ORDER\"");
		invalidAddress = customerAddress.clone();
		invalidAddress.updateFieldValueBy(field, "");
		paymentAndReviewPage.fillBillingAddress(invalidAddress);
		paymentAndReviewPage.placeOrder();
	}

	private void fillShippingAddressExceptField(AddressFields field) {
		Logger.info("In Shipping Address page: \n" + "- Fill valid all information and leave " + field.getValue2SC() + " fields in Shipping Address empty\n" + "- Click \"CONTINUE\"");
		invalidAddress = customerAddress.clone();
		invalidAddress.updateFieldValueBy(field, "");
		shippingAddressPage.fillShippingAddress(invalidAddress);
		Common.waitForPageLoad();
		shippingAddressPage.clickContinueButton();
		Common.waitForPageLoad();
	}

	private void fillValidInformation() {
		Logger.info(" In Shipping Address page: \n" + "- Fill valid all information\n" + "- Click \"CONTINUE\"");
		shippingAddressPage.fillShippingAddress(customerAddress);
		shippingAddressPage.clickContinueButton();
		Logger.info("In Payment & Review page\r\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n");
		paymentAndReviewPage.fillCreditCardNumber();
	}

	private void initTestCaseData() {
		//    Constants.ds.setLibraries("*LIBL");
		//  	Constants.ds.setNaming("system"); 
		Logger.tc("TC_2SCON_002 - Shipping And Billing Address With Each Mandatory Field Empty\n");
		Logger.to("TO_2SCON_6 - Warning message when leaving each mandatory field Billing address empty");
		Logger.to("TO_2SCON_12 - Warning message when leaving each mandatory field Shipping Address empty");
		item.initRandom(Recipient.MYSELF);
		customerAddress = lstAddresses.getDefaultShippingAddress();
	}

	private void searchAndAddItem(Item item) {
		String keyword = Common.getNumberFromText(item.getId());
		Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
		generalPage.search(keyword);
		Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		productPage.addSKUToCart(item, false);
		productPage.checkOut();
	}

	private void verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields field) {
		Logger.verify("The warning messages are displayed:\n" + "- Please Enter A " + field.getValue2SC());
		assertEquals(paymentAndReviewPage.getErrorMessageByField(field), Messages.getEmptyErrorMessageInPaymentAndReviewPage(field));
	}

	private void verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields field) {
		Logger.verify("The warning messages are displayed:\n" + "- Please Enter A " + field.getValue2SC());
		assertEquals(shippingAddressPage.getErrorMessageByField(field), Messages.getEmptyErrorMessageByShippingField(field));
	}
}
