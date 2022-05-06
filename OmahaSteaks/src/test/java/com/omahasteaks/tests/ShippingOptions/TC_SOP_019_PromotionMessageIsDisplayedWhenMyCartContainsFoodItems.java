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
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_019_PromotionMessageIsDisplayedWhenMyCartContainsFoodItems extends TestBase_2SC {
    CustomerAddress myAddress;

    String shippingMethod;

    List<String> lstShippingOptions;

    @Inject
    Item myItem;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Test
    public void TC_SOP_019_Promotion_Message_Is_Displayed_When_My_Cart_Contains_Food_Items() {
	initTestCaseData();

	searchAndAddItem(myItem);

	goToShoppingCartPageThenClickCheckout();

	fillMyShippingAddressThenClickContinueButton();

	verifyPromotionMessageIsDisplayedCorrectly();

	clickViewAllShippingOptions();

	verifyPromotionMessageIsDisplayedInShippingAndDeliveryPopup();

	selectShippingOption();

	verifyPromotionMsgIsDisplayedCorrectlyAfterUpdatingShippingOption();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyPromotionMsgIsDisplayedCorrectlyAfterUpdatingShippingOption() {
	Logger.verify("Verify Message \"Plus,Get a $20 Reward Crad!\" is displayed in My Cart section after selecting option from  'Shipping and Delivery'  popup  When  My cart contains Food items");
	assertEquals(paymentAndReviewPage2SC.getPromotionMsgAfterUpdatingShippingMethod(Recipient.MYSELF.getValue()), Constants.PROMOTIONS_MSG_IN_MODAL, "Promotion messages is not displayed after updating shipping method");
    }

    private void selectShippingOption() {
	Logger.info("In Shipping and Delivery popup:Select Shipping method (randomly) in \"Shipping And Delivery\" modal");
	paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal(lstShippingOptions);
	paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
    }

    private void verifyPromotionMessageIsDisplayedInShippingAndDeliveryPopup() {
	Logger.verify("In Shipping And Delivery Popup : Verify Each qualifying option(except custom delivery , Express Delivery and Rush Delivery) displays \"Plus,Get a $20 Reward Crad!\" in Shipping and Delivery  popup when My Cart contains  Food items");
	assertTrue(paymentAndReviewPage2SC.isPromotionsMsgDisplayedInShippingAndDeliveryPopup(lstShippingOptions), "Promotion message is not displayed in Shipping and Delivery  popup");
    }

    private void clickViewAllShippingOptions() {
	lstShippingOptions = paymentAndReviewPage2SC.getShippingMethodsList(Recipient.MYSELF.getValue());
	Logger.info("In Payment & Review page:Click 'View All Shipping Options\" link in My Cart Section");
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
    }

    private void verifyPromotionMessageIsDisplayedCorrectly() {
	Logger.verify("In Payment And Review Page :Verify  Message \"Choose an option below and get a $20 Reward Card!\" is displayed correctly in My Cart section when My cart contains Food items");
	assertEquals(paymentAndReviewPage2SC.getPromotionMessage(Recipient.MYSELF.getValue()), Constants.PROMOTIONS_MSG, "Promotion Message is not displayed as expected");
    }

    private void fillMyShippingAddressThenClickContinueButton() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickContinueButton();
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
	Logger.tc("TC_SOP_019 - Promotion message is displayed When My cart contains food items");
	Logger.to("TO_SOP_59 - In Payment & Review page:  Message \"Choose an option below and get a $20 Reward Card!\" is displayed correctly in My Cart section when My cart contains Food items");
	Logger.to("TO_SOP_63 - In Payment & Review page: Each qualifying option(except custom delivery , Express Delivery and Rush Delivery) displays \"Plus,Get a $20 Reward Crad!\" in Shipping and Delivery  popup when My Cart contains  Food items");
	Logger.to("TO_SOP_66 - In Payment & Review page: Message \"Plus,Get a $20 Reward Crad!\" is displayed in My Cart section after selecting option from  'Shipping and Delivery'  popup  When  My cart contains Food items");
	myAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF);
    }
}
