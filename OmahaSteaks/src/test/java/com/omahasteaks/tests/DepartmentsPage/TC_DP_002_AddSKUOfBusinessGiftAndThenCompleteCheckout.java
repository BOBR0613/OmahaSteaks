package com.omahasteaks.tests.DepartmentsPage;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.helper.Logger;

public class TC_DP_002_AddSKUOfBusinessGiftAndThenCompleteCheckout extends TestBase_2SC {
	CustomerAddress myAddress;

	@Inject
	ListAddresses lstAddress;

	@Inject
	ListSKUs myCart;

	@Inject
	SKU sku;

	@Inject
	GeneralPage generalPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;

	@Inject
	ConfirmationPage2SC confirmationPage2SC;

	@Test
	public void TC_DP_002_Add_SKU_Of_Business_Gift_And_Then_Complete_Checkout() {
		initTestCaseData();

		goToBusinessGiftsPage();

		addFirstSKUToCart(sku, myCart);

		verifyThatAddedItemsDisplayCorrectly(myCart);

		checkOutFromShoppingCart();

		fillShippingAddressAndClickContinue();

		fillBillingAddress();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void verifyThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its");
		assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
	}

	public void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(myAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	public void fillBillingAddress() {
		Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
		paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	}

	public void fillShippingAddressAndClickContinue() {
		Logger.info("Fill all valid informations in Shipping Address page" + "Click Continue");
		shippingAddressPage2SC.fillShippingAddress(myAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	public void checkOutFromShoppingCart() {
		Logger.info("In Shopping Cart page, click \"Check out\" button");
		shoppingCartPage.checkOut();
	}

	public void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
		Logger.verify("Verify that these added items with correct information exist in Shopping Cart Page");
		for (SKU sku : myCart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
		}
	}

	public void addFirstSKUToCart(SKU sku, ListSKUs listSku) {
		Logger.info("Add the first SKU and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Continue Shopping\"");
		generalPage.addFirstSKUToCart(sku);
		generalPage.selectExclusiveOffer(false);
		generalPage.getAddedToCartSKUList(listSku);
		generalPage.checkOut();
	}

	public void goToBusinessGiftsPage() {
		Logger.info("Go to Business Gift page");
		generalPage.goToDepartmentPage("Business Gifts");
	}

	public void initTestCaseData() {
		Logger.tc("TC_DP_002 Add Sku of Busineess Gift and then complete checkout");
		Logger.to("TO_DP_04 Add SKU of Business Gift then complete checkout");
		myCart.initEmpty();
		sku.setRecipient(Recipient.MYSELF); 
		myAddress = lstAddress.getBillingAddressByState("Nebraska");
		System.out.println("My state is " + myAddress.state);
	}
}
