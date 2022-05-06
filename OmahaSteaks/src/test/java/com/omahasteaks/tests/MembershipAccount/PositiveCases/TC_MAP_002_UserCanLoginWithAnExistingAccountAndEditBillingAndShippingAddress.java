package com.omahasteaks.tests.MembershipAccount.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAP_002_UserCanLoginWithAnExistingAccountAndEditBillingAndShippingAddress extends TestBase_2SC {

	@Inject
	CustomerAddress billingAddress, shippingAddress, editedBillingAddress, editedShippingAddress;

	@Inject
	ListAddresses lstAddresses;

	Recipient rec;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	Account newAccount;

	@Inject
	ProductPage productPage;

	@Inject
	Package mySku;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	PaymentAndReviewPage2SC paymentAndreviewPage2SC;

	@Inject
	ConfirmationPage2SC confirmationPage2SC;

	@Test
	public void TC_MAP_002_User_Can_Login_With_An_Existing_Account_And_Edit_Billing_And_Shipping_Address() {
		initTestCaseData();
		
		signIn();

		gotoAddressBookAndClickEditBillingAddress();

		fillBillingAddressAndClickUpdate(billingAddress);

		fillShippingAddressAndClickUpdate(shippingAddress);

		signOut();

		signIn();

		verifyMyAccountDisplaysOnUpperRightCornerOfTheScreen();

		verifyWelcomeRegisteredEmailDisplaysCorrectly();

		gotoAddressBookAndClickEditBillingAddress();

		fillBillingAddressAndClickUpdate(editedBillingAddress);

		verifyBillingAddessDisplayCorrectlyInMyAccountPage();

		fillShippingAddressAndClickUpdate(editedShippingAddress);

		verifyShippingAddressDisplayCorrectlyInMyAccountPage();

		seachAndAddItem(mySku);

		checkOutFromShoppingCart();

		verifyShippingAddressDisplayCorrectlyInShippingAddressPage();

		clickContinueFromShippingAddress();

		fillCreditCardNumberAndPlaceOrder();

		verifyBillingAddressDisplayCorrectlyInPayMentPage();
 	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyWelcomeRegisteredEmailDisplaysCorrectly() {
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			Logger.verify("In My Account page:\n" + "Verify " + account.getEmail() + "\" displays correctly");
			assertEquals(myAccountPage.getWelcomeText().toUpperCase(), newAccount.getEmail().toUpperCase(), newAccount.getEmail() + "\" does not display correctly");
		}
	}

	private void verifyBillingAddressDisplayCorrectlyInPayMentPage() {
		Logger.verify("In Confirmation page:\n" + "Verify the Billing Address displays correctly");
		confirmationPage2SC.closeModal();
		assertEquals(confirmationPage2SC.getBillingAddress(), editedBillingAddress.toBillingInformationArray(), "Billing address does not display correctly in payment page");
	}

	private void fillCreditCardNumberAndPlaceOrder() {
		Logger.step("In \"Payment And Review\" page: Pay for order and place order.");
		paymentAndreviewPage2SC.fillCreditCardNumber();
		paymentAndreviewPage2SC.placeOrder();
	}

	private void clickContinueFromShippingAddress() {
		Logger.step("In Shipping Address Page:\n" + "- Click \"Continue\" button");
		shippingAddressPage2SC.clickContinueButton();
	}

	private void verifyShippingAddressDisplayCorrectlyInShippingAddressPage() {
		Logger.verify("In Shipping Address page:\n" + "Verify the Shipping Address displays correctly");
		assertEquals(shippingAddressPage2SC.getRecipientAddress(), editedShippingAddress.toShippingArray(), "Failed by OMASTK_DE_021");
	}

	private void checkOutFromShoppingCart() {
 		shoppingCartPage.checkOut();
	}

	private void seachAndAddItem(SKU sku) {
		Logger.step("Search for item " + sku.getId() + " shipping to \"Myself\" and checkout.");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
	}

	private void verifyShippingAddressDisplayCorrectlyInMyAccountPage() {
		Logger.verify("In Address Book page (of Account Page)\n" + " - Verify the Shipping Address displays correctly");
		assertEquals(myAccountPage.getMyselfShippingAddress(), editedShippingAddress.toAddressArrayForMyAccountPage());
	}

	private void verifyBillingAddessDisplayCorrectlyInMyAccountPage() {
		Logger.verify("In Address Book page (of Account Page)\n" + " - Verify the Billing Address displays correctly");
		assertEquals(myAccountPage.getBillingAddress(), editedBillingAddress.toAddressArrayForMyAccountPage(), "Billing Address displays incorrectly");
	}

	private void verifyMyAccountDisplaysOnUpperRightCornerOfTheScreen() {
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			Logger.verify("In Homepage:\n" + "Verify \"My Account\" text exists at upper right corner of the screen.");
			assertTrue(generalPage.isMyAccountInLinkExisted(), "\"My Account\" text does not display at upper right corner of the screen");
		}
	}

	private void signIn() {
		Logger.step("Log into an existing account");
		generalPage.goToSignInPage();
		signInPage.signIn(newAccount);
	}

	private void signOut() {
		Logger.step("In My Account page:\n" + "  - Click on \" Sign out\" link");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE))
			generalPage.goToSignInPage();
		myAccountPage.signOut();

	}

	private void fillShippingAddressAndClickUpdate(CustomerAddress shippingAddress) {
		Logger.step("Fill out shipping address information for myself.");
		myAccountPage.goToEditAddressPageByRecipient(Recipient.MYSELF);
		myAccountPage.fillShippingAddress(shippingAddress);
		myAccountPage.clickUpdateButton();
	}

	private void fillBillingAddressAndClickUpdate(CustomerAddress billingAddress) {
		Logger.step("Fill out billing address information for billing address.");
		myAccountPage.fillBillingAddress(billingAddress);
		myAccountPage.clickUpdateButton();
	}

	private void gotoAddressBookAndClickEditBillingAddress() {
		Logger.step("Go to address book and click edit billing address.");
		//goToMyAccountPageFromHomepage();
		myAccountPage.goToMyAccountAddressBook();
		myAccountPage.goToEditBillingAddressPage();
	}

	public void goToMyAccountPageFromHomepage() {
 		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			generalPage.goToMyAccountPage();
		} else {
			generalPage.goToSignInPage();
		}
	}


	private void initTestCaseData() {
		Logger.reset();
		Logger.tc("TC_MAP_002 - User Can Login With An Existing Account And Edit Billing & Shipping Address\n");
		Logger.to("TO_MAP_02 - User can login with an existing membership account\n");
		Logger.to("TO_MAP_04 - User can edit the buyers address information in Address Book\n");
		Logger.to("TO_MAP_06 - User can edit the shipping address information for Myself\n");
		Logger.step("Go to " + Constants.OMAHA_URL);
 		newAccount = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop09@omahasteaks.com");

		mySku.initRandom(Recipient.MYSELF);
		billingAddress.initRandomInformation();
		shippingAddress.initRandomInformation();
		editedBillingAddress.initRandomInformation();
		editedShippingAddress.initRandomInformation();
		shippingAddress.aptSuite = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS.replace("0", "1"), 3);
		billingAddress.aptSuite = Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS.replace("0", "1"), 3);
		editedShippingAddress.setStreetAdress1("10909 JOHN GALT BVD");
		editedShippingAddress.setCity("Omaha");
		editedShippingAddress.setState("Nebraska");
		editedShippingAddress.setAptSuite("");
		editedShippingAddress.setZipCode("68137");
		editedBillingAddress.setAptSuite("");
	}
 
}
