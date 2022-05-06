package com.omahasteaks.tests.MembershipAccount.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
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

public class TC_MAN_003_ErrorMessageDisplaysForEachMandatoryFieldEmptyInEditBillingAddressPage extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    SignInPage signInPage;

    @Inject
    MyAccountPage myAccountPage;

    @Inject
    Account myAccount;

    @Inject
    CustomerAddress billingAddress, invalidAddress;

    @Inject
    ListAddresses lstAddresses;

    @Test
    public void TC_MAN_003_Error_Message_Displays_For_Each_Mandatory_Field_Empty_In_Edit_Billing_Address_Page() {

	initTestCaseData();

	signIn();

	gotoAddressBookAndClickEditBillingAddress();

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ZIP_CODE);

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.FIRST_NAME);

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.LAST_NAME);

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.PHONE);

	selectComanyAddressRadioButtonInEditBillingAddress();

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.COMPANY_NAME);

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ADDRESS1);

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.STATE);

	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.CITY);

	fillInvalidValueIntoPhoneNumberandClickUpdate();

	verifyErrorMessageDisplaysWhenFillingInvalidPhoneNumber();

	fillOneCharacterValueIntoLastNameFieldAndClickUpdate();

	verifyErrorMessageDisplaysWhenFillingOneCharacterIntoLastNameField();
 
    }
   

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void fillOneCharacterValueIntoLastNameFieldAndClickUpdate() {
	Logger.info("In Edit Billing Address page:" + "- Fill one character value into Last name field" + "- Fill valid all information" + "- Click \"Update\" button");
	invalidAddress = billingAddress.clone();
	invalidAddress.updateFieldValueBy(AddressFields.LAST_NAME, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 1));
	myAccountPage.fillBillingAddress(invalidAddress);
	myAccountPage.clickUpdateButton();
    }

    private void fillInvalidValueIntoPhoneNumberandClickUpdate() {
	Logger.info("In Edit Billing Address page:" + "- Fill invalid value into Phone Number" + "- Fill valid all information" + "- Click \"Update\" button");
	invalidAddress = billingAddress.clone();
	invalidAddress.updateFieldValueBy(AddressFields.PHONE, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 10));
	myAccountPage.fillBillingAddress(invalidAddress);
	myAccountPage.clickUpdateButton();
    }

    private void verifyErrorMessageDisplaysWhenFillingOneCharacterIntoLastNameField() {
	Logger.verify("The warning message is displayed:\r\n" + "\"Ok -- we can't deliver without a last name - it's a required field.\"");
	assertEquals(myAccountPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getRequiredMessageByField(AddressFields.LAST_NAME));
    }

    private void verifyErrorMessageDisplaysWhenFillingInvalidPhoneNumber() {
	Logger.verify("The warning message is displayed:\r\n" + "\"The phone number you specified is invalid. Please specify a phone number with the area code.\"");
	assertEquals(myAccountPage.getErrorMessageByField(AddressFields.PHONE), Messages.getInvalidMessageByField(AddressFields.PHONE));
    }

    private void selectComanyAddressRadioButtonInEditBillingAddress() {
	Logger.info("In Edit Billing Address page:" + "- Click \"Company Address\" radio button");
	myAccountPage.selectTypeAddress(Constants.COMPANY_ADDRESS);
    }

    private void gotoAddressBookAndClickEditBillingAddress() {
	Logger.info("In Homepage : " + "- Click on \"My Account\" link" + "- Click on \"Address Book\" link on left navigation menu" + "- Click \"Edit\" button in the Billing Address section");
	generalPage.goToMyAccountPage();
	myAccountPage.goToMyAccountAddressBook();
	myAccountPage.goToEditBillingAddressPage();
    }


    private void initTestCaseData() {
	Logger.tc("TC_MAN_003 - Error message displays for each mandatory filed empty in Edit Billing Address page");
	Logger.to("TO_MAN_09 - In Edit Billing Address page, warning message \"Please -- it's a required field\" is displayed for blank \"First name\" field\r\n");
	Logger.to("TO_MAN_10 - In Edit Billing Address page, warning message \"Ok -- we can't deliver without a last name - it's a required field.\" is displayed for blank \"Last name\" field\r\n");
	Logger.to("TO_MAN_11 - In Edit Billing Address page, warning message \"Address for delivery -- it's a must! Please provide.\" is displayed for blank \"Address1\" field\r\n");
	Logger.to("TO_MAN_12 - In Edit Billing Address page, warning message \"City, State and Zip code... all required fields.\" is displayed for blank \"City\" field\r\n");
	Logger.to("TO_MAN_13 - In Edit Billing Address page, warning message \"City, State and Zip code... all required fields.\" is displayed for blank \"Zip Code\" field\r\n");
	Logger.to("TO_MAN_14 - In Edit Billing Address page, warning message \"City, State and Zip code... all required fields.\" is displayed for blank \"State\" field\r\n");
	Logger.to("TO_MAN_15 - In Edit Billing Address page, warning message \"Just in case we need to speak with you about your order or some fantastic offers, please provide the phone number you'd like us to use.\" is displayed for blank \"Phone Required\" field\r\n");
	Logger.to("TO_MAN_16 - In Edit Billing Address page, warning message \"A company name is required.\" is displayed for blank \"Company name\" field\r\n");
	Logger.to("TO_MAN_17 - In Edit Billing Address page, warning message \"The zip code you specified is invalid.\" is displayed when filling invalid value into \"Zip Code\" field\r\n");
	Logger.to("TO_MAN_18 - In Edit Billing Address page, warning message \"The phone number you specified is invalid. Please specify a phone number with the area code.\" is displayed when filling invalid phone number into \"Phone Number\" field\r\n");
	Logger.to("TO_MAN_19 - In Edit Billing Address page, warning message \"The zip code you specified is invalid.\" is displayed when filling invalid zip code into \"Zip Code\" field\r\n");
	Logger.to("TO_MAN_20 - Warning message is displayed when Last Name contains one character in \"My Account - Edit Billing Address\" Page\r\n");
	account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop07@omahasteaks.com");
	myAccount.initRandomAccount();
	myAccount.setEmail(account.getEmail());
	myAccount.setPassword(account.getPassword());
	billingAddress.initRandomInformation();
	billingAddress.zipCode = "CA" + billingAddress.zipCode;
    }

    private void leaveFieldEmptyAndVerifyErrorMessage(AddressFields field) {
	Logger.info("In Billing Address form: \n" + "- Leave fields " + field + " empty (if there are any fields having default value, then delete them excluding Country field) \n" + "- Click \"CONTINUE CHECKOUT\"");
	myAccountPage.fillBillingAddressExceptField(billingAddress, field);
	myAccountPage.clickUpdateButton();
	
	Logger.verify("Verify a warning message with below information:\n" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
	assertEquals(myAccountPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field), field.getValue());
    }

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}
}
