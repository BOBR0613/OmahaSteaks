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
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_015_UserCanAddEGiftCardToMyCartThenCompleteCheckout extends TestBase_2SC {
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
    public void TC_SOP_015_User_Can_Add_E_Gift_Card_To_MyCart_Then_Complete_Checkout() {
	initTestCaseData();

	searchAndAddGiftCard(myEGiftCard);

	checkOutFromShoppingCartPage();

	verifyShippingOptionsDoesNotAppear();

	fillShippingAddressAndContinueCheckout();

	verifyAllShippingOptionsDoesNotAppear();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void searchAndAddGiftCard(SKU sku) {
	Logger.info("In Searchpage: \n" + "- Search a SKU (Gift cards)\n");
	generalPage.search("Gift Card");
	Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
	System.out.println("Adding sku");
	categoryPage.addEGiftCard(sku);
	Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
	generalPage.checkOut();
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

    private void fillShippingAddressAndContinueCheckout() {
	Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage2SC.fillShippingAddress(recipientAddress);
	shippingAddressPage2SC.fillEmailShippingAddress(recipientAddress.email);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_015 - User Can Add EGift Card To My Cart Then Complete Checkout");
	Logger.to("TO_SOP_13 - \"Shipping Options\" section does not appear in the Shipping Address page When My Cart contains E-Gift Card item.");
	Logger.to("TO_SOP_15 - \"View All Shipping Options\" link does not appear in My Cart section of the Payment & Review page when My Cart contains only E-Gift Card item.");
	myAddress = lstAddresses.getDefaultBillingAddress();
	recipientAddress = lstAddresses.getDefaultShippingAddress();
	myEGiftCard.setRecipient(Recipient.MYSELF);
	myEGiftCard.setName("Omaha Steaks Gift Card");
	myEGiftCard.setPrice(5);
	myEGiftCard.setQuantity(1);
    }

    private void verifyAllShippingOptionsDoesNotAppear() {
	Logger.verify("'View All Shipping Options' link does not appear in My Cart section of the Payment & Review page when My Cart contains only Wine items.");
	if (!paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(myEGiftCard.getRecipient().getValue()))
	    assertFalse(paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(myEGiftCard.getRecipient().getValue()), "'View All Shipping Options' link appears in the Shipping Address page When My Cart contains Wine item.");
	else {
	    paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(myEGiftCard.getRecipient().getValue());
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
