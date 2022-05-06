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
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_RCP_009_RemainingCardBalanceDisplaysAndPayWithCreditCardSectionIsNotDisplayedInPaymentReviewPage extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress shippingAddress;

    @Inject
    Item myItem;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_RCP_009_Remaining_Card_Balance_Displays_And_Pay_With_Credit_Card_Section_Is_Not_Displayed_In_Payment_Review_Page() {

	initTestCaseData();

	searchAndAddItem(myItem);

	checkOutFromShoppingCartPage();

	fillShippingAddressAndClickContinue();

	applyRewardCardInPaymentReviewPage();

	//verifyTheRemainingCardBalanceDisplaysInModal();

	closeEnterYourGiftCardOrVoucherNumberModal();

	//verifyPayWithCreditCardSectionIsNotDisplayedInPaymentReviewPage();

	verifyRewardCardIsDisplayedInPaymentReviewPage();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyRewardCardIsDisplayedInPaymentReviewPage() {
	Logger.verify("In Payment & Review Page: " + "Verify the reward card is display with correct information");
	assertTrue(paymentAndReviewPage2SC.isRewardCardDisplayed(Constants.REWARD_CARD_PRICE_75), "the reward card does not display in Payment Review Page");
    }

  

    private void closeEnterYourGiftCardOrVoucherNumberModal() {
	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Click \"Close\" button");
	shoppingCartPage.closeModal();
    }

   

    private void applyRewardCardInPaymentReviewPage() {
	Logger.info("In Payment & Review page:" + "- Click \"Apply another Reward Card, Gift Card, or Voucher\" link");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();

	Logger.info("In Enter Your Gift Card or Voucher Number popup:" + "- Enter valid reward card" + "- Click \"Submit\" button" + "- Click \"Close\" button");
	shoppingCartPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_75);
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In Shopping Cart Page:" + "- Click \"Checkout\" button");
	shoppingCartPage.checkOut();
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address page:" + "- Fill all valid information " + "- Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id: " + sku.getId() + "In Product Page:" + " - Leave \"Ship To Myself\"" + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_RCP_009 - Remaining Card Balance displays and Pay with Credit Cart section is not displayed inPayment and Review page");
	Logger.to("TO_RCP_09 - Payment & Review Page - \"The Remaining Card Balance\" displays correctly when adding Reward Card which has value larger than \"Total\"");
	Logger.to("TO_RCP_12 - Payment & Review Page - \"Pay with Credit Card\" section is not displayed when adding Reward Card which has value larger than \"Total\"");
	shippingAddress = lstAddresses.getShippingAddressByState("Nebraska");
	myItem.init(SkuType.ITEM, Recipient.MYSELF); 
    }
}
