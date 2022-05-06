package com.omahasteaks.tests.SignInLink;
 

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_SI_001_SignInLinkInTheShoppingCartPage extends TestBase_2SC {
	Boolean isGoldMembershipAvailable;
	@Inject
	Item mySKU;

	@Inject
	GeneralPage generalPage;

	@Inject
	ProductPage productPage;

	@Inject
	SearchResultPage searchResultPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	SignInPage signInPage;

	@Test
	public void TC_SI_001_Sign_In_Link_In_The_Shopping_Cart_Page() {

		initTestCaseData();

		searchAndAddItemToCart();

		verifyTheSignInLinkExistsInTheShoppingCartPage();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void verifyTheSignInLinkExistsInTheShoppingCartPage() {
		// assertTrue(shoppingCartPage.isSignInLinkDisplayed(), "The 'Sign In' link does not display on the 'Cart Summary' section in the 'Shopping Cart' page");
		//		isGoldMembershipAvailable = Common.isGoldMembershipAvailable();
		//		if (isGoldMembershipAvailable) {
		//			Logger.verify("Verify the 'Sign In' link is displayed on the 'Gold Membership' section in the Shopping Cart page");
		//			goToSignPage();
		//			verifyTheSignInPageDisplays();
		//		} else {
		//			Logger.verify("Verify the 'Sign In' link does not display on the 'Gold Membership' section in the Shopping Cart page");
		//			assertTrue(shoppingCartPage.isSignInLinkDisplayed(), "The 'Sign In' link doesn't displays on the 'Cart Summary' section in the 'Shopping Cart' page");
		//		}
	}

	protected void searchAndAddItemToCart() {
		Logger.info("From Homepage, enter \"" + mySKU.getName() + "\" into Search textbox and click Search button");
		Logger.info("Select the first item to go to product page");
		generalPage.search(mySKU.getName());
		searchResultPage.selectFirstItem();
		Logger.info("\"Add SKU to " + mySKU.getRecipient() + "\n" + "In Search page, At the first Item:\n" + " - In \"Select Size & Count\" dropdown, select a random Size, then select a random Count .\n" + " - In the second dropdown, select \"Ship To " + mySKU.getRecipient().getValue() + "\"\n" + "- Click \"ADD TO CART\" button\"\n");
		Logger.info("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		productPage.addSKUToCart(mySKU, false);
		Logger.info("In \"Added To Cart\" popup, click \"CHECK OUT\" button");
		Common.waitForPageLoad();
		productPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_SI_001 - 'Sign In' link on the Gold Membership section in the shopping cart page");
		Logger.to("TO_SI_01 - Verify the 'Sign In' link on the Gold Membership section in Shopping Cart page");
		mySKU.init(SkuType.PACKAGES,Recipient.MYSELF);
 	}
}
