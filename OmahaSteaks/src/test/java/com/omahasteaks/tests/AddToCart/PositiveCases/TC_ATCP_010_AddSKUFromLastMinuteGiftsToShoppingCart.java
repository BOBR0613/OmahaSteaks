package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient; 
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.LastMinuteGiftPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_010_AddSKUFromLastMinuteGiftsToShoppingCart extends TestBase_2SC {
	@Inject
	ListSKUs myCart;
	@Inject
	SKU mySku;
	@Inject
	LastMinuteGiftPage lastMinuteGiftPage;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_ATCP_010_Add_SKU_From_Free_Shipping_Packages_And_StockUp_To_ShoppingCart() {
		initTestCaseData();
		Logger.tc("TC_ATCP_010	Add First Item from Last-Minute Gifts To Shopping Cart");

		Logger.to("TO_ATCP_25	Add First Item from Last-Minute Gifts to Shopping Cart");
		Logger.step("Hover over \"Gifts\" menu and then click \"Last Minute Gifts\" link");
		generalPage.goToCategoryPage("Gifts/Last Minute Gifts");
		addFirstSKUToCart(mySku, myCart);
		checkout();
		verifyThatAddedItemsDisplayCorrectly(myCart);

	}

	private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
		lastMinuteGiftPage.addFirstItemToCart(sku,false);
		Logger.step("Add item \"" + sku.getName() + "\" to MyCart");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		generalPage.selectExclusiveOffer(false);
		generalPage.getAddedToCartSKUList(lSku);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void checkout() {
		Logger.step("Click \"Checkout\" or \"View My Cart\" button");	
		generalPage.checkOut();
	}

	private void initTestCaseData() {
		myCart.initEmpty();
		mySku.setRecipient(Recipient.MYSELF);
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
 		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " does not exist in "+ sku.getRecipient().getValue() + "'s cart.");
		}
	}
}
