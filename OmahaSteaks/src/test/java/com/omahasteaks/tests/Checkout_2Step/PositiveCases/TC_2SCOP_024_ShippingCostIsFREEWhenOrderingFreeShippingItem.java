package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_024_ShippingCostIsFREEWhenOrderingFreeShippingItem extends TestBase_2SC {

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
    public void TC_2SCOP_024_Shipping_Cost_Is_FREE_When_Ordering_Free_Shipping_Item() {
	initTestCaseData();

	addFirstSKUToCart();

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	verifyShippingCostInOrderTotalIsFREE();
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void verifyShippingCostInOrderTotalIsFREE() {
	Logger.verify("In Payment And Review page:\r\n" + "Verify that shipping cost in Order Total is FREE");
	assertEquals(paymentAndReviewPage2SC.getTotalShippingFee(), Constants.SHIPPING_COST_FREE, "Shipping cost in Order Total is not FREE");
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address page\r\n" + "- Fill valid information\r\n" + "- Click \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkOutFromShoppingCart() {
	Logger.info("In Shopping Cart page \r\n" + "- Click \"CHECKOUT\"");
	shoppingCartPage.checkOut();
    }

    private void addFirstSKUToCart() {
	Logger.info("From Homepage\r\n" + " - At the first SKU,select \"Ship To Myself\"\r\n" + " - Click \"ADD TO CART\" button");
	generalPage.search("FREE SHIPPING");
	generalPage.addFirstSKUToCart(myItem);
	generalPage.selectExclusiveOffer(false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_024 - Shipping cost is FREE when ordering free shipping item");
	Logger.to("TO_2SCOP_55 - In Payment&Review page, shipping cost in Order Total is FREE when ordering the free shipping item");
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myItem.setRecipient(Recipient.MYSELF);
    }

}
