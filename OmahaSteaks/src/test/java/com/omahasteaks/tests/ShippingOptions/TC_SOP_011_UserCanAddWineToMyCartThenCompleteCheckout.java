package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_011_UserCanAddWineToMyCartThenCompleteCheckout extends TestBase_2SC {
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress myAddress, recipientAddress;
    @Inject
    Item myWine;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ProductPage productPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    SignInPage signInPage;

    @Test
    public void TC_SOP_011_User_Can_Add_Wine_To_My_Cart_Then_Complete_Checkout() {
	initTestCaseData();

	addWineIntoCartThenCheckout(myWine);

	checkOutFromShoppingCartPage();

	verifyShippingOptionsDoesNotAppear();

	fillShippingAddressAndContinueCheckout();

	verifyWineInCartMessageDisplaysInPaymentAddReviewPage();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void addWineIntoCartThenCheckout(Item item) {
	Logger.info("From Homepage, enter \"" + Common.getNumberFromText(item.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("\"In Product Page: Add this item to " + item.getRecipient() + " - If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	generalPage.addFirstSKUToCart(item);
	productPage.selectExclusiveOffer(false);
	Logger.info("Click \"Checkout\"");
	generalPage.checkOut();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage2SC.fillShippingAddress(recipientAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_011 UserCanAddWineToMyCartThenCompleteCheckout");
	Logger.to("TO_SOP_01 'Shipping Options' section does not appear in the Shipping Address page When My Cart contains Wine item");
	Logger.to("TO_SOP_05 Message 'This item will ship separately from your food.' does not appear in My Cart section of the Payment & Review page when My Cart contains only Wine item.");
	Logger.to("TO_SOP_07 \"Adult signature required (Age 21+) for delivery. We will make 3 delivery attempts on consecutive business days.\" message displays in My Cart section of the Payment & Review page when My Cart contains only Wine item.");
	myAddress = lstAddresses.getDefaultBillingAddress();
	recipientAddress = lstAddresses.getDefaultShippingAddress();
	myWine.initRandom(Recipient.MYSELF, true);
    }

    private void verifyShippingOptionsDoesNotAppear() {
	Logger.verify("'Shipping Options' section does not appear in the Shipping Address page When My Cart contains Wine item.");
	assertFalse(shippingAddressPage2SC.isShippingOptionsDisplayed(), "'Shipping Options' section appears in the Shipping Address page When My Cart contains Wine item.");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void verifyWineInCartMessageDisplaysInPaymentAddReviewPage() {
	Logger.verify("Verify the message wine in cart is displayed in Payment and Review page");
	assertEquals(paymentAndReviewPage2SC.getWineInCartMesssage(Recipient.MYSELF.getValue()), Messages.WINE_IN_CART_MESSAGE, "The message wine in cart is not displayed as expected");

	Logger.verify("Message 'This item will ship separately from your food.'  does not appear in My Cart section of the Payment & Review page when My Cart contains only Wine item.");
	assertNotEquals(paymentAndReviewPage2SC.getNoteOfSKU(myWine), Messages.NOTE_OF_SKU_MESSAGE, "Message \"This item will ship separately from your food.\" appears next to Wine in My Cart");

    }

}
