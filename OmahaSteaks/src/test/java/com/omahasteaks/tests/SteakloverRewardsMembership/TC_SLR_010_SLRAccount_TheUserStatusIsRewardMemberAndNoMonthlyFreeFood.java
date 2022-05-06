package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_010_SLRAccount_TheUserStatusIsRewardMemberAndNoMonthlyFreeFood extends TestBase_SLR {

	@Test
	public void TC_SLR_010_SLRAccount_The_User_Status_Is_Reward_Member_And_No_Monthly_Free_Food() {
		initTestCaseData();
		
		goToMyAccountPage();

		verifyUserStatusIsRewardMemberInSLRSection();

		goToSteakloverRewardsPage();

		verifyUserStatusIsRewardMemberInSteakloverRewardsPage();

		verifyTheMonthlyFreeFoodDoesNotDisplayInRewardTable();
 
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheMonthlyFreeFoodDoesNotDisplayInRewardTable() {
		Logger.verify("Verify the monthly free food does not display in \"Rewards\" table when signing in with SLR Account");
		assertFalse(myAccountPage.isTheMonthlyFreeFoodDisplayed(), "The month free food displays in \"Rewards\" table when signing in with SLR Account");
	}

	private void verifyUserStatusIsRewardMemberInSteakloverRewardsPage() {
		Logger.verify("Verify the status in Steaklover Rewards page is \"Reward Member\" when signing in with SLR Account");
		assertTrue(myAccountPage.getUserStatusInSLRPage().contains(Constants.USER_STATUS_SLR), "The status in Steaklover Rewards page is not \"Reward Member\" when signing in with SLR Account");
	}

	private void verifyUserStatusIsRewardMemberInSLRSection() {
		Logger.verify("Verify the status in Steaklover Rewards Section is \"Reward Member\" when signing in with SLR Account");
		assertTrue(myAccountPage.isUserStatusDisplayedCorrectly(Constants.USER_STATUS_SLR), "The status in Steaklover Rewards Section is not \"Reward Member\" when signing in with SLR Account");
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_010 - The user status is Reward Member and no monthly free food when signing in with SLR Account");
		Logger.to("TC_SLR_030	In My account page, the status in Steaklover Rewards section is \"Reward Member\" when signning in with SLR Account");
		Logger.to("TC_SLR_032	In teaklover Rewards page, the status is \"Reward Member\" when signning in with SLR Account");
		Logger.to("TC_SLR_035	In Steaklover Rewards page, the monthly free food does not display in \"Rewards\" table when signing in with SLR account");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrmember01@omahasteaks.com");
		Common.modalDialog.closeSavorDialog();
		signIn(account);
	}
 
}
