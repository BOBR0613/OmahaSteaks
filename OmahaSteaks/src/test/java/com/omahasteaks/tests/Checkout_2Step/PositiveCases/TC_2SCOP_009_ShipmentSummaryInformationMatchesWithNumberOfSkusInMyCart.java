package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
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

public class TC_2SCOP_009_ShipmentSummaryInformationMatchesWithNumberOfSkusInMyCart extends TestBase_2SC {
    @Inject
    SKU inCartSpecialSku;
    @Inject
    ListSKUs myCart;
    @Inject
    Item mySku;
    @Inject
    CustomerAddress newAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Test
    public void TC_2SCOP_009_Shipment_Summary_Information_Matches_With_Number_Of_Skus_In_My_Cart() {
	initTestCaseData();

	addFirstSKUToCart(mySku, myCart);

	addInCartSpecialSKU();

	verifySKUsAreAddedToMyCartProperly();

	fillShippingAddress();

	fillBillingAddress();

	verifySKUsDisplayProperlyInPaymentAndReviewPage();

	fillCreditCardNumberThenPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyOrderNumberIsCorrectFormat();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyOrderNumberIsCorrectFormat() {
	String getOrderNumberText = confirmationPage2SC.getOrderNumberText();
	Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
	assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
	assertTrue(confirmationPage2SC.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
    }

    private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
	Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.getAddedToCartSKUList(lSku);
	productPage.checkOut();
    }

    private void addInCartSpecialSKU() {
	Logger.info("\"From Homepage, go to any Category Page\n" + " - For the first SKU,select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"\n");
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE)) {
	    inCartSpecialSku.setName(shoppingCartPage.getSkuNameInSpecialCartBonus(Recipient.MYSELF));
	}
	shoppingCartPage.addInCartSpecial(Recipient.MYSELF);
	inCartSpecialSku.setRecipient(Recipient.MYSELF);

	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
	    inCartSpecialSku.setId(shoppingCartPage.getAddedSkuIdFromModal());
	    inCartSpecialSku.setName(shoppingCartPage.getAddedSkuNameFromModal());
	    myCart.add(inCartSpecialSku);
	    shoppingCartPage.closeAddedToCartModal();
	}
	generalPage.waitForLoadingIconInvisible(Constants.SHORT_TIME);
	shoppingCartPage.checkOut();

    }

    private void continueToPaymentAndReviewPage() {
	shippingAddressPage2SC.clickContinueButton();
    }

    private void fillBillingAddress() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
	paymentAndReviewPage2SC.fillBillingAddress(newAddress);
    }

    private void fillCreditCardNumberThenPlaceMyOrder() {
	Logger.info("In Payment & Review page\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrderIgnoreError();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Click \"Add New Address\" link\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(newAddress);
	continueToPaymentAndReviewPage();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_009 - Shipment Summary Information Matches With Number Of Skus In My Cart");
	Logger.to("TO_2SCOP_33-1 - Information in \"Shipment Summary\" match with number of SKUs of Myself in My Cart");
	Logger.to("TO_2SCOP_33-2 - In Payment & Review page, verify IN-CART-SPECIAL item appears in the My Cart section.");
 	mySku.init(SkuType.PACKAGES, Recipient.MYSELF);
	myCart.initEmpty();
	newAddress.initRandomInformation();
    }

    private void verifySKUsAreAddedToMyCartProperly() {
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
	    for (SKU sku : myCart.getList()) {
		   System.out.println("Verify " + sku.getName() + " is added to My Cart properly");
			Logger.verify("Verify " + sku.getName() + " is added to My Cart properly");
		assertEquals(shippingAddressPage2SC.isSKUExisted(sku), true);
	    }
    }

    private void verifySKUsDisplayProperlyInPaymentAndReviewPage() {
	for (SKU sku : myCart.getList()) {
	    System.out.println("Verify " + sku.getName() + " displays in Payment & Review Page properly");
	    Logger.verify("Verify " + sku.getName() + " displays in Payment & Review Page properly");
	    assertEquals(paymentAndReviewPage2SC.isSKUExisted(sku), true);
	}
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertEquals(confirmationPage2SC.isThankYouMessageDisplayed(), true);
    }
}
