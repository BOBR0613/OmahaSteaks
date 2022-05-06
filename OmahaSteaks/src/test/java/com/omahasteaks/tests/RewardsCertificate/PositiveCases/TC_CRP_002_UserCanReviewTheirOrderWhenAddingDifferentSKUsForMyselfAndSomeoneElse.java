package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;

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

public class TC_CRP_002_UserCanReviewTheirOrderWhenAddingDifferentSKUsForMyselfAndSomeoneElse extends TestBase_CR {
	@Inject
	RewardSKU mySKU2, recipientSKU2, expectedSKU, mySKU, actualSKU, recipientSKU;
	
	@Inject
	ListRewardSKUs myCart, recipientCart, lstActualSKU;

	CustomerAddress myAddress, recipientAddress;
	
	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRP_002_User_Can_Review_Their_Order_When_Adding_Different_SKUs_For_Myself_And_Someone_Else() {

		initTestCaseData();

		addSKUToMyselfAndSomeoneElseAndGoToShoppingCartPage();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);

		fillShippingInformationForMyselfAndSomeoneElsesThenGoToShippingOptionsPage();

		verifyMessagePleaseReviewYourOrderDisplays();

		verifyTitleOfShoppingBagTableDisplaysCorrectly(myAddress);

		verifyTitleOfShoppingBagTableDisplaysCorrectly(recipientAddress);

		verifySKUInformationDisplaysCorrectlyInOrderReviewPage(myAddress, myCart);

		verifySKUInformationDisplaysCorrectlyInOrderReviewPage(recipientAddress, recipientCart);

		verifyShippingInfoDisplaysCorrectly(myAddress);

		verifyShippingInfoDisplaysCorrectly(recipientAddress);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillShippingInformationForMyselfAndSomeoneElsesThenGoToShippingOptionsPage() {
		fillResidentialAddressAndClickContinue(myAddress);
		fillDelayedArrivalDateAndClickContinueLink(Constants.DEFAULT_DATE);
		fillResidentialAddressAndClickContinue(recipientAddress);
		fillDelayedArrivalDateAndClickContinueLink(Constants.DEFAULT_DATE);
	}

	private void addSKUToMyselfAndSomeoneElseAndGoToShoppingCartPage() {
		searchSKUWithPoint("4");
		rewardGeneralPage.selectThirdItem();
		addSKUsToCarts(mySKU);
		addSKUsToCarts(recipientSKU);

		searchSKUWithPoint("6");
		rewardGeneralPage.selectThirdItem();
		addSKUsToCarts(mySKU2);
		addSKUsToCarts(recipientSKU2);

		goToShoppingCartPage();
	}

	protected void addSKUsToCarts(RewardSKU sku) {
		Logger.info("Add first SKU to the " + sku.getRecipient().getValue() + "'s cart");
		sku = rewardGeneralPage.addSKUToCart(sku);
		if (sku.getRecipient().equals(Recipient.MYSELF)) {
			myCart.add(sku);
		} else {
			recipientCart.add(sku);
		}
	}

	private void verifyShippingInfoDisplaysCorrectly(CustomerAddress customerAddress) {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" of " + customerAddress.firstName + " " + customerAddress.lastName + " section displays with correct information.");
		assertEquals(rewardOrderReviewPage.getShippingInformation(customerAddress), customerAddress.toShippingAddressForRewardsCollectionCenterPage(), "The shipping address does not display as expected.");
	}

	private void verifySKUInformationDisplaysCorrectlyInOrderReviewPage(CustomerAddress shippingAddress, ListRewardSKUs lstSKU) {
		lstActualSKU = rewardOrderReviewPage.getListSKU(shippingAddress);
		for (int i = 0; i < lstSKU.getList().size(); i++) {
			expectedSKU = lstSKU.getList().get(i);
			Logger.verify("Verify that SKU \"" + expectedSKU.getName() + " (" + expectedSKU.getId() + ")\" exists in " + expectedSKU.getRecipient().getValue() + "'s cart.");
			assertEquals(lstActualSKU.getList().get(i).getId(), expectedSKU.getId(), "SKU ID of the SKU which has ID = " + expectedSKU.getId() + " does not display correctly in the Order review page");
			assertEquals(lstActualSKU.getList().get(i).getName(), expectedSKU.getName(), "SKU Name of the SKU which has ID = " + expectedSKU.getId() + " does not display correctly in the Order review page");
			assertEquals(lstActualSKU.getList().get(i).getQuantity(), expectedSKU.getQuantity(), "SKU Quantity of the SKU which has ID = " + expectedSKU.getId() + " does not display correctly in the Order review page");
		}
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_002 - User can review their order when adding different SKUs for myself and someone else");
		Logger.to("TO_CRP_004 - User can add the different SKU for myself and someone else to cart");
		Logger.to("TO_CRP_031 - In Order Review page: Title of  'Shopping bag' table displays correctly with the inputted information in \"Shipping Information\" page with format [\"SHOPPING BAG FOR 'first name' 'last name'] for when the cart is ship to 'Myself' and 'Someone else'");
		Logger.to("TO_CRP_033 - In 'Order review' page: Content of columns ITEM#, SELECTIONS, QTY in \"Shopping bag\" table displays correctly in two modes (Myself , New Address) with the selected carts which contain many different SKUs ");
		Logger.to("TO_CRP_035 - In 'Order review' page: Content of column \"Shiping Info\" is displayed correctly with the inputted recipient's information in the Shipping Information page");

		myAddress = lstAddresses.getDefaultShippingAddress();
		myAddress.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();
		recipientAddress = lstAddresses.getDefaultBillingAddress();
		recipientAddress.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();

		mySKU.init(Recipient.MYSELF);
		mySKU2.init(Recipient.MYSELF);
		recipientSKU.init(Recipient.NEW_RECIPIENT);
		recipientSKU2.init(Recipient.NEW_RECIPIENT);
		myCart.initEmpty();
		recipientCart.initEmpty();
	}
}
