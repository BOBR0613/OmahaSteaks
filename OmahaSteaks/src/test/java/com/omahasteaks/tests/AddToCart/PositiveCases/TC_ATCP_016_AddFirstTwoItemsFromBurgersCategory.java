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
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_016_AddFirstTwoItemsFromBurgersCategory extends TestBase_2SC {
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
	initTestCaseData(departmentName);

	goToDepartmentPage(departmentName);

	addFirstSKUToCart(mySku, myCart);

	continueShoppingAndGoToCategoryPage();

	addFirstSKUToCart(KimAnhSku, KimAnhCart);

	goToShoppingCartPage();

	verifyThatAddedItemsDisplayCorrectly(myCart);

	verifyThatAddedItemsDisplayCorrectly(KimAnhCart);
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
	Logger.info("Add the first SKU and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Continue Shopping\"");
	generalPage.addFirstSKUToCart(sku);
	generalPage.selectExclusiveOffer(false);
	generalPage.getAddedToCartSKUList(lSku);
    }

    private void continueShoppingAndGoToCategoryPage() {
	Logger.info("Continue Shopping");
	generalPage.continueShopping();
	generalPage.goToCategoryPageByLeftMenu(category.getCategoryPath());
    }

    private void goToShoppingCartPage() {
	generalPage.checkOut();
    }

    private void goToDepartmentPage(String departmentName) {
	Logger.info("Go to" + departmentName + "Department page");
	generalPage.goToDepartmentPage(category.getCategoryPath());
    }

    private void initTestCaseData(String departmentName) {
	Logger.tc("TC_ATCP_009 	Add SKU From " + departmentName + " Department And Category Page\n");
	Logger.to("TO_ATCP Add SKU from " + departmentName + " Department  Page to Cart \n");
	Logger.to("TO_ATCP Add SKU from Category under " + departmentName + " Department  Page to Cart \n");
	myCart.initEmpty();
	KimAnhCart.initEmpty();
	mySku.setRecipient(Recipient.MYSELF);
	KimAnhSku.setRecipient(Recipient.KIM_ANH);	
	category = lstCategories.getRandomCategoryByDeparment(departmentName);
    }

    private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
	Logger.info("Verify that these added items with correct information exist in Shopping Cart Page");
	for (SKU sku : cart.getList()) {
	    Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
	    assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
	}
    }
}
