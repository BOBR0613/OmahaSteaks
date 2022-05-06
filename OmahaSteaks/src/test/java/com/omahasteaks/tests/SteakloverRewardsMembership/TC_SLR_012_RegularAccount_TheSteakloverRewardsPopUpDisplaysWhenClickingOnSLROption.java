package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_012_RegularAccount_TheSteakloverRewardsPopUpDisplaysWhenClickingOnSLROption extends TestBase_SLR {

	@Test
	public void TC_SLR_012_RegularAccount_The_Steaklover_Rewards_Pop_Up_Displays_When_Clicking_On_SLR_Option() {

		initTestCaseData();

		signinWithSLRAccount();

		goToMyAccountPage();

		goToSteakloverRewardsPage();

		verifyJoinSteakloverRewardPopUpDisplays();


	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void signinWithSLRAccount() {
		signIn(account);
 	}

 
	private void verifyJoinSteakloverRewardPopUpDisplays() {
		Common.waitForPageLoad();
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			Logger.verify("Verify Join Steaklover Reward popup is displayed after clicking Steaklover Rewards option in My Account page");
			assertTrue(myAccountPage.isJoinSteakloverRewardsModalDisplayed(), "Join Steaklover Reward popup is displayed after clicking Steaklover Rewards option in My Account page");
		}
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_012 - RegularAccount_The Steaklover Rewards popup displays when clicking on SLR option");
		Logger.to("TC_SLR_034	The \"Steaklover Rewards\" popup displays after clicking on \"Steaklover Reward\" option in \"My Omaha Steaks\" section when signing in with Regular Account");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop02@omahasteaks.com"); 
		Common.modalDialog.closeSavorDialog();
	}

}
