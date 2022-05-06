package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.Category;
import com.omahasteaks.data.ListCategories;
import com.omahasteaks.data.ListDepartments;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_009_AddSKUFromDepartmentAndCategoryPages extends TestBase_2SC {
	@Inject
	ListCategories lstCategories;
	@Inject
	ListDepartments lstDepartments;
	@Inject
	Category category;
	@Inject
	ListSKUs myCart;
	@Inject
	ListSKUs KimAnhCart;
	@Inject
	SKU mySku;
	@Inject
	SKU KimAnhSku;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;

	@DataProvider(name = "departmentName")
	public String[] dataProvider() {	
		return lstDepartments.getArray();
	}

	@Test(dataProvider = "departmentName")
	public void TC_ATCP_009_Add_SKU_From_Department_And_Category_Pages(String departmentName) {
		Logger.tc("TC_ATCP_009 	Add Item From " + departmentName + " Department And Category Page");
		initTestCaseData(departmentName);

		Logger.to("TO_ATCP Add item from " + departmentName + " Department Page to MyCart");
		goToDepartmentPage(departmentName);
		addFirstSKUToCart(mySku, myCart);
		goToShoppingCartPage();
		verifyThatAddedItemsDisplayCorrectly(myCart);

		Logger.to("TO_ATCP Add SKU from Category under " + departmentName + " Department Page to Kim Ahn's Cart");		
		goToCategoryPage();
		addFirstSKUToCart(KimAnhSku, KimAnhCart);
		goToShoppingCartPage();
		verifyThatAddedItemsDisplayCorrectly(KimAnhCart);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
		Common.modalDialog.closeSavorDialog();
		Logger.step("Add item \"" + generalPage.getFirstItemName() + "\" to cart and ship to " + sku.getRecipient().getValue());
		generalPage.addFirstSKUToCart(sku);
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		generalPage.selectExclusiveOffer(false);
		generalPage.getAddedToCartSKUList(lSku);
	}

	private void goToCategoryPage() {
		Logger.step("Go to \"" + category.getCategoryPath()+"\"");
		generalPage.goToCategoryPageByLeftMenu(category.getCategoryPath());
	}

	private void goToShoppingCartPage() {
		Logger.substep("Click \"Checkout\" or \"View My Cart\" button");
		generalPage.checkOut();
	}

	private void goToDepartmentPage(String departmentName) {
		Logger.step("Go to " + departmentName + " Department page");
		generalPage.goToDepartmentPage(category.getCategoryPath());
	}

	private void initTestCaseData(String departmentName) {
		myCart.initEmpty();
		KimAnhCart.initEmpty();
		mySku.setRecipient(Recipient.MYSELF);
		KimAnhSku.setRecipient(Recipient.KIM_ANH);	
		category = lstCategories.getRandomCategoryByDeparment(departmentName);
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
 		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " does not exist in "+ sku.getRecipient().getValue() + "'s cart.");
		}
	}
}
