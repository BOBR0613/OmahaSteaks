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

public class TC_ATCP_021_CandyAddedToMyCart extends TestBase_2SC {
	@Inject
	ListSKUs lstMyCart; 
	@Inject
	ListAddresses lstAddresses;
	@Inject
	Item myItem;  
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_ATCP_021_CandyAddedToMyCart() {
		Logger.tc("TC_ATCP_021 Candy Is Added To My Cart");
		initTestCaseData();

 		generalPage.goToCategoryPage("Desserts/Chocolates & Candy");
 		Common.modalDialog.closeSavorDialog();
 		AddSkuToMyCart(myItem);
 		System.out.println("verify");
	}

	private void AddSkuToMyCart(Item item) {
		productPage.addSKUToCart(item, false);
		Logger.substep("If Special Deal Offer appears, click \"Add To Cart\" button");
 		generalPage.getAddedToCartSKUList(lstMyCart);
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		productPage.checkOut();
	}
 

	// ================================================================================
	// Test Case Methods
	// ================================================================================
 
	private void initTestCaseData() {
		lstMyCart.initEmpty(); 
		myItem.initRandom(Recipient.MYSELF);  
 	}

	private void verifyThatSpecialDealExistsInMyCart(ListSKUs cart) {
		Logger.info("Verify that special deal exists in My Cart on the shopping cart page");
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " is not displayed as expected");
		}
	} 
	
}
