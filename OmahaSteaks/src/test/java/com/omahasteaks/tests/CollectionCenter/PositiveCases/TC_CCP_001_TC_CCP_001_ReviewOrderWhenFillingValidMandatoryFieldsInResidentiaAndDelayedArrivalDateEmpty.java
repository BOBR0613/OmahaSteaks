package com.omahasteaks.tests.CollectionCenter.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_CollectionCenter;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants; 
import com.omahasteaks.utils.helper.Logger;

public class TC_CCP_001_TC_CCP_001_ReviewOrderWhenFillingValidMandatoryFieldsInResidentiaAndDelayedArrivalDateEmpty extends TestBase_CollectionCenter {

	CustomerAddress residentialAddress;

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	GeneralPage generalPage;

	@Inject
	SKU firstSku;

	@Inject
	SKU actualSku;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CCP_001_User_Can_Review_Their_Order_When_Filling_Valid_Mandatory_Fields_In_Residential_AddressAnd_Leaving_Delayed_Arrival_Date_Empty() {

		initTestCaseData();

		enterValidCollectionNumber();

		verifyGourmetCustomCertificatePageDisplayed();

		selectFirstSKU();

		verifyShippingInformationPageDisplaysCorrectly();

		fillValidInformationIntoAllMandatoryFieldsInResidentialAddressSection();

		clickContinueButton();

		verifyShippingOptionsPageDisplaysCorrectly();

		clickContinueButton();

		verifyOrderReviewPageDisplaysCorrectly();

		verifyShippingBagTableDisplaysWithFourParts();

		verifyPleaseReviewYourOrderMsgDisplaysCorrectly();

		verifyShoppingBagTitleDisplaysCorrectly();

//		verifySKUInformationDisplaysCorrectly();

		verifyShippingAddressDisplaysCorrectly(); 

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void enterValidCollectionNumber() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));
		Common.waitForPageLoad();
	}

	private void verifyGourmetCustomCertificatePageDisplayed() {
		Logger.verify("Verify that Gourmet Custom Certificate page displays after entering valid Collection number and click Redeem.");
		Common.waitForDOMChange();
		assertTrue(collectionCenterPage.isButtonRedeemNowDisplayed(), "Button Redeem now does not display.");
//		assertTrue(collectionCenterPage.isGourmetCustomCertificateTextDisplayed(), "\"Gourmet Custom Certificate\" text does not display.");
	}

	private void selectFirstSKU() {
		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		firstSku = collectionCenterPage.getFirstSKUInfoFromGourmetCustomCertificatePage();
		collectionCenterPage.selectFirstSKU();
	}

	private void verifyShippingInformationPageDisplaysCorrectly() {
		Logger.verify("In Shipping Address page:" + "Verify \"Shipping Information\" page displays when user choose randomly a SKU and click \"Redeem Now\" in Gourmet Custom Certificate page.");
		Common.waitForDOMChange();
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_SHIPPING_INFORMATION, "Shipping information page does not display.");
	}

	private void fillValidInformationIntoAllMandatoryFieldsInResidentialAddressSection() {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all mandatory  'Residential Address' section." + "- Click \"Continue\" button");
		collectionCenterPage.selectAddressType(AddressType.RESIDENTIAL_ADDRESS);
		collectionCenterPage.fillShippingInformation(residentialAddress);
	}

	private void verifyShippingOptionsPageDisplaysCorrectly() {
		Logger.verify("Verify that User can go to the 'Shipping Options' page after filling valid information into all mandatory fields in 'Residential Address' section.");
		Common.waitForDOMChange();
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_INFORMATION_OPTIONS, "Shipping options page does not display.");
	}

	private void clickContinueButton() {
		Logger.info("Click on 'Continue' button");
		collectionCenterPage.clickContinueButton();
	}

	private void verifyOrderReviewPageDisplaysCorrectly() {
		Logger.verify("Verify that User can go to the 'Order Review' page after leaving 'Delayed Arrival Date' textbox empty and clicking on 'Continue' button.");
		Common.waitForDOMChange();
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_ORDER_REVIEW, "Order review page does not display.");
	}

	private void verifyShippingBagTableDisplaysWithFourParts() {
		Logger.verify("Verify that \"Shipping Bag\" section displays with 4 parts: Item, Selections, Quantity, Shipping Info.");
		Common.waitForDOMChange();
		assertTrue(collectionCenterPage.isShippingPartDisplaysInShippingTable(Constants.SHOPPING_BAG_ITEM), "'ITEM' part does not display in 'Shipping bag' table.");
		assertTrue(collectionCenterPage.isShippingPartDisplaysInShippingTable(Constants.SHOPPING_BAG_SELECTIONS), "'SELECTIONS' part does not display in 'Shipping bag' table.");
		assertTrue(collectionCenterPage.isShippingPartDisplaysInShippingTable(Constants.SHOPPING_BAG_QUANTITY), "'QTY' part does not display in 'Shipping bag' table.");
		assertTrue(collectionCenterPage.isShippingPartDisplaysInShippingTable(Constants.SHOPPING_BAG_SHIPPING_INFORMATION), "'SHIPPING INFORMATIONS' part does not display in 'Shipping bag' table.");
	}

	private void verifyPleaseReviewYourOrderMsgDisplaysCorrectly() {
		Logger.verify("In Order Review Page:" + "    Verify that  'Please review your order information and click the \"Submit Order\" button at the bottom of the page." + "Your order will not be completed unless you click \"Submit Order.\" ' message is displayed");
		Common.waitForDOMChange();
		assertTrue(collectionCenterPage.isPleaseReviewYourOrderMsgDisplayed(), "'Please Review Your Order' message does not display in 'Order review' page.");
	}

	private void verifyShoppingBagTitleDisplaysCorrectly() {
		Logger.verify(" Verify that title of  'Shopping bag' table displays with correct information.");
		Common.waitForDOMChange();
		assertTrue(collectionCenterPage.isShoppingBagTitleDisplayedCorrectly(residentialAddress.firstName + " " + residentialAddress.lastName), "Title of 'Shopping bag' does not display correctly.");
	}
 

	private void verifyShippingAddressDisplaysCorrectly() {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" section displays with correct information.");
		Common.waitForDOMChange();
		assertEquals(collectionCenterPage.getShippingInformation(), residentialAddress.toArray(), "The shipping address does not display as expected.");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCP_001 - Users can review their order when filling valid mandatory fields in Residential address and leaving delayed arrival date empty");
		Logger.to("TO_CCP_001 - In Redemption Center home page: User can go to Gourmet Custom Certificate after entering valid Collection number and click Redeem.");
		Logger.to("TO_CCP_002 - Shipping Information page displays when user choose randomly a SKU and click \"Redeem Now\" in Gourmet Custom Certificate page.");
		Logger.to("TO_CCP_005 - In Shipping Information page: User can go to the 'Shipping Options' page after filling valid information into all mandatory fields in 'Residential Address' section.");
		Logger.to("TO_CCP_012 - In Shipping Option page: User can go to the 'Order Review' page after leaving 'Delayed Arrival Date' textbox empty and clicking on 'Continue' button.");
		Logger.to("TO_CCP_019 - In Order Review Page: 'Please review your order information and click the \"Submit Order\" button at the bottom of the page." + "Your order will not be completed unless you click \"Submit Order.\" ' message is displayed");
		Logger.to("TO_CCP_020 - In Order Review page: \"Shipping Bag\" section displays with 4 parts: Item, Selections, Quantity, Shipping Info.");
		Logger.to("TO_CCP_021 - In Order Review page: \"Item\" part in \"Shipping Bag\" section displays with correct item number of a SKU that user has chosen in Gourmet Custom Certificate pag");
		Logger.to("TO_CCP_022 - In Order Review page: \"Selections\" part in \"Shipping Bag\" section displays with correct name of a SKU that user has chosen in Gourmet Custom Certificate page.");
		Logger.to("TO_CCP_023 - In Order Review page: \"QTY\" part in \"Shipping Bag\" section displays with correct name of a SKU that user has chosen in Gourmet Custom Certificate page.");
		Logger.to("TO_CCP_024 - In Order Review page: \"Shipping Info\" part in \"Shipping Bag\" section displays with correct information.");
		Logger.to("TO_CCP_025 - In Order Review page: Title of 'Shopping bag' table displays correct information by 'firstname and last name of shipping information");

		residentialAddress = lstAddresses.getDefaultShippingAddress();
		residentialAddress.removeResidentialAddressNotRequiredFields();
	}
}
