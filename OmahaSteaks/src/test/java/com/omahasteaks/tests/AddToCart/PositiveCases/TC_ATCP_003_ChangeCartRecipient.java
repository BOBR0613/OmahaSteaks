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

public class TC_ATCP_003_ChangeCartRecipient extends TestBase_2SC {
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
	public void TC_ATCP_003_Change_Cart_Recipient() {
		Logger.tc("TC_ATCP_003 	Change Cart Recipient");
		initTestCaseData();

		Logger.to("TO_ATCP_27	Add item to My Cart and Recipients Cart");
		searchAndAddSkuToMyCart(myItem);
		searchAndAddSkuToRecipientsCart(ThaoNhoItem); 

		Logger.to("TO_ATCP_35	Change carts recipient to a recipient not existing in the list");
		ChangeRecipientFromMyselfToKimAhn(Recipient.MYSELF, Recipient.KIM_ANH);
		verifyThatAddedItemsDisplayCorrectly(lstMyCart);
		verifyThatAddedItemsDisplayCorrectly(lstThaoNhoCart);

		Logger.to("TO_ATCP_36	Change carts recipient to a Recipient in the list");
		ChangeRecipientFromKimAhnToMyself(Recipient.KIM_ANH, Recipient.MYSELF);
		verifyThatAddedItemsDisplayCorrectly(lstMyCart);
		verifyThatAddedItemsDisplayCorrectly(lstThaoNhoCart);
		
	}

	private void searchAndAddSkuToMyCart(Item item) {
		Logger.step("Add item to " + item.getRecipient().getValue() + "'s cart.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(item.getId()) + "\"");
		generalPage.search(item.getId());
		Logger.substep("In Product Page, Leave \"Ship To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"Continue Shopping\" button");
 		productPage.addSKUToCart(item, false);
		generalPage.getAddedToCartSKUList(lstMyCart);
		generalPage.continueShopping();
	}

	
	private void searchAndAddSkuToRecipientsCart(Item item) {
		Logger.step("Add item to " + item.getRecipient().getValue() + "'s cart.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(item.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(item.getId()));
		Logger.substep("In Product Page, Select \"New recipient\" and enter \"" + item.getRecipient().getValue() + "\" as the name");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(item, false);
		generalPage.getAddedToCartSKUList(lstThaoNhoCart);
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


	private void ChangeRecipientFromMyselfToKimAhn(Recipient recipient1, Recipient recipient2) {
		Logger.step("Change recipient from \"" + recipient1.getValue() + "\" to \"" + recipient2.getValue() + "\"" );
		Logger.substep("Click on cart header link for \"" + recipient1.getValue() + "\"");
		Logger.substep("On the list of receivers, select \"Someone Else\"");
		Logger.substep("In the receiver name textbox, Enter \"" + recipient2.getValue() + "\"");
		Logger.substep("Click \"Save\" button");
		shoppingCartPage.sendCartTo(recipient1, recipient2);
		shoppingCartPage.waitForLoadingIconInvisible();
		lstMyCart.updateRecipient(recipient1, recipient2);
	}

	private void ChangeRecipientFromKimAhnToMyself(Recipient recipient1, Recipient recipient2) {
		Logger.step("Change recipient from \"" + recipient1.getValue() + "\" to \"" + recipient2.getValue() + "\"" );
		Logger.substep("Click on cart header link for \"" + recipient1.getValue() + "\"");
		Logger.substep("On the list of receivers, select \"" + recipient2.getValue() + "\"");
		Logger.substep("Click \"Save\" button");
		
		shoppingCartPage.sendCartTo(recipient1, recipient2);
		shoppingCartPage.waitForLoadingIconInvisible();
		lstMyCart.updateRecipient(recipient1, recipient2);
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify item \"" + sku.getName() + " (" + sku.getId() + ")\" exists in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " does not exist in " + sku.getRecipient().getValue() + "'s cart");
		}
	}
}
