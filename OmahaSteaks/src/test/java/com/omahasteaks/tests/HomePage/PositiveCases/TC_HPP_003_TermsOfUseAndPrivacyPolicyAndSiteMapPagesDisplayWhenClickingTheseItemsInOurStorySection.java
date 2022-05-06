package com.omahasteaks.tests.HomePage.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger; 

public class TC_HPP_003_TermsOfUseAndPrivacyPolicyAndSiteMapPagesDisplayWhenClickingTheseItemsInOurStorySection extends TestBase_2SC {

    @Inject
    HomePage homePage;

    @Inject
    GeneralPage generalPage;

    @Test
    public void TC_HPP_003_Terms_Of_Use_And_Privacy_Policy_And_Site_Map_Pages_Display_When_Clicking_These_Items_In_Our_Story_Section() {
	
	initTestCaseData();

	clickTermOfUseLinkInFooter();
	
	Common.waitForDOMChange(Constants.SLEEP_TIME);	
	
	verifyTermOfUsePopupDisplays();

	Common.waitForDOMChange(Constants.SLEEP_TIME);	
	
	clickPrivacyPolicyLinkInFooter();
	
	Common.waitForDOMChange(Constants.SLEEP_TIME);	
	
	verifyPrivacyPolicyPopupDisplays();

	clickLinkInOurStorySection(LinksFooter.SITEMAP);
	
	verifySitemapPageDisplays();
	
	clickLinkInOurStorySection(LinksFooter.PRIVACY);

	verifyPrivacyPolicyPageDisplays();

	Common.waitForDOMChange(Constants.SLEEP_TIME);
	
	clickLinkInOurStorySection(LinksFooter.TERMS_OF_USE);

	verifyTermsOfUsePageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyTermsOfUsePageDisplays() {
	Logger.verify("Verify \"Terms of Use\" page displays when clicking Term of use link in Our Story section");
	assertTrue(Common.getCurrentUrl().contains(Constants.URL_TERMS_OF_USE), "\"Terms of Use\" page does not display when clicking Terms of Use link in Our Story section");
    }

    public void verifySitemapPageDisplays() {
	Logger.verify("Verify \"Sitemap\" page displays when clicking Sitemap link in Our Story section");
	assertTrue(Common.getCurrentUrl().contains(Constants.URL_SITEMAP), "\"Sitemap\" page does not display when clicking Sitemap link in Our Story section");
    }

    public void clickLinkInOurStorySection(LinksFooter nameLink) {
	Logger.info("In Footer, click \"" + nameLink + "\" link in \"Our Story\" Section");
	homePage.clickLinkInFooterBySection(Constants.OUR_STORY, nameLink);
    }

    public void verifyPrivacyPolicyPopupDisplays() {
	Logger.verify("Verify \"Privacy Policy\" modal displays with information when clicking \"Privacy Policy\" link");
	assertTrue(homePage.isPrivacyPolicyPopupInFooterDisplayed(), "\"Privacy Policy\" modal does not display with information when clicking \"Privacy Policy\" link");
	generalPage.closeModal();
    }
    
    public void verifyPrivacyPolicyPageDisplays() {
		Logger.verify("Verify \"Privacy Policy\" page displays with information when clicking \"Privacy Policy\" link");
		assertTrue(homePage.isPrivacyPolicyPageDisplayed(), "\"Privacy Policy\" page does not display with information when clicking \"Privacy Policy\" link");
		generalPage.closeModal();
    }

    public void clickPrivacyPolicyLinkInFooter() {
	Logger.info("In Footer, click \"Privacy Policy\" link in the footer");
	generalPage.closeModal();
	homePage.clickTermAgreementLinkInFooter(LinksFooter.PRIVACY_POLICY);
    }

    public void verifyTermOfUsePopupDisplays() {
	Logger.verify("Verify \"Terms of Use\" modal displays with information when clicking \"Terms of Use\" link");
	assertTrue(homePage.isTermsOfUsePopupInFooterDisplayed(), "\"Terms of Use\" modal does not display with information when clicking \"Terms of Use\" link");
    }

    public void clickTermOfUseLinkInFooter() {
	Logger.info("In Footer, click \"Terms of Use\" link in the footer");
	homePage.clickTermAgreementLinkInFooter(LinksFooter.TERMS_OF_USE);
    }

    public void initTestCaseData() {
	Logger.tc("TC_HPP_03 Terms of Use & Privacy Policy and Sitemap pages diplay when clicking these items in Our Story section");
	Logger.to("TO_HPP_07 \"Terms of Use\" modal displays when clicking \"Terms of Use\" link");
	Logger.to("TO_HPP_08 \"Privacy Policy\" modal displays when clicking \"Privacy Policy\" link");
	Logger.to("TO_HPP_11 \"Sitemap\" page displays when clicking Sitemap link in Our Story section");
	Logger.to("TO_HPP_12 \"Privacy Policy\" page displays when clicking Privacy link in Our Story section");
	Logger.to("TO_HPP_13 \"Term of User\" page displays when clicking Term of User link in Our Story section");
    }

}
