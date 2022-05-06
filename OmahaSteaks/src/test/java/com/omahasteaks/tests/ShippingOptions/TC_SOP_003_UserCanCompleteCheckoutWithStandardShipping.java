package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertTrue;

import java.util.Calendar;

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

public class TC_SOP_003_UserCanCompleteCheckoutWithStandardShipping extends TestBase_2SC {
    CustomerAddress myAddress;
    String myShippingMethod, myDeliveryDate, myShippingCost;

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
    public void TC_SOP_003_User_Can_Complete_Checkout_With_Standard_Shipping() {
	initTestCaseData();

	searchAndAddItem(myItem);

	goToShoppingCartPageThenClickCheckout();

	fillMyShippingAddressThenClickContinueButton();

	selectMyShippingOptionFromShippingAndDeliveryModal();

	fillBillingAddress();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyShippingMethodSectionDisplaysCorrectly();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyShippingMethodSectionDisplaysCorrectly() {
	confirmationPage2SC.closeModal();
	Logger.verify("In Confirmation page: Verify Shipping Method, Cost, and Estimated Delivery date appear in My Cart section");
	assertTrue(confirmationPage2SC.getShippingMethodSection(myAddress).contains(myShippingMethod), "The Shipping method is not displayed correctly");
	assertTrue(confirmationPage2SC.getShippingMethodSection(myAddress).contains(myShippingCost), "The Shipping Cost is not displayed correctly");
	Calendar shippingDeliveryDate = confirmationPage2SC.getShippingDeliveryDate(myAddress);
	Calendar orderedDate = Calendar.getInstance();
	assertTrue(Common.isDateBetweenTwoDates(shippingDeliveryDate, orderedDate, Common.getDateAfterNumberOfBusinessDays(orderedDate, 10)), "Your package does not arrive in 7-10 business days or less from the day you place your order.");
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

    private void selectMyShippingOptionFromShippingAndDeliveryModal() {
	Logger.info("In Payment & Review page: Select " + myShippingMethod + " From Shipping And Delivery Modal");
	paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
	paymentAndReviewPage2SC.selectShippingMethodInShippingAndDeliveryModal(myShippingMethod);
	myShippingCost = paymentAndReviewPage2SC.getShippingMethodCostInShippingDeliveryModal(myShippingMethod);
	myDeliveryDate = paymentAndReviewPage2SC.getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(myShippingMethod);
	paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void initTestCaseData() {
	Logger.tc("TC_SOP_003 - User Can Complete Checkout With Standard Shipping");
	Logger.to("TO_SOP_24 - In Payment & Review page, user can click 'View All Shipping Options' link and select Shipping Method \"Standard Shipping\" on the 'Shipping & Delivery' modal then complete checkout");
	myAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF);
	myShippingMethod = Constants.STANDARD_SHIPPING;
    }

    private void searchAndAddItem(Item item) {
	Logger.info("From Homepage, enter \"" + Common.getNumberFromText(item.getId()) + "\" into Search textbox and click Search button");
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("\"In Product Page: Add this item to " + item.getRecipient() + " - If Exclusive Offer (Upsell offer) appears, click \"Add To Cart\"");
	generalPage.addFirstSKUToCart(item);
	productPage.selectExclusiveOffer(false);
	productPage.continueShopping();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
    }

    private void fillBillingAddress() {
	Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
	paymentAndReviewPage2SC.fillBillingAddress(myAddress);
    }
}
