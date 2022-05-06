package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_018_UserCanSelectShippingMethodThenCompleteCheckoutWhenMyCartContainsWineItems extends TestBase_2SC {
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    String shippingMethod;

    List<String> lstShippingOptions;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;

    CustomerAddress myAddress;

    @Inject
    Item myWine;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_SOP_018_User_Can_Select_Shipping_Option_Then_Complete_Checkout_When_My_Cart_Contains_Wine_Items() {
	initTestCaseData();

	addWineIntoCartThenCheckout(myWine);

	checkOutFromShoppingCartPage();

	fillShippingAddressAndContinueCheckout();

	verifyTheStandardShippingIsDefaultSelected();

	verifyTheCustomerDeliveryDateAndExpeditedOptionsDoNotAppearInPaymentPage();

	clickViewAllShippingOptions();

	verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup();

	clickCancelButtonInShippingAndDeliveryPopup();

	selectRandomShippingOption();

	verifySelectedShippingMethodIsDisplayedCorrectly();

	clickViewAllShippingOptions();

	verifyTitleOfShippingAndDeliveryPopupIsDisplayedCorrectly();

	selectRandomShippingOptionInShippingAndDeliveryPopup();

	clickCancelButtonInShippingAndDeliveryPopup();

	verifySelectedShippingMethodIsDisplayedCorrectly();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyShippingMethodIsDisplayedCorrectlyInConfirmPage();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup() {
	Logger.verify("Verify The Standard Shipping is default  selected When My cart contains Wine  items In \"Shipping and Delivery\" popup");
	assertTrue(paymentAndReviewPage2SC.isShippingMethodInShippingAndDeliveryPopupSelected(Constants.STANDARD_SHIPPING), "The Standard Shipping is not default  selected When My cart contains Food items In \"Shipping and Delivery\" popup");
    }

    private void verifyTitleOfShippingAndDeliveryPopupIsDisplayedCorrectly() {
	Logger.verify("In Payment & Review page: The \"Shipping and Delivery\" popup appears after clicking \"View All Shipping Options\" link ");
	assertTrue(paymentAndReviewPage2SC.isTitleOfShippingAndDeliveryDisplayedCorrectly(), "Title of Shipping and Deliver is not displayed correctly");
    }

    private void selectRandomShippingOptionInShippingAndDeliveryPopup() {
	Logger.info("In Shipping and Delivery popup:Select Shipping method (randomly) in \"Shipping And Delivery\" modal");
	paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal(lstShippingOptions);
    }

    private void verifySelectedShippingMethodIsDisplayedCorrectly() {
	Logger.verify("Verify The selected shipping option is displayed correctly");
	assertTrue(paymentAndReviewPage2SC.getCheckedShippingMethod(Recipient.MYSELF.getValue(), shippingMethod), "The selected shipping method is not displayed correctly in Payment and Review page");
    }

    private void verifyShippingMethodIsDisplayedCorrectlyInConfirmPage() {
	Logger.verify("In Confirmation page: Verify  Shipping method is displayed correctly in My Cart Section When My Cart contains only Dropship items (Wine)");
	assertTrue(confirmationPage2SC.getShippingMethodSection(myAddress).contains(shippingMethod), "The Shipping method is not displayed correctly");
    }

    private void selectRandomShippingOption() {
	Logger.info("In Payment & Review page: Select Shipping method (randomly) in 'My Cart' section");
	shippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethod(Recipient.MYSELF.getValue());
	paymentAndReviewPage2SC.selectShippingMethod(Recipient.MYSELF.getValue(), shippingMethod);
    }

    private void clickCancelButtonInShippingAndDeliveryPopup() {
	Logger.info("In Payment & Review page:- Click on \"Cancel\" button in  'Shipping And Delivery' Popup");
	paymentAndReviewPage2SC.clickCancelButtonInModal();
    }

    private void clickViewAllShippingOptions() {
	lstShippingOptions = paymentAndReviewPage2SC.getShippingMethodsList(Recipient.MYSELF.getValue());
	Logger.info("In Payment & Review page: -Click 'View All Shipping Options\" link in My Cart Section");
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
    }

    private void verifyTheCustomerDeliveryDateAndExpeditedOptionsDoNotAppearInPaymentPage() {
	Logger.verify("In Payment And Review Page:Verify The custom delivery date, Express Delivery and Rush Delivery do not appear in 'My Cart' section When My Cart contains only Dropship items (Wine)");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.RUSH_SHIPPING), "Rush Delivery displays in Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.CUSTOM_SHIPPING), "Custom Delivery Date displays in Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.EXPRESS_SHIPPING), "Express Delivery displays in Payment and Review Page");
    }

    private void verifyTheStandardShippingIsDefaultSelected() {
	Logger.verify("In Payment And Review Page : Verify  The Standard Shipping is default  selected When My cart contains only only Dropship items (Wine)");
	assertEquals(paymentAndReviewPage2SC.getSelectedShippingMethod(Recipient.MYSELF.getValue()), Constants.STANDARD_SHIPPING, "The Standard Shipping is not default  selected");
    }

    private void addWineIntoCartThenCheckout(Item item) {
	Logger.info("From Homepage, enter \"" + Common.getNumberFromText(item.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("\"In Product Page: Add this item to " + item.getRecipient() + " - If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	generalPage.addFirstSKUToCart(item);
	productPage.selectExclusiveOffer(false);
	Logger.info("Click \"Checkout\"");
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
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_018 - User can select shipping  Then complete checkout When My cart contains only Dropship items (Wine)");
	Logger.to("TO_SOP_48 - In Payment & Review page: User can select random one of shipping options then complete check out  When My Cart contains only Dropship items (Wine)");
	Logger.to("TO_SOP_49 - In Payment & Review page:  The custom delivery date, Express Delivery and Rush Delivery do not appear in 'My Cart' section When My Cart contains only Dropship items (Wine)");
	Logger.to("TO_SOP_56 - In Payment & Review page:  The Standard Shipping is default  selected When My cart contains only Dropship items (Wine)");
	Logger.to("TO_SOP_70 - In Confirm Page : The Shipping method is displayed correctly in My cart section When  My Cart contains only Dropship items (Wine)");
	Logger.to("TO_SOP_76 - In Payment And Review Page :  The selected shipping option is displayed correctly after  clicking on \"CANCEL\" button  in \"Shipping and Delivery\" popup");
	Logger.to("TO_SOP_79 - In Payment & Review page: The \"Shipping and Delivery\" popup appears after clicking \"View All Shipping Options\" link");
	Logger.to("TO_SOP_79 - In Payment & Review page: The \"Shipping and Delivery\" popup appears after clicking \"View All Shipping Options\" link");
	Logger.to("TO_SOP_81 - In \"Shipping and Delivery\" popup :The Standard Shipping is default  selected When My Cart contains only Dropship items (Wine)");
	myAddress = lstAddresses.getDefaultShippingAddress();
	myWine.initRandom(Recipient.MYSELF, true);
    }
}
