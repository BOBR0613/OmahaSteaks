package com.omahasteaks.tests.RewardCard.NegativeCases;

import static org.testng.Assert.assertTrue;

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
import com.omahasteaks.utils.helper.Logger;

public class TC_RCN_003_WeAreSorryModalDisplaysWithInvalidRewardCardInShoppingCartAndPaymentReviewPage extends TestBase_2SC {

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
    public void TC_RCN_003_We_Are_Sorry_Modal_Displays_When_Submitting_With_Invalid_Reward_Card_Number_In_Shopping_Cart_And_Payment_Review_Page() {

	initTestCaseData();

	searchAndAddItem(myItem);

	applyRewardCardInShoppingCartPage();

	verifyWeAreSorryModalDisplaysInShoppingCartPage();

	closeModalAndCheckOutFromShoppingCartPage();

	fillShippingAddressAndClickContinue();

	applyRewardCardInPaymentReviewPage();

	verifyWeAreSorryModalDisplaysInPaymentReviewPage();
    }

    private void verifyWeAreSorryModalDisplaysInPaymentReviewPage() {
    Common.waitForDOMChange();
	Logger.verify("In Payment & Review Page: " + "Verify that \"We're Sorry\" modal displays");
	assertTrue(paymentAndReviewPage2SC.isWeAreSorryModalDisplayed(), "\"We're Sorry\" modal does not display");
    }

    private void verifyWeAreSorryModalDisplaysInShoppingCartPage() {
    Common.waitForDOMChange();
	Logger.verify("In Shopping Cart Page: " + "Verify that \"We're Sorry\" modal displays");
	assertTrue(shoppingCartPage.isWeAreSorryModalDisplayed(), "\"We're Sorry\" modal does not display");
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void applyRewardCardInPaymentReviewPage() {
	Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();

	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Leave with \"Number\" textbox empty" + "- Click \"Submit\" button");
	paymentAndReviewPage2SC.enterVoucherAndClickSubmitButtonInVoucherModal(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 9));
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

    private void applyRewardCardInShoppingCartPage() {
	Logger.info("In Shopping Cart Page:" + "   - Click on \"Enter a Gift Card or Voucher\" link");
	shoppingCartPage.clickEnterGiftCardOrVoucherLink();

	Logger.info("In \"Enter a Gift Card or Voucher\" popup : " + "    - Leave with \"Number\" textbox empty" + "    - Click on \"Submit\" button");

	shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 9));
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id (randomly)" + sku.getName() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCN_03 - We're Sorry\" modal displays when submitting with invalid Reward Card number in Shopping Cart and Payment Review page");
	Logger.to("TO_RCN_03 - In Shopping Cart Page - \"We're Sorry\" modal displays when submitting with invalid Reward Card Number");
	Logger.to("TO_RCN_05	In Payment & Review Page - \"We're Sorry\" modal displays when submitting with invalid Reward Card Number");
	billingAddress = lstAddresses.getDefaultBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF);
    }

}
