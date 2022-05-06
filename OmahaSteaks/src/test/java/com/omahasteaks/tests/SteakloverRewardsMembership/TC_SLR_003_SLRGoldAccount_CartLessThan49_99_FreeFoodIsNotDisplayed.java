package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_003_SLRGoldAccount_CartLessThan49_99_FreeFoodIsNotDisplayed extends TestBase_SLR {

	String monthlyFreeFoodName;
	@Inject
	SKU mySKU;;

	@Test
	public void TC_SLR_003_SLR_Gold_Account_Cart_Less_Than_49_99_Free_Food_Is_Not_Displayed() {
		initTestCaseData();

		signInWithSteakloverRewardsGoldAccount();

		searchAndAddSKUToCart(mySKU);

		verifyTheMonthlyFreeFoodIsNotDisplayedInMycart();

		verifyTheFreeShippingMessageIsDisplayedInTheTopOfMyCartSection();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	
	private void signInWithSteakloverRewardsGoldAccount() {
		signIn(account);
 		monthlyFreeFoodName = Constants.SLR_MONTHLY_FREE_FOOD;
	}
	
	private void initTestCaseData() {
		Logger.tc("TC_SLR_003 - Steaklover rewards GOLD - Free food is not displayed and shipping fee is not free when cart less than 49.99 $");
		Logger.to("TO_SLR_027 - Verify that the free food is not displayed in the Shopping Cart page when loggin in by Steaklover Rewards account and  value of MyCart is greater than 49.99$");
		Logger.to("TO_SLR_028 - In the 'Shopping Cart' page, verify that the needed points is displayed correctly on the \"Steak Lover Rewards summary\" section when logging in by the Steaklover Rewards account which  which has points less than specified points in the Rewards table in \"My Account/Steaklover Rewards\" page");

		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		mySKU.init(SkuType.UNDER50, Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}

	private void verifyTheFreeShippingMessageIsDisplayedInTheTopOfMyCartSection() {
		Logger.verify("In the 'Shopping Cart' page, the \"This cart qualifies for FREE SHIPPING\" text is not displayed in the top of 'Sumary cart' section ");
		assertTrue(shoppingCartPage.isTheFreeShippingMessageDisplayed(Recipient.MYSELF), "The \"" + Messages.SLR_GOLD_FREE_SHIP_MSG + "\" is displayed in the Mycart section");
	}

	private void verifyTheMonthlyFreeFoodIsNotDisplayedInMycart() {
		Logger.verify("In the 'Shopping Cart' page, verify that the free food( "+ monthlyFreeFoodName+") whose point is FREE, is not displayed on the bottom of the cart ");
		assertFalse(shoppingCartPage.isFreeFoodAddedByMembership(Recipient.MYSELF), "The "+monthlyFreeFoodName+" -monthly free food displays in the Shopping Cart page");
	}
}
