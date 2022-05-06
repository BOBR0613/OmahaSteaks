package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_007_UserCanCompleteCheckoutwithTwoCreditCards extends TestBase_2SC {
    String selectedRecipient, deliveryDate;
    @Inject
    ListSKUs myCart;
    @Inject
    SKU mySku;
    @Inject
    CustomerAddress newAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    CategoryPage categoryPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ProductPage productPage;

    @Test
    public void TC_2SCOP_007_User_Can_Complete_Checkout_With_Two_Credit_Cards() {
	initTestCaseData();

	searchAndAddSKUToCart(mySku);

	fillShippingAddressAndClickContinue();

	fillBillingAddress();

	payWithTwoCards();

	verifyThat2ndCardAmountDisplaysCorrectly();

	verifyTheRemove2ndCardLinkAppears();

	clickRemove2ndCardLink();

	verify2ndCardEntriesDisappear();

	verifyAmountFieldForTheFirstCardAlsoDisappears();

	verifyPayWithTwoCardLinkAppears();

	placeOrder();

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

    private void verifyPayWithTwoCardLinkAppears() {
	// 10. Verify 'Pay with two card' link appears.
	Logger.verify("Verify 'Pay with two card' link appears.");
	assertTrue(paymentAndReviewPage2SC.isPayWithTwoCardsLinkDisplayed(), " - 'Pay with two card' link isn't displayed.");
    }

    private void verifyAmountFieldForTheFirstCardAlsoDisappears() {
	// 9. Verify Amount field for the first card also disappears.
	Logger.verify("Verify Amount field for the first card also disappears");
	assertFalse(paymentAndReviewPage2SC.isFirstCreditCardAmountDisplayed(), " - Amount field for the first card does not disappear");
    }

    private void verify2ndCardEntriesDisappear() {
	// 8. Verify 2ND CARD entries disappear.
	Logger.verify("Verify 2ND CARD entries disappear.");
	assertFalse(paymentAndReviewPage2SC.isRemove2ndCardLinkDisplayed(), " - 2ND CARD entries do not disappear");
    }

    private void clickRemove2ndCardLink() {
	// 7. Click the 'Remove 2nd card' link.
	Logger.info("Click the 'Remove 2nd card' link");
	paymentAndReviewPage2SC.clickRemove2ndCardLink();
    }

    private void verifyTheRemove2ndCardLinkAppears() {
	// 6. Verify the 'Remove 2nd card' link appears.
	Logger.verify("Verify the 'Remove 2nd card' link appears");
	assertTrue(paymentAndReviewPage2SC.isRemove2ndCardLinkDisplayed(), " - 'Remove 2nd card' link does not appear");
    }

    private void searchAndAddSKUToCart(SKU sku) {
	Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"" + "In Shopping Cart Page:\n" + " - Click \"Check Out\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddress() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
	paymentAndReviewPage2SC.fillBillingAddress(newAddress);
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address Page\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(newAddress);
	// selectedRecipient =
	// shippingAddressPage2SC.getSelectedRecipientNameInSendToDropDownList();
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_007 - User can complete Checkout with two credit cards");
	Logger.to("TO_2SCOP_24 - In Payment & Review, user can complete checkout with two credit cards");
	mySku = myCart.getRandomSKU(SkuType.ITEM, SkuStatus.EXISTING);
	mySku.setRecipient(Recipient.MYSELF);
	newAddress.initRandomInformation();
    }

    private void payWithTwoCards() {
	Logger.info("'In Payment & Review page\n" + " - Click 'Pay with two cards' link\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section of 1st Card\n" + " - 1st Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Fill \"5555555555554444\" number at Credit / Debit section of 2nd Card\n" + " - 2nd Card Expiration: we will generate randomly a date in future (MM/YY)");
	paymentAndReviewPage2SC.clickPayWithTwoCardsLink();
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillFirstCreditCardRandomAmount();
	paymentAndReviewPage2SC.fillSecondCreditCardNumber();
    }

    private void placeOrder() {
	paymentAndReviewPage2SC.placeOrder();
	Common.waitForPageLoad();
    }

    private void verifyThankYouMessageDisplays() {
	confirmationPage2SC.closeModal();
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), " - Thank You message is not displayed. ");
    }

    private void verifyThat2ndCardAmountDisplaysCorrectly() {
	Logger.verify("In Payment & Review page\n" + " - Verify that 2nd Card's amount displays the rest correctly");
	assertEquals(Math.round((Double.parseDouble(paymentAndReviewPage2SC.getTotalPrice().trim().replace("$", "")) - paymentAndReviewPage2SC.getFirstCreditCardAmount()) * 100.0) / 100.0, paymentAndReviewPage2SC.getSecondCreditCardAmount());
    }
}
