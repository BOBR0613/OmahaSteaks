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

public class TC_MAN_007_EditShippingAddressForRecipientWithEmptyEachMandatoryField extends TestBase_2SC {

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Inject
	CustomerAddress shippingAddress, invalidAddress;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_MAN_007_Edit_Shipping_Address_For_Recipient_With_Empty_Each_Mandatory_Field() {

		initTestCaseData();

		signIn();

		gotoAddressBookAndClickEditShippingAddressForRecipient();

//		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ZIP_CODE);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.FIRST_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.LAST_NAME);

		selectComanyAddressRadioButtonInEditShippingAddress();

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
		Logger.info("In Edit Shipping Address page:" + "- Fill one character value into Last name field" + "- Fill valid all information" + "- Click \"Update\" button");
		invalidAddress = shippingAddress.clone();
		invalidAddress.updateFieldValueBy(AddressFields.LAST_NAME, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 1));
		myAccountPage.fillShippingAddress(invalidAddress);
		myAccountPage.clickUpdateButton();
		Common.waitForDOMChange();
	}

	private void fillInvalidValueIntoPhoneNumberandClickUpdate() {
		Logger.info("In Edit Shipping Address page:" + "- Fill invalid value into Phone Number" + "- Fill valid all information" + "- Click \"Update\" button");
		invalidAddress = shippingAddress.clone();
		invalidAddress.updateFieldValueBy(AddressFields.PHONE, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 10));
		myAccountPage.fillShippingAddress(invalidAddress);
		myAccountPage.clickUpdateButton();
		Common.waitForDOMChange();
	}

	private void verifyErrorMessageDisplaysWhenFillingOneCharacterIntoLastNameField() {
		Logger.verify("The warning message is displayed:\r\n" + "\"Ok -- we can't deliver without a last name - it's a required field.\"");
		assertEquals(myAccountPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getRequiredMessageByField(AddressFields.LAST_NAME), "Warning message " + Messages.getRequiredMessageByField(AddressFields.LAST_NAME) + " is not displayed as expected");
	}

	private void verifyErrorMessageDisplaysWhenFillingInvalidPhoneNumber() {
		Logger.verify("The warning message is displayed:\r\n" + "\"The phone number you specified is invalid. Please specify a phone number with the area code.\"");
		assertEquals(myAccountPage.getErrorMessageByField(AddressFields.PHONE), Messages.getInvalidMessageByField(AddressFields.PHONE), "Error message " + Messages.getInvalidMessageByField(AddressFields.PHONE) + " is not displayed as expected");
	}

	private void selectComanyAddressRadioButtonInEditShippingAddress() {
		Logger.info("In Edit Shipping Address page:" + "- Click \"Company Address\" radio button");
		myAccountPage.selectTypeAddress(Constants.COMPANY_ADDRESS);
	}

	private void gotoAddressBookAndClickEditShippingAddressForRecipient() {
		Logger.info("In Homepage : " + "- Click on \"My Account\" link" + "- Click on \"Address Book\" link on left navigation menu" + "- Click \"Edit\" button in the Shipping Address section");
		generalPage.goToMyAccountPage();
		myAccountPage.goToMyAccountAddressBook();
		myAccountPage.clickCreateNewAddressButton();
		Common.waitForPageLoad();
	}

	private void initTestCaseData() {
		Logger.tc("TC_MAN_007 - Error message displays for each mandatory field empty in Edit Shipping Address page for recipient");
		Logger.to("TO_MAN_43 - In Edit Shipping Address page, warning message \"Please -- it's a required field\" is displayed for blank \"First name\" field for Receipent");
		Logger.to("TO_MAN_44 - In Edit Shipping Address page, warning message \"Ok -- we can't deliver without a last name - it's a required field.\" is displayed for blank \"Last name\" field for Recipient");
		Logger.to("TO_MAN_45 - In Edit Shipping Address page, warning message \"Address for delivery -- it's a must! Please provide.\" is displayed for blank \"Address1\" field for Recipient");
		Logger.to("TO_MAN_46 - In Edit Shipping Address page, warning message \"City, State and Zip code... all required fields.\" is displayed for blank \"City\" field for Recipient");
		Logger.to("TO_MAN_47 - In Edit Shipping Address page, warning message \"City, State and Zip code... all required fields.\" is displayed for blank \"Zip Code\" field for Recipient");
		Logger.to("TO_MAN_48 - In Edit Shipping Address page, warning message \"City, State and Zip code... all required fields.\" is displayed for blank \"State\" field for Recipient");
		Logger.to("TO_MAN_49 - In Edit Shipping Address page, warning message \"A company name is required.\" is displayed for blank \"Company name\" field for Recipient");
		Logger.to("TO_MAN_50 - In Edit Shipping Address page, warning message \"The zip code you specified is invalid.\" is displayed when filling invalid value into \"Zip Code\" field for Recipient");
		Logger.to("TO_MAN_51 - Warning message is displayed when Last Name contains one character in \"My Account - Edit Shipping Address\" Page for Recipient");
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop01@omahasteaks.com");
		newAccount.initRandomAccount();
		newAccount.setEmail(account.getEmail());
		newAccount.setPassword(account.getPassword());
		shippingAddress.initRandomInformation();
		shippingAddress.zipCode = "CA" + shippingAddress.zipCode;
	}

	private void leaveFieldEmptyAndVerifyErrorMessage(AddressFields field) {
		Logger.info("In Shipping Address form: \n" + "- Leave fields " + field + "- Click \"Update\"");
		myAccountPage.fillShippingAddressExceptField(shippingAddress, field);
		myAccountPage.clickUpdateButton();
		Common.waitForDOMChange();
		System.out.println("Verify a warning message with below information:\n" + Messages.getRequiredMessageByField(field) + " is displayed");
		assertEquals(myAccountPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field), " Warning message " + Messages.getRequiredMessageByField(field) + " is not displayed as expected");
	}

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}

}
