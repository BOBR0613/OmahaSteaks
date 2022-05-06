package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_012_ShoppingCartTable_VerifyTheInfoOfAddedSKUsOnMyCartAndRecipientCart extends TestBase_CR {
	@Inject
	ListRewardSKUs lstSKU;

	@Inject
	RewardSKU mySKU, recipientSKU;

	@Test
	public void TC_CRP_012_ShoppingCartTable_Verify_The_Info_Of_Added_SKUs_On_My_Cart_And_RecipientCart() {
		initTestCaseData();

		addSKUFromCategory(mySKU, "Poultry Entrees");

		verifyTheInfoOfSKUOnShoppingCartTable(mySKU);

		addSKUFromCategory(recipientSKU, "Roasts");

		verifyTheInfoOfSKUOnShoppingCartTable(recipientSKU);

		verifyTheInfoOfSKUOnShoppingCartTable(mySKU);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheInfoOfSKUOnShoppingCartTable(RewardSKU sku) {
		Logger.verify("Verify the information of SKU which ID = " + sku.getId() + " on the Shopping Cart table displays correctly");
		Boolean check = rewardGeneralPage.isSKUDisplayedInShoppingCartPage(sku);
		if (check) {
			assertTrue(check, "the information of SKU which ID = " + sku.getId() + " on the Shopping Cart table does not display");
		}
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_012 The info of added SKUs on my cart and recipient cart display correctly on Shopping Cart Table");
		Logger.to("TO_CRP_008 - The information on Shopping Cart table display corectlly when adding SKU to my cart and recipient's cart");
		mySKU.init(Recipient.MYSELF);
		recipientSKU.init(Recipient.KIM_ANH);
	}

}
