package com.omahasteaks.tests.MembershipAccount.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAP_004_UserCanSeeSteakloverRewardPoint extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    SignInPage signInPage;

    @Inject
    MyAccountPage myAccountPage;

    @Test
    public void TC_MAP_004_User_Can_See_Steaklover_Reward_Point() {

	initTestCaseData();

	signInWithExistingAccount();

	clickSteakloverRewardsLink();

	verifySteakloverRewardsPointIsDisplayedCorrectly();

    }
    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void signInWithExistingAccount() {
	Logger.info("In 'My Account' page:- Enter email:" + account.getEmail() + ",password:" + account.getPassword() + " - Click on\"Login\" button");
	generalPage.goToSignInPage();
	signInPage.signIn(account);
    }

    private void clickSteakloverRewardsLink() {
	Logger.info("In 'My Account' page: Click on 'Steaklover Rewards' link");
	myAccountPage.selectAccountSettingOption(Constants.STEAKLOVER_REWARDS);
	Common.waitForPageLoad();
    }

    private void verifySteakloverRewardsPointIsDisplayedCorrectly() {
	Logger.verify("In 'My Account' page:Verify Current Point Total is 50");
	assertEquals(myAccountPage.getSteakLoverRewardsPoint(), Constants.REWARDS_POINT_TOTAL, "The current point total is not displayed correctly as expected");

    }

    private void initTestCaseData() {
	Logger.tc("TC_MAP_004 - User can see Steaklover Rewards Point in My Account Page");
	Logger.tc("TO_MAP_11 - User can see Steaklover Rewards Point in My account Page");
	account = Constants.LIST_ACCOUNTS.setAccountByEmail("slrmember01@omahasteaks.com"); 
    }

}
