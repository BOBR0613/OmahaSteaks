package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_004_UserSelectsAndEditsExistingAddressThenCompleteCheckout extends TestBase_2SC {
	Account TC_2SCOP_004;
	Recipient recipient;
	@Inject
	ListSKUs myCart;
	@Inject
	Package mySku;
	@Inject
	CustomerAddress updatedShippingAddress;
	@Inject
	CustomerAddress recipientShippingAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	SignInPage signInPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	CategoryPage categoryPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	MyAccountPage myAccountPage;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	ConfirmationPage2SC confirmationPage2SC;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_2SCOP_004_User_Selects_And_Edits_Existing_Address_Then_Complete_Checkout() {
		initTestCaseData();

		signInWithExistingAccount();

		getRecipientsAddressFromMyAccountPage(recipient);

		addSKUToCartAndCheckOut(mySku);

		selectRecipient(recipient);

	//	verifyRecipientsAddressAndNotifyMessage(recipient);

		DriverUtils.getWebDriver().get(Common.currentUrlPage);

		editRecipientAddress();

		verifyShippingAddressDisplaysCorrectly(updatedShippingAddress, recipient);

//		fillCreditCardNumberThenPlaceMyOrder();

//		verifyThankYouMessageDisplays();

//		myAccountPage.cleanUpAddressBook();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSKUToCartAndCheckOut(SKU sku) {
		Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		Common.waitForPageLoad();
		productPage.addSKUToCart(sku, false);
		Common.waitForPageLoad();
 		generalPage.checkOut();
 		Common.waitForPageLoad();
		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
		Common.waitForPageLoad();
	}

	private void editRecipientAddress() {
		Logger.info("In Shipping Address Page\n" + " - Click \"Edit this address\" link\n" + " - Edit this address\n" + " - Click \"Update Contact\"\n" + " - Click \"Continue\"");
		// shippingAddressPage2SC.closeSendToDropDownList();
		Common.waitForPageLoad();
		shippingAddressPage2SC.clickEditThisAddressLink();
		Common.waitForDOMChange();
		shippingAddressPage2SC.fillShippingAddressInModal(updatedShippingAddress);
		shippingAddressPage2SC.updateContactInModal(); 
		shippingAddressPage2SC.clickContinueButton();
	}

	private void fillCreditCardNumberThenPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.placeOrder();
	}

	private void getRecipientsAddressFromMyAccountPage(Recipient recipient) {
		Logger.info("Get recipient's address from My Account Page");
		myAccountPage.goToMyAccountAddressBook();
		myAccountPage.goToEditAddressPageByRecipient(recipient);
		recipientShippingAddress = myAccountPage.getAddressInEditAddressPage();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_004 - User Selects And Edits Existing Address Then Complete Checkout");
		Logger.to("TO_2SCOP_07 - User can select an exist address (select an exist value in \"SEND TO\") in Shipping Address Page then complete check out");
		Logger.to("TO_2SCOP_08 - In Shipping Address, after selecting an existing address, user can edit this selected address by clicking \"Edit this address\" link");
		Logger.to("TO_2SCOP_09 - In Shipping Address, after selecting an existing address, selected value in \"Send To \" dropdown, is adding into How would you like to notify \"...\" of delivery? Question");
		mySku.initRandom(Recipient.MYSELF);
		TC_2SCOP_004 = new Account();
		TC_2SCOP_004.setEmail("testDesktop04@omahasteaks.com");
		TC_2SCOP_004.setPassword("qaR3gT3st04");
		recipient = Recipient.TC_2SCOP_004;
		updatedShippingAddress.initRandomInformation();
		updatedShippingAddress.email = "";
	}

	private void selectRecipient(Recipient recipient) {
		Logger.info("In Shipping Address Page\n" + " - Select an existing Recipient");
		Common.waitForDOMChange();
		shippingAddressPage2SC.selectRecipientInSendToDropDownList(recipient);
	}

	private void signInWithExistingAccount() {
		Logger.info("Sign in with existing account");
		generalPage.goToSignInPage();
		signInPage.signIn(TC_2SCOP_004);
	}

	private void verifyRecipientsAddressAndNotifyMessage(Recipient recipient) {
		Logger.verify(" - Verify \"How would you like to notify \"<Recipient's name>\" of delivery?\" displays\n" + " - Verify the Recipient's address is displayed correctly");
		assertEquals(shippingAddressPage2SC.getRecipientAddressText(), shippingAddressPage2SC.generateRecipientAddressInfo(recipientShippingAddress));
	}

	private void verifyShippingAddressDisplaysCorrectly(CustomerAddress Address, Recipient recipient) {
		Logger.verify("In New Payment and Review Page, verify that:\n" + "- Added Shipping address displays correctly");
		Common.waitForPageLoad();
		//	assertEquals(paymentAndReviewPage2SC.getShippingAddress(recipient.getValue()), Address.toShippingArray(), "The shipping address is not displayed as expected");
	}

	private void verifyThankYouMessageDisplays() {
		confirmationPage2SC.closeModal();
		Logger.verify("In Order Receipt Page\n" + " - Verify that added Shipping address displays correctly\n" + " - Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.\n" + " - Verify Contain text \"Order Number:\" + an order number format having 12 characters.");
		assertEquals(confirmationPage2SC.getShippingAddressContent(updatedShippingAddress), updatedShippingAddress.toShippingArrayForConfirmationPage(), "The shipping address is not displayed as expected");
		assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank you message is not displayed");
		String oderNumber = confirmationPage2SC.getOrderNumberText();
		assertEquals(oderNumber.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage2SC.isOrderNumberCorrectFormat(oderNumber.split(":", 2)[1].trim()));
	}
}
