package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_019_VerifyLinksOnTheFooter extends TestBase_CR {

	@Test
	public void TC_CRP_019_Verify_Links_On_The_Footer() {
		initTestCaseData();

		clickLinkOnTheFooter(LinksFooter.TERMS_OF_USE);

		verifyTermOfUsePageDisplaysCorrectlly();

		Common.switchToMainWindow();

		clickLinkOnTheFooter(LinksFooter.PRIVACY_POLICY);

		verifyPrivacyPolicyPageDisplayCorrectlly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void clickLinkOnTheFooter(LinksFooter nameLink) {
		Logger.info("In the footer: Click on " + nameLink);
		rewardGeneralPage.clickTermAgreementLinkInFooter(nameLink);
	}

	private void verifyTermOfUsePageDisplaysCorrectlly() {
		Logger.verify("In the footer: Verify the Term Of Use page displays correctlly");
		assertTrue(Common.getPageSource().contains("TERMS OF USE"));
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
	}

	private void verifyPrivacyPolicyPageDisplayCorrectlly() {
		Logger.verify("In the footer: Verify the Privacy Policy page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertTrue(rewardGeneralPage.isHeaderOfPrivacyPolicyPageDisplayed(), "Header of the Privacy Policy page does not display correctlly");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_019 - Verify links on the footer");
		Logger.to("TO_CRP_045 - Footer: Terms of Use page displays when clicking \"Terms of Use\" link");
		Logger.to("TO_CRP_046 - Footer: Privacy Policy page displays when clicking \"Privacy Policy\" link");
	}

}
