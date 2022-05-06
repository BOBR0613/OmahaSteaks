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
import pages.BillingAddressPage;
import pages.ConfirmationPage;
import pages.CustomPackagePage;
import pages.GeneralPage;
import pages.PaymentAndReviewPage;
import pages.SearchResultPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import pages.SignInPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Constants;
import utils.helper.Logger;

public class TC_COP_009_CompleteCheckoutWithCustomPackageAndMeal extends TestBase {
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
    CustomPackagePage CustomPackagePage;
    @Inject
    BillingAddressPage billingAddressPage;
    @Inject
    ShippingAddressPage shippingAddressPage;
    @Inject
    PaymentAndReviewPage paymentAndReviewPage;
    @Inject
    ConfirmationPage confirmationPage;

    @Test
    public void TC_COP_009_Complete_Checkout_With_Custom_package_And_Meal() {
	initTestCaseData();

	signIn();

	searchAndCreteCustomPackage(Common.getNumberFromText(package.getId()));

	searchAndCreteCustomMeal(Common.getNumberFromText(meal.getId()));

	verifypackageAndMealAddedInCart();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
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

    private void initTestCaseData() {
	Logger.tc("TC_COP_009_Complete Checkout With New package And Meal");
	Logger.to("TO_COP_18	Guest can create a package, add to cart, and then complete Checkout\n");
	Logger.to("TO_COP_19	Guest can create a meal, add to cart, and then complete Checkout\n");
	account = Constants.LIST_ACCOUNTS.getRandomAccount();
	package.initByName("Value Custom package", Recipient.THONG_NGUYEN);
	meal.initByName("Build Your Own Meal for 4", Recipient.THONG_NGUYEN);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndCreteCustomPackage(String keyword) {
	Logger.info("Search a Custom package with id (e.g. 54786)");
	generalPage.search(keyword);
	Logger.info("In Custom package Page:\n" + " - Select 1 item in each group\n" + " - Select \"Ship To Someone Else\"\n" + " - Enter the Recipient's name 1 (e.g. Thong Nguyen)\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Close pop up or Click \"Continue Shopping\"");
	CustomPackagePage.createCustomPackageAndAddToCart(package, false);
	CustomPackagePage.continueShopping();
    }

    private void searchAndCreteCustomMeal(String keyword) {
	Logger.info("Search a Custom MEAL with id (e.g. 55091)");
	generalPage.search(keyword);
	Logger.info("In Build Your Own Meal Page:\n" + " - Select 1 item in each group\n" + " - Select Ship To \"Thong Nguyen\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	CustomPackagePage.createCustomPackageAndAddToCart(meal, false);
	CustomPackagePage.checkOut();
    }

    private void signIn() {
	Logger.info("Sign in with a valid account");
	generalPage.goToSignInPage();
	signInPage.signIn(account.getEmail(), account.getPassword());
    }

    private void verifypackageAndMealAddedInCart() {
	Logger.verify("In My Cart, verify the package and the meal with correct SKUID added in Myself Cart");
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.THONG_NGUYEN, package.getId()), "The sku is not displayed as expected");
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.THONG_NGUYEN, meal.getId()), "The sku is not displayed as expected");
	shoppingCartPage.checkOut();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("VP Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/