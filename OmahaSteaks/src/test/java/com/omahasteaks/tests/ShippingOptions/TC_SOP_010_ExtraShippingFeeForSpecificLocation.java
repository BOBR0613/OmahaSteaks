package com.omahasteaks.tests.ShippingOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
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

public class TC_SOP_010_ExtraShippingFeeForSpecificLocation extends TestBase_2SC {

	CustomerAddress billingAddress, shippingAddressFreeShipping, shippingAddressWithFee;
	@Inject
	ConfirmationPage2SC confirmationPage;
	@Inject
	GeneralPage generalPage;
	@Inject
	Item itemMyself;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	ProductPage productPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_SOP_010_Extra_Shipping_Fee_For_Specific_Location() {
		initTestCaseData();
		addSKUToCartAndCheckOut();

		shippingAddressWithFee = lstAddresses.getShippingAddressByState("Hawaii");
		fillShippingAddress();

		Logger.verify("In USA, the extra shipping fee for Hawaii should be $39.99");
		verifyExtraShippingFee(Constants.EXTRA_SHIPPING_FEE_HAWAII);

		shippingAddressWithFee = lstAddresses.getShippingAddressByState("Alaska");
		editShippingAddress();
		Logger.verify("In USA, the extra shipping fee for Alaska should be $39.99");
		verifyExtraShippingFee(Constants.EXTRA_SHIPPING_FEE_ALASKA);

		shippingAddressWithFee = lstAddresses.getShippingAddressByState("Puerto Rico");
		editShippingAddress();
		Common.waitForDOMChange();
//		shippingAddressPage2SC.clickContinueButton();
	
		Logger.verify("In USA, the extra shipping fee for Puerto Rico should be $39.00");
		verifyExtraShippingFee(Constants.EXTRA_SHIPPING_FEE_PUERTO_RICO);

		shippingAddressWithFee = lstAddresses.getShippingAddressByState("Virgin Islands");
		editShippingAddress();
		Logger.verify("In USA, the extra shipping fee for Virgin Islands should be $125.00");
		verifyExtraShippingFee(Constants.EXTRA_SHIPPING_FEE_VIRGIN_ISLANDS);

		shippingAddressWithFee = lstAddresses.getRandomShippingAddressCanada();
		editShippingAddress();
		Common.waitForDOMChange();
		Logger.verify("In Canada, the extra shipping fee should be $42.99");
		verifyExtraShippingFee(Constants.EXTRA_SHIPPING_FEE_CANADA);

		fillCreditCardNumberAndPlaceMyOrder();

		verifyThankYouMessageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void addSKUToCartAndCheckOut() {
		Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(itemMyself.getId()));
		productPage.addSKUToCart(itemMyself, false);
		generalPage.checkOut();

		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	    Common.waitForDOMChange();
	}

	private void editShippingAddress() {
		Logger.info("\"In New Payment and Review Page:\n" + " - Click \"Click the 'Edit this address' link in My Cart section to update Shipping Address.");
		paymentAndReviewPage2SC.selectEditShippingAddrLink(Recipient.MYSELF.getValue());
		Logger.info("In  Shipping Address Page:\n" + "- Update information based on existing data.\n" + "- Click Click 'UPDATE CONTACT' button.\n");
		shippingAddressPage2SC.clickEditThisAddressLink();
	    Common.waitForDOMChange();
		shippingAddressPage2SC.fillShippingAddressInModal(shippingAddressWithFee);
	    Common.waitForDOMChange();
		shippingAddressPage2SC.updateContactInModal();
	    Common.waitForDOMChange();
		shippingAddressPage2SC.clickContinueButton();
	    Common.waitForDOMChange();
	}

	private void fillCreditCardNumberAndPlaceMyOrder() {
		Logger.info("In New Payment and Review Page:\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "- Fill valid mandatory information at Billing Address section\n" + "- Click \"Place My Order\" button");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void fillShippingAddress() {
		Logger.info("In Shipping Address form:\n" + " - Fill valid information\n" + " - Click \"Continue Checkout\"");
		shippingAddressPage2SC.fillShippingAddress(shippingAddressWithFee);
		shippingAddressPage2SC.clickContinueButton();
	    Common.waitForDOMChange();
	}

	private void initTestCaseData() {
		Logger.tc("TC_SOP_010 ExtraShippingFeeForSpecificLocation");
		Logger.to("TO_SOP_43 In USA, the extra shipping fee for Hawaii / Alaska should be $39.99");
		Logger.to("TO_SOP_44 In USA, the extra shipping fee for Puerto Rico should be $39.00");
		Logger.to("TO_SOP_45 In USA, the extra shipping fee for Virgin Islands should be $125.00");
		Logger.to("TO_SOP_46 In Canada, the extra shipping fee should be $42.99");
		itemMyself.init(SkuType.PACKAGES, Recipient.MYSELF);
		billingAddress = lstAddresses.getRandomBillingAddress();
	}

	private void verifyExtraShippingFee(String extraShippingFee) {
		Common.waitForDOMChange();
		assertEquals(paymentAndReviewPage2SC.getExtraShippingFee(Recipient.MYSELF.getValue()), extraShippingFee, "The extra shipping fee is not correct!");
	}

	private void verifyThankYouMessageDisplays() {
		Common.waitForDOMChange();
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertTrue(confirmationPage.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
	}

}
