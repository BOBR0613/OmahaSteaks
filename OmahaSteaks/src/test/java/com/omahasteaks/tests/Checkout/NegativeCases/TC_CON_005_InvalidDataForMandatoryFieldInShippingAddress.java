package com.omahasteaks.tests.Checkout.NegativeCases;
/*package tests.Checkout.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.AddressFields;
import data.ENUM.Recipient;
import data.objects.Package;
import data.objects.CustomerAddress;
import data.objects.SKU;
import pages.BillingAddressPage;
import pages.GeneralPage;
import pages.ProductPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Messages;
import utils.helper.Logger;

public class TC_CON_005_InvalidDataForMandatoryFieldInShippingAddress extends TestBase {
    CustomerAddress billingAddress;
    CustomerAddress shippingAddress;
    CustomerAddress invalidShippingAddress;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Package pkg;
    @Inject
    GeneralPage generalPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    BillingAddressPage billingAddressPage;
    @Inject
    ShippingAddressPage shippingAddressPage;

    @Test
    public void TC_CON_005_Invalid_Data_For_Mandatory_Field_InShipping_Address() {
	initTestCaseData();
	searchAndAddItem(package);
	checkOutFromShoppingCartPage();
	fillBillingAddressAndContinueCheckout();
	fillInvalidZipCodeStateCityAndVerifyMessage();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddressAndContinueCheckout() {
	Logger.info("In Billing Address form: \n" + " - Fill valid mandatory information\n" + "- Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout ");
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
    }

    private void fillInvalidZipCodeStateCityAndVerifyMessage() {
	// Verify Invalid city/state/zip information
	Logger.info("'- Fill valid phone number\n" + "- Edit Zipcode to be invalid");
	invalidShippingAddress = shippingAddress.clone();
	invalidShippingAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "12345");
	shippingAddressPage.fillShippingAddress(invalidShippingAddress);
	Logger.verify("A warning displaying:\n" + "\"Invalid city/state/zip information\"");
	assertEquals(billingAddressPage.getAddressWarningMessage(), Messages.INVALID_CITY_STATE_ZIP);
	// Verify Zip code does not match USPS database (95129)
	Logger.info("Edit Zipcode to not match with city and state");
	invalidShippingAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "60603");
	shippingAddressPage.fillShippingAddress(invalidShippingAddress);
	Logger.verify("A warning displaying:\n" + "\"Zip code does not match USPS database (95129)\"");
	assertEquals(billingAddressPage.getAddressWarningMessage(), Messages.ZIPCODE_NOT_MATCH);
	// Verify "Address not found on street"
	Logger.info("Edit City, and State to match with zipcode");
	invalidShippingAddress.updateFieldValueBy(AddressFields.CITY, "Chicago");
	invalidShippingAddress.updateFieldValueBy(AddressFields.CITY, "Illinois");
	shippingAddressPage.fillShippingAddress(invalidShippingAddress);
	Logger.verify("Verify a warning message with below information:\n" + "\"Address not found on street\"");
	assertEquals(billingAddressPage.getAddressWarningMessage(), Messages.ADDRESS_NOT_FOUND);
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_005_InvalidDataForMandatoryFieldInShippingAddress");
	Logger.info("TO_CON_12	Warning message when filling invalid zip code in Shipping address \n" + "TO_CON_13	Warning message when filling Street address, Zip Code, City, State and Country code does not match together in Shipping address");
	package.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = billingAddress.clone();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Hompage, enter \"" + Common.getNumberFromText(sku.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	Logger.info("In Product Page:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Checkout\"\n" + "- If on mobile device: Click \"View My Cart\"");
	productPage.addSKUToCart(sku, false);
	productPage.checkOut();
    }
}
*/