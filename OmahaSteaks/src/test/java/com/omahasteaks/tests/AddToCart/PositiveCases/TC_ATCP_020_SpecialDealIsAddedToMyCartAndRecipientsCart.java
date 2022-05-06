package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_020_SpecialDealIsAddedToMyCartAndRecipientsCart extends TestBase_2SC {
	@Inject
	ListSKUs lstMyCart;
	@Inject
	ListSKUs lstThaoNhoCart;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	Item myItem;
	@Inject
	Item ThaoNhoItem;
	@Inject
	Package recipientItem;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_ATCP_020_Special_Deal_Is_Added_To_My_Cart_And_Recipients_Cart() {
		Logger.tc("TC_ATCP_020  Special Deal Is Added To My Cart And Recipients Cart");
		initTestCaseData();

		Logger.to("TO_ATCP_27	Add same item to MyCart and Recipients Cart");
		Logger.to("TO_ATCP_30	Add \"Special Deal Unlocked\" item in \"Added To Cart\" modal");
		searchAndAddSkuToRecipientsCart(ThaoNhoItem); 
		searchAndAddSkuToMyCart(myItem);
		verifyThatSpecialDealExistsInMyCart(lstMyCart);
		verifyThatSpecialDealDoesNotExistsInRecipientsCart(lstThaoNhoCart);
	}

	private void searchAndAddSkuToMyCart(Item item) {
		Logger.step("Add item and special deal to my cart and proceed to cart page");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(item.getId()) + "\"");
		generalPage.search(item.getId());
		Logger.substep("In Product Page, Leave \"Ship To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(item, false);
		Logger.substep("If Special Deal Offer appears, click \"Add To Cart\" button");
		generalPage.addSpecialDealUnlocked(true);
		generalPage.getAddedToCartSKUList(lstMyCart);
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		productPage.checkOut();
	}

	
	private void searchAndAddSkuToRecipientsCart(Item item) {
		Logger.step("Add item and special deal to recipients cart and proceed to cart page");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(item.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(item.getId()));
		Logger.substep("In Product Page, Select \"New recipient\" and enter \"" + item.getRecipient().getValue() + "\" as the name");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(item, false);
		Logger.substep("If Special Deal Offer appears, click \"Add To Cart\" button");
		generalPage.addSpecialDealUnlocked(true);
		generalPage.getAddedToCartSKUList(lstThaoNhoCart);
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		productPage.checkOut();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
 
	private void initTestCaseData() {
		lstMyCart.initEmpty();
		lstThaoNhoCart.initEmpty();
		myItem.initRandom(Recipient.MYSELF);
		recipientItem.initRandom(Recipient.KIM_ANH);
 		ThaoNhoItem.initRandom(Recipient.THAO_NHO); 
 	}

	private void verifyThatSpecialDealExistsInMyCart(ListSKUs cart) {
		Logger.info("Verify that special deal exists in My Cart on the shopping cart page");
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " is not displayed as expected");
		}
	}


	private void verifyThatSpecialDealDoesNotExistsInRecipientsCart(ListSKUs cart) {
		Logger.info("Verify that special deal exists in recipients Cart on the shopping cart page");
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " is not displayed as expected");
		}
	}
	
	
}
