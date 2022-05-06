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

public class TC_COP_007_CompleteCheckoutWithAutoShipAndSave extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    package packageRecipient1;
    @Inject
    package packageRecipient2;
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
    public void TC_COP_007_Complete_Checkout_With_AutoShip_And_Save() {
	initTestCaseData();
	signIn();
	searchAndAddItem(packageRecipient1);
	searchAndAddItem(packageRecipient2);
	goToMyCart();
	verifySKUAddedInCartWithAutoShipOption();
	checkOut();
	fillBillingAddressAndContinueCheckout();
	fillShippingAddressAndContinueCheckout();
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
	Logger.info("Go to Shopping Cart page by clicking \"My Cart\" icon");
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.info("TC_COP_007_CompleteCheckoutWithAutoShipAndSave");
	Logger.info("TO_COP_09	Guest can select Autoship using \"Standard Shipping\" and complete Checkout");
	account = Constants.LIST_ACCOUNTS.getRandomAccount();
	packageRecipient1.initByName("Celebratory Collection", Recipient.THONG_NGUYEN);
	packageRecipient2.initByName("The Tasteful Gift", Recipient.KIM_ANH);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(package package) {
	Logger.info("Search a SKU with id (e.g. " + Common.getNumberFromText(package.getId()) + ")");
	generalPage.search(Common.getNumberFromText(package.getId()));
	Logger.info("Search a SKU with id (e.g. " + package.getId() + ")\n" + "In Product Page:\n" + " - Select Ship To \"" + package.getRecipient() + "\"\n" + " - Select \"AutoShip & Save (Extra 5%)\" radio\n" + " - Select the first option in ComboBox (Every Week)\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop plaftform: Click \"Continue Shopping\"\n" + "- If on mobile device: Close pop up because of not having \"Continue Shopping\" button");
	productPage.addSKUToCart(package, "Every Week", false);
	productPage.continueShopping();
    }

    private void signIn() {
	Logger.info("Sign in with a valid account");
	generalPage.goToSignInPage();
	signInPage.signIn(account.getEmail(), account.getPassword());
    }

    private void verifySKUAddedInCartWithAutoShipOption() {
	Logger.verify("VP	In My Cart, verify the package with correct SKUID added in Myself Cart with AutoShip option");
	assertTrue(shoppingCartPage.isSkuByIdAdded(packageRecipient1.getRecipient(), packageRecipient1.getId(), "Every Week"), "The package is not added with AutoShip option");
	assertTrue(shoppingCartPage.isSkuByIdAdded(packageRecipient2.getRecipient(), packageRecipient2.getId(), "Every Week"), "The package is not added with AutoShip option");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("VP Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/