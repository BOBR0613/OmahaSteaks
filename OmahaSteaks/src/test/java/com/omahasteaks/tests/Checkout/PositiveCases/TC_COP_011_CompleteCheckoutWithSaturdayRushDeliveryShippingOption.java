package com.omahasteaks.tests.Checkout.PositiveCases;
/*package tests.Checkout.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.AddressFields;
import data.ENUM.Recipient;
import data.objects.CustomerAddress;
import data.objects.Item;
import pages.BillingAddressPage;
import pages.ConfirmationPage;
import pages.GeneralPage;
import pages.PaymentAndReviewPage;
import pages.ProductPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import tests.TestBase;
import utils.common.Common;
import utils.helper.Logger;

public class TC_COP_011_CompleteCheckoutWithSaturdayRushDeliveryShippingOption extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item itemMyself;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CustomerAddress myShippingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    BillingAddressPage billingAddressPage;
    @Inject
    ShippingAddressPage shippingAddressPage;
    @Inject
    PaymentAndReviewPage paymentAndReviewPage;
    @Inject
    ConfirmationPage confirmationPage;

    @Test
    public void TC_COP_011_Complete_Checkout_With_Saturday_Delivery_Shipping_Option() {
	initTestCaseData();

	searchAndAddItem(itemMyself);

	checkOutFromShoppingCartPage();

	fillBillingAddressAndContinueCheckout();

	fillShippingAddressAndContinueCheckout(myShippingAddress);

	selectSaturdayDeliveryShippingOption();

	checkSaturdayDeliveryShippingOptionIsSelected();

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

    private void checkSaturdayDeliveryShippingOptionIsSelected() {
	Logger.verify("Verify that \"Saturday Rush Delivery\" option is selected");
	assertTrue(paymentAndReviewPage.isCheckboxShippingOptionByNameOfRecipientChecked(itemMyself.getRecipient(), "Saturday Rush Delivery"), "Saturday Rush Delivery option is not checked as expected");
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
	if (!Common.isValidDayForCOP011()) {
	    throw new SkipException("This time is not suitable time frame for this case");
	}
	Logger.tc("TC_COP_011_Complete Checkout With \"Saturday Rush Delivery\" Shipping Option");
	Logger.to("TO_COP_22	Guest can select \"Saturday delivery\" shipping options for non-free-shipping SKU and complete checkout\n");
	itemMyself.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	myShippingAddress = lstAddresses.getDefaultShippingAddress();
	myShippingAddress.updateFieldValueBy(AddressFields.COMPANY_NAME, "");
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private void selectSaturdayDeliveryShippingOption() {
	Logger.info("Click \"View All Shipping Options\" link in \"Your Shipping Information\" section");
	paymentAndReviewPage.clickRecipientViewAllShippingOptions(itemMyself.getRecipient());
	Logger.info("In Shipping Option popup:\n" + "- Select \"Saturday Rush Delivery\" shipping options\n" + "- Click \"Continue Checkout\"");
	paymentAndReviewPage.selectShippingOptionByName("Saturday Rush Delivery");
	paymentAndReviewPage.continueCheckout();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
*/