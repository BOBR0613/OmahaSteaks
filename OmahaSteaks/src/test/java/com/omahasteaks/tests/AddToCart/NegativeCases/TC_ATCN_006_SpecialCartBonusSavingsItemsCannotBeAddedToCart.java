package com.omahasteaks.tests.AddToCart.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCN_006_SpecialCartBonusSavingsItemsCannotBeAddedToCart extends TestBase_2SC {
	@Inject
	SKU mySpecialCartBonus,recipSpecialCartBonus;

	@Inject
	Item myItem;

	@Inject
	Item dropshipItem;

	@Inject
	ListSKUs myCart;

	@Inject
	ListSKUs recipientCart;

	@Inject
	Package packageRecipient;

	@Inject
	GeneralPage generalPage;

	@Inject
	ProductPage productPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_ATCN_006_Special_Cart_Bonus_Savings_Items_Cannot_Be_Added_To_Cart() {
		Logger.tc("TC_ATCN_006	Special Cart Bonus Savings Items Cannot Be Added To Cart containing dropships or gift cards");
		initTestCaseData();

		Logger.to("TO_ATCN_14	In Shopping Cart page - Special Cart Bonus Savings cannot be added to the cart which includes Dropship items");
		searchAndAddSku(myItem);
		searchAndAddDropshipSku(dropshipItem);
		Logger.step("In Shopping Cart page: Click \"Special Cart Bonus Savings\" to all carts.");
		shoppingCartPage.addSpecialCartBonus(Recipient.ALL_CARTS);
		verifySpecialCartBonusSavingsNotDeliveryErrorMessage(dropshipItem);
		getBonusSavingsSkuInfoFromAddedToCartModal();
		verifyThatAddedItemsDisplayCorrectly(myCart);
		verifyThatAddedItemsAreNotInCart(recipientCart);
 	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void getBonusSavingsSkuInfoFromAddedToCartModal() {
		mySpecialCartBonus.setRecipient(Recipient.MYSELF);
		mySpecialCartBonus.setId("44000XA");
		mySpecialCartBonus.setName("Buy All 3 and Save");
		myCart.add(mySpecialCartBonus);
		
		recipSpecialCartBonus.setRecipient(Recipient.DD);
		recipSpecialCartBonus.setId("44000XA");
		recipSpecialCartBonus.setName("Buy All 3 and Save");
 		recipientCart.add(recipSpecialCartBonus);
		 
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
 		for (SKU sku : cart.getList()) {
 		   Logger.verify("Assert Item \"" + sku.getId() + "\" exists in \"" + sku.getRecipient().getValue() + "'s\" cart\"");
 			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "Item " + sku.getId() + " does not exist in " + sku.getRecipient().getValue() + "'s cart");
		}
	}

	private void verifyThatAddedItemsAreNotInCart(ListSKUs cart) {
 		for (SKU sku : cart.getList()) {
  		   Logger.verify("Assert Item \"" + sku.getId() + "\" doesnt exists in \"" + sku.getRecipient().getValue() + "'s\" cart\"");
  		   assertFalse(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), " - The " + sku.getName() + "(" + sku.getId() + ") is displayed.");
		}
	}

	private void verifySpecialCartBonusSavingsNotDeliveryErrorMessage(SKU sku) {
		String actualMessageOnPopup = shoppingCartPage.getMessageOnPopupAndClose();
		String expectedMessage = String.format(Messages.SPECIAL_CART_BONUS_SAVINGS_NOT_DELIVER_ERROR_MESSAGE.get(Constants.SITE), sku.getRecipient().getValue());
		Logger.verify("Verify the warning message displays \"" + expectedMessage + "\"");
		assertEquals(actualMessageOnPopup, expectedMessage);
	}

	private void initTestCaseData() {
		myItem.init(SkuType.PACKAGES,Recipient.MYSELF);
		dropshipItem.initRandom(Recipient.DD, true);
		myCart.initEmpty();
		recipientCart.initEmpty();
	}

	private void searchAndAddSku(SKU sku) {
		Logger.step("Search and add a Normal Food item to MySelf Cart e.g. \"" + sku.getId() + "\"");
		Logger.substep("In Product Page, Leave \"Ship To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"Continue Shopping\" in Added To Cart modal");
		generalPage.search(Common.getNumberFromText(sku.getId()));
  		productPage.addSKUToCart(sku, false);
		generalPage.continueShopping();
	}

	private void searchAndAddDropshipSku(SKU sku) {
		Logger.step("Search and add a Dropship SKU to Recipient's Cart e.g. \"" + sku.getId() + "\"");
		Logger.substep("In Product Page, Select \"Ship To Recipient\" and ship to \"" + sku.getRecipient().getValue() + "\"");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
 		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
 		generalPage.checkOut();
	}
	
	
}
