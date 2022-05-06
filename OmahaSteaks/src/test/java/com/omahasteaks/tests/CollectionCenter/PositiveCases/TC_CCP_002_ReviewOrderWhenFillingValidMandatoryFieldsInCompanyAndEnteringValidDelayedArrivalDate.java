package com.omahasteaks.tests.CollectionCenter.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_CollectionCenter;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CCP_002_ReviewOrderWhenFillingValidMandatoryFieldsInCompanyAndEnteringValidDelayedArrivalDate extends TestBase_CollectionCenter {

	CustomerAddress companyAddress;

	String dateTime, mainWindowHandle = "";

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	SKU firstSku;

	@Inject
	SKU actualSku;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;

	@Test
	public void TC_CCP_002_User_Can_Review_Their_Order_When_Filling_Valid_Mandatory_Fields_In_Company_Address_And_Entering_Valid_Delayed_Arrival_Date() {

		initTestCaseData();

		enterValidCollectionNumber();

		selectFirstSKU();

		fillValidInformationIntoAllMandatoryFieldsInCompanyAddressSection();

		clickContinueButton();

		verifyShippingOptionsPageDisplaysCorrectly();

		verifyStandardShippingOptionIsDefaultSelected();

		verifyDelayedArrivalDateTextboxDisplaysCorrectly();

		enterValidDelayedArrivalDate();

		clickContinueButton();

		verifyShippingAddressDisplaysCorrectly();

		verifyOrderReviewPageDisplaysCorrectly();

//		verifySKUInformationDisplaysCorrectly();

		clickLinkOnTheFooter(LinksFooter.TERMS_OF_USE);

		verifyTermOfUsePageDisplaysCorrectlly();

		Common.switchToWindow(mainWindowHandle,true);

		clickLinkOnTheFooter(LinksFooter.PRIVACY_POLICY);

		verifyPrivacyPolicyPageDisplayCorrectlly();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void enterValidCollectionNumber() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));
	}

	private void selectFirstSKU() {
		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		firstSku = collectionCenterPage.getFirstSKUInfoFromGourmetCustomCertificatePage();
		collectionCenterPage.selectFirstSKU();
	}

	private void fillValidInformationIntoAllMandatoryFieldsInCompanyAddressSection() {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all mandatory 'Company Address' section." + "- Click \"Continue\" button");
		collectionCenterPage.selectAddressType(AddressType.COMPANY_ADDRESS);
		collectionCenterPage.fillShippingInformation(companyAddress);
	}

	private void clickContinueButton() {
		Logger.info("Click on 'Continue' button");
		collectionCenterPage.clickContinueButton();
	}

	private void verifyShippingOptionsPageDisplaysCorrectly() {
		Logger.verify("Verify that User can go to the 'Shipping Options' page after filling valid information into all mandatory fields in 'Company Address' section.");
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_INFORMATION_OPTIONS, "Shipping options page does not display.");
	}

	private void verifyStandardShippingOptionIsDefaultSelected() {
		Logger.verify("In Shipping Option Page: Verify that \"Standard Shipping\" option is default selected.");
		assertTrue(collectionCenterPage.isShippingMethodSelected(Constants.STANDARD_SHIPPING), "Standard shipping option does not select.");
	}

	private void verifyDelayedArrivalDateTextboxDisplaysCorrectly() {
		Logger.verify("In Shipping Option Page: Verify that  \"Delayed Arrival Date\" textbox appears in Shipping Options page.");
		assertTrue(collectionCenterPage.isDelayedArrivalDateTextboxDisplayed(), "\"Delayed Arrival Date\" textbox does not display.");
	}

	private void enterValidDelayedArrivalDate() {
		Logger.info("In Shipping Information Page:" + "     - Type a valid date in  'Delayed Arrival Date' textbox" + "     - Click on 'Continue' button");
		collectionCenterPage.inputDelayedArrivalDate(dateTime);
	}

	private void verifyShippingAddressDisplaysCorrectly() {
		Logger.verify("Verify that \"Shipping Info\" part in \"Shipping Bag\" section displays with correct information.");
		assertEquals(collectionCenterPage.getShippingInformation(), companyAddress.toArray(), "The shipping address does not display as expected.");
	}

	private void verifyOrderReviewPageDisplaysCorrectly() {
		Logger.verify("Verify that User can go to the 'Order Review' page after entering valid 'Delayed Arrival Date' and clicking on 'Continue' button.");
		assertEquals(collectionCenterPage.getCurrentTabName(), Constants.TITLE_ORDER_REVIEW, "Order review page does not display.");
	}

 

	private void clickLinkOnTheFooter(LinksFooter nameLink) {
		Logger.info("In the footer: Click on " + nameLink);
		collectionCenterPage.clickTermAgreementLinkInFooter(nameLink);
	}

	private void verifyTermOfUsePageDisplaysCorrectlly() {
		Logger.verify("In the footer: Verify the Term Of Use page displays correctlly");
		assertTrue(Common.getPageSource().contains("TERMS OF USE"));
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
	}

	private void verifyPrivacyPolicyPageDisplayCorrectlly() {
		Logger.verify("In the footer: Verify the Privacy Policy page displays correctlly");
		assertTrue(Common.getTitlePage().equals(Constants.COLLECTION_CENTER_TITLE));
		assertTrue(collectionCenterPage.isHeaderOfPrivacyPolicyPageDisplayed(), "Header of the Privacy Policy page does not display correctlly.");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCP_002 - Users can review their order when filling valid mandatory fields in Company address and entering valid delayed arrival date");
		Logger.to("TO_CCP_006 - In Shipping Information page: User can go to the 'Shipping Options' page after filling valid information into all mandatory fields in 'Company Address' section.");
		Logger.to("TO_CCP_013 - User can go to Order Review page by clicking \"Continue\" after filling a valid format date in Shipping Options page.");
		Logger.to("TO_CCP_014 - In Shipping Option Page: \"Standard Shipping\" option is default selected.");
		Logger.to("TO_CCP_015 - In Shipping Option Page: \"Delayed Arrival Date\" field appears in \"Standard Shipping\" option in Shipping Options page.");
		Logger.to("TO_CCP_028 - Footer: Terms of Use page displays when clicking \"Terms of Use\" link.");
		Logger.to("TO_CCP_029 - Footer: Privacy Policy page displays when clicking \"Privacy Policy\" link.");
		mainWindowHandle = DriverUtils.getWindowHandle();
		companyAddress = lstAddresses.getDefaultShippingAddress();
		companyAddress.removeCompanyAddressNotRequiredFields();
		dateTime = Common.randomNewDate("MM/dd/yyyy", 10);
	}
}
