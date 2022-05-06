package com.omahasteaks.tests.Checkout.NegativeCases;
/*package tests.Checkout.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;

import data.ListAddresses;
import data.ENUM.AddressFields;
import data.ENUM.Recipient;
import data.objects.CustomerAddress;
import data.objects.Item;
import data.objects.SKU;
import pages.BillingAddressPage;
import pages.GeneralPage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Messages;
import utils.helper.Logger;

public class TC_CON_003_WarningWhenBuyingWineWithEmptyOrInvalidAge extends TestBase {
    CustomerAddress billingAddress;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item wine;
    @Inject
    GeneralPage generalPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    BillingAddressPage billingAddressPage;

    @Test
    public void TC_CON_003_Warning_When_Buying_Wine_With_Empty_Or_Invalid_Age() {
	initTestCaseData();
	searchAndAddItem(wine);
	checkOutFromShoppingCartPage();
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.BIRTHDAY);
	fillBillingAddressWithTheAgeIsLessThan21AndVerifyErrorMessage();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillBillingAddressWithTheAgeIsLessThan21AndVerifyErrorMessage() {
	Logger.info("In Billing Address page:\n" + " - Fill in Birthday field so that the age is less than 21");
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
	AddressFields field = AddressFields.BIRTHDAY_UNDER_21;
	Logger.verify("Verify a warning message with below information:\n" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
	assertEquals(billingAddressPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field));
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_003_WarningWhenBuyingWineWithEmptyOrInvalidAge");
	Logger.info("TO_CON_03	Warning message when adding SKU item: wine/alcohol and not filling Month, Day and Year in Birthday in Billing address \n" + "TO_CON_04	Warning message when adding SKU item: wine/alcohol and filling birthday under 21 year olds in Billing address");
	wine.initRandom(Recipient.MYSELF, true);
	billingAddress = lstAddresses.getRandomBillingAddress();
	billingAddress.setRandomBirthday(false);
    }

    private void leaveFieldEmptyAndVerifyErrorMessage(AddressFields field) {
	Logger.info("In Billing Address page:\n" + " - Fill valid all information except birthday information (let Birthday fields empty)\n" + " - Check on \"I agree to the Terms of Use and Privacy Policy\" checkbox\n" + " - Click Continue Checkout ");
	billingAddressPage.fillBillingAddressExceptField(billingAddress, field);
	billingAddressPage.continueCheckout();
	Logger.verify("Verify a warning message with below information:\n" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
	assertEquals(billingAddressPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field));
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Hompage, enter \"" + Common.getNumberFromText(sku.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	Logger.info("In Product Page:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Checkout\"\n" + "- If on mobile device: Click \"View My Cart\"");
	productPage.addSKUToCart(sku, false);
	productPage.checkOut();
    }
}*/