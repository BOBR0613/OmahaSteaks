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

public class TC_SOP_013_UserCanAddGiftCardToMyCartThenCompleteCheckout extends TestBase_2SC {
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;
    CustomerAddress myAddress, recipientAddress;
    @Inject
    Item myGiftCard;
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
    public void TC_SOP_013_User_Can_Add_Gift_Card_To_MyCart_Then_Complete_Checkout() {
	initTestCaseData();

	searchAndAddGiftCard(myGiftCard);

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
	Logger.info("In Searchpage: \r\n" + "- Search a SKU (Gift cards)\r\n");
	generalPage.search("Gift Card");
	Logger.info("In Gift Card page:\r\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\r\n" + "- Click \"add to Cart\" button");
	categoryPage.addGiftCard(sku);
	Logger.info("In \"Added To Cart\" popup: \r\n" + "- Click \"CHECKOUT\"");
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
	shippingAddressPage2SC.clickContinueButton();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_013 UserCanAddGiftCardToMyCartThenCompleteCheckout");
	Logger.to("TO_SOP_09 \"Shipping Options\" section does not appear in the Shipping Address page When My Cart contains Gift Card item.");
	Logger.to("TO_SOP_11 \"View All Shipping Options\" link does not appear in My Cart section of the Payment & Review page when My Cart contains only Gift Card item.");
	myAddress = lstAddresses.getDefaultBillingAddress();
	recipientAddress = lstAddresses.getDefaultShippingAddress();
	recipientAddress.setRandomEmail();
	myGiftCard.setRecipient(Recipient.MYSELF);
	myGiftCard.setName("Omaha Steaks Gift Card");
	myGiftCard.setPrice(5);
	myGiftCard.setQuantity(1);
    }

    private void verifyAllShippingOptionsDoesNotAppear() {
	Logger.verify("'View All Shipping Options' link does not appear in My Cart section of the Payment & Review page when My Cart contains only Wine items.");
	if (!paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(myGiftCard.getRecipient().getValue()))
	    assertFalse(paymentAndReviewPage2SC.isViewAllShippingOptionsDisplayed(myGiftCard.getRecipient().getValue()), "'View All Shipping Options' link appears in the Shipping Address page When My Cart contains Wine item.");
	else {
	    paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(myGiftCard.getRecipient().getValue());
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
