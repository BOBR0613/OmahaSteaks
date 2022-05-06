package com.omahasteaks.tests.EndToEndWF_2Step;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SC_SMK_005_RemoveFromMyShoppingCart extends TestBase_SMK {
    @Test
    public void TC_2SC_SMK_005_Remove_From_My_Shopping_Cart() {
	Logger.tc("TC_2SC_SMK_005 Remove From My Shopping Cart");

	initTestCaseData(); 
	searchAndAddItem(myItem, true); 
	goToMyCart(); 
	cancelRemovingMainSKU(); 
	verifyThatMainSKUAndUpsellSKUAreRemainedInShoppingCart();

	removeMainSKU();

	verifySKUsAreRemoved();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void verifyThatMainSKUAndUpsellSKUAreRemainedInShoppingCart() {
	for (SKU sku : myCart.getList()) {
	    Logger.verify("Verify that: - SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is remained in " + sku.getRecipient().getValue() + "'s cart.");
	    assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
	}
    }

	@Override
	protected void initTestCaseData() {
		myCart.initEmpty(); 
		billingAddress = lstAddresses.getRandomBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		myItem.init(SkuType.FREESHIP, Recipient.MYSELF); 
	}
	
	
    private void cancelRemovingMainSKU() {
	Logger.info("Remove Main SKU from Shopping Cart:\n" + " - Select added SKU\n" + " - Click \"Remove\" link\n" + " - Click \"Yes\" link\n" + " - In popup displays, click \"Cancel \"");
	shoppingCartPage.removeItem(myItem, false);
    }

    private void removeMainSKU() {
	Logger.info("Remove Main SKU from Shopping Cart:\n" + " - Select added SKU\n" + " - Click \"Remove\" link\n" + " - Click \"Yes\" link\n" + " - In popup displays, click \"CONFIRM UPDATE & CONTINUE\"");
	shoppingCartPage.removeItem(myItem, true);
    }

    private void verifySKUsAreRemoved() {
	Logger.verify("Verify that:\n" + " - Main SKU and upsell SKU are removed from Shopping Cart.\n" + " - a message \"Your Cart Is Currently Empty\" appears in Shoping Cart");
	shoppingCartPage.waitForCartEmpty();
	assertFalse(shoppingCartPage.isItemAddedToCart(myItem), "The item is not deleted as expected");
	assertEquals(shoppingCartPage.getEmptyMessageText().trim(), Constants.EMPTY_MESSAGE, "The empty message is not displayed as expected");
    }
}
