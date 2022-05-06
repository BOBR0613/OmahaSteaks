package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_010_TheInfoOfShoppingCartTableDisplaysCorrectlyWhenAddingTheSameSKUForMyself extends TestBase_CR {
	@Inject
	ListRewardSKUs lstSKU;

	@Inject
	RewardSKU mySKU;

	int expectedQty = 0;

	@Test
	public void TC_CRP_010_The_Info_Of_Shopping_Cart_Table_Displays_Correctly_When_Adding_The_Same_SKU_For_Myself() {
		initTestCaseData();

		verifyYourCartIsEmptyMsgIsDisplayedInShoppingCartTable();

		verifyCheckOutLinkDoesNotDisplayInShoppingCartTable();

		verifyCartIconDoesNotDisplayInShoppingCartTable();

		addTheSameSKUForMyself();

		verifyTheInfoOfSKUOnShoppingCartTableDisplaysCorrectly(mySKU);

		goToShoppingCartPage();

		verifySKUDisplaysCorrectlyInShoppingCartPage(mySKU);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addTheSameSKUForMyself() {
		addSKUFromCategory(mySKU, "Roasts");
		expectedQty = mySKU.getQuantity();
		addSKUToCart(mySKU);
		expectedQty = expectedQty + mySKU.getQuantity();
		mySKU.setQuantity(expectedQty);
	}

	private void verifyTheInfoOfSKUOnShoppingCartTableDisplaysCorrectly(RewardSKU sku) {
		Logger.verify("Verify the information of SKU on the Shopping Cart table displays correctly");
		assertTrue(rewardGeneralPage.isSKUDisplayedInShoppingCartPage(sku), "the information of SKU on the Shopping Cart table does not display");
	}

	private void verifyCartIconDoesNotDisplayInShoppingCartTable() {
		Logger.verify("Verify \"Cart\" icon does not display in the 'Shopping cart' table");
		assertFalse(rewardGeneralPage.isCartIconInShoppingCartSectionDisplayed(), "\"Cart\" icon displays in the 'Shopping cart' table");
	}

	private void verifyCheckOutLinkDoesNotDisplayInShoppingCartTable() {
		Logger.verify("Verify \"Checkout\" link does not display in the 'Shopping cart' table");
		assertFalse(rewardGeneralPage.isCheckOutLnkInShoppingCartSectionDisplayed(), "\"Checkout\" link displays in the 'Shopping cart' table");
	}

	private void verifyYourCartIsEmptyMsgIsDisplayedInShoppingCartTable() {
		Logger.verify("Verify \"Your cart is empty.\" message is displayed in the Shopping cart table");
		assertTrue(rewardGeneralPage.isYourCartEmptyInShoppingCartSectionDisplayed(), "\"Your cart is empty.\" message does not display in the Shopping cart table");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_010 The info of Shopping Cart table displays correctly when adding the same SKU for Myself");
		Logger.to("TO_CRP_006 - The information on the Shopping Cart table displays correctly when adding SKU to my cart ");
		Logger.to("TO_CRP_009 - The quantity of SKU is displayed correctly when adding the same SKU for Myself");
		Logger.to("TO_CRP_010 - \"Your cart is empty.\" message is displayed in the Shopping cart table when the cart is empty");
		Logger.to("TO_CRP_011 - User can go to the Shopping Cart page when clicking on the \"CHECKOUT\" link on the 'Shopping Cart' table");
		Logger.to("TO_CRP_013 - There is no \"Checkout\" link in the 'Shopping cart' table when the cart is empty");
		Logger.to("TO_CRP_014 - There is no \"Cart\" icon in the 'Shopping cart' table when the cart is empty");
		mySKU.init(Recipient.MYSELF);
	}

	private void verifySKUDisplaysCorrectlyInShoppingCartPage(RewardSKU sku) {
		lstSKU = rewardShoppingCart.getListSKUInfoInShoppingCart();
		Logger.verify("Verify that the information of selected SKU displays correctly in the Shopping cart page");
		assertTrue(lstSKU.contains(sku), "The SKU which has ID = " + sku.getId() + " does not display on the Shopping Cart page");
	}
}
