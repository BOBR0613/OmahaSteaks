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
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_SOP_017_UserCanSelectShippingMethodThenCompleteCheckout extends TestBase_2SC {
    CustomerAddress myAddress;

    String shippingMethod;

    List<String> lstShippingOptions;

    @Inject
    Item myItem;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ProductPage productPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    ConfirmationPage2SC confirmationPage2SC;

    @Test
    public void TC_SOP_017_User_Can_Select_Shipping_Option_Then_Complete_Checkout() {
	initTestCaseData();

	searchAndAddItem(myItem);

	goToShoppingCartPageThenClickCheckout();

	fillMyShippingAddressThenClickContinueButton();

	verifyTheStandardShippingIsDefaultSelected();

	verifyTheCustomerDeliveryDateAndExpeditedOptionsDoNotAppearInPaymentPage();

	verifyDetailsPopupIsDisplayedCorrectly();

	clickViewAllShippingOptions();

	verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup();

	closeShippingAndDeliveryPopup();

	selectRandomShippingOption();

	verifySelectedShippingMethodIsDisplayedCorrectly();

	clickViewAllShippingOptions();

	selectRandomShippingOptionInShippingAndDeliveryPopup();

	closeShippingAndDeliveryPopup();

	verifySelectedShippingMethodIsDisplayedCorrectly();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyShippingMethodIsDisplayedCorrectlyInConfirmPage();

	verifyThankYouMessageDisplays();

    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyTheStandardShippingIsDefaultSelectedInShippingAndDeliveryPopup() {
	Logger.verify("Verify The Standard Shipping is default  selected When My cart contains Food items In \"Shipping and Delivery\" popup");
	assertTrue(paymentAndReviewPage2SC.isShippingMethodInShippingAndDeliveryPopupSelected(Constants.STANDARD_SHIPPING), "The Standard Shipping is not default  selected When My cart contains Food items In \"Shipping and Delivery\" popup");
    }

    private void selectRandomShippingOptionInShippingAndDeliveryPopup() {
	Logger.info("In Shipping and Delivery popup:Select Shipping method (randomly) in \"Shipping And Delivery\" modal");
	paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal(lstShippingOptions);
    }

    private void verifySelectedShippingMethodIsDisplayedCorrectly() {
	Logger.verify("Verify The selected shipping option is displayed correctly");
	assertTrue(paymentAndReviewPage2SC.getCheckedShippingMethod(Recipient.MYSELF.getValue(), shippingMethod), "The selected shipping method is not displayed correctly in Payment and Review page");
    }

    private void verifyDetailsPopupIsDisplayedCorrectly() {
	Logger.verify("In Payment And Review Page : - Verify Popup  is displayed when clicking on \"detail\" link -  The Incentive Offer terms and conditions are displayed correctly after clicking on\"detail\"link");
	Logger.info("In Payment & Review page: - Click on \"Details \" link in My Cart section");
	paymentAndReviewPage2SC.clickDetailsLink(Recipient.MYSELF.getValue());
	assertTrue(paymentAndReviewPage2SC.isDetailsPopupDisplayed(), "Details popup is not displayed as expected");
	assertEquals(paymentAndReviewPage2SC.getIncentiveOfferTermsAndConditionsMessage(), Messages.INCENTIVE_OFFER_TERMS_AND_CONDITIONS_MSG, "The Incentive Offer terms and conditions are not displayed correctly");
    }

    private void closeShippingAndDeliveryPopup() {
	Logger.info("In Payment & Review page: Close 'Shipping And Delivery' Popup");
	paymentAndReviewPage2SC.closeModal();
    }

    private void clickViewAllShippingOptions() {
	lstShippingOptions = paymentAndReviewPage2SC.getShippingMethodsList(Recipient.MYSELF.getValue());
	Logger.info("In Payment & Review page:Click 'View All Shipping Options\" link in My Cart Section");
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
    }

    private void verifyShippingMethodIsDisplayedCorrectlyInConfirmPage() {
	Logger.verify("In Confirmation page: Verify  Shipping method is displayed correctly in My Cart Section When My Cart contains Food  items");
	assertTrue(confirmationPage2SC.getShippingMethodSection(myAddress).contains(shippingMethod), "The Shipping method is not displayed correctly");
    }

    private void selectRandomShippingOption() {
	Logger.info("In Payment & Review page: Select Shipping method (randomly) in 'My Cart' section");
	shippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethod(Recipient.MYSELF.getValue());
	paymentAndReviewPage2SC.selectShippingMethod(Recipient.MYSELF.getValue(), shippingMethod);
    }

    private void verifyTheCustomerDeliveryDateAndExpeditedOptionsDoNotAppearInPaymentPage() {
	Logger.verify("In Payment And Review Page : Verify  The custom delivery date, Express Delivery and Rush Delivery do not appear in  'My Cart' section When My Cart contains only Food items");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.RUSH_SHIPPING), "Rush Delivery displays in Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.CUSTOM_SHIPPING), "Custom Delivery Date displays in Payment and Review Page");
	assertFalse(paymentAndReviewPage2SC.isShippingMethodDisplayed(Recipient.MYSELF.getValue(), Constants.EXPRESS_SHIPPING), "Express Delivery displays in Payment and Review Page");
    }

    private void verifyTheStandardShippingIsDefaultSelected() {
	Logger.verify("In Payment And Review Page : Verify  The Standard Shipping is default  selected When My cart contains Food items");
	assertEquals(paymentAndReviewPage2SC.getSelectedShippingMethod(Recipient.MYSELF.getValue()), Constants.STANDARD_SHIPPING, "The Standard Shipping is not default  selected");
    }

    private void fillMyShippingAddressThenClickContinueButton() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(myAddress);
	shippingAddressPage2SC.clickContinueButton();
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
	generalPage.continueShopping();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_017 - User can select shipping method then complete checkout");
	Logger.to("TO_SOP_47 - In Payment & Review page: User can select  random one of shipping option then complete check out  When My Cart contains Food items");
	Logger.to("TO_SOP_50 - In Payment & Review page: The custom delivery date, Express Delivery and Rush Delivery do not appear in  'My Cart' section When My Cart contains only Food items");
	Logger.to("TO_SOP_55 - In Payment & Review page:  The Standard Shipping is default  selected When My cart contains Food items");
	Logger.to("TO_SOP_69 - In Confirmation Page : The Shipping method is displayed correctly in My cart section When  My Cart contains Food items");
	Logger.to("TO_SOP_73 - In Payment And Review Page : Popup  is displayed when clicking on \"detail\" link");
	Logger.to("TO_SOP_74 - In Payment And Review Page : The Incentive Offer terms and conditions are displayed correctly  after clicking on \"detail\" link");
	Logger.to("TO_SOP_75 - In Payment And Review Page :  The selected shipping option is displayed correctly after  closing \"Shipping and Delivery\" popup");
	Logger.to("TO_SOP_80 - In \"Shipping and Delivery\" popup :  The Standard Shipping is default  selected When My cart contains Food items");
	myAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF);
    }
}
