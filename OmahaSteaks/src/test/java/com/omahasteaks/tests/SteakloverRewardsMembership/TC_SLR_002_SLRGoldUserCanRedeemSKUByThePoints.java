package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_002_SLRGoldUserCanRedeemSKUByThePoints extends TestBase_SLR {

	String pointTotal, ptsSKU;

	@Inject
	SKU slrSKU;

	@Inject
	Package mySKU;

	@Test
	public void TC_SLR_002_SLR_Gold_User_Can_Redeem_SKU_By_The_Points() {
		initTestCaseData();

		signInWithSteakloverRewardsGoldAccount(account);

		searchAndAddSKUToCart(mySKU);

		verifyRedeemPointLinkDisplays();

		verifyYourBalanceValueIsTheSameAsTheCurrentPointTotal();

		showSelectYourSteakloverRewards();

		Common.delay(Constants.SLEEP_TIME);
		
		verifySelectSteakloverRewardsPopupIsDisplayed();

		addSKUInSelectSteakloverRewardsPopupToCart();

		verifySteakloverRewardsModalDisplays();

		verifyTheAddedSKUInSelectSeakloverRewardsDisplayCorrectlyInSteakloveRewardModal();

		closeSteakloverRewardsModal();

		verifyYourBalanceValueDisplaysCorrectlyAfterAddedSteakloverRewardSKUToCart();

		Common.delay(Constants.SLEEP_TIME);
		
		verifySteakloverRewardsSKUDisplaysInCart();

		checkOutFromShoppingCartPage();
		
		Common.delay(Constants.SLEEP_TIME);

		verifyTheAddedSteakloverRewardsSKUDisplaysInMyCartSection();

		goToPaymentAndReviewPage();
		
		Common.delay(Constants.SLEEP_TIME);

		verifySteakloverRewardsSKUDisplaysInPaymentReviewPage();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void goToPaymentAndReviewPage() {
		Logger.info("In Shipping Address page, click 'Continue' button");
		shippingAddressPage2SC.clickContinueButton();
	}

	private void verifyTheAddedSteakloverRewardsSKUDisplaysInMyCartSection() {
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
			Logger.verify("In Shipping Address pagem verify The added SKU in Steaklover Rewards is displayed in My Cart section");
			slrSKU.setName(Common.getRewardSkuDescription(slrSKU.getName()));
			assertTrue(shippingAddressPage2SC.isSKUExisted(slrSKU), "The added SKU in Steaklover Rewards is not displayed as expected");
		}
	}

	private void verifySteakloverRewardsSKUDisplaysInPaymentReviewPage() {
		Logger.verify("Verify the free food which selected from the 'Select Your Steaklover Rewards!' popup is displayed correctly");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
				slrSKU.setName(Common.getRewardSkuDescription(slrSKU.getName()));
				assertTrue(paymentAndReviewPage2SC.isSKUExisted(slrSKU), "The free food which selected from the 'Select Your Steaklover Rewards!' popup is displayed incorrectly");
		}else {
			slrSKU.setName(slrSKU.getName().split("[)]")[1].trim());
			assertTrue(paymentAndReviewPage2SC.isSKUExisted(slrSKU), "The free food which selected from the 'Select Your Steaklover Rewards!' popup is displayed incorrectly");
			
		}
	}

	private void verifySteakloverRewardsSKUDisplaysInCart() {
		Logger.verify("Verify the free food is displayed correctly after the SLR gold user adds one of free food from the  \"Select Your Steaklover Rewards!\" popup to the cart");
		assertTrue(shoppingCartPage.isFreeFoodAddedByName(Recipient.MYSELF, slrSKU.getName()), "The free food is displayed incorrectly after the SLR gold user adds one of free food from the  \"Select Your Steaklover Rewards!\" popup to the cart");
		slrSKU.setName(Common.getRewardSkuDescription(slrSKU.getName()));
		assertEquals(shoppingCartPage.getTheQuantityOfFreeFood(slrSKU), Integer.toString(slrSKU.getQuantity()), "The price of monthly free food is displayed correctly in the Mycart section");
		assertEquals(shoppingCartPage.getPriceOfFreeFood(slrSKU), slrSKU.getPrice(), "The quantity of monthly free food is displayed correctly in the Mycart section");
	}

	private void verifyYourBalanceValueDisplaysCorrectlyAfterAddedSteakloverRewardSKUToCart() {
		Logger.verify("Verify 'Your balance' value is displayed correctly after adding free food from the 'Select Your Rewards!' popup to cart");
		assertEquals(shoppingCartPage.getYourBalancePoint(), String.valueOf(Integer.parseInt(pointTotal) - Integer.parseInt(ptsSKU)), "'Your balance' value is displayed incorrectly after adding free food from the 'Select Your Rewards!' popup to cart");
	}

	private void verifyTheAddedSKUInSelectSeakloverRewardsDisplayCorrectlyInSteakloveRewardModal() {
		Logger.verify("In Steaklover Rewards popup, verify the SKU which added in Select Steaklover Rewards popup is displayed correctly");
		assertEquals(shoppingCartPage.getAddedSKUNameInSteakloverRewardsModal(), slrSKU.getName(), "the added SKU in Select Steaklover Rewards popup displayed incorrectly");
	}

	private void verifySteakloverRewardsModalDisplays() {
		Logger.verify("Verify Steaklover Rewards popup displays");
		assertTrue(shoppingCartPage.isSteakloverRewardsModalDisplayed(), "Steaklover Rewards popup does not display");
	}

	private void addSKUInSelectSteakloverRewardsPopupToCart() {
		ptsSKU = shoppingCartPage.getSKUPointInSelectSteakloverRewardsModal();
		addFreeFoodToCartFromSelectYourSteakloverRewardsPopup(slrSKU);
	}

	private void verifySelectSteakloverRewardsPopupIsDisplayed() {
		Logger.verify("Verify that the \"Select Your Steaklover Rewards!\" popup is displayed");
		assertTrue(shoppingCartPage.isSelectSteakloverRewardsModalDisplayed(), "the \"Select Your Steaklover Rewards!\" popup does not display");
	}

	private void verifyYourBalanceValueIsTheSameAsTheCurrentPointTotal() {
		Logger.verify("Verify that the 'YOUR BALANCE' value is the same as the 'Current point total' in the 'Steaklover Rewards' of My Account page");
		assertEquals(shoppingCartPage.getYourBalancePoint(), pointTotal, " the 'YOUR BALANCE' value does not the same as the 'Current point total' in the 'Steaklover Rewards' of My Account page");
	}

