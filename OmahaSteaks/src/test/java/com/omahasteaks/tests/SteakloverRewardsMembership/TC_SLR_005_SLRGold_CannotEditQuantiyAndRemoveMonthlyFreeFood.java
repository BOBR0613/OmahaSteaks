package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_005_SLRGold_CannotEditQuantiyAndRemoveMonthlyFreeFood extends TestBase_SLR {

	String monthlyFreeFoodName;

	@Inject
	Package mySKU;

	@Test
	public void TC_SLR_005_SLRGold_Cannot_Edit_Quantiy_And_Remove_Monthly_FreeFood() {
		initTestCaseData();

		signInWithSteakloverRewardsGoldAccount();

		searchAndAddSKUToCart(mySKU);

		verifyThereIsNoRemoveLink();

		verifyThereIsNoEditQuantityComboBox();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void verifyThereIsNoEditQuantityComboBox() {
		Logger.verify("In the 'Shopping Cart' page, verify that Steaklover reward gold user cannot eidt the quantity of monthly free food");
		assertFalse(shoppingCartPage.isQuantityComboBoxDisplayed(Recipient.MYSELF, monthlyFreeFoodName), "There quantity ComboBox on the monthly free food");
	}

	private void verifyThereIsNoRemoveLink() {
		Logger.verify("In the 'Shopping Cart' page, verify that SLR gold user cannot remove the monthly free food");
		assertFalse(shoppingCartPage.isRemoveLinkInFreeFoodDisplayed(Recipient.MYSELF, monthlyFreeFoodName), "There is 'Remove' link on the monthly free food");
	}

	private void signInWithSteakloverRewardsGoldAccount() {
		signIn(account);
		monthlyFreeFoodName = Constants.SLR_MONTHLY_FREE_FOOD;
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_005 - SLR GOLD user cannot edit the quantity and remove the monthly free food");
		Logger.to("TO_SLR_005 - In the 'Shopping Cart' page, verify that Steaklover reward gold user cannot remove the monthly free food");
		Logger.to("TO_SLR_006 - In the 'Shopping Cart' page, verify that Steaklover reward gold user cannot eidt the quantity of monthly free food");

		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		mySKU.initRandom(Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}

}
