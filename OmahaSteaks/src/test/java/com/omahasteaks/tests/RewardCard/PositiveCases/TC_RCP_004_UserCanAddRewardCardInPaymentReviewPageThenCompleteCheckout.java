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

public class TC_RCP_004_UserCanAddRewardCardInPaymentReviewPageThenCompleteCheckout extends TestBase_2SC {

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
    public void TC_RCP_004_User_Can_Add_Reward_Card_In_Payment_Review_Page_Then_Complete_Checkout() {

	initTestCaseData();

	searchAndAddItem(item);

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	applyRewardCardInPaymentReviewPage();

	verifyRewardCardIsDisplayedCorrectlyInPaymentReviewPage();

	fillBillingAddressAndPlaceOrder();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void applyRewardCardInPaymentReviewPage() {
	clickApplyARewardCardGiftCardOrVoucherlink();

	enterVoucherAndClickSubmitButtonInVoucherModal();

	closeEnterYourGiftCardOrVoucherNumberPopup();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("In Confirmation page:" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void fillBillingAddressAndPlaceOrder() {
	Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifyRewardCardIsDisplayedCorrectlyInPaymentReviewPage() {
	Logger.verify("In Payment And Review Page:" + "Verify Reward Card is displayed");
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_30), "Reward Card does not display in Payment & Review Page");
    }

    private void closeEnterYourGiftCardOrVoucherNumberPopup() {
	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Click \"Close\" button");
	paymentAndReviewPage2SC.closeModal();
    }

    private void enterVoucherAndClickSubmitButtonInVoucherModal() {
	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Enter valid reward card" + "- Click \"Submit\" button");
	paymentAndReviewPage2SC.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_30);
    }

    private void clickApplyARewardCardGiftCardOrVoucherlink() {
	Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();
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
	Logger.tc("TC_RCP_04 - User can add Reward Card in Shopping Cart page then complete checkout");
	Logger.to("TO_RCP_04 - User can add Reward Card in Payment & Review Page then complete checkout");
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	item.initRandom(Recipient.MYSELF);
    }

}
