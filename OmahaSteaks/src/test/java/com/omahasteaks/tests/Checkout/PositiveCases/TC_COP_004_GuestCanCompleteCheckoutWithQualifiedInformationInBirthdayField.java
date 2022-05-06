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
import pages.SignInPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Constants;
import utils.helper.Logger;

public class TC_COP_004_GuestCanCompleteCheckoutWithQualifiedInformationInBirthdayField extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item itemMyself, itemRep1, itemRep2;

    @Inject
    CustomerAddress billingAddress, myShippingAddress, rep1ShippingAddress, rep2ShippingAddress;

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
    public void TC_COP_004_Guest_Can_Complete_Checkout_With_Qualified_Information_In_Birthday_Field() {
	initTestCaseData();

	signInWithExistingAccount();

	searchAndAddItem(itemMyself);

	searchAndAddItem(itemRep1);

	searchAndAddItem(itemRep2);

	checkOutFromShoppingCartPage();

	checkBirthdayLabelDisplaysRequired();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout(myShippingAddress);

	fillShippingAddressAndContinueCheckout(rep1ShippingAddress);

	fillShippingAddressAndContinueCheckout(rep2ShippingAddress);

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkBirthdayLabelDisplaysRequired() {
	Logger.verify("Birthday becomes required field (Birthday label becomes \"Birthday (required)\")");
	assertTrue(billingAddressPage.isRequiredBirthdayLabelDisplayed(), "The required birthday is not displayed as expected");
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	goToMyCart();
	shoppingCartPage.checkOut();
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
	Logger.tc("TC_COP_004_Guest Can Complete Checkout With Qualified Information In Birthday Field");
	Logger.to("TO_COP_20	The Birthday field become required field when containing wine in cart\n");
	Logger.to("TO_COP_21	Guest can complete the Checkout when cart containing wine if the age is qualified.\n");
	itemMyself.initByName("Tour de France Wine Gift Set", Recipient.MYSELF);
	itemRep1.initByName("Sweet Wine Trio", Recipient.KIM_ANH);
	itemRep2.initByName("Napa Valley Wine Trio", Recipient.THAO_NHO);
	billingAddress = lstAddresses.getRandomBillingAddress();
	billingAddress.setRandomBirthday(true);
	myShippingAddress = lstAddresses.getRandomShippingAddressByState("New York");
	rep1ShippingAddress = lstAddresses.getRandomShippingAddressByState("New York");
	rep2ShippingAddress = lstAddresses.getRandomShippingAddressByState("New York");
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private void signInWithExistingAccount() {
	Logger.info("Sign in with existing account");
	generalPage.goToSignInPage();
	signInPage.signIn(Constants.OMAHA_EMAIL, Constants.OMAHA_PASSWORD);
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/