package com.omahasteaks.tests.StoresPage.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.MenuTabsInStoresPage;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.StoresPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SP_001_NearestStoresAddressDisplaysWhenFillingValidZipcodeIntoZipCodeField extends TestBase_2SC {

    @Inject
    CustomerAddress address;

    @Inject
    GeneralPage generalPage;

    @Inject
    StoresPage storesPage;

    @Test
    public void TC_SP_001_Nearest_Stores_Address_Displays_When_Filling_Valid_Zipcode_Into_ZipCode_Field() {
	initTestCaseData();

	goToStoresPage();

	verifyStoresPageDisplays();

//	verifyTabDisplaysInStoresPage(MenuTabsInStoresPage.HALF_OFF_DEALS);

	verifyTabDisplaysInStoresPage(MenuTabsInStoresPage.STORE_LOCATIONS);

	verifyTabDisplaysInStoresPage(MenuTabsInStoresPage.STORE_SPECIALS);

	verifyTabDisplaysInStoresPage(MenuTabsInStoresPage.CAREERS);

	fillZipCodeAndClickSubmitButton();

	verifyNearestStoresAddressDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyNearestStoresAddressDisplays() {
	Logger.verify("Verify Nearest Stores address displays when fill valid zipcode into Zip Code field");
	assertTrue(storesPage.isNearestStoresAddressDisplayed(), "Nearest Stores address does not displays");
    }

    public void fillZipCodeAndClickSubmitButton() {
	Logger.info("In Stores page, fill valid Zipcode into Zip Code field");
	storesPage.clickMenuTabdisplayed(MenuTabsInStoresPage.STORE_LOCATIONS);
	address.setZipCode("68104");
	storesPage.fillZipCode(address);
	Common.waitForDOMChange();
	storesPage.clickSubmitButton();
	Common.waitForPageLoad();
    }

    public void verifyTabDisplaysInStoresPage(MenuTabsInStoresPage tabInStoresPage) {
	Logger.verify("Verify " + tabInStoresPage + " tab displays in Stores page");
	assertTrue(storesPage.isMenuTabdisplayed(tabInStoresPage), tabInStoresPage + " tab does not display in Stores page");
    }

    public void verifyStoresPageDisplays() {
	Logger.verify("Verify Stores page displays when clicking Stores link of top menu in Homepage");
	assertTrue(Common.getCurrentUrl().contains(Constants.URL_STORES), "\"Stores\" page does not display when clicking Stores link of top menu in Homepage");
    }

    public void goToStoresPage() {
	Logger.info("In Homepage, click \"Stores\" link in Top menu");
	generalPage.goToStoresPage();
    }

    public void closeStoresPage() {
	Logger.info("Close the stores tab/Window");
	generalPage.closeStoresPage();
    }

    public void initTestCaseData() {
	Logger.tc("TC_SP_001 Nearest Stores address displays when filling valid Zipcode into Zipcode field");
	Logger.to("TO_SP_01 Shipped Free, Store Locations, Store Specials, Careers tab displays in Stores page");
	Logger.to("TO_SP_02 Nearest Stores address displays when fill valid zipcode into Zip Code field");
	address.initRandomInformation();
    }
}
