package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_013_TokenizationErrorsWhenPayingWithOneCreditCard extends TestBase_2SC {
    CustomerAddress customerAddress;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item item;
    @Inject
    GeneralPage generalPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Test
    public void TC_2SCON_013_Tokenization_Errors_When_Paying_With_One_Credit_Card() {
	initTestCaseData();

	searchAndAddItem(item);

	checkOutFromShoppingCartPage();

	fillValidInformationInShippingAddressPageAndContinue();

	fillBillingAddress();

	Logger.info("In Payment and Review Page:\n" + "- Enter an incomplete CC number (fewer than 16 digits) starting with 4 in the CARD NUMBER text field.  For example, " + Constants.CREDIT_CARD_NUMBER_FEWER_THAN_16_DIGITS + ".\n" + "- Enter valid data in CARD EXPIRATION text field.\n" + "- Click PLACE ORDER button.");
	fillCreditCardNumberAndClickPlaceOrder(Constants.CREDIT_CARD_NUMBER_FEWER_THAN_16_DIGITS);

	verifyErrorMessage(Messages.INVALID_CREDIT_CARD_ERROR_MESSAGE_1);

	Logger.info("In Payment and Review Page:\n" + "- Enter invalid number (for example, " + Constants.INVALID_CREDIT_CARD_NUMBER + ") in CARD NUMBER text field.\n" + "- Enter valid data in CARD EXPIRATION text field.\n" + "- Click PLACE ORDER button.");
	fillCreditCardNumberAndClickPlaceOrder(Constants.INVALID_CREDIT_CARD_NUMBER);

	verifyErrorMessage(Messages.INVALID_CREDIT_CARD_NUMBER_ERROR_MESSAGE.get(Constants.SITE));
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void verifyErrorMessage(String errorMessage) {
	Logger.verify("Verify the error: \"" + errorMessage + "\" displays correctly.");
	assertEquals(paymentAndReviewPage2SC.getErrorMessage(), errorMessage);
    }

    private void closeModal() {
	Logger.info("In Payment and Review Page:\n" + "- Close 'We've encountered an issue processing this card' modal ");
	paymentAndReviewPage2SC.closeModal();
    }

    private void fillCreditCardNumberAndClickPlaceOrder(String creditCardNumber) {
	paymentAndReviewPage2SC.fillCreditCardNumber(creditCardNumber, true);
	paymentAndReviewPage2SC.placeOrder();
	closeModal();
    }

    private void fillBillingAddress() {
	Logger.info("In Payment and Review Page:\n" + "- Fill valid USA address into Billing Address section");
	paymentAndReviewPage2SC.fillBillingAddress(customerAddress);
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillValidInformationInShippingAddressPageAndContinue() {
	Logger.info("In Shipping Address page: \n" + "- Fill valid all information\n" + "- Click \"CONTINUE\"");
	shippingAddressPage2SC.fillShippingAddress(customerAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCON_013 - Tokenization Errors When Paying With One Credit Card");
	Logger.to("TO_2SCON_39 - One Credit Card - Tokenization errors when paying with one Credit Card number (fewer than 16 digits)\n");
	Logger.to("TO_2SCON_40 - One Credit Card - Tokenization errors when editing Credit Card number\n");
	item.initRandom(Recipient.MYSELF);
	customerAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	generalPage.addFirstSKUToCart(item);
	generalPage.selectExclusiveOffer(false);
	generalPage.checkOut();
    }
}
