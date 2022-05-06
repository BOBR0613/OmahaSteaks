package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_015_ThereIsNoSKUOutOfStock extends TestBase_CR {
	String[] lstCategoriesName;

	int numberOfSkuOutOfStock = 0;

	@Inject
	RewardSKU sku;

	@Test
	public void TC_CRP_015_There_Is_No_SKU_Out_Of_Stock() {
		initTestCaseData();

		for (String name : lstCategoriesName) {
			goToCategoryPage(name);
			addTheFirstSKUToCart();
			verifyOutOfStockMessageDoesNotDisplay();
		}

		Logger.info("There are " + numberOfSkuOutOfStock + " sku out of stock");
		assertEquals(numberOfSkuOutOfStock, 0, "There are " + numberOfSkuOutOfStock + " sku out of stock");
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyOutOfStockMessageDoesNotDisplay() {
		Logger.verify("Verify that the out of stock message does not display");
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			numberOfSkuOutOfStock++;
			Logger.warning("\"" + sku.getName() + "\"" + " is out of stock.");
		} else
			assertFalse(rewardGeneralPage.isItemOutOfStockMsgDisplayed(), "\"" + sku.getName() + "\"" + " is out of stock.");
	}

	private void addTheFirstSKUToCart() {
		Logger.info("Add the first SKU to cart");
		rewardGeneralPage.selectFirstItem();
		rewardGeneralPage.addSKUToCart(sku);
	}

	private void goToCategoryPage(String nameCategory) {
		Logger.info("Go to category: " + nameCategory);
		rewardGeneralPage.selectCategory(nameCategory);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_015 - There is no SKU Out Of Stock");
		Logger.to("TO_CRP_062 - There is no SKU out of stock");
		sku.init(Recipient.MYSELF);
		lstCategoriesName = rewardGeneralPage.getListShoppingCategories();
	}

}
