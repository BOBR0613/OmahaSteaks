package com.omahasteaks.tests.WinFreeSteaksPage.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.WinFreeSteaksPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_WSN_001_WarningMessagesDisplayForBlankAllFieldsWhenSigningUp extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    WinFreeSteaksPage winFreeSteaksPage;

    @Test
    public void TC_WSN_001_Warning_Messages_Dipslay_For_Blank_All_Fieds_When_Signing_Up() {
	initTestCaseData();

	goToWinFreeSteaksPage();

	leaveAllFieldsEmptyAndClickEnterToWinButton();

	verifyWarningMessagesDisplayForBlankAllFields();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyWarningMessagesDisplayForBlankAllFields() {
	Logger.verify("Verify a warning message with below information:\n" + "Please Enter Your Email Address\n" + "Please Enter Your First Name\n" + "Please Enter Your Last Name\n" + "Please Select Your State\n" + "Please Enter Your City\n" + "Please Enter Your Phone Number");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.EMAIL), Messages.getEmptyErrorMessageInWinFreeSteaksPage(AddressFields.EMAIL), "Warning message \"Please Enter Your Email Address\" does not display");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getEmptyErrorMessageInWinFreeSteaksPage(AddressFields.FIRST_NAME), "Warning message \"Please Enter Your First Name\" does not display");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getEmptyErrorMessageInWinFreeSteaksPage(AddressFields.LAST_NAME), "Warning message \"Please Enter Your Last Name\" does not display");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.CITY), Messages.getEmptyErrorMessageInWinFreeSteaksPage(AddressFields.CITY), "Warning message \"Please Enter Your City\" does not display");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.STATE), Messages.getEmptyErrorMessageInWinFreeSteaksPage(AddressFields.STATE), "Warning message \"Please Select Your State\" does not display");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.PHONE), Messages.getEmptyErrorMessageInWinFreeSteaksPage(AddressFields.PHONE), "Warning message \"Please Enter Your Phone Number\" does not display");
    }

    public void leaveAllFieldsEmptyAndClickEnterToWinButton() {
	Logger.info("In Win Free Steaks page, Leave every fields empty " + "Tick I agree checkbox" + "Click \"Enter to win\" button");
	winFreeSteaksPage.clickIAgreeCheckbox();
	winFreeSteaksPage.clickEnterToWinInWinFreeSteakPage();
    }

    public void goToWinFreeSteaksPage() {
	Logger.info("In Hompage, go to Win Free Steaks page");
	generalPage.goToWinFreeSteaksPage();
    }

    public void initTestCaseData() {
	Logger.tc("TC_WSN_001 Warning messages display for blank all fields when signing up");
	Logger.to("TO_WSN_01 In Win Free Steaks page, Warning message \"Please Enter Your Email Address\" displays for blank \"Email Address\" field when signing up");
	Logger.to("TO_WSN_02 In Win Free Steaks page, Warning message \"Please Enter Your First Name\" displays for blank \"First Name\" field when signing up");
	Logger.to("TO_WSN_03 In Win Free Steaks page, Warning message \"Please Enter Your Last Name\" displays for blank \"Last Name\" field when signing up");
	Logger.to("TO_WSN_04 In Win Free Steaks page, Warning message \"Please Enter Your City\" displays for blank \"City\" field when signing up");
	Logger.to("TO_WSN_05 In Win Free Steaks page, Warning message \"Please Enter Your State\" displays for blank \"State\" field when signing up");
	Logger.to("TO_WSN_06 In Win Free Steaks page, Warning message \"Please Enter Your Phone Number\" displays for blank \"Phone\" field when signing up");
    }
}
