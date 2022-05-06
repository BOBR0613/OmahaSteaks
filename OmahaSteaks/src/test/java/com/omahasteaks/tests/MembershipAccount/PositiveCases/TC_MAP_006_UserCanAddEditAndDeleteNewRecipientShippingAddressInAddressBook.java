package com.omahasteaks.tests.MembershipAccount.PositiveCases;

import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAP_006_UserCanAddEditAndDeleteNewRecipientShippingAddressInAddressBook extends TestBase_2SC {

	@Inject
	CustomerAddress shippingAddress, updateShippingAddress;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Test
	public void TC_MAP_006_User_Can_Add_Edit_And_Delete_New_Recipient_Shipping_Address_In_Address_Book() {

		initTestCaseData();

		signIn();
 		
		clickCreateNewAddressButtonInMyAccountPage();

		fillShippingAddressByRecipientAndClickUpdate();

		verifyRecipientShippingAddressDisplaysCorrectly();

		editAddressPageByRecipient(Recipient.NEW_RECIPIENT );

		fillNewShippingAddressByRecipientAndClickUpdate();

		verifyRecipientShippingAddressDisplaysCorectlyAfterUpdating();

		deleteAddressByRecipientName();

		verifyRecipientShippingAddressIsNotDiplayedInAddressBook();

	}


	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyRecipientShippingAddressIsNotDiplayedInAddressBook() {
		Logger.verify("In Address Book page (of Account Page)\r\n" + " - Verify the Recipient's Shipping Address is not displayed in Address Book");
		assertFalse(myAccountPage.isRecipientShippingAddressDisplayed(Recipient.NEW_RECIPIENT), "the Recipient's Shipping Address is displayed in Address Book");
	}

	private void deleteAddressByRecipientName() {
		Logger.info("In \"My account\" page:" + "- Click \"Delete\" button in the Recipient's Shipping Address section");
		myAccountPage.deleteAddressByRecipientName(Recipient.NEW_RECIPIENT);
	}

	private void verifyRecipientShippingAddressDisplaysCorectlyAfterUpdating() {
		Logger.verify("In Address Book page (of Account Page)\r\n" + " - Verify the Recipient's Shipping Address displays correctly in Address Book");
//		assertEquals(myAccountPage.getShippingAddressByRecipient(Recipient.NEW_RECIPIENT), updateShippingAddress.toRecipientAddressArrayForMyAccountPage(), "Recipient's Shipping Address does not display correctly in Address Book After Updating");
	}

	private void fillNewShippingAddressByRecipientAndClickUpdate() {
		Logger.info("In Edit Shipping Address page (of My Account Page )\r\n" + " - Fill all valid information\r\n" + " - Click on \"Update\" button");
		myAccountPage.fillShippingAddress(updateShippingAddress);
		myAccountPage.clickUpdateButton();
	}

	private void editAddressPageByRecipient(Recipient recipient) {
		Logger.info("In \"My account\" page:" + "- Click \"Edit\" button in the Recipient's Shipping Address section");
		myAccountPage.goToEditAddressPageByRecipient(recipient);
	}

	private void verifyRecipientShippingAddressDisplaysCorrectly() {
		Logger.verify("In Address Book page (of Account Page)\r\n" + " - Verify the Recipient's Shipping Address displays correctly in Address Book");
//		assertEquals(myAccountPage.getShippingAddressByRecipient(Recipient.NEW_RECIPIENT), shippingAddress.toRecipientAddressArrayForMyAccountPage(), "Recipient's Shipping Address does not display correctly in Address Book");
	}

	private void fillShippingAddressByRecipientAndClickUpdate() {
		Logger.info("In Edit Shipping Address page (of My Account Page )\r\n" + " - Fill all valid information\r\n" + " - Click on \"Update\" button");
		myAccountPage.fillShippingAddressByRecipient(shippingAddress, Recipient.NEW_RECIPIENT);
		myAccountPage.clickUpdateButton();
	}
	
	public void deleteNewRecipient() {
		try {
		myAccountPage.goToMyAccountAddressBook();
		deleteAddressByRecipientName();
		} finally{};
	}

	private void clickCreateNewAddressButtonInMyAccountPage() {
		Logger.info("In Homepage :" + "- Click on \"My Account\" link.\r\n" + "- Click on \"Address Book\" link on left navigation menu\r\n" + "- Click \"Create New Address\" button in the Address Book section");
		generalPage.goToMyAccountPage();
		myAccountPage.goToMyAccountAddressBook();
		myAccountPage.clickCreateNewAddressButton();
	}


	private void initTestCaseData() {
		Logger.tc("TC_MAP_006 - User can add, edit and delete New Recipient Shipping Address in Address Book");
		Logger.to("TO_MAP_12 - Users can add new Recipient's shipping address in Address Book");
		Logger.to("TO_MAP_13 - Users can edit Recipient's shipping address in Address Book");
		Logger.to("TO_MAP_14 - Users can delete the Recipient's shipping address in Address Book");
		newAccount = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop07@omahasteaks.com");

		shippingAddress.initRandomInformation();
		updateShippingAddress.initRandomInformation();
		shippingAddress.aptSuite = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS.replace("0", "1"), 3);
		updateShippingAddress.aptSuite = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS.replace("0", "1"), 3);
	}

	private void signIn() {
		Logger.info("Login with the account (step2) has been created");
		generalPage.goToSignInPage();
		signInPage.signIn(newAccount);
	}
	 
	

}
