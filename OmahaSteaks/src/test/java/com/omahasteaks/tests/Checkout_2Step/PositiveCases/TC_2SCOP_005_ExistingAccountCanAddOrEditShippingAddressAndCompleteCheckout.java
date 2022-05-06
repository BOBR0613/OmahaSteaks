package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
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
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_005_ExistingAccountCanAddOrEditShippingAddressAndCompleteCheckout extends TestBase_2SC {
	@Inject
	Package mySku;
	@Inject
	CustomerAddress newShippingAddress;
	@Inject
	CustomerAddress updatedShippingAddress;
	@Inject
	CustomerAddress invalidShippingAddress;
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
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	ConfirmationPage2SC confirmationPage2SC;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ProductPage productPage;
	@Inject
	MyAccountPage myAccountPage;

	@Test
	public void TC_2SCOP_005_Existing_Account_Can_Add_Or_Edit_Shipping_Address_And_Complete_Checkout() {
		initTestCaseData();

		signInWithExistingAccount();

		addSKUToCartAndCheckOut(mySku);

//		editRecipientAddressByClickingEditLinkInPhoneSection();

//		verifyRecipientsAddressHasBeenUpdated();

		enterANewAddressByClickingEnterANewAddressLink();

		editRecipientsAddressByClickingEditAddressInPleaseConfirmAddressPopup();

		fillCreditCardNumberThenPlaceMyOrder();

		verifyThankYouMessageDisplays();

		myAccountPage.cleanUpAddressBook();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSKUToCartAndCheckOut(SKU sku) {
		Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();

		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	}

	private void editRecipientAddressByClickingEditLinkInPhoneSection() {
		Logger.info("In Shipping Address Page\n" + " - Select an existing Recipient\n" + " - Clicking \"Edit\" link beside Phone checkbox\n" + " - Edit this address to new address");
		shippingAddressPage2SC.selectNextRecipientAddressBySelected();
		shippingAddressPage2SC.editContactByClickingLinkInPhoneSection();
		updatedShippingAddress.initRandomInformation();
		shippingAddressPage2SC.fillShippingAddressInModal(updatedShippingAddress);
		shippingAddressPage2SC.updateContactInModal();
	}

	private void editRecipientsAddressByClickingEditAddressInPleaseConfirmAddressPopup() {
		Logger.info(" - Click \"Edit Address\" in \"Please Confirm Address\" popup\n" + " - Re-enter this address with valid ZipCode then click \"Continue\"");
		shippingAddressPage2SC.clickEditAddressButtonInPleaseConfirmAddressModal();
		newShippingAddress.initRandomInformation();
		newShippingAddress.phoneNumber = "";
		newShippingAddress.email = "";
		shippingAddressPage2SC.fillShippingAddress(newShippingAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void enterANewAddressByClickingEnterANewAddressLink() {
		Logger.info("In Shipping Address Page\n" + " - Click \"Enter a new address\" link beside \"Edit this address\" link\n" + " - Enter an address with invalid ZipCode then click \"Continue\"");
		shippingAddressPage2SC.clickEnterANewAddressLink();
		shippingAddressPage2SC.fillShippingAddress(invalidShippingAddress);
		Common.ignoreAlertPopup();
		shippingAddressPage2SC.clickContinueButton();
	}

	private void fillCreditCardNumberThenPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.placeOrder();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_005 Existing Account Can Add Or Edit Shipping Address And Complete Checkout");
		Logger.to("TO_2SCOP_02	User can login by an existing account then complete Checkout\n");
		Logger.to("TO_2SCOP_10	In Shipping Address, after selecting an existing address, user can edit this selected address by clicking \"Edit\" link beside Phone checkbox\n");
		Logger.to("TO_2SCOP_12	In Shipping Address, after edit an existing address, user can edit it again by clicking \"Edit Address\" in \"Please Confirm Address\" popup\n");
		Logger.to("TO_2SCOP_13	In Shipping Address, after selecting an existing address, user can enter a new address by clicking \"Enter a new address\" link beside \"Edit this address\" link\n");
		mySku.init(SkuType.OVER100,Recipient.MYSELF);
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("testDesktop01@omahasteaks.com");
		invalidShippingAddress.initRandomInformation();
		invalidShippingAddress.zipCode = "70000";
	}

	private void signInWithExistingAccount() {
		Logger.info("Sign in with existing account");
		generalPage.goToSignInPage();
		signInPage.signIn(account);
	}

	private void verifyRecipientsAddressHasBeenUpdated() {
		Logger.verify("In Shipping Address Page\n" + " - Verify the new address is updated");
		assertEquals(shippingAddressPage2SC.getRecipientAddressText(), shippingAddressPage2SC.generateRecipientAddressInfo(updatedShippingAddress));
	}
 
	private void verifyThankYouMessageDisplays() {
		confirmationPage2SC.closeModal();
		Logger.verify("In Order Receipt Page\n" + " - Verify that added Shipping address displays correctly\n" + " - Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.\n" + " - Verify Contain text \"Order Number:\" + an order number format having 12 characters.");
 		assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank you message is not displayed");
	}
}
