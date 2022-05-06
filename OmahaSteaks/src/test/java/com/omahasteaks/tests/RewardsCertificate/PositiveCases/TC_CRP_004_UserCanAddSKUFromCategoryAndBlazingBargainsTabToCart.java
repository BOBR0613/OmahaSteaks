package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_004_UserCanAddSKUFromCategoryAndBlazingBargainsTabToCart extends TestBase_CR {

	String nameCategory;

	@Inject
	ListRewardSKUs lstSKU;
	
	@Inject
	RewardSKU mySKU;

	@Test
	public void TC_CRP_004_User_Can_Add_SKU_From_Category_And_Blazing_Bargains_Tab_To_Cart() {
		initTestCaseData();

		selectCategoryAndVerifyItIsDisplayedCorrectly(Constants.FIRST_CATEGORY);

		addSKUToCartAndVerifySKUDIsDisplayedCorrectly(mySKU);

		selectCategoryAndVerifyItIsDisplayedCorrectly(Constants.LAST_CATEGORY);

		addSKUToCartAndVerifySKUDIsDisplayedCorrectly(mySKU);

		selectCategoryAndVerifyItIsDisplayedCorrectly(Constants.RANDOMLY_CATEGORY);

		addSKUToCartAndVerifySKUDIsDisplayedCorrectly(mySKU);

		addSKUFromBlazingBargainsPageToCart();

		goToShoppingCartPage();

		verifySKUIsDisplayedCorrectlyInShoppingCartPage(mySKU);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSKUToCartAndVerifySKUDIsDisplayedCorrectly(RewardSKU sku) {
		addFirstSKUToCart(sku);
		goToShoppingCartPage();
		verifySKUIsDisplayedCorrectlyInShoppingCartPage(sku);
		deleteSKUFromCart(sku);
	}

	private void selectCategoryAndVerifyItIsDisplayedCorrectly(String orderCategory) {
		nameCategory = goToShoppingCategoryPage(orderCategory);
		verifyTheCategoryPageIsDisplayedCorrectly(nameCategory);
	}

	private void verifyTheCategoryPageIsDisplayedCorrectly(String nameCategory) {
		Logger.verify("Verify The category page " + nameCategory + " is displayed correctly");
		assertEquals(rewardGeneralPage.getCategoryName(), nameCategory, "The page " + nameCategory + " does not display");
	}

	private void addSKUFromBlazingBargainsPageToCart() {
		Logger.info("Click \"Blazing Bargains\" tab in top menu");
		Logger.info("Add the first SKU and ship to " + mySKU.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.addSKUFromBlazingBargainsPage(mySKU);
	}

	private void verifySKUIsDisplayedCorrectlyInShoppingCartPage(RewardSKU sku) {
		lstSKU = rewardShoppingCart.getListSKUInfoInShoppingCart();
		Logger.verify("Verify that the information of selected SKU which has ID = " + sku.getId() + " is displayed correctly in the Shopping cart page");
		assertTrue(lstSKU.contains(sku), "The SKU which has ID = " + sku.getId() + " does not display on the Shopping Cart page");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_004 - User can add SKU from catergory and \"Blazing Bargains\" tab to cart");
		Logger.to("TO_CRP_01 - User can add SKU  from the category to the cart");
		Logger.to("TO_CRP_02 - User can add SKU from \"Blazing Bargains\" tab to cart");
		Logger.to("TO_CRP_05 - The category page is displayed correctly when clicking on the category name in the left of page");
		mySKU.init(Recipient.MYSELF);
	}
}
