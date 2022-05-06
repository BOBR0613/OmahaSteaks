package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertTrue;

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
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_018_PayPalButtonAndTabWorksInMyCartAndPaymentAndReviewPage extends TestBase_2SC {
    CustomerAddress shippingAddress;

    @Inject
    Item myItem;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    SearchResultPage searchResultPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ProductPage productPage;

    @Test
    public void TC_2SCOP_018_PayPal_Button_And_Tab_Works_In_My_Cart_And_Payment_And_Review_Page() {

	initTestCaseData();

	searchAndAddItem(myItem);

	checkOutWithPayPalFromShoppingCartPage();

	verifyPayPalLoginPageIsDisplayed();

	goBack();

	verifyTheExpectedPageIsDisplayed(Constants.getRunningURL() + Constants.URL_SHOPPING_CART, Constants.CHECKOUT_SHOPPING_CART_PAGE_TITLE);

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	checkOutWithPayPalFromPaymentAndReviewPage();

	verifyPayPalLoginPageIsDisplayed();

	goBack();

	verifyTheExpectedPageIsDisplayed(Constants.getRunningURL() + Constants.URL_CHECKOUT_DOMAIN, Constants.CHECKOUT_PAYMENT_PAGE_TITLE);
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void checkOutWithPayPalFromPaymentAndReviewPage() {
	Logger.info("In Payment & Review Page:\r\n" + "- Click PayPal tab\r\n" + "- Click 'Check out with PayPal'");
	paymentAndReviewPage2SC.clickPayPalTab();
	paymentAndReviewPage2SC.clickCheckOutWithPayPal();
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address Page:\r\n" + "- Fill valid information\r\n" + "- Click  \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkOutFromShoppingCart() {
	Logger.info("In Shopping Cart page:\r\n" + "- Click \"CHECKOUT\"");
	shoppingCartPage.checkOut();
    }

    private void verifyTheExpectedPageIsDisplayed(String urlPage, String pageTitle) {
	Logger.verify("Verify the " + pageTitle + " page is displayed");
	assertTrue(Common.getCurrentUrl().startsWith(urlPage) && Common.getTitlePage().trim().equals(pageTitle), " - The " + pageTitle + " page is not displayed");
    }

    private void goBack() {
	Logger.info("Click the Back Arrow next to URL in browser");
	Common.goBack();
    }

    private void verifyPayPalLoginPageIsDisplayed() {
	Logger.verify("Verify PayPal page is displayed");
	assertTrue(Common.getCurrentUrl().startsWith(Constants.URL_PAYPAL_PAGE), " - PayPal page is not displayed");
    }

    private void checkOutWithPayPalFromShoppingCartPage() {
	Logger.info("In My Cart page:\r\n" + "- Click 'Check out with PayPal' button");
	shoppingCartPage.clickCheckOutWithPayPal();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, \r\n" + "- Search a SKU (Food)\r\n" + sku.getName() + "In Search page\n" + "- Select the first Item and add it to Cart" + "If Exclusive Offer (Upsell offer) modal:\r\n" + "- Click \"No Thanks\"" + "In \"Added To Cart\" popup: \r\n" + "- Click \"CHECKOUT\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_018 - PayPal button and tab works in My Cart and Payment & Review page");
	Logger.to("TO_2SCOP_047 - In Shopping Cart page - Verify PayPal button works");
	Logger.to("TO_2SCOP_048	- In Payment & Review page - Verify PayPal tab works");
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF, false);
    }
}
