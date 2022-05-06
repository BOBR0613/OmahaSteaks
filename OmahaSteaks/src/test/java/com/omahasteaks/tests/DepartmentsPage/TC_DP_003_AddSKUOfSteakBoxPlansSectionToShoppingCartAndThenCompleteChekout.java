package com.omahasteaks.tests.DepartmentsPage;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.BoxPlans;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.DepartmentsPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_DP_003_AddSKUOfSteakBoxPlansSectionToShoppingCartAndThenCompleteChekout extends TestBase_2SC {
    CustomerAddress myAddress;

    @Inject
    ListAddresses lstAddress;
    @Inject
    ListSKUs myCart;
    @Inject
    SKU sku;
    @Inject
    GeneralPage generalPage;
    @Inject
    CategoryPage categoryPage;
    @Inject
    DepartmentsPage departmentPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Test
    public void TC_DP_003_Add_SKU_Of_Steak_Box_Plans_Section_To_Shopping_Cart_And_Then_Complete_Chekout() {
	initTestCaseData();

	goToGiftsDepartmentPage();

	searchWithZipCode();

	verifyTheEstArrivalDateInShippingMethodDisplaysInWhenWillItArrivePopup();

	goToBoxPlansPage();

	addFirstSKUOfSteakBoxPlansToCart();

	verifyThatAddedItemsDisplayCorrectly();

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	fillBillingAddress();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyTheEstArrivalDateInShippingMethodDisplaysInWhenWillItArrivePopup() {
	for (String shippingMethod : Constants.LIST_SHIPPING_METHOD) {
	    Logger.verify("Verify The Est. Arrival date in " + shippingMethod + " is displayed in \"When will it arrive?\" popup");
	    assertTrue(departmentPage.isEstArrivalDateByShippingMethodDisplayed(shippingMethod), "The Est. Arrival date in " + shippingMethod + " does not display in \"When will it arrive?\" popup");
	}
    }

    public void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
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

    public void fillShippingAddressAndClickContinue() {
	Logger.info("Fill all valid informations in Shipping Address page" + "Click Continue");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    public void checkOutFromShoppingCart() {
	Logger.info("In Shopping Cart page, click \"Check out\" button");
	shoppingCartPage.checkOut();
    }

    public void verifyThatAddedItemsDisplayCorrectly() {
	Logger.verify("Verify that these added items with correct information exist in Shopping Cart Page");
	for (SKU sku : myCart.getList()) {
	    Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
	    assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
	}
    }

    public void addFirstSKUOfSteakBoxPlansToCart() {
	Logger.info("Add the first SKU in Steak Box Plans and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Continue Shopping\"");
	departmentPage.addFirstSKUToCartByBoxPlans(sku, BoxPlans.GIFT_ALL_AT_ONCE);
	generalPage.selectExclusiveOffer(false);
	generalPage.getAddedToCartSKUList(myCart);
	generalPage.checkOut();
    }

    public void goToBoxPlansPage() {
	Logger.info("Go to Box Plans page");
	generalPage.closeModal();
	generalPage.goToCategoryPageByLeftMenu("Gifts/Gifting Options/Gift Plans");
    }

    public void searchWithZipCode() {
	String zipCode = "12345";
	Logger.info("Search with a ZipCode : " + zipCode);
	categoryPage.searchWithZipCode(zipCode);
    }

    public void goToGiftsDepartmentPage() {
	Logger.info("Go to Gifts Department page");
	generalPage.goToDepartmentPage("Gifts");
    }

    public void initTestCaseData() {
	Logger.tc("TC_DP_003 Add Sku of Steak Box Plans section to shopping cart and then complete checkout");
	Logger.to("TO_DP_05 The Est. Arrival date is displayed in \"When will it arrive?\" popup");
	Logger.to("TO_DP_06 Add Sku of Steak Box Plans section to shopping cart and then complele checkout");
	myCart.initEmpty();
	sku.setRecipient(Recipient.MYSELF);
	myAddress = lstAddress.getDefaultShippingAddress();
    }
}
