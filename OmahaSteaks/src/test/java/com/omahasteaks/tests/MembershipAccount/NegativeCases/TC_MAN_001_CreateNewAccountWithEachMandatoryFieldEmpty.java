package com.omahasteaks.tests.MembershipAccount.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAN_001_CreateNewAccountWithEachMandatoryFieldEmpty extends TestBase_2SC {

    String pwdHaveLessThan8Characters, pwdNoHaveALetter, pwdNoHaveANumber;

    @Inject
    GeneralPage generalPage;

    @Inject
    SignInPage signInPage;

    @Inject
    MyAccountPage myAccountPage;

    @Inject
    Account newAccount, invalidAccount;

    @Test
    public void TC_MAN_001_Create_New_Account_With_Each_Mandatory_Field_Empty() {

	initTestCaseData();

	goToSignInPageAndClickCreateNewAccount();

	createNewAccountWithPasswordEmpty();

	verifyWarningMessageDisplaysWhenPasswordDoNotMatch();

	createNewAccountWithConfirmPasswordEmpty();

	verifyWarningMessageDisplaysWhenPasswordDoNotMatch();

	createNewAccountWithInvalidPassword(pwdHaveLessThan8Characters);

	verifyWarningMessageDisplaysWhenPasswordHaveLessThan8Characters();

	createNewAccountWithInvalidPassword(pwdNoHaveALetter);

	verifyWarningMessageDisplaysWhenPasswordNoHaveALetter();

	createNewAccountWithInvalidPassword(pwdNoHaveANumber);

	verifyWarningMessageDisplaysWhenPasswordNoHaveANumber();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void createNewAccountWithInvalidPassword(String password) {
	Logger.info("In \"My account\" page:" + "  - Fill valid email into \"username\" Email Address textbox" + "  - Fill invalid password" + "  - Click \"Join\" button");
	myAccountPage.createNewAccount(newAccount.getEmail(), password, true);
    }

    private void createNewAccountWithConfirmPasswordEmpty() {
	Logger.info("In \"My account\" page:" + "  - Fill valid all information and leave confirm password field" + "  - Click \"Join\" button");
	myAccountPage.createNewAccount(newAccount.getEmail(), newAccount.getPassword(), Constants.EMPTY_STRING, true);
    }

    private void createNewAccountWithPasswordEmpty() {
	Logger.info("In \"My account\" page:" + "  - Fill valid all information and leave password field" + "  - Click \"Join\" button");
	myAccountPage.createNewAccount(newAccount.getEmail(), Constants.EMPTY_STRING, newAccount.getPassword(), true);
    }

    private void verifyWarningMessageDisplaysWhenPasswordNoHaveANumber() {
	Logger.verify("The warning messages is displayed:" + "   Passwords: Password must contain at least 1 number.");
	assertEquals(myAccountPage.getErrorMessageByCreateAccountField(Constants.PASSWORD), Messages.INVALID_PASSWORD_ERROR_MESSAGE_4);

    }

    private void verifyWarningMessageDisplaysWhenPasswordNoHaveALetter() {
	Logger.verify("The warning message is displayed:" + "   Passwords: Password must contain at least 1 letter.");
	assertEquals(myAccountPage.getErrorMessageByCreateAccountField(Constants.PASSWORD), Messages.INVALID_PASSWORD_ERROR_MESSAGE_3);

    }

    private void verifyWarningMessageDisplaysWhenPasswordHaveLessThan8Characters() {
	Logger.verify("The warning message is displayed:" + "   Passwords: Password must contain at least 8 characters.");
	assertEquals(myAccountPage.getErrorMessageByCreateAccountField(Constants.PASSWORD), Messages.INVALID_PASSWORD_ERROR_MESSAGE_2);

    }

    private void verifyWarningMessageDisplaysWhenPasswordDoNotMatch() {
	Logger.verify("The warning message isdisplayed:" + "Passwords: Sorry, the passwords do not match. Please enter them again.");
	System.out.println("comparing...");
	System.out.println("1:"+myAccountPage.getErrorMessageByCreateAccountField(Constants.PASSWORD + "s"));
	System.out.println("2:"+Messages.INVALID_PASSWORD_ERROR_MESSAGE_1);
	assertEquals(myAccountPage.getErrorMessageByCreateAccountField(Constants.PASSWORD + "s"), Messages.INVALID_PASSWORD_ERROR_MESSAGE_1);

    }

    private void goToSignInPageAndClickCreateNewAccount() {
	Logger.info("In Homepage:" + "- Click 'Sign In' link" + "In \"My account\" page: " + "- Click \"Create New Account\" button" + "- Click \"Join\" button");
	generalPage.goToSignInPage();
	signInPage.clickCreateNewAccountButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_MAN_001 - Create new account with each mandatory field empty");
	Logger.to("TO_MAN_02 - In 'My Account' page, warning message \"Sorry, the passwords do not match. Please enter them again.\" is displayed for blank \"Password\" field when creating new account");
	Logger.to("TO_MAN_03 - In 'My Account' page, warning message \"Sorry, the passwords do not match. Please enter them again.\" is displayed for blank \"Confirm Password\" field when creating new account");
	Logger.to("TO_MAN_06 - In 'My Account' page, warning message displays when creating account with password have less than 8 characters");
	Logger.to("TO_MAN_07 - In 'My Account' page, warning message displays when creating account with password no have a letter");
	Logger.to("TO_MAN_08 - In 'My Account' page, warning message displays when creating account with password no have a number");
	newAccount.initRandomAccount();
	pwdHaveLessThan8Characters = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 5);
	pwdNoHaveALetter = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 8);
	pwdNoHaveANumber = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 8);
    }
}
