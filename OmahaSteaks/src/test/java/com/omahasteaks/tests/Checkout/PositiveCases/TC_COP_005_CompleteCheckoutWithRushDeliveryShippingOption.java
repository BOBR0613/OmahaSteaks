package com.omahasteaks.tests.Checkout.PositiveCases;
/*package tests.Checkout.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import tests.TestBase;
import utils.common.Common;
import utils.helper.Logger;

public class TC_COP_005_CompleteCheckoutWithRushDeliveryShippingOption extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item itemMyself;
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
    public void TC_COP_005_Complete_Checkout_With_Rush_Delivery_Shipping_Option() {
	initTestCaseData();

	searchAndAddItem(itemMyself);

	checkOutFromShoppingCartPage();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout(myShippingAddress);

	selectRushDeliveryShippingOption();

	checkRushDeliveryShippingOptionIsSelected();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	goToMyCart();
	shoppingCartPage.checkOut();
    }

    private void checkRushDeliveryShippingOptionIsSelected() {
	Logger.verify("Verify that \"Rush Delivery\" option is selected");
	assertTrue(paymentAndReviewPage.isCheckboxShippingOptionByNameOfRecipientChecked(itemMyself.getRecipient(), "Rush Delivery"), "Rush Delivery option is not checked as expected");
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
	Logger.tc("TC_COP_005_CompleteCheckoutWith \"Rush Delivery\" shipping options");
	Logger.to("Guest can select \"Rush Delivery\" shipping options for non-free-shipping SKU and complete checkout\n");
	itemMyself.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	myShippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private void selectRushDeliveryShippingOption() {
	Logger.verify("Click \"View All Shipping Options\" link in \"Your Shipping Information\" section");
	paymentAndReviewPage.clickRecipientViewAllShippingOptions(itemMyself.getRecipient());
	Logger.verify("In Shipping Option popup:\n" + "- Select \"Rush Delivery\" shipping options\n" + "- Click \"Continue Checkout\"");
	paymentAndReviewPage.selectShippingOptionByName("Rush Delivery");
	paymentAndReviewPage.continueCheckout();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/