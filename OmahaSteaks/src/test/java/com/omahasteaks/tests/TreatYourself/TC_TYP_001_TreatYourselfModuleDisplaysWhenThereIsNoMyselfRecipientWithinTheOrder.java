package com.omahasteaks.tests.TreatYourself;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_TYP_001_TreatYourselfModuleDisplaysWhenThereIsNoMyselfRecipientWithinTheOrder extends TestBase_2SC {

	@Inject
	ListSKUs myCart;

	@Inject
	Item myItem, recipientItem, treatYourselfItem;

	@Inject
	GeneralPage generalPage;

	@Inject
	ProductPage productPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_TYP_001_Treat_Yourself_Module_Displays_When_There_Is_No_Myself_Recipient_Within_The_Order() {

		initTestCaseData();

		searchAndAddItem(recipientItem);

		verifyTreatYourselfModuleDisplays();

		changefromRecipientCarttoMyselfCart();

		verifyTreatYourselfModuleDoesNotDisplay();

		addTreatYourselfSKUToCart();

		verifySendToSomeoneElselinkDoesNotExistWithTreatYourselfSKU();

		int cartNumber = myCart.size();

		removeTreatYourselfSsku();

		verifyTreatYourselfSkuIsRemovedAndNumberOfCartReduces(cartNumber);

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	public void verifyTreatYourselfSkuIsRemovedAndNumberOfCartReduces(int cartNumber) {
		Logger.verify("Verify that:" + " - SKU from Treat Yourself is removed ");
		assertFalse(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, treatYourselfItem.getId()), "The SKU from Treat Yourself is not deleted as expected");

		Logger.verify("In My Cart section:" + "Verify Cart count (number above \"My Cart\" icon) is reduced one");
		assertEquals(generalPage.getCartNumber(), cartNumber - 1, "The number of cart item is not increased as expected");
	}

	public void removeTreatYourselfSsku() {
		Logger.info("In Myself Cart section:" + " - Remove the “Treat Yourself�? SKU from Myself Cart section");
		shoppingCartPage.removeSkuById(treatYourselfItem, true);
	}

	public void addTreatYourselfSKUToCart() {
		Logger.info("In Shopping Cart page:" + "Select \"Send to: Someone else\"");
		shoppingCartPage.sendCartTo(Recipient.MYSELF, Recipient.THONG_NGUYEN);

		Logger.info("In Treat Yourself module:" + "- Click \"Add to cart\" button");
		treatYourselfItem.setId(shoppingCartPage.getSkuID_TreatYourself());
		treatYourselfItem.setRecipient(Recipient.MYSELF);
		shoppingCartPage.addTreatYourself();
		shoppingCartPage.selectExclusiveOffer(false);
		generalPage.getAddedToCartSKUList(myCart);
		generalPage.checkOut();
	}

	public void verifySendToSomeoneElselinkDoesNotExistWithTreatYourselfSKU() {
		Logger.verify("In Shopping Cart page:" + "Verify “Send to someone else�? link does not exist with “Treat Yourself�? SKU");
		assertFalse(shoppingCartPage.isLinkSendToSomeOneElseDisplayed(treatYourselfItem), "“Send to someone else�? link does not exist with “Treat Yourself�? SKU");
	}

	public void changefromMyselfCarttoRecipientCart() {
		Logger.info("In Shopping Cart page:" + "Select \"Send to: Someone else\"");
		shoppingCartPage.sendCartTo(Recipient.MYSELF, Recipient.THONG_NGUYEN);
	}

	public void changefromRecipientCarttoMyselfCart() {
		Logger.info("In Shopping Cart page:" + "Select \"Send to: Myself\"");
		shoppingCartPage.sendCartTo(Recipient.THONG_NGUYEN, Recipient.MYSELF);
	}

	public void verifyTreatYourselfModuleDisplays() {
		Logger.verify("In Shopping Cart page:" + "Verify Treat Yourself module displays");
		assertTrue(shoppingCartPage.isTreatYourselfModuleDisplayed(), "Treat Yourself module does not display");
	}

	public void verifyYourCartIsCurrentlyEmptyIsDisplayed() {
		Logger.verify("In Shopping Cart page:" + "Verify that 'Your Cart Is Currently Empty' appears");
		assertEquals(shoppingCartPage.getEmptyMessageText().trim(), Constants.EMPTY_MESSAGE);
	}

	public void removeItemToCart() {
		Logger.info("In Shopping Cart page:" + "- Remove SKU to cart");
		shoppingCartPage.removeItem(recipientItem);
		Common.modalDialog.closeUnknownModalDialog();
	}

	public void verifyTreatYourselfModuleDoesNotDisplay() {
		Logger.verify("In Shopping Cart page:" + "Verify Treat Yourself module does not display");
		Common.waitForDOMChange();
		assertFalse(shoppingCartPage.isTreatYourselfModuleDisplayed(), "Treat Yourself module displays");
	}

	public void searchAndAddItem(SKU sku) {
		Logger.info("From Homepage, search a SKU with id (randomly)" + "In Product Page:" + " - Select: \"Ship To \"" + sku.getRecipient() + " - Click \"ADD TO CART\" button" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"" + "-Click \"Checkout\" button");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		productPage.addSKUToCart(sku, false);
		generalPage.getAddedToCartSKUList(myCart);
		generalPage.checkOut();
	}

	public void initTestCaseData() {
		Logger.tc("TC_TYP_001 - Treat Yourself module displays when there no Myself recipient within the order");
		Logger.to("TO_TYP_01 - In Shopping Cart, Treat Yourself module displays when there no “Myself�? recipient within the order");
		Logger.to("TO_TYP_02 - In Shopping Cart, Treat Yourself module does not display when there is “Myself�? recipient within the order");
		Logger.to("TO_TYP_03 - When there is no “Myself�? recipient within the order, replacing other recipient (ex: Anh Chau) by “Myself�? then “Treat Yourself�? module does not display");
		Logger.to("TO_TYP_04 - With “Treat Yourself�? SKU, “Send to someone else�? link does not exist");
		Logger.to("TO_TYP_08 - When customer removes the “Treat Yourself�? SKU from their Shopping Cart (Myself),  the number shopping cart is reduced one.");
		myItem.initRandom(Recipient.MYSELF);
		recipientItem.initRandom(Recipient.THONG_NGUYEN);
		myCart.initEmpty();
	}

}
