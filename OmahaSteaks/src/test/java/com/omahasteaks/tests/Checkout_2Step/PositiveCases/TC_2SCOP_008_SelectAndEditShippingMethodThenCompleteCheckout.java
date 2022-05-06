package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
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

public class TC_2SCOP_008_SelectAndEditShippingMethodThenCompleteCheckout extends TestBase_2SC {
	String selectedRecipient, deliveryDate, shippingMethod;
	List<String> lstShippingOptions;
	@Inject
	ListSKUs myCart;
	@Inject
	SKU mySku;
	@Inject
	CustomerAddress newAddress;
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
	public void TC_2SCOP_008_Select_And_Edit_Shipping_Method_Then_Complete_Checkout() {
		initTestCaseData();

		addFirstSKUToCart(mySku, myCart);

		fillShippingAddressAndClickContinue();

		fillBillingAddress();

		selectRandomShippingOptionInShippingAndDeliveryPopup();

		verifyShippingDeliveryDisplaysCorrectly(shippingMethod);

		selectDeliveryShippingMethod(Constants.STANDARD_SHIPPING);

		verifyShippingDeliveryDisplaysCorrectly(Constants.STANDARD_SHIPPING);

		fillCreditCardNumberThenPlaceMyOrder();

		verifyThankYouMessageDisplays();

		verifyOrderNumberInCorrectFormat();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyOrderNumberInCorrectFormat() {
		String getOrderNumberText = confirmationPage2SC.getOrderNumberText();
		Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
		assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
		assertTrue(confirmationPage2SC.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
	}

	private void selectRandomShippingOptionInShippingAndDeliveryPopup() {
		selectedRecipient = Recipient.MYSELF.getValue();
		Logger.info("In Payment & Review page:Click 'View All Shipping Options\" link in My Cart Section");
		paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(Recipient.MYSELF.getValue());
		lstShippingOptions = paymentAndReviewPage2SC.getShippingMethodsListInShippingAndDeliveryModal();
		Logger.info("In Shipping and Delivery popup:Select Shipping method (randomly) in \"Shipping And Delivery\" modal");
		shippingMethod = paymentAndReviewPage2SC.selectRandomShippingMethodInShippingAndDeliveryModal(lstShippingOptions);
		deliveryDate = paymentAndReviewPage2SC.getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(shippingMethod);
		Logger.info(" - Click 'Save & Continue' button in Modal dialog");
		paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
	}

	private void selectDeliveryShippingMethod(String shippingMethod) {
		Logger.info("In Payment & Review page\n" + " - Click ' View All Shipping Options'");
		selectedRecipient = Recipient.MYSELF.getValue();
		paymentAndReviewPage2SC.clickViewAllShippingOptionsByRecipientNameLink(selectedRecipient);
		Logger.info(" - Select Shipping Method option in modal dialog");
		paymentAndReviewPage2SC.selectShippingMethodInShippingAndDeliveryModal(shippingMethod);
		deliveryDate = paymentAndReviewPage2SC.getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(shippingMethod);
		Logger.info(" - Click 'Save & Continue' button in Modal dialog");
		paymentAndReviewPage2SC.clickSaveAndContinueButtonInModal();
	}

	private void verifyShippingDeliveryDisplaysCorrectly(String shippingMethod) {
		Logger.verify("In Payment & Review page\n" + " - Verify Shipping Method and Delivery Date display correctly");
		if (deliveryDate != null) {
			assertTrue(paymentAndReviewPage2SC.getShippingInfo(selectedRecipient).contains(shippingMethod), "The Shipping method is not displayed as expected");
		} else
			Logger.warning(shippingMethod + " shipping method is not displayed!");
	}

	private void addFirstSKUToCart(SKU sku, ListSKUs lSku) {
		Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
		shoppingCartPage.checkOut();
	}

	private void fillBillingAddress() {
		Common.waitForPageLoad();
		Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address");
		paymentAndReviewPage2SC.fillBillingAddress(newAddress);
	}

	private void fillCreditCardNumberThenPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.placeOrderIgnoreError();
	}

	private void fillShippingAddressAndClickContinue() {
		Logger.info("In Shipping Address Page\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\"");
		shippingAddressPage2SC.fillShippingAddress(newAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_008 - Select And Edit Shipping Method Then Complete Checkout\n");
		Logger.to("TO_2SCOP_26 - In Payment & Review, user can edit shipping method by clicking \"Edit delivery date\" then complete Checkout\n");
		mySku = myCart.getRandomSKU(SkuType.PACKAGES, SkuStatus.EXISTING);
		mySku.setRecipient(Recipient.MYSELF);
		newAddress.initRandomInformation();
	}

	private void verifyThankYouMessageDisplays() {
		Logger.verify("Verify that a message appears with \"Thank you for your order! It is being prepared to ship\" in its ");
		assertTrue(confirmationPage2SC.isThankYouMessageDisplayed(), "Thank You message is not displayed as expected");
	}
}
