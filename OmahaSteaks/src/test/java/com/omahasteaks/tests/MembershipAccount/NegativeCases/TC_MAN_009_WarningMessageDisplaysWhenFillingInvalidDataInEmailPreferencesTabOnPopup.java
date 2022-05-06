package com.omahasteaks.tests.MembershipAccount.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAN_009_WarningMessageDisplaysWhenFillingInvalidDataInEmailPreferencesTabOnPopup extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    String invalidEmail;

    @Inject
    SignInPage signInPage;

    @Inject
    MyAccountPage myAccountPage;

    @Inject
    Account newAccount;

    @Inject
    CustomerAddress shippingAddress;

    @Inject
    ListAddresses lstAddresses;

    @Test
    public void TC_MAN_009_Warning_Message_Displays_When_Filling_Invalid_Data_In_Email_Preferences_Tab_On_Popup() {

	initTestCaseData();

	signIn();

	selectContactPreferencesOption();

	fillInvalidEmailAddress("");

	verifyErrorMsgIsDisplayedCorrectlyWhenLeavingEmailAddressFieldEmpty();

	fillInvalidEmailAddress(invalidEmail);

	verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidEmailAddress();

	closeEmailPreferencesPopup();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void closeEmailPreferencesPopup() {
	Logger.info("In \"Email Preferences\" tab:- Close \"Email Preferences\" tab");
	myAccountPage.closeModal();
    }

    private void verifyErrorMsgIsDisplayedCorrectlyWhenLeavingEmailAddressFieldEmpty() {
	Logger.verify("In \"Email Preferences\" tab: Verify  warning message \"Please Enter Your Email Address\" is displayed for blank \"Email Address\" field in Email Preferences tab");
	assertEquals(myAccountPage.getErrorMsgInEmailPreferencesTab(), Messages.EMAIL_EMPTY_MESSAGE, "Warning message \"Please Enter Your Email Address\" is not displayed for blank \"Email Address\" field in Email Preferences tab");
    }

    private void verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidEmailAddress() {
	Logger.verify("In \"Email Preferences\" tab:Verify  warning message \"Please Enter A Valid Email Address\" is displayed for blank \"Email Address\" field in Email Preferences tab");
	assertEquals(myAccountPage.getErrorMsgInEmailPreferencesTab(), Messages.INVALID_EMAIL_ADDRESS_ERROR_MESSAGE, "Warning message \"Please Enter A Valid Email Address\" is not displayed for blank \"Email Address\" field in Email Preferences tab");
    }

    private void selectContactPreferencesOption() {
	Logger.info("In My account page: \n" + "   - Click on 'Contact Preference' link on left navigation");
	generalPage.goToMyAccountPage();
	myAccountPage.selectContactPreferencesOption();
    }

    private void fillInvalidEmailAddress(String emailAddress) {
	Logger.info("In \"Email Preferences\" tab: - Leave Email address field  empty+ - Click on \"Update Preferences\" Button");
	myAccountPage.fillEmailAddressInEmailPreferencesTab(emailAddress);
	myAccountPage.clickUpdatePreferencesButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_MAN_009 - Warning message displays for invalid data For email field in Email Preferences tab");
	Logger.to("TO_MAN_59 - Warning message \"Please Enter Your Email Address\" is displayed for blank \"Email Address\" field in Email Preferences tab");
	Logger.to("TO_MAN_60 - Warning message \"Please Enter A Valid Email Address\" is displayed when filling invalid email address in Email Preferences tab");
	account = Constants.LIST_ACCOUNTS.getRandomAccount();
	newAccount.initRandomAccount();
	newAccount.setEmail(account.getEmail());
	newAccount.setPassword(account.getPassword());
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	invalidEmail = Common.getRandomString("");
    }

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}
	
}
