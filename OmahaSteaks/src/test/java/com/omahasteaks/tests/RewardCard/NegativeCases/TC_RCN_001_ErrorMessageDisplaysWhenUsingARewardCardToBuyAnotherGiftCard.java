package com.omahasteaks.tests.RewardCard.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
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

public class TC_RCN_001_ErrorMessageDisplaysWhenUsingARewardCardToBuyAnotherGiftCard extends TestBase_2SC {

	@Inject
	GeneralPage generalPage;

	@Inject
	ListAddresses lstAddresses;
	CustomerAddress shippingAddress, billingAddress;

	@Inject
	Item myItem;

	@Inject
	Item myGiftCard;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;

	@Inject
	CategoryPage categoryPage;

	@Inject
	ProductPage productPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_RCN_001_Error_Message_Displays_When_Using_A_Reward_Card_To_Buy_Another_Gift_Card() {

		initTestCaseData();

		searchAndAddGiftCard(myGiftCard);

		verifyGiftCardIsAddedWithCorrectInformation();

		checkOutFromShoppingCart();

		fillShippingAddressAndClickContinue();

		applyRewardCardInPaymentReviewPage();

		verifyRewardCardIsAddedWithCorrectInformation();

		fillBillingAddress();

		fillCreditCardNumberAndPlaceOrder();

		verifyErrorMessageDisplaysInPaymentReviewPage();

		goToHomePage();

		searchAndAddItem(myItem);

		verifyTheSKUsWhichAreAddedToCartDisplayInShoppingCartPage();

		checkOutFromShoppingCart();

		clickPlaceOrderInPaymentReviewPage();

		verifyErrorMessageDisplaysInPaymentReviewPage();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void clickPlaceOrderInPaymentReviewPage() {
		Logger.info("In Payment and Review page:" + "- Click \"Place Order\" button");
		paymentAndReviewPage2SC.placeOrder();
	}

	private void verifyTheSKUsWhichAreAddedToCartDisplayInShoppingCartPage() {
		Logger.verify("In Shopping Cart Page: " + "Verify  the SKUs which are added to cart display in Shopping Cart page");
		assertTrue(shoppingCartPage.isGiftCardByNameAdded(Recipient.MYSELF.getValue(), myGiftCard.getName()), "The Gift card does not display in Shopping Cart page");
		assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, myItem.getId()), "The SKU does not display in Shopping Cart page");
	}

	private void searchAndAddItem(SKU sku) {
		Logger.info("From Homepage, search a SKU with id (randomly)" + sku.getName() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
	}

	private void goToHomePage() {
		Logger.info("In Payment and Review page:" + "- Go to homepage");
		paymentAndReviewPage2SC.goToHomePage();
	}

	private void verifyErrorMessageDisplaysInPaymentReviewPage() {
		Logger.verify("In Payment & Review Page: " + "Verify Error \"Gift/Reward card not allowed - We're sorry, you may not use gift cards, e-gift cards, or rewards cards toward the purchase of gift cards or e-gift cards.\" displays ");
		assertEquals(paymentAndReviewPage2SC.getErrorMessage(), Messages.GIFT_OR_REWARD_CARD_ERROR_MESSAGE, "Error \"Gift/Reward card not allowed - We're sorry, you may not use gift cards, e-gift cards, or rewards cards toward the purchase of gift cards or e-gift cards.\" displays");
	}

	private void fillCreditCardNumberAndPlaceOrder() {
		Logger.info("From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		clickPlaceOrderInPaymentReviewPage();
	}

	private void fillBillingAddress() {
		Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address");
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	}

	private void verifyRewardCardIsAddedWithCorrectInformation() {
		Common.waitForPageLoad();
		Logger.verify("In Payment & Review Page: " + "Verify  the reward card is added with correct information");
		assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_5), "Reward Card does not display in Payment And Review Page");
	}

	private void applyRewardCardInPaymentReviewPage() {
		Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
		paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();

		Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
		paymentAndReviewPage2SC.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_5);
		paymentAndReviewPage2SC.closeModal();
	}

	private void fillShippingAddressAndClickContinue() {
		Logger.info("In Shipping Address page:" + "- Fill all valid information " + "- Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void checkOutFromShoppingCart() {
		Logger.info("In Shopping Cart page : " + "- Click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void verifyGiftCardIsAddedWithCorrectInformation() {
		Logger.verify("In Shopping Cart Page: " + "Verify  the Gift card is added with correct information");
		assertTrue(shoppingCartPage.isGiftCardByNameAdded(Recipient.MYSELF.getValue(), myGiftCard.getName()), "The Gift card id not added with the correct information");
	}

	private void searchAndAddGiftCard(SKU sku) {
		String url="";
		Logger.info("In Homepage: \n" + "- Search a SKU (Gift cards)\n");
		if (Constants.OMAHA_URL.contains("?")) {
			String str = Constants.OMAHA_URL.trim();
			String[] arrOfStr = str.split("\\?",2);
			url = arrOfStr[0].trim() + "buy/Gifts/Gift-Cards" + "?" + arrOfStr[1]; 
		}
		else
		if (Constants.OMAHA_URL.contains("/legacy")) {
			String str = Constants.OMAHA_URL.trim();
			String[] arrOfStr = str.split("legacy",2);
			url = arrOfStr[0].trim() + "buy/Gifts/Gift-Cards"; 
		}
		else url = Constants.OMAHA_URL + "/buy/Gifts/Gift-Cards";
		DriverUtils.getWebDriver().get(url);
		Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
		categoryPage.addEGiftCard(sku);
		Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_RCN_001 - Error message displays when using a Reward Card to buy another Gift Card");
		Logger.to("TO_RCN_01 - Error \"Gift/Reward card not allowed - We're sorry, you may not use gift cards, e-gift cards, or rewards cards toward the purchase of gift cards or e-gift cards.\" displays when using a Reward Card to buy another gift card");
		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		shippingAddress.confirmEmail = "slrgold01@omahasteaks.com";
		myItem.initRandom(Recipient.MYSELF);
		myGiftCard.setName("Omaha Steaks E-Gift Card");
		myGiftCard.setRecipient(Recipient.MYSELF);
		myGiftCard.setPrice(150);
		myGiftCard.setQuantity(1);
	}

}
