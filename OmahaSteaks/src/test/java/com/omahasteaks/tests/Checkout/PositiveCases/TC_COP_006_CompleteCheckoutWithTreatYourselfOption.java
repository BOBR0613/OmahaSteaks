package com.omahasteaks.tests.Checkout.PositiveCases;
/*package tests.Checkout.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.Recipient;
import data.objects.Package;
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
import pages.TreatYourselfPage;
import tests.TestBase;
import utils.common.Common;
import utils.helper.Logger;

public class TC_COP_006_CompleteCheckoutWithTreatYourselfOption extends TestBase {

    @Inject
    ListAddresses lstAddresses;

    @Inject
    Item itemRecipient;

    @Inject
    package packageMyself;

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

    
     * @Inject TreatYourselfPage treatYourselfPage;
     

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
    public void TC_COP_006_Complete_Checkout_With_Treat_Yourself_Option() {

	initTestCaseData();

	searchAndAddItem(itemRecipient);

	checkOut();

	addToCartInTreatYourselfPage();

	verifyTreatYouTooAddedInMyselfCart();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();
    }

    private void addToCartInTreatYourselfPage() {
	
	 * treatYourselfPage.addpackageToCart(packageMyself, false); treatYourselfPage.checkOut();
	 
    }

    private void checkOut() {
	Logger.info("Go to Shopping Cart page");
	generalPage.goToMyCart();
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

    private void initTestCaseData() {
	if (true) {
	    Logger.warning("This TC is skipped due to \"Treat Your Self\" Page is removed!");
	    throw new SkipException("Skipping - This is not ready for testing ");
	}

	Logger.info("COP_006_CompleteCheckoutWithTreatYourSelfOption");
	Logger.info("TO05	In 'Treat Yourself' page, customer can select 'Ship to myself' and then complete Checkout");

	itemRecipient.initRandom(Recipient.THONG_NGUYEN);
	packageMyself.initByName("Treat You, Too", Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(Item item) {
	Logger.info("Search a SKU with id (e.g. " + item.getId() + ")");
	generalPage.search(Common.getNumberFromText(item.getId()));

	Logger.info("In Product Page:\n" + " - Select \"Ship To Someone Else\"\n" + " - Enter the Recipient's name 1 (e.g. Thong Nguyen)\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Close pop up or Click \"Continue Shopping\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("VP Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }

    private void verifyTreatYouTooAddedInMyselfCart() {
	Logger.verify("In My Cart, verify the package with correct SKUID added in Myself Cart");
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, packageMyself.getId()), "The sku is not displayed as expected");
	shoppingCartPage.checkOut();
    }
}
*/