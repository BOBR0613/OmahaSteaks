package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_017_ErrorMessageDisplayWhenSubmitingWithGiftCardVoucherEmpty extends TestBase_2SC {

    CustomerAddress shippingAddress;

    @Inject
    Item myItem;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    SearchResultPage searchResultPage;

    @Inject
    ProductPage productPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Test
    public void TC_2SCON_017_Error_Message_Display_When_Submiting_With_Gift_Card_Voucher_Empty() {

	initTestCaseData();

	searchAndAddItem(myItem);

	clickEnterGiftCartOrVoucherLink();

	enterVoucherAndClickSubmit();

	verifyErrorMessageDisplays();

	closeModalAndCheckOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	clickAppyARewardCardGiftCardOrVoucherLink();

	enterVoucherAndClickSubmit();

	verifyErrorMessageDisplays();
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void verifyErrorMessageDisplays() {
	Logger.verify("Verify that error message displays \r\n" + "\"Please Enter A Valid Number\"");
	assertEquals(generalPage.getErrorMessageInVoucherModal(), Messages.VOUCHER_NUMBER_EMPTY_MESSAGE);
    }

    private void enterVoucherAndClickSubmit() {
	Logger.info("In \"Enter Your Gift Card or Voucher Number\" modal, leave number textbox empty and click SUBMIT button");
	generalPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.EMPTY_STRING);
    }

    private void clickAppyARewardCardGiftCardOrVoucherLink() {
	Logger.info("In Payment and Review Page, click \"Apply a Reward Card, Gift Card, or Voucher\"");
	paymentAndReviewPage2SC.clickApplyARewardCardGiftCardOrVoucherLink();
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address Page:\r\n" + "- Fill valid information into Shipping Address section\r\n" + "- Click  \"Continue \"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void closeModalAndCheckOutFromShoppingCart() {
	Logger.info("Close Enter Your Gift Card or Voucher Number modal and click \"Checkout\"");
	shoppingCartPage.closeModal();
	shoppingCartPage.checkOut();
    }

    private void clickEnterGiftCartOrVoucherLink() {
	Logger.info("In Shopping Cart (My Cart), Click \"Enter a Gift Card or Voucher\"");
	shoppingCartPage.clickEnterGiftCardOrVoucherLink();
    }

    private void searchAndAddItem(Item item) {
	Logger.info("From Homepage, Search a " + Common.getNumberFromText(item.getId()) + " with ID: " + item.getId());
	generalPage.search(Common.getNumberFromText(item.getId()));

	Logger.info("In Search page\n" + "- Select the first Item and add it to Cart" + "In Exclusive Offer (Upsell offer) modal:\r\n" + "- Click \"No Thanks\"");
	productPage.addSKUToCart(item, false);

	Logger.info("In \"Added To Cart\" popup: \r\n" + "- Click \"CHECKOUT\"");
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCON_017 In Shopping Cart (My Cart), Click \"Enter a Gift Card or Voucher\"\n");
	Logger.to("TO_2SCON_46	In Shopping Cart page - \"Enter a Gift Card or Voucher\" - Error message displays when submiting with no data in the Number text field\n");
	Logger.to("TO_2SCON_47	In Payment & Review page - 'Apply a Reward Card, Gift, or Voucher' link - Error message displays when submiting with no data in the Number text field\n");
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF, false);
    }

}
