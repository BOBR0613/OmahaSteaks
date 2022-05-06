package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_002_CreateNewAccountNewAddressUseSameAsMyAddressOptionThenCompleteCheckout extends TestBase_2SC {
    @Inject
    Account account;
    @Inject
    CustomerAddress billingAddress;
    @Inject
    CategoryPage categoryPage;
    @Inject
    ConfirmationPage2SC confirmationPage;
    @Inject
    GeneralPage generalPage;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    MyAccountPage myAccountPage;
    @Inject
    ListSKUs myCart;
    @Inject
    Package mySku;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ProductPage productPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    CustomerAddress shippingAddress;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_2SCOP_002_Create_New_Account_New_Address_Use_Same_As_My_Address_Option_Then_Complete_Checkout() {
	initTestCaseData();

	addSKUToCartAndCheckOut(mySku);

	selecAddNewAddress();

	fillShippingAddress();

	selectEditShippingAddress();

	selectMyselfAddress();

	fillShippingAddress();

	selectSameAsMyAddressAndFillCreditNumberThenPlaceThisOrder();

	verifyBillingAddressDisplaysCorrectly();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void addSKUToCartAndCheckOut(SKU sku) {
	Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.checkOut();

	Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Click \"Add New Address\" link\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_002 - Create New Account New Address Use Same As My Address Option Then Complete Checkout");
	Logger.to("TO_2SCOP_05 - User can fill all information (mandatory and optional information) in Shipping Address Page (Select \"Add New Address\") and Billing Address then complete Checkout");
	Logger.to("TO_2SCOP_06 - User can fill information in Shipping Address Page and check \"Same As \"My Address\"?\" checkbox then complete Checkout");
	mySku.initRandom(Recipient.MYSELF);
	shippingAddress.initRandomInformation();
	billingAddress = shippingAddress.clone();

    }

    private void selecAddNewAddress() {
	Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Select \"Add New Address\"");
	shippingAddressPage2SC.selectAddNewAddressFromSendToDropDownList();
    }

    private void selectEditShippingAddress() {
	Logger.info("In Payment & Review page\n" + " - Click \"Edit this address\" link in My Cart section");
	paymentAndReviewPage2SC.selectEditShippingAddrLink(shippingAddress.firstName);
    }

    private void selectMyselfAddress() {
	Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Select \"Myself\" ");
	shippingAddressPage2SC.selectRecipientInSendToDropDownList(Recipient.MYSELF);
    }

    private void selectSameAsMyAddressAndFillCreditNumberThenPlaceThisOrder() {
	Logger.info("In Payment & Review page\n" + " - Check \"Same As \"My Address\"?\" checkbox\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
	paymentAndReviewPage2SC.selectSameAsMyAddress();
	paymentAndReviewPage2SC.fillEmailAndPhone(billingAddress);
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifyBillingAddressDisplaysCorrectly() {
	confirmationPage.closeModal();
	Logger.verify("In Order Receipt Page\n" + " - Verify that added Billing address displays correctly\n" + " - Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.\n" + " - Verify Contain text \"Order Number:\" + an order number format having 12 characters.");
	assertEquals(confirmationPage.getBillingAddress(), billingAddress.toBillingArray(), "The billing address is not displayed as expected");
	assertTrue(confirmationPage.isThankYouMessageDisplayed(), "Thank you message is not displayed");
	String oderNumber = confirmationPage.getOrderNumberText();
	assertEquals(oderNumber.split(":")[0].trim(), "Order Number", "Order Number text does not displayed");
	assertTrue(confirmationPage.isOrderNumberCorrectFormat(oderNumber.split(":", 2)[1].trim()), "Order Number is not in Correct Format");

    }

}
