package com.omahasteaks.tests.TreatYourself;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_TYP_003_UserCanAddTreatYourselfSKUInShoppingCartThenCompleteCheckout extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    Item treatYourselfItem, recipientItem;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    CustomerAddress billingAddress, myShippingAddress, recipientShippingAddress;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Test
    public void TC_TYP_003_User_Can_Add_Treat_Yourself_SKU_In_Shopping_Cart_Then_Complete_Checkout() {

	initTestCaseData();

	searchAndAddItem(recipientItem);

	verifyTreatYourselfModuleDisplays();

	addTreatYourselfSKUToCart();

	verifyTreatYourselfSkuIsAddedToMyselfCart();

	completeCheckOutProcess();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    public void verifyThankYouMessageDisplays() {
	Logger.verify("In Confirmation page:" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    public void completeCheckOutProcess() {
	checkoutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	fillBillingAddressAndPlaceOrder();
    }

    public void fillBillingAddressAndPlaceOrder() {
	Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    public void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address Page" + " - Fill mandatory information in Shipping Address" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(myShippingAddress);
	shippingAddressPage2SC.clickContinueButton();
	shippingAddressPage2SC.fillShippingAddress(recipientShippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    public void checkoutFromShoppingCart() {
	Logger.info("In Shopping Cart Page:" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    public void verifyTreatYourselfSkuIsAddedToMyselfCart() {
	Logger.verify("In Myself Cart section:" + " - Verify Treat Yourself Sku is added to Myself Cart section");
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, treatYourselfItem.getId()), "The SKU from Treat Yourself is not added as expected");
    }

    public void addTreatYourselfSKUToCart() {
	Logger.info("In Treat Yourself module:" + "- Click \"Add to cart\" button");
	treatYourselfItem.setId(shoppingCartPage.getSkuID_TreatYourself());
	treatYourselfItem.setRecipient(Recipient.MYSELF);
	shoppingCartPage.addTreatYourself();
	shoppingCartPage.selectExclusiveOffer(false);
	generalPage.checkOut();
    }

    public void verifyTreatYourselfModuleDisplays() {
	Logger.verify("In Shopping Cart page:" + "Verify Treat Yourself module displays");
	assertTrue(shoppingCartPage.isTreatYourselfModuleDisplayed(), "Treat Yourself Module does not display");
    }

    public void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, search a SKU with id (randomly)" + "In Product Page:" + " - Select: \"Ship To \"" + sku.getRecipient() + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();
    }

    public void initTestCaseData() {
	Logger.tc("TC_TYP_003 - User can add treat yourself SKU in Shopping Cart then complete checkout");
	Logger.to("TO_TYP_10 - User can add Treat Yourself SKU in Shopping Cart Page then complete checkout");
	recipientItem.initRandom(Recipient.THONG_NGUYEN);
	billingAddress = lstAddresses.getDefaultBillingAddress();
	myShippingAddress = lstAddresses.getDefaultShippingAddress();
	recipientShippingAddress = lstAddresses.getRandomShippingAddress();
    }

}
