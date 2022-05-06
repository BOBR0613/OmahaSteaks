package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_009_SLRGoldAccount_TheUserStatusIsGoldAndHaveMonthlyFreeFood extends TestBase_SLR {

	@Test
	public void TC_SLR_009_SLRGoldAccount_The_User_Status_Is_Gold_And_Have_Monthly_Free_Food() {
		initTestCaseData();

		signIn(account);

		verifyUserStatusIsGoldInTheSteakloverRewardsSection();

		goToSteakloverRewardsPage();

		verifyUserStatusIsGoldInTheSteakloverRewardPage();

		verifyTheMonthlyFreeFoodIsDisplayedInRewardsTable();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyUserStatusIsGoldInTheSteakloverRewardsSection() {
		Logger.verify("Verify The User Status in Steaklover Rewards section is Gold when signing in with Gold Account");
		assertTrue(myAccountPage.isUserStatusDisplayedCorrectly(Constants.USER_STATUS_GOLD), "The user status in Steaklover Rewards section is not Gold when signing in with Gold Account");
	}
	
	private void verifyTheMonthlyFreeFoodIsDisplayedInRewardsTable() {
		Logger.verify("Verify the monthly free food is displayed in \"Rewards\" table when signing in with Gold Account");
		assertTrue(myAccountPage.isTheMonthlyFreeFoodDisplayed(), "The monthly free food does not display in \"Rewards\" table when signing in with Gold Account");
	}

	private void verifyUserStatusIsGoldInTheSteakloverRewardPage() {
		Logger.verify("Verify the status is \"Gold\" when signing in with Gold Account");
		assertTrue(myAccountPage.getUserStatusInSLRPage().contains(Constants.USER_STATUS_GOLD), "The status is not Gold when signing in with Gold account");
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_009 - The user status is Gold and have monthly free food when signing in with Gold Account");
		Logger.to("TC_SLR_029	In My account page, the status in Steaklover Rewards section is \"GOLD\" when signning in with Gold Account");
		Logger.to("TC_SLR_031	In Steaklover Rewards page, the status is \"Gold\" when signing in with Gold Account");
		Logger.to("TC_SLR_034	In Steaklover Rewards page, the monthly free food is displayed in \"Rewards\" table when signing in with Gold account");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		Common.modalDialog.closeSavorDialog();
	}
}
