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

public class TC_MAP_001_UserCanCreateNewAccountAndAddBillingAndShippingAddress extends TestBase_2SC {

	@Inject
	CustomerAddress billingAddress, shippingAddress;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

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
	public void TC_MAP_001_User_Can_Create_New_Account_And_Add_Billing_And_Shipping_Address() {
		initTestCaseData();

		createNewAccount(account);

		verifyMyAccountDisplaysOnUpperRightCornerOfTheScreen();

		goToMyAccountPageFromHomepage();

		verifyWelcomeRegisteredEmailDisplaysCorrectly();

		gotoAddressBookAndClickEditBillingAddress();

		fillBillingAddressAndClickUpdate();

		verifyBillingAddessDisplayCorrectlyInMyAccountPage();

		fillShippingAddressAndClickUpdate();

		verifyShippingAddressDisplayCorrectlyInMyAccountPage();

		seachAndAddItem(mySku);

		checkOutFromShoppingCartPage();

		verifyShippingAddressDisplayCorrectlyInShippingAddressPage();

		clickContinueFromShippingAddress();

		fillCreditCardNumberAndPlaceOrder();

		verifyBillingAddressDisplayCorrectlyInPayMentPage();

		goToMyAccountfromConfirmPage();

		deteleMyAccount();

		signInWithDeletedAccount();

		verifyErrorMessageDisplaysCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyErrorMessageDisplaysCorrectly() {
		Logger.verify("In My Account page:\n" + "Verify error message \"No account found\n" + "Please check your email address or create a new account.\" is displayed");
		assertEquals(myAccountPage.getErrorMessage(), Constants.ERROR_MESSAGE);
	}

	private void signInWithDeletedAccount() {
		Logger.info(" In In \"My account\" page : \n" + "  - Login with the account (step2) has been deleted");
		signInPage.signIn(account);
	}

	private void deteleMyAccount() {
		Logger.info("In \"My account\" page : \n" + "  - Click \"Delete My Account\"\n" + "  - Enter password into textbox\n" + "  - Click on \"Yes, Delete My Information\" button");
		myAccountPage.selectAccountSettingOption(Constants.DELETE_MY_ACCOUNT);
		myAccountPage.fillPasswordToDeleteAccount(account.getPassword());
		myAccountPage.clickYesDeleteMyInformation();
	}

	private void goToMyAccountfromConfirmPage() {
		Logger.info("In Confirmation page: \n" + "  - Click \"My account\" link");
		confirmationPage2SC.goToMyAccountPage();
	}

	private void fillCreditCardNumberAndPlaceOrder() {
		Logger.info("In \"Payment And Review\" page: \n" + "  - Fill \" 4111111111111111\" number at Credit / Debit section\n" + "  - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "  - Click \"Place Order\" button");
		paymentAndreviewPage2SC.fillCreditCardNumber();
		paymentAndreviewPage2SC.placeOrder();
	}

	private void clickContinueFromShippingAddress() {
		Logger.info("In Shipping Address Page:\n" + "- Click \"Continue\" button");
		shippingAddressPage2SC.clickContinueButton();
	}

	private void fillBillingAddressAndClickUpdate() {
		Logger.info("In Edit Billing Address page (of My Account Page ):  \n" + "- Fill all valid information\n" + "- Uncheck \"Check if Shipping address is the same as the Billing address \" checkbox\n" + "- Click on \"Update\" button");
		myAccountPage.fillBillingAddress(billingAddress);
		myAccountPage.clickUpdateButton();
	}

	private void gotoAddressBookAndClickEditBillingAddress() {
		goToMyAccountPageFromHomepage();
		Logger.info("- Click on \"Address Book\" link on left navigation menu\n" + "- Click \"Edit\" button in the Billing Address section");
		myAccountPage.goToMyAccountAddressBook();
		myAccountPage.goToEditBillingAddressPage();
	}

	private void verifyWelcomeRegisteredEmailDisplaysCorrectly() {
		Logger.verify("In My Account page:\n" + "Verify " + account.getEmail() + "\" displays correctly");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			assertEquals(myAccountPage.getWelcomeText(), account.getEmail(), account.getEmail() + "\" does not display correctly");
	}

	private void goToMyAccountPageFromHomepage() {
		Logger.info("In Homepage:\n" + "- Click \"My Account\" link");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			generalPage.goToMyAccountPage();
		} else {
			generalPage.goToSignInPage();
		}
	}

