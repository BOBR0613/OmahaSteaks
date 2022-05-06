package com.omahasteaks.tests.SteakloverRewardsMembership;

import com.google.inject.Inject;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TestBase_SLR extends TestBase_2SC {
	@Inject
	GeneralPage generalPage;

	@Inject
	SignInPage signInPage;

	@Inject
	MyAccountPage myAccountPage;

	@Inject
	ProductPage productPage;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	ConfirmationPage2SC confirmationPage2SC;

	public void signIn(Account account) {
		Logger.info("In Homepage, Click on the Sign In link in the top menu");
		generalPage.goToSignInPage();
		Logger.info("In Sign in page, fill valid account: Email - " + account.getEmail() + " - Password - " + account.getPassword() + " - Click \"Sign in\" button");
		signInPage.signIn(account);
	}

	public void searchAndAddSKUToCart(SKU sku) {
		Logger.info("Search a SKU with id = " + Common.getNumberFromText(sku.getId()) + "\")");
		generalPage.search(Common.getNumberFromText(sku.getId()));
		Logger.info("In Product Page:\n" + "- Select \"Ship To " + sku.getRecipient().getValue() + "\"\n" + "- Click \"ADD TO CART\" button\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- In Add To Cart popup, click CHEKOUT button");
		Common.waitForDOMChange();
		productPage.addSKUToCart(sku, false);
		Common.waitForDOMChange();
		productPage.checkOut();
	}

	public void showSelectYourSteakloverRewards() {
		Logger.info("In Steaklover rewards section, click " + Constants.REDEEM_POINT + " link");
		shoppingCartPage.clickSteakloverRewardOption(Constants.REDEEM_POINT);
	}

	public void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page, click 'Check Out' button");
		shoppingCartPage.checkOut();
		Common.waitForPageLoad();
	}

	public void closeSteakloverRewardsModal() {
		Logger.info("In Steaklover Rewards popup, click 'Close' button");
		shoppingCartPage.closeModal();
	}

	public void createAccount(Account account, Boolean joinSteakloverRewards) {
		Logger.info("In Homepage:\n" + "- Click 'Sign In' link" + "In \"My account\" page: \n" + "- Click \"Create New Account\" button\n" + "- Enter all valid information\n" + "- Check \"Join Steaklover Rewards \" checkbox\n" + " - Click \"Join\" button");
		generalPage.goToSignInPage();
		signInPage.clickCreateNewAccountButton();
		myAccountPage.createNewAccount(account.getEmail(), account.getPassword(), account.getPassword(), joinSteakloverRewards);
	}

	public void addFreeFoodToCartFromSelectYourSteakloverRewardsPopup(SKU sku) {
		Logger.info("In \"Select Steaklover Rewards popup\", select first SKU to " + sku.getRecipient() + "-Click \"Add To Cart\" button");
		sku.setName(shoppingCartPage.getSKUNameInSelectSteakloverRewardsModal());
		sku.setQuantity(1);
		sku.setPrice(0.00);
		shoppingCartPage.addSelectSteakloverRewardsSKU(sku.getRecipient());
	}

	public String getMonthlyFreeFoodName() {
		Logger.info("In 'My Account' page: Click on 'Steaklover Rewards' link - Get the monthly free food name");
		goToSteakloverRewardsPage();
		return myAccountPage.getMonthlyFreeFoodName();
	}

	public void goToMyAccountPage() {
		Logger.info("In Hompage, go To My Account page");
		generalPage.goToMyAccountPage();
	}

	public void goToSteakloverRewardsPage() {
		Logger.info("In My Account page, select \"Steaklover Rewards\" option in \"My Omaha Steaks\" section");
		myAccountPage.selectMyOmahaSteaksOption(Constants.STEAKLOVER_REWARDS);
	}
}
