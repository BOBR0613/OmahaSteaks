package com.omahasteaks.tests.Promotion;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ManageBannerPromotion;
import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.data.objects.BannerPromotion;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;
import java.util.List;


public class TC_PM_001_VerifyPromotionDataOnTheHomePage extends TestBase_2SC {
	@Inject
	ManageBannerPromotion managePromotion;

	@Inject
	HomePage homePage=null;

	BannerPromotion lpromo = null;
	BannerPromotion rpromo = null;

	@Test
	public void TC_PM_001_Verify_Promotion_Data_On_The_Home_Page() {

		initTestCaseData();

		lpromo = verifyThePromotionDataOnTheTopBanner( PromotionLocation.TOP_LEFT_BANNER);
		rpromo = verifyThePromotionDataOnTheTopBanner( PromotionLocation.TOP_RIGHT_BANNER);
		
		verifyThePromotionTextOnTheTopBannerIsBold(lpromo, lpromo.getBoldText(PromotionLocation.TOP_LEFT_BANNER), PromotionLocation.TOP_LEFT_BANNER);
		verifyThePromotionTextOnTheTopBannerIsBold(rpromo, rpromo.getBoldText(PromotionLocation.TOP_RIGHT_BANNER), PromotionLocation.TOP_RIGHT_BANNER);
		clickPromotionLink(PromotionLocation.TOP_LEFT_BANNER);
		verifyThePageIsDisplayedCorrectly(lpromo, PromotionLocation.TOP_LEFT_BANNER);
		clickPromotionLink(PromotionLocation.TOP_RIGHT_BANNER);
		verifyThePageIsDisplayedCorrectly(rpromo, PromotionLocation.TOP_RIGHT_BANNER);

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyThePageIsDisplayedCorrectly(BannerPromotion promo, PromotionLocation promotionLocation) {
 		String promotionLink = promo.getBannerLink(promotionLocation);

		if (promotionLink.contains(Constants.OMAHA_URL)) {
			Logger.verify("Verify the page is displayed correctly after clicking promotion link on " + promotionLocation.getLocation());
			assertEquals(Common.getCurrentUrl(), promo.getBannerLink(promotionLocation), "The url is displayed incorrectly");
			return;
		}
		Logger.verify("Verify the promotion popup is displayed correctly after clicking promotion link on " + promotionLocation.getLocation());
		assertTrue(promo.getBoldText(promotionLocation).contains(homePage.getTitleOfPromotionPopup()), "The promotion popup is displayed incorrectly after clicking promotion link on top left banner");
	}

	private void clickPromotionLink(PromotionLocation location) {
		Logger.info("Click Promotion link on " + location);
		homePage.clickPromotionLink(location);
	}

	private void verifyThePromotionTextOnTheTopBannerIsBold(BannerPromotion promo, String text, PromotionLocation location) {
		Logger.verify("Verify " + text + " is bold correctly as expected in " + location);
		if (promo.getExpectedTopBannerText(location).equals(" ")) {
			Logger.warning("Skip checking the promotion text is BOLD on the " + location);
			return;
		}
		assertTrue(homePage.isPromotionTextBold(text, location), text + " is bold incorrectly in " + location);
	}

	private BannerPromotion verifyThePromotionDataOnTheTopBanner(PromotionLocation location) {
		List<BannerPromotion> promotionList = managePromotion.getPromotionDataInDateRange();
 		String actualText = homePage.getPromotionText(location); 
 		BannerPromotion promo=null;
		/*
		 * If today in the date ranges, verify the promotion data with the expected text
		 * on that date range.
		 */
 		for (BannerPromotion pr : promotionList) {
		   promo = pr;
		   Logger.verify("Verify the promotion text on the " + location + " is displayed correctly as the expected text in a date range: from " + promo.getStartingDate() + " to " + promo.getEndingDate());
		   String expectedData = promo.getExpectedTopBannerText(location);
 		   if (expectedData.equalsIgnoreCase(actualText)) break;
		}
		
		assertEquals(actualText,promo.getExpectedTopBannerText(location), "The promotion text does not display correctly in the " + location);
		assertTrue(actualText.equalsIgnoreCase(promo.getExpectedTopBannerText(location)), "The promotion text does not display correctly in the " + location);
		return promo;
	}
	

	private void initTestCaseData() {
		Logger.tc("TC_PM_001 - Verify the promotion data on the Home page");
		Logger.to("TO_PM_01 Verify that the promotion text on the top left banner is displayed correctly");
		Logger.to("TO_PM_02 Verify that the promotion text on the top right banner is displayed correctly");
		Logger.to("TO_PM_04	Verify the promotion text on the top left banner is bold");
		Logger.to("TO_PM_05	Verify the promotion text on the top right banner is bold");
		Logger.to("TO_PM_06	Verify the promotion popup is displayed when clicking promotion text on the top right banner");
		Logger.to("TO_PM_07	Verify the promotion page is displayed when clicking promotion text on the top left banner");
	}
	
}
