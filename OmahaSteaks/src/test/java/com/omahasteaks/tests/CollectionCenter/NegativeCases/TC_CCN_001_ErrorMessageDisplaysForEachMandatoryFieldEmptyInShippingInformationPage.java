package com.omahasteaks.tests.CollectionCenter.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_CollectionCenter;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CCN_001_ErrorMessageDisplaysForEachMandatoryFieldEmptyInShippingInformationPage extends TestBase_CollectionCenter {

	CustomerAddress address;

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	ListAddresses lstAddresses;

	@Inject
	GeneralPage generalPage;
	
	@Test
	public void TC_CCN_001_Error_Message_Displays_For_Each_Mandatory_Field_Empty_In_Shipping_Information_Page() {

		initTestCaseData();

		fillValidCollectionNumberAndSelectSKU();

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ZIP_CODE);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.FIRST_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.LAST_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.COMPANY_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ADDRESS1);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.STATE);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.CITY);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.EMAIL);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.PHONE);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void fillValidCollectionNumberAndSelectSKU() {
		enterValidCollectionNumber();
		selectFirstSKU();
	}

	private void leaveFieldEmptyAndVerifyErrorMessage(AddressFields field) {
		Logger.info("In Shipping Information Page:" + "- Leave fields " + field + " empty (if there are any fields having default value, then delete them excluding Country field)" + "- Click \"CONTINUE\"");
		collectionCenterPage.selectAddressType(AddressType.COMPANY_ADDRESS);
		collectionCenterPage.fillShippingAddressExceptField(address, field);
		collectionCenterPage.clickContinueButton();
		if (field.equals(AddressFields.PHONE) && collectionCenterPage.getCurrentTabName().equals(Constants.TITLE_INFORMATION_OPTIONS)) {
			Logger.verify("Verify a warning message with below information:" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
			Common.goBack();
		} else if (field.equals(AddressFields.EMAIL)) {
			Logger.verify("Verify a warning message with below information:" + field.getValue() + ": " + "\"Please fill out this field\" is displayed ");
			if (collectionCenterPage.getWarningMessage().equals(Messages.COLLECTION_CENTER_EMPTY_EMAIL_ERROR_MESSAGE))
				assertTrue(collectionCenterPage.getWarningMessage().equals(Messages.COLLECTION_CENTER_EMPTY_EMAIL_ERROR_MESSAGE), Messages.COLLECTION_CENTER_EMPTY_EMAIL_ERROR_MESSAGE + " does not exist");
		} else {
			Logger.verify("Verify a warning message with below information:" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
			assertEquals(collectionCenterPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field), field.getValue());
		}
	}

	private void selectFirstSKU() {
		Logger.info("In Gourmet Custom Certificate Page: - Select first  SKU. - Click \"REDEEM NOW\" button");
		collectionCenterPage.selectFirstSKU();
	}

	private void enterValidCollectionNumber() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button.");
		String gcid = generalPage.getGCID("PPD");
		System.out.println("gcid :" + gcid);
		collectionCenterPage.submitCollectionNumber(gcid);
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCN_001 Error message displays for each mandatory field empty in Shipping Information page");
		Logger.to("TO_CCN_04 - In Shipping Information page: Warning message \"Please -- it's a required field\" displays when clicking \"Continue\" without filling \"First Name\" field.");
		Logger.to("TO_CCN_05 - In Shipping Information page: Warning message \"Ok -- we can't deliver without a last name - it's a required field.\" displays when clicking \"Continue\" without filling \"Last Name\" field.");
		Logger.to("TO_CCN_06 - In Shipping Information page: Warning message displays when clicking \"Continue\" without filling \"Phone\" field.");
		Logger.to("TO_CCN_07 - In Shipping Information page: Warning message \"Address for delivery -- it's a must! Please provide.\" displays when clicking \"Continue\" without filling \"Address Line 1\" field.");
		Logger.to("TO_CCN_08 - In Shipping Information page: Warning message \"City, State and Zip code... all required fields.\" displays when clicking \"Continue\" without filling \"City\"/\"State\"/\"Zip\" field.");
		Logger.to("TO_CCN_09 - In Shipping Information page: Warning message \"Please fill out this field.\" displays when clicking \"Continue\" without filling \"Email Address\" field.");
		Logger.to("TO_CCN_24 - In Shipping Information page: Warning message \"A company name is required.\" displays when clicking \"Continue\" without filling \"Company\" field.");
		address = lstAddresses.getDefaultShippingAddress();
		address.removeCompanyAddressNotRequiredFields();
		address.zipCode = "CA" + address.zipCode;
	}

}
