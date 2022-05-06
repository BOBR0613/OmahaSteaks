package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_001_UseCanGoToOrderReviewPageWhenFillingAllValidInformation extends TestBase_CR {

	CustomerAddress myAddress;

	@Inject
	RewardSKU mySKU, actualSKU;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	ListRewardSKUs myCart, recipientCart, lstActualSKU;

	@Test
	public void TC_CRP_001_Use_Can_Go_To_Order_Review_Page_When_Filling_All_Valid_Information() {

		initTestCaseData();

		addSKUToMyselfAndGoToShoppingCartPage();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);

		verifyShippingInformationPageDisplays();

		fillResidentialAddressAndClickContinue(myAddress);

		fillDelayedArrivalDateAndClickContinueLink(Constants.DEFAULT_DATE);

		verifyMessagePleaseReviewYourOrderDisplays();

		verifyShippingBagSectionDisplaysCorrectly();

		verifyTitleOfShoppingBagTableDisplaysCorrectly(myAddress);

		verifySKUInformationDisplaysCorrectlyInOrderReviewPage(myAddress, mySKU);

		verifyShippingInfoDisplaysCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyShippingInfoDisplaysCorrectly() {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" section displays with correct information.");
		assertEquals(rewardOrderReviewPage.getShippingInformation(myAddress), myAddress.toShippingAddressForRewardsCollectionCenterPage(), "The shipping address does not display as expected.");
	}

	private void verifySKUInformationDisplaysCorrectlyInOrderReviewPage(CustomerAddress shippingAddress, RewardSKU sku) {
		Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
		actualSKU = rewardOrderReviewPage.getListSKU(shippingAddress).getList().get(0);

		assertEquals(actualSKU.getId(), sku.getId(), "SKU ID of the SKU which has ID = " + sku.getId() + " does not display correctly in the Order review page");
		assertEquals(actualSKU.getName(), sku.getName(), "SKU Name of the SKU which has ID = " + sku.getId() + " does not display correctly in the Order review page");
		assertEquals(actualSKU.getQuantity(), 5, "SKU Quantity of the SKU which has ID = " + sku.getId() + " does not display correctly in the Order review page");
	}

	private void verifyShippingBagSectionDisplaysCorrectly() {
		Logger.verify("Verify that \"Shipping Bag\" section displays with 4 parts: Item, Selections, Quantity, Shipping Info");
		assertTrue(rewardOrderReviewPage.isShippingColumnDisplaysInShippingTable(Constants.SHOPPING_BAG_ITEM), "'ITEM' part does not display in 'Shipping bag' table.");
		assertTrue(rewardOrderReviewPage.isShippingColumnDisplaysInShippingTable(Constants.SHOPPING_BAG_SELECTIONS), "'SELECTIONS' part does not display in 'Shipping bag' table.");
		assertTrue(rewardOrderReviewPage.isShippingColumnDisplaysInShippingTable(Constants.SHOPPING_BAG_QUANTITY), "'QTY' part does not display in 'Shipping bag' table.");
		assertTrue(rewardOrderReviewPage.isShippingColumnDisplaysInShippingTable(Constants.SHOPPING_BAG_SHIPPING_INFORMATION), "'SHIPPING INFORMATIONS' part does not display in 'Shipping bag' table");
	}

	private void verifyShippingInformationPageDisplays() {
		Logger.verify("In Shipping Address page:" + "Verify \"Shipping Information\" page displays when user choose randomly a SKU");
		assertEquals(rewardGeneralPage.getCurrentTabName(), Constants.TITLE_SHIPPING_INFORMATION, "Shipping information page does not display.");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_001 - User can go to Order Review page when filling all valid information");
		Logger.to("TO_CRP_022 - User can go to 'Shipping information' page after entering valid reward number in the shopping cart page");
		Logger.to("TO_CRP_029 - Verify that 'Please review your order information and click the \"Submit Order\" button at the bottom of the page.Your order will not be completed unless you click \"Submit Order.\" ' message is displayed in Order Review Page");
		Logger.to("TO_CRP_030 - In Order Review page: Title of  'Shopping bag' table displays correctly with the inputed information in \"Shipping Information\" page with format [\"SHOPPING BAG FOR 'first name' 'last name']");
		Logger.to("TO_CRP_032 - In 'Order review' page: Content of columns ITEM#, SELECTIONS, QTY in \"Shopping bag\" table displays correctly in Myself mode with the selected cart which contains only one SKU");
		Logger.to("TO_CRP_034 - In 'Order review' page: Content of column \"Shiping Info\" is displayed correctly with the inputed information in the Shipping Information page");

		myAddress = lstAddresses.getDefaultShippingAddress();
		myAddress.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();
		mySKU.init(Recipient.MYSELF);
		myCart.initEmpty();
	}
	
	private void addSKUToMyselfAndGoToShoppingCartPage() {
	  for (int i=0; i<5; i++) {
		searchSKUWithPoint("4");
		rewardGeneralPage.selectNinthItem();
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
