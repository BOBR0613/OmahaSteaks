package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.LinksCustomerService;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_018_CustomerServices_SendUsAnEmail_UserCanResetDateByClickingOnResetButton extends TestBase_CR {
	@Inject
	Account account;

	@Test
	public void TC_CRP_018_CustomerServices_SendUsAnEmail_User_Can_Reset_Date_By_Clicking_On_Reset_Button() {
		initTestCaseData();

		goToEachSubLink(LinksCustomerService.CONTACT_US);

		fillInformationAndClickReset();

		verifytheTextFilledIntoAllFieldsAreDeleted();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifytheTextFilledIntoAllFieldsAreDeleted() {
		Logger.verify("Verify the text filled into \"Your Email Address\" field is deleted");
		assertTrue(rewardGeneralPage.getTextInEmailField().equals(Constants.EMPTY_STRING), " the text filled into \"Your Email Address\" field does not delete");
		Logger.verify("Verify the text filled into \"Message Body\" field is deleted");
		assertTrue(rewardGeneralPage.getTextInEmailMsgField().equals(Constants.EMPTY_STRING), "The text filled into \"Message Body\" field does not delete");

	}

	private void fillInformationAndClickReset() {
		Logger.info("In \"Customer Service - Send us an email\" page, fill valid information into all fields");
		String textMsg = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 10);
		rewardGeneralPage.fillInformationToContactUsPage(account.getEmail(), textMsg);

		Logger.info("Click \"Reset\" link");
		rewardGeneralPage.clickResetLink();
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_018 - In Customer Services - Send Us An Email page, user can reset filled data by clicking on Reset button");
		Logger.to("TO_CRP_055	In Customer Service - Send us an email page: The text filled into \"Your Email Address\" field is deleted when clicking \"Reset\" link");
		Logger.to("TO_CRP_056	In Customer Service - Send us an email page: The text filled into \"Message Body\" field is deleted when clicking \"Reset\" link");
		account.initRandomAccount();
	}
}
