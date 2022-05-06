package com.omahasteaks.tests.MembershipAccount.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAP_008_UserCanUpdateShippingAddressForMyseftAsTheSameAsBillingAdrressInAddressBook extends TestBase_2SC {

    @Inject
    CustomerAddress shippingAddress, updateShippingAddress, billingAddress;

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
    public void TC_MAP_008_User_Can_Update_Shipping_Address_For_Myseft_As_The_Same_As_Billing_Adrress_In_Address_Book() {

	initTestCaseData();

	signIn();

	gotoAddressBookAndClickEditBillingAddress();

	fillBillingAddressAndClickUpdate();

	verifyBillingAddessDisplayCorrectlyInMyAccountPage();

	fillNewShippingAddressAndClickUpdate(shippingAddress);

	verifyShippingAddressDisplayCorrectlyInMyAccountPage();

	fillNewShippingAddressAndClickUpdate(updateShippingAddress);

	verifyNewShippingAddressDisplaysAsTheSameAsBillingAddressInMyAccountPage();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyShippingAddressDisplayCorrectlyInMyAccountPage() {
	Logger.verify("In Address Book page (of Account Page)" + " - Verify the Shipping Address displays correctly");
	assertEquals(myAccountPage.getMyselfShippingAddress(), shippingAddress.toAddressArrayForMyAccountPage(), "Shipping Address does not display correctly");
    }

    private void verifyNewShippingAddressDisplaysAsTheSameAsBillingAddressInMyAccountPage() {
	Logger.verify("In Address Book page (of Account Page)" + " - Verify the Shipping Address displays as the same as Billing Address");
	assertEquals(myAccountPage.getMyselfShippingAddress(), myAccountPage.getBillingAddress(), " Shipping Address does not display as the same as Billing Address");
    }

    private void fillNewShippingAddressAndClickUpdate(CustomerAddress customerAddress) {
	Logger.info("In Address Book page (of My Account Page):" + "In Shipping Address section:" + " - Click \"Edit\" button for Myself" + "In Edit Shipping Address page (of My Account Page )" + " - Fill all information as the same as all Billing Address information" + " - Click on \"Update\" button.");
	myAccountPage.goToEditAddressPageByRecipient(Recipient.MYSELF);
	myAccountPage.fillShippingAddress(customerAddress);
	myAccountPage.clickUpdateButton();
    }

    private void verifyBillingAddessDisplayCorrectlyInMyAccountPage() {
	Logger.verify("In Address Book page (of Account Page)" + " - Verify the Billing Address displays correctly");
	assertEquals(myAccountPage.getBillingAddress(), billingAddress.toAddressArrayForMyAccountPage(), "Billing Address does not display correctly");
    }

    private void fillBillingAddressAndClickUpdate() {
	Logger.info("In Edit Billing Address page (of My Account Page ): " + "- Fill all valid information" + "- Uncheck \"Check if Shipping address is the same as the Billing address \" checkbox" + "- Click on \"Update\" button");
	myAccountPage.fillBillingAddress(billingAddress);
	myAccountPage.clickUpdateButton();
    }

    private void gotoAddressBookAndClickEditBillingAddress() {
	Logger.info("In Homepage : " + "- Click on \"My Account\" link" + "- Click on \"Address Book\" link on left navigation menu" + "- Click \"Edit\" button in the Billing Address section");
	generalPage.goToMyAccountPage();
	myAccountPage.goToMyAccountAddressBook();
	myAccountPage.goToEditBillingAddressPage();
    }

    private void initTestCaseData() {
	Logger.tc("TC_MAP_008 - User can update Shipping Address for Myself as the same as Billing Address in Address Book");
	Logger.to("TO_MAP_20 - User can update the shipping address for Myself as the same as the billing address which is updated in Address Book");
	newAccount = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop07@omahasteaks.com");
	shippingAddress.initRandomInformation();
	billingAddress.initRandomInformation();
	updateShippingAddress = shippingAddress.clone();
	updateShippingAddress.updateFieldValueBy(AddressFields.FIRST_NAME, billingAddress.firstName);
	updateShippingAddress.updateFieldValueBy(AddressFields.LAST_NAME, billingAddress.lastName);
    }


	private void signIn() {
		Logger.info("Login with the account (step2) has been created");
		generalPage.goToSignInPage();
		signInPage.signIn(newAccount);
	}

}
