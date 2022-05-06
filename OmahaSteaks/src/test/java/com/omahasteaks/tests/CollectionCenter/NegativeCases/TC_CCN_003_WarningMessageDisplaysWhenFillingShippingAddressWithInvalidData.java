package com.omahasteaks.tests.CollectionCenter.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_CollectionCenter;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CCN_003_WarningMessageDisplaysWhenFillingShippingAddressWithInvalidData extends TestBase_CollectionCenter {

	CustomerAddress shippingAddress, invalidShippingAddress, addressNotMatch;

	String invalidAddress1, invalidPhoneNumber, invalidCity, invalidEmailFormat, invalidZipCode;

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;
	
	@Test
	public void TC_CCN_003_Warning_Message_Displays_When_Filling_Shipping_Address_With_Invalid_Data() {

		initTestCaseData();

		goToShippingInformationPage();

		fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields.PHONE, invalidPhoneNumber, Messages.INVALID_PHONE_MESSAGE);

		fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields.EMAIL, invalidEmailFormat, Messages.SORRY_INVALID_EMAIL_MESSAGE);

		fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields.ADDRESS1, Constants.INVALID_STREET, Messages.STREET_NOT_FOUND);

		fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields.ADDRESS1, invalidAddress1, Messages.INVALID_ADDRESS);

		fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields.ZIP_CODE, invalidZipCode, Messages.INVALID_ZIP_CODE_ERROR_MESSAGE);

		fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields.CITY, invalidCity, Messages.INVALID_CITY_STATE_ZIP);

		fillNotMatchZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.ZIP_CODE, addressNotMatch.zipCode, Messages.ZIPCODE_NOT_MATCH);

		fillNotMatchZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.CITY, addressNotMatch.city, Messages.CITY_NOT_MATCH);

		fillNotMatchZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.STATE, addressNotMatch.state, Messages.STATE_NOT_MATCH);

		fillNotMatchZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields.ADDRESS1, addressNotMatch.streetAddress1, Messages.ADDRESS_NOT_FOUND);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillNotMatchZipCodeStateCityStreetAndVerifyWarningMessage(AddressFields field, String valueUpdate, String expectedMessage) {
		invalidShippingAddress = shippingAddress.clone();
		invalidShippingAddress.updateFieldValueBy(field, valueUpdate);
		Logger.info("In Shipping Information page: " + "- Fill all valid information except " + field.getValue() + " does not match with each other fields.");
		collectionCenterPage.fillShippingInformation(invalidShippingAddress);

		Logger.verify("Verify warning message \" " + expectedMessage + " \" is displayed");
	}

	private void fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields field, String valueUpdate, String expectedMessage) {
		Logger.info("In Shipping Information page: " + "- Fill all valid information except " + field.getValue() + " field to be invalid");
		invalidShippingAddress = shippingAddress.clone();
		invalidShippingAddress.updateFieldValueBy(field, valueUpdate);
		collectionCenterPage.fillShippingInformation(invalidShippingAddress);
		collectionCenterPage.clickContinueButton();

		Logger.verify("Verify warning message \" " + expectedMessage + " \" is displayed");
		if (collectionCenterPage.getCurrentTabName().equals(Constants.TITLE_INFORMATION_OPTIONS)) {
			Common.goBack();
		} else {
			assertEquals(collectionCenterPage.getErrorMessageByField(field), expectedMessage, "Warning message \" " + expectedMessage + " \" is not  displayed");
		}
	}

	private void goToShippingInformationPage() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));

		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		collectionCenterPage.selectFirstSKU();
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCN_003 - Warning message displays when filling shipping address with invalid data.");
		Logger.to("TO_CCN_11 - In Shipping Information page: Warning message \"The phone number you specified is invalid. Please specify a phone number with the area code.\" displays when clicking \"Continue\" after filling invalid \"Phone\" field.");
		Logger.to("TO_CCN_12 - In Shipping Information page: Warning message \"Sorry, this is an invalid email address. Please try again.\" displays when clicking \"Continue\" after filling invalid email format  in 'Email Address' field.");
		Logger.to("TO_CCN_13 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value (address that only has street name, not has street number) in \"Address Line 1\" field (e.g. Prospect Rd).");
		Logger.to("TO_CCN_14 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value (an alphanumeric that doesn't have any correct street name ) in \"Address Line 1\" field (e.g. 12345abcdef).");
		Logger.to("TO_CCN_15 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value in \"City\" field (e.g. city123).");
		Logger.to("TO_CCN_16 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value in \"Zip\" field (e.g. zip9512).");
		Logger.to("TO_CCN_17 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling Address Line 1 does not match Zip, City, State, Country (e.g. State: California, Zip: 95129, City: San Jose, Country: United State, Address1: 105 Jay Street).");
		Logger.to("TO_CCN_18 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling City does not match Zip, State, Address Line 1, Country (e.g. State: California, Zip: 95129, City: Schenectady, Country: United State, Address Line 1: 5273 Prospect Rd).");
		Logger.to("TO_CCN_19 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling State does not match Zip, City, Address Line 1, Country (e.g. State: New York, Zip: 95129, City: San Jose, Country: United State, Address Line 1: 5273 Prospect Rd).");
		Logger.to("TO_CCN_20 - In Shipping Information page: Warning message displays when clicking \"Continue\" after filling Zip does not match City, State, Address Line 1, Country (e.g. State: California, ZipCode: 12345, City: San Jose, Country: United State, Address1: 5273 Prospect Rd).");

		shippingAddress = lstAddresses.getDefaultBillingAddress();
		addressNotMatch = lstAddresses.getDefaultShippingAddress();

		invalidAddress1 = "address" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 7);
		invalidPhoneNumber = "phone" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 5);
		invalidCity = "city" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 3);
		invalidEmailFormat = "email" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 5);
		invalidZipCode = "zip" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 3);
	}
}
