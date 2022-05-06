package com.omahasteaks.tests.SalePage.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.ProgressiveOfferTab;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.SalePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_SPP_002_UserCanRegisterEmailToReceiveEmailInEarnAnExtra10PercentOffTab extends TestBase_2SC {
	@Inject
	GeneralPage generalPage;
	@Inject
	SalePage salePage;
	@Inject
	Account newAccount;

	@Test
	public void TC_SPP_002_User_Can_Register_Email_To_Receive_Email_In_Earn_An_Extra_10_Percent_Off_Tab() {

		initTestCaseData();

		goToSideMenuItemPage(SideMenuItem.SALE);

		goToEarnAnExtra10PercentOffTabInSalePage();

		fillValidEmailAndClickSubmit();

		verifyUserCanSignUpForEmailSuccessful();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void verifyUserCanSignUpForEmailSuccessful() {
		Common.waitForDOMChange();
		Logger.verify("Verify \"You're In!\" popup displays when signing up for emails successful");
		assertTrue(salePage.isYouAreInModalDisplayed(), "\"You're In!\" popup does not display when signing up for emails successful");
	}

	public void fillValidEmailAndClickSubmit() {
		Logger.info("Fill valid email in \"Earn an Extra 10% off\" tab");
		salePage.fillEmailAddressInProgressiveOffersTab(newAccount.getEmail());
		salePage.clickIAgreeCheckbox();
		salePage.clickSubmitInSalePage();
	}

	public void goToEarnAnExtra10PercentOffTabInSalePage() {
		Logger.info("Go to Earn an Extra 10% Off Tab in Sale page");
		salePage.clickProgressiveOfferTabByName(ProgressiveOfferTab.EARN_AN_EXTRA_25_DOLLARS_OFF);
	}

	public void goToSideMenuItemPage(SideMenuItem sideMenuItemName) {
		Logger.info("In Homepage, click SALE link in Side Menu Item link");
		generalPage.clickSideMenuItemLink(sideMenuItemName);
		Common.modalDialog.closeSavorDialog();
	}

	public void initTestCaseData() {
		Logger.tc("TC_SPP_002 User Can Register Email To Receive Email In Earn An Extra 10 Percent Off Tab");
		Logger.to("TO_SPP_02 \"You're In!\" popup displays when signing up for emails successful");
		newAccount.initRandomAccount();
	}
}
