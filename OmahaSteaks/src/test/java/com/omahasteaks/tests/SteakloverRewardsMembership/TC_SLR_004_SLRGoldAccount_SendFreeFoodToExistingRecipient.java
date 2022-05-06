package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_004_SLRGoldAccount_SendFreeFoodToExistingRecipient extends TestBase_SLR {

	String ptsSKU, ptsSLR;

	@Inject
	SKU recipientSKU, slrSKU, mySKU;

	@Test
	public void TC_SLR_004_SLR_Gold_Account_Send_Free_Food_To_Existing_Recipient() {
		initTestCaseData();

		signIn(account);

		searchAndAddSKUToCart(mySKU);

		searchAndAddSKUToCart(recipientSKU);

		clickAddToCartButtonWithoutAnyFreeFoodSelected();

		verifyChooseYourRewardsIsHighligted();

		selectFirstSKUInSelectYourSteakloverRewardsModal();

		verifyValueOfAvailablePointDisplaysCorrectly();

//		verifyTheFreeFoodIsDisable();

		sendFreeFoodToExistingRecipient();

		verifySteakloverRewardsSKUDisplaysInRecipientCart();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	
	private void sendFreeFoodToExistingRecipient() {
		Logger.info("In \"Select Steaklover Rewards popup\", select existing recipient");
		shoppingCartPage.selectRecipientInSeletecSteakloverRewardsModal(Recipient.THONG_NGUYEN);
		clickAddToCartButtonInSelectYourSteakloverRewardsPopup();
		closeSteakloverRewardsModal();
	}

	private void verifySteakloverRewardsSKUDisplaysInRecipientCart() {
		Logger.verify("Verify the free sku is displayed in an existing recipient's cart after selected free sku in \"Select Steaklover Rewards popup by Gold account");
		assertTrue(shoppingCartPage.isFreeFoodAddedByName(Recipient.THONG_NGUYEN, slrSKU.getName()), "The free food is displayed incorrectly after the SLR gold user adds one of free food from the  \"Select Your Steaklover Rewards!\" popup to the cart");
		assertEquals(shoppingCartPage.getPriceOfFreeFood(slrSKU), slrSKU.getPrice(), "The price of monthly free food is displayed correctly in the Mycart section");
		assertEquals(shoppingCartPage.getTheQuantityOfFreeFood(slrSKU), Integer.toString(slrSKU.getQuantity()), "The quantity of monthly free food is displayed correctly in the Recipient's cart section");		
	}

 
	private void verifyValueOfAvailablePointDisplaysCorrectly() {
		Logger.verify("In \"Select Steaklover Rewards popup\", verify value of available points is displayed correctly after selecting a free food");
		assertEquals(shoppingCartPage.getAvailablePoints(), String.valueOf(Integer.parseInt(ptsSLR) - Integer.parseInt(ptsSKU)), "Value of available points is displayed incorrectly after selecting a free food");
	}

	private void selectFirstSKUInSelectYourSteakloverRewardsModal() {
		Logger.info("In \"Select Steaklover Rewards popup\", select first SKU");
		slrSKU.setName(shoppingCartPage.getSKUNameInSelectSteakloverRewardsModal());
		slrSKU.setRecipient(Recipient.THONG_NGUYEN);
		ptsSKU = shoppingCartPage.getSKUPointInSelectSteakloverRewardsModal();
		shoppingCartPage.selectFirstSKUInSelectSteakloverRewards();
	}

	private void verifyChooseYourRewardsIsHighligted() {
		Logger.verify("In the \"Select Your Steaklover Rewards!\" popup, verify the span which contains \"Choose Your Rewards\" is highlighted when user do not select any free food and click \"ADD TO CART\" button");
		assertTrue(shoppingCartPage.isChooseYourRewardsHighlighted(), "The span which contains \"Choose Your Rewards\" does not highlight");
	}

	private void clickAddToCartButtonInSelectYourSteakloverRewardsPopup() {
		Logger.info("In \"Select Steaklover Rewards popup\", click \"Add to cart\" button ");
		shoppingCartPage.clickAddToCartInSelectYourSlrRewardsModal();
	}

	private void clickAddToCartButtonWithoutAnyFreeFoodSelected() {
		ptsSLR = shoppingCartPage.getYourBalancePoint();
		showSelectYourSteakloverRewards();
		clickAddToCartButtonInSelectYourSteakloverRewardsPopup();
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_004 SLR Gold Account - The list free food is disable when value of available points not enough after sending to existing recipient");
		Logger.to("TO_SLR_010	In the \"Select Your Steaklover Rewards!\" popup, verify that value of available points is displayed correctly after selecting a free food");
		Logger.to("TO_SLR_011	In the \"Select Your Steaklover Rewards!\" popup, verify the free food is disable when steaklover rewards user don't have enough points to redeem");
		Logger.to("TO_SLR_012	In the \"Select Your Steaklover Rewards!\" popup, verify the span which contains \"Choose Your Rewards\" is highlighted when user do not select any free food and click \"ADD TO CART\" button");
		Logger.to("TO_SLR_013	In the \"Select Your Steaklover Rewards!\" popup, verify the gold user can send free food to an existing recipient");
		mySKU.init(SkuType.PACKAGES, Recipient.MYSELF);
		recipientSKU.init(SkuType.ITEM, Recipient.THONG_NGUYEN);
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		
		//Init data for free food
		slrSKU.setQuantity(1);
		slrSKU.setPrice(0.00);
		Common.modalDialog.closeSavorDialog();
	}

}
