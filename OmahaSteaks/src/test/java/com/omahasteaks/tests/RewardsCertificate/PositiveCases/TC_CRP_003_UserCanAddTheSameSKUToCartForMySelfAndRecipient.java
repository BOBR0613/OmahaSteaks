package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_003_UserCanAddTheSameSKUToCartForMySelfAndRecipient extends TestBase_CR {
	@Inject
	ListRewardSKUs lstSKU;
	
	CustomerAddress myAddress;

	@Inject
	RewardSKU mySKU, recipientSKU;
	
	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRP_003_User_Can_Add_The_Same_SKU_For_Myself_And_Recipient() {
		initTestCaseData();

		addTheSameSKUForMyselfAndSomeoneElse();

		goToShoppingCartPage();

		verifySKUDisplaysCorrectlyInShoppingCartPage(mySKU);

		verifySKUDisplaysCorrectlyInShoppingCartPage(recipientSKU);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addTheSameSKUForMyselfAndSomeoneElse() {
		addSKUFromCategory(mySKU, Constants.RANDOMLY_CATEGORY);
		addSKUToCart(recipientSKU);
	}
	
	private void verifySKUDisplaysCorrectlyInShoppingCartPage(RewardSKU sku) {
		lstSKU = rewardShoppingCart.getListSKUInfoInShoppingCart();
		Logger.verify("Verify that the information of selected SKU displays correctly in the Shopping cart page");
		assertTrue(lstSKU.contains(sku), "The SKU which has ID = "+ sku.getId() + " does not display on the Shopping Cart page");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_003 - User can add the same SKU for Myself and Recipient");
		Logger.to("TO_CRP_003 - User can add the same SKU for Myself and Someone else to cart");

		mySKU.init(Recipient.MYSELF);
		recipientSKU.init(Recipient.NEW_RECIPIENT);
	}
}
