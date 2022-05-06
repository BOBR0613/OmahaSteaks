package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.FreeShippingPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_008_AddFreeShipPackageByClickOnFreeShipOfferLink extends TestBase_2SC {
	@Inject
	ListSKUs myCart;
	@Inject
	SKU sku;
	@Inject
	SignInPage signInPage;
	@Inject
	GeneralPage generalPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	FreeShippingPage freeShippingPage;

	@Test
	public void TC_ATCP_008_Add_FreeShip_package_By_Click_On_FreeShip_Offer_Link() {
		initTestCaseData();

		signIn();

		goFreeShippingPageAndAddFirstItem();

		verifyTheSkuAddedInMyselfCart();

		removeTheSkuAddedThenClickNo();

		verifyTheSkuAddedInMyselfCart();
	}

	private void goFreeShippingPageAndAddFirstItem() {
		Logger.info("From Hompage, click on \"Free Ship offer\" at the right corner");
		generalPage.selectFreeShipPackages();
		Logger.info("At the first item, in Freeshipping page:\n" + "- Select \"Ship To Myself\"\n" + "- Click \"Add To Cart\" button" + "- in Add To Cart popup, click CHEKOUT button");
		freeShippingPage.addFirstSkuToCart(true);
		freeShippingPage.getAddedToCartSKUList(myCart);
		freeShippingPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_ATCP_008 	Add Free Ship package By Click On Free Ship Offer Link\n");
		Logger.to("TO_ATCP_44	Add To Cart with Freeship package  by clicking on\"On Select package\" under \"Free Ship offer\" at the right corner\n");
		Logger.to("TO_ATCP_45	In Shopping Cart, Click on \"remove\" link next to existing SKU then click \"No\" link\n");
		account = Constants.LIST_ACCOUNTS.getRandomAccount();
		myCart.initEmpty();
	}

	private void removeTheSkuAddedThenClickNo() {
		Logger.info("Click on \"Remove\" link, then click \"No\" button");
		shoppingCartPage.removeSkuById(sku, false);
	}

	private void signIn() {
		Logger.info("Sign in with a valid account");
		generalPage.goToSignInPage();
		signInPage.signIn(account.getEmail(), account.getPassword());
	}

	private void verifyTheSkuAddedInMyselfCart() {
		Logger.verify("Verify that added SKU exists in Shopping Cart (My Cart) with correct information");
		sku = myCart.getList().get(0);
		assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
	}
}
