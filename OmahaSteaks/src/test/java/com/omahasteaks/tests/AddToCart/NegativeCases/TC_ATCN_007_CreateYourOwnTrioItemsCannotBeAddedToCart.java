package com.omahasteaks.tests.AddToCart.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
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
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCN_007_CreateYourOwnTrioItemsCannotBeAddedToCart extends TestBase_2SC {
	@Inject
	SKU createYourOwnTrio;

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
	public void TC_ATCN_007_Create_Your_Own_Trio_Items_Cannot_Be_Added_To_Cart() {
		Logger.tc("TC_ATCN_007	Create Your Own Trio Items Cannot Be Added To Cart");
		initTestCaseData();

		Logger.to("TO_ATCN_16	In Shopping Cart page - Create Your Own Trio! cannot be added to the cart which includes Dropship items");
		searchAndAddSku(myItem);
		searchAndAddDropshipSku(dropshipItem);
		Common.delay(5);
		shoppingCartPage.addCreateYourOwnTrioItem(createYourOwnTrio);
		verifyCreateYourOwnTrioNotDeliveryErrorMessage(dropshipItem);
		getCreateYourOwnTrioSkuInfoFromAddedToCartModal();
		verifyThatAddedItemsDisplayCorrectly(myCart);
		verifyThatAddedItemsNotExistInCart(recipientCart);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void getCreateYourOwnTrioSkuInfoFromAddedToCartModal() {
		if (!shoppingCartPage.isAddedToCartModalDisplayed()) {
			createYourOwnTrio.setRecipient(Recipient.MYSELF);
			myCart.add(createYourOwnTrio);
			return;
		}
		createYourOwnTrio.setRecipient(Recipient.MYSELF);
		createYourOwnTrio.setId(shoppingCartPage.getAddedSkuIdFromModal());
		createYourOwnTrio.setName(shoppingCartPage.getAddedSkuNameFromModal());
		myCart.add(createYourOwnTrio);
		recipientCart.add(createYourOwnTrio);
		shoppingCartPage.closeAddedToCartModal();
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
		Logger.info("Verify that these added items with correct information exist in Shopping Cart");
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getName()), "The sku is not displayed as expected. (" + sku.getId() + ")");
		}
	}

	private void verifyThatAddedItemsNotExistInCart(ListSKUs cart) {
		Logger.info("Verify that added items don't exist in Cart");
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is not existed in " + sku.getRecipient().getValue() + "'s cart.");
			assertFalse(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getName()), " - The " + sku.getName() + "(" + sku.getId() + ") is displayed.");
		}
	}

	private void verifyCreateYourOwnTrioNotDeliveryErrorMessage(SKU sku) {
		Logger.verify("Verify the warning message displays:\n" + "- The sack for " + sku.getRecipient().getValue() + " does not qualify for the item you are attempting to purchase. Wine products, live lobster and gift cards do not qualify for the purchase of bonus savings items.");
		Common.waitForDOMChange();
		String actualMessageOnPopup = shoppingCartPage.getMessageOnPopupAndClose();
		assertEquals(actualMessageOnPopup, String.format(Messages.SPECIAL_CART_BONUS_SAVINGS_NOT_DELIVER_ERROR_MESSAGE.get(Constants.SITE), sku.getRecipient().getValue()));
	}

	private void initTestCaseData() {
		createYourOwnTrio.setRecipient(Recipient.ALL_CARTS);
		createYourOwnTrio.setQuantity(1);
		createYourOwnTrio.setName("Bonus Trio");
		createYourOwnTrio.setId("#44000XA");  // Special Cart Bonus Savings is created then assigned, #44001; not using the default selection that has value of #44000.
		myItem.initRandom(Recipient.MYSELF);
		dropshipItem.initRandom(Recipient.DD, true);
		myCart.initEmpty();
		recipientCart.initEmpty();
	}

	private void searchAndAddSku(SKU sku) {
		Logger.step("Add item to cart and proceed to the cart page.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(sku.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		Logger.substep("In Product Page, Leave \"Ship To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(sku, false);
		Logger.substep("Click \"Continue Shopping\" button");
		generalPage.continueShopping();
	}

	private void searchAndAddDropshipSku(SKU sku) {
		Logger.step("Add dropship item to cart and proceed to the cart page.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(sku.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		Logger.substep("In Product Page, Leave \"Ship To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(sku, false);
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		generalPage.checkOut();
	}
}
