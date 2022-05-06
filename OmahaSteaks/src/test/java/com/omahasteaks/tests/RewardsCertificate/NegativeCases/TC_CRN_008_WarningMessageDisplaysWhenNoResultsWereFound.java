package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_008_WarningMessageDisplaysWhenNoResultsWereFound extends TestBase_CR {

	String keyword;

	@Test
	public void TC_CRN_008_Warning_Message_Displays_When_No_Results_Were_Found() {
		initTestCaseData();

		searchSKUWithPoint(Constants.EMPTY_STRING);

		verifyWarningMessageIsDisplayed();

		searchSKUWithPoint(keyword);

		verifyWarningMessageIsDisplayed();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyWarningMessageIsDisplayed() {
		Logger.verify("Verify warning message \"Sorry, no search results were found.\" is displayed");
		assertTrue(rewardGeneralPage.isNoSearchResultsFoundDisplayed(), "Warning message \"Sorry, no search results were found.\" does not display when searching keyword with search type Point Value");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_008 Warning message displays when no results were found");
		Logger.to("TO_CRN_005	Search result page with message \"Sorry, no search results were found.\" is displayed when leaving the search text box empty");
		Logger.to("TO_CRN_006	Warning message \"Sorry, no search results were found.\" displays when searching keyword with search type Point Value");
		keyword = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 5);
	}
}
