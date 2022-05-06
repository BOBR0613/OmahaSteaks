package com.omahasteaks.tests.MinimumThreshold;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test; 

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient; 
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_MIN_004OnlyCartsAboveMinThresholdAreProcessed extends TestBase_2SC {

	@Inject
	GeneralPage generalPage;

	@Inject
	CustomerAddress shippingAddress, billingAddress;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	SKU mysku, rcvrsku;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Inject
	ConfirmationPage2SC confirmationPage;


	@Test
	public void TC_MIN_004Only_Carts_Above_Min_Threshold_Are_Processed() {

		initTestCaseData();

		addDessertItemToMyCart(); 
		
		addDessertItemToNewCart();

		assertTrue(shoppingCartPage.isMinThresholdMessageShown(), "Minimum threshold error message is not shown on items below the threshold.");

		assertFalse(shoppingCartPage.isCheckoutButtonDisplayed(), "Checkout button should not be displayed when all carts are below the minimum threshold.");		
	}



	public void addDessertItemToMyCart() {
		Logger.info("Go to Desserts page");
		generalPage.goToDepartmentPage("Desserts");
		generalPage.addFirstSKUToCart(mysku); 
		generalPage.checkOut();
	}

	
	public void addDessertItemToNewCart() {
		Logger.info("Go to Gifts page");
		generalPage.goToDepartmentPage("Desserts");
		generalPage.addFirstSKUToCart(rcvrsku);
		generalPage.checkOut();
	}

	protected void fillShippingAddressAndContinueCheckout(CustomerAddress address) {
		Logger.info("In Shipping Address form:\n" + " - Enter valid information\n" + " - Click Continue Checkout");
		shippingAddressPage.fillShippingAddress(address);
		shippingAddressPage.clickContinueButton();
	}

	protected void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date:we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
		paymentAndReviewPage.fillCreditCardNumber();
		paymentAndReviewPage.fillBillingAddress(billingAddress);
		paymentAndReviewPage.placeOrder();
	}


	protected void verifyShippingAddressesDisplayCorrectly(Recipient recipient, CustomerAddress expectedShippingAddress) {
		Logger.verify("In Payment Option and Review page, verify that:\n" + " - Added Shipping address displays correctly");
		assertEquals(paymentAndReviewPage.getShippingAddress(recipient.getValue()), shippingAddress.toShippingArray(), "The shipping address is not displayed as expected");
	}

	protected void verifyBillingAddressAndThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
		assertEquals(confirmationPage.getBillingAddress(), billingAddress.toBillingArray(), "The billing address is not displayed as expected");
	}

	private void initTestCaseData() {
		Logger.tc("TC_MIN_004 - Only carts above the minimum threshold are processed when submitting order");
		Logger.to("TO_MIN_01 - Add cart to self above the minimum and a cart to guest not meeting requirement");
		Logger.to("TO_MIN_02 - Only cart to self is shown on payment & review page");
		billingAddress.getCustomerAddress("001739909");
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		mysku.setRecipient(Recipient.MYSELF); 
		rcvrsku.setRecipient(Recipient.KIM_ANH);
		Common.modalDialog.closeSavorDialog();
	}
 

}
