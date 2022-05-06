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

public class TC_ATCN_002_WarningWhenUncheckingAllItemsInSPecialCartBonusSavings extends TestBase_2SC {
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
	public void TC_ATCN_002_Warning_When_Unchecking_All_Items_In_Special_Cart_Bonus_Savings() {
		Logger.tc("TC_ATCN_002	Warning message when unchecking all items in \"Special Cart Bonus Savings\" and clicking \"Add To Cart\" button");
		initTestCaseData();

		Logger.to("TO_ATCN_04	Warning message when unchecking  all items in \"Special Cart Bonus Savings\" and clicking \"Add To Cart\" button");
		searchAndAddpackage();
		uncheckAllItemAndAddSpecialCartBonus();
		verifyErrorMessageDisplay();
	}

	private void initTestCaseData() {
		pkg.initRandom(Recipient.MYSELF);
	}

	private void searchAndAddpackage() {
		Logger.step("Add item to cart and proceed to the cart page.");
		Logger.substep("Search for an item by id e.g. \"" + Common.getNumberFromText(pkg.getId()) + "\"");
		generalPage.search(Common.getNumberFromText(pkg.getId()));
		Logger.substep("In Product Page, Leave \"Ship To Myself\" as default");
		Logger.substep("Click \"ADD TO CART\" button");
		Logger.substep("If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
		Logger.substep("Click \"CHECKOUT\" or \"VIEW MY CART\" button");
		Common.modalDialog.closeSavorDialog();
		productPage.addSKUToCart(pkg, false);
		productPage.checkOut();
	}

	private void uncheckAllItemAndAddSpecialCartBonus() {
		Logger.step("Uncheck all checkboxes from the \"Special Cart Bonus Savings\" section of the Shopping-Cart page");
		Logger.substep("Uncheck all checkbox");
		Logger.substep("Select \"Myself\" in the dropdown");
		Logger.substep("Click \"Add To Cart\" button");
		shoppingCartPage.uncheckAllItemAndAddSpecialCartBonus(Recipient.MYSELF);
	}

	private void verifyErrorMessageDisplay() {
		Logger.verify("The error message displays \"" + Messages.UNCHECK_ALL_WARNING_MESSAGE_IN_SPECIAL_CART + "\"");
		assertEquals(shoppingCartPage.getErrorMessageInSpecialCartBonus(), Messages.UNCHECK_ALL_WARNING_MESSAGE_IN_SPECIAL_CART);
	}
	
	
}
