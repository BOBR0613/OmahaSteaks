package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
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
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_025_UserCanSelectShippingMethodWhenMyCartAndRecipientCartContainEGiftCard extends TestBase_2SC {
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    String shippingMethod;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;

    CustomerAddress myAddress, recipientAddress;

    @Inject
    Item myItem, myEGiftCard;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    SearchResultPage searchResultPage;

    @Inject
    CategoryPage categoryPage;

    @Test
    public void TC_SOP_025_User_Can_Select_Shipping_Method_When_My_Cart_And_Recipient_Cart_Contain_EGift_Card() {
	initTestCaseData();

	searchAndAddItem(myItem);

	searchAndAddEGiftCard(myEGiftCard);

	goToShoppingCartPageThenClickCheckout();

	fillMyShippingAddressThenClickNextButton();

	fillRecipientShippingAddressThenClickContinueButton();

	verifyTheStandardShippingIsDefaultSelected();

	verifyPromotionMessageIsDisplayedCorrectly();

	verifyTheShippingOptionsHaveOnlyStandardShippingInCartConatinsEGiftItemSection();

	selectRandomMySelfShippingOption();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyShippingMethodIsDisplayedCorrectlyInConfirmPage();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void selectRandomMySelfShippingOption() {
	Logger.info("In Payment & Review page: Select Shipping method (randomly) in cart 1 of 2 section");
	shippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethod(Recipient.MYSELF.getValue());
	paymentAndReviewPage2SC.selectShippingMethod(Recipient.MYSELF.getValue(), shippingMethod);
    }

    private void verifyTheStandardShippingIsDefaultSelected() {
	Logger.verify("In Payment & Review page:Verify the Standard Shipping is default  selected  in Cart 1 of 2 section when My cart and Recipient cart contain Food and E-Gift items");
	assertEquals(paymentAndReviewPage2SC.getSelectedShippingMethod(Recipient.MYSELF.getValue()), Constants.STANDARD_SHIPPING, "The standard shipping is not selected default method in Cart 1 of 2 Section");
    }

    private void verifyTheShippingOptionsHaveOnlyStandardShippingInCartConatinsEGiftItemSection() {
	Logger.verify("In Payment & Review page:Verify the shipping options have only 'Standard Shipping' method in Cart 2 of 2  section when My cart and Recipient cart contain Food and E-Gift items");
	assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.DD.getValue()).contains(Constants.STANDARD_SHIPPING), "Standard shipping is not  displayed in Cart conatins E-Gift item section");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.RUSH_SHIPPING), "Rush Delivery displays in Cart conatins E-Gift item section");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.CUSTOM_SHIPPING), "Custom Delivery Date displays in Cart conatins E-Gift item section");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.EXPRESS_SHIPPING), "Express Delivery displays in Cart conatins E-Gift item section");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.THANKSGIVING_SHIPPING), "For Thanksgiving delivery displays in Cart conatins E-Gift item section");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.PREFERRED_SHIPPING), "Preferred Shipping displays in Cart conatins E-Gift item section");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.CHRISTMAS_DELIVERY), "Christmas Delivery displays in Cart conatins E-Gift item section");
    }

    private void verifyPromotionMessageIsDisplayedCorrectly() {
	Logger.verify("In Payment & Review page: Verify promotion message is displayed correctly when My cart and Recipient cart contain Food and E-Gift items");
	assertEquals(paymentAndReviewPage2SC.getPromotionMessage(Recipient.MYSELF.getValue()), Constants.PROMOTIONS_MSG, "Promotion Message is not displayed in Cart 1 of 2 section as expected");
	assertEquals(paymentAndReviewPage2SC.getPromotionMsgAfterUpdatingShippingMethod(Recipient.DD.getValue()), Constants.PROMOTIONS_MSG_IN_MODAL, "Promotion Message is not displayed in Cart 2 of 2 section as expected");
    }

    private void searchAndAddEGiftCard(SKU sku) {
	Logger.info("In Homepage: " + "    - Search a SKU( E-Gift Card )  ( eg. Omaha Steaks E-Gift Card) " + "    - Click \"Search\" button");
	generalPage.search("E-Gift Card");
	Logger.info("- Select the \"Omaha Steaks E-Gift Card\"\n" + "- Click \"Buy E Gift Card\" button");
	categoryPage.clickBuyEGiftCardButton();
	Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
	categoryPage.addEGiftCard(sku);
	generalPage.continueShopping();
    }

    private void fillRecipientShippingAddressThenClickContinueButton() {
	Logger.info("In Shipping Address form:- Fill valid information - Fill valid email into \"Email Address' textbox- Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(recipientAddress);
	shippingAddressPage2SC.fillEmailShippingAddress(recipientAddress.email);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void fillMyShippingAddressThenClickNextButton() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Next\"");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickNextButton();
    }

    private void goToShoppingCartPageThenClickCheckout() {
	Logger.info("Go to Shopping Cart Page then click checkout");
	generalPage.goToMyCart();
	shoppingCartPage.checkOut();
    }

    private void searchAndAddItem(Item item) {
	Logger.info("From Homepage, enter \"" + Common.getNumberFromText(item.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("\"In Product Page: Add this item to " + item.getRecipient() + " - If Exclusive Offer (Upsell offer) appears, click \"Add To Cart\"");
	generalPage.addFirstSKUToCart(item);
	productPage.selectExclusiveOffer(false);
	generalPage.continueShopping();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void verifyShippingMethodIsDisplayedCorrectlyInConfirmPage() {
	Logger.verify("In Confirmation page: Verify  Shipping method is displayed correctly in Cart 1 of 2 and cart 2 of 2 Section When My Cart and Recipient Cart contain Food items");
	assertTrue(confirmationPage2SC.getShippingMethodSection(myAddress).contains(shippingMethod), "The Shipping method is not displayed correctly");
	assertTrue(confirmationPage2SC.getShippingMethodSection(recipientAddress).contains(Constants.STANDARD_SHIPPING), "The Shipping method is not displayed correctly");
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_025 - User can select shipping method when my cart and recipient cart contain E-Gift card");
	Logger.to("TO_SOP_84 - In Payment & Review page: Promotion message is displayed correctly when My cart and Recipient cart contain Food and E-Gift items");
	Logger.to("TO_SOP_85 - In Payment & Review page:  The shipping options have only 'Standard Shipping' method  in Cart contains E-Gift item section when My cart and Recipient cart contain Food and E-Gift items");
	Logger.to("TO_SOP_86 - In Payment & Review page: The Standard Shipping is default selected in Cart contains Food item section when My cart and Recipient cart contain Food and E-Gift items");
	Logger.to("TO_SOP_87 - In Confirmation page:The  Shipping method is displayed correctly in Cart 1 of 2  and cart 2 of 2  Section When My cart and Recipient cart contain Food and E-Gift items");
	myAddress = lstAddresses.getDefaultShippingAddress();
	recipientAddress = lstAddresses.getDefaultBillingAddress();
	myItem.initRandom(Recipient.MYSELF);
	myEGiftCard.setRecipient(Recipient.DD);
	myEGiftCard.setPrice(5);
	myEGiftCard.setQuantity(1);
    }
}
