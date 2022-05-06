package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_002_UserCanAddFoodToMyCartAndRecipientCartThenCompleteCheckout extends TestBase_2SC {
	CustomerAddress myAddress, recipientAddress;
	String myShippingMethod, myDeliveryDate, myShippingCost, recipientShippingMethod, recipientDeliveryDate, recipientShippingCost;

	Calendar christmasDate;

	@Inject
	Item myItem, recipientItem;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	ProductPage productPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;

	@Inject
	ConfirmationPage2SC confirmationPage2SC;

	@Test
	public void TC_SOP_002_User_Can_Add_Food_To_My_Cart_And_Recipient_Cart_Then_Complete_Checkout() {
		initTestCaseData();

		searchAndAddItem(myItem);

		searchAndAddItem(recipientItem);

		goToShoppingCartPageThenClickCheckout();

		verifyShippingOptionsDoesNotAppear();

		fillMyShippingAddressThenClickNextButton();

		verifyShippingOptionsDoesNotAppear();

		fillRecipientShippingAddressThenClickContinueButton();

		Logger.verify("In Payment & Review page: Verify Cart 1 of 2 section is Myself Cart, and Cart 2 of 2 section is Recipient's Cart");
		assertEquals(paymentAndReviewPage2SC.getRecipientSectionTitle(myItem.getRecipient().getValue()), "Cart 1 of 2", "Cart 1 of 2 section is not Myself Cart");
		assertEquals(paymentAndReviewPage2SC.getRecipientSectionTitle(recipientItem.getRecipient().getValue()), "Cart 2 of 2", "Cart 2 of 2 section is not Recipient's Cart");

	//	Logger.verify("In Payment & Review page: Verify \"View All Shipping Options\" link appears in Cart 1 of 2 section");
	//	assertTrue(paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(myItem.getRecipient().getValue()), "\"View All Shipping Options\" link does not appear in Cart 1 of 2 section");
	//	Logger.verify("In Payment & Review page: Verify \"View All Shipping Options\" link appears in Cart 2 of 2 section");
	//	assertTrue(paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(recipientItem.getRecipient().getValue()), "\"View All Shipping Options\" link does not appear in Cart 2 of 2 section");

		updateMyShippingOption();

		updateRecipientShippingOption();

		Logger.verify("In Payment & Review page: Shipping Method, Cost, and Estimated Delivery date appear in My Cart section");
		assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(myShippingMethod), "The Shipping method is not displayed correctly " + myShippingMethod);

		if (paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(Constants.CHRISTMAS_DELIVERY)) {
			Calendar dateDelivery = paymentAndReviewPage2SC.getDeliveryDate(Recipient.MYSELF.getValue());
			assertTrue(Common.isDateBetweenTwoDates(dateDelivery, Common.getDateBeforeNumberOfBusinessDays(christmasDate, 16), Common.getDateBeforeNumberOfBusinessDays(christmasDate, 9)), "Delivery Date does not arrive in 10 - 15 biz. days before Christmas from the day you place your order");
		} else {
			assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(myDeliveryDate), "The Delivery Date is not displayed correctly " + myDeliveryDate);
		}
		assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(myShippingCost), "The Shipping Cost is not displayed correctly " + myShippingCost);

		Logger.verify("In Payment & Review page: Shipping Method, Cost, and Estimated Delivery date appear in Recipient Cart section");
		assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.DD.getValue()).contains(recipientShippingMethod), "The Shipping method is not displayed correctly " + recipientShippingMethod);
		if (paymentAndReviewPage2SC.getShippingInfo(Recipient.DD.getValue()).contains(Constants.CHRISTMAS_DELIVERY)) {
			Calendar dateDelivery = paymentAndReviewPage2SC.getDeliveryDate(Recipient.DD.getValue());
			assertTrue(Common.isDateBetweenTwoDates(dateDelivery, Common.getDateBeforeNumberOfBusinessDays(christmasDate, 16), Common.getDateBeforeNumberOfBusinessDays(christmasDate, 9)), "Delivery Date does not arrive in 10 - 15 biz. days before Christmas from the day you place your order");
		} else {
			assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.DD.getValue()).contains(recipientDeliveryDate), "The Delivery Date is not displayed correctly " + recipientDeliveryDate);
		}
		assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.DD.getValue()).contains(recipientShippingCost), "The Shipping Cost is not displayed correctly " + recipientShippingCost);

		Logger.verify("In Payment & Review page: Verify Shipping Cost displays correctly in Order Totals section after updating Shipping Options");
		Double expectedShippingFee = Double.valueOf(myShippingCost.replace("$", "").replace("FREE", "0")) + Double.valueOf(recipientShippingCost.replace("$", "").replace("FREE", "0"));
		assertEquals(Double.valueOf(paymentAndReviewPage2SC.getTotalShippingFee().replace("$", "").replace("FREE", "0")), Common.round(expectedShippingFee, 2), "Shipping Cost does not display correctly in Order Totals section after updating Shipping Options");

		fillBillingAddress();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillRecipientShippingAddressThenClickContinueButton() {
		Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Continue\"");
		shippingAddressPage2SC.fillShippingAddress(recipientAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void fillMyShippingAddressThenClickNextButton() {
		Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Next\"");
		shippingAddressPage2SC.fillShippingAddress(myAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void goToShoppingCartPageThenClickCheckout() {
		Logger.info("Go to Shopping Cart Page then click checkout");
		generalPage.goToMyCart();
		shoppingCartPage.checkOut();
	}

	private void updateRecipientShippingOption() {
		Logger.info("In Payment & Review page: Update Recipient Shipping Option");
		paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.DD.getValue());
		recipientShippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal();
		recipientDeliveryDate = paymentAndReviewPage2SC.getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(recipientShippingMethod);
		recipientShippingCost = paymentAndReviewPage2SC.getShippingMethodCostInShippingDeliveryModal(recipientShippingMethod);
		paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
		if (recipientShippingMethod.equals(Constants.THANKSGIVING_SHIPPING)) {
			recipientDeliveryDate = Messages.THANKSGIVING_ESTIMATED_DELIVERY;
		}
	}

	private void updateMyShippingOption() {
		Logger.info("In Payment & Review page: Update My Shipping Option");
		paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
		myShippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal();
		myDeliveryDate = paymentAndReviewPage2SC.getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(myShippingMethod);
		myShippingCost = paymentAndReviewPage2SC.getShippingMethodCostInShippingDeliveryModal(myShippingMethod);
		paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
		if (myShippingMethod.equals(Constants.THANKSGIVING_SHIPPING)) {
			myDeliveryDate = Messages.THANKSGIVING_ESTIMATED_DELIVERY;
		}
	}

	private void verifyShippingOptionsDoesNotAppear() {
		Logger.verify("'Shipping Options' section does not appear in the Shipping Address page When My Cart contains Food item.");
		assertFalse(shippingAddressPage2SC.isShippingOptionsDisplayed(), "'Shipping Options' section appears in the Shipping Address page When My Cart contains Food item.");
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(myAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void initTestCaseData() {
		Logger.tc("TC_SOP_002 - User Can Add Food To My Cart And Recipient Cart Then Complete Checkout");
		Logger.to("TO_SOP_18 - \"Shipping Options\" section does not appear in the Shipping Address page When My Cart and Recipient Cart contain food item");
		Logger.to("TO_SOP_20 - \"View All Shipping Options\" link appears in Cart 1 of 2 section and Cart 2 of 2 section of the Payment & Review page when My Cart and Recipient Cart contain food");
		Logger.to("TO_SOP_26 - Shipping Method, Cost, and Estimated Delivery date appear in My Cart section and Recipient Cart section after updating Shipping Options When My Cart and Recipient Cart contain Food items");
		Logger.to("TO_SOP_28 - Shipping Cost displays correctly in Order Totals section after updating Shipping Options When My Cart and Recipient Cart contain Foods");
		myAddress = lstAddresses.getDefaultShippingAddress();
		recipientAddress = lstAddresses.getDefaultShippingAddress();
		myItem.initRandom(Recipient.MYSELF);
		recipientItem.initRandom(Recipient.DD);
		Calendar currentDate = Calendar.getInstance();
		int year = currentDate.get(Calendar.YEAR);
		christmasDate = (Calendar) currentDate.clone();
		christmasDate.set(year, 11, 25);
	}

	private void searchAndAddItem(Item item) {
		Logger.info("From Homepage, enter \"" + Common.getNumberFromText(item.getId()) + "\" into Search textbox and click Search button");
		generalPage.search(Common.getNumberFromText(item.getId()));
		Logger.info("\"In Product Page: Add this item to " + item.getRecipient() + " - If Exclusive Offer (Upsell offer) appears, click \"Add To Cart\"");
		generalPage.addFirstSKUToCart(item);
		productPage.selectExclusiveOffer(false);
		productPage.continueShopping();
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
	}

	private void fillBillingAddress() {
		Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
		paymentAndReviewPage2SC.fillBillingAddress(myAddress);

	}
}
