package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.Category;
import com.omahasteaks.data.ListCategories;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_012_AddSKUFromWineDepartmentPage extends TestBase_2SC {
	String winePageTitle;
	@Inject
	ListCategories lstCategories;
	@Inject
	Category category;
	@Inject
	ListSKUs KimAnhCart;
	@Inject
	SKU KimAnhSku;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	CategoryPage categoryPage;

	@Test
	public void TC_ATCP_012_Add_SKU_From_Wine_Department_Page() {
		Logger.tc("TC_ATCP_012 	Add SKU From Wine Department Page\n");
		initTestCaseData();

		Logger.to("TO_ATCP_15	Add First Item from \"Wine\" Department Page to a recipients cart");
		goToWineDepartmentPage();
		addFirstSKUToCart(KimAnhSku, KimAnhCart);
		continueShopping();
		verifyThatUserStillStaysAtWinePage();
		goToShoppingCartPage();

		verifyThatAddedItemsDisplayCorrectly(KimAnhCart);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
		Logger.step("Add the first SKU and ship to Myself");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"Continue Shopping\"");
		generalPage.addFirstSKUToCart(sku);
		generalPage.selectExclusiveOffer(false);
		generalPage.getAddedToCartSKUList(lSku);
	}

	private void continueShopping() {
		Logger.step("Click \"Continue Shopping\" button");
		generalPage.continueShopping();
	}

	private void goToShoppingCartPage() {
		Logger.step("Go to Shopping Cart page by clicking the \"Cart\" icon");
		generalPage.goToMyCart();
	}

	private void goToWineDepartmentPage() {
		Logger.step("Go to Wine Department page by clicking the \"Wine\" menu link");
		generalPage.goToDepartmentPage(category.getCategoryPath());
		winePageTitle = Common.getTitlePage();
	}

	private void initTestCaseData() {
		KimAnhCart.initEmpty();
		KimAnhSku.setRecipient(Recipient.KIM_ANH);
		category = lstCategories.getRandomCategoryByDeparment("Wine");
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
 		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " does not exist in "+ sku.getRecipient().getValue() + "'s cart.");
		}
	}

	private void verifyThatUserStillStaysAtWinePage() {
		Logger.verify("Verify that user still stays at \"Wine\" department page with it's title: " + winePageTitle);
		assertEquals(Common.getTitlePage(), winePageTitle);
	}
	
	
}
