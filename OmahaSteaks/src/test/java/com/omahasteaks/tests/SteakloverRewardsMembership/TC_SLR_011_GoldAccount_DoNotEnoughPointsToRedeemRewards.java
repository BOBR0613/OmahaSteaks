package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_011_GoldAccount_DoNotEnoughPointsToRedeemRewards extends TestBase_SLR {
	String monthlyFreeFoodName;

	@Inject
	Package mySKU;

	@Test
	public void TC_SLR_011_GoldAccount_Do_Not_Enough_Points_To_Redeem_Rewards() {
		initTestCaseData();

		signinWithGoldAccount();

		searchAndAddSKUToCart(mySKU);

		verifyTheMonthlyFreeFoodIsDisplaysInShoppingCartPage();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheMonthlyFreeFoodIsDisplaysInShoppingCartPage() {
		Logger.verify("Verify the monthly free food is displayed in the Shopping Cart page when loggin with Gold account");
		assertTrue(shoppingCartPage.isFreeFoodAddedByMembership(Recipient.MYSELF), "The monthly free food( " + monthlyFreeFoodName + " ) does not display in My Cart when login with Gold account");
	}

 	private void signinWithGoldAccount() {
		signIn(account);
		monthlyFreeFoodName = Constants.SLR_MONTHLY_FREE_FOOD;
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_011 The monthly free food and \"point needed for next reward\" message are displayed when logging in by Gold account which have not enough points to redeem");
		Logger.to("TC_SLR_036	In the 'Shopping Cart' page, verify that points until your next reward is displayed correctly on the \"Steak Lover Rewards summary\" section when logging in by the Steaklover Rewards account which has not enough points to redeem rewards");
		Logger.to("TC_SLR_037	In the 'Shopping Cart' page, verify that the monthly free food whose point is FREE, is displayed on the bottom of the cart when signing in by the SLR Gold account and value of the cart is greater than 49.99$");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		mySKU.init(SkuType.PACKAGES, Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}
}
