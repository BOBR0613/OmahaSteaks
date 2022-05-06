package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_015_AddToCartFunctionWorksOnMultipleTabs extends TestBase_2SC {
	@Inject
	Item myItem;
	@Inject
	GeneralPage generalPage;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_ATCP_015_Add_To_Cart_Function_Works_On_Multiple_Tabs() {
		initTestCaseData();

		searchAndAddItem(myItem, false, false);

		verifyThatAddedItemDisplayCorrectly(myItem);

		openAndSwitchToNewTab();

		goToMyCart();

		verifyThatAddedItemDisplayCorrectly(myItem);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void goToMyCart() {
		Logger.info("In Homepage, click \"My Cart\" link in Top menu");
		DriverUtils.navigate(Constants.runningURL.get(Constants.SITE));
		generalPage.goToMyCart();
	}

	private void openAndSwitchToNewTab() {
		Logger.info("Open the new tab");
		Common.openAndSwitchToNewTab();
	}

	private void initTestCaseData() {
		Logger.tc("TC_ATCP_015	Add To Cart function works on multiple tabs");
		Logger.to("TO_ATCP_49	Add To Cart function works on multiple tabs");
		myItem.init(SkuType.PACKAGES,Recipient.MYSELF);
	}

	private void searchAndAddItem(Item item, boolean isExclusive, boolean isRandom) {
		Logger.info("From Homepage, enter \"" + item.getName() + "\" into Search textbox and click Search button");
		Logger.info("Select the first item to go to product page");
		generalPage.search(item.getName());
		searchResultPage.selectFirstItem();
		Logger.info("Add item to cart: " + item.getName() + "\n - In \"Select Size & Count\" dropdown, select a random Size, then select a random Count .\n" + " - In the second dropdown, select \"Ship To " + item.getRecipient().getValue() + "\"\n" + " - Click \"ADD TO CART\" button\"\n");
		productPage.addItemToCart(item, isExclusive, isRandom);
		productPage.checkOut();
	}

	public void openMyCartInNewTab() {
		Logger.info("In Shopping Cart page, click \"My Cart\" link in Top menu");
		Common.openAndSwitchToNewTab();
		DriverUtils.navigate(Constants.runningURL.get(Constants.SITE));
		generalPage.goToMyCart();
	}

	private void verifyThatAddedItemDisplayCorrectly(Item item) { 
		Logger.verify("Verify that the added item with correct information exist in Shopping Cart Page");
		assertTrue(shoppingCartPage.isItemAddedToCart(item), "The item is not displayed as expected");
	}
}
