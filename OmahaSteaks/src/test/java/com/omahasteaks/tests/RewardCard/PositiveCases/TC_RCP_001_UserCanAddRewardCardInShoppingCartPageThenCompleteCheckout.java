package com.omahasteaks.tests.RewardCard.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
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

public class TC_RCP_001_UserCanAddRewardCardInShoppingCartPageThenCompleteCheckout extends TestBase_2SC {

	@Inject
	GeneralPage generalPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	Item mySku;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	CustomerAddress billingAddress, shippingAddress;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	ConfirmationPage2SC confirmationPage2SC;

	@Test
	public void TC_RCP_001_User_Can_Add_Reward_Card_In_Shopping_Cart_Page_Then_Complete_Checkout() {

		initTestCaseData();

		searchAndAddItem(mySku);

		applyRewardCardInShoppingCart();

		verifyRewardCardIsAddedInShoppingCart();

		completeCheckOutProcess();

		verifyThankYouMessageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void completeCheckOutProcess() {
		checkOutFromShoppingCartPage();

		fillShippingAddressAndContinueCheckout();

		fillBillingAddressAndPlaceMyOrder();
	}

	public void applyRewardCardInShoppingCart() {
		clickEnterGiftCardOrVoucherLink();

		enterRewardCardAndClickSubmitButtonInVoucherModal();

		closeYourGiftCardHasBeenAppliedModal();
	}

	private void clickEnterGiftCardOrVoucherLink() {
		Logger.info("In Shopping Cart Page:" + "- Click \"Enter a Gift Card or Voucher\" link");
		shoppingCartPage.clickEnterGiftCardOrVoucherLink();
	}

	private void searchAndAddItem(SKU sku) {
		Logger.info("From Homepage, search a SKU with id (randomly)" + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button\" " + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
	}

	private void enterRewardCardAndClickSubmitButtonInVoucherModal() {
		Logger.info("In \"Enter a Gift Card or Voucher\" popup :" + "- Enter  valid Reward Card into \"Number\" textbox" + "- Click on \"Submit\" button");
		shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_30);
		Logger.info("In \"Your Gift Card Has Been Applied\" popup : " + "- Click on \"Close\" button ");
		shoppingCartPage.closeModal();
	}

	private void closeYourGiftCardHasBeenAppliedModal() {
		Logger.info("In \"Your Gift Card Has Been Applied\" popup : " + "- Click on \"Close\" button ");
		shoppingCartPage.closeModal();
	}

	private void verifyRewardCardIsAddedInShoppingCart() {
		Logger.verify("In Shopping Cart Page:" + "Verify Reward Card is added");
		assertTrue(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_30), "Reward Card does not display in Shopping Cart Page");
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page : " + "  -Click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void fillShippingAddressAndContinueCheckout() {
		Logger.info("In Shipping Address page:" + " In Shipping Address section: " + " - Fill all valid information" + "  In Optional Delivery Notification section:" + "  - Fill valid email address and shipping phone values" + " - Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void fillBillingAddressAndPlaceMyOrder() {
		Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
	}

	private void initTestCaseData() {
		Logger.tc("TC_RCP_001 - UserCanAddRewardCardInShoppingCartPageThenCompleteCheckout");
		Logger.to("TO_RCP_01 - User can add Reward Card in Shopping Cart Page then complete checkout");

		mySku.initRandom(Recipient.MYSELF);
		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
	}

}
