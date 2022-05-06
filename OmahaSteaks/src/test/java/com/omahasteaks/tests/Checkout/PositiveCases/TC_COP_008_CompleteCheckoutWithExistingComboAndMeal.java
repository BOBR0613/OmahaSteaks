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
import data.objects.Meal;
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
import utils.common.Constants;
import utils.helper.Logger;

public class TC_COP_008_CompleteCheckoutWithExistingpackageAndMeal extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Package pkg;
    @Inject
    Meal meal;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CustomerAddress shippingAddress;
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
    public void TC_COP_008_Complete_Checkout_With_Existing_package_And_Meal() {
	initTestCaseData();

	signIn();

	searchAndAddItem(package);

	searchAndAddItem(meal);

	goToMyCart();

	verifypackageAndMealAddedSuccessfully();

	checkOut();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    private void checkOut() {
	Logger.info("In Shopping Cart page:\n" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddressAndContinueCheckout() {
	Logger.info("In Billing Address form: \n" + " - Fill valid mandatory information\n" + "- Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout");
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date:we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber();
	paymentAndReviewPage.placeMyOrder();
    }

    private void fillShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address form:\n" + " - Enter valid information\n" + " - Click Continue Checkout");
	shippingAddressPage.fillShippingAddress(shippingAddress);
	shippingAddressPage.continueCheckout();
    }

    private void goToMyCart() {
	Logger.info("Go to Shopping Cart page");
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.tc("TC_COP_008_Complete Checkout With Existing package And Meal");
	Logger.to("TO_COP_16	Guest can add an existing package into cart and then complete Checkout\n");
	Logger.to("TO_COP_17	Guest can add an existing meal into cart and then complete Checkout\n");
	account = Constants.LIST_ACCOUNTS.getRandomAccount();
	package.initRandom(Recipient.MYSELF);
	meal.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("Search a package with id (e.g. 52477)");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	Logger.info("In Product Page:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Close pop up or Click \"Continue Shopping\"");
	productPage.addSKUToCart(sku, false);
	productPage.continueShopping();
    }

    private void signIn() {
	generalPage.goToSignInPage();
	signInPage.signIn(account.getEmail(), account.getPassword());
    }

    private void verifypackageAndMealAddedSuccessfully() {
	Logger.verify("In My Cart, verify the sku (include main item and special item) with correct names exist in My Shopping Cart and number above \"My Cart\" icon equal to added SKUs");
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, package.getId()), "The sku is not displayed as expected");
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, meal.getId()), "The sku is not displayed as expected");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("VP Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/