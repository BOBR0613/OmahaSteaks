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

public class TC_MAN_010_WarningMessageDisplaysWhenFillingInvalidDataInPhonePreferencesTabOnPopup extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    String invalidPhoneNumber, invalidEmail;

    @Inject
    SignInPage signInPage;

    @Inject
    MyAccountPage myAccountPage;

    @Inject
    Account newAccount;

    @Test
    public void TC_MAN_010_Warning_Message_Displays_When_Filling_Invalid_Data_In_Phone_Preferences_Tab_On_Popup() {

	initTestCaseData();

	signIn();

	gotoPhonePreferencesTab();

	fillInvalidPhoneNumber(invalidPhoneNumber);

	clickUpdatePreferencesButton();

	verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidPhoneNumber();

	selectDoNotCallMeWithExclusiveOffersOption();

	verifyWarningMessageIsDisplayedCorrectly();

	gotocScheduleYourCallTimesPopup();

	fillInvalidEmailAddress(invalidEmail);

	fillInvalidPhoneNumber(invalidPhoneNumber);

	clickSubmitButton();

	verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidEmailAddressInScheduleYourCallTimesPopup();

	verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidPhoneNumberInScheduleYourCallTimesPopup();

	fillInvalidPhoneNumber("");

	fillInvalidEmailAddress("");

	clickSubmitButton();

	verifyErrorMsgIsDisplayedCorrectlyWhenLeavingEmailAddressFieldEmptyInScheduleYourCallTimesPopup();

	verifyErrorMsgIsDisplayedCorrectlyWhenLeavingPhoneNumberEmptyInScheduleYourCallTimesPopup();

	closeEmailPreferencesPopup();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidEmailAddressInScheduleYourCallTimesPopup() {
	Logger.verify("In \"Schedule Your Call Times\" popup :- Verify warning message \"Please Enter A Valid Email Address\"  is displayed correctly");
	assertEquals(myAccountPage.getErrorMsgInEmailPreferencesTab(), Messages.INVALID_EMAIL_ADDRESS_ERROR_MESSAGE, "Warning message \"Please Enter A Valid Email Address\"  is not displayed as expected");
    }

    private void verifyErrorMsgIsDisplayedCorrectlyWhenLeavingEmailAddressFieldEmptyInScheduleYourCallTimesPopup() {
	Logger.verify("In \"Schedule Your Call Times\" popup :- Verify warning message \"Please Enter Your  Email Address\"  is displayed correctly");
	assertEquals(myAccountPage.getErrorMsgInEmailPreferencesTab(), Messages.EMAIL_EMPTY_MESSAGE, "Warning message \"Please Enter Your  Email Address\"  is not displayed as expected");
    }

    private void fillInvalidEmailAddress(String emailAddress) {
	Logger.info("In \"Schedule Your Call Times\" popup : - Fill invalid email address( " + emailAddress + " ) into Email Address textbox");
	myAccountPage.fillEmailAddressInEmailPreferencesTab(emailAddress);
    }

    private void verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidPhoneNumberInScheduleYourCallTimesPopup() {
	Logger.verify("In \"Schedule Your Call Times\" popup :- Verify warning message \"Please Enter A Valid Phone Number\" is displayed correctly");
	assertEquals(myAccountPage.getErrorMsgForPhoneTxtInScheduleYourCallTimesPopup(), Messages.INVALID_PHONE_ERROR_MESSAGE, "Warning message \"Please Enter A Valid Phone Number\" is not displayed as expected");
    }

    private void verifyErrorMsgIsDisplayedCorrectlyWhenLeavingPhoneNumberEmptyInScheduleYourCallTimesPopup() {
	Logger.verify("In \"Schedule Your Call Times\" popup :Verify warning message \"Please Enter Your  Phone Number\" is displayed correctly");
	assertEquals(myAccountPage.getErrorMsgForPhoneTxtInScheduleYourCallTimesPopup(), Messages.PHONE_EMPTY_MESSAGE, "Warning message \"Please Enter Your  Phone Number\" is not displayed as expected");
    }

    private void gotocScheduleYourCallTimesPopup() {
	Logger.info("In \" Phone Preferences\" Tab: - Click on \"Schedule my call times\" link ");
	myAccountPage.clickScheduleYourCallTimesLink();
    }

    private void verifyWarningMessageIsDisplayedCorrectly() {
	Logger.verify("In \" Phone Preferences\" Tab: Verify warning message \"Note: If you are a member of Omaha Steaks Personal Shopper, selecting this option will opt you out of the program.\" is displayed correctly");
	assertEquals(myAccountPage.getWarningMsgWhenSelectingDoNotCallMeOption(), Messages.DO_NOTT_CALL_ME_MESSAGE, "Warning message \"Note: If you are a member of Omaha Steaks Personal Shopper, selecting this option will opt you out of the program.\" is not displayed");
    }

    private void selectDoNotCallMeWithExclusiveOffersOption() {
	Logger.info("In \" Phone Preferences\" Tab: - Select \"Do not call me with exclusive offers\" option");
	myAccountPage.selectCallMeWithExlusiveOffers(Constants.PHONE_PREFERENCES_OPTION_5);
    }

    private void closeEmailPreferencesPopup() {
	Logger.info("In \"Email Preferences\" tab:- Close \"Email Preferences\" tab");
	myAccountPage.closeModal();
    }

    private void verifyErrorMsgIsDisplayedCorrectlyWhenFillingInvalidPhoneNumber() {
	Logger.verify("In \" Phone Preferences\" Tab: Verify warning message \" Please Enter A Valid Phone Number\" is displayed correctly");
	assertEquals(myAccountPage.getErrorMsgForPhoneTxtInPhoneNumberPreferencesTab(), Messages.INVALID_PHONE_ERROR_MESSAGE, "Warning message \" Please Enter A Valid Phone Number\" is not displayed as expected");
    }

    private void gotoPhonePreferencesTab() {
	Logger.info("In My account page: \n" + "   - Click on 'Contact Preference' link on left navigation");
	generalPage.goToMyAccountPage();
	myAccountPage.selectContactPreferencesOption();
	Logger.info("Click on 'Phone preferences' tab on 'Email Preferences' popup");
	myAccountPage.clickPhonePreferencesTab();
    }

    private void fillInvalidPhoneNumber(String invalidPhoneNumber) {
	Logger.info("In \" Phone Preferences\" Tab:-  Fill invalid ( " + invalidPhoneNumber + " )phone number into \"Phone Number\" textbox");
	myAccountPage.fillPhoneNumberInPhonePreferencesTab(invalidPhoneNumber);
    }

    private void clickUpdatePreferencesButton() {
	Logger.info("In \" Phone Preferences\" Tab: - Click on \"UPDATE PREFERENCES\" button");
	myAccountPage.clickUpdatePreferencesButtonInPhonePreferencesTab();
    }

    private void clickSubmitButton() {
	Logger.info("In \"Schedule Your Call Times\" popup :- Click on \"SUBMIT\" button");
	myAccountPage.clickSubmitButtonInScheduleYourCallTimesPopup();
    }

    private void initTestCaseData() {
	Logger.tc("TC_MAN_010 - Warning message displays when filling invalid data in phone preferences tab on popup ");
	Logger.to("TO_MAN_61 - Warning message displays when selecting 'Do not call me with exclusive offers' in Phone preferences tab");
	Logger.to("TO_MAN_62 - Warning message \"Please Enter A Valid Phone Number\" is displayed when filling invalid phone number in Phone Preferences tab");
	Logger.to("TO_MAN_63 - Warning message \"Please Enter Your Email Address\" is displayed for blank \"Email Address\" field in Schedule Your Call Times popup after clicking \"Schedule Your Call Times\" link in \"Phone Preferences\" tab");
	Logger.to("TO_MAN_64 - Warning message \"Please Enter Your Phone Number\" is displayed for blank \"Phone Number\" field in Schedule Your Call Times popup after clicking \"Schedule Your Call Times\" link in \"Phone Preferences\" tab");
	Logger.to("TO_MAN_65 - Warning message \"Please Enter A Valid Email Address\" is displayed when filling invalid value into \"Email Address\" field in Schedule Your Call Times popup after clicking \"Schedule Your Call Times\" link in \"Phone Preferences\" tab");
	Logger.to("TO_MAN_66 - Warning message \"Please Enter A Valid Phone Number\" is displayed when filling invalid value into \"Phone Number\" field in Schedule Your Call Times popup after clicking \"Schedule Your Call Times\" link in \"Phone Preferences\" tab");

	account = Constants.LIST_ACCOUNTS.setAccountByEmail("testDesktop06@omahasteaks.com");
	account.setPassword("qaR3gT3st06");
	newAccount.initRandomAccount();
	newAccount.setEmail(account.getEmail());
	newAccount.setPassword(account.getPassword());
	invalidPhoneNumber = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 10);
	invalidEmail = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 11);
    }
    
	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
 	}
	
}
