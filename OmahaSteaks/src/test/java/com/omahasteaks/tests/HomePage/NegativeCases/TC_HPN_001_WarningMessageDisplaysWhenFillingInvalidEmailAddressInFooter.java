package com.omahasteaks.tests.HomePage.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_HPN_001_WarningMessageDisplaysWhenFillingInvalidEmailAddressInFooter extends TestBase_2SC {

    @Inject
    HomePage homePage;

    @Inject
    GeneralPage generalPage;

    @Test
    public void TC_HPN_001_Warning_Message_Displays_When_Filling_Invalid_Email_Address_In_Footer() {

	initTestCaseData();

	fillBlankForEmailFielInFooterdAndClickJoin();

	verifyWarningMessageDisplaysWhenFillingBlankForEmailField();

	fillInvalidEmailInFooterAndClickJoin();

	verifyWarningMessageDisplaysWhenFillingInvalidEmailAddress();
    }

    public void verifyWarningMessageDisplaysWhenFillingInvalidEmailAddress() {
	Logger.verify("Warning message \"Please Enter A Valid Email Address\" displays when filling invalid email in Footer");
	assertEquals(homePage.getEmailErrorMessage(), Messages.INVALID_EMAIL_ADDRESS_ERROR_MESSAGE, "Warning message \"Please Enter A Valid Email Address\" does not display when filling invalid email in Footer");
    }

    public void fillInvalidEmailInFooterAndClickJoin() {
	Logger.info("Fill invalid email in Footer");
	account.setEmail(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 20));
	fillEmailAddressAndClickJoin();
    }

    public void fillEmailAddressAndClickJoin() {
	homePage.fillEmailAddressInFooter(account.getEmail());
	homePage.clickJoinButtonInFooter();
    }

    public void verifyWarningMessageDisplaysWhenDoesNotTickOnOptionCheckBox() {
	Logger.verify("Warning message \"Please Accept Our Terms Of Use\" displays when does not tick on the \"I agree to the Terms of Use and Privacy Policy\" checkbox in Footer");
	assertEquals(homePage.getIAgreeErrorMessage(), Messages.I_AGREE_CHECKBOX_ERROR_MESSAGE, "Warning message \"Please Accept Our Terms Of Use\" does not display when does not tick on the \"I agree to the Terms of Use and Privacy Policy\" checkbox in Footer");
    }

    public void verifyWarningMessageDisplaysWhenFillingBlankForEmailField() {
	Logger.verify("Warning message \"Please Enter Your Email Address\" displays when filling blank for email field in Footer");
	assertEquals(homePage.getEmailErrorMessage(), Messages.EMAIL_EMPTY_MESSAGE, "Warning message \"Please Enter Your Email Address\" does not display when filling blank for email field in Footer");
    }

    public void fillBlankForEmailFielInFooterdAndClickJoin() {
	Logger.info("Fill blank for email field in Footer"); 
	account.setEmail(Constants.EMPTY_STRING);
	fillEmailAddressAndClickJoin();
    }

    public void initTestCaseData() {
	Logger.tc("TC_HPN_001 Warning message displays when filling invalid Email Address in Footer");
	Logger.to("TO_HPN_01 Warning message \"Please Enter Your Email Address\" displays for blank \"Email Address\" field when subscribing to email");
	Logger.to("TO_HPN_02 Warning message \"Please Enter A Valid Email Address\" displays in \"Email Address\" field when subscribing with invalid email address");
	Logger.to("TO_HPN_03 Warning message \"Please Accept Our Terms Of Use\" displays when subscribing to email without tick \"I agree to the Terms of Use and Privacy Policy\" checkbox");
	account.initRandomAccount();
    }
}
