package com.omahasteaks.tests.RewardCard.PositiveCases;

import static org.testng.Assert.assertTrue;

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

public class TC_RCP_005_UserCanAddTwoRewardCardsInPaymentReviewPageThenCompleteCheckout extends TestBase_2SC {

    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress shippingAddress, billingAddress;

    @Inject
    Item item;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_RCP_005_User_Can_Add_Two_Reward_Cards_In_Payment_Review_Page_Then_Complete_Checkout() {

	initTestCaseData();

	searchAndAddItem(item);

	applyRewardCardInShoppingCart();

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	applyRewardCardInPaymentReviewPage();
	
	verifyTwoRewardCardsAreAddedWithCorrectInformation();

	fillBillingAddressAndPlaceOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void applyRewardCardInShoppingCart() {
	clickAndEnterGiftCardOrVoucherInVoucherModalFromShoppingCartPage();

	closeEnterYourGiftCardOrVoucherNumberModal();
    }

    private void closeEnterYourGiftCardOrVoucherNumberModal() {
	Logger.info("In Enter Your Gift Card or Voucher Number modal:" + "- Click \"Close\" button");
	shoppingCartPage.closeModal();
    }

    private void applyRewardCardInPaymentReviewPage() {
	Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();

	Logger.info("In Enter Your Gift Card or Voucher Number modal:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
	paymentAndReviewPage2SC.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_75);
	paymentAndReviewPage2SC.closeModal();
    }

    private void clickAndEnterGiftCardOrVoucherInVoucherModalFromShoppingCartPage() {
	Logger.info("In Shopping Cart Page:" + "   - Click on \"Enter a Gift Card or Voucher\" link");
	shoppingCartPage.clickEnterGiftCardOrVoucherLink();

	Logger.info("In Enter Your Gift Card or Voucher Number modal:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
	shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_50);
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("In Confirmation page:" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void fillBillingAddressAndPlaceOrder() {
	Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "Click \"Place Order\" button");
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifyTwoRewardCardsAreAddedWithCorrectInformation() {
	Logger.verify("In Payment & Review Page: " + "Verify the two reward cards are added with correct information");
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_50), "Reward Card does not display in Payment And Review Page");
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_75), "Reward Card does not display in Payment And Review Page");
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

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id" + sku.getId() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCP_005 - User can add two Reward Cards to Payment & Review Page then complete checkout");
	Logger.to("TO_RCP_05 - User can add 2 Reward Cards in Payment & Review Page then complete checkout");
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getShippingAddressByState("Nebraska");
	item.init(SkuType.OVER100,Recipient.MYSELF);
    }

}
