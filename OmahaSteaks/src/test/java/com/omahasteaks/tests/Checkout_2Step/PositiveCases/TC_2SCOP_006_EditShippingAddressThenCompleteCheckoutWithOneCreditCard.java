package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
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
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_006_EditShippingAddressThenCompleteCheckoutWithOneCreditCard extends TestBase_2SC {
    @Inject
    CategoryPage categoryPage;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;
    String deliveryDate;
    @Inject
    GeneralPage generalPage;
    @Inject
    CustomerAddress invalidShippingAddress;
    @Inject
    MyAccountPage myAccountPage;
    @Inject
    ListSKUs myCart;
    @Inject
    Package mySku;
    @Inject
    CustomerAddress newShippingAddress;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ProductPage productPage;
    @Inject
    SearchResultPage searchResultPage;
    String selectedRecipient;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    SignInPage signInPage;
    @Inject
    CustomerAddress updatedShippingAddress;

    @Test
    public void TC_2SCOP_006_Edit_Shipping_Address_Then_Complete_Checkout_With_One_Credit_Card() {
	initTestCaseData();

	signInWithExistingAccount();

	addSKUToCartAndCheckOut(mySku);

	editRecipientAddressByClickingEditLinkInEmailSection();

	verifyRecipientsAddressHasBeenUpdated();

	fillCreditCardNumberThenPlaceMyOrder();

	verifyThankYouMessageDisplays();

	myAccountPage.cleanUpAddressBook();
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

    private void editRecipientAddressByClickingEditLinkInEmailSection() {
	Logger.info("In Shipping Address Page\n" + " - Select an existing Recipient\n" + " - Clicking 'Edit' link beside Email checkbox\n" + "(if this Address does not have email -> click 'add an email address')\n" + " - Edit this address to new address");
	shippingAddressPage2SC.selectNextRecipientAddressBySelected();
	shippingAddressPage2SC.clickEditThisAddressLink();
	updatedShippingAddress.initRandomInformation();
	updatedShippingAddress.firstName = Common.getRandomString("CO_006");
	updatedShippingAddress.lastName = Common.getRandomString("CO_006");
	shippingAddressPage2SC.fillShippingAddressInModal(updatedShippingAddress);
	shippingAddressPage2SC.updateContactInModal();
	// selectedRecipient =
	// shippingAddressPage2SC.getSelectedRecipientNameInSendToDropDownList();
    }

    private void fillCreditCardNumberThenPlaceMyOrder() {
	Logger.info("In Payment & Review page\n" + "- Fill ' 4111111111111111' number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click 'Place Order'");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_006	User can edit shipping address, select shipping method and complete checkout with one credit card");
	Logger.to("TO_2SCOP_11	In Shipping Address, after selecting an existing address, user can edit this selected address by clicking \"Edit\" link beside Email checkbox\n");
	Logger.to("TO_2SCOP_15	In Shipping Address, user can select Shipping Method \"Custome Delivery Date\" then complete checkout\n");
	Logger.to("TO_2SCOP_23	In Payment & Review, user can complete checkout with one credit card\n");
	mySku.initRandom(Recipient.MYSELF);
	account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
	invalidShippingAddress.initRandomInformation();
	invalidShippingAddress.zipCode = "70000";
    }

    private void signInWithExistingAccount() {
	Logger.info("Sign in with existing account");
	generalPage.goToSignInPage();
	signInPage.signIn(account);
    }

    private void verifyRecipientsAddressHasBeenUpdated() {
	Logger.verify("In Shipping Address Page\n" + " - Verify the new address is updated");
	assertEquals(shippingAddressPage2SC.getRecipientAddressText(), shippingAddressPage2SC.generateRecipientAddressInfo(updatedShippingAddress));
	shippingAddressPage2SC.clickContinueButton();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.info("In Order Receipt Page\n" + " - Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.\n" + " - Verify Contain text \"Order Number:\" + an order number format having 12 characters.");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank you message is not displayed");
	String oderNumber = confirmationPage2SC.getOrderNumberText();
	assertEquals(oderNumber.split(":")[0].trim(), "Order Number", "Order Number text does not displayed");
	assertTrue(confirmationPage2SC.isOrderNumberCorrectFormat(oderNumber.split(":", 2)[1].trim()), "Order Number is not in Correct Format");
    }
}
