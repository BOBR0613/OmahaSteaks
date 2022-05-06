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
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_LGP_001_AddSKUOfFreeExpressDeliveryTabInLastMinuteGiftPageAndCompleteCheckOut extends TestBase_2SC {
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
	public void TC_LGP_001_Add_SKU_Of_Free_Express_Delivery_Tab_In_Last_Minute_Gift_Page_And_Complete_Check_Out() {

		initTestCaseData();

		goToLastMinuteGiftPage();

		clickFreeExpressDeliveryTab();

		verifyFirstItemDoesNotDisplayOutOfStock();

		verifyPromotionMessageDisplaysWithSKUsUnderFreeExpressDeliveryTab();

		addFirstSKUToCartAndCheckOut();

		checkOutFromShoppingCartPage();

		fillShippingAddressAndClickContinue();

		verifyExpressDeliveryDisplaysInPaymentAndReviewPage();

		verifyExpressDeliveryCostDisplaysAsFREEInPaymentAndReviewPage();

		fillBillingAddress();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void verifyThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its");
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

	public void verifyExpressDeliveryCostDisplaysAsFREEInPaymentAndReviewPage() {
		Logger.verify("Verify Express Delivery cost displays as FREE in 'Payment & Review' page after adding SKU of \"Free Express Delivery\" tab in Last Minute Gift page");
		assertEquals(paymentAndReviewPage2SC.getShippingCostByShippingMethod(Recipient.MYSELF.getValue(), Constants.EXPRESS_SHIPPING), Constants.SHIPPING_COST_FREE);
	}

	public void verifyExpressDeliveryDisplaysInPaymentAndReviewPage() {
		Logger.verify("Verify Express Delivery displays in 'Payment & Review' page after adding SKU of \"Free Express Delivery\" tab in Last Minute Gift page");
		assertTrue(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), "Express Delivery"), " Express Delivery does not display in 'Payment & Review' page after adding SKU of \"Free Express Delivery\" tab in Last Minute Gift page");
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
		lastMinuteGiftPage.addFirstSKUToCartFromProgressTab(sku, DeliveryTab.FREE_EXPRESS_DELIVERY);
		generalPage.selectExclusiveOffer(false);
		generalPage.checkOut();
	}

	public void verifyPromotionMessageDisplaysWithSKUsUnderFreeExpressDeliveryTab() {
		Logger.verify("Verify \"Price Includes Express Shipping\" message displays with SKUs under \"Free Express Delivery\" tab");
		assertTrue(lastMinuteGiftPage.isPromotionMessageDisplayed(Constants.EXPRESS_DELIVERY_MESSAGE), "\"Price Includes Express Shipping\" message does not display with SKUs under \"Free Express Delivery\" tab");
	}

	public void verifyFirstItemDoesNotDisplayOutOfStock() {
		Logger.verify("Verify First Item of \"Free Express Delivery\" tabs in Last Minute Gift page does not displays Out Of Stock");
		assertFalse(lastMinuteGiftPage.isFirstSKUDisplayedOutOfStock(), "First Item of \"Free Express Delivery\" tab in Last Minute Gift page displays Out Of Stock");
	}

	public void clickFreeExpressDeliveryTab() {
		Logger.info("Click \"Free Express Delivery\" tab in Last-Minute Gift page");
		lastMinuteGiftPage.clickProgressTab(Constants.FREE_EXPRESS_DELIVERY);
	}

	public void goToLastMinuteGiftPage() {
		Logger.info("Go to Last-Minute Gift page in Homepage");
		generalPage.clickSideMenuItemLink(SideMenuItem.LAST_MINUTE_GIFT);
	}

	public void initTestCaseData() {
		Logger.tc("TC_LGP_001 Add SKU of \"Free Express Delivery\" tab in Last Minute Gift page and complete check out");
		Logger.to("TO_LGP_001 Express Delivery cost displays as FREE in 'Payment & Review' page after adding SKU of \"Free Express Delivery\" tab in Last Minute Gift page");
		Logger.to("TO_LGP_002 The Shipping Options displays Express Delivery in 'Payment & Review' page after adding SKU of \"Free Express Delivery\" tabs in Last Minute Gift page");
		Logger.to("TO_LGP_003 First Item of \"Free Express Delivery\" tab in Last Minute Gift page does not display Out Off Stock");
		Logger.to("TO_LGP_004 \"Price Includes Express Shipping\" message displays with SKUs under \"Free Express Delivery\" tab");
		sku.setRecipient(Recipient.MYSELF);
		myAddress = lstAddress.getDefaultShippingAddress();
	}

}
