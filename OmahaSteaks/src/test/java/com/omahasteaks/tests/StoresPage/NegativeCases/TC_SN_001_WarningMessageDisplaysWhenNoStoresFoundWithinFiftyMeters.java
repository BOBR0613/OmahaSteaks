package com.omahasteaks.tests.StoresPage.NegativeCases;

import static org.testng.Assert.assertEquals;
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
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SN_001_WarningMessageDisplaysWhenNoStoresFoundWithinFiftyMeters extends TestBase_2SC {
    @Inject
    CustomerAddress address;

    @Inject
    GeneralPage generalPage;

    @Inject
    StoresPage storesPage;

    @Test
    public void TC_SN_001_Warning_Message_Displays_When_No_Stores_Found_Within_Fifty_Meters() {
	initTestCaseData();

	goToStoresPage();

	verifyStoresPageDisplays();

	fillValidZipCodeAndClickSubmitButton();

	verifyMessageNoStoresFoundWithinFiftyMetersDisplays();

	fillZipCodeWithInvalidFormatAndClickSubmitButton();

	verifyWarningMessageDisplaysWhenFillingZipCodeWithInvalidFormat();

	fillInvalidZipCodeAndClickSubmitButton();

	verifyWarningMessageDisplaysWhenFillingInvalidZipCode();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyWarningMessageDisplaysWhenFillingZipCodeWithInvalidFormat() {
    Logger.verify("Verify Warning message \"Invalid Address: The zip code or address you entered does not appear to be valid.\" displays when fill invalid zipcode format"
    		+ " into Zip Code field");
	assertEquals(storesPage.getErrorMessage(), Messages.INVALID_ZIP_CODE_ERROR_MESSAGE, "Warning message \"Invalid Address: The zip code or address you entered does not appear to be valid.\" does not display when fill invalid zipcode into Zip Code field");
	}

    public void fillZipCodeWithInvalidFormatAndClickSubmitButton() {
	Logger.info("In Stores Locations page, fill zipcode with invalid format into Zip Code field");
	storesPage.clickMenuTabdisplayed(MenuTabsInStoresPage.STORE_LOCATIONS);
	address.setZipCode(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 6));
	storesPage.fillZipCode(address);
	storesPage.clickSubmitButton();
    }

    public void verifyWarningMessageDisplaysWhenFillingInvalidZipCode() {
	Logger.verify("Verify Warning message \"Invalid Address: The zip code or address you entered does not appear to be valid.\" displays when fill invalid zipcode into Zip Code field");
	assertEquals(storesPage.getErrorMessage(), Messages.INVALID_ZIP_CODE_ERROR_MESSAGE, "Warning message \"Invalid Address: The zip code or address you entered does not appear to be valid.\" does not display when fill invalid zipcode into Zip Code field");
    }

    public void fillInvalidZipCodeAndClickSubmitButton() {
	Logger.info("In Stores Locations page, fill invalid Zip code into Zip Code field");
	storesPage.clickMenuTabdisplayed(MenuTabsInStoresPage.STORE_LOCATIONS);
	address.setZipCode(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 5));
	storesPage.fillZipCode(address);
	storesPage.clickSubmitButton();
    }

    public void verifyMessageNoStoresFoundWithinFiftyMetersDisplays() {
	Logger.verify("Verify Message \"No stores found within 50 meters\" displays with inputted zip code when no stores found within 50 meters");
	assertTrue(storesPage.isNoStoresFoundDisplayed(), "Message \"No stores found within 50 meters\" does not display with inputted zip code when no stores found within 50 meters");
    }

    public void fillValidZipCodeAndClickSubmitButton() {
	Logger.info("In Stores Locations page, fill valid Zipcode into Zip Code field");
	storesPage.clickMenuTabdisplayed(MenuTabsInStoresPage.STORE_LOCATIONS);
	storesPage.fillZipCode(address);
	storesPage.clickSubmitButton();
    }

    public void verifyStoresPageDisplays() {
	Logger.verify("Verify Stores page displays when clicking Stores link of top menu in Homepage");
	assertTrue(Common.getCurrentUrl().contains(Constants.URL_STORES), "\"Stores\" page does not display when clicking Stores link of top menu in Homepage");
    }

    public void goToStoresPage() {
	Logger.info("In Homepage, click \"Stores\" link in Top menu");
	generalPage.goToStoresPage();
    }

    public void initTestCaseData() {
	Logger.tc("TC_SN_001 Warning message displays when no stores found within fifty meters");
	Logger.to("TO_SN_01 Message \"No stores found within 50 meters\" displays with inputted zip code when no stores found within 50 meters");
	Logger.to("TO_SN_02 Warning message \"Invalid Address: The zip code or address you entered does not appear to be valid.\" displays when fill invalid zipcode into Zip Code field");
	Logger.to("TO_SN_03 Waring message \"Application Server Error: Unexpected communication error occured. Please try again later.\" displays when fill zipcode with invalid format into Zip Code field");
	address.initRandomInformation();
    }
}
