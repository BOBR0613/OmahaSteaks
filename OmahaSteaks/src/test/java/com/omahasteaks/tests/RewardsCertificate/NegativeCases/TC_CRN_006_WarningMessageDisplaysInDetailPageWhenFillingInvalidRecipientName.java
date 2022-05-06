package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_006_WarningMessageDisplaysInDetailPageWhenFillingInvalidRecipientName extends TestBase_CR {

	@Inject
	RewardSKU sku, sku1;

	@Test
	public void TC_CRN_006_Warning_Message_Displays_In_Detail_Page_When_Filling_Invalid_Recipient_Name() {
		initTestCaseData();

		addSKUFromCategory(sku, "Roasts");

		verifyWarningMessageIsDisplayed();

		goToHomepage();

		addSKUFromCategory(sku1, "Roasts");

		verifyWarningMessageIsDisplayed();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void goToHomepage() {
		Logger.info("Go to homepage");
		DriverUtils.getWebDriver().get(Constants.REWARD_COLLECTTION_URL);
	}

	private void verifyWarningMessageIsDisplayed() {
		Logger.verify("Verify warning message \"Please let us know who you're sending this to... it's a required field when sending a gift.\" is displayed");
		assertEquals(rewardGeneralPage.getErrorMessageByField("Shopping Name"), Messages.INAVLID_RECIPIENT_NAME_MESSAGE, "Warning message \"Please let us know who you're sending this to... it's a required field when sending a gift.\" does not display");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_006 Warning message displays in detail page when filling invalid recipient's name");
		Logger.to("TO_CRN_032	In the detail page, warning message \"Shopping Name:  Please let us know who you're sending this to... it's a required field when sending a gift.\" is displayed when leaving the 'Enter First Name or Nickname' empty and clicking 'Select' button");
		Logger.to("TO_CRN_033	In the detail page, warning message \"Shopping Name:  Please let us know who you're sending this to... it's a required field when sending a gift.\" is displayed when entering some space characters in the 'Enter First Name or Nickname' text box and clicking 'Select' button");
		sku.init(Recipient.EMPTY);
		sku1.init(Recipient.SHOPPING_NAME_TC_CRN_006);
	}
}
