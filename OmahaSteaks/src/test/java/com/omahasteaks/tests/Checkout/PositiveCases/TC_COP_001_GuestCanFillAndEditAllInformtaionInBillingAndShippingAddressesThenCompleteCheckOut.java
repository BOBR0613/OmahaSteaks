package com.omahasteaks.tests.Checkout.PositiveCases;
/*package tests.Checkout.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.Recipient;
import data.objects.CustomerAddress;
import data.objects.Item;
import pages.BillingAddressPage;
import pages.ConfirmationPage;
import pages.GeneralPage;
import pages.PaymentAndReviewPage;
import pages.ProductPage;
import pages.SearchResultPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import pages.SignInPage;
import tests.TestBase;
import utils.common.Common;
import utils.helper.Logger;

public class TC_COP_001_GuestCanFillAndEditAllInformtaionInBillingAndShippingAddressesThenCompleteCheckOut extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item itemMyself;
    @Inject
    Item itemRep1;
    @Inject
    Item itemRep2;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CustomerAddress myShippingAddress;
    @Inject
    CustomerAddress rep1ShippingAddress;
    @Inject
    CustomerAddress rep2ShippingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    SignInPage signInPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    BillingAddressPage billingAddressPage;
    @Inject
    ShippingAddressPage shippingAddressPage;
    @Inject
    PaymentAndReviewPage paymentAndReviewPage;
    @Inject
    ConfirmationPage confirmationPage;

    @Test
    public void TC_COP_001_Guest_Can_Fill_And_Edit_All_Informtaion_In_Billing_And_Shipping_Addresses_Then_Complete_Check_Out() {
	initTestCaseData();

	searchAndAddItem(itemMyself);

	searchAndAddItem(itemRep1);

	searchAndAddItem(itemRep2);

	goToMyCart();

	checkOutFromShoppingCartPage();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout(myShippingAddress);

	fillShippingAddressAndContinueCheckout(rep1ShippingAddress);

	fillShippingAddressAndContinueCheckout(rep2ShippingAddress);

	verifyAddressesDisplayCorrectly();

	reInitTestData();

	editBillingAddress();

	editShippingAddress();

	verifyAddressesDisplayCorrectly();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void editBillingAddress() {
	paymentAndReviewPage.clickEditBillingAddressLink();
	fillBillingAddressAndContinueCheckout();
    }

    private void editShippingAddress() {
	paymentAndReviewPage.clickRecipientEditShippingAddressLink(Recipient.MYSELF);
	fillShippingAddressAndContinueCheckout(myShippingAddress);
    }

    private void fillBillingAddressAndContinueCheckout() {
	Logger.info("In Billing Address form: \n" + " - Fill valid mandatory information\n" + "- Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout ");
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber();
	paymentAndReviewPage.placeMyOrder();
    }

    private void fillShippingAddressAndContinueCheckout(CustomerAddress shippingAddress) {
	Logger.info("In " + shippingAddress.firstName + " " + shippingAddress.lastName + " Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage.fillShippingAddress(shippingAddress);
	shippingAddressPage.continueCheckout();
    }

    private void goToMyCart() {
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.tc("TC_COP_001_Guest Can Fill And Edit All Information In Billing And Shipping Addresses Then Complete Check Out");
	Logger.to("TO_COP_01	Guest can fill all information (mandatory information and optional information) in Billing and Shipping address then complete Checkout\n");
	Logger.to("TO_COP_03	Guest can edit information of Billing address at Payment & Review page then complete Checkout\n");
	Logger.to("TO_COP_04	Guest can edit information of Shipping address at Payment & Review page then complete Checkout\n");
	itemMyself.initRandom(Recipient.MYSELF);
	itemRep1.initRandom(Recipient.THAO_NHO);
	itemRep2.initRandom(Recipient.KIM_ANH);
	billingAddress = lstAddresses.getRandomBillingAddress();
	myShippingAddress = lstAddresses.getDefaultShippingAddress();
	rep1ShippingAddress = myShippingAddress.clone();
	rep2ShippingAddress = myShippingAddress.clone();
    }

    private void reInitTestData() {
	myShippingAddress = billingAddress.clone();
	billingAddress = rep1ShippingAddress.clone();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private void verifyAddressesDisplayCorrectly() {
	Logger.verify("In Payment Option and Review page, verify that:\n" + " - Added Billing address displays correctly\n" + " - Added Shipping address displays correctly");
	assertEquals(paymentAndReviewPage.getBillingAddress(), billingAddress.toArray(), "The billing address is not displayed as expected");
	assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.MYSELF), myShippingAddress.toArray(), "Myselfs shipping address is not displayed as expected");
	assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.THAO_NHO), rep1ShippingAddress.toArray(), "Thao's shipping address is not displayed as expected");
	assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.KIM_ANH), rep2ShippingAddress.toArray(), "Kim Anh's shipping address is not displayed as expected");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/