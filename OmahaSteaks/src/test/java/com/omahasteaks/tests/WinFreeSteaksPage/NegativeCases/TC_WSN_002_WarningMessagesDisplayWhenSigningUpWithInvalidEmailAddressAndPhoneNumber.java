package com.omahasteaks.tests.WinFreeSteaksPage.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.WinFreeSteaksPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_WSN_002_WarningMessagesDisplayWhenSigningUpWithInvalidEmailAddressAndPhoneNumber extends TestBase_2SC {
    CustomerAddress customerAddress, invalidAddress;
    @Inject
    ListAddresses lstAddress;

    @Inject
    GeneralPage generalPage;

    @Inject
    WinFreeSteaksPage winFreeSteaksPage;

    @Test
    public void TC_WSN_002_Warning_Messages_Display_When_Signing_Up_With_Invalid_Email_Address_And_Phone_Number() {
	initTestCaseData();

	goToWinFreeSteaksPage();

	fillInvalidPhoneAndEmailInWinFreeSteaksPage();

	verifyTheWarningMssagesIsDisplayed();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyTheWarningMssagesIsDisplayed() {
	Logger.verify("The warning messages are displayed:\n" + "Please Enter A Valid Email Address");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.EMAIL), Messages.WFS_INVALID_EMAIL_ERROR_MESSAGE, "Warning message \"Please Enter Your Email Address\" does not display");
	Logger.verify("The warning messages are displayed:\n" + "Please Enter A Valid Phone Number");
	assertEquals(winFreeSteaksPage.getErrorMessageByField(AddressFields.PHONE), Messages.WFS_INVALID_PHONE_ERROR_MESSAGE, "Warning message \"Please Enter A Valid Phone Number\" does not display");
	Logger.verify("The warning messages are displayed:\n" + "Please Accept Our Terms And Conditions");
	assertEquals(winFreeSteaksPage.getIAgreeErrorMessage(), Messages.WFS_I_AGREE_CHECKBOX_ERROR_MESSAGE, "Warning message \"Please Accept Our Terms And Conditions\" does not display");
    }

    public void fillInvalidPhoneAndEmailInWinFreeSteaksPage() {
	Logger.info("In Win Free Steaks page, fill invalid information into email address and phone number fields" + "Don't tick I agree checkbox" + "Click \"Enter to win\" button");
	invalidAddress = customerAddress.clone();
	invalidAddress.updateFieldValueBy(AddressFields.EMAIL, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 20));
	invalidAddress.updateFieldValueBy(AddressFields.PHONE, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 9));
	winFreeSteaksPage.fillEmailAddress(invalidAddress.email);
	winFreeSteaksPage.fillCustomerAddress(invalidAddress);
	winFreeSteaksPage.clickEnterToWinInWinFreeSteakPage();
    }

    public void goToWinFreeSteaksPage() {
	Logger.info("In Hompage, go to Win Free Steaks page");
	generalPage.goToWinFreeSteaksPage();
    }

    public void initTestCaseData() {
	Logger.tc("TC_WSN_002 Warning messages display when signing up with invalid email address and phone number");
	Logger.to("TO_WSN_07 In Win Free Steaks page, Warning message \"Please Accept Our Terms And Conditions\" displays when signing up without tick \"I agree to the Terms of this contest as well as the Omaha Steaks' Terms of Use and Privacy Policy\" checkbox");
	Logger.to("TO_WSN_08 In Win Free Steaks page, Warning message \"Please Enter A Valid Email Address\" displays in \"Email Address\" field when signing up with invalid email address");
	Logger.to("TO_WSN_09 In Win Free Steaks page, Warning message \"Please Enter A Valid Phone Number\" displays in \"Phone\" field when signing up with invalid phone number");
	customerAddress = lstAddress.getDefaultShippingAddress();
    }
}
