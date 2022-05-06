package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_008_FillingValidOrLeavingEmptyDelayedArrivalDateThenCompleteReviewingOrders extends TestBase_CR {

	String dateTime;

	@Inject
	RewardSKU actualSKU, mySKU, recipientSKU;

	CustomerAddress myAddress, recipientAddress;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRP_008_Filling_Valid_Or_Leaving_Empty_Delayed_Arrival_Date_Then_Complete_Reviewing_Orders() {

		initTestCaseData();

		addSKUsToMyselfAndSomeoneElseAndApplyValidRewardNumber();

		fillResidentialAddressAndClickContinue(myAddress);

		verifyStandardShippingOptionIsDefaultSelected();

		verifyDelayedArrivalDateTextboxDisplaysCorrectly();

		fillDelayedArrivalDateAndClickContinueLink(Constants.DEFAULT_DATE);

		fillResidentialAddressAndClickContinue(recipientAddress);

		fillDelayedArrivalDateAndClickContinueLink(dateTime);

		verifyOrderReviewPageDisplaysCorrectly();

		verifyInformationOfShoppingBagTableIsDisplayedCorrectlyInOrderReviewPage(myAddress, mySKU);

		verifyInformationOfShoppingBagTableIsDisplayedCorrectlyInOrderReviewPage(recipientAddress, recipientSKU);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyShippingInfoDisplaysCorrectly(CustomerAddress customerAddress) {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" of " + customerAddress.firstName + " " + customerAddress.lastName + " section displays with correct information.");
		assertEquals(rewardOrderReviewPage.getShippingInformation(customerAddress), customerAddress.toShippingAddressForRewardsCollectionCenterPage(), "The shipping address does not display as expected.");
	}

	private void addSKUsToMyselfAndSomeoneElseAndApplyValidRewardNumber() {
		searchSKUWithPoint("24");
		rewardGeneralPage.selectFirstItem();
		addSKUToCart(mySKU);  
		addSKUToCart(recipientSKU);

		goToShoppingCartPage();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);
	}

	private void verifyStandardShippingOptionIsDefaultSelected() {
		Logger.verify("In Shipping Option Page: Verify that \"Standard Shipping\" option is default selected.");
		assertTrue(rewardShippingOptionPage.isShippingMethodSelected(Constants.STANDARD_SHIPPING), "Standard shipping option does not select.");
	}

	private void verifyDelayedArrivalDateTextboxDisplaysCorrectly() {
		Logger.verify("In Shipping Option Page: Verify that  \"Delayed Arrival Date\" textbox appears in Shipping Options page.");
		assertTrue(rewardShippingOptionPage.isDelayedArrivalDateTextboxDisplayed(), "\"Delayed Arrival Date\" textbox does not display.");
	}

	private void verifyOrderReviewPageDisplaysCorrectly() {
		Logger.verify("Verify that User can go to the 'Order Review' page after leaving 'Delayed Arrival Date' textbox empty and clicking on 'Continue' button.");
		assertEquals(rewardGeneralPage.getCurrentTabName(), Constants.TITLE_ORDER_REVIEW, "Order review page does not display.");
	}

	private void verifySKUInformationDisplaysCorrectlyInOrderReviewPage(CustomerAddress shippingAddress, RewardSKU sku) {
		Logger.verify("Verify that SKU \"" + sku.getName() + " (" + sku.getId() + ")\" is existed in " + sku.getRecipient().getValue() + "'s cart.");
		actualSKU = rewardOrderReviewPage.getListSKU(shippingAddress).getList().get(0);

		assertEquals(actualSKU.getId(), sku.getId(), "SKU ID of the SKU which has ID = " + sku.getId() + " does not display correctly in the Order review page");
		assertEquals(actualSKU.getName(), sku.getName(), "SKU Name of the SKU which has ID = " + sku.getId() + " does not display correctly in the Order review page");
		assertEquals(actualSKU.getQuantity(), sku.getQuantity(), "SKU Quantity of the SKU which has ID = " + sku.getId() + " does not display correctly in the Order review page");
	}

	private void verifyInformationOfShoppingBagTableIsDisplayedCorrectlyInOrderReviewPage(CustomerAddress shippingAddress, RewardSKU sku) {
		verifyTitleOfShoppingBagTableDisplaysCorrectly(shippingAddress);
		verifySKUInformationDisplaysCorrectlyInOrderReviewPage(shippingAddress, sku);
		verifyShippingInfoDisplaysCorrectly(shippingAddress);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_008 - Filling valid or leaving empty delayed arrival date then complete reviewing orders");
		Logger.to("TO_CRP_041 - In Shipping Option Page: \"Standard Shipping\" option is default selected");
		Logger.to("TO_CRP_042 - In Shipping Option Page: \"Delayed Arrival Date\" field appears in \"Standard Shipping\" option in Shipping Options page");
		Logger.to("TO_CRP_043 - In Shipping Option page: User can go to the 'Order Review' page after leaving 'Delayed Arrival Date' textbox empty and clicking on 'Continue' button");
		Logger.to("TO_CRP_044 - User can go to Order Review page by clicking \"Continue\" after filling a valid format date in Shipping Options page");

		mySKU.init(Recipient.MYSELF);
		recipientSKU.init(Recipient.NEW_RECIPIENT);
		myAddress = lstAddresses.getDefaultBillingAddress();
		myAddress.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();
		recipientAddress = lstAddresses.getDefaultShippingAddress();
		recipientAddress.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();
		dateTime = Common.randomNewDate("MM/dd/yyyy", 10);
	}
}
