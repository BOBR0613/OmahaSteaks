package com.omahasteaks.tests.MembershipAccount.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAN_012_WarningMessageDisplaysWhenEditingEmailAddressWithInvaldData extends TestBase_2SC {
	String invaliEmail, invalidConfirmEmail;
	@Inject
	Account newAccount;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Test
	public void TC_MAN_012_Warning_Message_Displays_When_Editing_Email_Address_With_Invald_Data() {

		initTestCaseData();

		signIn();

		gotoChangeEmailAddressPage();

		changeEmailAddressWithInvalidData("", "");

		verifyWarningMessageDisplaysWhenLeavingAllFieldsEmpty();

		changeEmailAddressWithInvalidData(newAccount.getEmail(), newAccount.getEmail());

		verifyWarningMessageDisplaysWhenEdittingEmailWithEmailWhichIsTheSameAsCurrentEmail();

		changeEmailAddressWithInvalidData(invaliEmail, invaliEmail);

		verifyWarningMessageDisplaysWhenEdittingEmailWithInvalidEmailFormat();

		changeEmailAddressWithInvalidData(invaliEmail, invalidConfirmEmail);

		verifyWarningMessageDisplaysWhenEdittingEmailWithInvalidConfirmEmailAddress();
	}

	
	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void verifyWarningMessageDisplaysWhenEdittingEmailWithInvalidConfirmEmailAddress() {
		Logger.verify("In \"Change Email Address\" page :Verify warning message \"Confirm Email Address does not match.\" is displayed when new email address and confirm email address are not the same");
		assertEquals(myAccountPage.getValidationConfirmEmailAddress(), Messages.CONFIRM_EMAIL_ADDRESS_VALIDATION, "Warning message \"Confirm Email Address does not match.\" is not displayed as expected ");
	}

	private void verifyWarningMessageDisplaysWhenEdittingEmailWithInvalidEmailFormat() {
		Common.waitForDOMChange();
		Logger.verify("In \"Change Email Address\" page :  Verify warning message \"Sorry, this is an invalid email address. Please try again.\" is displayed corectly");
		assertEquals(myAccountPage.getErrorMessageByField(AddressFields.CONFIRM_EMAIL), Messages.SORRY_INVALID_EMAIL_MESSAGE, "Warining message \"Sorry, this is an invalid email address. Please try again.\" is not displayed ");
	}

	private void verifyWarningMessageDisplaysWhenEdittingEmailWithEmailWhichIsTheSameAsCurrentEmail() {
		Logger.verify("In \"Change Email Address\" page :Verify warning message \"Email address was not changed.\" is displayed when user edits email address with email which is the same as the current email");
		assertEquals(myAccountPage.getErrorEmailAddressWasNotChanged(), Messages.EMAIL_WAS_NOT_CHANGED, "Error message is not displayed when user edits email address with email which is the same as the current email");
	}

	private void gotoChangeEmailAddressPage() {
		Logger.info("In homepage : \n" + " - Click \"My Account\" link");
		Common.waitForPageLoad();
		generalPage.goToMyAccountPage();
		Logger.info("In \"My Account \" page :- Click on \"Change email address\" link on left navigation menu");
		myAccountPage.selectAccountSettingOption(Constants.CHANGE_EMAIL_ADDRESS);
	}

	private void verifyWarningMessageDisplaysWhenLeavingAllFieldsEmpty() {
		Logger.verify("In \"Change Email Address\" page : Verify warning message \"The email address you supplied appears to be invalid. Could you please enter it again?\" is displayed for blank all fields ");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE)) {
			assertEquals(myAccountPage.getErrorEmailAddressWasNotChanged(), Messages.EMAIL_ADDRESS_REQUIRED_MESSAGE, " Required message is not displayed as expected ");
		} else {
			assertEquals(myAccountPage.getErrorMessageByField(AddressFields.EMAIL), Messages.EMAIL_ADDRESS_REQUIRED_MESSAGE, " Required message is not displayed as expected ");
		}
	}

	private void changeEmailAddressWithInvalidData(String invalidNewEmail, String invalidConfirmEmail) {
		Logger.info("In \"Change Email Address\" page : Enter " + invalidNewEmail + "into New Email address textbox .  Enter " + invalidConfirmEmail + "into Confirm email address textbox - Click on \"Update\" button");
		myAccountPage.changeEmailAddress(invalidNewEmail, invalidConfirmEmail);
	}


	private void initTestCaseData() {
		Logger.tc("TC_MAN_012 - Warning message displays when editing email address with invald data");
		Logger.to("TO_MAN_70 - In the 'Change email address' page, warning message \"Email address was not changed.\" is displayed when user edits email address with email which is the same as the current email");
		Logger.to("TO_MAN_71 - In the 'Change email address' page, warning message \"The email address you supplied appears to be invalid. Could you please enter it again?\" is displayed for blank all fields");
		Logger.to("TO_MAN_72 - In the 'Change email address' page, warning message \"Sorry, this is an invalid email address. Please try again.\" is displayed when user edits email address with invalid new email and confirm email");
		Logger.to("TO_MAN_73 - In the 'Change email address' page, warning message \"Confirm Email Address does not match.\" is displayed when new email address and confirm email address are not the same");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrmember01@omahasteaks.com");
		newAccount.initRandomAccount();
		newAccount.setEmail(account.getEmail());
		newAccount.setPassword(account.getPassword());
		invaliEmail = Common.getRandomString("");
		invalidConfirmEmail = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 9);
	}


	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}

}
