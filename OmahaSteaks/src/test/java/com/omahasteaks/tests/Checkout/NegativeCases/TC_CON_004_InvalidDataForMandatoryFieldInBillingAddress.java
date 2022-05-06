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
import pages.ShoppingCartPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Messages;
import utils.helper.Logger;

public class TC_CON_004_InvalidDataForMandatoryFieldInBillingAddress extends TestBase {
    CustomerAddress billingAddress;
    CustomerAddress invalidBillingAddress;
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

    @Test
    public void TC_CON_004_Invalid_Data_For_Mandatory_Field_InBilling_Address() {
	initTestCaseData();
	searchAndAddItem(package);
	checkOutFromShoppingCartPage();
	fillInvalidPhoneNumberAndVerifyMessage();
	fillInvalidZipCodeStateCityAndVerifyMessage();
	fillConfirmEmailToNotMatchWithEmailAndVerifyMessage();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillConfirmEmailToNotMatchWithEmailAndVerifyMessage() {
	Logger.info("'- Edit Zipcode to be valid\n" + "- Edit 'Confirm Email' to not match with Email\n" + "- Click 'Continue Checkout' button");
	invalidBillingAddress = billingAddress.clone();
	invalidBillingAddress.updateFieldValueBy(AddressFields.CONFIRM_EMAIL, "NOT_MATCH_EMAIL");
	billingAddressPage.fillBillingAddress(invalidBillingAddress);
	billingAddressPage.continueCheckout();
	AddressFields field = AddressFields.CONFIRM_EMAIL;
	Logger.verify("Verify a warning message with below information:\n" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
	assertEquals(billingAddressPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field));
    }

    private void fillInvalidPhoneNumberAndVerifyMessage() {
	Logger.info("In Billing Address page:\n" + " - Fill valid all information except Phone number\n" + " - Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout");
	invalidBillingAddress = billingAddress.clone();
	invalidBillingAddress.updateFieldValueBy(AddressFields.PHONE, "123456789");
	billingAddressPage.fillBillingAddress(invalidBillingAddress);
	billingAddressPage.continueCheckout();
	Logger.verify("Verify a warning message with below information:\n" + "Phone Number: The phone number you specified is invalid. Please specify a phone number with the area code.");
	assertEquals(billingAddressPage.getInvalidMessageByField(AddressFields.PHONE), Messages.getInvalidMessageByField(AddressFields.PHONE));
    }

    private void fillInvalidZipCodeStateCityAndVerifyMessage() {
	// Verify Invalid city/state/zip information
	Logger.info("'- Fill valid phone number\n" + "- Edit Zipcode to be invalid");
	invalidBillingAddress = billingAddress.clone();
	invalidBillingAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "12345");
	billingAddressPage.fillBillingAddress(invalidBillingAddress);
	Logger.verify("A warning displaying:\n" + "\"Invalid city/state/zip information\"");
	assertEquals(billingAddressPage.getAddressWarningMessage(), Messages.INVALID_CITY_STATE_ZIP);
	// Verify Zip code does not match USPS database (95129)
	Logger.info("Edit Zipcode to not match with city and state");
	invalidBillingAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "60603");
	billingAddressPage.fillBillingAddress(invalidBillingAddress);
	Logger.verify("A warning displaying:\n" + "\"Zip code does not match USPS database (95129)\"");
	assertEquals(billingAddressPage.getAddressWarningMessage(), Messages.ZIPCODE_NOT_MATCH);
	// Verify "Address not found on street"
	Logger.info("Edit City, and State to match with zipcode");
	invalidBillingAddress.updateFieldValueBy(AddressFields.CITY, "Chicago");
	invalidBillingAddress.updateFieldValueBy(AddressFields.CITY, "Illinois");
	billingAddressPage.fillBillingAddress(invalidBillingAddress);
	Logger.verify("Verify a warning message with below information:\n" + "\"Address not found on street\"");
	assertEquals(billingAddressPage.getAddressWarningMessage(), Messages.ADDRESS_NOT_FOUND);
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_004_InvalidDataForMandatoryFieldInBillingAddress");
	Logger.info("TO_CON_05	Warning message when filling invalid phone number in Billing address \n" + "TO_CON_06	Warning message when filling invalid zip code in Billing address \n" + "TO_CON_07	Warning message when filling Street address, Zip Code, City, State and Country code does not match together in Billing address \n" + "TO_CON_08	Warning message when filling confirmed email address which does not match with email address in Billing address");
	package.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	billingAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "CA" + billingAddress.zipCode);
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