//	private void verifyRedeemPointLinkDisplays() {
//		Logger.verify("Verify \"Redeem point\" link is displayed in \"Steaklover Rewards\" section");
//		assertTrue(shoppingCartPage.isSteakloverRewardOptionDisplayed(Constants.REDEEM_POINT), "\"Redeem point\" link does not display in \"Steaklover Rewards\" section");
//	}
	
	private void verifyRedeemPointLinkDisplays() {
		Logger.verify("Verify \"Redeem point\" link is displayed in \"Steaklover Rewards\" section");
		assertTrue(shoppingCartPage.isSteakloverRewardOptionDisplayed(Constants.REDEEM_POINT), "\"Redeem point\" link does not display in \"Steaklover Rewards\" section");
	}

	private void signInWithSteakloverRewardsGoldAccount(Account account) {
		signIn(account);
		Logger.info("In My Account page, click Steaklover Rewards option in My Omaha Steaks section");
		myAccountPage.selectMyOmahaSteaksOption(Constants.STEAKLOVER_REWARDS);
		pointTotal = myAccountPage.getSteakLoverRewardsPoint();
	}

	private void initTestCaseData() {
		Logger.tc("TC_SLR_002 The Steaklover Rewards GOLD account can redeem reward SKU by the points");
		Logger.to("TO_SLR_001 - In the 'Shopping Cart' page, verify that the \"REDEEM POINTS\" link is displayed on the \"Steak Lover Rewards\" pane when logging in by the SLR Gold account which has points greater than specified points in the Rewards table in \"My Account/Steaklover Rewards\" page");
		Logger.to("TO_SLR_008 - In the 'Shopping Cart' page, verify that the \"Select Your Steaklover Rewards!\" popup is displayed when clicking on the \"REDEEM POINTS\" link");
		Logger.to("TO_SLR_009 - In the \"Shopping Cart\" page, verify that the 'YOUR BALANCE' value is the same as the 'Current point total' in the 'Home\\ My Account\\Steaklover Rewards' page");
		Logger.to("TO_SLR_014 - In the 'Steaklover Rewards' popup, the used total points and the item's name are the same as the selected item in the \"Select Your Steaklover Rewards' popup");
		Logger.to("TO_SLR_015 - In the 'Shopping Cart' page, after adding one of free food from the \"Select Your Steaklover Rewards!\" popup to the cart, verify that the added free food is displayed correctly");
		Logger.to("TO_SLR_016 - In the 'Shopping Cart' page, verify that  'Your balance' value is displayed correctly after adding free food from the 'Select Your Rewards!' popup to cart");
		Logger.to("TC_SLR_020 - In the 'Shipping Information' page, verify that the free food which selected from the 'Select Your Steaklover Rewards!' popup, is displayed correctly in the 'My Cart' section");
		Logger.to("TO_SLR_022 - In the Payment and Review page, verify that the free food which selected from the 'Select Your Steaklover Rewards!' popup, is displayed correctly on the bottom of the 'total items'");

		mySKU.initRandom(Recipient.MYSELF);
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		slrSKU.setRecipient(Recipient.MYSELF);
		Common.modalDialog.closeSavorDialog();
	}
}
