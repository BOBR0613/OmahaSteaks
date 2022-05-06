package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_008_RegularAccount_CartValueGreaterThan49_99_NoMonthlyFreeFood extends TestBase_SLR {

	String monthlyFreeFoodName;

	@Inject
	Account slrAccount;

	@Inject
	Package mySKU;

	@Test
	public void TC_SLR_008_RegularAccount_Cart_Value_Greater_Than_49_99_No_Monthly_Free_Food_And_Sign_Up_Link_Displays() {
		initTestCaseData();

		signInWithSLRAccount();

		searchAndAddSKUToCart(mySKU);
 
		goToShoppingCartPage();

		verifyTheFreeFoodDoesNotDisplayInShoppingCartWhenLogInWithRegularAccount();

		verifySignUpLinkDisplaysInSteakloverRewardsSection();
 
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void goToShoppingCartPage() {
		Logger.info("In Hompage, go to Shopping Cart page");
		generalPage.goToMyCart();
	}

	private void verifyTheFreeFoodDoesNotDisplayInShoppingCartWhenLogInWithRegularAccount() {
		Logger.verify("Verify the free food is not displayed in the Shopping Cart page when loggin with Regular account");
		assertFalse(shoppingCartPage.isFreeFoodAddedByMembership(Recipient.MYSELF), "The monthly free food( " + monthlyFreeFoodName + " ) displays in My Cart when logged into a Regular account");
	}

 	private void signInWithSLRAccount() {
		signIn(slrAccount);
		Logger.info("In 'My Account' page: Click on 'Steaklover Rewards' link - Get the monthly free food name");
		myAccountPage.selectAccountSettingOption(Constants.STEAKLOVER_REWARDS);
		monthlyFreeFoodName = Constants.SLR_MONTHLY_FREE_FOOD;
		
		Logger.verify("Verify the free food is displayed in the rewards list aftere logging into Gold Membership account");
		assertFalse( myAccountPage.freeItemExists(),"Free item " + monthlyFreeFoodName + " exists in rewards list");
	}
 
	
	
	private void verifySignUpLinkDisplaysInSteakloverRewardsSection() {
		String signIn_signUpLnk = Constants.SIGN_IN_OR_SIGN_UP.get(DriverUtils.getRunningMode());
		Logger.verify("Verify the " + signIn_signUpLnk + " link is displayed in Steaklover Rewards section");
		assertTrue(shoppingCartPage.isSLROptionDisplayed(signIn_signUpLnk), signIn_signUpLnk + " link does not display in \"Steaklover Rewards\" section");
	}
 

	private void initTestCaseData() {
		Logger.tc("TC_SLR_008 Regular Account - There is no monthly free food and the \"Sign Up\" link is displayed");
		Logger.to("TO_SLR_024 - In the Shopping Cart page, verify that the \"SIGN UP\" link is displayed correctly when logging in with Regular Account and adding any item to the cart");
		Logger.to("TO_SLR_025 - Verify that the \"Steaklover Rewards\" popup is displayed when clicking on the \"SIGN UP\" link in the Shopping Cart page");
		Logger.to("TO_SLR_026 - Verify that the free food is not displayed in the Shopping Cart page when loggin by Regular account and  value of MyCart is greater than 49.99$");
		account.initRandomAccount();
		mySKU.init(SkuType.OVER100,Recipient.MYSELF); 
		slrAccount = Constants.LIST_ACCOUNTS.getAccountByEmail("slrmember01@omahasteaks.com");
		Common.modalDialog.closeSavorDialog();
	}

}
