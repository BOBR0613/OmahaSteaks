package com.omahasteaks.tests.Promotion;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ManageShippingPromotion;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.data.objects.ShippingOptionPromotion;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_PM_002_VerifyPromotionDataOnPaymentAndReviewPage extends TestBase_2SC {

	@Inject
	ManageShippingPromotion managePromotion;
	@Inject
	Package mySku;
	@Inject
	CustomerAddress shippingAddress;

	@Inject
	HomePage homePage;
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

	@Test
	public void TC_PM_002_Verify_Promotion_Data_On_Payment_And_Review_Page() {
		initTestCaseData();

		addSKUToCartAndCheckOut(mySku);

		fillShippingAddress();

		verifyPromotionDataDisplaysCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSKUToCartAndCheckOut(SKU sku) {
		Logger.info("In Homepage:\n" + " - Search any Food SKU \n" + " - Select the first Food SKU, select \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + " - Click \"Checkout\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.checkOut();

		Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
		shoppingCartPage.checkOut();
	}

	private void fillShippingAddress() {
		Logger.info("In Shipping Address Page\n" + " - Open \"Send to\" dropdown list\n" + " - Click \"Add New Address\" link\n" + " - Fill mandatory information in Shipping Address\n" + " - Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		shippingAddressPage2SC.clickContinueButton();
	}

	private void verifyPromotionDataDisplaysCorrectly() {
		String[] arrShippingMethods = managePromotion.getListShippingOptions();
		ShippingOptionPromotion promotion = null;

		if (arrShippingMethods == null) {
			Logger.warning("Skip checking promotion data on the Payment And Review page");
			return;
		}
		for (String shippingMethod : arrShippingMethods) {
			promotion = managePromotion.getPromotionDataInDateRange(shippingMethod);
			if (promotion != null)
				Logger.verify("Verify the promotion data of " + shippingMethod + " method is displayed correctly as the expected text in a date range from" + promotion.getStartDate() + " to " + promotion.getEndDate());
			else {
				promotion = managePromotion.getDefaultPromotionData(shippingMethod);
				if (promotion == null) {
					Logger.warning("Skip checking promotion data of " + shippingMethod + " on the Payment And Review page");
					return;
				}
				Logger.verify("In default range - " + shippingMethod + " method is displayed correctly as the expected text in the default range");
				assertTrue(paymentAndReviewPage2SC.getPromotionTextOfShippingMethod(shippingMethod).contains(promotion.getPromotionText()), "The promotion data of " + shippingMethod + " does not display as expected");
			}
		}
	}

	private void initTestCaseData() {
		Logger.tc("TC_PM_002 - Verify the promotion data of the shipping options on the Payment and Review page");
		Logger.to("TO_PM_03 - Promotion data of the shipping options is displayed correctly on the Payment and Review page ");
		mySku.initRandom(Recipient.MYSELF);
		shippingAddress.initRandomInformation();
	}
}
