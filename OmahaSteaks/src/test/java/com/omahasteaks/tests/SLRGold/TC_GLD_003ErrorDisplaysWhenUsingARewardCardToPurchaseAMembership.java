package com.omahasteaks.tests.SLRGold;

import static org.testng.Assert.assertEquals; 

import org.testng.annotations.Test; 

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item; 
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage; 
import com.omahasteaks.tests.SteakloverRewardsMembership.TestBase_SLR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_GLD_003ErrorDisplaysWhenUsingARewardCardToPurchaseAMembership extends TestBase_SLR {

	@Inject
	GeneralPage generalPage;

	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage;

	@Inject
	ListAddresses lstAddresses;
	CustomerAddress shippingAddress, billingAddress; 

	@Inject
	Item myItem;

	@Inject
	MyAccountPage myAccountPage;


	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;

	@Inject
	CategoryPage categoryPage;

	@Inject
	ProductPage productPage;

	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;

	@Inject
	ShoppingCartPage shoppingCartPage;

	@Test
	public void TC_GLD_003Error_Displays_When_Using_A_Reward_Card_To_Purchase_A_Membership() {

		initTestCaseData();

		renewMembershipAndCheckout();

		generalPage.viewMyCart();

		shoppingCartPage.checkOut(); 
		
		paymentAndReviewPage.clickApplyARewardCardGiftCardOrVoucherLink();   

		generalPage.enterVoucherAndClickSubmitButtonInVoucherModal(Constants.REWARD_CARD_PRICE_20);

		assertEquals(generalPage.getErrorMessageInVoucherModal().trim(), "Reward card " + Constants.REWARD_CARD_PRICE_20 + " is not allowed.");


	}


	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void renewMembershipAndCheckout() {
		signIn(account);
		Logger.info("In Membership page:" + "- Click \"Join Order\" button");
		Common.waitForDOMChange();
		myAccountPage.clickRewewMembershipButton();
	} 



	private void initTestCaseData() {
		Logger.tc("TC_GLD_003 - Error message displays when using a Reward Card to buy a gold membership");
		Logger.to("TO_GLD_01 - Error \"" + Messages.RWM0031 + "\" displays when using a Reward Card to buy a gold membership");
		DriverUtils.getWebDriver().manage().deleteAllCookies();
		billingAddress = lstAddresses.getDefaultBillingAddress();
		shippingAddress = lstAddresses.getDefaultShippingAddress();
		shippingAddress.setRandomEmail();
		account = Constants.LIST_ACCOUNTS.setAccountByEmail("slrgold01@omahasteaks.com");
		Constants.DB.updateGoldExpireDate(account.getEmail(), 14);
		Common.modalDialog.closeSavorDialog();
	}

}
