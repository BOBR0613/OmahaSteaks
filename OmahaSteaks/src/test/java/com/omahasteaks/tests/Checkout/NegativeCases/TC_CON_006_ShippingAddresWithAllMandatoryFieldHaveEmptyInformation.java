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
import pages.ConfirmationPage;
import pages.GeneralPage;
import pages.PaymentAndReviewPage;
import pages.ProductPage;
import pages.SearchResultPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Messages;
import utils.helper.Logger;

public class TC_CON_006_ShippingAddresWithAllMandatoryFieldHaveEmptyInformation extends TestBase {
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
    @Inject
    ShippingAddressPage shippingAddressPage;
    @Inject
    PaymentAndReviewPage paymentAndReviewPage;
    @Inject
    ConfirmationPage confirmationPage;

    @Test
    public void TC_CON_006_Shipping_Addres_With_All_Mandatory_Field_Have_Empty_Information() {
	initTestCaseData();
	searchAndAddItem(item);
	goToMyCart();
	checkOutFromShoppingCartPage();
	fillBillingAddressAndContinueCheckout();
	leaveShippingAddressEmptyAndContinueCheckout();
	verifyWarningMessageDisplaysCorrectly();
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

    private void goToMyCart() {
	generalPage.goToMyCart();
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_006_ShippingAddresWithAllMandatoryFieldHaveEmptyInformation");
	Logger.info("TO_CON_09 Warning message with suitable information appears when leaving all mandatory fields empty in Shipping Address");
	item.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
    }

    private void leaveShippingAddressEmptyAndContinueCheckout() {
	Logger.info("In Shipping Address form: \n" + "- Leave every fields empty (if there are any fields having default value, then delete them excluding Country field)n" + "- Click \"CONTINUE CHECKOUT\"");
	shippingAddressPage.continueCheckout();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Hompage, enter \"" + Common.getNumberFromText(sku.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	Logger.info("In Product Page:\n" + " - Select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Checkout\"\n" + "- If on mobile device: Click \"View My Cart\"");
	productPage.addSKUToCart(sku, false);
	productPage.checkOut();
    }

    private void verifyWarningMessageDisplaysCorrectly() {
	Logger.verify("\"Verify a warning message with below information:\n" + "\"Please review the following:\n" + "(Click on the underlined value to position cursor to field with error)\n" + "First name: Please -- it's a required field\n" + "Last name: Ok -- we can't deliver without a last name - it's a required field.\n" + "Address1: Address for delivery -- it's a must! Please provide.\n" + "City: City, State and Zip code... all required fields.\n" + "State: City, State and Zip code... all required fields.\n" + "Zip Code: City, State and Zip code... all required fields.\n");
	assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.FIRST_NAME), Messages.getRequiredMessageByField(AddressFields.FIRST_NAME));
	assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.LAST_NAME), Messages.getRequiredMessageByField(AddressFields.LAST_NAME));
	assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.ADDRESS1), Messages.getRequiredMessageByField(AddressFields.ADDRESS1));
	assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.CITY), Messages.getRequiredMessageByField(AddressFields.CITY));
	assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.STATE), Messages.getRequiredMessageByField(AddressFields.STATE));
	assertEquals(shippingAddressPage.getErrorMessageByField(AddressFields.ZIP_CODE), Messages.getRequiredMessageByField(AddressFields.ZIP_CODE));
    }
}
*/