package com.omahasteaks.tests.HomePage.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_HPP_002_CheckURLWillBeRedirectedToLiveRewardSite extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Test
    public void TC_HPP_002_Check_URL_Will_Be_Redirected_To_Live_Reward_Site() {

	initTestCaseData();

	goToGiftCertificatesPage();

	verifyTheGiftCertificatesPageIsDisplayed();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyTheGiftCertificatesPageIsDisplayed() {
	Logger.verify("Verify the Gift Certificates page is diplayed");
	assertEquals(Common.getCurrentUrl(), Constants.LIVE_REWARD_URL, "The Gift Cerificates page does not display");
    }

    public void goToGiftCertificatesPage() {
	Logger.info("Go to Gift Certificates page");
	generalPage.goToGiftCertificatesPage();
    }

    public void initTestCaseData() {
	Logger.tc("TC_HPP_002 Check URL of reward will be redirected to live reward site");
	Logger.to("TO_HPP_06 'https://www.omastk.com/collection' url will be redirected to live reward site");
    }

}
