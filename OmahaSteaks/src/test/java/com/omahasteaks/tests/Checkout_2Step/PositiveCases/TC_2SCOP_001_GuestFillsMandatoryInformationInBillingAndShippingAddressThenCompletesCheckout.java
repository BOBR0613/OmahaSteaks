package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_001_GuestFillsMandatoryInformationInBillingAndShippingAddressThenCompletesCheckout extends TestBase_2SC {
    @Inject
    ListSKUs myCart;
    @Inject
    Package mySku;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CustomerAddress shippingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ConfirmationPage2SC confirmationPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ProductPage productPage;

    @Test
    public void TC_2SCOP_001_Guest_Fills_Mandatory_Information_In_Billing_And_Shipping_Address_Then_Completes_Checkout() {
	initTestCaseData();

	addSKUToCartAndCheckOut(mySku);

	fillShippingAddress();

	verifyShippingAddressDisplaysCorrectly();

	fillBillingAddressAndCreditCardNumberThenPlaceThisOrder();

	verifyBillingAddressDisplaysCorrectly();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void addSKUToCartAndCheckOut(SKU sku) {
	Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();

	Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddressAndCreditCardNumberThenPlaceThisOrder() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Click \"Add New Address\" link\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_001 - Guest Fills Mandatory Information In Billing And Shipping Address Then Completes Checkout");
	Logger.to("TO_2SCOP_04 - User can fill mandatory information in Shipping Address (Select \"Add New Address\") and Billing Address then complete Checkout");
	mySku.initRandom(Recipient.MYSELF);
	account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrmember01@omahasteaks.com");
	shippingAddress.initRandomInformation();
	shippingAddress.removeShippingAddressOptionalFields();
	billingAddress.initRandomInformation();
    }

    private void verifyBillingAddressDisplaysCorrectly() {
	confirmationPage.closeModal();
	Logger.verify("In Order Receipt Page\n" + " - Verify that added Billing address displays correctly\n" + " - Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.\n" + " - Verify Contain text \"Order Number:\" + an order number format having 12 characters.");
	assertEquals(confirmationPage.getBillingAddress(), billingAddress.toBillingArray(), "The billing address is not displayed as expected");
	assertTrue(confirmationPage.isThankYouMessageDisplayed(), "Thank you message is not displayed");
	String oderNumber = confirmationPage.getOrderNumberText();
	assertEquals(oderNumber.split(":")[0].trim(), "Order Number");
	assertTrue(confirmationPage.isOrderNumberCorrectFormat(oderNumber.split(":", 2)[1].trim()));
    }

    private void verifyShippingAddressDisplaysCorrectly() {
	Logger.verify("Verify Shipping Address displays correctly");
	assertEquals(paymentAndReviewPage2SC.getShippingAddress(Recipient.MYSELF.getValue()), shippingAddress.toShippingArray(), "The shipping address is not displayed as expected");
    }
}