	private void createNewAccount(Account account) {
		Logger.info("In Homepage:\n" + "- Click 'Sign In' link" + "In \"My account\" page: \n" + "- Click \"Create New Account\" button\n" + "- Enter all valid information\n" + "- Uncheck \"Join Steaklover Rewards \" checkbox\n" + " - Click \"Join\" button");
		generalPage.goToSignInPage();
		signInPage.clickCreateNewAccountButton();
		myAccountPage.createNewAccount(account);
	}

	public void fillShippingAddressAndClickUpdate() {
		Logger.info("In Address Book page (of My Account Page):\n" + "In Shipping Address section:\n" + "  - Click \"Edit\" button for Myself\n" + "In Edit Shipping Address page (of My Account Page )\n" + "  - Fill all valid information.\n" + "  - Click on \"Update\" button.");
		myAccountPage.goToEditAddressPageByRecipient(Recipient.MYSELF);
		myAccountPage.fillShippingAddress(shippingAddress);
		myAccountPage.clickUpdateButton();
	}

	private void seachAndAddItem(SKU sku) {
		Logger.info("Search a SKU with" + sku.getId() + "In Product Page:\n" + " - Leave \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "-Click \"Checkout\" button");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	}

	private void verifyMyAccountDisplaysOnUpperRightCornerOfTheScreen() {
		Logger.verify("In Homepage:\n" + "Verify \"My Account\" text exists at upper right corner of the screen.");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			assertTrue(generalPage.isMyAccountInLinkExisted(), "My Account text does not exist at upper right corner of the screen.");
	}

	private void verifyBillingAddessDisplayCorrectlyInMyAccountPage() {
		Logger.verify("In Address Book page (of Account Page)\n" + " - Verify the Billing Address displays correctly");
		assertEquals(myAccountPage.getBillingAddress(), billingAddress.toAddressArrayForMyAccountPage(), "Billing Address does not display correctly");
	}

	private void verifyShippingAddressDisplayCorrectlyInMyAccountPage() {
		Logger.verify("In Address Book page (of Account Page)\n" + " - Verify the Shipping Address displays correctly");
		assertEquals(myAccountPage.getMyselfShippingAddress(), shippingAddress.toAddressArrayForMyAccountPage(), "Shipping Address does not display correctly");
	}

	private void verifyShippingAddressDisplayCorrectlyInShippingAddressPage() {
		Logger.verify("In Shipping Address page:\n" + "Verify the Shipping Address displays correctly");
		assertEquals(shippingAddressPage2SC.getRecipientAddress(), shippingAddress.toShippingArray(), "Failed by OMASTK_DE_021");
	}

	private void verifyBillingAddressDisplayCorrectlyInPayMentPage() {
		Logger.verify("In Confirmation page:\n" + "Verify the Billing Address displays correctly");
		confirmationPage2SC.closeModal();
		assertEquals(confirmationPage2SC.getBillingAddress(), billingAddress.toBillingArray(), "Failed by OMASTK_DE_021");
	}

	private void initTestCaseData() {
		Logger.tc("TC_MAP_001 - User Can Create New Account And Add Billing & Shipping Address\n");
		Logger.to("TO_MAP_01 - User can create a membership account by email address\n");
		Logger.to("TO_MAP_05 - User can add the buyer address information in Address Book \n");
		Logger.to("TO_MAP_07 - User can add the shipping address information for Myself\n");
		Logger.to("TO_MAP_03 - User can delete an existing membership account\n");
		account.initRandomAccount();
		mySku.initRandom(Recipient.MYSELF);
		billingAddress.initRandomInformation();
		billingAddress.aptSuite = "";  //Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS.replace("0", "1"), 3);
		shippingAddress.initRandomInformation();
		shippingAddress.aptSuite = ""; //Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS.replace("0", "1"), 3);
	}
}
