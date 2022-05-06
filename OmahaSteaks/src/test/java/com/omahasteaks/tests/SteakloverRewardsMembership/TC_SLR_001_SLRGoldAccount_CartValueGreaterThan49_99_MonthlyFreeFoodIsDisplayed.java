package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject; 
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType; 
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_001_SLRGoldAccount_CartValueGreaterThan49_99_MonthlyFreeFoodIsDisplayed extends TestBase_SLR {

	String monthlyFreeFoodName;

	@Inject
	SKU myMonthlyFreeFood, recipientMonthlyFreeFood, mySKU, recipientSKU;;	

	@Test
	public void TC_SLR_001_SLR_Gold_Account_Cart_Value_Greater_Than_49_99_Monthly_Free_Food_Is_Displayed() {
		initTestCaseData();

		signInWithSteakloverRewardsGoldAccount();

		searchAndAddSkuToMyselfAndRecipient();

		verifyTheFreeShippingMessageIsDisplayed();

		verifyTheMonthlyFreeFoodIsDisplayed();

		checkOutFromShoppingCartPage();

		//verifyTheMonthlyFreeFoodDisplaysInShippingAddressPage();

		goToPaymentAndReviewPage();

		verifyTheShippingFeeIsFREEInThePaymentAndReiewPage();

		completedCheckout();

		verifyTheShippingFeeIsFREEInTheConfirmationPage();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void signInWithSteakloverRewardsGoldAccount() {
		Common.modalDialog.closeSavorDialog();
		signIn(account);
		monthlyFreeFoodName = Constants.SLR_MONTHLY_FREE_FOOD;
		myMonthlyFreeFood.setName(monthlyFreeFoodName);
		recipientMonthlyFreeFood.setName(monthlyFreeFoodName);
		Common.modalDialog.closeSavorDialog();
	}

	private void searchAndAddSkuToMyselfAndRecipient() {
		searchAndAddSKUToCart(mySKU);
		searchAndAddSKUToCart(recipientSKU); 
	}

	private void verifyTheFreeShippingMessageIsDisplayed() {
		Logger.verify("In the 'Shopping Cart' page, verify the \"This cart qualifies for FREE SHIPPING\" text is displayed in the top of MYSELF and RECIPIENT's Summary cart section");
		assertTrue(shoppingCartPage.isTheFreeShippingMessageDisplayed(Recipient.MYSELF), "The \"" + Messages.SLR_GOLD_FREE_SHIP_MSG + "\" is not displayed in the Mycart section");
		assertTrue(shoppingCartPage.isTheFreeShippingMessageDisplayed(Recipient.THONG_NGUYEN), "The \"" + Messages.SLR_GOLD_FREE_SHIP_MSG + "\" is not displayed in the Thong Nguyen's section");
	}

	private void verifyTheMonthlyFreeFoodIsDisplayed() {
		Logger.verify("In the 'Shopping Cart' page, verify that the free food( " + monthlyFreeFoodName + ") whose point is FREE, is displayed correctly on the bottom of each cart");
 		assertTrue(shoppingCartPage.isFreeFoodAddedByMembership(Recipient.MYSELF), "The monthly free food( " + monthlyFreeFoodName + " ) does not display in My Cart");
		assertTrue(shoppingCartPage.isFreeFoodAddedByMembership(Recipient.THONG_NGUYEN), "The monthly free food( " + monthlyFreeFoodName + " ) does not display in Thong Nguyen's Cart");
//		assertEquals(shoppingCartPage.getTheQuantityOfFreeFood(myMonthlyFreeFood), Integer.toString(myMonthlyFreeFood.getQuantity()), "The price of monthly free food is displayed correctly in the Mycart section");
//		assertEquals(shoppingCartPage.getPriceOfFreeFood(myMonthlyFreeFood), myMonthlyFreeFood.getPrice(), "The quantity of monthly free food is displayed correctly in the Mycart section");
//		assertEquals(shoppingCartPage.getTheQuantityOfFreeFood(recipientMonthlyFreeFood), Integer.toString(recipientMonthlyFreeFood.getQuantity()), "The quantity of monthly free food is displayed correctly in the Recipient Cart section");
//		assertEquals(shoppingCartPage.getPriceOfFreeFood(recipientMonthlyFreeFood), recipientMonthlyFreeFood.getPrice(), "The price of monthly free food is displayed correctly in the Recipient section");
	}

	private void verifyTheShippingFeeIsFREEInTheConfirmationPage() {
		Logger.verify("In the Order Review page, verify that the shipping fee is FREE in the 'Order Totals' section when logging in by the Steaklover Rewards Gold account and 'item subtotal' value in the 'Cart Summary' section is greater than 49.99$");
		assertEquals(confirmationPage2SC.getShippingFeeInOrderTotalSection(), Constants.SHIPPING_COST_FREE);
	}

	private void goToPaymentAndReviewPage() {
		Logger.info("In the Shipping Information page - Click \"Next Address button\"" + " - Click \"Continue Button\"");
		shippingAddressPage2SC.clickContinueButton(); 
 		shippingAddressPage2SC.clickContinueButton();
	}

	private void completedCheckout() {
		Logger.info("In Payment & Review page\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.placeOrder();
	}

	private void verifyTheShippingFeeIsFREEInThePaymentAndReiewPage() {
		Logger.verify("In the Payment and Review page, verify that the shipping fee is FREE when logging in by the Steaklover Rewards Gold account and 'item subtotal' value in the 'Cart Summary' section is greater than 49.99$");
		assertEquals(paymentAndReviewPage2SC.getTotalShippingFee(), Constants.SHIPPING_COST_FREE);
	}
 

	private void initTestCaseData() {
		Logger.tc("TC_SLR_001 - Steaklover Reward GOLD - Monthly Free Food is displayed and shipping fee is FREE when the cart greater than 49.99 $");
		Logger.to("TO_SLR_002 - In the 'Shopping Cart' page, the \"This cart qualifies for FREE SHIPPING\" text is displayed in the top of Myself and Recipient 'Summary cart' section when logging in by the SLR Gold account and value of MyCart and Recipient Cart are greater than 49.99$");
		Logger.to("TO_SLR_004 - In the 'Shopping Cart' page, verify that the free food whose point is FREE, is displayed in each cart when logging in by the SLR Gold account and value of MyCart and Recipient Cart are greater than 49.99$:" + "   - Qty: 1" + "   - Price: $0.00" + "   - Text: FREE with your Steaklover Rewards GOLD membership");
		Logger.to("TO_SLR_019 - In the 'Shipping Address' page, verify that the monthly free food is displayed correctly in the 'My Cart' section");
		Logger.to("TO_SLR_021 - In the Payment and Review page, verify that the shipping fee is FREE when logging in by the Steaklover Rewards Gold account and value of MyCart and Recipient Cart are greater than 49.99$");
		Logger.to("TO_SLR_023 - In the Order Review page, verify that the shipping fee is FREE in the 'Order Totals' section when logging in by the SLR Gold account and value of MyCart and Recipient Cart are greater than 49.99$");

		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		mySKU.init(SkuType.PACKAGES, Recipient.MYSELF);
		recipientSKU.init(SkuType.PACKAGES, Recipient.THONG_NGUYEN);
		myMonthlyFreeFood.setRecipient(Recipient.MYSELF);
		recipientMonthlyFreeFood.setRecipient(Recipient.THONG_NGUYEN);
		
		
		// Init data for free foods
		recipientMonthlyFreeFood.setPrice(0.00);
		recipientMonthlyFreeFood.setQuantity(1);
		myMonthlyFreeFood.setPrice(0.00);
		myMonthlyFreeFood.setQuantity(1);
	}
}
