package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.LinksCustomerService;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_005_CustomerService_SendUsAnEmail_WarningMessageDisplaysWhenFillingInvalidInforIntoAllFields extends TestBase_CR {
	String invalidEmail, textMsg;

	@Inject
	Account account;

	@Test
	public void TC_CRN_005_CustomerService_SendUsAnEmail_Warning_Message_Displays_When_Filling_Invalid_Information_Into_All_Fields() {
		initTestCaseData();

		goToEachSubLink(LinksCustomerService.CONTACT_US);

		fillEmptyIntoAllFieldsAndSubmit();

		verifyWarningMessageDisplaysWhenSubmitWithoutFillingAllFields();

		fillAllFieldWithoutEmailMsgAndSubmit();

		verifyWarningMessageDisplaysWhenSubmitWithoutFillingEmailMsg();

		fillInvalidEmailIntoEmailFieldAndSubmit();

		verifyWarningMessageDisplaysWhenFillingInvalidEmail();

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField() {
		Logger.info("In Shipping Information page: Clicking to underlined value in warning message of \" Your Email Address\"");
		rewardGeneralPage.clickOnEmailAddressFieldLinkInListErrorMessage();

		Logger.verify("Verify the cursor moves to error \" Your Email Address\" field");
		assertTrue(rewardGeneralPage.isEmailAddressFieldFocused(), "The cursor does not move to error \" Your Email Address\" field");
	}

	private void verifyWarningMessageDisplaysWhenFillingInvalidEmail() {
		Logger.verify("Verify warning message \"Valid Email Address Required\" displays when filling invalid email into \"Your Email Address\" field");
		assertEquals(rewardGeneralPage.getErrorMessageByField("Message Email Address"), Messages.INVALID_EMAIL_ADRESS_MESSAGE, "Warning message \"Valid Email Address Required\" does not display");
	}

	private void fillInvalidEmailIntoEmailFieldAndSubmit() {
		fillInformationToContactUsPage(invalidEmail, textMsg);

		rewardGeneralPage.clickSubmitLink();
	}

	private void verifyWarningMessageDisplaysWhenSubmitWithoutFillingEmailMsg() {
		Logger.verify("Verify warning message \"Please fill out this field.\" displays when clicking \"Submit\" without filling all fields");
		if (rewardGeneralPage.getWarningMessageInEmailMsgField().equals(Messages.EMPTY_EMAIL_ERROR_MESSAGE_1))
			assertEquals(rewardGeneralPage.getWarningMessageInEmailMsgField(), "Please fill out this field.", "Warning message \"Please fill out this field.\" does not display");
	}

	private void fillAllFieldWithoutEmailMsgAndSubmit() {
		fillInformationToContactUsPage(account.getEmail(), Constants.EMPTY_STRING);

		rewardGeneralPage.clickSubmitLink();
	}

	private void verifyWarningMessageDisplaysWhenSubmitWithoutFillingAllFields() {
		Logger.verify("Verify warning message \"Please fill out this field.\" displays when clicking \"Submit\" without filling all fields");
		if (rewardGeneralPage.getWarningMessageInEmailAddressField().equals(Messages.EMPTY_EMAIL_ERROR_MESSAGE_1))
			assertEquals(rewardGeneralPage.getWarningMessageInEmailAddressField(), Messages.EMPTY_EMAIL_ERROR_MESSAGE_1, "Warning message \"Please fill out this field.\" does not display");
	}

	private void fillEmptyIntoAllFieldsAndSubmit() {
		fillInformationToContactUsPage(Constants.EMPTY_STRING, Constants.EMPTY_STRING);

		rewardGeneralPage.clickSubmitLink();
	}

	private void fillInformationToContactUsPage(String email, String textMsg) {
		Logger.info("In \"Customer Service - Send us an email\" page, fill information into all fields");
		rewardGeneralPage.fillInformationToContactUsPage(email, textMsg);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_005 CustomerService_SendUsAnEmail Warning message displays when filling invalid information into all fields");
		Logger.to("TO_CRN_028	In 'Send us an email' page, warning message \"Please fill out this field.\" displays when clicking \"Submit\" link without filling \"Your Email Address\" field");
		Logger.to("TO_CRN_029	In Customer Service - Send us an email page: warning message \"Please fill out this field.\" displays when clicking \"Submit\" link without filling \"Message Body\" field");
		Logger.to("TO_CRN_030	In Customer Service - Send us an email page: warning message \"Valid Email Address Required\" displays when filling invalid email into \"Your Email Address\" field");
		Logger.to("TO_CRN_031	In Customer Service - Send us an email page: The cursor moves to error field correctly when clicking to underlined value in warning message.");
		account.initRandomAccount();
		invalidEmail = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 10);
		textMsg = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 10);
	}
}
