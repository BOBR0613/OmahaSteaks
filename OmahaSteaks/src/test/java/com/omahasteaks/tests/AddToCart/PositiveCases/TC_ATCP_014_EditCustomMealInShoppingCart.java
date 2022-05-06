package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListCategories;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.objects.Meal;
import com.omahasteaks.page.CustomPackagePage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_014_EditCustomMealInShoppingCart extends TestBase_2SC {

	@Inject
	ListCategories lstCategories;
	@Inject
	ListSKUs myCart;
	@Inject
	Meal customMeal;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	CustomPackagePage CustomPackagePage;

	@Test
	public void TC_ATCP_014_Edit_Custom_Meal_In_Shopping_Cart() {
		Logger.tc("TC_ATCP_014 - Edit Custom Meal In Shopping Cart\n");
		initTestCaseData();

		Logger.to("TO_ATCP_48 - In Shopping Cart page - Editing Meals items\n");
		searchAndCreateCustomMeal();
		verifyThatCustomMealDisplayWithCorrectSelectedItems();
		selectEditSku();
		editCustomMeal();
		verifyThatCustomMealDisplayWithCorrectSelectedItems();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void searchAndCreateCustomMeal() {
		String keyword = Common.getNumberFromText(customMeal.getId());
		Logger.step("Search a Custom Meal with id (e.g. " + keyword + ")");
		generalPage.search(keyword); 
		Logger.substep("Select first item in each group");
		Logger.substep("Select \"Ship To Someone Else\" and then enter the Recipient's name (e.g. Thong Nguyen)");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		CustomPackagePage.createCustomPackageAndAddToCart(customMeal, false);
		generalPage.getAddedToCartSKUList(myCart);
		Logger.substep("Click \"Checkout\" or \"View My Cart\" button to go to the Shopping Cart Page");
		CustomPackagePage.checkOut();
	}

	private void initTestCaseData() {
		myCart.initEmpty();
		customMeal.initRandom(SkuStatus.CUSTOM, Recipient.THONG_NGUYEN);
	}

	private void selectEditSku() {
		Logger.step("In Shopping Cart page: Click \"Edit\" link to edit the custom Meal");
		shoppingCartPage.selectEditSku(customMeal);
	}

	private void editCustomMeal() {
		Logger.step("Edit items in the custom meal");
		Logger.substep("Click the Remove link");
		Logger.substep("Select second item in the group");
		Logger.substep("Click \"Save Edits\" button");
		CustomPackagePage.editCustomPackageAndSaveEdits(customMeal);
	}

	private void verifyThatCustomMealDisplayWithCorrectSelectedItems() {
		Logger.verify("Verify that custom Meal displays in Shopping Cart (My Cart) with correct selected items");
		assertTrue(shoppingCartPage.isSkuByIdAdded(customMeal.getRecipient(), customMeal.getId()), "The custom Meal is not displayed as expected");
		if (DriverUtils.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			assertTrue(shoppingCartPage.isSubItemsDisplayed(customMeal), "The sub Item in custom Meal is not displayed as expected");
	} 
	
	
}
