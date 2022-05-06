package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
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

public class TC_2SCOP_014_InformationInShipmentSummaryMatchWithNumberOfSKUsOfMyselfAndRecipientInMyCart extends TestBase_2SC {
	CustomerAddress billingAddress;
	CustomerAddress shippingAddress;
	@Inject
	ListSKUs lstSku;
	@Inject
	Package packageMyself;
	@Inject
	Package packageRecipient;
	@Inject
	ListAddresses lstAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	SignInPage signInPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;
	@Inject
	ConfirmationPage2SC confirmationPage;

	@Test
	public void TC_2SCOP_014_Information_In_Shipment_Summary_Match_With_Number_Of_SKUs_Of_Myself_And_Recipient_In_My_Cart() {
		initTestCaseData();

		searchAndAddpackageThenCheckout();

		verifyRecipientDisplaysInCorrectCart();

	//	verifyItemAppearsInCart1OfShippingAddressPage();

	//	verifyItemAppearsInCart2OfShippingAddressPage();

		checkOutFromShoppingCartPage();

		fillShippingAddress1AndClickContinueButton();

		fillShippingAddress2AndClickContinueButton();

		verifyInfoOfSKUInPaymentAndReviewPage();

 	 	fillCreditCardNumberAndPlaceMyOrder();

 	 	verifyThankYouMessageDisplays();

 	 	verifyOrderNumberInCorrectFormat();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyOrderNumberInCorrectFormat() {
		String getOrderNumberText = confirmationPage.getOrderNumberText();
		Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
		assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
	}

	private void verifyInfoOfSKUInPaymentAndReviewPage() {
		Logger.verify("In New Payment & Review Page, verify that:\n" + "1. Verify SKU's info in Cart 1 of 2 section.\n" + "2. Verify SKU's info in Cart 2 of 2 section.");
		for (SKU sku : lstSku.getList()) {
			Logger.info("Verifying existance of item " + sku.getId()+" "+sku.getName());
			assertTrue(paymentAndReviewPage.isSKUExisted(sku));
		}
	}

	private void fillShippingAddress1AndClickContinueButton() {
		Logger.info("In New Shipping Address Page:\n" + "- Fill valid information\n" + "- Click \"Continue\"");
		fillShippingAddress(billingAddress);
		shippingAddressPage.clickContinueButton();
	}

	private void fillShippingAddress2AndClickContinueButton() {
		Logger.info("In New Shipping Address Page:\n" + "- Fill valid information\n" + "- Click \"Continue\"");
		fillShippingAddress(shippingAddress);
		shippingAddressPage.clickContinueButton();
	}

 

	private void verifyItemAppearsInCart2OfShippingAddressPage() {
		Logger.verify("In Shipping Address page, verify that:\n" + "Shipping Address (2 of 2) - Kim: Verify correct item appears in Cart 2 of 2 section.");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			verifySKUInfoInShipmentSummary(lstSku.getList().get(1));
		}
	}

	private void verifyItemAppearsInCart1OfShippingAddressPage() {
		Logger.verify("In Shipping Address page, verify that:\n" + "Shipping Address (1 of 2) - Myself: Verify correct Item appears in Cart 1 of 2 section.");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			verifySKUInfoInShipmentSummary(lstSku.getList().get(0));
		}
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	}

	private void verifyRecipientDisplaysInCorrectCart() {
		Logger.verify("In Shopping-Cart page, \n" + "Verify Cart 1 of 2 and Cart 2 of 2 appear.\n" + "Cart 1 of 2 - Send To: " + Recipient.MYSELF.getValue() + "\n" + "Cart 2 of 2 - Send To: " + Recipient.THAO_NHO.getValue());
		assertEquals(shoppingCartPage.getCartShipmentTitleByRecipient(Recipient.MYSELF,1), "Myself");
		assertEquals(shoppingCartPage.getCartShipmentTitleByRecipient(Recipient.THAO_NHO,2), "Thao Nho");
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("In New Payment and Review Page:\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "- Click \"Place My Order\" button");
		paymentAndReviewPage.fillCreditCardNumber();
		paymentAndReviewPage.fillBillingAddress(billingAddress);
		paymentAndReviewPage.placeOrder();
	}

	private void fillShippingAddress(CustomerAddress address) {
		shippingAddressPage.fillShippingAddress(address);
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_014 - Information In Shipment Summary Match With Number Of SKUs Of Myself And Recipient In My Cart\n");
		Logger.to("TO_2SCOP_35 - Information in \"Shipment Summary\" match with number of SKUs of Myself and Recipient in My Cart\n");
		packageMyself.init(SkuType.PACKAGES, Recipient.MYSELF);
		packageRecipient.init(SkuType.PACKAGES, Recipient.THAO_NHO);
		lstSku.initEmpty();
		billingAddress = lstAddress.getRandomBillingAddress();
		shippingAddress = lstAddress.getDefaultShippingAddress();
	}

	private void searchAndAddpackageThenCheckout() {
		Logger.info("Search a SKU:" + packageMyself.getName() + "with id (randomly)\n" + "In Product Page:\n" + " - Leave \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Continue Shopping\"\n" + "- If on mobile device: Close pop up because of not having \"Continue Shopping\"  button");
		generalPage.search(Common.getNumberFromText(packageMyself.getId()));
		productPage.addSKUToCart(packageMyself, false);
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			generalPage.getAddedToCartSKUList(lstSku);
		productPage.continueShopping();

		Logger.info("Search a SKU" + packageRecipient.getName() + " with id (randomly)\n" + "In Product Page:\n" + " - Select Ship To \"Kim Anh\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Checkout\"\n" + "- If on mobile device: Click \"View my card\"");
		generalPage.search(Common.getNumberFromText(packageRecipient.getId()));
		productPage.addSKUToCart(packageRecipient, false);
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			generalPage.getAddedToCartSKUList(lstSku);
		productPage.checkOut();
	}

	private void verifySKUInfoInShipmentSummary(SKU sku) {
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			assertEquals(shippingAddressPage.isSKUExisted(sku), true, "The shipment summary is not displayed as expected");
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("In Order Receipt Page\n" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.");
		assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
	}
}
