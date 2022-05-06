package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_006_SLRGoldAccount_CanRemoveFreeFoodWhichSelectedFromThePopup extends TestBase_SLR {

	@Inject
	SKU strSKU;

	@Inject
	Package mySKU;

	@Test
	public void TC_SLR_006_SLR_Gold_Account_Can_Remove_Free_Food_Which_Selected_From_The_Popup() {
		initTestCaseData();

		signIn(account);

		searchAndAddSKUToCart(mySKU);

		addFreeFoodToCartFromSelectYourSteakloverRewardsPopup();

		verifyThereIsNoEditQuantityComboBox();

		verifyThereIsRemoveLinkOnTheMonthlyFreeFood();

		removeFreeFood();

		verifyFreeFoodIsRemovedSuccessFully();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	
	private void verifyFreeFoodIsRemovedSuccessFully() {
		Logger.verify("Verify the free food which removed is not displayed in the My Cart section");
		assertFalse(shoppingCartPage.isFreeFoodAddedByName(strSKU.getRecipient(), strSKU.getName()), "The " + strSKU.getName() + " free food displays in the Shopping Cart page");
	}

	private void removeFreeFood() {
		Logger.info("In shopping cart page, remove " + strSKU.getName());
		shoppingCartPage.removeFreeFood(strSKU);
	}

	public void addFreeFoodToCartFromSelectYourSteakloverRewardsPopup() {
		showSelectYourSteakloverRewards();
		addFreeFoodToCartFromSelectYourSteakloverRewardsPopup(strSKU);
		closeSteakloverRewardsModal();
	}

	private void verifyThereIsNoEditQuantityComboBox() {
		Logger.verify("In the 'Shopping Cart' page, verify that Steaklover reward gold user cannot edit the quantity of free food which selected from the 'Select Your Steaklover Rewards!' popup");
		assertFalse(shoppingCartPage.isQuantityComboBoxDisplayed(strSKU.getRecipient(), strSKU.getName()), "There quantity ComboBox on the monthly free food");
	}

	private void verifyThereIsRemoveLinkOnTheMonthlyFreeFood() {
		Logger.verify("In the 'Shopping Cart' page, verify that SLR gold user can remove the monthly free food");
		assertTrue(shoppingCartPage.isRemoveLinkInFreeFoodDisplayed(strSKU.getRecipient(), strSKU.getName()), "There is no 'Remove' link on the monthly free food");
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_006 - SLR GOLD user can remove and cannot edit the quantity free food which selected from the 'Select Your Steaklover Rewards!' popup");
		Logger.to("TO_SLR_017 - In the 'Shopping Cart' page, verify that steaklover reward user can remove the free food which selected from the 'Select Your Steaklover Rewards!' popup");
		Logger.to("TO_SLR_018 - In the 'Shopping Cart' page, verify that steaklover reward user cannot edit the quantity of the free food which selected from the 'Select Your Steaklover Rewards!' popup");

		strSKU.setRecipient(Recipient.MYSELF);
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		mySKU.init(SkuType.PACKAGES,Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}
}
