package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
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
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_016_AddedInformationDisplayCorectlyInConfirmationPage extends TestBase_2SC {
    CustomerAddress billingAddress;
    CustomerAddress shippingAddress;
    String creditCardExpDate;
    String actualCreditCardType;

    @Inject
    ListSKUs myCart;

    @Inject
    Item myItem;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    SignInPage signInPage;

    @Inject
    SearchResultPage searchResultPage;

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
    public void TC_2SCOP_016_Added_Information_Display_Corectly_In_Confirmation_Page() {
	initTestCaseData();

	searchAndAddItem(myItem);

	checkOutFromAddedToCart();

	checkoutFromShoppingCart();

	continueFromShippingAddressPage();

	fillBillingAddressAndCreditCardInPaymentAndReviewPage();

	// paymentAndReviewPage2SC.getCreditCardType();

	// placeOrder();

	verifyDataAppearsCorrectlyInTheBillingInformationSection(actualCreditCardType);

	verifyShippingAddressAppearsCorrectlynMyCartSection();

	verifyItemsAppearCorrectlyInMyCartSection();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyShippingAddressAppearsCorrectlynMyCartSection() {
	Logger.verify("In Confirmation Page:\n" + "Verify Shipping Address appears correctly in the My Cart section.");
	String[] expectedShippingAddress = shippingAddress.toShippingArrayForConfirmationPage();
	String[] actualShippingAddress = confirmationPage2SC.getShippingAddressContent(shippingAddress);
	assertEquals(actualShippingAddress, expectedShippingAddress);
    }

    private void verifyItemsAppearCorrectlyInMyCartSection() {
	Logger.info("In Confirmation Page:\n" + "Verify items appear correctly in My Cart section.");
	for (SKU sku : myCart.getList()) {
	    Logger.verify("Verify " + sku.getName() + " displays correctly in " + confirmationPage2SC.getShippingSectionName(shippingAddress) + " section.");
	    assertTrue(confirmationPage2SC.isSKUDisplayed(shippingAddress, sku));
	}
    }

    private void verifyDataAppearsCorrectlyInTheBillingInformationSection(String actualCreditCardType) {
	Logger.verify("In Confirmation Page:\n" + "Verify data (Billing Address, Payment Method, Contact Info) appears correctly in the Billing Information section.");
	// Verify Billing Address section
	confirmationPage2SC.closeModal();
	String[] actualBillingAddress = confirmationPage2SC.getBillingAddress();
	assertEquals(actualBillingAddress, billingAddress.toBillingArray());

	// Verify Payment Method section
	String[] actualPaymentMethodContent = confirmationPage2SC.getPaymentMethodContent();
	String[] expectedPaymentMethodContent = new String[] { actualCreditCardType, "************" + Constants.CREDIT_CARD_NUMBER.substring(12), "Exp: " + creditCardExpDate };
	assertEquals(actualPaymentMethodContent, expectedPaymentMethodContent);

	// Verify Contact Info section
	String[] actualContactInfoContent = confirmationPage2SC.getContactInfoContent();
	String[] expectedContactInfoContent = new String[] { billingAddress.email, billingAddress.phoneNumber };
	assertEquals(actualContactInfoContent, expectedContactInfoContent);
    }

    private void fillBillingAddressAndCreditCardInPaymentAndReviewPage() {
	Logger.info("In Payment and Review Page:\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "- Fill valid mandatory information into Billing Address section\n" + "- Click \"Place My Order\" button");
	creditCardExpDate = "01/" + Common.randomYearOfFuture();
	paymentAndReviewPage2SC.fillCreditCardNumber(Constants.CREDIT_CARD_NUMBER, creditCardExpDate);
	creditCardExpDate = "01/" + creditCardExpDate.substring(5);
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	actualCreditCardType = paymentAndReviewPage2SC.getCreditCardType();
	paymentAndReviewPage2SC.placeOrder();
    }

    private void continueFromShippingAddressPage() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information into Shipping Address section\n" + "- Click  \"Continue \"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkoutFromShoppingCart() {
	Logger.info("In Shopping Cart page\n" + "- Click \"CHECKOUT\"");
	shoppingCartPage.checkOut();
    }

    private void checkOutFromAddedToCart() {
	Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
	generalPage.getAddedToCartSKUList(myCart);
	generalPage.checkOut();
    }

    private void searchAndAddItem(Item item) {
	Logger.info("From Homepage, Search a SKU " + item.getName());
	generalPage.search(Common.getNumberFromText(item.getId()));
	Logger.info("In Search page\n" + "- Select the first Item and add it to Cart");
	Logger.info("In Exclusive Offer (Upsell offer) modal:\n" + "- Click \"Add To Cart\"");
	productPage.addSKUToCart(item, true);
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_016 Added Information Display Corectly In Confirmation Page");
	Logger.to("TO_2SCOP_31	In Confirmation Page, Verify data (Billing Address, Payment Method, Contact Info) appears correctly in the Billing Information section");
	Logger.to("TO_2SCOP_32	In Confirmation Page, Verify Shipping Address appears correctly in the My Cart section.");
	Logger.to("TO_2SCOP_33	In Confirmation Page, Verify items appear correctly in My Cart section.");
	billingAddress = lstAddresses.getRandomBillingAddress();
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	shippingAddress.email = billingAddress.email;
	shippingAddress.phoneNumber = billingAddress.phoneNumber;
	myCart.initEmpty();
	myItem.init(SkuType.ITEM,Recipient.MYSELF);
    }
}
