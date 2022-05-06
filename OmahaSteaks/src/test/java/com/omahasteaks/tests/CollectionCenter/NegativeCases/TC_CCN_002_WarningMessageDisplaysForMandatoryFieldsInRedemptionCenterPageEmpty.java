package com.omahasteaks.tests.CollectionCenter.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_CollectionCenter;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CCN_002_WarningMessageDisplaysForMandatoryFieldsInRedemptionCenterPageEmpty extends TestBase_CollectionCenter {
	String dateTime;

	@Inject
	CustomerAddress address, invalidAddress;

	String invalidCollectionNumber;

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	GeneralPage generalPage;
	
	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CCN_002_Warning_Message_Displays_For_Mandatory_Fields_In_Redemption_Center_Page_Empty() {
		initTestCaseData();

		enterInvalidCollectionNumber();

		verifyWarningMessageDisplaysWhenFillingInvalidCollectionNumber();

		leavingCollectionNumberFieldEmpty();

		verifyWarningMessageDisplaysWhenLeavingCollectionNumberFieldEmpty();

		fillValidCollectionNumberAndSelectSKU();

		leaveShippingInformationEmptyAndClickContinue();

		verifyWarningMessageDisplaysCorrectlyInShippingInformationPage();

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.FIRST_NAME);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.LAST_NAME);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.COMPANY_NAME);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.ADDRESS1);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.STATE);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.CITY);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.ZIP_CODE);

		fillValidInformationIntoShippingInformationPageAndClickContinue();

		fillInvalidFormatDateAndClickContinue();

		verifyWarningMessageDisplaysWhenFillingInvalidFormatDate();

		fillDateAfterMoreThan365DaysAndClickContinue();

		verifyWarningMessageDisplaysWhenFillingADateAfterMoreThan365Days();

		fillDateInThePastAndClickContinue();

		verifyWarningMessageDisplaysWhenFillingADateOfToday();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillValidCollectionNumberAndSelectSKU() {
		enterValidCollectionNumber();

		selectFirstSKU();
	}

	private void fillDateInThePastAndClickContinue() {
		Logger.info("In Shipping Option page, filling a date in the past in \"Delayed Arrival Date\"" + "- Click \"Continue\"");
		fillDelayedArrivalDateAndClickContinue(Common.randomNewDate("MM/dd/yyyy", -1));
	}

	private void fillDelayedArrivalDateAndClickContinue(String date) {
		collectionCenterPage.inputDelayedArrivalDate(date);
		collectionCenterPage.clickContinueButton();
	}

	private void verifyWarningMessageDisplaysWhenFillingADateOfToday() {
		Logger.verify("Verify warning message \"Requested Date Can't arrange a shipment date in the past\" displays");
		assertEquals(collectionCenterPage.getErrorgMessageInShippingOptions(), Messages.REQUESTED_DATE_BEFORE_TODAY_MESSAGE, "Warning message \"Requested Date Can't arrange a shipment date in the past\" does not display");
	}

	private void verifyWarningMessageDisplaysWhenFillingADateAfterMoreThan365Days() {
		Logger.verify("Verify warning message \"Requested Date You can't delay your shipment more than 365 days.\" displays");
		assertEquals(collectionCenterPage.getErrorgMessageInShippingOptions(), Messages.REQUESTED_DATE_MORE_THAN_365_DAYS_MESSAGE, "Warning message \"Requested Date You can't delay your shipment more than 365 days.\" does not display");
	}

	private void fillDateAfterMoreThan365DaysAndClickContinue() {
		Logger.info("In Shipping Option page, filling a date after more than 365 days in \"Delayed Arrival Date\"" + "- Click \"Continue\"");
		fillDelayedArrivalDateAndClickContinue(Common.randomNewDate("MM/dd/yyyy", 366));
	}

	private void verifyWarningMessageDisplaysWhenFillingInvalidFormatDate() {
		Logger.verify("Verify warning message \"Requested Date invalid format\" displays");
		assertEquals(collectionCenterPage.getErrorgMessageInShippingOptions(), Messages.INVALID_REQUESTED_DATE_MESSAGE, "Warning message \"Requested Date invalid format\" does not display");
	}

	private void fillInvalidFormatDateAndClickContinue() {
		Logger.info("In Shipping Options page, filling invalid format date in \"Delayed Arrival Date\"" + "- Click \"Continue\"");
		fillDelayedArrivalDateAndClickContinue(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 8));
	}

	private void fillValidInformationIntoShippingInformationPageAndClickContinue() {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all mandatory" + "- Click \"Continue\" button");
		collectionCenterPage.selectAddressType(AddressType.RESIDENTIAL_ADDRESS);
		collectionCenterPage.fillShippingInformation(address);
		collectionCenterPage.clickContinueButton();
	}

	private void clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields field) {
		Logger.info("In Shipping Information page: Clicking to underlined value in warning message of " + field.getValue());
		collectionCenterPage.clickOnAddressFieldLinkInListErrorMessage(field);

		Logger.verify("Verify the cursor moves to error field " + field.getValue());
		assertTrue(collectionCenterPage.isAddressFieldFocused(field), "The cursor does not move to error field " + field.getValue());
	}

	private void verifyWarningMessageDisplaysCorrectlyInShippingInformationPage() {
		Logger.verify("\"Verify a warning message with below information:" + "Please -- it's a required field" + "Ok -- we can't deliver without a last name - it's a required field." + "A company name is required." + "Address for delivery -- it's a must! Please provide." + "City, State and Zip code... all required fields.");
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getRequiredMessageByField(AddressFields.FIRST_NAME));
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getRequiredMessageByField(AddressFields.LAST_NAME));
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.COMPANY_NAME), Messages.getRequiredMessageByField(AddressFields.COMPANY_NAME));
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.ADDRESS1), Messages.getRequiredMessageByField(AddressFields.ADDRESS1));
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.ZIP_CODE), Messages.getRequiredMessageByField(AddressFields.ZIP_CODE));
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.STATE), Messages.getRequiredMessageByField(AddressFields.STATE));
		assertEquals(collectionCenterPage.getErrorMessageByField(AddressFields.CITY), Messages.getRequiredMessageByField(AddressFields.CITY));
	}

	private void leaveShippingInformationEmptyAndClickContinue() {
		Logger.info("In Shipping Information page:" + "-  Leave every fields empty (if there are any fields having default value, then delete them excluding Country field)n" + "- Click \"CONTINUE\"");
		collectionCenterPage.selectAddressType(AddressType.COMPANY_ADDRESS);
		collectionCenterPage.fillShippingInformation(invalidAddress);
		collectionCenterPage.clickContinueButton();
	}

	private void selectFirstSKU() {
		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		collectionCenterPage.selectFirstSKU();
	}

	private void enterValidCollectionNumber() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));
	}

	private void verifyWarningMessageDisplaysWhenLeavingCollectionNumberFieldEmpty() {
		Logger.verify("Verify warning message displays when entering invalid Redemption number and click Redeem.");
		assertEquals(Common.getTextAndCloseAlert(), "PreapplyCertError", "Alert message does not display when leaving \"Redemption number\" field empty");
	}

	private void leavingCollectionNumberFieldEmpty() {
		Logger.info("From Homepage: leaving \"Redemption number\" field empty and click 'REDEEM' button.");
		invalidCollectionNumber = "";
		if (Common.getCurrrentBrowser().equals(Constants.BROWSER_IE)) {
			DriverUtils.navigate(Constants.COLLECTTION_CENTER_URL);
			Common.waitForPageLoad();
		}
		collectionCenterPage.submitCollectionNumber(invalidCollectionNumber);
	}

	private void verifyWarningMessageDisplaysWhenFillingInvalidCollectionNumber() {
		Logger.verify("Verify warning message displays when entering invalid Redemption number and click Redeem.");
		assertEquals(Common.getTextAndCloseAlert(), "Account " + invalidCollectionNumber.toUpperCase() + " is not valid (0102). Please double-check your entry and try again.", "Alert message does not display when filling invalid  Redemption number");
	}

	private void enterInvalidCollectionNumber() {
		Logger.info("From Homepage: Enter invalid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(invalidCollectionNumber);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCN_002 Warning message displays for mandatory field in Redemption Center page empty");
		Logger.to("TO_CCN_01 - In Redemption Center home page: Warning message displays when entering invalid Redemption number and click Redeem.");
		Logger.to("TO_CCN_02 - In Redemption Center home page: Warning message displays when leaving blank Redemption number and click Redeem.");
		Logger.to("TO_CCN_03 - In Shipping Information page: Warning message with the list of missing fields displays when clicking \"Continue\" without filling all required fields.");
		Logger.to("TO_CCN_10 - In Shipping Information page: The cursor moves to error field correctly when clicking to underlined value in warning message.");
		Logger.to("TO_CCN_21 - In Shipping Options page: Warning message \"Requested Date invalid format\" displays when clicking \"Continue\" after filling invalid format date in \"Delayed Arrival Date\".");
		Logger.to("TO_CCN_22 - In Shipping Options page: Warning message \"Requested Date You can't delay your shipment more than 365 days.\" displays when clicking \"Continue\" after filling a date after more than 365 days compared to Approximate Arrival Date in \"Delayed Arrival Date\".");
		Logger.to("TO_CCN_23 - In Shipping Options page: Warning message \"Requested Date Can't arrange a shipment date in the past\" displays when clicking \"Continue\" after filling a date in the past in \"Delayed Arrival Date\".");
		address = lstAddresses.getDefaultShippingAddress();
		address.removeCompanyAddressNotRequiredFields();
		invalidCollectionNumber = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 8);
		invalidAddress.setRandomEmail();
		invalidAddress.setStreetAdress1("");
	}
}
