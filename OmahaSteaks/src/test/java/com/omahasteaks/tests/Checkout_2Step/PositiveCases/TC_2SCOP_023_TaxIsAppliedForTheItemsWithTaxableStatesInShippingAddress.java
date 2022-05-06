package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_023_TaxIsAppliedForTheItemsWithTaxableStatesInShippingAddress extends TestBase_2SC {

    CustomerAddress shippingAddress;

    @Inject
    Item myItem;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    ProductPage productPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Test
    public void TC_2SCOP_023_Tax_Is_Applied_For_The_Items_With_Taxable_States_In_Shipping_Address() {
	initTestCaseData();

	searchAndAddItem(myItem);

	checkOutFromShoppingCartPage();

	fillShippingAddressAndClickContinue();

	verifyThatTaxIsAppiedForTheItemsWithTaxableStatesInShippingAddress();
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void verifyThatTaxIsAppiedForTheItemsWithTaxableStatesInShippingAddress() {
	Logger.verify("In Payment And Review page:\r\n" + "Verify that tax amount in Order Total is not $0.00");
	assertNotEquals(paymentAndReviewPage2SC.getTaxAmountInOrderTotal(), ("$0.00"), "Tax amount in Order Total is $0.00");
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address page\r\n" + "- Fill valid information\r\n" + "- Click \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In Shopping Cart page \r\n" + "- Click \"CHECKOUT\"");
	shoppingCartPage.checkOut();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, Search a SKU:" + sku.getName() + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_023	- Tax is applied for the items with taxable states in shipping address");
	Logger.to("TO_2SCOP_54 - In Payment&Review page, tax amount in Order Total is not $0.00 with taxable states in Shipping Address");
	Random rd = new Random();
	List<String> lstState = Arrays.asList(Constants.TAXABLE_STATES_OF_SHIPPING_ADDRESS);
	shippingAddress = lstAddresses.getShippingAddressByState(lstState.get(rd.nextInt(lstState.size())));
	myItem.initRandom(Recipient.MYSELF);
    }

}
