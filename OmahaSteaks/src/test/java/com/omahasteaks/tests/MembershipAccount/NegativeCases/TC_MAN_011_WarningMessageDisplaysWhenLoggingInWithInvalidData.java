package com.omahasteaks.tests.MembershipAccount.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAN_011_WarningMessageDisplaysWhenLoggingInWithInvalidData extends TestBase_2SC {

	String invalidPassword, invalidFormatEmail;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Test
	public void TC_MAN_011_Warning_Message_Displays_When_Logging_In_With_Invalid_Data() {

		initTestCaseData();

		gotoSignInPage();

		signInWithInvalidData("", "");

		verifyWarningMsgDisplaysWhenLoggingInWithBlankEmailAddress();

		signInWithInvalidData(account.getEmail(), invalidPassword);

		verifyWarningMsgDisplaysWhenLoggingInWithInvalidPassword();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void gotoSignInPage() {
		Logger.info("In Homepage:  - Click 'Sign In' link");
		generalPage.goToSignInPage();
	}

	private void signInWithInvalidData(String email, String password) {
		Logger.info("In \"My Account\" Page :  Enter " + email + " into Email Address textbox ; - Enter " + password + " into Password textbox");
		signInPage.signIn(email, password);
	}

	private void verifyWarningMsgDisplaysWhenLoggingInWithBlankEmailAddress() {
		Logger.info("In \"My Account\" Page : - Verify Warning message \"No account found Please check your email address or create a new account.\" is displayed for blank \"Email Address\" field when login");
		assertEquals(myAccountPage.getErrorMessageInErrorBox(), Messages.NO_ACCOUNT_MESSAGE, "Warning message \"No account found Please check your email address or create a new account.\" is not displayed for blank \"Email Address\" field when login");
	}

	private void verifyWarningMsgDisplaysWhenLoggingInWithInvalidPassword() {
		Logger.info("In \"My Account\" Page : Verify Warning message \"Incorrect password Please try again or reset your password.\" is displayed for blank \"Password\" field when login");
		assertEquals(myAccountPage.getErrorMessageInErrorBox(), Messages.INCORRECT_PASSWORD, "Warning message \"Incorrect password Please try again or reset your password.\" is not displayed for blank \"Password\" field when login");
	}

	private void initTestCaseData() {
		Logger.tc("TC_MAN_011 - Warning message displays when logging in with invalid data ");
		Logger.to("TO_MAN_67 - Warning message \"No account found Please check your email address or create a new account.\" is displayed for blank \"Email Address\" field when login");
		Logger.to("TO_MAN_68 - Warning message \"Incorrect password Please try again or reset your password.\" is displayed for blank \"Password\" field when login");
		Logger.to("TO_MAN_69 - In the 'My Account' page, warning message is displayed when user logs in with invalid email format");
		account = Constants.LIST_ACCOUNTS.getRandomAccount();
		invalidPassword = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 9);
		invalidFormatEmail = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 10);
	}

}
