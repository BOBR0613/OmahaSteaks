package com.omahasteaks.tests.SteakloverRewardsMembership;
 
import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_007_SLRAccount_CartValueUnder_50_NoMonthlyFreeFood extends TestBase_SLR {

	String monthlyFreeFoodName;

	int minPts;

	@Inject
	Account goldAccount;

	@Inject
	Package mySKU;

	@Test
	public void TC_SLR_007_SLRAccount_Cart_Value_Under_50_NoMonthlyFreeFood() {
		initTestCaseData();
		
		signInWithSteakloverRewardsGoldAccount();

		searchAndAddSKUToCart(mySKU);

		verifyTheFreeFoodDoesNotDisplay();
	}

	
	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void signInWithSteakloverRewardsGoldAccount() {
		signIn(goldAccount);
		monthlyFreeFoodName = Constants.SLR_MONTHLY_FREE_FOOD; 
	}


	private void verifyTheFreeFoodDoesNotDisplay() {
		Logger.verify("Verify the free food is not displayed in the Shopping Cart page when loggin with Steaklover Rewards account");
		assertFalse(shoppingCartPage.isFreeFoodAddedByMembership(Recipient.MYSELF), "The monthly free food( " + monthlyFreeFoodName + " ) displays in My Cart when login with Steaklover Rewards account");
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_007 - There is no monthly free food and the number of points which need for the next rewards is displayed correctly");
		Logger.to("TO_SLR_017 - Verify that the free food is not displayed in the Shopping Cart page when loggin in by Steaklover Rewards account and 'item subtotal' value in the 'Cart Summary' section is greater than 49.99$");
		Logger.to("TO_SLR_018 - In the 'Shopping Cart' page, verify that the \"( ... points until your next reward)\" text is displayed on the \"Steak Lover Rewards summary\" section when logging in by the Steaklover Rewards account which  which has points less than specified points in the Rewards table in \"My Account/Steaklover Rewards\" page");
		goldAccount = Constants.LIST_ACCOUNTS.getAccountByEmail("slrmember01@omahasteaks.com");
		mySKU.init(SkuType.UNDER50, Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}

	 
}
