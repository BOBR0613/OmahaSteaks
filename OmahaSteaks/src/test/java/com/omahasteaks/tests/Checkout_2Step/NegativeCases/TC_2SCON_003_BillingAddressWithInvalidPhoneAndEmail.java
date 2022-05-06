package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
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

public class TC_2SCON_003_BillingAddressWithInvalidPhoneAndEmail extends TestBase_2SC {
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
	public void TC_2SCON_003_Billing_Address_With_Invalid_Phone_And_Email() {
		initTestCaseData();

		searchAndAddItem(item);

		checkOutFromShoppingCartPage();

		fillValidInformation();

		fillInvalidPhoneAndEmailInBillingAddress();

		verifyTheWarningMssagesIsDisplayed();
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	private void checkOutFromShoppingCartPage() {
		Logger.info("In My Cart, click CHECKOUT button"); 
		Common.delay(5);
		shoppingCartPage.checkOut();
		Common.waitForPageLoad();
	}

	private void fillInvalidPhoneAndEmailInBillingAddress() {
		Logger.info("In Payment & Review page: \n" + "- Fill valid all information except Phone and Email fields in Billing Address empty\n" + "- Click \"Place Order\"");
		invalidAddress = customerAddress.clone();
		invalidAddress.updateFieldValueBy(AddressFields.PHONE, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 9));
		invalidAddress.updateFieldValueBy(AddressFields.EMAIL, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 20));
		paymentAndReviewPage.fillBillingAddress(invalidAddress);
		paymentAndReviewPage.placeOrder();
	}

	private void fillValidInformation() {
		Logger.info(" In Shipping Address page: \n" + "- Fill valid all information\n" + "- Click \"CONTINUE\"");
		shippingAddressPage.fillShippingAddress(customerAddress);
		shippingAddressPage.clickContinueButton();
		Logger.info("In Payment & Review page\r\n" + " - Fill \"4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n");
		paymentAndReviewPage.fillCreditCardNumber();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_003 - Billing Address With Invalid Phone And Email");
		Logger.to("TO_2SCON_7 - Warning message when filling invalid phone number in Billing address");
		Logger.to("TO_2SCON_9 - Warning message when filling invalid email in Billing address");
		item.init(SkuType.OVER100,Recipient.MYSELF);
		customerAddress = lstAddresses.getRandomBillingAddress();
	}

	private void searchAndAddItem(Item item) {
		String keyword = Common.getNumberFromText(item.getId());
		Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
		generalPage.search(keyword);
		Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		productPage.addSKUToCart(item, false);
		productPage.checkOut();
	}

	private void verifyTheWarningMssagesIsDisplayed() {
		Logger.verify("The warning messages are displayed:\n" + "- Please Enter A Valid Phone Number");
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.PHONE), Messages.BINVALID_PHONE_ERROR_MESSAGE);
		Logger.verify("The warning messages are displayed:\n" + "- Please Enter A Valid Email Address");
		assertEquals(paymentAndReviewPage.getErrorMessageByField(AddressFields.EMAIL), Messages.BPINVALID_EMAIL_ERROR_MESSAGE);
	}
}
