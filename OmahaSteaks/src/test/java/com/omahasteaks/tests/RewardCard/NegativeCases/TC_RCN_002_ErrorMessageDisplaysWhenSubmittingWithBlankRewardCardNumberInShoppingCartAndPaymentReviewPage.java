package com.omahasteaks.tests.RewardCard.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
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

public class TC_RCN_002_ErrorMessageDisplaysWhenSubmittingWithBlankRewardCardNumberInShoppingCartAndPaymentReviewPage extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress shippingAddress, billingAddress;

    @Inject
    Item myItem;

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
    public void TC_RCN_002_Error_Message_Displays_When_Submitting_With_Blank_Reward_Card_Number_In_Shopping_Cart_And_Payment_Review_Page() {

	initTestCaseData();

	searchAndAddItem(myItem);

	applyRewardCardInShoppingCartPage();

	verifyErrorMessageDisplaysInShoppingCartPage();

	closeModalAndCheckOutFromShoppingCartPage();

	fillShippingAddressAndClickContinue();

	applyRewardCardInPaymentReviewPage();

	verifyErrorMessageDisplaysInPaymentReviewPage();
    }
    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyErrorMessageDisplaysInPaymentReviewPage() {
	Logger.verify("In \"Enter a Gift Card or Voucher\" popup : " + "Verify that \"Please Enter A Valid Number\" displays");
	assertEquals(paymentAndReviewPage2SC.getErrorMessageInVoucherModal(), Messages.VOUCHER_NUMBER_EMPTY_MESSAGE);
    }

    private void applyRewardCardInPaymentReviewPage() {
	Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();

	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Leave with \"Number\" textbox empty" + "- Click \"Submit\" button");
	paymentAndReviewPage2SC.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.EMPTY_STRING);
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address page:" + "- Fill all valid information " + "- Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void closeModalAndCheckOutFromShoppingCartPage() {
	Logger.info("Close \"Enter a Gift Card or Voucher\" popup" + "In Shopping Cart Page:" + "- Click \"Checkout\" button");
	shoppingCartPage.closeModal();
	shoppingCartPage.checkOut();
    }

    private void verifyErrorMessageDisplaysInShoppingCartPage() {
	Logger.verify("In \"Enter a Gift Card or Voucher\" popup : " + "Verify that \"Please Enter A Valid Number\" displays");
	assertEquals(shoppingCartPage.getErrorMessageInVoucherModal(), Messages.VOUCHER_NUMBER_EMPTY_MESSAGE);
    }

    private void applyRewardCardInShoppingCartPage() {
	Logger.info("In Shopping Cart Page:" + "   - Click on \"Enter a Gift Card or Voucher\" link");
	shoppingCartPage.clickEnterGiftCardOrVoucherLink();

	Logger.info("In \"Enter a Gift Card or Voucher\" popup : " + "    - Leave with \"Number\" textbox empty" + "    - Click on \"Submit\" button");
	shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.EMPTY_STRING);
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id (randomly)" + sku.getName() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCN_002 - Error message displays when submitting with blank Reward Card number in Shopping Cart and Payment Review page");
	Logger.to("TO_RCN_02 - In Shopping Cart Page - \"Please Enter A Valid Number\" displays when submitting with blank Reward Card Number");
	Logger.to("TO_RCN_04 - In Payment & Review Page - \"Please Enter A Valid Number\" displays when submitting with blank Reward Card Number");
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF);
    }

}
