package com.omahasteaks.tests.HomePage.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.enums.Sections;
import com.omahasteaks.data.objects.ImageLink;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_HPP_001_CheckAllLinksAndImagesInHomepage extends TestBase_2SC {

	String linkStatus = "";
	int totalBrokenLink = 0;

	@Inject
	HomePage homePage;

	@Inject
	ListImageLink listImageLinkInHomePage;

	@Test
	public void TC_HPP_001_Check_All_Links_And_Images_In_Homepage() {

		initTestCaseData();

		verifyCenterStageTabsAreClickable();

		verifyAllLinksAreValid();

		verifyAllImagesAreValid();

		logLink();

		assertEquals(totalBrokenLink, 0, "There is/are " + totalBrokenLink + " links/images broken");
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void verifyCenterStageTabsAreClickable() {
		Logger.verify("Verify Center Stage Tabs are clickable");
		assertTrue(homePage.isCenterStageTabClickable(), "Center Stage Tabs are not clickable");
	}

	public void verifyAllLinksAreValid() {
		Logger.verify("Verify All Links in homepage are valid");

		for (ImageLink imgLink : listImageLinkInHomePage.getListImageLink()) {
			if (!imgLink.getHref().equals("")) {
				if (imgLink.getHref().equals(Constants.IGNORE_PRESS_LINK) || imgLink.getHref().equals(Constants.IGNORE_DAD_LINK) || imgLink.getHref().contains(Constants.IGNORE_BLOG_LINK))
					continue;

				String code = Common.getResponseCodeOfLink(imgLink.getHref());
				linkStatus = linkStatus.concat(Common.generateLogForLink(imgLink.getHref(), code));
				if (code.equalsIgnoreCase("404")) {
					Logger.warning("Link: \"" + imgLink.getHref() + "\" is a broken link. \nStatus - code: " + code);
					totalBrokenLink++;
				}
			}
		}
	}

	public void verifyAllImagesAreValid() {
		Logger.verify("Verify All Images in homepage are valid");

		for (ImageLink imgLink : listImageLinkInHomePage.getListImageLink()) {

			if (!imgLink.getSrc().equals("")) {

				String code = Common.getResponseCodeOfLink(imgLink.getSrc());
				linkStatus = linkStatus.concat(Common.generateLogForLink(imgLink.getSrc(), code));
				if (code.equalsIgnoreCase("404"))  {
					Logger.warning("Image: \"" + imgLink.getSrc() + "\" is a broken image. \nStatus - code: " + code);
					totalBrokenLink++;
				}
			}
		}
	}

	public void initTestCaseData() {
		Logger.tc("TC_HPP_001_CheckLinkAndImageOfElementInHeader");
		Logger.to("TO_HPP_01 Check all links in Homepage");
		Logger.to("TO_HPP_02 Check all images in Homepage");
		Logger.to("TO_HPP_03 In the progressive offers, all images for each of the tabs exist");
		Logger.to("TO_HPP_04 In the progressive offers, all links for each of the tabs exist");
		Logger.to("TO_HPP_05 Center Stage Tabs are clickable");
		listImageLinkInHomePage = homePage.getAllLinksAndImageBySection(Sections.ALL_SECTIONS);
	}

	private void logLink() {
		Logger.logFile("HomepageLink.txt", linkStatus.toString());
	}
}
