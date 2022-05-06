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

public class TC_SOP_007_UserCanAddFoodAndWineToMyCartThenCompleteCheckout extends TestBase_2SC {
	CustomerAddress myAddress;
	String myShippingMethod, myDeliveryDate, myShippingCost;

	Calendar christmasDate;

	@Inject
	Item myItem, myWine;

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
	public void TC_SOP_007_User_Can_Add_Food_And_Wine_To_My_Cart_Then_Complete_Checkout() {
		initTestCaseData();

		searchAndAddItem(myItem);

		searchAndAddItem(myWine);

		goToShoppingCartPageThenClickCheckout();

		verifyShippingOptionsDoesNotAppear();

		fillMyShippingAddressThenClickContinueButton();

		Logger.verify("In Payment & Review page: Verify \"View All Shipping Options\" link appears in My Cart");
		assertTrue(paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(myItem.getRecipient().getValue()), "\"View All Shipping Options\" link does not appear in My Cart");

		Logger.verify("In Payment & Review page: Message \"This item will ship separately from your food.\" appears next to Wine in My Cart");
		assertEquals(paymentAndReviewPage2SC.getNoteOfSKU(myWine), Messages.getNoteOfSkuMessageByPlatform(), "Message \"" + Messages.getNoteOfSkuMessageByPlatform() + "\" does not appear next to Wine in My Cart");

		Logger.verify("In Payment & Review page: \"Adult signature required (Age 21+) for delivery. We will make 3 delivery attempts on consecutive business days.\" message displays in My Cart");
		assertEquals(paymentAndReviewPage2SC.getWineInCartMesssage(Recipient.MYSELF.getValue()), Messages.WINE_IN_CART_MESSAGE, "\"Adult signature required (Age 21+) for delivery. We will make 3 delivery attempts on consecutive business days.\" message does not display in My Cart");

		updateMyShippingOption();

		Logger.verify("In Payment & Review page: Shipping Method, Cost, and Estimated Delivery date appear in My Cart section");
		assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(myShippingMethod), "The Shipping method is not displayed correctly");
		if (paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(Constants.CHRISTMAS_DELIVERY)) {
			Calendar dateDelivery = paymentAndReviewPage2SC.getDeliveryDate(Recipient.MYSELF.getValue());
			assertTrue(Common.isDateBetweenTwoDates(dateDelivery, Common.getDateBeforeNumberOfBusinessDays(christmasDate, 16), Common.getDateBeforeNumberOfBusinessDays(christmasDate, 9)), "Delivery Date does not arrive in 10 - 15 biz. days before Christmas from the day you place your order");
		} else {
			assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(myDeliveryDate), "The Delivery Date is not displayed correctly");
		}
		assertTrue(paymentAndReviewPage2SC.getShippingInfo(Recipient.MYSELF.getValue()).contains(myShippingCost), "The Shipping Cost is not displayed correctly");

		Logger.verify("In Payment & Review page: Verify Shipping Cost displays correctly in Order Totals section after updating Shipping Options");
		Double expectedShippingFee = Double.valueOf(myShippingCost.replace("$", "").replace("FREE", "0"));
		assertEquals(Double.valueOf(paymentAndReviewPage2SC.getTotalShippingFee().replace("$", "").replace("FREE", "0")), Common.round(expectedShippingFee, 2), "Shipping Cost does not display correctly in Order Totals section after updating Shipping Options");

		fillBillingAddress();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void fillMyShippingAddressThenClickContinueButton() {
		Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Continue\"");
		shippingAddressPage2SC.fillShippingAddress(myAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void goToShoppingCartPageThenClickCheckout() {
		Logger.info("Go to Shopping Cart Page then click checkout");
		generalPage.goToMyCart();
		shoppingCartPage.checkOut();
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
		Logger.verify("'Shipping Options' section does not appear in the Shipping Address page When My Cart contains Wine item.");
		assertFalse(shippingAddressPage2SC.isShippingOptionsDisplayed(), "'Shipping Options' section appears in the Shipping Address page When My Cart contains Wine item.");
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(myAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void initTestCaseData() {
		Logger.tc("TC_SOP_007 - User Can Add Food And Wine To My Cart Then Complete Checkout");
		Logger.to("TO_SOP_29 - \"Shipping Options\" section does not appear in the Shipping Address page When My Cart contains food and wine items");
		Logger.to("TO_SOP_31 - \"View All Shipping Options\" link appears in My Cart section of the Payment & Review page when My Cart contains food and wine items");
		Logger.to("TO_SOP_33 - Message \"This item will ship separately from your food.\" appears next to Wine in My Cart of the Payment & Review page when My Cart contains Food and Wine items");
		Logger.to("TO_SOP_35 - Shipping Method, Cost, and Estimated Delivery date appear in My Cart section after updating Shipping Options When My Cart contains Food and Wine items");
		Logger.to("TO_SOP_37 - Shipping Cost displays correctly in Order Totals section after updating Shipping Options When My Cart contains Food and Wine items");
		Logger.to("TO_SOP_39 - \"Adult signature required (Age 21+) for delivery. We will make 3 delivery attempts on consecutive business days.\" message displays in My Cart of the Payment & Review page");
		myAddress = lstAddresses.getDefaultShippingAddress();
		myItem.initRandom(Recipient.MYSELF);
		myWine.initRandom(Recipient.MYSELF, true);
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
