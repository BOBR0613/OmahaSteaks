package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_006_PaymentAndReviewWithBlankAndInvalidCreditAndExpirationDate extends TestBase_2SC {
    @Inject
    ListSKUs myCart;
    @Inject
    SKU mySku;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    CustomerAddress newAddress;
    @Inject
    CustomerAddress shippingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ConfirmationPage2SC confirmationPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Test
    public void TC_2SCON_006_Payment_And_Review_With_Blank_And_Invalid_Credit_And_Expiration_Date() {
	initTestCaseData();

	addFirstSKUToCart(mySku, myCart);

	fillShippingAddressAndContinue();

	fillBillingAddress();

	leaveCreditFieldsBlankAndClickPlaceOrder();

	verifyWarningOfCreditCardFieldsDisplayproperly();

	enterInvalidCreditCardNumberAndClickPlaceOrder();

	verifyWarningOfInvalidCreditCardDisplaysproperly();

	enterInvalidCreditCardExpirationDateAndClickPlaceOrder();

	verifyWarningOfCreditCardExpirationDateDisplaysproperly();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
	Logger.info("Add the first SKU and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Checkout\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	productPage.checkOut();
	shoppingCartPage.checkOut();
    }

    private void enterInvalidCreditCardExpirationDateAndClickPlaceOrder() {
	Logger.info("In Payment & Review page\n" + " - Enter valid credit card number with expiration date is less than current date\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.fillCreditCardNumber(Constants.CREDIT_CARD_NUMBER, false);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void enterInvalidCreditCardNumberAndClickPlaceOrder() {
	Logger.info("In Payment & Review page\n" + " - Enter invalid credit card number with valid expiration date\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.fillCreditCardNumber(Constants.INVALID_CREDIT_CARD_NUMBER, true);
	paymentAndReviewPage2SC.placeOrder();
	paymentAndReviewPage2SC.closeModal();
    }

    private void fillBillingAddress() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
	paymentAndReviewPage2SC.fillBillingAddress(newAddress);
    }

    private void fillShippingAddressAndContinue() {
	Logger.info("In Shipping Address Page\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(newAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCON_006 - Warning message with suitable information appears when filling blank, invalid credit card and expiration date in Payment and Review Page");
	Logger.to("TO_2SCON_17 - Warning message when filling blank Credit card in Payment Options and Review\n");
	Logger.to("TO_2SCON_18 - Warning message when filling invalid Credit card in Payment Options and Review\n");
	Logger.to("TO_2SCON_19 - Warning message when filling Expiration date less than current date in Payment Options and Review");
	mySku = myCart.getRandomSKU(SkuType.ITEM, SkuStatus.EXISTING);
	mySku.setRecipient(Recipient.MYSELF);
	myCart.initEmpty();
	newAddress.initRandomInformation();
    }

    private void leaveCreditFieldsBlankAndClickPlaceOrder() {
	Logger.info("\"In Payment & Review page\n" + " - Leave Credit card field blank\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifyWarningOfCreditCardExpirationDateDisplaysproperly() {
	Logger.verify("Verify the warning \"Enter a Valid Date MM / YY\" displays properly");
	assertEquals(paymentAndReviewPage2SC.getErrorMessageByField(AddressFields.CREDIT_CARD_DATE), Messages.INVALID_CREDIT_CARD_EXPIRATION_DATE_ERROR_MESSAGE);
    }

    private void verifyWarningOfCreditCardFieldsDisplayproperly() {
	Logger.verify("Verify the warning \"Credit Card - Invalid credit card type--please double check your entry.\" displays properly");
	assertEquals(paymentAndReviewPage2SC.getErrorMessageByField(AddressFields.CREDIT_CARD), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.CREDIT_CARD));
	assertEquals(paymentAndReviewPage2SC.getErrorMessageByField(AddressFields.CREDIT_CARD_DATE), Messages.getEmptyErrorMessageInPaymentAndReviewPage(AddressFields.CREDIT_CARD_DATE));
    }

    private void verifyWarningOfInvalidCreditCardDisplaysproperly() {
	Logger.verify("Verify the warning \"Please Enter Your Credit Card\" and \"Please Enter Your Expiration Date\" display properly");
	assertEquals(paymentAndReviewPage2SC.getErrorMessage(), Messages.INVALID_CREDIT_CARD_NUMBER_ERROR_MESSAGE.get(Constants.SITE));
    }
}
