package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_024_PromotionMessageDisplaysWhenMyCartAndRecipientContainFoodItems extends TestBase_2SC {

    List<String> lstrecipientShippingOptions, lstMySelfShippingOptions;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;

    CustomerAddress myAddress, recipientAddress;

    @Inject
    Item myItem, recipientItem;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_SOP_024_Promotion_Message_Is_Displayed_When_My_Cart_And_Recipient_Contain_Food_Items() {
	initTestCaseData();

	searchAndAddItem(myItem);

	searchAndAddItem(recipientItem);

	goToShoppingCartPageThenClickCheckout();

	fillMyShippingAddressThenClickNextButton();

	fillRecipientShippingAddressThenClickContinueButton();

	verifyPromotionMessageIsDisplayedCorrectly();

	clickViewAllShippingOptionsInMyCartSection();

	verifyPromotionMessageIsDisplayedInShippingAndDeliveryPopup(myItem);

	selectShippingOption(myItem);

	verifyPromotionMsgIsDisplayedCorrectlyAfterUpdatingShippingOption(myItem);

	clickViewAllShippingOptionsInRecipientCartSection();

	verifyPromotionMessageIsDisplayedInShippingAndDeliveryPopup(recipientItem);

	selectShippingOption(recipientItem);

	verifyPromotionMsgIsDisplayedCorrectlyAfterUpdatingShippingOption(recipientItem);

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void clickViewAllShippingOptionsInMyCartSection() {
	Logger.info("In Payment & Review page: -Click 'View All Shipping Options\" link");
	lstMySelfShippingOptions = paymentAndReviewPage2SC.getShippingMethodsList(Recipient.MYSELF.getValue());
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
    }

    private void clickViewAllShippingOptionsInRecipientCartSection() {
	Logger.info("In Payment & Review page: -Click 'View All Shipping Options\" link");
	lstrecipientShippingOptions = paymentAndReviewPage2SC.getShippingMethodsList(Recipient.DD.getValue());
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.DD.getValue());
    }

    private void verifyPromotionMsgIsDisplayedCorrectlyAfterUpdatingShippingOption(SKU sku) {
	Logger.verify("In Payment And Review Page:Verify Message \"Plus,Get a $20 Reward Crad!\" is displayed in cart 1 of 2 and cart 2 of 2 section after selecting option from  'Shipping and Delivery'  popup  When  My cart and Recipient Cart contain Food items");
	assertEquals(paymentAndReviewPage2SC.getPromotionMsgAfterUpdatingShippingMethod(sku.getRecipient().getValue()), Constants.PROMOTIONS_MSG_IN_MODAL, "Promotion messages is not displayed after updating shipping method");
    }

    private void selectShippingOption(SKU sku) {
	Logger.info("In Shipping and Delivery popup:Select Shipping method (randomly) in \"Shipping And Delivery\" modal");
	if (sku.getRecipient().getValue().equals(Recipient.MYSELF.getValue()))
	    paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal(lstMySelfShippingOptions);
	else
	    paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal(lstrecipientShippingOptions);
	paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
    }

    private void verifyPromotionMessageIsDisplayedInShippingAndDeliveryPopup(SKU sku) {
	Logger.verify("In Shipping And Delivery Popup :Verify Each qualifying option(except custom delivery , Express Delivery and Rush Delivery) displays  \"Plus,Get a $20 Reward Crad!\" in Shipping and Delivery  popup When  My cart and Recipient Cart contain only Dropship items (Wine) ");
	if (sku.getRecipient().getValue().equals(Recipient.MYSELF.getValue()))
	    assertTrue(paymentAndReviewPage2SC.isPromotionsMsgDisplayedInShippingAndDeliveryPopup(lstMySelfShippingOptions), "Promotion message is not displayed in Shipping and Delivery  popup");
	else
	    assertTrue(paymentAndReviewPage2SC.isPromotionsMsgDisplayedInShippingAndDeliveryPopup(lstrecipientShippingOptions), "Promotion message is not displayed in Shipping and Delivery  popup");
    }

    private void verifyPromotionMessageIsDisplayedCorrectly() {
	Logger.verify("In Payment And Review Page:- Verify  Message \"Choose an option below and get a $20 Reward Card!\" is displayed correctly in Cart 1 of 2 and cart 2 of 2 section when My cart and  recipient cart contain Food items");
	assertEquals(paymentAndReviewPage2SC.getPromotionMessage(Recipient.MYSELF.getValue()), Constants.PROMOTIONS_MSG, "Promotion Message is not displayed in Cart 1 of 2 section as expected");
	assertEquals(paymentAndReviewPage2SC.getPromotionMessage(Recipient.DD.getValue()), Constants.PROMOTIONS_MSG, "Promotion Message is not displayed in Cart 2 of 2 section as expected");
    }

    private void fillRecipientShippingAddressThenClickContinueButton() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(recipientAddress);
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

    private void initTestCaseData() {
	Logger.tc("TC_SOP_024 - Promotion message is displayed when my cart and recipient contain food items");
	Logger.to("TO_SOP_61 - In Payment & Review page:  Message \"Choose an option below and get a $20 Reward Card!\" is displayed correctly  in Cart 1 of 2 section and Cart 2 of 2 section  when My cart and recipient cart contain Food items");
	Logger.to("TO_SOP_68 - In Payment & Review page: Message \"Plus,Get a $20 Reward Crad!\" is displayed in Cart 1 of 2 section and Cart 2 of 2 section after selecting option from 'Shipping and Delivery'popup  When  My cart and Recipient Cart contain Food items");
	Logger.to("TO_SOP_78 - In Payment & Review page: Each qualifying option displays  \"Plus,Get a $20 Reward Crad!\" in Shipping and Delivery  popup When  My cart and Recipient Cart contain Food items");
	myAddress = lstAddresses.getDefaultBillingAddress();
	recipientAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF);
	recipientItem.initRandom(Recipient.DD);
    }
}
