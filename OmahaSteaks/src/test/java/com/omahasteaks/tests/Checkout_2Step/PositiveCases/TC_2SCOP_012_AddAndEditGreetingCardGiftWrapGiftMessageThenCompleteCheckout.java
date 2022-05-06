package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
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

public class TC_2SCOP_012_AddAndEditGreetingCardGiftWrapGiftMessageThenCompleteCheckout extends TestBase_2SC {
	String giftMessage;
	String giftMessageEdited;
	@Inject
	Package pkg;
    @Inject
    ListAddresses lstAddresses;
    @Inject
	CustomerAddress customerAddress;
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
	public void TC_2SCOP_012_Add_And_Edit_Greeting_Card_Gift_Wrap_Gift_Message_Then_Complete_Checkout() {
		initTestCaseData();

		signIn();

		searchAndAddpackageThenCheckout();

		selectBothGiftingOptionAndEnterGiftMessageThenContinue();

		verifyGiftMessageDisplays(giftMessage);

		verifyBothGiftingOptionDisplays();

		editGiftMessage();

		verifyGiftMessageDisplays(giftMessageEdited);

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();

		verifyOrderNumberInCorrectFormat();
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	private void verifyOrderNumberInCorrectFormat() {
		String getOrderNumberText = confirmationPage.getOrderNumberText();
		Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
		assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
	}

	private void editGiftMessage() {
		Logger.info("Click \"edit\" link to Edit the Gift Message\n" + "\"In Gift Message popup modal:\n" + " - Enter Edited Gift Message\n" + " - Click \"Submit\" button\"");
		paymentAndReviewPage.editGiftMessage(Recipient.MYSELF.getValue(), giftMessageEdited, "");
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage.fillCreditCardNumber();
		paymentAndReviewPage.placeOrderIgnoreError();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_012 - Add And Edit Greeting Cart Gift Wrap Gift Message Then Complete Checkout\n");
		Logger.to("TO_2SCOP_21 - In Shipping Address, user can add both Greeting Card and Gift Wrap and complete Checkout\n");
		Logger.to("TO_2SCOP_22 - In Shipping Address, user can add Gift Message and complete Checkout\n");
		Logger.to("TO_2SCOP_29 - In Payment & Review, user can edit Gift Message and complete Checkout\n");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop02@omahasteaks.com");
		pkg.initRandom(Recipient.MYSELF); 
		customerAddress = lstAddresses.getRandomShippingAddressByState("NY");
		giftMessage = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 30).replace("<", "").replace(">", "").trim();
		giftMessageEdited =  Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 30).replace("<", "").replace(">", "").trim();
	}

	private void searchAndAddpackageThenCheckout() {
		Logger.info("Search a SKU by id (randomly)");
		generalPage.search(Common.getNumberFromText(pkg.getId()));
		Logger.info("In Product Page:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Check out\"\n" + "- If on mobile device: Click \"View My Cart\"");
		productPage.addSKUToCart(pkg, false);
		productPage.checkOut();
		Logger.info("In Shopping Cart page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	    
	}

	private void selectBothGiftingOptionAndEnterGiftMessageThenContinue() {
		Logger.info("In Shipping Address page:\n" + " - Select a Greeting card to add\n" + " - Select Gift Wrap checkbox (e.g. \"Gift-Wrap Your Cooler | Add $7.99\")\n" + " - Enter Gift Message\n" + " - Click \"Continue\" button");
		shippingAddressPage.selectGiftOptionGreetingCard();
		shippingAddressPage.selectFirstGreetingCard();
		shippingAddressPage.selectGiftOptionGiftWrap();
		shippingAddressPage.enterGiftMessage(giftMessage);
		shippingAddressPage.clickContinueButton(); 
	}

	private void signIn() {
		Logger.info("In Homepage:\n" + " - Sign in with an existing account");
		generalPage.goToSignInPage();
		signInPage.signIn(account);
	}

	private void verifyBothGiftingOptionDisplays() {
		Logger.verify("Verify that both gifting option displays in Payment & Review page");
		assertEquals(paymentAndReviewPage.isGiftWrapAdded(Recipient.MYSELF.getValue()), true, "The gift wrap option is not added as expected");
		assertEquals(paymentAndReviewPage.isGreetingCardAdded(Recipient.MYSELF.getValue(), ""), true, "The greeting card is not added as expected");
	}

	
	private void verifyGiftMessageDisplays(String message) {
		System.out.println("Verify Gift Message: \"" + message + "\" displays properly");
		assertEquals(paymentAndReviewPage.getGiftMessage(Recipient.MYSELF.getValue()), message.replace("<", "").replace(">", "").trim(), "The gifting message is not displayed as expected");
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("In Order Receipt Page\n" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.");
		assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
	}
}
