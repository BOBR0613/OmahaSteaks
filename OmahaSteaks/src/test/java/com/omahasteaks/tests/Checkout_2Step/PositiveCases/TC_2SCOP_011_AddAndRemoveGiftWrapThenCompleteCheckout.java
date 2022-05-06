package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
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
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_011_AddAndRemoveGiftWrapThenCompleteCheckout extends TestBase_2SC {
	@Inject
	Package pkg;
	@Inject
	GeneralPage generalPage;
	@Inject
	SignInPage signInPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;
	@Inject
	ConfirmationPage2SC confirmationPage;

	@Test
	public void TC_2SCOP_011_Add_And_Remove_GiftWrap_Then_Complete_Checkout() {
		initTestCaseData();

		signIn();

		searchAndAddpackageThenCheckout();

		selectGiftWrapOptionThenContinue();

		Logger.verify("Verify that Gift Wrap option displays in Payment & Review page");
		verifyGiftWrapOptionDisplayed(true);

		removeGiftWrapOptionDisplayed();

		Logger.verify("Verify that Gift Wrap option is removed in Payment & Review page");
		verifyGiftWrapOptionDisplayed(false);

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();

		verifyOrderNumberIsCorrectFormat();
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	private void verifyOrderNumberIsCorrectFormat() {
		String getOrderNumberText = confirmationPage.getOrderNumberText();
		Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
		assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage.fillCreditCardNumber();
		paymentAndReviewPage.placeOrderIgnoreError();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_011 - Add And Remove Gift Wrap Then Complete Checkout\n");
		Logger.to("TO_2SCOP_20 - In Shipping Address, user can add a Gift Wrap and complete Checkout\n");
		Logger.to("TO_2SCOP_28 - In Payment & Review, user can remove a Gift Wrap option and complete Checkout\n");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop07@omahasteaks.com");
		pkg.init(SkuType.PACKAGES, Recipient.MYSELF);
	}

	private void removeGiftWrapOptionDisplayed() {
		Logger.verify("Click \"edit\" link to Edit the Gifting option\n" + " - Click \"No Thanks\" button to remove Gift Wrap option");
		paymentAndReviewPage.removeGiftingOption(Recipient.MYSELF.getValue());
	}

	private void searchAndAddpackageThenCheckout() {
		Logger.info("In Homepage:\n" + " - Search a SKU by id (randomly)");
		generalPage.search(Common.getNumberFromText(pkg.getId()));
		Logger.info("In Product Page:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Check out\"\n" + "- If on mobile device: Click \"View My Cart\"");
		productPage.addSKUToCart(pkg, false);
		generalPage.viewMyCart();
		Logger.info("In Shopping Cart page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	}

	private void selectGiftWrapOptionThenContinue() {
		Logger.info("In Shipping Address page:\n" + " - Select Gift Wrap checkbox \n" + " - Click \"Continue\" button");
		shippingAddressPage.selectGiftOptionGiftWrap();
		shippingAddressPage.clickConfirmAddressButton();
		shippingAddressPage.clickContinueButton();
		shippingAddressPage.clickConfirmAddressButton();
	}

	private void signIn() {
		Logger.info("In Homepage:\n" + " - Sign in with an existing account");
		generalPage.goToSignInPage();
		signInPage.signIn(account);
	}

	private void verifyGiftWrapOptionDisplayed(boolean isExisted) {
		String failedMessage = isExisted ? "The gift wrap option is not added as expected" : "The gift wrap option is not removed as expected";
		assertEquals(paymentAndReviewPage.isGiftWrapAdded(Recipient.MYSELF.getValue()), isExisted, failedMessage);
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("In Order Receipt Page\n" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.");
		assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
	}
}
