package com.omahasteaks.tests.SalePage.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.SalePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_SPP_001_ProgressiveOffersTabsAreClickable extends TestBase_2SC {
    @Inject
    GeneralPage generalPage;
    @Inject
    SalePage salePage;

    @Test
    public void TC_SPP_001_Progressive_Offers_Tabs_Are_Clickable() {

	initTestCaseData();

	goToSideMenuItemPage(SideMenuItem.SALE);

	verifyProgressiveOffersTabsAreClickable();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void goToSideMenuItemPage(SideMenuItem sideMenuItemName) {
	Logger.info("In Homepage, click SALE link in Side Menu Item link");
	generalPage.clickSideMenuItemLink(sideMenuItemName);
	Common.modalDialog.closeSavorDialog();
    }

    public void verifyProgressiveOffersTabsAreClickable() {
	Logger.verify("Verify Progressive Offers tabs are clickable");
	assertTrue(salePage.isProgressiveOffersTabClickable(), " Progressive Offers tabs are not clickable");
    }

    public void initTestCaseData() {
	Logger.tc("TC_SPP_001 Progressive Offers tabs are clickable");
	Logger.to("TO_SPP_01 Overstocks, $19.99 Sale, Shipped Free Packages, Slow Cooker & Skillet Meals and Earn an Extra 10% off tabs of Sale page are clickable");
    }

}
