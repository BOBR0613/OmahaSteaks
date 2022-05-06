package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_011_UserCanGoToShoppingCartPageWhenClickingCartIcon extends TestBase_CR {
	@Inject
	ListRewardSKUs lstSKU;

	@Inject
	RewardSKU mySKU1, mySKU;

	@Test
	public void TC_CRP_011_User_Can_Go_To_Shopping_Cart_Page_When_Clicking_Cart_Icon() {
		initTestCaseData();

		searchSKUWithPoint("12");
		
		rewardGeneralPage.addFirstAvailableItem(mySKU);
		
 		verifyTheInfoOfSKUOnShoppingCarttableDisplaysCorrectly(mySKU);

		addSKUFromCategory(mySKU1, "Poultry Selections");

		verifySubtotalIsDisplayedCorrectly();

		clickCartIconInShoppingCarttable();

		verifySKUDisplaysCorrectlyInShoppingCartPage(mySKU);

		verifySKUDisplaysCorrectlyInShoppingCartPage(mySKU1);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifySKUDisplaysCorrectlyInShoppingCartPage(RewardSKU sku) {
		lstSKU = rewardShoppingCart.getListSKUInfoInShoppingCart();
		Logger.verify("Verify that the information of selected SKU displays correctly in the Shopping cart page");
		assertTrue(lstSKU.contains(sku), "The SKU which has ID = " + sku.getId() + " does not display on the Shopping Cart page");
	}

	private void verifyTheInfoOfSKUOnShoppingCarttableDisplaysCorrectly(RewardSKU sku) {
		Logger.verify("Verify the information of SKU on the Shopping Cart table displays correctly");
		assertTrue(rewardGeneralPage.isSKUDisplayedInShoppingCartPage(sku), "the information of SKU on the Shopping Cart table does not display");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_011 - User can go to Shopping Cart page when clicking cart icon");
		Logger.to("TO_CRP_007 - The information on the Shopping Cart table displays correctly when adding SKU to recipient's cart ");
		Logger.to("TO_CRP_012 - User can go to the Shopping Cart page when clicking on the cart icon link on the 'Shopping Cart' table");
		Logger.to("TO_CRP_015 - In the shopping cart table, the 'subtotal' displays correctly when adding multiple different SKUs");
		mySKU.init(Recipient.MYSELF);
		mySKU1.init(Recipient.MYSELF);
	}

	private void clickCartIconInShoppingCarttable() {
		Logger.info("In Shopping Cart table, click Cart icon");
		rewardGeneralPage.goToShoppingCartPage(false);
	}

	private void verifySubtotalIsDisplayedCorrectly() {
		Logger.verify("Verify the subtotal is displayed correctly");
		String total = String.valueOf(mySKU.getPoint() + mySKU1.getPoint());
		assertEquals(rewardGeneralPage.getSubTotal(), total, "The subtotal does not display correctly");
	}
}
