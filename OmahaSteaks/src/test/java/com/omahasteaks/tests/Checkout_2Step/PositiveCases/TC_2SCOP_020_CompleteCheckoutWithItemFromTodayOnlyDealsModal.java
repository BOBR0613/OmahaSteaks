package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.PromotionModal;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_020_CompleteCheckoutWithItemFromTodayOnlyDealsModal extends TestBase_2SC {
    CustomerAddress billingAddress, shippingAddress;
    @Inject
    ListSKUs myCart;
    @Inject
    SKU sku;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    GeneralPage generalPage;
    @Inject
    PromotionModal promotionModal;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Test
    public void TC_2SCOP_020_Complete_Checkout_With_Item_From_TodayOnlyDeals_Modal() {
	initTestCaseData();

	selectGetTheDeals();

	verifyTodayOnlyDealsModalDisplaysWithThreeItems();

	verifyThatSubItemsAreRotated();

	addFirstSKUInTodayOnlyDeal();

	verifyThatAddedItemsDisplayCorrectlyInMyCart();

	checkoutFromShoppingCart();

	fillShippingAddress();

	fillBillingAddressAndCreditCardNumberThenPlaceThisOrder();

	verifyThatAddedItemsDisplayCorrectlyInOrderReceiptPage();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void checkoutFromShoppingCart() {
	Logger.info("In Shopping Cart page:\n" + "- Click \"Check out\"");
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddressAndCreditCardNumberThenPlaceThisOrder() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Click \"Add New Address\" link\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void selectGetTheDeals() {
	Logger.info("In Just For You modal\n" + " - Fill valid email into text box\n" + " - Check \"I Agree\" check box\n" + " - Click \"Get The Deals\" button\n");
	promotionModal.selectGetTheDealsButtonWithRandomEmail();
    }

    private void addFirstSKUInTodayOnlyDeal() {
	Logger.info("In Today-Only Deals modal:\n" + "At the first SKU:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
	promotionModal.addFirstSkuOnTodayOnlyDealsModal(sku);
	generalPage.selectExclusiveOffer(true);
	generalPage.getAddedToCartSKUList(myCart);
	generalPage.checkOut();
    }

    private void verifyTodayOnlyDealsModalDisplaysWithThreeItems() {
	Logger.verify("Verify Today-Only Deals modal pops up with three items.");
	assertTrue(promotionModal.isTodayOnlyDealsModalDisplayed(), "Today-Only Deals modal pops up does not displays with three items.");
    }

    private void verifyThatSubItemsAreRotated() {
	if (!Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE)) {
	    Logger.verify("Verify that subitems are rotated when clicking rotator in each item");
	    assertTrue(promotionModal.areSubItemsRotated(), "Subitems are not rotated when clicking rotator in each item");
	}
    }

    private void verifyThatAddedItemsDisplayCorrectlyInMyCart() {
	Logger.info("Verify that the added SKU displays in Shopping Cart");
	for (SKU sku : myCart.getList()) {
	    Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
	    assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
	}
    }

    private void verifyThatAddedItemsDisplayCorrectlyInOrderReceiptPage() {
	Logger.info("In Order Receipt Page\n" + "- Verify added items appear in My Cart section");
	// Common.modalDialog.closeModalDialog();
	confirmationPage2SC.closeModal();
	for (SKU sku : myCart.getList()) {
	    Logger.verify("Verify " + sku.getName() + " displays correctly in " + confirmationPage2SC.getShippingSectionName(shippingAddress) + " section.");
	    assertTrue(confirmationPage2SC.isSKUDisplayed(shippingAddress, sku));
	}
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_020 - Complete Checkout With Item From \"Today Only Deals\" Modal");
	Logger.to("TO_2SCOP_051 - Just For You Modal - Add an item from \"Today-Only Deals\" modal after clicking \"GET THE DEALS\" button in \"Just For You\" Modal");
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    DriverUtils.getWebDriver().manage().deleteAllCookies();
    if (DriverUtils.getDriverType().equalsIgnoreCase("Chrome")) {
      DriverUtils.getWebDriver().get("chrome://settings/clearBrowserData");
      DriverUtils.getWebDriver().findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
    }
    DriverUtils.getWebDriver().get(Constants.OMAHA_URL);
	
	myCart.initEmpty();
	sku.setRecipient(Recipient.MYSELF);
    }
}
