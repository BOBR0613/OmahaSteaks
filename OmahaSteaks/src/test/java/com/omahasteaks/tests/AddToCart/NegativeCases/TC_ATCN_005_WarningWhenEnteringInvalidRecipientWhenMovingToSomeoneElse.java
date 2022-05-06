package com.omahasteaks.tests.AddToCart.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCN_005_WarningWhenEnteringInvalidRecipientWhenMovingToSomeoneElse extends TestBase_2SC {
	@Inject
	Package pkg;
	@Inject
	Package packageRecipient;
	@Inject
	GeneralPage generalPage;
	@Inject
	ProductPage productPage;
	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_ATCN_005_Warning_When_Submitting_Invalid_Data_Into_Move_To_Someone_Else() {
		Logger.tc("TC_ATCN_005  Warning When Entering Invalid Recipient When Moving To Someone Else");
		initTestCaseData();
		searchAndAddpackage(); 
		
		Logger.to("TO_ATCN_11	In \"Send To Someone Else\" popup ,select \"Move to\" and verify warning message is shown when \"Recipient's Name\" is blank");
		Logger.to("TO_ATCN_12	In \"Send To Someone Else\" popup ,select \"Move to\", warning message when submitting a 16 characters-text into \"Recipient's Name\" textbox \n");
		moveSkuToSomeoneElseWithFillingAMore15CharactersTextIntoRecipientName();
		verifyWarningMessageOnPopup();
		
		moveSkuToSomeoneElseButLeaveRecipientNameEmpty();
		verifyMessageEnterRecipientName();
	}

	private void initTestCaseData() {
		pkg.init(SkuType.ITEM, Recipient.MYSELF);
	}

	private void moveSkuToSomeoneElseButLeaveRecipientNameEmpty() {
		Logger.step("In shopping Cart, click on \"Send to someone else\" link");
		Logger.step("In" + "\"Select Someone Else\" popup");
		Logger.substep("Click \"MOVE TO\" radio button");
		Logger.substep("Select \"Someone Else\"");
		Logger.substep("Leave the \"Recipients Name\" textbox empty");
		Logger.substep("Click \"Add To Cart\" button");
		shoppingCartPage.selectMoveToAndSelectShipTo(pkg, Recipient.EMPTY);
	}

	private void moveSkuToSomeoneElseWithFillingAMore15CharactersTextIntoRecipientName() {
		Logger.step("Enter \"" + Recipient.MORE_15_CHARACTER.getValue() + "\" into the \"Recipient's Name\" textbox and then Click \"MOVE TO CART\" button");
		shoppingCartPage.selectMoveToAndSelectShipTo(pkg, Recipient.MORE_15_CHARACTER);
	}

	private void searchAndAddpackage() { 
		Logger.step("Add item to cart and proceed to the cart page.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(pkg.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(pkg.getId()));
		Logger.substep("In Product Page, Leave \"Shipt To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(pkg, false);
		productPage.checkOut(); 
	}

	private void verifyMessageEnterRecipientName() {
		Logger.verify("The warning message displays \"" + "Please Select Your Recipient" + "\"");
		assertEquals(shoppingCartPage.getRecipientWarningMessage(), "Please Enter Your Recipient's Name");
	}

	private void verifyWarningMessageOnPopup() {
		Logger.verify("The warning message displays \"" + Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE_ON_POPUP + "\"");
		assertEquals(shoppingCartPage.getMessageOnPopupAndClose(), Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE_ON_POPUP);
		shoppingCartPage.waitForLoadingIconInvisible();
	}
}
