package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_003_WarningMessageDisplaysWhenFillingShippingAddressWithInvalidData extends TestBase_CR {
	CustomerAddress shippingAddress, invalidShippingAddress, addressNotMatch;

	String invalidAddress1, invalidCity, invalidZipCode;

	@Inject
	RewardSKU mySKU;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRN_003_Warning_Message_Displays_When_Filling_Shipping_Address_With_Invalid_Data() {
		initTestCaseData();

		searchAndAddSKU9ToCart(mySKU);
		searchAndAddSKU9ToCart(mySKU);

		fillRewardNumberAndCheckOut();

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
		rewardShippingInfoPage.fillShippingInformation(invalidShippingAddress);

		Logger.verify("Verify warning message \" " + expectedMessage + " \" is displayed");
	}

	private void fillInvalidDataInEachFieldAndVerifyWarningMessage(AddressFields field, String valueUpdate, String expectedMessage) {
		Logger.info("In Shipping Information page: " + "- Fill all valid information except " + field.getValue() + " field to be invalid");
		invalidShippingAddress = shippingAddress.clone();
		invalidShippingAddress.updateFieldValueBy(field, valueUpdate);
		rewardShippingInfoPage.fillShippingInformation(invalidShippingAddress);
		rewardShippingInfoPage.clickContinueLink();

		Logger.verify("Verify warning message \" " + expectedMessage + " \" is displayed");
		if (rewardShippingInfoPage.getCurrentTabName().equals(Constants.TITLE_INFORMATION_OPTIONS)) {
			Common.goBack();
		} else {
			assertEquals(rewardShippingInfoPage.getErrorMessageByField(field), expectedMessage, "Warning message \" " + expectedMessage + " \" is not  displayed");
		}
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_003 Warning message displays when filling shipping address with invalid data");
		Logger.to("TO_CRN_016	In Shipping Information page: Warning message \"The phone number you specified is invalid. Please specify a phone number with the area code.\" displays when clicking \"Continue\" after filling invalid \"Phone\" field");
		Logger.to("TO_CRN_017	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value (address that only has street name, not has street number) in \"Address Line 1\" field (e.g. Prospect Rd)");
		Logger.to("TO_CRN_018	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value (an alphanumeric that doesn't have any correct street name ) in \"Address Line 1\" field (e.g. 12345abcdef)");
		Logger.to("TO_CRN_019	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value in \"City\" field (e.g. 123abc)");
		Logger.to("TO_CRN_020	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling invalid value in \"Zip\" field (e.g. 9512)");
		Logger.to("TO_CRN_021	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling Address Line 1 does not match Zip, City, State, Country (e.g. State: California, Zip: 95129, City: San Jose, Country: United State, Address1: 105 Jay Street)");
		Logger.to("TO_CRN_022	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling City does not match Zip, State, Address Line 1, Country (e.g. State: California, Zip: 95129, City: Schenectady, Country: United State, Address Line 1: 5273 Prospect Rd)");
		Logger.to("TO_CRN_023	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling State does not match Zip, City, Address Line 1, Country (e.g. State: New York, Zip: 95129, City: San Jose, Country: United State, Address Line 1: 5273 Prospect Rd)");
		Logger.to("TO_CRN_024	In Shipping Information page: Warning message displays when clicking \"Continue\" after filling Zip does not match City, State, Address Line 1, Country (e.g. State: California, ZipCode: 12345, City: San Jose, Country: United State, Address1: 5273 Prospect Rd)");
		mySKU.init(Recipient.MYSELF, 10);
		shippingAddress = lstAddresses.getDefaultBillingAddress();
		addressNotMatch = lstAddresses.getDefaultShippingAddress();

		invalidAddress1 = "address" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 7);
		invalidCity = "city" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 3);
		invalidZipCode = "zip" + Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 3);

	}
}
