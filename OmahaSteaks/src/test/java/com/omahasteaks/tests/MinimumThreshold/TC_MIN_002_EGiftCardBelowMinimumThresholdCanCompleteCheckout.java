package com.omahasteaks.tests.MinimumThreshold;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
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
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MIN_002_EGiftCardBelowMinimumThresholdCanCompleteCheckout extends TestBase_2SC {

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
	public void TC_MIN_002_EGift_Card_Below_Minimum_Threshold_Can_Complete_Checkout() {

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
		shippingAddressPage2SC.clickContinueButton();
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page : " + "  -Click CHECKOUT button");
		shoppingCartPage.checkOut();
	}

	private void searchAndAddGiftCard(SKU sku) {
		String url="";
		Logger.info("In Homepage: " + "    - Go to \"" +  Constants.OMAHA_URL + "\"/buy/Gifts/Gift-Cards\" ( eg. Omaha Steaks E-Gift Card) " + "    - Click \"Search\" button");
		if (Constants.OMAHA_URL.contains("?")) {
			  String str = Constants.OMAHA_URL.trim();
			  String[] arrOfStr = str.split("\\?",2);
			  url = arrOfStr[0].trim() + "buy/Gifts/Gift-Cards" + "?" + arrOfStr[1]; 
		}
		else 
		if (Constants.OMAHA_URL.contains("legacy")) {
			  String str = Constants.OMAHA_URL.trim();
			  String[] arrOfStr = str.split("legacy",2);
			  url = arrOfStr[0].trim() + "buy/Gifts/Gift-Cards"; 
		}
		else url = Constants.OMAHA_URL + "/buy/Gifts/Gift-Cards";
		DriverUtils.getWebDriver().get(url);
		Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
		Common.modalDialog.closeSavorDialog();
		categoryPage.addEGiftCard(sku);
		Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_MIN_002 - User can add E-Gift card below the minimum threshold for Myself then complete checkout.");
		Logger.to("TO_MIN_01 - User can add E-Gift Card below the minimum threshold to cart");
		Logger.to("TO_MIN_02 - User can complete checkout");
		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getShippingAddressByState("California");
		myEGiftCard.setRecipient(Recipient.MYSELF);
		myEGiftCard.setPrice(80);
		myEGiftCard.setQuantity(1);
		Common.modalDialog.closeSavorDialog();
	}

}
