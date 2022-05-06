package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
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
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_021_EditShoppingCartFromPaymentReviewPageThenCompleteCheckout extends TestBase_2SC {
	@Inject
	ListSKUs myCart;
	@Inject
	Package recipientSKU;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	CustomerAddress billingAddress, shippingAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ConfirmationPage2SC confirmationPage;
	@Inject
	CategoryPage categoryPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_2SCOP_021_Edit_Shopping_Cart_From_PaymentReviewPage_Then_Complete_Checkout() {
		initTestCaseData();

		addSKUToCart(recipientSKU);

		checkoutFromShoppingCart();

		fillShippingAddress();

		editCartFromPaymentReviewPageByAddingSpecialBonusSKU();

		verifySpecialBonusSkuIsAdded();

		fillBillingAddressAndCreditCardNumberThenPlaceMyOrder();

		verifySpecialBonusSkuIsAddedInConfirmPage();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSKUToCart(SKU sku) {
		Logger.info("From Homepage, Search a SKU" + "- Select this SKU\n" + "- Select \"Ship To Someone Else\"\n" + "- Fill to the \"Recipient's Name\" textbox\n" + "- Click \"ADD TO CART\" button" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();
	}

	private void checkoutFromShoppingCart() {
		Logger.info("In Shopping Cart Page:\n" + " - Click \"Checkout\" button");
		shoppingCartPage.checkOut();
	}

	private void editCartFromPaymentReviewPageByAddingSpecialBonusSKU() {
		Logger.info("In Payment & Review page\n" + "- Click \"edit\" link next to 'xx total items'" + "In Shopping Cart Page:\n" + " - Add \"Special Cart Bonus Savings\" SKU\n" + " - Click \"Check Out\"");
		paymentAndReviewPage2SC.selectLinkEditCartByRecipient(recipientSKU.getRecipient());
		shoppingCartPage.addSpecialCartBonus(recipientSKU.getRecipient());
		recipientSKU.setName(shoppingCartPage.getAddedSkuNameFromModal());
		shoppingCartPage.closeAddedToCartModal();
		shoppingCartPage.checkOut();
	}

	private void fillBillingAddressAndCreditCardNumberThenPlaceMyOrder() {
		Logger.info("In Payment & Review page\n" + " - Fill mandatory information in Billing Address\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n" + " - Click \"Place Order\"");
		paymentAndReviewPage2SC.fillCreditCardNumber();
		paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
		paymentAndReviewPage2SC.placeOrder();
	}

	private void fillShippingAddress() {
		Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Click \"Add New Address\" link\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
		// shippingAddressPage2SC.closeViewItemsPage();
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void initTestCaseData() {
		Logger.tc("TC_2SCOP_021 - Edit Shopping Cart From Payment Review Page Then Complete Checkout");
		Logger.to("TO_2SCOP_52 - In Payment & Review page - User returns to Shopping Cart page by clicking the edit link next to 'xx total items'");
		recipientSKU.init(SkuType.ITEM,Recipient.THONG_NGUYEN);
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		shippingAddress.removeShippingAddressOptionalFields();
		billingAddress = lstAddresses.getRandomBillingAddress();
	}

	private void verifySpecialBonusSkuIsAdded() {
		Logger.verify("Verify Special Bonus Sku is added");
		assertTrue(paymentAndReviewPage2SC.isSKUExisted(recipientSKU), "The special sku is not displayed as expected");
	}

	private void verifySpecialBonusSkuIsAddedInConfirmPage() {
		confirmationPage.closeModal();
		Logger.verify("In Order Receipt Page\n" + "- Verify added items appear in My Cart section");
		assertTrue(confirmationPage.isSKUDisplayed(shippingAddress, recipientSKU), "The special sku is not displayed as expected");
	}
}