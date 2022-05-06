package com.omahasteaks.tests.AddToCart.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ATCN_001_WarningWhenSubmittingInvalidDataIntoRecipientTextboxInProductionPage extends TestBase_2SC {
	@Inject
	Package pkg;
	@Inject
	Package packageRecipient;
	@Inject
	GeneralPage generalPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_ATCN_001_Warning_When_Submitting_Invalid_Data_Into_Recipient_Textbox_In_Production_Page() {
		Logger.tc("TC_ATCN_001  Warning When Submitting Invalid Data Into Recipient Textbox In Product Detail Page\n");
		initTestCaseData();

		Logger.to("TO_ATCN_01	Warning message when submitting empty value into \"Recipient's Name\" textbox");
		leaveRecipientNameEmptyAndSelectAddToCart();
		verifyMessageEnterRecipientName();

		Logger.to("TO_ATCN_02	Warning message when submitting a more than 15 characters-text into \"Recipient's Name\" textbox");
		fillAMore15CharactersTextAndSelectAddToCart();
		verifyWarningMessageOnPopup(); 
		
		Logger.to("TO_ATCN_03	Warning message when submitting some spacebar characters into \"Recipient's Name\" textbox");
		fillSomeSpaceCharactersAndSelectAddToCart();
		verifyMessageEnterRecipientName();
	}

	private void fillAMore15CharactersTextAndSelectAddToCart() {
		Logger.step("Enter \"" + Recipient.MORE_15_CHARACTER.getValue() + "\" in  \"Recipient's Name\" textbox then Click \"ADD TO CART\" button");
		pkg.setRecipient(Recipient.MORE_15_CHARACTER);
		productPage.selectShipToAndSelectAddToCart(pkg);
	}

	private void fillSomeSpaceCharactersAndSelectAddToCart() {
		Logger.step("Enter \"" + Recipient.SPACE_CHARACTER.getValue() + "\" in  \"Recipient's Name\" textbox then Click \"ADD TO CART\" button");
		pkg.setRecipient(Recipient.SPACE_CHARACTER);
		productPage.selectShipToAndSelectAddToCart(pkg);
	}

	private void initTestCaseData() {
		pkg.initRandom(Recipient.EMPTY);
	}

	private void leaveRecipientNameEmptyAndSelectAddToCart() {
		Logger.step("Search a SKU with id (e.g. \"" + Common.getNumberFromText(pkg.getId()) + "\")");
		generalPage.search(Common.getNumberFromText(pkg.getId()));
		Logger.step("Select \"Ship To Someone Else\"\n" + " - Leave the \"Recipient's Name\" textbox empty\n" + " - Click \"ADD TO CART\" button");
		productPage.selectShipToAndSelectAddToCart(pkg);
	}

	public void verifyMessageEnterRecipientName() {
		Logger.verify("The warning message displays \"Please Enter Your Recipient's Name\"");
		assertEquals(productPage.getRecipientWarningMessage(), Messages.INVALID_RECIPIENT_MESSAGE);
	}

	private void verifyWarningMessageOnPopup() {
		Logger.verify("The warning message displays \"Recipient name must be 15 characters or less\" on popup");
		assertEquals(productPage.getMessageOnPopupAndClose(), Messages.CHARACTER_LIMITATION_RECIPIENT_MESSAGE_ON_POPUP);
	}
	
}
