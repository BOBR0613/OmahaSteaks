package com.omahasteaks.tests.MembershipAccount.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAP_003_UserCanChangeEmailAndUpdateEmailAndPhonePreferences extends TestBase_2SC {
	String phonePreferencesOption;
	List<String> listEmailPreferencesOptions;

	@Inject
	CustomerAddress customerAddress;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Test
	public void TC_MAP_003_User_Can_Change_Email_And_Update_Email_And_Phone_Preferences() {
		initTestCaseData();
		
		signIn();

 		changeEmailAddress();

 		changeEmailAddressBackToOriginal();

		selectContactPreferencesOption();

		updateEmailPreferences();

		verifyEmailPreferencesIsUpdatedCorrectly();

		updatePhonePreferences();

		verifyPhonePreferencesIsUpdatedCorrectly();

		closeContactPreferencesModal();
	}

	
	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void changeEmailAddress() {
		Logger.info("In homepage : \n" + " - Click \"My Account\" link");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			generalPage.goToMyAccountPage();
		} else {
			generalPage.goToSignInPage();
		}
		Logger.info("In \"My account \" page: \n" + " - Click the 'Change Email Address' link in the left hand navigation.\n" + "  - Fill valid information into all files .\n" + "  - Click on 'Update' button");
		myAccountPage.selectAccountSettingOption(Constants.CHANGE_EMAIL_ADDRESS);
 		myAccountPage.changeEmailAddress(newAccount.getEmail());
		Logger.verify("Verify " + newAccount.getEmail() + " text exists");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			assertEquals(myAccountPage.getWelcomeMembershipText().toLowerCase(), newAccount.getEmail().toLowerCase(), "Email was not changed to " + newAccount.getEmail());
	}

	private void changeEmailAddressBackToOriginal() {
		Logger.info("In homepage : \n" + " - Click \"My Account\" link");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			generalPage.goToMyAccountPage();
		} else {
			generalPage.goToSignInPage();
		}
		Logger.info("In \"My account \" page: \n" + " - Click the 'Change Email Address' link in the left hand navigation.\n" + "  - Fill valid information into all files .\n" + "  - Click on 'Update' button");
		myAccountPage.selectAccountSettingOption(Constants.CHANGE_EMAIL_ADDRESS);
		newAccount.setEmail(account.getEmail());
		myAccountPage.changeEmailAddress(account.getEmail());
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			assertEquals(myAccountPage.getWelcomeMembershipText().toLowerCase(), account.getEmail().toLowerCase(), "Could not reset account to its original email address ("+account.getEmail()+")");
	}

	private void selectContactPreferencesOption() {
		Logger.info("In My account page: \n" + "   - Click on 'Contact Preference' link on left navigation");
		myAccountPage.selectAccountSettingOption(Constants.CONTACT_PREFERENCES);
	}

	private void updateEmailPreferences() {
		Logger.info("In 'Email preferences' popup :\n" + " - Random select one of Exclusive Offers \n" + " - Random select one of Steaklover Rewards \n" + " - Click on the 'UPDATE PREFERENCES' button.\n" + " - Click 'MAKE ADDITIONAL CHANGES' button on 'Contact Preferences Updated' popups");
		listEmailPreferencesOptions = myAccountPage.updateRandomEmailPreferences();
	}

	public void verifyEmailPreferencesIsUpdatedCorrectly() {
		Common.waitForDOMChange();
		Logger.verify("Verify 'How Often Do You Want to See Deals' is the same when user updates email preferences");
		assertTrue(myAccountPage.isCheckboxContactPreferencesOptionByValueChecked(listEmailPreferencesOptions.get(0).toString()), "'How Often Do You Want to See Deals' isn't the same when user updates email preferences as expected");
		Logger.verify("Verify 'Steaklover Rewards' is the same when user updates email preferences");
		if (myAccountPage.isCheckboxContactPreferencesOptionByValueChecked(listEmailPreferencesOptions.get(1).toString()))
			assertTrue(myAccountPage.isCheckboxContactPreferencesOptionByValueChecked(listEmailPreferencesOptions.get(1).toString()), "'Steaklover Rewards' isn't the same when user updates email preferences as expected");
	}

	private void updatePhonePreferences() {
		Logger.info("Click on 'Phone preferences' tab on 'Email Preferences' popup");
		myAccountPage.clickPhonePreferencesTab();
		Logger.info("In 'Phone preferences' popup :\n" + " - Enter valid phone number\n" + " - Random select one of 'Call me with exclusive offers'.\n" + " - Click on the 'UPDATE PREFERENCES' button.\n" + " - Click on 'MAKE ADDITIONAL CHANGES' button on 'Contact Preferences Updated' popup");
		phonePreferencesOption = myAccountPage.updateRandomPhonePreferences(customerAddress.phoneNumber);
	}

	public void verifyPhonePreferencesIsUpdatedCorrectly() {
		Logger.verify("Verify 'Call me with exlusive offers' is the same when user updates phone preferences");
		assertTrue(myAccountPage.isCheckboxContactPreferencesOptionByValueChecked(phonePreferencesOption), "'Call me with exlusive offers' isn't the same when user updates phone preferences as expected");
	}

	private void closeContactPreferencesModal() {
		Logger.info("Close Contact Preferences modal");
		myAccountPage.closeModal();
	}

	private void initTestCaseData() {
		Logger.tc("TC_MAP_003 - User Can Change Email And Update Email & Phone Preferences");
		Logger.to("TO_MAP_08 - Selected offer and rewards are the same when user update email preferences");
		Logger.to("TO_MAP_09 - Selected offer and rewards are the same when user update phone preferences");
		Logger.to("TO_MAP_10 - User can change email address successfully");
 		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop07@omahasteaks.com");
		newAccount.initRandomAccount();
		customerAddress.initRandomInformation();
		customerAddress.email = newAccount.getEmail();
	}
 
	private void signIn() {
		Logger.info("Login with the account (step2) has been created");
		generalPage.goToSignInPage();
		signInPage.signIn(account);
	}
}
