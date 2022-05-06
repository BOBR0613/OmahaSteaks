package com.omahasteaks.tests.LastMinuteGift;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.DeliveryTab;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.LastMinuteGiftPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_LGP_002_AddSKUOfFreeRushDeliveryTabInLastMinuteGiftPageAndCompleteCheckOut extends TestBase_2SC {
    CustomerAddress myAddress;

    @Inject
    GeneralPage generalPage;

    @Inject
    LastMinuteGiftPage lastMinuteGiftPage;

    @Inject
    ProductPage productPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Inject
    SKU sku;

    @Inject
    ListAddresses lstAddress;

    @Test
    public void TC_LGP_002_Add_SKU_Of_Free_Rush_Delivery_Tab_In_Last_Minute_Gift_Page_And_Complete_Check_Out() {

	initTestCaseData();

	goToLastMinuteGiftPage();

	clickFreeRushDeliveryTab();

	verifyFirstItemDoesNotDisplayOutOfStock();

	verifyPromotionMessageDisplaysWithSKUsUnderFreeRushDeliveryTab();

	addFirstSKUToCartAndCheckOut();

	checkOutFromShoppingCartPage();

	fillShippingAddressAndClickContinue();

	verifyRushDeliveryDisplaysInPaymentAndReviewPage();

	verifyRushDeliveryCostDisplaysAsFREEInPaymentAndReviewPage();

	fillBillingAddress();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	Common.waitForDOMChange();
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    public void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    public void fillBillingAddress() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
    }

    public void verifyRushDeliveryCostDisplaysAsFREEInPaymentAndReviewPage() {
	Logger.verify("Rush Delivery cost displays as FREE in 'Payment & Review' page after adding SKU of \"Free Rush Delivery\" tab in Last Minute Gift page");
	assertEquals(paymentAndReviewPage2SC.getShippingCostByShippingMethod(Recipient.MYSELF.getValue(), "Rush Delivery"), Constants.SHIPPING_COST_FREE);
    }

    public void verifyRushDeliveryDisplaysInPaymentAndReviewPage() {
	Logger.verify("Verify Rush Delivery displays in 'Payment & Review' page after adding SKU of \"Free Rush Delivery\" tab in Last Minute Gift page");
	Common.waitForDOMChange();
	assertTrue(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(),  "Rush Delivery"), " Rush Delivery does not display in 'Payment & Review' page after adding SKU of \"Free Rush Delivery\" tab in Last Minute Gift page");
    }

    public void fillShippingAddressAndClickContinue() {
	Logger.info("Fill all valid informations in Shipping Address page" + "Click Continue");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    public void checkOutFromShoppingCartPage() {
	Logger.info("In Shopping Cart page, click \"Check out\" button");
	shoppingCartPage.checkOut();
    }

    public void addFirstSKUToCartAndCheckOut() {
	Logger.info("Add the first SKU and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Check out\"");
	lastMinuteGiftPage.addFirstSKUToCartFromProgressTab(sku, DeliveryTab.FREE_RUSH_DELIVERY);
	generalPage.selectExclusiveOffer(false);
	generalPage.checkOut();
    }

    public void verifyPromotionMessageDisplaysWithSKUsUnderFreeRushDeliveryTab() {
	Logger.verify("Verify \"Price Includes Rush Shipping\" message displays with SKUs under \"Free Rush Delivery\" tab");
	Common.waitForDOMChange();
	assertTrue(lastMinuteGiftPage.isPromotionMessageDisplayed(Constants.RUSH_DELIVERY_MESSAGE), "\"Price Includes Rush Shipping\" message does not display with SKUs under \"Free Rush Delivery\" tab");
    }

    public void verifyFirstItemDoesNotDisplayOutOfStock() {
	Logger.verify("Verify First Item of \"Free Rush Delivery\" tabs in Last Minute Gift page does not displays Out Off Stock");
	Common.waitForDOMChange();
	assertFalse(lastMinuteGiftPage.isFirstSKUDisplayedOutOfStock(), "First Item of \"Free Rush Delivery\" tab in Last Minute Gift page displays Out Off Stock");
    }

    public void clickFreeRushDeliveryTab() {
	Logger.info("Click \"Free Rush Delivery\" tab in Last-Minute Gift page");
	lastMinuteGiftPage.clickProgressTab(Constants.FREE_RUSH_DELIVERY);
    }

    public void goToLastMinuteGiftPage() {
	Logger.info("Go to Last-Minute Gift page in Homepage");
	generalPage.clickSideMenuItemLink(SideMenuItem.LAST_MINUTE_GIFT);
    }

    public void initTestCaseData() {
	Logger.tc("TC_LGP_002 Add SKU of \"Free Rush Delivery\" tab in Last Minute Gift page and complete checkout");
	Logger.to("TO_LGP_005 Rush Delivery cost displays as FREE in 'Payment & Review' page after adding SKU of \"Free Rush Delivery\" tab in Last Minute Gift page");
	Logger.to("TO_LGP_006 The Shipping Options displays Rush Delivery in 'Payment & Review' page after adding SKU of \"Free Rush Delivery\" tabs in Last Minute Gift page");
	Logger.to("TO_LGP_007 First Item of \"Free Rush Delivery\" tab in Last Minute Gift page does not display Out Off Stock");
	Logger.to("TO_LGP_008 \"Price Includes Rush Shipping\" message displays with SKUs under \"Free Rush Delivery\" tab");
	sku.setRecipient(Recipient.MYSELF);
	myAddress = lstAddress.getDefaultShippingAddress();
    }

}
