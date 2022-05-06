package com.omahasteaks.tests.SLRGold;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test; 

import com.google.inject.Inject; 
import com.omahasteaks.data.enums.Recipient; 
import com.omahasteaks.data.objects.SKU; 
import com.omahasteaks.page.GeneralPage; 
import com.omahasteaks.page.ShoppingCartPage; 
import com.omahasteaks.tests.SteakloverRewardsMembership.TestBase_SLR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_GLD_004MembershipCanBeAddedOnShipmentsBelowMinThreshold extends TestBase_SLR {

	@Inject
	GeneralPage generalPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	SKU sku;


	@Test
	public void TC_GLD_004Membership_Can_Be_Added_On_Shipments_Below_Min_Threshold() {

		initTestCaseData();

		addDessertItemToCart();

		generalPage.checkOut();

		addGoldMembershipToCart(); 

		assertTrue(shoppingCartPage.joinedSlrGold(), "Error when adding Gold Membership to cart below minimum threshold.");

	}

	public void addDessertItemToCart() {
		Logger.info("Go to Desserts page");
		generalPage.goToDepartmentPage("Desserts");
		Common.modalDialog.closeSavorDialog();
		generalPage.addFirstSKUToCart(sku);
	}


	public void addGoldMembershipToCart() {
		Logger.info("Adding gold membership to cart");
		shoppingCartPage.joinSlrGoldMembershipToCart(); 
	}


	private void initTestCaseData() {
		Logger.tc("TC_GLD_004 - Gold membership can be added on orders below min threshold");
		Logger.to("TO_GLD_01 - Add gold membership to a shipment below the minimum threshold");
		sku.setRecipient(Recipient.MYSELF); 
		Common.modalDialog.closeSavorDialog();
	}

}
