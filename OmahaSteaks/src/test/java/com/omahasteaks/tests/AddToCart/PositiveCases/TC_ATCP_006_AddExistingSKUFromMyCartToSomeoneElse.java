package com.omahasteaks.tests.AddToCart.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCP_006_AddExistingSKUFromMyCartToSomeoneElse extends TestBase_2SC {
	@Inject
	Package myPkg;
	@Inject
	Package packageRecipient;
	@Inject
	GeneralPage generalPage;
	@Inject
	HomePage homePage;
	@Inject
	ShoppingCartPage shoppingCartPage; 
	
	@Test
	public void TC_ATCP_006_Add_Existing_SKU_From_My_Cart_To_Someone_Else() {
		Logger.tc("TC_ATCP_006 	Add Existing SKU From MyCart To SomeoneElse");
		initTestCaseData();

		Logger.to("TO_ATCP_40	In Shopping Cart, ADD an existing SKU to someone else whose name does not exist in the list");
		addItemFromHomePageToMyCart();
		addToSomeoneElseNotInList();
		verifyThatAddedSKUExistsInRecipientCart();

		Logger.to("TO_ATCP_41	In Shopping Cart, ADD an existing SKU to someone else whose name exists in the list");
		addToSomeoneElseExisting();
		verifyThatAddedSKUExistsInRecipientCartWithQuantityIncreasingBy1();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addToSomeoneElseExisting() {
		Logger.step("In Shopping Cart, Add item from MyCart to someone in the list");
		Logger.substep("On Shopping Cart page, click on \"Send to someone else\" link");
		Logger.substep("Send To Someone Else\" popup: Select \"Thong Nguyen\"");
		Logger.substep("Click \"Add To Cart\" button");
		Logger.substep("Click \"Close\" button");
		shoppingCartPage.addSkuToSomeoneElse(myPkg, Recipient.THONG_NGUYEN);
		myPkg.setQuantity(myPkg.getQuantity() * 2);
	}

	private void addToSomeoneElseNotInList() {
		Logger.step("In Shopping Cart, Add item from MyCart to someone not in the list");
		Logger.substep("In shopping Cart, click on \"Send to someone else\" link");
		Logger.substep("In \"Send To Someone Else\" popup: Select \"Someone Else\"");
		Logger.substep("Enter recipient name \"Thong Nguyen\"");
		Logger.substep("Click \"Add To Cart\" button\"");
		Logger.substep("In \"Added To Cart\" popup, Click \"Close\" button");
		shoppingCartPage.addSkuToSomeoneElse(myPkg, Recipient.THONG_NGUYEN);
		myPkg.setRecipient(Recipient.THONG_NGUYEN);
		myPkg.setQuantity(1);
	}

	private void initTestCaseData() {
		myPkg.init(Recipient.MYSELF);
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

	private void verifyThatAddedSKUExistsInRecipientCart() {
		Logger.verify("Verify that added SKU exists in Recipient Cart (Thong Nguyen)");
		assertTrue(shoppingCartPage.isSkuByIdAdded(myPkg.getRecipient(), myPkg.getId()), "The sku is not displayed as expected");
	}

	private void verifyThatAddedSKUExistsInRecipientCartWithQuantityIncreasingBy1() {
		Logger.verify("Verify that added SKU exists in Recipient Cart (Thong Nguyen) with quantity increasing 1");
		assertEquals(shoppingCartPage.getQuantityOfSku(myPkg), myPkg.getQuantity(), "The quantity is not updated as expected");
	}
	
	
}
