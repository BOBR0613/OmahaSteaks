package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Item;
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

public class TC_2SCOP_015_CompleteCheckoutWhenMyCartContainingWine extends TestBase_2SC {
    @Inject
    Item item;
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
    public void TC_2SCOP_015_Complete_Checkout_When_MyCart_Containing_Wine() {
	initTestCaseData();

	signIn();

	searchAndAddpackageThenCheckout();

	fillShippingAddress();
	Common.waitForPageLoad();
	verifyWineInCartMessageDisplaysInPaymentAddReviewPage();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyOrderNumberInCorrectFormat();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyOrderNumberInCorrectFormat() {
	String getOrderNumberText = confirmationPage.getOrderNumberText();
	Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
	assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
	assertTrue(confirmationPage.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("In New Payment and Review Page:\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "- Fill valid mandatory information at Billing Address section\n" + "- Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber();
	paymentAndReviewPage.placeOrder();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address page:\n" + " - Select both checkbox (Greeting Card and Gift Wrap) \n" + " - Enter Gift Message\n" + " - Click \"Continue\" button");
	shippingAddressPage.clickContinueButton();
    shippingAddressPage.clickConfirmAddressButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_015 - Complete Checkout When MyCart Containing Wine");
	Logger.to("TO_2SCOP_36 - Existing account can complete Checkout when cart containing wine\n");
	item.init(SkuType.WINE, Recipient.MYSELF);
	account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
    }

    private void searchAndAddpackageThenCheckout() {
	Logger.info("Search a wine with id (randomly)");
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("In Product Page:\n" + " - Leave \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Check Out\"\n" + "- If on mobile device: Click \"View My Cart\"");
	productPage.addSKUToCart(item, false);
	productPage.checkOut();
	Logger.info("In Shopping Cart page:\n" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    private void signIn() {
	Logger.info("Sign in with a valid account");
	generalPage.goToSignInPage();
	signInPage.signIn(account);
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("In Order Receipt Page\n" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }

    private void verifyWineInCartMessageDisplaysInPaymentAddReviewPage() {
	Logger.verify("Verify the message wine in cart is displayed in Payment and Review page");
	assertEquals(paymentAndReviewPage.getWineInCartMesssage(Recipient.MYSELF.getValue()), Messages.WINE_IN_CART_MESSAGE, "The message wine in cart is not displayed as expected");
    }
}
