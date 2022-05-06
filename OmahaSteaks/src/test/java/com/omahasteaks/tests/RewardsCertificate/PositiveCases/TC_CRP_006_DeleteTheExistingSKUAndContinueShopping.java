package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_006_DeleteTheExistingSKUAndContinueShopping extends TestBase_CR {
	@Inject
	RewardSKU mySKU;
	
	@Test
	public void TC_CRP_006_Delete_The_Existing_SKU_And_Continue_Shopping() {
		initTestCaseData();

		addRandomlySkuFromCategoryAndGoToShoppingCartPage();
		
		Logger.step("Delete item \"" + mySKU.getId() + "\" from the cart.");
		deleteSKUFromCart(mySKU);

		verifyTheEmptyMessageIsDisplayedCorrectlyInTheShoppingCartPage();

		continueShopping();

		verifyTheCategoryPageDisplaysCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addRandomlySkuFromCategoryAndGoToShoppingCartPage() {
		Logger.step("Add an item from \"Burgers & Specialty Items\" menu to the cart.");
		addSKUFromCategory(mySKU, "Burgers & Specialty Items");
		Logger.step("Go to shopping cart page.");
		goToShoppingCartPage();
	}
	
	private void verifyTheCategoryPageDisplaysCorrectly() {
		Logger.verify("Verify that the first category page is displayed after clicking on the \"Resume shopping\" link");
		assertEquals(rewardGeneralPage.getCategoryName(), rewardGeneralPage.getCategoryNameFromCategoriesSection(Constants.FIRST_CATEGORY), "the first category page " + rewardGeneralPage.getCategoryNameFromCategoriesSection(Constants.FIRST_CATEGORY) + " is displayed after clicking on the \"Resume shopping\" link");

	}

	private void continueShopping() {
		Logger.step("Continue shopping by clicking on the \"Resume Shopping\" link");
		rewardShoppingCart.continueShopping();
	}

	private void verifyTheEmptyMessageIsDisplayedCorrectlyInTheShoppingCartPage() {
		Logger.verify("Verify that the empty message is displayed when user delete all items in the cart");
		assertTrue(rewardShoppingCart.isEmptyCartMessageDisplayedCorrectly(), "The " + Messages.YOUR_CART_EMPTY_MESSAGE + " message does not display");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_006 - User can edit the existing SKU and continue shopping");
		Logger.to("TO_CRP_016 - In Shopping Cart, user can delete existing SKU");
		Logger.to("TO_CRP_020 - In the shopping cart page, the \"Your cart is empty!\" message is displayed when deleting all existing SKUs on the cart");
		Logger.to("TO_CRP_018 - In the shopping cart, user can continue shopping when clicking on the \"Resume Shopping\" link");
		mySKU.init(Recipient.MYSELF);
	}

}
