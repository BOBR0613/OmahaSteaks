package com.omahasteaks.tests.SalePage.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.ProgressiveOfferTab;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.SalePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SPN_001_WarningMessageDisplaysWhenFillingInvalidEmailAddressInEarnAnExtra10PercentOff extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;
    @Inject
    SalePage salePage;

    @Test
    public void TC_SPN_001_Warning_Message_Displays_When_Filling_Invalid_Email_Address_In_Earn_An_Extra_10_Percent_Off() {

	initTestCaseData();
	
	goToSideMenuItemPage(SideMenuItem.SALE);

	goToEarnAnExtra10PercentOffTabInSalePage();

	fillBlankForEmailFieldAndClickSubmit();

	verifyWarningMessageDisplaysWhenFillingBlankForEmailField();

	verifyWarningMessageDisplaysWhenDoesNotTickOnOptionCheckBox();

	fillInvalidEmailAndClickSubmit();

	verifyWarningMessageDisplaysWhenFillingInvalidEmailAddress();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyWarningMessageDisplaysWhenFillingInvalidEmailAddress() {
	Logger.verify("Warning message \"Please Enter A Valid Email Address\" displays when filling invalid email in \"Earn an Extra 10% off\" tab");
	assertEquals(salePage.getEmailErrorMessage(), Messages.INVALID_EMAIL_ADDRESS_ERROR_MESSAGE, "Warning message \"Please Enter A Valid Email Address\" does not display when filling invalid email in \"Earn an Extra 10% off\" tab");
    }

    public void fillInvalidEmailAndClickSubmit() {
	Logger.info("Fill invalid email in \"Earn an Extra 10% off\" tab");
	account.setEmail(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 20));
	fillEmailAddressAndClickSubmit();
    }

    public void fillEmailAddressAndClickSubmit() {
	salePage.fillEmailAddressInProgressiveOffersTab(account.getEmail());
	salePage.clickSubmitInSalePage();
    }

    public void verifyWarningMessageDisplaysWhenDoesNotTickOnOptionCheckBox() {
	Logger.verify("Warning message \"Please Accept Our Terms Of Use\" displays when does not tick on the \"I agree to the Terms of Use and Privacy Policy\" checkbox in \"Earn an Extra 10% off\" tab");
	assertEquals(salePage.getIAgreeErrorMessage(), Messages.I_AGREE_CHECKBOX_ERROR_MESSAGE, "Warning message \"Please Accept Our Terms Of Use\" does not display when does not tick on the \"I agree to the Terms of Use and Privacy Policy\" checkbox in \"Earn an Extra 10% off\" tab");
    }

    public void verifyWarningMessageDisplaysWhenFillingBlankForEmailField() {
	Logger.verify("Warning message \"Please Enter Your Email Address\" displays when filling blank for email field in \"Earn an Extra 10% off\" tab");
	assertEquals(salePage.getEmailErrorMessage(), Messages.EMAIL_EMPTY_MESSAGE, "Warning message \"Please Enter Your Email Address\" does not display when filling blank for email field in \"Earn an Extra 10% off\" tab");
    }

    public void fillBlankForEmailFieldAndClickSubmit() {
	Logger.info("Fill blank for email field in \"Earn an Extra 10% off\" tab");
	Logger.info("Do not tick on the \"I agree to the Terms of Use and Privacy Policy\" checkbox in \"Earn an Extra 10% off\" tab");
	account.setEmail(Constants.EMPTY_STRING);
	fillEmailAddressAndClickSubmit();
    }

    public void goToEarnAnExtra10PercentOffTabInSalePage() {
	Logger.info("Go to Earn an Extra 10% Off Tab in Sale page");
	salePage.clickProgressiveOfferTabByName(ProgressiveOfferTab.EARN_AN_EXTRA_25_DOLLARS_OFF);
    }

    public void goToSideMenuItemPage(SideMenuItem sideMenuItem) {
	Logger.info("In Homepage, click SALE link in Side Menu Item link");
	generalPage.clickSideMenuItemLink(sideMenuItem);
	Common.modalDialog.closeSavorDialog();
    }

    public void initTestCaseData() {
	Logger.tc("TC_SPN_001 Warning message displays when filling invalid email address in Earn An Extra 10 Percent Off");
	Logger.to("TO_SPN_01 Warning message \"Please Enter A Valid Email Address\" displays when filling invalid email in \"Earn an Extra 10% off\" tab");
	Logger.to("TO_SPN_02 Warning message \"Please Enter Your Email Address\" displays when filling blank for email field in \"Earn an Extra 10% off\" tab");
	Logger.to("TO_SPN_03 Warning message \"Please Accept Our Terms Of Use\" displays when does not tick on the \"I agree to the Terms of Use and Privacy Policy\" checkbox in \"Earn an Extra 10% off\" tab");
	account.initRandomAccount();
    }

}
