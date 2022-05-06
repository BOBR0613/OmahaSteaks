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
import pages.BillingAddressPage;
import pages.GeneralPage;
import pages.ProductPage;
import pages.SearchResultPage;
import pages.ShoppingCartPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Messages;
import utils.helper.Logger;

public class TC_CON_001_BillingAddressWithAllMandatoryFieldHaveEmptyInformation extends TestBase {
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item item;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    BillingAddressPage billingAddressPage;

    @Test
    public void TC_CON_001_Billing_Address_With_All_Mandatory_Field_Have_Empty_Information() {
	initTestCaseData();
	searchAndAddItem(item);
	goToMyCart();
	checkOutFromShoppingCartPage();
	leaveBillingAddressEmptyAndContinueCheckout();
	verifyWarningMessageDisplaysCorrectly();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void goToMyCart() {
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_001_BillingAddressWithAllMandatoryFieldHaveEmptyInformation");
	Logger.info("TO01 Warning message with suitable information appears when leaving all mandatory fields empty");
	item.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
    }

    private void leaveBillingAddressEmptyAndContinueCheckout() {
	Logger.info("In Billing Address form: \n" + "- Leave every fields empty (if there are any fields having default value, then delete them excluding Country field) \n" + "- Click \"CONTINUE CHECKOUT\"");
	billingAddressPage.continueCheckout();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.continueShopping();
    }

    private void verifyWarningMessageDisplaysCorrectly() {
	Logger.verify("\"Verify a warning message with below information:\n" + "\"Please review the following:\n" + "(Click on the underlined value to position cursor to field with error)\n" + "First name: Please -- it's a required field\n" + "Last name: Ok -- we can't deliver without a last name - it's a required field.\n" + "Address1: Address for delivery -- it's a must! Please provide.\n" + "City: City, State and Zip code... all required fields.\n" + "State: City, State and Zip code... all required fields.\n" + "Zip Code: City, State and Zip code... all required fields.\n" + "Phone Required: Just in case we need to speak with you about your order or some fantastic offers, please provide the phone number you'd like us to use.\n" + "Email Address Required: The email address you supplied appears to be invalid. Could you please enter it again?\n" + "Acceptance Required: Please confirm that you agree to the Terms of Use and Privacy Policy by checking the box below.\"\"\n");
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getRequiredMessageByField(AddressFields.FIRST_NAME));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getRequiredMessageByField(AddressFields.LAST_NAME));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.ADDRESS1), Messages.getRequiredMessageByField(AddressFields.ADDRESS1));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.CITY), Messages.getRequiredMessageByField(AddressFields.CITY));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.STATE), Messages.getRequiredMessageByField(AddressFields.STATE));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.ZIP_CODE), Messages.getRequiredMessageByField(AddressFields.ZIP_CODE));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.PHONE), Messages.getRequiredMessageByField(AddressFields.PHONE));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.EMAIL), Messages.getRequiredMessageByField(AddressFields.EMAIL));
	assertEquals(billingAddressPage.getErrorMessageByField(AddressFields.ACCEPTANCE), Messages.getRequiredMessageByField(AddressFields.ACCEPTANCE));
    }
}
*/