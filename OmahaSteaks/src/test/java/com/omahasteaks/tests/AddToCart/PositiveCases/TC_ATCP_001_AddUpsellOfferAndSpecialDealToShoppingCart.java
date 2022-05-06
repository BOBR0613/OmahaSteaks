package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient; 
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage; 
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common; 
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_001_AddUpsellOfferAndSpecialDealToShoppingCart extends TestBase_2SC {
	@Inject
	ListSKUs myCart;
	@Inject
	Item itemMyself;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;
	@Inject
	HomePage homePage;

	@Test
	public void TC_ATCP_001_Add_Upsell_Offer_And_Special_Deal_To_Shopping_Cart() {
		Logger.tc("TC_ATCP_001  Add Upsell Offer And Special Deal To Shopping Cart");
		initTestCaseData();

		Logger.to("TO_ATCP_22	Add SKU from Free Shipping Packages to Shopping Cart");
		Logger.to("TO_ATCP_26	Add SKU to Cart then go to shopping cart by clicking \"Check Out\"");
		Logger.to("TO_ATCP_30	Add \"Upsell offer\" and \"Special Deal\" in Added-To-Cart popup");
		goToFreeShippingPackagesPage();
		addFirstSKUToCart();
		addExclusiveOfferSKU();
		addSpecialDealUnlocked();
		getAddedSKUsDataAndClickCheckout();
		verifyThatAddedItemsDisplayCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addExclusiveOfferSKU() {
		Logger.step("Add Exclusive Offer SKU if it's displayed");
		productPage.selectExclusiveOffer(true);
		Common.waitForDOMChange();
	}

	private void addFirstSKUToCart() {
		Logger.step("Select the first item");
		Logger.substep("If having \"Select your Free Gift\" dropdown , select the first item");
		Logger.substep("Select \"Ship To Myself\"");
		Logger.substep("Click \"Add To Cart\" button");
		productPage.addFirstSKUToCart(itemMyself);
		Common.waitForDOMChange();
	}

	private void addSpecialDealUnlocked() {
		Logger.step("In \"Added To Cart\" popup:");
		Logger.substep("Add the Special Deal Unlocked item if it's displayed");
		generalPage.addSpecialDealUnlocked(true);
		Common.waitForDOMChange();
	}


	private void getAddedSKUsDataAndClickCheckout() {
		generalPage.getAddedToCartSKUList(myCart);
		Logger.substep("Go to Cart page by clicking on \"Checkout\" button"); 
		generalPage.checkOut();
		Common.waitForDOMChange();
	}

	private void goToFreeShippingPackagesPage() {
		Logger.step("Go to \"Free Shipping Packages\" page");
		Logger.substep("(D) Go to \"Free Shipping Packages\" page by clicking link"); 
		Logger.substep("(M) Click Menu and select \"Free Shipping Packages\" tab"); 
		homePage.selectFreeShipPackages();
		Common.waitForDOMChange();
		Common.modalDialog.closeSavorDialog();
	}

	private void initTestCaseData() {
		myCart.initEmpty();
		itemMyself.setRecipient(Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}

	private void verifyThatAddedItemsDisplayCorrectly() {
		Common.waitForDOMChange();
		for (SKU sku : myCart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " does not exist in " + sku.getRecipient().getValue()+"'s cart");
		}
	}
}
