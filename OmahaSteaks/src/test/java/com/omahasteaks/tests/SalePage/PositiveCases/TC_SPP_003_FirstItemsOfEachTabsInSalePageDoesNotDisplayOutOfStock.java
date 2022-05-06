package com.omahasteaks.tests.SalePage.PositiveCases;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.ProgressiveOfferTab;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.SalePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.helper.Logger;

public class TC_SPP_003_FirstItemsOfEachTabsInSalePageDoesNotDisplayOutOfStock extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    SalePage salePage;

    @Test
    public void TC_SPP_003_First_Items_Of_Each_Tabs_In_SalePage_Does_Not_Display_Out_Of_Stock() {
	initTestCaseData();

	goToSideMenuItemPage(SideMenuItem.SALE);

	for (ProgressiveOfferTab progressiveOfferTab : ProgressiveOfferTab.asArrays()) {

	    clickTabInSalePage(progressiveOfferTab);
	    verifyFirstItemInTabDoesNotDisplayOutOfStock(progressiveOfferTab);
	}

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyFirstItemInTabDoesNotDisplayOutOfStock(ProgressiveOfferTab progressiveOfferTab) {
	Logger.verify("Verify First Item of " + progressiveOfferTab.getValue() + " tab in Salepage does not display Out Of Stock");
	assertFalse(salePage.isFirstSKUDisplayedOutOfStock(), "First Item of " + progressiveOfferTab.getValue() + " tab in Sale page displays Out Off Stock");
    }

    public void clickTabInSalePage(ProgressiveOfferTab progressiveOfferTab) {
	Logger.info("In Sale page, click " + progressiveOfferTab.getValue() + " tab");
	salePage.clickProgressiveOfferTabByName(progressiveOfferTab);
    }

    public void goToSideMenuItemPage(SideMenuItem sideMenuItemName) {
	Logger.info("In Homepage, click SALE link in Side Menu Item link");
	generalPage.clickSideMenuItemLink(sideMenuItemName);
    }

    public void initTestCaseData() {
	Logger.tc("TC_SPP_003 First Item of each tabs in Salepage does not display Out Of Stock");
	Logger.to("TC_SPP_003 First Item of each tabs in Salepage does not display Out Of Stock");
    }
}
