package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_004_WarningMessageDisplaysWhenRequestedDateDoesNotApply extends TestBase_CR {
	CustomerAddress address;

	@Inject
	RewardSKU mySKU;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRN_004_Warning_Message_Displays_When_Requested_Date_Does_Not_Apply() {
		initTestCaseData();

		searchAndAddSKU9ToCart(mySKU);
		searchAndAddSKU9ToCart(mySKU);

		fillRewardNumberAndCheckOut();

		fillResidentialAddressAndClickContinue(address);

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
	private void verifyWarningMessageDisplaysWhenFillingADateOfToday() {
		Logger.verify("Verify warning message \"Requested Date Can't arrange a shipment date in the past\" displays");
		assertEquals(rewardShippingOptionPage.getErrorgMessageInShippingOptions(), Messages.REQUESTED_DATE_BEFORE_TODAY_MESSAGE, "Warning message \"Requested Date Can't arrange a shipment date in the past\" does not display");
	}

	private void fillDateInThePastAndClickContinue() {
		Logger.info("In Shipping Option page, filling a date in the past in \"Delayed Arrival Date\"" + "- Click \"Continue\"");
		fillDelayedArrivalDateAndClickContinueLink(Common.randomNewDate("MM/dd/yyyy", -1));
	}

	private void verifyWarningMessageDisplaysWhenFillingADateAfterMoreThan365Days() {
		Logger.verify("Verify warning message \"Requested Date You can't delay your shipment more than 365 days.\" displays");
		assertEquals(rewardShippingOptionPage.getErrorgMessageInShippingOptions(), Messages.REQUESTED_DATE_MORE_THAN_365_DAYS_MESSAGE, "Warning message \"Requested Date You can't delay your shipment more than 365 days.\" does not display");
	}

	private void fillDateAfterMoreThan365DaysAndClickContinue() {
		Logger.info("In Shipping Option page, filling a date after more than 365 days in \"Delayed Arrival Date\"" + "- Click \"Continue\"");
		fillDelayedArrivalDateAndClickContinueLink(Common.randomNewDate("MM/dd/yyyy", 366));
	}

	private void verifyWarningMessageDisplaysWhenFillingInvalidFormatDate() {
		Logger.verify("Verify warning message \"Requested Date invalid format\" displays");
		assertEquals(rewardShippingOptionPage.getErrorgMessageInShippingOptions(), Messages.INVALID_REQUESTED_DATE_MESSAGE, "Warning message \"Requested Date invalid format\" does not display");
	}

	private void fillInvalidFormatDateAndClickContinue() {
		Logger.info("In Shipping Options page, filling invalid format date in \"Delayed Arrival Date\"" + "- Click \"Continue\"");
		fillDelayedArrivalDateAndClickContinueLink(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 8));
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_004 Warning message displays when Requested Date does not apply");
		Logger.to("TO_CRN_025	In Shipping Options page: Warning message \"Requested Date invalid format\" displays when clicking \"Continue\" after filling invalid format date in \"Delayed Arrival Date\"");
		Logger.to("TO_CRN_026	In Shipping Options page: Warning message \"Requested Date You can't delay your shipment more than 365 days.\" displays when clicking \"Continue\" after filling a date after more than 365 days compared to Approximate Arrival Date in \"Delayed Arrival Date\"");
		Logger.to("TO_CRN_027	In Shipping Options page: Warning message \"Requested Date Can't arrange a shipment date in the past\" displays when clicking \"Continue\" after filling a date of today or a date in the past in \"Delayed Arrival Date\"");
		mySKU.init(Recipient.MYSELF, 10);
		address = lstAddresses.getDefaultShippingAddress();
		address.removeCompanyAddressNotRequiredFields();
	}
}
