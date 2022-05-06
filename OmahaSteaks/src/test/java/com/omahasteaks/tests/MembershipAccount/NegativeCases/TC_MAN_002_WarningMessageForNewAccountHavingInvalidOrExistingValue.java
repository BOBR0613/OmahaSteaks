package com.omahasteaks.tests.MembershipAccount.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class TC_MAN_002_WarningMessageForNewAccountHavingInvalidOrExistingValue extends TestBase_2SC {

	String emailAddressTxt;
	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Test
	public void TC_MAN_002_Warning_Message_Displays_When_Creating_New_Account_Which_Has_Been_Already_Existed() {

		initTestCaseData();

		createNewAccount(account);

		goToMyAccountPageAndSignOut();

		createNewAccount(newAccount);

		verifyWarningmessageDisplaysWhenCreatingNewAccountWhichHasBeenAlreadyExisted();

		createNewAccountWithAllFieldsEmpty();

		verifyWarningMessagePleaseFillOutThisFieldDisplays();

		createNewAccountWithInvalidEmailFormat();

		verifyWarningMessageDisplaysWhenFillInvalidEmailFormat();

		post_Condition();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void verifyWarningMessageDisplaysWhenFillInvalidEmailFormat() {
		Logger.verify("Verify the warning message is displayed when fill invalid email format");
		assertTrue(signInPage.getWarningMessage().equals("Please include an '@' in the email address. '" + emailAddressTxt + "' is missing an '@'."), "Please include an '@' in the email address. '" + emailAddressTxt + "' is missing an '@'." + " does not display");
	}

	private void createNewAccountWithInvalidEmailFormat() {
		Logger.info("In \"My account\" page: " + "- Click \"Create New Account\" button" + "- Fill  invalid email format" + "- Click \"Join\" button");
		emailAddressTxt = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 2);
		myAccountPage.createNewAccount(emailAddressTxt, Constants.EMPTY_STRING, true);
	}

	private void verifyWarningMessagePleaseFillOutThisFieldDisplays() {
		Logger.verify("Verify the warning message is displayed:" + "\"Please fill out this field\" is displayed ");
		assertTrue(signInPage.getWarningMessage().equals("Please fill out this field."), "\"Please fill out this field\" does not exist");
	}

	private void createNewAccountWithAllFieldsEmpty() {
		Logger.info("In \"My account\" page: " + "- Click \"Create New Account\" button" + "- Leave all fields empty" + "- Click \"Join\" button");
		myAccountPage.createNewAccount(Constants.EMPTY_STRING, Constants.EMPTY_STRING, true);
	}

	private void verifyWarningmessageDisplaysWhenCreatingNewAccountWhichHasBeenAlreadyExisted() {
		Logger.verify("Verify the warning message is displayed:" + "   Email Address: Already in use. Please try again.");
		assertEquals(myAccountPage.getErrorMessageByCreateAccountField(Constants.EMAIL_ADDRESS), Messages.USED_EMAIL_ADDRESS_MESSAGE);
	}

	private void goToMyAccountPageAndSignOut() {
		Logger.info("In Homepage:" + "- Click \"My Account\" link" + "- Click \"Sign out\" link");
		generalPage.goToMyAccountPage();
		myAccountPage.signOut();
	}

	private void createNewAccount(Account account) {
		Logger.info("In Homepage:\n" + "- Click 'Sign In' link" + "In \"My account\" page: \n" + "- Click \"Create New Account\" button\n" + "- Enter all valid information\n" + "- Uncheck \"Join Steaklover Rewards \" checkbox\n" + " - Click \"Join\" button");
		generalPage.goToSignInPage();
		signInPage.clickCreateNewAccountButton();
		myAccountPage.createNewAccount(account);
	}

	private void initTestCaseData() {
		Logger.tc("TC_MAN_002 - Warning message displays for new account having invalid or existing value");
		Logger.to("TO_MAN_01 - In 'My Account' page, warning message \"Please fill out this field\" is displayed for blank \"Email Address\" field when creating new account");
		Logger.to("TO_MAN_04 - In 'My Account' page, warning message displays when creating account with invalid email format");
		Logger.to("TO_MAN_05 - In \"My Account\" page, warning message displays when creating new account which has been already existed");
		account.initRandomAccount();
		newAccount.initRandomAccount();
		newAccount.setEmail(account.getEmail());
	}

	private void post_Condition() {
		generalPage.goToSignInPage();
		signInPage.signIn(account);
		myAccountPage.deleteAccount(newAccount);
	}

}
