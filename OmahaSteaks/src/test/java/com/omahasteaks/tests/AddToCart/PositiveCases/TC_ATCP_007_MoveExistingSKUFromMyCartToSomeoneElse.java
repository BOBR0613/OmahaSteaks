package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.FreeShippingPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_007_MoveExistingSKUFromMyCartToSomeoneElse extends TestBase_2SC {
	@Inject
	Package myPkg;
	@Inject
	Item item;
	@Inject
	Package packageRecipient;
	@Inject
	GeneralPage generalPage;
	@Inject
	HomePage homePage;
	@Inject
	ProductPage productPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	FreeShippingPage freeShippingPage;

	@Test
	public void TC_ATCP_007_Move_Existing_SKU_From_MyCart_To_Someone_Else() {
		Logger.tc("TC_ATCP_007 	Move Existing SKU From MyCart To Someone Else");
		initTestCaseData();

		Logger.to("TO_ATCP_42	In Shopping Cart, Move an existing SKU from myCart to someone not in the recipient list");
		addItemFromHomePageToMyCart();
		searchAndAddItemFromProductPageToMyCart(item);
		moveToSomeoneElseNotInRecipientList();
		verifyThatAddedSKUMovedToRecipientCart(myPkg);

		Logger.to("TO_ATCP_43	In Shopping Cart, Move an existing SKU from myCart to someone in the recipient list");
		moveToSomeoneInRecipientList();
		verifyThatAddedSKUMovedToRecipientCart(item);
	}

	private void initTestCaseData() {
		myPkg.init(Recipient.MYSELF);
		item.initRandom(Recipient.MYSELF);
	}

	private void moveToSomeoneInRecipientList() {
		Logger.step("Move item \"" + item.getId() + "\" to \"" + Recipient.KIM_ANH.getValue() + "'s\" cart");
		Logger.substep("Click on \"Send To Someone Else\" link for item " + item.getId() + " in MySelf Cart");
		Logger.substep("In \"Send To Someone Else\" modal, select \"Move To\"");
		Logger.substep("Select recipient \"" + Recipient.KIM_ANH.getValue() + "\"");
		Logger.substep("Click \"Move This Item\" button\"");
		shoppingCartPage.moveSkuToSomeoneElse(item, Recipient.KIM_ANH);
		item.setRecipient(Recipient.KIM_ANH);
	}

	private void moveToSomeoneElseNotInRecipientList() {
		Logger.step("In Shopping Cart page, Move item \"" + myPkg.getId() + "\" to a new recipient");
		Logger.substep("Click on \"Send To Someone Else\" link for item " + myPkg.getId() + " in MySelf Cart");
		Logger.substep("In \"Send To Someone Else\" modal, select \"Move To\"");
		Logger.substep("Select \"Someone Else\" and enter recipient name \"Kim Anh\"");
		Logger.substep("Click \"Move This Item\" button\"");
		shoppingCartPage.moveSkuToSomeoneElse(myPkg, Recipient.KIM_ANH);
		myPkg.setRecipient(Recipient.KIM_ANH);
	}

	private void searchAndAddItemFromProductPageToMyCart(SKU sku) {
		Logger.step("Add item to myCart (e.g. \"" + Common.getNumberFromText(sku.getId()) + "\")");
		Logger.substep("Search for item \"" + Common.getNumberFromText(sku.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		Logger.substep("In Product Page: Select \"Ship To Myself\"");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("In Add To Cart popup, click CHEKOUT button");
		productPage.addSKUToCart(sku, false);
		productPage.checkOut();
	}


	private void addItemFromHomePageToMyCart() {
		homePage.addFirstSkuToCart(myPkg, false);
		Logger.step("From Home Page, Add \"" + myPkg.getName()+"\" to myself Cart");
		Logger.substep("Select \"Myself\" in the Shipto list");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"Checkout\" or \"View My Cart\" button");
		homePage.checkOut();
	}

	private void verifyThatAddedSKUMovedToRecipientCart(SKU sku) {
		Logger.verify("Verify item \"" + sku.getName() + "\" exists in " + sku.getRecipient().getValue() + "'s\" Cart");
		assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item \"" + sku.getId() + "\" does not exist in " + sku.getRecipient() + "'s Cart");
		Logger.verify("Verify item \"" + sku.getName() + "\" does not exists in MyCart");
		assertFalse(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, sku.getId()), "Item \"" + sku.getId() + "\" exists in " + Recipient.MYSELF + "'s Cart");
	}
}