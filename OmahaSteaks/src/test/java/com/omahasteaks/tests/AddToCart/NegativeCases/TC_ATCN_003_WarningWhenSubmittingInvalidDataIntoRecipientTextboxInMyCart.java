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

public class TC_ATCN_003_WarningWhenSubmittingInvalidDataIntoRecipientTextboxInMyCart extends TestBase_2SC {
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
	public void TC_ATCN_003_Warning_When_Submitting_Invalid_Data_Into_Recipient_Textbox_In_My_Cart() {
		Logger.tc("TC_ATCN_003	Warning when submitting invalid data into Recipient Textbox in My Cart");
		initTestCaseData();

		Logger.to("TO_ATCN_06	In My Cart, in \"Send To\" section, warning message when submitting a 16 characters-text into \"Recipient's Name\" textbox");
		searchAndAddpackage();
		sendSkuToSomeoneElseWithFillingAMore15CharactersTextIntoRecipientName();
		verifyWarningMessageOnPage();
	}

	private void initTestCaseData() {
		pkg.init("Private Reserve Ribeye Crown Steaks",Recipient.MYSELF);
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

	public void sendSkuToSomeoneElseButLeaveRecipientNameEmpty() {
		Logger.step("In \"Send To\" section:");
		Logger.substep("Select \"Someone Else\" and Leave the \"Recipient's Name\" textbox empty");
		Logger.substep("Click \"Add To Cart\" button");
		shoppingCartPage.sendCartTo(Recipient.MYSELF, Recipient.EMPTY);
	}

	private void sendSkuToSomeoneElseWithFillingAMore15CharactersTextIntoRecipientName() {
		Logger.step("In \"Send To\" section:");
		Logger.substep("Select \"Someone Else\"");
		Logger.substep("Enter \"" + Recipient.MORE_15_CHARACTER.getValue() + "\" as the recipient");
		Logger.substep("Click \"Add To Cart\" button");
		shoppingCartPage.sendCartTo(Recipient.MYSELF, Recipient.MORE_15_CHARACTER);
	}


	public void verifyWarningMessageOnPage() {
		Logger.verify("The warning message displays \"" + Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE + "\"");
		assertEquals(shoppingCartPage.getErrorMessageOnPage(), Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE);
	}
}
