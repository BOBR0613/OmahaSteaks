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

public class TC_CCP_003_UserCanReviewTheirOrderWhenFillingValidAllFieldsInResidentialAddress extends TestBase_CollectionCenter {

	CustomerAddress residentialAddress;

	String dateTime;
	String[] listFieldInResidentialSection;
	String[] addressInResidentialSection;
	String[] addressInCompanySection;

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	SKU firstSku;

	@Inject
	GeneralPage generalPage;

	@Inject
	SKU actualSku;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CCP_003_User_Can_Review_Their_Order_When_Filling_Valid_All_Fields_In_Residential_Address() {

		initTestCaseData();

		fillValidCollectionNumberAndSelectSKU();

		clickBackToExclusiveOfferLink();

		verifyGourmetCustomCertificatePageIsDisplayed();

		selectFirstSKU();

		verifyShippingInformationPageDisplaysCorrectly();

		fillValidInformationIntoAllFieldsInResidentialAddressSection();

		selectAddressType(AddressType.COMPANY_ADDRESS);

		verifyResidentialAddressAndCompanyAddressSectionsHaveExactlyTheSameFields();

		verifyInformationInResidentialAddressAndCompanyAddressSectionsAreTheSame();

		selectAddressType(AddressType.RESIDENTIAL_ADDRESS);

		clickContinueButton();

		enterValidDelayedArrivalDate();

	//	verifySKUInformationDisplaysCorrectly();

		verifyShippingAddressDisplaysCorrectly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillValidCollectionNumberAndSelectSKU() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));

		selectFirstSKU();
	}

	private void enterValidDelayedArrivalDate() {
		Logger.info("In Shipping Information Page:" + "- Type a valid date in  'Delayed Arrival Date' textbox" + "- Click on 'Continue' button");
		collectionCenterPage.inputDelayedArrivalDate(dateTime);

		clickContinueButton();
	}

	private void verifyResidentialAddressAndCompanyAddressSectionsHaveExactlyTheSameFields() {
		addressInCompanySection = collectionCenterPage.getShippingAddressInShippingInformationPage();

		Logger.verify("Verify that \"Residential Address\" and \"Company Address\" options have exactly the same fields.");
		assertEquals(collectionCenterPage.getListFieldsInShippingInformationPage(), listFieldInResidentialSection, " Number of field in \"Residential Address\" and \"Company Address\" sections are not the same.");
	}

	private void verifyInformationInResidentialAddressAndCompanyAddressSectionsAreTheSame() {
		Logger.verify("Verify that the information in filled fields are not lost when changing from \"Residential Address\" option to \"Company Address\" option");
		assertEquals(addressInResidentialSection, addressInCompanySection, "Shipping information in \"Residential Address\" and \"Company Address\" sections are not the same.");
	}

	private void verifyGourmetCustomCertificatePageIsDisplayed() {
		Logger.verify("Verify that the Gourmet Custom Certificate page displays when clicking \"Back to Exclusive Offer\"");
		assertTrue(collectionCenterPage.isButtonRedeemNowDisplayed(), "Button Redeem now does not display.");
//		assertTrue(collectionCenterPage.isGourmetCustomCertificateTextDisplayed(), "\"Gourmet Custom Certificate\" text does not display.");
	}

	private void clickBackToExclusiveOfferLink() {
		Logger.info("In Shipping Information Page:" + "- Click on 'Back to Exclusive Offer' link");
		collectionCenterPage.clickBackToExclusiveOfferLink();
	}

	private void selectFirstSKU() {
		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		firstSku = collectionCenterPage.getFirstSKUInfoFromGourmetCustomCertificatePage();
		collectionCenterPage.selectFirstSKU();
	}

	private void fillValidInformationIntoAllFieldsInResidentialAddressSection() {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all fields in 'Residential Address' section.");
		collectionCenterPage.selectAddressType(AddressType.RESIDENTIAL_ADDRESS);
		collectionCenterPage.fillShippingInformation(residentialAddress);

		listFieldInResidentialSection = collectionCenterPage.getListFieldsInShippingInformationPage();
		addressInResidentialSection = collectionCenterPage.getShippingAddressInShippingInformationPage();
	}

	private void verifyShippingInformationPageDisplaysCorrectly() {
		Logger.verify("In Shipping Address page:" + "Verify \"Shipping Information\" page displays when user choose randomly a SKU and click \"Redeem Now\" in Gourmet Custom Certificate page.");
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_SHIPPING_INFORMATION, "Shipping information page does not display.");
	}

	private void clickContinueButton() {
		Logger.info("Click on 'Continue' button");
		collectionCenterPage.clickContinueButton();
	}

	private void selectAddressType(AddressType type) {
		Logger.info("Select " + type);
		collectionCenterPage.selectAddressType(type);
	}

 

	private void verifyShippingAddressDisplaysCorrectly() {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" section displays with correct information.");
		assertEquals(collectionCenterPage.getShippingInformation(), residentialAddress.toArray(), "The shipping address does not display as expected.");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CPP_003 - User can review their order when filling valid all fields in residential address");
		Logger.to("TO_CCP_003 - In Shipping Information page: \"Residential Address\" and \"Company Address\" options have exactly the same fields.");
		Logger.to("TO_CCP_004 - In Shipping Information page: When changing from \"Residential Address\" option to \"Company Address\" option , the information in filled fields are not lost.");
		Logger.to("TO_CCP_007 - In Shipping Information page: User can go to the 'Shipping Options' page after filling valid information into all fields in 'Residential Address' section.");
		Logger.to("TO_CCP_009 - In Shipping Information page: Gourmet Custom Certificate page displays when clicking \"Back to Exclusive Offer\"");
		residentialAddress = lstAddresses.getDefaultShippingAddress();
		dateTime = Common.randomNewDate("MM/dd/yyyy", 30);
	}
}
