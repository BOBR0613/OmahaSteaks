package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_007_ShippingInfoPage_CursorMovesToErrorFieldCorrectly extends TestBase_CR {

	@Inject
	CustomerAddress invalidAddress;

	@Inject
	RewardSKU mySKU;

	@Test
	public void TC_CRN_007_ShippingInfoPage_Cursor_Moves_To_Error_Field_Correctly() {
		initTestCaseData();

		searchAndAddSKU9ToCart(mySKU);
		searchAndAddSKU9ToCart(mySKU);

		fillRewardNumberAndCheckOut();

		leaveShippingInformationEmptyAndClickContinue();

		verifyWarningMessageDisplaysCorrectlyInShippingInformationPage();

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.FIRST_NAME);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.LAST_NAME);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.COMPANY_NAME);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.ADDRESS1);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.STATE);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.CITY);

		clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields.ZIP_CODE);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void clickUnderlinedValueInWarningMsgAndVerifyTheCursorMovesToErrorField(AddressFields field) {
		Logger.info("In Shipping Information page: Clicking to underlined value in warning message of " + field.getValue());
		rewardShippingInfoPage.clickOnAddressFieldLinkInListErrorMessage(field);

		Logger.verify("Verify the cursor moves to error field " + field.getValue());
		assertTrue(rewardShippingInfoPage.isAddressFieldFocused(field), "The cursor does not move to error field " + field.getValue());
	}

	private void verifyWarningMessageDisplaysCorrectlyInShippingInformationPage() {
		Logger.verify("\"Verify a warning message with below information:" + "Please -- it's a required field" + "Ok -- we can't deliver without a last name - it's a required field." + "A company name is required." + "Address for delivery -- it's a must! Please provide." + "City, State and Zip code... all required fields.");
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getRequiredMessageByField(AddressFields.FIRST_NAME));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getRequiredMessageByField(AddressFields.LAST_NAME));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.COMPANY_NAME), Messages.getRequiredMessageByField(AddressFields.COMPANY_NAME));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.ADDRESS1), Messages.getRequiredMessageByField(AddressFields.ADDRESS1));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.ZIP_CODE), Messages.getRequiredMessageByField(AddressFields.ZIP_CODE));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.STATE), Messages.getRequiredMessageByField(AddressFields.STATE));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(AddressFields.CITY), Messages.getRequiredMessageByField(AddressFields.CITY));
	}

	private void leaveShippingInformationEmptyAndClickContinue() {
		Logger.info("In Shipping Information page:" + "-  Leave every fields empty (if there are any fields having default value, then delete them excluding Country field)n" + "- Click \"CONTINUE\"");
		rewardShippingInfoPage.selectAddressType(AddressType.COMPANY_ADDRESS);
		rewardShippingInfoPage.fillShippingInformation(invalidAddress);
		rewardShippingInfoPage.clickContinueLink();
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_007 ShippingInfoPage cursor moves to error field correctly");
		Logger.to("TO_CRN_014	In Shipping Information page: The cursor moves to error field correctly when clicking to underlined value in warning message");
		Logger.to("TO_CRN_015	In Shipping Information page: Warning message with the list of missing fields displays when clicking \"Continue\" without filling all required fields");
		mySKU.init(Recipient.MYSELF, 10);
		invalidAddress.setRandomEmail();
		invalidAddress.setStreetAdress1("");
	}
}
