package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.BillingAddressPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
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

public class TC_2SCON_007_BillingAddressPopupWithAllOrEachMandatoryFieldEmpty extends TestBase_2SC {
	@Inject
	ListSKUs myCart;
	@Inject
	SKU mySku;
	@Inject
	Item item;
	@Inject
	CustomerAddress newAddress;
	@Inject
	ListAddresses lstAddress;
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
	ConfirmationPage2SC confirmationPage2SC;
	 

	@Test
	public void TC_2SCON_007_Billing_Address_Popup_With_All_Or_Each_Mandatory_Field_Empty() {
		initTestCaseData();

		signIn();

		addSKUToCartAndCheckout(mySku, myCart);

		leaveAllMadatoryFieldsOfBillingAddressEmpty();

		Logger.info("In Edit Billing Address popup, warning message displays when leaving all mandatory fields empty");

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.FIRST_NAME);

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.LAST_NAME);

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.STREET_ADDRESS);

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.STATE);

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.CITY);

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.PHONE);

		verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.ZIP_CODE);
	}

	private void addSKUToCartAndCheckout(SKU sku, ListSKUs lSku) {
		Logger.info("Search a SKU with id (randomly)\n" + "In Product Page:\n" + " - Leave \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Check Out\"\n" + "- If on mobile device: Click \"View My Cart\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		productPage.checkOut();
		
		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
		
		Logger.info("In Shipping Address Page\n" + " - Click \"Continue\" button");
		shippingAddressPage2SC.clickContinueButton();
		shippingAddressPage2SC.clickConfirmAddressButton();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void initTestCaseData() {
		Logger.tc("TC_2SCON_007	Billing Address Popup With All Or Earch Mandatory Field Empty\n");
		Logger.to("TO_2SCON_26	In Edit Billing Address popup, warning message displays when leaving all mandatory fields empty\n");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrmember01@omahasteaks.com");
		mySku = myCart.getRandomSKU(SkuType.ITEM, SkuStatus.EXISTING);
		mySku.setRecipient(Recipient.MYSELF);
		myCart.initEmpty();
		newAddress.initRandomInformation();
	}

	private void leaveAllMadatoryFieldsOfBillingAddressEmpty() {
		Logger.info("In Payment & Review page\n" + " - Click \"Edit this address\" in Billing Address section\n" + " - Leave all mandatory fields empty\n" + " - Click \"Update Contact\" button");
		paymentAndReviewPage2SC.selectEditBillingAddressLink();
		newAddress.removeBillingAddressMandatoryFields();
		paymentAndReviewPage2SC.fillBillingAddressInModal(newAddress);
		paymentAndReviewPage2SC.updateContactInModal();
	}

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account);
	}

	private void verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields field) {
		Logger.verify("Verify a warning message \"" + Messages.getEmptyErrorMessageInPaymentAndReviewPageInModal(field) + "\" displays properly");
		assertEquals(paymentAndReviewPage2SC.getErrorMessageByField(field), Messages.getEmptyErrorMessageInPaymentAndReviewPageInModal(field));
	}
}
