package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject; 
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SideMenuItem; 
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CustomPackagePage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC; 
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_004_AddCustomPackagesToRecipientAndInShoppingCartSendItToMyself extends TestBase_2SC {
	@Inject
	SKU sku;
	@Inject
	GeneralPage generalPage;
	@Inject
	CustomPackagePage CustomPackagePage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_ATCP_004_Add_Custom_Packages_To_Recipient_And_In_Shopping_Cart_Send_It_To_Myself() {

		Logger.tc("TC_ATCP_004 	Add Custom Packages To Recipient And In Shopping Cart Send It To Myself");
		initTestCaseData();

		Logger.to("TO_ATCP_23	Add Custom Package to Shopping Cart");
		searchAndCreateCustomPackage();
		verifyThatAddedItemsDisplayCorrectly();
		
		Logger.to("TO_ATCP_37	In Shopping Cart, Send SKU from a Recipient to Myself");
		sendCartFromRecipientToMyself();
		verifyThatAddedItemsDisplayCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void initTestCaseData() {
		sku.initByName(Recipient.THAO_NHO);
	}

	private void searchAndCreateCustomPackage() {
		Logger.step("Click on the \"" + SideMenuItem.CUSTOM_PACKAGE.getValue() + "\" menu link on the home page");
		Logger.step("Add the first Custom Package to the cart"); 
		generalPage.clickSideMenuItemLink(SideMenuItem.CUSTOM_PACKAGE);

		Logger.substep("Click on \"Build Your Package\" button on the first item");
 		Logger.substep("Select first item in each group");
		Logger.substep("Select \"Ship To Someone Else\"");
		Logger.substep("Enter the Recipient's name 1 (e.g. " + sku.getRecipient().getValue() +")");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
 		CustomPackagePage.addFirstSKUToCart(sku,false); 
 		
		Logger.substep("Click the \"Checkout\" button");
		CustomPackagePage.checkOut();
	}

	
	private void sendCartFromRecipientToMyself() {
		Logger.step("From Carts section of the Shopping cart page - Select \"Myself\" from \"Send To\" ComboBox");
		shoppingCartPage.sendCartTo(Recipient.THAO_NHO, Recipient.MYSELF);
		shoppingCartPage.waitForLoadingIconInvisible();  
		sku.setRecipient(Recipient.MYSELF);
	}

	
	private void verifyThatAddedItemsDisplayCorrectly() {  
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item \"" + sku.getId() + "\" does not exist in "  + sku.getRecipient().getValue() + "'s cart.");
 	}
}
