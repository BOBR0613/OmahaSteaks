package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
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
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_021_UserCanSelectShippingMethodWhenMyCartAndRecipientCartContainWineItems extends TestBase_2SC {
    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    String shippingMethod, recipientShippingMethod;

    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;

    CustomerAddress myAddress, recipientAddress;

    @Inject
    Item myWine, recipientWine;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_SOP_021_User_Can_Select_Shipping_Method_When_My_CartAnd_Recipient_Cart_Contain_Wine_Items() {
	initTestCaseData();

	searchAndAddItem(myWine);

	searchAndAddItem(recipientWine);

	goToShoppingCartPageThenClickCheckout();

	fillMyShippingAddressThenClickNextButton();

	fillRecipientShippingAddressThenClickContinueButton();

	verifyTheStandardShippingIsDefaultSelected();

	verifyTheCustomerDeliveryDateAndExpeditedOptionsDoNotAppearInPaymentPage();

	clickViewAllShippingOptions(myWine);

	verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup();

	clickCancelButtonInShippingAndDeliveryPopup();

	clickViewAllShippingOptions(recipientWine);

	verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup();

	clickCancelButtonInShippingAndDeliveryPopup();

	selectRandomMySelfShippingOption();

	verifySelectedShippingMethodIsDisplayedCorrectly(myWine);

	selectRandomRecipientShippingOption();

	verifySelectedShippingMethodIsDisplayedCorrectly(recipientWine);

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyShippingMethodIsDisplayedCorrectlyInConfirmPage();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyShippingMethodIsDisplayedCorrectlyInConfirmPage() {
	Logger.verify("In Confirmation page:Verify  Shipping method is displayed correctly in Cart 1 of 2  and cart 2 of 2  Section When My Cart contain only Dropship items (Wine) ");
	assertTrue(confirmationPage2SC.getShippingMethodSection(myAddress).contains(shippingMethod), "The Shipping method is not displayed correctly");
	assertTrue(confirmationPage2SC.getShippingMethodSection(recipientAddress).contains(recipientShippingMethod), "The Shipping method is not displayed correctly");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifySelectedShippingMethodIsDisplayedCorrectly(SKU sku) {
	Logger.verify("Verify The selected shipping option is displayed correctly in " + sku.getRecipient().getValue() + "section");
	if (sku.getRecipient().getValue().equals(Recipient.MYSELF.getValue()))
	    assertTrue(paymentAndReviewPage2SC.getCheckedShippingMethod(sku.getRecipient().getValue(), shippingMethod), "The selected shipping method is not displayed correctly in Payment and Review page");
	else
	    assertTrue(paymentAndReviewPage2SC.getCheckedShippingMethod(sku.getRecipient().getValue(), recipientShippingMethod), "The selected shipping method is not displayed correctly in Payment and Review page");
    }

    private void selectRandomRecipientShippingOption() {
	Logger.info("In Payment & Review page: Select Shipping method (randomly) in cart  2 of 2 section");
	recipientShippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethod(Recipient.DD.getValue());
	paymentAndReviewPage2SC.selectShippingMethod(Recipient.DD.getValue(), recipientShippingMethod);
    }

    private void selectRandomMySelfShippingOption() {
	Logger.info("In Payment & Review page: Select Shipping method (randomly) in cart 1 of  2  section");
	shippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethod(Recipient.MYSELF.getValue());
	paymentAndReviewPage2SC.selectShippingMethod(Recipient.MYSELF.getValue(), shippingMethod);
    }

    private void clickCancelButtonInShippingAndDeliveryPopup() {
	Logger.info("In Payment & Review page:- Click on \"Cancel\" button in  'Shipping And Delivery' Popup");
	paymentAndReviewPage2SC.clickCancelButtonInModal();
    }

    private void verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup() {
	Logger.verify("In \"Shipping and Delivery\" popup: Verify the Standard Shipping is default  selected  When  My cart and Recipient Cart contain only Dropship items (Wine) ");
	assertTrue(paymentAndReviewPage2SC.isShippingMethodInShippingAndDeliveryPopupSelected(Constants.STANDARD_SHIPPING), "The Standard Shipping is not default  selected When My cart and Recipient cart  contains Food items In \"Shipping and Delivery\" popup");
    }

    private void clickViewAllShippingOptions(SKU sku) {
	Logger.info("In Payment & Review page: -Click 'View All Shipping Options\" link");
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(sku.getRecipient().getValue());
    }

    private void verifyTheCustomerDeliveryDateAndExpeditedOptionsDoNotAppearInPaymentPage() {
	Logger.verify("In Payment & Review page:Verify  the custom delivery date, Express Delivery and Rush Delivery do not appear in Cart 1 of 2 section and Cart 2 of 2 section When My Cart and Recipient Cart contain only Dropship items (Wine) ");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.RUSH_SHIPPING), "Rush Delivery displays in Cart 1 of 2 sectionn of Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.CUSTOM_SHIPPING), "Custom Delivery Date displays in Cart 1 of 2 sectionn of Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.EXPRESS_SHIPPING), "Express Delivery displays in Cart 1 of 2 sectionn of Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.RUSH_SHIPPING), "Rush Delivery displays in Cart 2 of 2 sectionn of Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.CUSTOM_SHIPPING), "Custom Delivery Date displays in Cart 2 of 2 sectionn of Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.DD.getValue(), Constants.EXPRESS_SHIPPING), "Express Delivery displays in Cart 2 of 2 sectionn of Payment and Review Page");
    }

    private void verifyTheStandardShippingIsDefaultSelected() {
	Logger.verify("In Payment and review page:Verify the Standard Shipping is default  selected in Cart 1 of 2 section and Cart 2 of 2 section  When My cart and Recipient Cart contain only Dropship items (Wine) ");
	assertEquals(paymentAndReviewPage2SC.getSelectedShippingMethod(Recipient.MYSELF.getValue()), Constants.STANDARD_SHIPPING, "The Standard Shipping in Cart 1 of 2 section is not default  selected");
	assertEquals(paymentAndReviewPage2SC.getSelectedShippingMethod(Recipient.DD.getValue()), Constants.STANDARD_SHIPPING, "The Standard Shipping in Cart 2 of 2 section is not default  selected");
    }

    private void fillRecipientShippingAddressThenClickContinueButton() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(recipientAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void fillMyShippingAddressThenClickNextButton() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Next\"");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickNextButton();
    }

    private void goToShoppingCartPageThenClickCheckout() {
	Logger.info("Go to Shopping Cart Page then click checkout");
	generalPage.goToMyCart();
	shoppingCartPage.checkOut();
    }

    private void searchAndAddItem(Item item) {
	Logger.info("From Homepage, enter \"" + Common.getNumberFromText(item.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("\"In Product Page: Add this item to " + item.getRecipient() + " - If Exclusive Offer (Upsell offer) appears, click \"Add To Cart\"");
	generalPage.addFirstSKUToCart(item);
	productPage.selectExclusiveOffer(false);
	Logger.info("- Click \"Continue shopping\" button");
	generalPage.continueShopping();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_021_User can select shipping method when my cart and recipient cart contain wine items");
	Logger.to("TO_SOP_51 - In Payment & Review page:  The custom delivery date, Express Delivery and Rush Delivery do not appear in Cart 1 of 2 section and Cart 2 of 2 section When My Cart and Recipient Cart contain only Dropship items (Wine)");
	Logger.to("TO_SOP_54 - In Payment & Review page: User can select random 1 shipping option in Cart 1 of 2 section and Cart 2 of 2 section then complete check out  When My Cart and Recipient Cart contain only Dropship items (Wine)");
	Logger.to("TO_SOP_58 - In Payment & Review page:  The Standard Shipping is default  selected in Cart 1 of 2 section and Cart 2 of 2 section  When My cart and Recipient Cart contain only Dropship items (Wine)");
	Logger.to("TO_SOP_72 - In Confirm Page : The Shipping method is displayed correctly in Cart 1 of 2 section and Cart 2 of 2 section When My Cart and Recipient Cart contain only Dropship items (Wine)");
	Logger.to("TO_SOP_83 - In \"Shipping and Delivery\" popup :  The Standard Shipping is default  selected  When  My cart and Recipient Cart contain only Dropship items (Wine)");
	myAddress = lstAddresses.getDefaultBillingAddress();
	recipientAddress = lstAddresses.getDefaultShippingAddress();
	myWine.initRandom(Recipient.MYSELF, true);
	recipientWine.initRandom(Recipient.DD, true);
    }
}
