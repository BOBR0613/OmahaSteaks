package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_008_BillingAddressPopupWithInvalidPhoneAndZipcode extends TestBase_2SC {
	@Inject
	ListSKUs myCart;
	@Inject
	SKU mySku;
	@Inject
	CustomerAddress newAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	SignInPage signInPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	MyAccountPage myAccountPage;

	@Test
	public void TC_2SCON_008_Billing_Address_Popup_With_Invalid_Phone_And_Zip_Code() {
		initTestCaseData();

		signIn();

		addSKUToCartAndCheckOut(mySku);

		enterInvalidPhoneAndZipCode();

		verifyInvalidPhoneErrorMessage();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSKUToCartAndCheckOut(SKU sku) {
		Logger.info("Search a SKU with id (randomly)\n" + "In Product Page:\n" + " - Leave \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Check Out\"\n" + "- If on mobile device: Click \"View My Cart\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		productPage.checkOut();

		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();

		Logger.info("In Shipping Address Page\n" + " - Click \"Continue\" button");
		shippingAddressPage2SC.clickContinueButton();
	}

	private void enterInvalidPhoneAndZipCode() {
		Logger.info("\"In Payment & Review page\n" + " - Click \"Edit this address\" in Billing Address section\n" + " - Enter invalid phone number\n" + " - Enter invalid zip code which does not match with State");
		paymentAndReviewPage2SC.selectEditBillingAddressLink();
		newAddress.phoneNumber = "012345678";
		newAddress.zipCode = "95129";
		/*
		 * newAddress.state = "California";
		 * paymentAndReviewPage2SC.fillBillingAddressInModal(newAddress);
		 */
		newAddress.state = "New York";
		paymentAndReviewPage2SC.fillBillingAddressInModal(newAddress);
		verifyInvalidZipCodeErrorMessage();
		Logger.info(" - Click \"Update Contact\" button\"");
		paymentAndReviewPage2SC.updateContactInModal();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_008	Billing Address Popup With Invalid Phone And Zipcode\n");
		Logger.to("TO_2SCON_28	In Edit Billing Address popup, warning message displays when filling invalid phone number\n");
		Logger.to("TO_2SCON_29	In Edit Billing Address popup, warning message displays when filling invalid zip code");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop11@omahasteaks.com");
		mySku = myCart.getRandomSKU(SkuType.ITEM, SkuStatus.EXISTING);
		mySku.setRecipient(Recipient.MYSELF);
		newAddress.initRandomInformation();
	}

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account);
	}

	private void verifyInvalidPhoneErrorMessage() {
		Logger.verify("Verify the warning \"The phone number you specified is invalid. Please specify a phone number with the area code.\" displays properly");
		assertEquals(paymentAndReviewPage2SC.getErrorMessageInModal(), Messages.BINVALID_PHONE_ERROR_MESSAGE_IN_MODAL);
	}

	private void verifyInvalidZipCodeErrorMessage() {
		String actualMessage = paymentAndReviewPage2SC.getErrorMessageAtBottom();
		Logger.verify("Verify the warning \"Warning! The zip code entered does not match USPS database. Please review before proceeding. Shipping Policy\" displays properly");
		assertEquals(actualMessage, Messages.BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_1);
	}
}
