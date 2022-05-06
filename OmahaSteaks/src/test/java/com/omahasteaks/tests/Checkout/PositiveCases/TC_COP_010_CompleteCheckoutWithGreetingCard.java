package com.omahasteaks.tests.Checkout.PositiveCases;
/*package tests.Checkout.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.Recipient;
import data.objects.CustomerAddress;
import data.objects.Item;
import pages.BillingAddressPage;
import pages.ConfirmationPage;
import pages.GeneralPage;
import pages.PaymentAndReviewPage;
import pages.ProductPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import pages.SignInPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Constants;
import utils.helper.Logger;

public class TC_COP_010_CompleteCheckoutWithGreetingCard extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item itemMyself;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CustomerAddress myShippingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    SignInPage signInPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    BillingAddressPage billingAddressPage;
    @Inject
    ShippingAddressPage shippingAddressPage;
    @Inject
    PaymentAndReviewPage paymentAndReviewPage;
    @Inject
    ConfirmationPage confirmationPage;

    @Test
    public void TC_COP_010_Complete_Checkout_With_Greeting_Card() {
	initTestCaseData();

	signInWithExistingAccount();

	searchAndAddItem(itemMyself);

	checkOutFromShoppingCartPage();

	fillBillingAddressAndContinueCheckout();

	String greetingCardLabel = selectGreetingCardAndEnterGiftMessageThenGetGreetingCardLabel(1, Constants.GIFT_MESSAGE);

	fillShippingAddressAndContinueCheckout(myShippingAddress);

	verifyGreetingCard(greetingCardLabel);

	verifyGreetingMessage(Recipient.MYSELF, Constants.GIFT_MESSAGE);

	editShippingAddress();

	greetingCardLabel = selectGreetingCardAndEnterGiftMessageThenGetGreetingCardLabel(2, Constants.EDITED_GIFT_MESSAGE);

	continueCheckoutInShippingAddressPage();

	verifyGreetingCard(greetingCardLabel);

	verifyGreetingMessage(Recipient.MYSELF, Constants.EDITED_GIFT_MESSAGE);

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	goToMyCart();
	shoppingCartPage.checkOut();
    }

    private void continueCheckoutInShippingAddressPage() {
	Logger.info("Continue Checkout from Shipping Address Page");
	shippingAddressPage.continueCheckout();
    }

    private void editShippingAddress() {
	Logger.info("Click Edit Shipping Address Link of Recipient");
	paymentAndReviewPage.clickRecipientEditShippingAddressLink(Recipient.MYSELF);
    }

    private void fillBillingAddressAndContinueCheckout() {
	Logger.info("In Billing Address form: \n" + " - Fill valid mandatory information\n" + "- Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout ");
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber();
	paymentAndReviewPage.placeMyOrder();
    }

    private void fillShippingAddressAndContinueCheckout(CustomerAddress shippingAddress) {
	Logger.info("In " + shippingAddress.firstName + " " + shippingAddress.lastName + " Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage.fillShippingAddress(shippingAddress);
	continueCheckoutInShippingAddressPage();
    }

    private void goToMyCart() {
	Logger.info("Go to Shopping Cart Page");
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.tc("TC_COP_010_CompleteCheckoutWithGreetingCard");
	Logger.to("TO_COP_07	Guest can add a Greeting Card with text message and complete Checkout\n");
	Logger.to("TO_COP_30	Guest can Edit Greeting Card and complete Checkout\n");
	account = Constants.LIST_ACCOUNTS.getRandomAccount();
	itemMyself.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	myShippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private String selectGreetingCardAndEnterGiftMessageThenGetGreetingCardLabel(int position, String giftMessage) {
	Logger.info("select Greeting Card and enter gift message " + giftMessage);
	shippingAddressPage.clickGreetingCardImageByPosition(position);
	String greetingCardLabel = shippingAddressPage.getGreetingCardLabelinModal();
	shippingAddressPage.clickButtonAddGreetingCardInModal();
	shippingAddressPage.enterPersonalizedGreeting(giftMessage);
	Logger.info("Get Greeting Card Label: " + greetingCardLabel);
	return greetingCardLabel;
    }

    private void signInWithExistingAccount() {
	Logger.info("Sign in with existing account");
	generalPage.goToSignInPage();
	signInPage.signIn(account.getEmail(), account.getPassword());
    }

    private void verifyGreetingCard(String greetingCardLabel) {
	Logger.verify("Check Greeting Card with label: " + greetingCardLabel);
	assertTrue(paymentAndReviewPage.isGreetingCardByRecipientAndCardTypeExisted(Recipient.MYSELF, greetingCardLabel), "The greeting card is not added as expected");
    }

    private void verifyGreetingMessage(Recipient recipient, String giftMessage) {
	Logger.verify("Check Greeting Message: " + giftMessage + " of " + recipient.getValue());
	assertEquals(paymentAndReviewPage.getGreetingMessageByRecipient(recipient), giftMessage, "The gift message is not displayed as expected");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/