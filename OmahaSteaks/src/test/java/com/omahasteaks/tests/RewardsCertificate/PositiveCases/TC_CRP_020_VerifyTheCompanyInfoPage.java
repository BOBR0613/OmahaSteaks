package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_020_VerifyTheCompanyInfoPage extends TestBase_CR {

	@Test
	public void TC_CRP_020_Verify_The_Company_Info_Page() {
		initTestCaseData();

		goToCompanyInfoTab();

		verifyTheCompanyInfoPageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheCompanyInfoPageDisplays() {
		Logger.verify("Verify that the 'Company Info' page is displayed after clicking on the 'Company Info' tab");
		assertEquals(rewardGeneralPage.getHeaderOfPage(), "About Our Company", "The \"About Our Company\" page does not display");
	}

	private void goToCompanyInfoTab() {
		Logger.info("Click on the 'Company Info' tab");
		rewardGeneralPage.selectTabInTopMenu(Constants.REWARD_TAB_COMPANY_INFO);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_020 Verify the Company Info page");
		Logger.to("TO_CRP_057	\"About Our Company\" page is displayed when clicking on the 'Company Info' tab");
	}

}
