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

public class TC_CON_002_BillingAddressWithEachMandatoryFieldHasEmptyInformation extends TestBase {
    CustomerAddress billingAddress;
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
    public void TC_CON_002_Billing_Addres_With_Each_Mandatory_Field_Has_Empty_Information() {
	initTestCaseData();
	searchAndAddItem(package);
	checkOutFromShoppingCartPage();
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.FIRST_NAME);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.LAST_NAME);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ADDRESS1);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.CITY);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.STATE);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ZIP_CODE);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.PHONE);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.EMAIL);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.CONFIRM_EMAIL);
	leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ACCEPTANCE);
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.info("TC_CON_002_BillingAddressWithEachMandatoryFieldHasEmptyInformation");
	Logger.info("TO_CON_02	Warning message with corresponding information appears when leaving each mandatory field Billing address empty");
	package.initRandom(Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	billingAddress.zipCode = "CA" + billingAddress.zipCode;
    }

    private void leaveFieldEmptyAndVerifyErrorMessage(AddressFields field) {
	Logger.info("In Billing Address form: \n" + "- Leave fields " + field + " empty (if there are any fields having default value, then delete them excluding Country field) \n" + "- Click \"CONTINUE CHECKOUT\"");
	billingAddressPage.fillBillingAddressExceptField(billingAddress, field);
	billingAddressPage.continueCheckout();
	Logger.verify("Verify a warning message with below information:\n" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
	assertEquals(billingAddressPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field), field.getValue());
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