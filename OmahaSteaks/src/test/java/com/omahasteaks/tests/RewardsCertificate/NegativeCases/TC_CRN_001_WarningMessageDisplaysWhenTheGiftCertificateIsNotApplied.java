package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_001_WarningMessageDisplaysWhenTheGiftCertificateIsNotApplied extends TestBase_CR {

	String invalidReward;

	@Inject
	RewardSKU mySKU1, mySKU2;
	

	@Test
	public void TC_CRN_001_Warning_Message_Displays_When_The_Gift_Certificate_Is_Not_Applied() {
		initTestCaseData();

		searchAndAddSKU9ToCart(mySKU1); 
		
		goToShoppingCartPage();

		fillRewardNumberAndClickCheckOut(Constants.EMPTY_STRING);

		verifyWarningMessageDisplaysWhenLeavingRewardNumberEmpty();

		fillRewardNumberAndClickCheckOut(invalidReward);

		verifyWarningMessageDisplaysWhenFillingInvalidRewardNumber();

		
		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID2);

		searchAndAddMoreSKUToCart();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID2);

		verifyWarningMessageDisplaysWhenValueOfRewardNumberSmallerThanTotalPoints();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyWarningMessageDisplaysWhenValueOfRewardNumberSmallerThanTotalPoints() {
		Logger.verify("Verify warning message \"Gift account:  More certificates are required.\" is displayed when entering a reward number which has point value smaller than total points of SKUs");
		assertEquals(rewardShoppingCart.getListErrorMessageByField(Constants.GIFT_ACCOUNT)[0], Messages.INVALID_REWARD_CODE_MESSAGE_1, "Warning message \"Gift account:  More certificates are required.\" does not display");
	}

	private void searchAndAddMoreSKUToCart() {
		goToHomePage();

		searchAndAddSKU9ToCart(mySKU2);
 
		goToShoppingCartPage();
	}

	private void goToHomePage() {
		Logger.info("In Shopping Cart page, click \"Resume Shopping\" link");
		rewardShoppingCart.gotoHomePage();
	}

 

	private void verifyWarningMessageDisplaysWhenFillingInvalidRewardNumber() {
		Logger.verify("Warning message \"Gift account:  Please call Customer Service for assistance. Gift account:  Account " + invalidReward + " is not valid (0102). Please double-check your entry and try again\" is displayed when entering invalid reward number");
		String[] lstMessages = rewardShoppingCart.getListErrorMessageByField(Constants.GIFT_ACCOUNT);
		assertEquals(lstMessages[0], Messages.INVALID_REWARD_CODE_MESSAGE_2, "Warning message \"Please call Customer Service for assistance.\" does not display");
		assertEquals(lstMessages[1], "Account " + invalidReward.toUpperCase() + " is not valid (0102). Please double-check your entry and try again.", "Warning message \"Account " + invalidReward + " is not valid (0102). Please double-check your entry and try again.\" does not display");
	}

	private void verifyWarningMessageDisplaysWhenLeavingRewardNumberEmpty() {
		Logger.verify("Verify warning message \"Gift account:  More certificates are required.\" is displayed when leaving reward number empty");
		assertEquals(rewardShoppingCart.getListErrorMessageByField(Constants.GIFT_ACCOUNT)[0], Messages.INVALID_REWARD_CODE_MESSAGE_1, "Warning message \"Gift account:  More certificates are required.\" does not display");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_001 Warning message displays when the Gift Certificate is not applied");
		Logger.to("TO_CRN_001	In Shopping cart page, warning message \"Gift account:  More certificates are required.\" is displayed when leaving reward number empty");
		Logger.to("TO_CRN_002	\"In Shopping cart page, warning message \"Gift account:  Please call Customer Service for assistance. Gift account:  Account sfdsdfdfdfdtfd is not valid (0102). Please double-check your entry and try again\" is displayed when entering invalid reward number");
		Logger.to("TO_CRN_003	In the shopping cart page, the \"Gift account:  Gift Certificate 9452616X34T is not fully used. ( 10 remaining) (0117)\" message is displayed when entering a reward number which has point value greater than total points of SKUs.");
		Logger.to("TO_CRN_004	In the shopping cart page, the \"Gift account:  More certificates are required.\" message is displayed when entering a reward number which has point value smaller than total points of SKUs.");
		mySKU1.init(Recipient.MYSELF, 10);
		mySKU2.init(Recipient.MYSELF, 10);
		invalidReward = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 10);
	}
}
