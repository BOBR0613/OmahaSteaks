package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Color;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_009_FontColorForFreeAndNotFreeShippingCharges extends TestBase_2SC {

    CustomerAddress billingAddress, shippingAddressFreeShipping, shippingAddressWithFee;
    @Inject
    Package packageMyself;
    @Inject
    ConfirmationPage2SC confirmationPage;
    @Inject
    GeneralPage generalPage;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_SOP_009_Font_Color_For_Free_And_Not_Free_Shipping_Charges() {
	initTestCaseData();
	addSKUToCartAndCheckOut();
	fillShippingAddress();
	Logger.verify("Verify Font color for shipping charges is Red when the shipping charges displayed is the word FREE");
	verifyFontColorForShippingCharges(Color.RED.asHex());
	editShippingAddress();
	Logger.verify("Verify Font color for shipping charges is Dark Gray  when the shipping charges displayed is a dollar amount");
	verifyFontColorForShippingCharges(Color.DARK_GRAY.asHex());
	fillCreditCardNumberAndPlaceMyOrder();
	verifyThankYouMessageDisplays();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void addSKUToCartAndCheckOut() {
	Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
	generalPage.search(Common.getNumberFromText(packageMyself.getId()));
	productPage.addSKUToCart(packageMyself, false);
	generalPage.checkOut();

	Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    private void editShippingAddress() {
	Logger.info("\"In New Payment and Review Page:\n" + " - Click \"Click the 'Edit this address' link in My Cart section to update Shipping Address.");
	paymentAndReviewPage2SC.selectEditShippingAddrLink(Recipient.MYSELF.getValue());
	Logger.info("In  Shipping Address Page:\n" + "- Update information based on existing data.\n" + "- Click Click 'UPDATE CONTACT' button.\n");
	shippingAddressPage2SC.clickEditThisAddressLink();
	shippingAddressPage2SC.fillShippingAddressInModal(shippingAddressWithFee);
	shippingAddressPage2SC.updateContactInModal();
	shippingAddressPage2SC.clickContinueButton();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("In New Payment and Review Page:\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "- Fill valid mandatory information at Billing Address section\n" + "- Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddressFreeShipping);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_009_FontColorForFreeAndNotFreeShippingCharges");
	Logger.to("TO_SOP_41 Font color for shipping charges is Dark Gray  when the shipping charges displayed is a dollar amount");
	Logger.to("TO_SOP_42 Font color for shipping charges is Red when the shipping charges displayed is the word FREE");
	packageMyself.init(SkuType.FREESHIP, Recipient.MYSELF);
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddressFreeShipping = lstAddresses.getDefaultShippingAddress();
	shippingAddressWithFee = lstAddresses.getShippingAddressByState("Puerto Rico");
    }

    private void verifyFontColorForShippingCharges(String fontColor) {
	assertEquals(paymentAndReviewPage2SC.getColorOfShippingCostInOrderTotalsSection(), fontColor, "The color displays incorrect!");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }
}
