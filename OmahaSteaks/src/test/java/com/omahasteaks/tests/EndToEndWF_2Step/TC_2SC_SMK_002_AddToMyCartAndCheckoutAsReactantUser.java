package com.omahasteaks.tests.EndToEndWF_2Step;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SC_SMK_002_AddToMyCartAndCheckoutAsReactantUser extends TestBase_SMK {

    String[] shippingAddressForMysefl;

    @Test
    public void TC_2SC_SMK_002_Add_To_My_Cart_And_Checkout_As_Reactant_User() {
	Logger.tc("TC_2SC_SMK_002 Add To MyCart And Checkout As Reactant User");

	initTestCaseData();

	searchAndAddItem(myItem, false);

	goToMyCart();

	verifyMainSkuIsAddedSuccessfully();

	signInToRedeemPoints();

	checkOutFromShoppingCartPage();

	getShippingAddressAndContinueCheckout();

	verifyAddedShippingAddressDisplaysCorrectly();

	fillCreditCardNumberAndClickPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyOrderNumberInCorrectFormat();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyAddedShippingAddressDisplaysCorrectly() {
	Logger.verify("In New Payment and Review Page, verify that: Added Shipping address displays correctly");
	assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.MYSELF.getValue()), paymentAndReviewPage.convertShippingAddressForMobile(shippingAddressForMysefl));
    }

    private void getShippingAddressAndContinueCheckout() {
	shippingAddressForMysefl = shippingAddressPage.getRecipientAddress();
	continueCheckout();
    }

    private void continueCheckout() {
	Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
	shippingAddressPage.clickContinueButton();
    }

    private void fillCreditCardNumberAndClickPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date: we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber();
	paymentAndReviewPage.placeOrder();
    }

    private void signInToRedeemPoints() {
//	shoppingCartPage.goToSignInPage();
    generalPage.goToSignInPage();
	signInPage.signIn(Constants.OMAHA_EMAIL, Constants.OMAHA_PASSWORD);
	
	generalPage.goToMyCart();
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you. Your order is being prepared\" in its content.");
	assertTrue(confirmationPage.isThankYouMessageDisplayed(), "The Thank you message is not displayed as expected");
    }
}
