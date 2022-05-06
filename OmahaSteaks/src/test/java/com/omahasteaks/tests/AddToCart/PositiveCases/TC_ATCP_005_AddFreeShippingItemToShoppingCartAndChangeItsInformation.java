package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SideMenuItem; 
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.FreeShippingPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_005_AddFreeShippingItemToShoppingCartAndChangeItsInformation extends TestBase_2SC {
	@Inject
	Package myPkg;
	@Inject
	GeneralPage generalPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	FreeShippingPage freeShippingPage;

	@Test
	public void TC_ATCP_005_Add_Free_Shipping_Item_To_Shopping_Cart_And_Change_Its_Information() {
		Logger.tc("TC_ATCP_005_AddFreeShippingItemToShoppingCartAndChangeItsInformation");
		initTestCaseData();

		Logger.to("TO_ATCP_32	Add Free Shipping Package to the shopping cart");
		addFirstItemFromFreeShippingPage();
		verifyTheSkuAddedInMyselfCart();
		
		Logger.to("TO_ATCP_39	In Shopping Cart, Update quantity of an existing SKU");
		updateQuantity();
		verifyQuantityIsUpdated();
	}

	private void initTestCaseData() {
		myPkg.init(Recipient.MYSELF);
	}

	private void addFirstItemFromFreeShippingPage() {
		Logger.step("Click the \"" + SideMenuItem.FREE_SHIPPING_PACKAGES.getValue() + "\" menu link on Home Page");
		generalPage.clickSideMenuItemLink(SideMenuItem.FREE_SHIPPING_PACKAGES);
		Common.waitForPageLoad();
		Common.modalDialog.closeSavorDialog();
		Common.waitForDOMChange();
		freeShippingPage.addFirstSkuToCart(myPkg, false);
		Logger.step("Add \"" + myPkg.getName()+"\" to myself Cart");
		Logger.substep("Select \"Myself\" in the Shipto list");
		Logger.substep("Select \"1\" in the quantity list");
 		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
 		Logger.substep("Click \"Checkout\" or \"View My Cart\" button");
		productPage.checkOut();
	}

	private void updateQuantity() {
		Logger.step("In Shopping Cart page - Update SKU quantity to \"2\"");
		shoppingCartPage.updateQuantityOfSku(myPkg, 2);
	}

	private void verifyQuantityIsUpdated() {
		Logger.verify("Verify the the quantity and price are updated");
		assertEquals(shoppingCartPage.getQuantityOfSku(myPkg), 2, "The quantity is not updated as expected");
	}

	private void verifyTheSkuAddedInMyselfCart() {
		Logger.verify("Verify that added SKU exists in Shopping Cart (My Cart) with correct information");
		assertTrue(shoppingCartPage.isSkuByIdAdded(myPkg.getRecipient(), myPkg.getId()), "The sku is not displayed as expected");
	}
}
