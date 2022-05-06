package com.omahasteaks.tests.WinFreeSteaksPage.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.WinFreeSteaksPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.helper.Logger;

public class TC_WSP_001_MessageThankYouDisplaysWhenSigningUpSuccessfullyInWinFreeSteaksPage extends TestBase_2SC {

    CustomerAddress myAddress;

    @Inject
    ListAddresses lstAddress;

    @Inject
    GeneralPage generalPage;

    @Inject
    WinFreeSteaksPage winFreeSteaksPage;

    @Test
    public void TC_WSP_001_Message_Thank_You_Displays_When_Signing_Up_Successfully_In_Win_Free_Steaks_Page() {
	initTestCaseData();

	goToWinFreeSteaksPage();

	fillCustomerAddressAndClickEnterToWinButton();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyThankYouMessageDisplays() {
	Logger.verify("Verify \"Thank you\" message displays when when signing up with valid informations");
	assertTrue(winFreeSteaksPage.isThankYouMessageDisplayed(), "\"Thank you\" message does not display when when signing up with valid informations");
    }

    public void fillCustomerAddressAndClickEnterToWinButton() {
	Logger.info("In Win Free Steaks page, fill all valid information into all fields" + "Tick I agree checkbox" + "Click \"Enter to win\" button");
	winFreeSteaksPage.fillEmailAddress(myAddress.email);
	winFreeSteaksPage.fillCustomerAddress(myAddress);
	winFreeSteaksPage.clickIAgreeCheckbox();
	winFreeSteaksPage.clickEnterToWinInWinFreeSteakPage();
    }

    public void goToWinFreeSteaksPage() {
	Logger.info("In Hompage, go to Win Free Steaks page");
	generalPage.goToWinFreeSteaksPage();
    }

    public void initTestCaseData() {
	Logger.tc("TC_WSP_001 Message \"Thank you\" displays when signing up successfully in \"Win Free Steaks\" page");
	Logger.to("TO_WSP_001 In Win Free Steaks page, Message \"ENTRY SUBMITTED Thank You\" displays when signing up with valid informations");
	myAddress = lstAddress.getDefaultShippingAddress();
    }
}
