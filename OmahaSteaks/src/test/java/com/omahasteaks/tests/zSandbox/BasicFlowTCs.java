package com.omahasteaks.tests.zSandbox;
/*package tests.Sandbox;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;

import data.ENUM.Recipient;
import data.objects.CustomerAddress;
import data.objects.Item;
import pages.BillingAddressPage;
import pages.ConfirmationPage;
import pages.GeneralPage;
import pages.PaymentAndReviewPage;
import pages.ProductPage;
import pages.SearchResultPage;
import pages.ShippingAddressPage;
import pages.ShoppingCartPage;
import pages.SignInPage;
import tests.TestBase;
import utils.helper.Logger;

public class BasicFlowTCs extends TestBase {
    @Inject
    Item dishMyself;
    @Inject
    Item dish1stRecipient;
    @Inject
    Item dish2ndRecipient;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CustomerAddress shippingAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    SignInPage signInPage;
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
    public void Test() {
	// dish.initDish("Filet Mignons", "6 oz.", 8, "Myself");
	WebDriver wd = DriverUtils.getWebDriver();
	// String Mode = "ENHANCED";
	String Mode = "MOBI_ENH";
	wd.navigate().to("https://www.omastk.com/shop/?setviewmode=" + Mode);
	// wd.navigate().to("https://www.omastk.com/product/Filet-Mignons-00000004702?ITMSUF=WZA&shoptype=PROMOTION");
	// productPage.addDishToCart(dish, false, true);
	// productPage.continueShopping();
	// generalPage.goToMyCart();
	//// shoppingCartPage.addSpecialCartBonus("Myself");
	//// String skuid = shoppingCartPage.getAddedSkuIdFromModal();
	//// generalPage.goToMyCart();
	//// shoppingCartPage.removeSkuById(skuid);
	//
	// shoppingCartPage.checkOut();
	Logger.info("------------------------TC04------------------------");
	dishMyself.setRecipient(Recipient.MYSELF);
	dish1stRecipient.setRecipient(Recipient.THONG_NGUYEN);
	dish2ndRecipient.setRecipient(Recipient.KIM_ANH);
	// billingAddress.initInformation("Mary", "Kass-Muckey", "Logigear", "8102 N
	// 124TH ST",
	// "TEST ORDER - PLEASE DELETE", "", "68142-1", "OMAHA", "Nebraska",
	// "4029607722", "United States",
	// "marym@omahasteaks.com", "March", 25);
	// shippingAddress.initInformation("Thong", "Nguyen", "Logigear", "8102 N 124TH
	// ST", "TEST ORDER - PLEASE DELETE",
	// "", "68142-1", "OMAHA", "Nebraska", "4029607722", "United States",
	// "marym@omahasteaks.com", "June", 22);
	generalPage.search("Steaks");
	searchResultPage.selectFirstItem();
	productPage.addItemToCart(dishMyself, false, true);
	productPage.continueShopping();
	generalPage.search("Yellowfin");
	searchResultPage.selectFirstItem();
	productPage.addItemToCart(dish1stRecipient, false, true);
	productPage.continueShopping();
	generalPage.search(Constants.SKU006NAME);
	searchResultPage.selectFirstItem();
	productPage.addItemToCart(dish2ndRecipient, false, true);
	productPage.continueShopping();
	generalPage.goToMyCart();
	shoppingCartPage.checkOut();
	billingAddressPage.fillBillingAddress(billingAddress);
	billingAddressPage.continueCheckout();
	shippingAddressPage.fillShippingAddress(billingAddress);
	shippingAddressPage.continueCheckout();
	Logger.info("------------------------DONE------------------------");
    }
}
*/