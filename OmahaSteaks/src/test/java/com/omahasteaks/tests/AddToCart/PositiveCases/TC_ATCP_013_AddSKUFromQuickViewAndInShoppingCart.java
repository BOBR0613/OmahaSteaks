package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.Category;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListCategories;
import com.omahasteaks.data.ListDepartments;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_013_AddSKUFromQuickViewAndInShoppingCart extends TestBase_2SC {

	@Inject
	ListDepartments lstDepartmnents;
	@Inject
	ListCategories lstCategories;
	@Inject
	Category category;
	@Inject
	ListSKUs myCart;
	@Inject
	ListSKUs KimAnhCart;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	SKU mySku;
	@Inject
	SKU KimAnhSku;
	@Inject
	GeneralPage generalPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	CategoryPage categoryPage;

	@Test
	public void TC_ATCP_013_Add_SKU_From_Quick_View_And_In_Shopping_Cart() {
		initTestCaseData();

		goToWineDepartmentPage();

		addSkuInQuickViewModal(KimAnhSku, KimAnhCart);
		Common.waitForDOMChange();
		addTheUpsellSkuIntoMyself();

		verifyThatAddedItemsDisplayCorrectly(myCart);

		verifyThatAddedItemsDisplayCorrectly(KimAnhCart);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSkuInQuickViewModal(SKU sku, ListSKUs lSku) {
		Logger.info("Add the first SKU and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Checkout\"");
		categoryPage.clickFirstSKUQuickViewLink();
		categoryPage.addQuickViewItemToCart(); 
	//	generalPage.selectExclusiveOffer(true);
		generalPage.getAddedToCartSKUList(lSku);
		generalPage.checkOut();
	}

	private void addTheUpsellSkuIntoMyself() {
		if (!shoppingCartPage.isTheUpsellSkuDisplayed())
			return;
	
		shoppingCartPage.addTheUpsellSku(mySku.getRecipient());
	    shoppingCartPage.getAddedToCartSKUListInShoppingCartPage(myCart,
		mySku.getRecipient()); shoppingCartPage.closeAddedToCartModal();
	}

	private void goToWineDepartmentPage() {
		Logger.info("Go to Wine Department page");
		generalPage.goToDepartmentPage("Wine");
	}

	private void initTestCaseData() {
		Logger.tc("TC_ATCP_013	Add SKU From Quick View And In Shopping Cart\n");
		Logger.to("TO_ATCP_29	Add SKU to Cart from its Quick View\n");
		Logger.to("TO_ATCP_46	In Shopping Cart, add an upsell SKU into My Cart");
		myCart.initEmpty();
		KimAnhCart.initEmpty();
		mySku.setRecipient(Recipient.MYSELF);
		KimAnhSku.setRecipient(Recipient.KIM_ANH); 
	}

	private void verifyThatAddedItemsDisplayCorrectly(ListSKUs cart) {
		Logger.info("Verify that these added items with correct information exist in Shopping Cart Page");
		for (SKU sku : cart.getList()) {
			Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
			assertTrue(shoppingCartPage.isSkuByIdAdded(sku.getRecipient(), sku.getId()), "The sku is not displayed as expected");
		}
	}
}
