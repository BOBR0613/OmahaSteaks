package com.omahasteaks.tests.Checkout.NegativeCases;
/*package tests.Checkout.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.AddressFields;
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
import tests.TestBase;
import utils.common.Common;
import utils.common.Constants;
import utils.common.Messages;
import utils.helper.Logger;

public class TC_CON_007_WarningMessageForInvalidCreditCardData extends TestBase {
    CustomerAddress billingAddress;
    CustomerAddress shippingAddress;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item itemMyself;
    @Inject
    GeneralPage generalPage;
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
    public void TC_CON_007_Warning_Message_For_Invalid_Credit_Card_Data() {
	initTestCaseData();
	searchAndAddItem(itemMyself);
	goToMyCart();
	checkOutFromShoppingCartPage();
	fillBillingAddressAndContinueCheckout();
	fillShippingAddressAndContinueCheckout();
	fillCreditCardBeEmptyAndVerifyMessage();
	fillInvalidCreditCardAndVerifyMessage();
	fillInvaidExpireDateAndVerifyMessage();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddressAndContinueCheckout() {
	Logger.info("In Billing Address form: \n" + " - Fill valid mandatory information\n" + "- Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout ");
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
    }

    private void fillCreditCardBeEmptyAndVerifyMessage() {
	Logger.info("From Payment Option and Review page, let Credit Card be empty\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.placeMyOrder();
	Logger.verify("Verify that a message:\n" + "Credit Card: Invalid credit card type--please double check your entry.\"");
	assertEquals(paymentAndReviewPage.getInvalidMessageByField(AddressFields.CREDIT_CARD), Messages.INVALID_CREDIT_CARD);
    }

    private void fillInvaidExpireDateAndVerifyMessage() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: enter a month which is smaller than current month of current year");
	paymentAndReviewPage.fillCreditCardNumber(Constants.CREDIT_CARD_NUMBER, false);
	paymentAndReviewPage.placeMyOrder();
	Logger.verify("Verify that a message appears:\n" + "Credit Card Date: Oops, that expiration date is not valid. Please verify your card's expiration date and reselect from the drop down menu.\"");
	assertEquals(paymentAndReviewPage.getInvalidMessageByField(AddressFields.CREDIT_CARD_DATE), Messages.INVALID_CREDIT_CARD_DATE);
    }

    private void fillInvalidCreditCardAndVerifyMessage() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 123456789\" number\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber("123456789", true);
	paymentAndReviewPage.placeMyOrder();
	Logger.verify("Verify that a message:\n" + "Credit Card: Invalid credit card type--please double check your entry.\"");
	assertEquals(paymentAndReviewPage.getInvalidMessageByField(AddressFields.CREDIT_CARD), Messages.INVALID_CREDIT_CARD);
    }

    private void fillShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address page:\n" + " - Check \"Check here to ship to the address you provided as the billing address\"\n" + " - Click Continue Checkout ");
	shippingAddressPage.checkToShipToTheAddressdAsTheBillingAddress();
	shippingAddressPage.continueCheckout();
    }

    private void goToMyCart() {
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_007_WarningMessageForInvalidCreditCardData");
	Logger.info("TO_CON_15	Warning message when filling blank Credit card in Payment Options and Review\n" + "TO_CON_16	Warning message when filling invalid Credit card in Payment Options and Review\n" + "TO_CON_17	Warning message when filling Expiration date less than current date");
	itemMyself.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }
}*/