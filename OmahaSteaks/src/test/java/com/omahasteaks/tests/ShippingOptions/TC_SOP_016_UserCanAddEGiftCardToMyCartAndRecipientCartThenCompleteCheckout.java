package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_016_UserCanAddEGiftCardToMyCartAndRecipientCartThenCompleteCheckout extends TestBase_2SC {
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress myAddress, recipientAddress;
    @Inject
    Item myEGiftCard;
    @Inject
    Item recipientEGiftCard;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    CategoryPage categoryPage;
    @Inject
    ProductPage productPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    SignInPage signInPage;

    @Test
    public void TC_SOP_016_User_Can_Add_E_Gift_Card_To_MyCart_And_RecipientCart_Then_Complete_Checkout() {
	initTestCaseData();

	searchAndAddGiftCard(myEGiftCard);

	searchAndAddGiftCard(recipientEGiftCard);

	goToMyCart();

	checkOutFromShoppingCartPage();

	verifyShippingOptionsDoesNotAppear();

	fillMyShippingAddressAndContinueCheckout();

	verifyShippingOptionsDoesNotAppear();

	fillRecipientShippingAddressAndContinueCheckout();

	verifyAllShippingOptionsDoesNotAppear(myEGiftCard);

	verifyAllShippingOptionsDoesNotAppear(recipientEGiftCard);

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void searchAndAddGiftCard(SKU sku) {
	Logger.info("In Searchpage: \r\n" + "- Search a SKU (Gift cards)\r\n");
	generalPage.search("Gift Card");
	Common.waitForPageLoad();
	Logger.info("In Gift Card page:\r\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\r\n" + "- Click \"add to Cart\" button");
	categoryPage.addEGiftCard(sku);
	Common.waitForPageLoad();
	Logger.info("In \"Added To Cart\" popup: \r\n" + "- Click \"Continue Shopping\"");
	generalPage.continueShopping();
	Common.waitForPageLoad();
    }

    private void goToMyCart() {
	Logger.info("Click on My Cart icon to Go to My Shopping Cart");
	generalPage.goToMyCart();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillRecipientShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage2SC.fillShippingAddress(recipientAddress);
	shippingAddressPage2SC.fillEmailShippingAddress(recipientAddress.email);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void fillMyShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.fillEmailShippingAddress(myAddress.email);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_016 - User Can Add EGift Card To My Cart And Recipient Cart Then Complete Checkout");
	Logger.to("TO_SOP_14 - \"Shipping Options\" section does not appear in the Shipping Address page When My Cart and Recipient Cart contain E-Gift Card item");
	Logger.to("TO_SOP_16 - \"View All Shipping Options\" link does not appear in Cart 1 of 2 section and Cart 2 of 2 section of the Payment & Review page when My Cart and Recipient Cart contain only E-Gift Card item");
	myAddress = lstAddresses.getDefaultBillingAddress();
	recipientAddress = lstAddresses.getDefaultShippingAddress();
	myEGiftCard.setRecipient(Recipient.MYSELF);
	myEGiftCard.setName("Omaha Steaks Gift Card");
	myEGiftCard.setPrice(5);
	myEGiftCard.setQuantity(1);
	recipientEGiftCard.setRecipient(Recipient.DD);
	recipientEGiftCard.setName("Omaha Steaks Gift Card");
	recipientEGiftCard.setPrice(5);
	recipientEGiftCard.setQuantity(1);
    }

    private void verifyAllShippingOptionsDoesNotAppear(SKU sku) {
	Logger.verify("'View All Shipping Options' link does not appear in " + sku.getRecipient().getValue() + " Cart section of the Payment & Review page when My Cart contains only Wine items.");
	if (!paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(sku.getRecipient().getValue()))
	    assertFalse(paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(sku.getRecipient().getValue()), "'View All Shipping Options' link appears in the Shipping Address page When My Cart contains Wine item.");
	else {
	    paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(sku.getRecipient().getValue());
	    assertTrue(paymentAndReviewPage2SC.isShippingOptionsDisplayedCorrectly(), "Shipping Options does not display correctly");
	    paymentAndReviewPage2SC.clickCancelButtonInModal();
	}
    }

    private void verifyShippingOptionsDoesNotAppear() {
	Logger.verify("'Shipping Options' section does not appear in the Shipping Address page When My Cart contains Wine item.");
	assertFalse(shippingAddressPage2SC.isShippingOptionsDisplayed(), "'Shipping Options' section appears in the Shipping Address page When My Cart contains Wine item.");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

}
