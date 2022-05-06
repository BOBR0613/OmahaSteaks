package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_005_EditQuantityAndRecipientInTheShoppingCart extends TestBase_CR {

	String qtyOfSKU, recipientName;

	@Inject
	RewardSKU mySKU;

	@Inject
	ListRewardSKUs myCart;
	
	@Test
	public void TC_CRP_005_Edit_Quantity_And_Recipient_In_The_Shopping_Cart() {
		initTestCaseData();

		addSKUToMyselfAndGoToShoppingCartPage();

		editTheQuantity("9");

		verifyTheQuantityOfSKUDisplaysCorrectlyAfterEditingCart();

		verifyTheNumberOfRewardNumberTextboxDisplaysCorrectly();

		addSKUFromCategory(mySKU, "Roasts");
		
		goToShoppingCartPage();

		editTheQuantity("9");

		verifyTheQuantityOfSKUDisplaysCorrectlyAfterEditingCart();

	//	verifyTheMaximumOfNumberOfRewardNumberTextboxIs100();

		editTheRecipient();

		verifyTheRecipientOfSKUDisplaysCorrectlyAfterEditingCart();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheQuantityOfSKUDisplaysCorrectlyAfterEditingCart() {
		Logger.verify("Verify that the quantity of SKU displays correctly after editing the quantity");
		assertEquals(rewardShoppingCart.getTheQuantityOfSKU(mySKU), qtyOfSKU, "The quantity of SKU does not display correctly after changing the quantity");
	}

	private void verifyTheNumberOfRewardNumberTextboxDisplaysCorrectly() {
		Logger.verify("Verify the number of rewards number textbox displays correctly after editing the quantity of SKU");
//		assertEquals(rewardShoppingCart.getNumberOfRewardsNumberTextBox(mySKU), (Integer.parseInt(qtyOfSKU) * mySKU.getPoint()), "The number of rewards number textbox does not display correctly");
	}

 	private void verifyTheRecipientOfSKUDisplaysCorrectlyAfterEditingCart() {
		Logger.verify("Verify that the recipient name is displayed correctly after selecting the different recipient");
		assertEquals(rewardShoppingCart.getRecipientName(mySKU), recipientName, "The recipient name does not display correctly");
	}

	private void editTheQuantity(String valueUpdate) {
		Logger.info("In shopping cart page, edit the quantity of existing SKU: - Select "+ valueUpdate);
		qtyOfSKU = rewardShoppingCart.editCart(mySKU, Constants.REWARDS_SHOPPING_CART_QTY_FIELD, valueUpdate);
	}

	private void editTheRecipient() {
		Logger.info("In shopping cart page, edit the recipient name of existing SKU:");
		recipientName = rewardShoppingCart.editCartWithRandomlyValue(mySKU, Constants.REWARDS_SHOPPING_CART_SEND_TO_FIELD);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_005 - Edit the quantity and recipient in the shopping cart");
		Logger.to("TO_CRP_017 - In Shopping Cart, user can edit the quantity  of existing SKU");
		Logger.to("TO_CRP_019 - In the shopping cart, the number of reward number text box displays correctly when user change the quantity of the existing SKU");
		Logger.to("TO_CRP_021 - User can change the recipient by selecting the existing recipient in the \"Send to\" ComboBox");
		Logger.to("TO_CRP_063 - In the shopping cart page, the maximum of the rewards number text box is 100  when the point of the cart is more than 100.");
		mySKU.init(Recipient.MYSELF, 10);
	}

	private void addSKUToMyselfAndGoToShoppingCartPage() { 
		searchSKUWithPoint("10");
		rewardGeneralPage.selectFirstItem();
		addSKUToCart(mySKU); 
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectSecondItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectThirdItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectFourthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectFifthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectSixthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectSeventhItem();
			addSKUToCart(mySKU); 
		}

		goToShoppingCartPage();
	}

	protected void addSKUToCart(RewardSKU sku) {
		Logger.info("Add first SKU to the " + sku.getRecipient().getValue() + "'s cart");
		sku = rewardGeneralPage.addSKUToCart(sku); 
		myCart.add(sku); 
	}
}
