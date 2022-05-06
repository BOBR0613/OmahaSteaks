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

public class TC_MAN_008_WarningMessageDisplaysWhenEditingShippingAddressForRecipientWithInvalidData extends TestBase_2SC {
	String invalidAddress1;
	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Inject
	CustomerAddress shippingAddress, invalidShippingAddress, address;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_MAN_008_Warning_Message_Displays_When_Editing_Shipping_Address_For_Recipient_With_Invalid_Data() {

		initTestCaseData();

		signIn();

		gotoAddressBookAndClickEditShippingAddress();

		fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.ZIP_CODE, address.zipCode, Messages.INVALID_CITY_STATE_ZIP);

		fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.ADDRESS1, address.streetAddress1, Messages.ADDRESS_NOT_FOUND);

		fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.CITY, address.city, Messages.CITY_NOT_MATCH);

		fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.STATE, address.state, Messages.STATE_NOT_MATCH);

		fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.ADDRESS1, Constants.INVALID_STREET, Messages.STREET_NOT_FOUND);

		fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.ADDRESS1, invalidAddress1, Messages.INVALID_ADDRESS);
	}

	
	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void fillInvalidZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields field, String valueUpdate, String expectedMessage) {
		Logger.info("In Edit Shipping Address page (of My Account Page ): " + "\n- Fill all valid information " + "- \nEdit " + field + "to be invalid");
		invalidShippingAddress = shippingAddress.clone();
		invalidShippingAddress.updateFieldValueBy(field, valueUpdate);
		myAccountPage.fillShippingAddress(invalidShippingAddress);

		Logger.verify("Verify warning message \" " + expectedMessage + " \" is displayed");
		assertEquals(myAccountPage.getAddressWarningMessage(), expectedMessage, "Warning message " + expectedMessage + " is not displayed as expected");
	}

	private void gotoAddressBookAndClickEditShippingAddress() {
		Logger.info("In Homepage : " + "- Click on \"My Account\" link" + "- Click on \"Address Book\" link on left navigation menu" + "- Click \"Edit\" button in the Shipping Address section");
		generalPage.goToMyAccountPage();
		myAccountPage.goToMyAccountAddressBook();
		myAccountPage.clickCreateNewAddressButton();
	}
 

	private void initTestCaseData() {
		Logger.tc("TC_MAN_008 - Warning message displays for invalid data for address fields in edit Shipping Address page for Recipient");
		Logger.to("TO_MAN_52 - Warning message \"State does not match USPS database (California)\" is displayed when filling State does not match Zip Code, City, Address1, Country in Edit Shipping address page for Recipient (e.g. State: New York, ZipCode: 95129, City: San Jose, Country: United State, Address1: 5273 Prospect Rd)");
		Logger.to("TO_MAN_53 - Warning message \"City does not match USPS database (Schenectady)\" is displayed when filling City does not match Zip Code, State, Address1, Country in Edit Shipping address page for Recipient (e.g. State: California, ZipCode: 95129, City:Schenectady, Country: United State, Address1: 5273 Prospect Rd)");
		Logger.to("TO_MAN_54 - Warning message \"Invalid city/state/zip information\" is displayed when filling Zip Code does not match City, State, Address1, Country in Edit Shipping address page for Recipient (e.g. State: California, ZipCode: 12345, City: San Jose, Country: United State, Address1: 5273 Prospect Rd)");
		Logger.to("TO_MAN_55 - Warning message \"Address not found on street\" is displayed when filling Address1 does not match ZipCode, City, State, Country in Edit Shipping address page for Recipient (e.g. State: California, ZipCode: 95129, City: San Jose, Country: United State, Address1: 105 Jay Street)");
		Logger.to("TO_MAN_56 - Warning message \"Street name not found\" is displayed when filling invalid address into Address1 field in Edit Shipping address page for Recipient (e.g. State: California, ZipCode: 95129, City: San Jose, Country: United State, Address1:Prospect Rd)");
		Logger.to("TO_MAN_57 - Warning message \"Insufficient address information\" is displayed when filling invalid value into Address1 field in Edit Shipping address page for Recipient (e.g. State: California, ZipCode: 95129, City: San Jose, Country: United State, Address1:12345abcdef)");
		account = Constants.LIST_ACCOUNTS.getRandomAccount();
		newAccount.initRandomAccount();
		newAccount.setEmail(account.getEmail());
		newAccount.setPassword(account.getPassword());
		shippingAddress = lstAddresses.getDefaultBillingAddress();
		address = lstAddresses.getDefaultShippingAddress();
		invalidAddress1 = Common.getRandomString("");
	}

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}
}
