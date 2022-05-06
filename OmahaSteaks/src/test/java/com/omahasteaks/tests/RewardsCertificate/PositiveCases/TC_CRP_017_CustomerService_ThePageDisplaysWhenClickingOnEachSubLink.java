package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.data.enums.LinksCustomerService;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_017_CustomerService_ThePageDisplaysWhenClickingOnEachSubLink extends TestBase_CR {

	@Test
	public void TC_CRP_017_CustomerService_The_Page_Displays_When_Clicking_On_Each_Sub_Link() {
		initTestCaseData();

		goToEachSubLink(LinksCustomerService.PRIVACY_POLICY);

		verifyThePrivacyPolicyPageDisplays();

		goToEachSubLink(LinksCustomerService.GUARANTEE);

		verifyPageDisplaysCorrectly(LinksCustomerService.GUARANTEE);

		goToEachSubLink(LinksCustomerService.FREQUENTLY_ASKED_QUESTIONS);

		verifyPageDisplaysCorrectly(LinksCustomerService.FREQUENTLY_ASKED_QUESTIONS);

		goToEachSubLink(LinksCustomerService.SHIPPING);

		verifyPageDisplaysCorrectly(LinksCustomerService.SHIPPING);

		goToEachSubLink(LinksCustomerService.CONTACT_US);

		verifyContactUsPageDisplays();

		goToEachSubLink(LinksCustomerService.PRODUCT_SAFETY);

		verifyProductSafetyPageDisplays();

		goToEachSubLink(LinksCustomerService.STEAK_CUT_INFORMATION);

		verifySteakCutInformationPageDisplays();

		goToEachSubLink(LinksCustomerService.POINTS_OF_DISTINCTION);

		verifyPageDisplaysCorrectly(LinksCustomerService.POINTS_OF_DISTINCTION);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyProductSafetyPageDisplays() {
		Logger.verify("Verify the \"Product Safety Information\" page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertEquals(rewardGeneralPage.getHeaderOfPage(), "Product Safety Information", "Header of the \"Product Safety Information\" page does not display correctlly.");
	}

	private void verifyContactUsPageDisplays() {
		Logger.verify("Verify the \"Customer Service - Send us an email\" page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertEquals(rewardGeneralPage.getHeaderOfPage(), "Customer Service - Send us an email", "Header of the \"Customer Service - Send us an email\" page does not display correctlly.");
	}

	private void verifySteakCutInformationPageDisplays() {
		Logger.verify("Verify the \"Omaha Steaks Butchers Corner\" page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertEquals(rewardGeneralPage.getHeaderOfPage(), "Omaha Steaks Butchers Corner", "Header of the \"Omaha Steaks Butchers Corner\" page does not display correctlly.");
	}

	private void verifyThePrivacyPolicyPageDisplays() {
		Logger.verify("Verify the \"Privacy Policy\" page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertTrue(Common.getPageSource().contains("Privacy Policy"));
	}

	private void verifyPageDisplaysCorrectly(LinksCustomerService nameLink) {
		Logger.verify("Verify the \"" + nameLink.getValue() + "\" page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertEquals(rewardGeneralPage.getHeaderOfPage(), nameLink.getValue(), "Header of the \"" + nameLink.getValue() + " page does not display correctlly.");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_017 - Customer Services - The page displays correctly when clicking on sub links");
		Logger.to("TO_CRP_047	The Privacy Policy page displays correctly when clicking \"Privacy Policy\" link in Customer Service page");
		Logger.to("TO_CRP_048	The Guarantee page displays correctly when clicking \"Guarantee\" link in Customer Service page");
		Logger.to("TO_CRP_049	The Frequently Asked Questions page displays correctly when clicking \"Frequently Asked Questions\" link in Customer Service page");
		Logger.to("TO_CRP_050	The Shipping page displays correctly when clicking \"Shipping\" link in Customer Service page");
		Logger.to("TO_CRP_051	The Product Safety Information page displays correctly when clicking \"Product Safety\" link in Customer Service page");
		Logger.to("TO_CRP_052	The Omaha Steaks Butchers Corner page displays correctly when clicking \"Steak Cut Information\" link in Customer Service page");
		Logger.to("TO_CRP_053	The Points of Distinction page displays correctly when clicking \"Points of Distinction\" link in Customer Service page");
		Logger.to("TO_CRP_054	The Customer Service - Send us an email  page displays correctly when clicking \"Contact Us\" link in Customer Service page");
	}
}
