package com.omahasteaks.tests.EGiftCard.PositiveCases;

import static org.testng.Assert.assertEquals;

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

public class TC_ECP_001_UserCanAddOnlyEGiftCardThenCompleteCheckout extends TestBase_2SC {

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
	public void TC_ECP_001_User_Can_Add_Only_EGift_Card_For_Myself_Then_Complete_Checkout_After_Filling_Valid_Email_In_Optional_Delivery_Notification_Section() {

		initTestCaseData();

		searchAndAddGiftCard(myEGiftCard);

		checkOutFromShoppingCartPage();

		fillShippingAddressAndContinueCheckout();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyShippingEmailAddressDisplaysCorrectlyInMyCartSection();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void verifyShippingEmailAddressDisplaysCorrectlyInMyCartSection() {
		Logger.verify("In Confirmation Page:" + "Verify that Shipping email address is displayed correctly in My Cart Section");
		assertEquals(confirmationPage2SC.getShippingAddressContent(shippingAddress)[4], shippingAddress.email, "OMASTK_DE_19 - The email of Billing address is used for sending e-gift card instead of the email of shipping address");
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("In Payment & Review page" + "- Fill mandatory information in Billing Address" + "From Payment Option and Review page, in Credit Card: " + "- Fill \" 4111111111111111\" number" + "- Expire Date: we will generate randomly a date in future" + "Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void fillShippingAddressAndContinueCheckout() {
		Logger.info("In Shipping Address page:" + "In Shipping Address section: " + "    - Fill all valid information" + "In Optional Delivery Notification section:" + "    - Fill valid email address value" + "- Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.fillEmailShippingAddress(shippingAddress.email);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page : " + "  -Click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void searchAndAddGiftCard(SKU sku) {
		Logger.info("In Homepage: " + "    - Search a SKU( E-Gift Card )  ( eg. Omaha Steaks E-Gift Card) " + "    - Click \"Search\" button");
		generalPage.search("E-Gift Card");
		Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
		categoryPage.addEGiftCard(sku);
		Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_ECP_01 - User can add only E-Gift card for Myself then complete checkout after filling valid email in Optional Delivery Notification section");
		Logger.to("TO_ECP_01 - User can complete checkout with only E-Gift Card to cart after filling valid value in email textbox in Shipping Address page");
		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getShippingAddressByState("California");
		myEGiftCard.setRecipient(Recipient.MYSELF);
		myEGiftCard.setPrice(5);
		myEGiftCard.setQuantity(1);
	}

}
