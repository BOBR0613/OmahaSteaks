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

public class TC_MAN_004_WarningMessageDisplaysForInvalidDataForAddressFieldsInEditBillingAddressPage extends TestBase_2SC {
	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Inject
	CustomerAddress billingAddress, invalidBillingAddress;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_MAN_004_Warning_Message_Displays_For_Invalid_Data_For_Address_Fields_In_Edit_Billing_Address_Page() {

		initTestCaseData();

		signIn();

		gotoAddressBookAndClickEditBillingAddress();

		fillInvalidZipCodeStateCityStreetAndVerifyMessage();
 	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillInvalidZipCodeStateCityStreetAndVerifyMessage() {
		// Verify Invalid city/state/zip information
		Logger.info("In Edit Billing Address page (of My Account Page ): " + "- Fill all valid information expect Zip Code" + "- Uncheck \"Check if Shipping address is the same as the Billing address \" checkbox" + "- Click on \"Update\" button");
		invalidBillingAddress = billingAddress.clone();
		invalidBillingAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "12345");
		myAccountPage.fillBillingAddress(invalidBillingAddress);

		Logger.verify("Verify warning message \"Invalid city/state/zip information\" is displayed");
		assertEquals(myAccountPage.getAddressWarningMessage(), Messages.INVALID_CITY_STATE_ZIP);

		// Verify "Address not found on street"
		Logger.info("- Fill valid Zip Code" + "- Edit Address1 to be invalid");
		invalidBillingAddress = billingAddress.clone();
		invalidBillingAddress.updateFieldValueBy(AddressFields.ADDRESS1, "105 Jay St");
		myAccountPage.fillBillingAddress(invalidBillingAddress);

		Logger.verify("Verify a warning message with below information:\n" + "\"Address not found on street\"");
		assertEquals(myAccountPage.getAddressWarningMessage(), Messages.ADDRESS_NOT_FOUND);

		// Verify warning message "City does not match USPS database (San Jose)"
		Logger.info("- Fill valid Address1" + "- Edit City to be invalid");
		invalidBillingAddress = billingAddress.clone();
		invalidBillingAddress.updateFieldValueBy(AddressFields.CITY, "Schenectady");
		myAccountPage.fillBillingAddress(invalidBillingAddress);

		Logger.verify("Verify warning message \"City does not match USPS database (San Jose)\" is displayed");
		assertEquals(myAccountPage.getAddressWarningMessage(), Messages.CITY_NOT_MATCH);

		// Verify warning message "State does not match USPS database (CA)"
		Logger.info("- Fill valid City" + "- Edit State to be invalid");
		invalidBillingAddress = billingAddress.clone();
		invalidBillingAddress.updateFieldValueBy(AddressFields.STATE, "New York");
		myAccountPage.fillBillingAddress(invalidBillingAddress);

		Logger.verify("Verify warning message \"State does not match USPS database (CA)\" is displayed");
		assertEquals(myAccountPage.getAddressWarningMessage(), Messages.STATE_NOT_MATCH);

		// Verify warning message "Street not found in city"
		Logger.info("- Fill valid State" + "- Edit Address1 to be invalid");
		invalidBillingAddress = billingAddress.clone();
		invalidBillingAddress.updateFieldValueBy(AddressFields.ADDRESS1, "Prospect Rd");
		myAccountPage.fillBillingAddress(invalidBillingAddress);

		Logger.verify("Verify warning message \"Street name not found\" is displayed");
		assertEquals(myAccountPage.getAddressWarningMessage(), Messages.STREET_NOT_FOUND);

		// Verify warning message "Insufficient address information"
		Logger.info("'- Edit Address1 to be invalid");
		invalidBillingAddress = billingAddress.clone();
		invalidBillingAddress.updateFieldValueBy(AddressFields.ADDRESS1, "12345abcdef");
		myAccountPage.fillBillingAddress(invalidBillingAddress);

		Logger.verify("Verify warning message \"Insufficient address information\" is displayed");
		assertEquals(myAccountPage.getAddressWarningMessage(), Messages.INVALID_ADDRESS);
	}

	private void gotoAddressBookAndClickEditBillingAddress() {
		Logger.info("In Homepage : " + "- Click on \"My Account\" link" + "- Click on \"Address Book\" link on left navigation menu" + "- Click \"Edit\" button in the Billing Address section");
		generalPage.goToMyAccountPage();
		myAccountPage.goToMyAccountAddressBook();
		Common.waitForPageLoad();
		myAccountPage.goToEditBillingAddressPage();
		Common.waitForPageLoad();
	}


	private void initTestCaseData() {
		Logger.tc("TC_MAN_004 - Warning message displays for invalid data for address fields in Edit Billing Address Page");
		Logger.to("TO_MAN_21 - Warning message \"State does not match USPS database (California)\" is displayed when filling State does not match Zip Code, City, Address1, Country in Edit Billing address page (e.g. State: New York, ZipCode: 95129, City: San Jose, Country: United State, Address1: 5273 Prospect Rd)");
		Logger.to("TO_MAN_22 - Warning message \"City does not match USPS database (San Jose)\" is displayed when filling City does not match Zip Code, State, Address1, Country in Edit Billing address page (e.g. State: California, ZipCode: 95129, City:Schenectady, Country: United State, Address1: 5273 Prospect Rd)");
		Logger.to("TO_MAN_23 - Warning message \"Invalid city/state/zip information\" is displayed when filling Zip Code does not match City, State, Address1, Country in Edit Billing address page  (e.g. State: California, ZipCode: 12345, City: San Jose, Country: United State, Address1: 5273 Prospect Rd)");
		Logger.to("TO_MAN_24 - Warning message \"Address not found on street\" is displayed when filling Address1 does not match ZipCode, City, State, Country in Edit Billing address page  (e.g. State: California, ZipCode: 95129, City: San Jose, Country: United State, Address1: 105 Jay Street)");
		Logger.to("TO_MAN_25 - Warning message \"Street name not found\" is displayed when filling invalid address into Address1 field in Edit Billing address page  (e.g. State: California, ZipCode: 95129, City: San Jose, Country: United State, Address1:Prospect Rd)");
		Logger.to("TO_MAN_26 - Warning message \"Insufficient address information\" is displayed when filling invalid value into Address1 field in Edit Billing address page  (e.g. State: California, ZipCode: 95129, City: San Jose, Country: United State, Address1:12345abcdef)");
		newAccount.initRandomAccount();
		billingAddress = lstAddresses.getDefaultBillingAddress();
		account = Constants.LIST_ACCOUNTS.getRandomAccount();
		newAccount.setEmail(account.getEmail());
		newAccount.setPassword(account.getPassword());
	}

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}
}
