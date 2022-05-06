package com.omahasteaks.tests.MinimumThreshold;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class TC_MIN_003CartBelowMinThresholdCannotCheckout extends TestBase_2SC {

	@Inject
	GeneralPage generalPage;

	@Inject
	CustomerAddress shippingAddress, billingAddress;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	SKU sku;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Inject
	ConfirmationPage2SC confirmationPage;


	@Test
	public void TC_MIN_003Cart_Below_Min_Threshold_CANNOT_Checkout() {

		initTestCaseData();

		addDessertItemToCart();

		generalPage.checkOut();

		assertTrue(shoppingCartPage.isMinThresholdMessageShown(), "Minimum threshold error message is not shown on items below the threshold.");

		assertTrue(!shoppingCartPage.isCheckoutButtonDisplayed(), "Checkout button should not be displayed when all carts are below the minimm threshold.");

	}



	public void addDessertItemToCart() {
		Logger.info("Go to Desserts page");
		generalPage.goToDepartmentPage("Desserts");
		generalPage.addFirstSKUToCart(sku);
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
		Logger.tc("TC_MIN_003 Cart below the minimum threshold cannot checkout");
		Logger.to("TO_MIN_01 - Add an item below minimum threshold to cart");
		Logger.to("TO_MIN_02 - Attempt to go to checkout");
		billingAddress = lstAddresses.getRandomBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		sku.setRecipient(Recipient.MYSELF); 
		Common.modalDialog.closeSavorDialog();
	}
 

}
