package com.omahasteaks.tests.EndToEndWF_2Step;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common; 
import com.omahasteaks.utils.helper.Logger;

public class TestBase_SMK extends TestBase_2SC {

	@Inject
	Item myItem;

	@Inject
	CustomerAddress shippingAddress, billingAddress;

	@Inject
	SearchResultPage searchResultPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	GeneralPage generalPage;

	@Inject
	MyAccountPage myAccountPage;
	@Inject
	ProductPage productPage;

	@Inject
	ListSKUs myCart;

	@Inject
	ShippingAddressPage2SC shippingAddressPage;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	SignInPage signInPage;

	@Inject
	ConfirmationPage2SC confirmationPage;

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	protected void goToMyCart() {
		Logger.info("Click on \"My Cart\" link");
		Common.modalDialog.closeUnknownModalDialog();
		generalPage.goToMyCart();
	}

	protected void checkOutFromShoppingCartPage() {
		Logger.info("In My Cart, click CHECKOUT button");
		shoppingCartPage.checkOut();
		Common.waitForPageLoad();
	}

	protected void verifyOrderNumberInCorrectFormat() {
		String getOrderNumberText = confirmationPage.getOrderNumberText();
		Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
		assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
	}

	protected void verifyBillingAddressAndThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
		assertEquals(confirmationPage.getBillingAddress(), billingAddress.toBillingArray(), "The billing address is not displayed as expected");
	}

	protected void initTestCaseData() {
		myCart.initEmpty(); 
		billingAddress = lstAddresses.getRandomBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		myItem.init(SkuType.PACKAGES, Recipient.MYSELF); 
	}

	protected void verifyMainSkuIsAddedSuccessfully() {
		Common.waitForPageLoad();
		Logger.verify("In My Cart, verify the items (include main item and special item) with correct names exist in My Shopping Cart and number above \"My Cart\" icon equal to added SKUs");
		assertTrue(shoppingCartPage.isItemAddedToCart(myItem), "Item " + myItem.getId() + " is not displayed as expected");
	}

	protected void checkOut() {
		Logger.info("In \"Added To Cart\" popup, click \"CHECKOUT\"\n" + "In Shopping Cart page, click CHECKOUT button");
		goToMyCart();
		checkOutFromShoppingCartPage();
	}

	protected void searchAndAddItem(Item item, boolean doAddExclusiveOffer) {
		Logger.info("From Homepage, enter \"" + item.getName() + "\" into Search textbox and click Search button");
		Logger.info("Select the first item to go to product page");
		generalPage.search(item.getName());
		searchResultPage.selectFirstItem();
		Logger.info("\"Add SKU to " + item.getRecipient() + "\n" + "In Search page, At the first Item:\n" + " - In \"Select Size & Count\" dropdown, select a random Size, then select a random Count .\n" + " - In the second dropdown, select \"Ship To " + item.getRecipient().getValue() + "\"\n" + "- Click \"ADD TO CART\" button\"\n");
		if (doAddExclusiveOffer) {
			Logger.info("If Exclusive Offer (Upsell offer) appears, click \"Add To Cart\"");

		} else
			Logger.info("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		productPage.addItemToCart(item, doAddExclusiveOffer, false);
		generalPage.getAddedToCartSKUList(myCart);
		Logger.info("In \"Added To Cart\" popup, click \"CONTINUE SHOPPING\" button");
		productPage.continueShopping();
		Common.waitForPageLoad();
	}

	protected void fillShippingAddressAndContinueCheckout(CustomerAddress address) {
		Logger.info("In Shipping Address form:\n" + " - Enter valid information\n" + " - Click Continue Checkout");
		shippingAddressPage.fillShippingAddress(address);
		shippingAddressPage.clickContinueButton();
		Common.waitForPageLoad();
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
}
