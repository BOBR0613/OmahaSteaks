package com.omahasteaks.tests.RewardCard.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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

public class TC_RCP_003_UserCanRemoveRewardCardInShoppingCartPageThenCompleteCheckout extends TestBase_2SC {

    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress shippingAddress, billingAddress;
    @Inject
    Item item1, item2, item3;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_RCP_003_User_Can_Remove_Reward_Card_In_Shopping_Cart_Page_Then_Complete_Checkout() {

	initTestCaseData();

	searchAndAddItemFromHomePage(item1);

	applyRewardCardInShoppingCart();

	verifyRewardCardIsAddedInShoppingCart();

	removeItemFromCart(item1);

	verifyYourCartIsCurrentlyEmptyIsDisplayed();

	searchAndAddItemFromShoppingCartPage(item2);

	verifyTheRewardCardIsRetainedAfterRemovingAllSKUsToCart();

	searchAndAddItemFromShoppingCartPage(item3);

	verifythatTheRewardCardIsRetainedAfterAddingAdditionalSKuToCart();

	removeRewardCardFromShoppingCart();

	verifyRewardCardIsRemovedInShoppingCart();

	completeCheckOutProcess();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void completeCheckOutProcess() {
	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	fillCreditCardNumberAndBillingAddressAndPlaceMyOrder();
    }

    public void applyRewardCardInShoppingCart() {
	clickEnterGiftCardOrVoucherLink();

	enterVoucherAndClickSubmitButtonInVoucherModal();
    }

    private void checkOutFromShoppingCart() {
	Logger.info("In Shopping Cart Page:" + "- Click \"Checkout\" button");
	shoppingCartPage.checkOut();
    }

    private void verifyRewardCardIsRemovedInShoppingCart() {
	Logger.verify("In Shopping Cart page:" + " - Verify Reward Card is removed");
	assertFalse(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_30), "Reward Card does not remove in Shopping Cart page");
    }

    private void removeRewardCardFromShoppingCart() {
	Logger.info("In Shopping Cart page:" + "In Gift Cards & Vouchers section:" + "- Click \"Remove\" link");
	shoppingCartPage.removeRewardCard(Constants.REWARD_CARD_PRICE_30);
    }

    private void verifyRewardCardIsAddedInShoppingCart() {
	Logger.verify("In Shopping Cart Page:" + "Verify Reward Card is added");
	assertTrue(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_30), "Reward Card does not display in Shopping Cart Page");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("In Confirmation page:" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void fillCreditCardNumberAndBillingAddressAndPlaceMyOrder() {
	Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address page:" + "- Fill all valid information " + "- Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void verifythatTheRewardCardIsRetainedAfterAddingAdditionalSKuToCart() {
	Logger.verify("In Shopping Cart page:" + "Verify The Reward Card is retained after removing all SKUs to Cart");
	assertTrue(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_30), "The Reward Card is not retained after adding additional SKU to Cart");
    }

    private void verifyTheRewardCardIsRetainedAfterRemovingAllSKUsToCart() {
	Logger.verify("In Shopping Cart page:" + "Verify The Reward Card is retained after removing all SKUs to Cart");
	assertTrue(shoppingCartPage.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_30), "The Reward Card is not retained after removing all SKUs to Cart");
    }

    private void searchAndAddItemFromShoppingCartPage(SKU sku) {
	Logger.info("From Shopping Cart Page, search a SKU with id (randomly)" + sku.getName() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void verifyYourCartIsCurrentlyEmptyIsDisplayed() {
	Logger.verify("In Shopping Cart page:" + "Verify that 'Your Cart Is Currently Empty' appears when removed all items");
	assertEquals(shoppingCartPage.getEmptyMessageText().trim(), Constants.EMPTY_MESSAGE);
    }

    private void removeItemFromCart(Item item) {
	Logger.info("In Shopping Cart Page:" + "- Remove all SKUs from Cart");
	shoppingCartPage.removeItem(item);
    }

    private void enterVoucherAndClickSubmitButtonInVoucherModal() {
	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
	shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_30);
	shoppingCartPage.closeModal();
    }

    private void clickEnterGiftCardOrVoucherLink() {
	Logger.info("In Shopping Cart Page:" + "- Click \"Enter a Gift Card or Voucher\" link");
	shoppingCartPage.clickEnterGiftCardOrVoucherLink();
    }

    private void searchAndAddItemFromHomePage(SKU sku) {
	Logger.info("From Homepage, search a SKU with id (randomly)" + sku.getName() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCP_03 - User can remove Reward Card in Shopping Cart page then complete checkout");
	Logger.to("TO_RCP_03 - User can remove Reward Card in Shopping Cart Page then complete checkout");
	Logger.to("TO_RCP_16 - The Reward Card is retained although user adds additional SKUs to Cart");
	Logger.to("TO_RCP_17 - The Reward Card is retained although user removes all SKUs from Cart");
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	item1.initRandom(Recipient.MYSELF);
	item2.initRandom(Recipient.MYSELF);
	item3.initRandom(Recipient.MYSELF);
    }

}
