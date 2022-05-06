package com.omahasteaks.tests.AddToCart.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCN_004_WarningWhenSubmittingInvalidDataIntoAddToSomeoneElse extends TestBase_2SC {
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
	public void TC_ATCN_004_Warning_When_Submitting_Invalid_Data_Into_Add_To_Someone_Else() {
		Logger.tc("TC_ATCN_004  Warning when submitting invalid data into Recipient Textbox in \"Send To Someone Else\" popup at Add To option\n");
		initTestCaseData();

		Logger.to("TO_ATCN_09	In \"Send To Someone Else\" popup ,select \"Add to\", warning message when submitting a 16 characters-text into \"Recipient's Name\" textbox \n");
		searchAndAddpackage();
		addSkuToSomeoneElseWithFillingAMore15CharactersTextIntoRecipientName();
		verifyWarningMessageOnPopup();

		Logger.to("TO_ATCN_08	In \"Send To Someone Else\" popup ,select \"Add to\", warning message when submitting empty into \"Recipient's Name\" textbox \n");
		addSkuToSomeoneElseButLeaveRecipientNameEmpty();
		verifyMessageEnterRecipientName();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void addSkuToSomeoneElseButLeaveRecipientNameEmpty() {
		Common.waitForDOMChange();
		Logger.step("In shopping Cart, click on \"Send to someone else\" link");
		Logger.step("In \"Send To Someone Else\" popup:");
		Logger.substep("Select \"Add To\" radio button");
		Logger.substep("Select \"Someone Else\"");
		Logger.substep("Leave the \"Recipient's Name\" textbox empty and click \"ADD TO CART\" button");
		shoppingCartPage.selectAddToAndSelectShipTo(pkg, Recipient.EMPTY);
	}

	private void addSkuToSomeoneElseWithFillingAMore15CharactersTextIntoRecipientName() {
		Common.waitForDOMChange();
		Logger.step("Enter \"" + Recipient.MORE_15_CHARACTER.getValue() + "\" into the \"Recipient's Name\" textbox and click \"ADD TO CART\" button");
		shoppingCartPage.selectAddToAndSelectShipTo(pkg, Recipient.MORE_15_CHARACTER);
	}

	private void initTestCaseData() {
		pkg.initRandom(Recipient.MYSELF);
	}

	private void searchAndAddpackage() {
		Logger.step("Add item to cart and proceed to the cart page.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(pkg.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(pkg.getId()));
		Logger.substep("In Product Page, Leave \"Shipt To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(pkg, false);
		productPage.checkOut();
	}

	private void verifyMessageEnterRecipientName() {
		Common.waitForDOMChange();
		Logger.verify("The warning message displays \"" + Messages.INVALID_RECIPIENT_MESSAGE2 + "\"");
		assertEquals(shoppingCartPage.getRecipientWarningMessage(), Messages.INVALID_RECIPIENT_MESSAGE2);
	}

	private void verifyWarningMessageOnPopup() {
		Common.waitForDOMChange();
		Logger.verify("The warning message displays \"" + Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE_ON_POPUP + "\"");
		assertEquals(shoppingCartPage.getMessageOnPopupAndClose(), Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE_ON_POPUP);
		shoppingCartPage.waitForLoadingIconInvisible();
	}
}
