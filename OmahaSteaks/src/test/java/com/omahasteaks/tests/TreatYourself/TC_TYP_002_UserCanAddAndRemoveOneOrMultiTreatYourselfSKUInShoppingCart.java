package com.omahasteaks.tests.TreatYourself;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_TYP_002_UserCanAddAndRemoveOneOrMultiTreatYourselfSKUInShoppingCart extends TestBase_2SC {

    @Inject
    Item recipientItem, treatYourselfItem;

    @Inject
    GeneralPage generalPage;

    @Inject
    ProductPage productPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_TYP_002_Customer_Can_Add_And_Remove_One_Or_Multi_Treat_Yourself_SKU_To_Shopping_Cart() {

	initTestCaseData();

	searchAndAddItem(recipientItem);

	verifyTreatYourselfModuleDisplays();

	addTreatYourselfSKUToCart();

	verifyMyselfCartSectionIsAddedAtTheTopOfShoppingCart();

	verifyOneSKUAddedToMyselfCartSection();

	//addMultipleTreatYourselfSKUsToMyselfCart();
	//verifyMultipleSKUsAddedToMyselfCart();

	removeTreatYourselfSKUFromMyselfCart();

	verifyMyselfCartSectionIsDisappeared();

	verifyTreatYourselfModuleDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyMyselfCartSectionIsDisappeared() {
	Logger.verify("In Shopping Cart page:" + "Verify Myself Cart section is disappeared.");
	assertFalse(shoppingCartPage.isCartShipmentDisplayed(Recipient.MYSELF), "Myself Cart section is appeared.");
    }

    public void verifyTreatYourselfModuleDisplays() {
	Logger.verify("In Shopping Cart page:" + "Verify Treat Yourself module displays");
	assertTrue(shoppingCartPage.isTreatYourselfModuleDisplayed(), "Treat Yourself Module does not display");
    }

    public void removeTreatYourselfSKUFromMyselfCart() {
	Logger.info("In Myself Cart section:" + " - Remove all SKUs from Myself cart section");
	Common.waitForPageLoad();
	shoppingCartPage.removeSkuById(treatYourselfItem, true);
    }

    public void verifyMultipleSKUsAddedToMyselfCart() {
	Logger.verify("In Myself Cart section:" + " - Multiple SKUs added to Myself Cart section");
	assertEquals(shoppingCartPage.getQuantityOfSku(treatYourselfItem), Constants.QUANTITY_MULTIPLE, "One SKU does not add to Myself Cart section");
    }

    public void addMultipleTreatYourselfSKUsToMyselfCart() {
	Logger.info("In Myself Cart section:" + " - Add multiple SKUs");
	shoppingCartPage.updateQuantityOfSku(treatYourselfItem, Constants.QUANTITY_MULTIPLE);
    }

    public void verifyOneSKUAddedToMyselfCartSection() {
    Common.waitForPageLoad();
	Logger.verify("In Myself Cart section:" + " - One Sku added to Myself Cart section");
	assertEquals(shoppingCartPage.getQuantityOfSku(treatYourselfItem), Constants.QUANTITY_DEFAULT, "One SKU does not add to Myself Cart section");

    }

    public void verifyMyselfCartSectionIsAddedAtTheTopOfShoppingCart() {
    Common.waitForPageLoad();
	Logger.verify("In Shopping Cart page:" + "Verify Myself Cart section is added at the top of Shopping Cart");
	assertTrue(shoppingCartPage.isMyselfCartSectionAddedAtTheTopOfShoppingCart(), "Myself Cart section is not added at the top of Shopping Cart");
    }

    public void addTreatYourselfSKUToCart() {
	Logger.info("In Treat Yourself module:" + "- Click \"Add to cart\" button");
	treatYourselfItem.setId(shoppingCartPage.getSkuID_TreatYourself());
	treatYourselfItem.setRecipient(Recipient.MYSELF);
	shoppingCartPage.addTreatYourself();
	shoppingCartPage.selectExclusiveOffer(false);
	generalPage.checkOut();
    }

    public void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id (randomly)" + "In Product Page:" + " - Select: \"Ship To \"" + sku.getRecipient() + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    public void initTestCaseData() {
	Logger.tc("TC_TYP_002 - Customer can add and remove one or multi Treat Yourself SKU toShopping Cart");
	Logger.to("TO_TYP_05 - Customer can add one SKU to their Shopping Cart, “Myself�?");
	Logger.to("TO_TYP_06 - Customer can add multiple SKUs to their Shopping Cart, “Myself�?");
	Logger.to("TO_TYP_07 - Myself Cart section is added at the top of Shopping Cart");
	Logger.to("TO_TYP_09 - When customer removes all SKUs from their Shopping Cart (Myself), My Cart section is disappeared.");
	recipientItem.initRandom(Recipient.THONG_NGUYEN);
    }
}
