package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_010_AddAndEditGreetingThenCompleteCheckout extends TestBase_2SC {
	String selectedRecipient;
	String expectedGreetingCard;
	@Inject
	Item mySku;
	@Inject
	CustomerAddress newAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	SearchResultPage searchResultPage;
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
	public void TC_2SCOP_010_Add_And_Edit_Greeting_Then_Complete_Checkout() {
		initTestCaseData();

		addFirstSKUToCart(mySku);

		fillShippingAddress();

		selectGiftGreetingCard();

		enterGiftMessageAndContinueToPaymentPage();

		verifyGreetingCardDisplaysCorrectly(expectedGreetingCard);

		editGiftGreetingCard();

		verifyGreetingCardDisplaysCorrectly(expectedGreetingCard);

		fillBillingAddress();

		fillCreditCardNumberThenPlaceMyOrder();

		verifyThankYouMessageDisplays();

		verifyOrderNumberInCorrectFormat();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyOrderNumberInCorrectFormat() {
		String getOrderNumberText = confirmationPage2SC.getOrderNumberText();
		Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
		assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage2SC.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
	}

	private void addFirstSKUToCart(SKU sku) {
		Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		productPage.checkOut();
		shoppingCartPage.checkOut();
	}

	private void continueToPaymentAndReviewPage() {
		shippingAddressPage2SC.clickContinueButton();
	}

	private void editGiftGreetingCard() {
		Logger.info("In Payment & Review page\n" + " - Click \"edit\" link of Greeting Card\n" + " - Select \"Add a Greeting Card\"\n" + " - Select a secondary card to add");
		paymentAndReviewPage2SC.clickEditGiftOptionLinkByRecipientName(selectedRecipient);
		paymentAndReviewPage2SC.selectGiftOptionGreetingCardInModal();
		expectedGreetingCard = paymentAndReviewPage2SC.selectSecondGreetingCardInModal();
	}

	private void enterGiftMessageAndContinueToPaymentPage() {
		Logger.info("'In Shipping Address Page\n" + " - Enter Gift Message\n" + " - Click \"Continue\"");
		shippingAddressPage2SC.enterGiftMessage(Constants.GIFT_MESSAGE);
		continueToPaymentAndReviewPage();
	}

	private void fillBillingAddress() {
		Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
		paymentAndReviewPage2SC.fillBillingAddress(newAddress);
	}

	private void fillCreditCardNumberThenPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.placeOrderIgnoreError();
	}

	private void fillShippingAddress() {
		Logger.info("In Shipping Address Page\n" + " - Leave default \"send to\" option (Myself)\n" + " - Fill mandatory information in Shipping Address");
		shippingAddressPage2SC.fillShippingAddress(newAddress);
		selectedRecipient = Recipient.MYSELF.getValue();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_010 - Add And Edit Greeting Then Complete Checkout");
		Logger.to("TO_2SCOP_19 - In Shipping Address, user can add a Greeting Card and complete Checkout\n");
		Logger.to("TO_2SCOP_27 - In Payment & Review, user can edit a Greeting Card and complete Checkout\n");
		mySku.init(SkuType.PACKAGES, Recipient.MYSELF);
		newAddress.initRandomInformation();
	}

	private void selectGiftGreetingCard() {
		Logger.info("In Shipping Address Page\n" + " - Select the first Greeting card to add");
		shippingAddressPage2SC.selectGiftOptionGreetingCard();
		expectedGreetingCard = shippingAddressPage2SC.selectFirstGreetingCard();
	}

	/**
	 * @param expectedGreetingCard
	 */
	private void verifyGreetingCardDisplaysCorrectly(String expectedGreetingCard) {
		Common.waitForDOMChange();
		String actualGreetingCard = paymentAndReviewPage2SC.getGreetingCardImageByRecipientName(selectedRecipient);
		Logger.verify(" - Verify Greeting Card " + expectedGreetingCard + " displays correctly");
		assertTrue(expectedGreetingCard.contains(actualGreetingCard), "The greeting card is not displayed as expected");
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertEquals(confirmationPage2SC.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
	}
}
