package com.omahasteaks.tests.RewardCard.PositiveCases;

import static org.testng.Assert.assertEquals; 

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_RCP_008_PaymentMethodSaysGiftCardWhenCompletingCheckoutWithRewardCardOnly extends TestBase_2SC {

	@Inject
	ListAddresses lstAddresses;
	CustomerAddress shippingAddress, billingAddress;

	@Inject
	GeneralPage generalPage;

	@Inject
	Item myItem;

	@Inject
	ProductPage productPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;

	@Inject
	ConfirmationPage2SC confirmationPage2SC;

	@Test
	public void TC_RCP_008_Payment_Method_Says_Gift_Card_When_Completing_Checkout_With_Reward_Card_Only() {

		initTestCaseData();

		searchAndAddItem(myItem);
		
		applyRewardCardInShoppingCart();

		//verifyTheAdditionalSavingsDisplaysInModal();

		closeEnterYourGiftCardOrVoucherNumberModal();

		completeCheckOutProcess();

		verifyPaymentMethodSaysGiftCardInConfirmationPage();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void completeCheckOutProcess() {
		checkOutFromShoppingCart();

		fillShippingAddressAndClickContinue();

		fillBillingAddressAndPlaceOrder();
	}

	public void applyRewardCardInShoppingCart() {
		clickEnterGiftCardOrVoucherLinkInShoppingCartPage();

		enterVoucherAndClickSubmitInVoucherModal();
	}

	private void verifyPaymentMethodSaysGiftCardInConfirmationPage() {
		Logger.verify("In Confirmation Page: " + "Verify \"Payment Method\" says \"Gift Card");
		assertEquals(confirmationPage2SC.getPaymentMethodContent()[0], Constants.PAYMENT_METHOD_CONTENT, "\"Payment Method\" does not say \"Gift Card");
	}

	private void fillBillingAddressAndPlaceOrder() {
		Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void fillShippingAddressAndClickContinue() {
		Logger.info("In Shipping Address page:" + "- Fill all valid information " + "- Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void checkOutFromShoppingCart() {
		Logger.info("In Shopping Cart Page:" + "- Click \"Checkout\" button");
		shoppingCartPage.checkOut();
	}

	private void closeEnterYourGiftCardOrVoucherNumberModal() {
		Logger.info("In Enter Your Gift Card or Voucher Number modal:" + "- Click \"Close\" button");
		shoppingCartPage.closeModal();
		Common.waitForDOMChange();
		Common.waitForPageLoad();
	}

	private void enterVoucherAndClickSubmitInVoucherModal() {
		Logger.info("In \"Enter a Gift Card or Voucher\" popup : " + "    - Enter  valid Reward Card into \"Number\" textbox " + "    - Click on \"Submit\" button");
		shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_500);
	}

	private void clickEnterGiftCardOrVoucherLinkInShoppingCartPage() {
		Logger.info("In Shopping Cart Page:" + "- Click \"Enter a Gift Card or Voucher\" link");
		shoppingCartPage.clickEnterGiftCardOrVoucherLink();
	}

	private void searchAndAddItem(SKU sku) {
		Logger.info("From Homepage, search a SKU with id: " + sku.getId() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_RCP_008 - Payment Method says \"Gift Card\" when completing checkout with Reward Card only");
		Logger.to("TO_RCP_07 - In Shopping Cart Page - \"The Additional Savings\" displays correctly when adding Reward Card which has value larger than \"Item Subtotal\"");
		Logger.to("TO_RCP_15 - Confirmation Page - \"Payment Method\" says \"Gift Card\" when completing checkout with Reward Card only");
		myItem.init(SkuType.OVER100, Recipient.MYSELF); 
		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getShippingAddressByState("Nebraska"); 
	}

}
