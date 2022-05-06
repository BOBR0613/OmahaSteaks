package com.omahasteaks.tests.Checkout.PositiveCases;
/*package tests.Checkout.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.Recipient;
import data.objects.Package;
import data.objects.CustomerAddress;
import data.objects.Item;
import data.objects.SKU;
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

public class TC_COP_003_GuestCanAddFreeShipingSKUThenCompleteCheckout extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    package packageMyself;
    @Inject
    Item itemRep1;
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
    public void TC_COP_003_Guest_Can_Add_Free_Shiping_SKU_Then_Complete_Checkout() {
	initTestCaseData();

	searchAndAddItem(packageMyself);

	searchAndAddItem(itemRep1);

	checkOutFromShoppingCartPage();

	fillBillingAddressAndContinueCheckout();

	checkSKUInfoInShippingAddressPage(packageMyself);

	checkFreeShippingInShippingAddressPage(packageMyself);

	fillShippingAddressAndContinueCheckout(myShippingAddress);

	checkSKUInfoInShippingAddressPage(itemRep1);

	fillShippingAddressAndContinueCheckout(rep1ShippingAddress);

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkFreeShippingInShippingAddressPage(SKU sku) {
	Logger.verify("- Free Shipping is displayed correctly in " + sku.getRecipient() + "'s Shipment Summary\" section");
	assertTrue(shippingAddressPage.isFreeShippingDisplayed(sku));
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	goToMyCart();
	shoppingCartPage.checkOut();
    }

    private void checkSKUInfoInShippingAddressPage(SKU sku) {
	Logger.verify("- SKU's Info is displayed correctly in " + sku.getRecipient() + "'s Shipment Summary\" section");
	assertTrue(shippingAddressPage.isSKUDisplayed(sku), "The sku is not displayed in shipping page as expected");
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
	Logger.tc("TC_COP_003_Guest Can Add Free Shipping SKU Then Complete Checkout");
	Logger.to("TO_COP_12	Add to cart with shipping free SKU then shipping free option displaying in \"Shipment Summary\"\n");
	Logger.to("TO_COP_14	Information in \"Shipment Summary\" match with number of SKUs of Recipient in My Cart\n");
	Logger.to("TO_COP_15	Information in \"Shipment Summary\" match with number of SKUs of Myself and Recipient in My Cart\n");
	packageMyself.initRandom(Recipient.MYSELF);
	itemRep1.initRandom(Recipient.THAO_NHO);
	billingAddress = lstAddresses.getRandomBillingAddress();
	myShippingAddress = billingAddress;
	rep1ShippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(SKU sku) {
	String keyword = Common.getNumberFromText(sku.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + sku.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(sku, false);
	productPage.continueShopping();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/