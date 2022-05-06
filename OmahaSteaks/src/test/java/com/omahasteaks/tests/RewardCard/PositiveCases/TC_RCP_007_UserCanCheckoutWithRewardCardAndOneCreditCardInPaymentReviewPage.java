package com.omahasteaks.tests.RewardCard.PositiveCases;

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

public class TC_RCP_007_UserCanCheckoutWithRewardCardAndOneCreditCardInPaymentReviewPage extends TestBase_2SC {
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
    public void TC_RCP_007_User_Can_Checkout_With_Reward_Card_And_One_Credit_Card_In_Payment_Review_Page() {

	initTestCaseData();

	searchAndAddItem(item);

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	applyRewardCardInPaymentReviewPage();

	verifyRemainingCardBalanceDoesNotDisplay();

	closeEnterYourGiftCardOrVoucherNumberModal();

	verifyRewardCardsIsAddedWithCorrectInformation();

	completeCheckOutProcess();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void completeCheckOutProcess() {
	fillBillingAddress();

	fillCreditCardNumberAndPlaceOrder();
    }

    private void closeEnterYourGiftCardOrVoucherNumberModal() {
	Logger.info("In Enter Your Gift Card or Voucher Number modal:" + "- Click \"Close\" button");
	paymentAndReviewPage2SC.closeModal();
    }

    private void verifyRemainingCardBalanceDoesNotDisplay() {
	Logger.verify("In Payment & Review Page:" + "Verify  \"The Remaining Card Balance\" does not display");
	Common.waitForDOMChange();
	assertFalse(paymentAndReviewPage2SC.isLabelRemainingCardBalanceDisplayed(), "\"The Remaining Card Balance\" displays");
    }

    private void applyRewardCardInPaymentReviewPage() {
	Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();

	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
	paymentAndReviewPage2SC.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_5);
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("In Confirmation page:" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	Common.waitForDOMChange();
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void fillCreditCardNumberAndPlaceOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillBillingAddress() {
	Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address");
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
    }

    private void verifyRewardCardsIsAddedWithCorrectInformation() {
	Logger.verify("In Payment & Review Page: " + "Verify the two reward cards are added with correct information");
	Common.waitForDOMChange();
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_5), "Reward Card does not display in Payment And Review Page");
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
	Logger.info("From Homepage, search a SKU with id (randomly)" + sku.getName() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCP_007 - User can checkout with Reward Card and one Credit Card inPayment and Review page");
	Logger.to("TO_RCP_10 - Payment & Review Page - \"The Remaining Card Balance\" does not display when adding Reward Card which has value smaller than \"Total\"");
	Logger.to("TO_RCP_13 - Payment & Review Page - User can checkout with Reward Card and one Credit Card");
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	item.initRandom(Recipient.MYSELF);
    }

}
