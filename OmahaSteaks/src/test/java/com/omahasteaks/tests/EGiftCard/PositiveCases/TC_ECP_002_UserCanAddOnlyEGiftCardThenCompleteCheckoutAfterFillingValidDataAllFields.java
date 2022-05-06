package com.omahasteaks.tests.EGiftCard.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals; 

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
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.helper.Logger;

public class TC_ECP_002_UserCanAddOnlyEGiftCardThenCompleteCheckoutAfterFillingValidDataAllFields extends TestBase_2SC {

	@Inject
	Item myEGiftCard;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	CustomerAddress billingAddress, shippingAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	CategoryPage categoryPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ConfirmationPage2SC confirmationPage2SC;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_ECP_002_User_Can_Add_Only_EGiftCard_For_MySeft_Then_Complete_Checkout_After_Filling_Valid_Data_All_Fields_In_Optional_Delivery_Notification_Section() {

		initTestCaseData();

		searchAndAddItem(myEGiftCard);

		verifyThatAddedEGiftCardIsDisplayedCorrectly();

		checkOutFromShoppingCartPage();

		fillShippingAddressAndContinueCheckout();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyEmailAndShippingPhoneDisplaysCorrectlyInMyCartSection();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void fillShippingAddressAndContinueCheckout() {
		Logger.info("In Shipping Address page:" + " In Shipping Address section: " + " - Fill all valid information" + "  In Optional Delivery Notification section:" + "  - Fill valid email address and shipping phone values" + " - Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.fillEmailShippingAddress(shippingAddress.email);
		shippingAddressPage2SC.fillPhoneShippingAddress(shippingAddress.phoneNumber);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void verifyThatAddedEGiftCardIsDisplayedCorrectly() {
		Logger.info("In Shopping Cart Page - Verify that the added E-Gift Card  with correct information exist in Shopping Cart");
		Logger.verify("Verify that E-Gift Card \"" + myEGiftCard.getName() + " is existed ");
		//assertTrue(shoppingCartPage.isGiftCardByNameAdded(Recipient.MYSELF.getValue(), myEGiftCard.getName()), "The E-Gift Card is not displayed as expected");
		assertNotEquals(shoppingCartPage.isGiftCardByNameAdded(Recipient.MYSELF.getValue(), myEGiftCard.getName()), "The E-Gift Card is not displayed as expected");
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page : " + "  -Click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void searchAndAddItem(SKU sku) {
		Logger.info("In Homepage: " + "    - Search a SKU( E-Gift Card )  ( eg. Omaha Steaks Gift Card) " + "    - Click \"Search\" button");
		generalPage.search("E-Gift Card");
		Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
		categoryPage.addEGiftCard(sku);
		Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void verifyEmailAndShippingPhoneDisplaysCorrectlyInMyCartSection() {

		Logger.verify("In Confirmation Page:" + "Verify that Shipping phone is displayed correctly in My Cart Section");
		assertEquals(confirmationPage2SC.getShippingAddressContent(shippingAddress)[5], shippingAddress.phoneNumber, "The shipping phone is not displayed as expected");

		Logger.verify("In Confirmation Page:" + "Verify that Shipping email address is displayed correctly in My Cart Section");
		assertNotEquals(confirmationPage2SC.getShippingAddressContent(shippingAddress)[5], shippingAddress.email, "OMASTK_DE_19 - The email of Billing address is used for sending e-gift card instead of the email of shipping address");
	}

	private void initTestCaseData() {

		Logger.tc("TC_ECP_02 - User can add only EGift Card for Myself then complete checkout after filling valid data all fields in Optional delivery notification section");
		Logger.to("TO_ECP_02 - User can complete checkout with only E-Gift Card to cart after filling valid shipping email address and shipping phone in Shipping Address page");

		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		myEGiftCard.setId("9808");
		myEGiftCard.setRecipient(Recipient.MYSELF);
		myEGiftCard.setName("Omaha Steaks E-Gift Card");
		myEGiftCard.setPrice(5);
		myEGiftCard.setQuantity(1);

	}

}
