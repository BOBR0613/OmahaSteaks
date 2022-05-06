package com.omahasteaks.tests.MinimumThreshold;
 
import static org.testng.Assert.assertEquals; 

import org.testng.annotations.Test; 

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient; 
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage; 
import com.omahasteaks.tests.SteakloverRewardsMembership.TestBase_SLR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;  
import com.omahasteaks.utils.helper.Logger;

public class TC_MIN_001SlrGoldMembersDoNotHaveAMinimumThreshold extends TestBase_SLR {

    @Inject
    GeneralPage generalPage;

    @Inject
    CustomerAddress shippingAddress, billingAddress;
    
    @Inject
    ListAddresses lstAddresses;
    
    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    SKU sku;
    
    @Inject
    MyAccountPage myAccountPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage;

    @Inject
    ConfirmationPage2SC confirmationPage;
     
    
    @Test
    public void TC_MIN_001SLR_Gold_Members_Do_NOT_Have_A_Minimum_Threshold() {

	initTestCaseData();
	
	addDessertItemToCart();
	
	generalPage.checkOut();
 
	checkOutFromShoppingCartPage();
	
	shippingAddressPage.clickContinueButton();
	
	shippingAddressPage.clickConfirmAddressButton();

	fillCreditCardNumberAndPlaceMyOrder();
	 
	verifyThankYouMessageDisplays(); 
 
    }
    
    
    public void addDessertItemToCart() {
    	Logger.info("Go to Desserts page");
    	generalPage.goToDepartmentPage("Desserts");
    	generalPage.addFirstSKUToCart(sku);
    }
 
    protected void fillShippingAddressAndContinueCheckout(CustomerAddress address) {
	Logger.info("In Shipping Address form:\n" + " - Enter valid information\n" + " - Click Continue Checkout");
	shippingAddressPage.fillShippingAddress(address);
	shippingAddressPage.clickContinueButton();
    }
    
    protected void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("From Payment Option and Review page, in Credit Card: \n" + " - Fill \" 4111111111111111\" number\n" + " - Expire Date:we will generate randomly a date in future\n" + "Click \"Place My Order\" button");
	paymentAndReviewPage.fillCreditCardNumber();
	paymentAndReviewPage.fillBillingAddress(billingAddress);
	paymentAndReviewPage.placeOrder();
    }
    

    protected void verifyShippingAddressesDisplayCorrectly(Recipient recipient, CustomerAddress expectedShippingAddress) {
	Logger.verify("In Payment Option and Review page, verify that:\n" + " - Added Shipping address displays correctly");
	assertEquals(paymentAndReviewPage.getShippingAddress(recipient.getValue()), shippingAddress.toShippingArray(), "The shipping address is not displayed as expected");
    }
    
    protected void verifyThankYouMessageDisplays() {
	Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
    
    private void initTestCaseData() {
    	Logger.tc("TC_MIN_001 - Gold memberships do NOT have a minimum order threshold");
    	Logger.to("TO_MIN_01 - Checkout with an item below the minimum threshold for a gold member");
    	Common.modalDialog.closeSavorDialog();
    	billingAddress = lstAddresses.getRandomBillingAddress();
    	shippingAddress = lstAddresses.getDefaultShippingAddress();
     	sku.setRecipient(Recipient.MYSELF); 
		account = Constants.LIST_ACCOUNTS.setAccountByEmail("slrgold01@omahasteaks.com");
		Constants.DB.updateGoldExpireDate(account.getEmail(), 90);
	   	Common.modalDialog.closeSavorDialog();
		signIn(account);
    }
    

}
