package com.omahasteaks.tests.CollectionCenter.PositiveCases;

import static org.testng.Assert.assertEquals;

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

public class TC_CCP_004_UserCanReviewTheirOrderWhenFillingValidAllFieldsInCompanyAddress extends TestBase_CollectionCenter {

	CustomerAddress companyAddress;

	String dateTime;
	String[] infomationInShippingInformationPage;

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
	public void TC_CCP_004_User_Can_Review_Their_Order_When_Filling_Valid_All_Fields_In_Company_Address() {

		initTestCaseData();

		fillValidCollectionNumberAndSelectSKU();

		fillValidInformationIntoAllFieldsInCompanyAddressSection();

		clickBackButtonOfBrowser();

		clickForwardButtonOfBrowser();

		verifyShippingInformationPageDisplaysCorrectly();

		verifyShippingInformationAreNotLost();

		clickContinueButton();

		enterValidDelayedArrivalDate();

		clickBackButtonOfBrowser();

		verifyShippingInformationPageDisplaysCorrectly();

		verifyShippingInformationAreNotLost();

		clickForwardButtonOfBrowser();

		verifyShippingOptionsPageDisplaysCorrectly();

		verifyInformationInShippingOptionPageAreNotLost();

		clickContinueButton();

		clickBackButtonOfBrowser();

		clickForwardButtonOfBrowser();

		verifyOrderReviewPageDisplaysCorrectly();

	//	verifySKUInformationDisplaysCorrectly();

		verifyShippingAddressDisplaysCorrectly();

		clickBackButtonOfBrowser();

		verifyShippingOptionsPageDisplaysCorrectly();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillValidCollectionNumberAndSelectSKU() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));

		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		firstSku = collectionCenterPage.getFirstSKUInfoFromGourmetCustomCertificatePage();
		collectionCenterPage.selectFirstSKU();
	}

	private void clickBackButtonOfBrowser() {
		Logger.info("Click on \"Back\" button of the browser.");
		Common.goBack();
	}

	private void clickForwardButtonOfBrowser() {
		Logger.info("Click on \"Forward\" button of the browser.");
		Common.goForward();
	}

	private void verifyShippingInformationPageDisplaysCorrectly() {
		Logger.verify("In Shipping Address page:" + "Verify \"Shipping Information\" page displays correctly.");
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_SHIPPING_INFORMATION, "Shipping information page does not display.");
	}

	private void fillValidInformationIntoAllFieldsInCompanyAddressSection() {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all fields in 'Company Address' section." + "- Click \"Continue\" button");
		collectionCenterPage.selectAddressType(AddressType.COMPANY_ADDRESS);
		collectionCenterPage.fillShippingInformation(companyAddress);

		infomationInShippingInformationPage = collectionCenterPage.getShippingAddressInShippingInformationPage();
	}

	private void verifyShippingInformationAreNotLost() {
		Logger.verify("Verify the information in \"Shipping information\" page are not lost.");
		assertEquals(collectionCenterPage.getShippingAddressInShippingInformationPage(), infomationInShippingInformationPage, "The shipping information are not the same.");
	}

	private void clickContinueButton() {
		Logger.info("Click on 'Continue' button");
		collectionCenterPage.clickContinueButton();
	}

	private void enterValidDelayedArrivalDate() {
		Logger.info("In Shipping Information Page:" + "- Type a valid date in  'Delayed Arrival Date' textbox" + "- Click on 'Continue' button");
		collectionCenterPage.inputDelayedArrivalDate(dateTime);
	}

	private void verifyShippingOptionsPageDisplaysCorrectly() {
		Logger.verify("Verify that User can go to the 'Shipping Options' page.");
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_INFORMATION_OPTIONS, "Shipping options page does not display.");
	}

	private void verifyInformationInShippingOptionPageAreNotLost() {
		Logger.verify("Verify the information in \"Shipping information\" page are not lost.");
		assertEquals(collectionCenterPage.getDelayedArrivalDateInShippingOptionsPage(), dateTime, "The date time is not the same.");
	}

	private void verifyOrderReviewPageDisplaysCorrectly() {
		Logger.verify("Verify that \"Order review\" page displays correctly.");
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_ORDER_REVIEW, "Order review page does not display.");
	}

 

	private void verifyShippingAddressDisplaysCorrectly() {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" section displays with correct information.");
		assertEquals(collectionCenterPage.getShippingInformation(), companyAddress.toArray(), "The shipping address does not display as expected.");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCP_004 - User can review their order when filling valid all fields in company address");
		Logger.to("TO_CCP_08 - In Shipping Information page: User can go to the 'Shipping Options' page after filling valid information into all fields in 'Company Address' section.");
		Logger.to("TO_CCP_10 - User can go to the 'Shipping Information' page by clicking forward  button of browser.");
		Logger.to("TO_CCP_11 - The information in filled fields in 'Shipping Information' page are not lost by clicking forward  button of browser.");
		Logger.to("TO_CCP_16 - In Shipping Option Page:  User can go to back 'Shipping Information' page by clicking back button of browser.");
		Logger.to("TO_CCP_17 - In Shipping Option Page: The information in filled fields in 'Shipping Information' page are not lost when clicking back button of browser.");
		Logger.to("TO_CCP_18 - User can go to 'Order Review' page by clicking forward  button of browser.");
		Logger.to("TO_CCP_26 - In Shipping Option Page:  User can go to back 'Shipping Options' page by clicking back button of browser.");
		Logger.to("TO_CCP_27 - In Shipping Option Page: The information in filled field in 'Shipping Options' page are not lost when clicking back button of browser.");
		companyAddress = lstAddresses.getDefaultShippingAddress();
		dateTime = Common.randomNewDate("MM/dd/yyyy", 20);
	}
}
