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

public class TC_RCP_002_UserCanAdd2RewardCardsInShoppingCartPageThenCompleteCheckout extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
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
    @Inject
    Item mySku;

    @Test
    public void TC_RCP_002_User_Can_Add_Two_Reward_Cards_In_Shopping_Cart_Page_Then_Complete_Checkout() {

	initTestCaseData();

	searchAndAddItem(mySku);

	applyTwoRewardCardsInShoppingCart();

	verifyTwoRewardCardsAreAddedInShoppingCart();

	checkOutFromShoppingCartPage();

	fillShippingAddressAndContinueCheckout();

	verifyTwoRewardCardsAreDisplayedCorrectly();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void applyTwoRewardCardsInShoppingCart() {
	clickAndEnterGiftCardOrVoucherInVoucherModal(Constants.REWARD_CARD_PRICE_5);

	clickAndEnterGiftCardOrVoucherInVoucherModal(Constants.REWARD_CARD_PRICE_15);
    }

    private void verifyTwoRewardCardsAreAddedInShoppingCart() {
	Logger.verify("In Shopping Cart Page:" + "Verify Two Reward Cards  are added ");
	assertTrue(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_5), "Reward Card does not display in Shopping Cart Page");
	assertTrue(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_15), "Reward Card does not display in Shopping Cart Page");
    }

    private void clickAndEnterGiftCardOrVoucherInVoucherModal(String rewardCard) {
	clickEnterGiftCardOrVoucherLink();
	enterVoucherAndClickSubmitButtonInVoucherModal(rewardCard);
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: " + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void verifyTwoRewardCardsAreDisplayedCorrectly() {
	Logger.verify("Payment & Review Page - Two Reward Cards are displayed correctly");
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_5), "Reward Card does not display");
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_15), "Reward Card does not display");
    }

    private void fillShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address page:" + " In Shipping Address section:" + " - Fill all valid information\r\n" + "  In Optional Delivery Notification section:" + "  - Fill valid email address and shipping phone values" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In Shopping Cart page :" + "  -Click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void clickEnterGiftCardOrVoucherLink() {
	Logger.info("In Shopping Cart Page:" + "- Click \"Enter a Gift Card or Voucher\" link");
	shoppingCartPage.clickEnterGiftCardOrVoucherLink();
    }

    private void enterVoucherAndClickSubmitButtonInVoucherModal(String rewardCard) {
	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
	shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(rewardCard);
	shoppingCartPage.closeModal();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id (randomly)" + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCP_002 - User can add two Reward Cards in Shopping cart page then complete checkout");
	Logger.to("TO_RCP_02 - User can add two Reward Cards in Shopping Cart Page then complete checkout");
	Logger.to("TO_RCP_14 - Payment & Review Page - User can checkout with two Reward Cards and one Credit Card");
	mySku.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    }

}
