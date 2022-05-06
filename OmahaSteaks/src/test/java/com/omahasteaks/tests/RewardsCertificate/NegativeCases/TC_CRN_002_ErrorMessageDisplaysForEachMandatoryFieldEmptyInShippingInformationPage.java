package com.omahasteaks.tests.RewardsCertificate.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRN_002_ErrorMessageDisplaysForEachMandatoryFieldEmptyInShippingInformationPage extends TestBase_CR {
	CustomerAddress address;

	@Inject
	RewardSKU mySKU;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRN_002_Error_Message_Displays_For_Each_Mandatory_Field_Empty_In_Shipping_Information_Page() {
		initTestCaseData();

		searchAndAddSKU9ToCart(mySKU); 

		fillRewardNumberAndCheckOut();


		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.FIRST_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.LAST_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.COMPANY_NAME);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ADDRESS1);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.STATE);

		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.CITY);
		
		leaveFieldEmptyAndVerifyErrorMessage(AddressFields.ZIP_CODE);
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void leaveFieldEmptyAndVerifyErrorMessage(AddressFields field) {
		Logger.info("In Shipping Information Page:" + "- Leave fields " + field + " empty (if there are any fields having default value, then delete them excluding Country field)" + "- Click \"CONTINUE\"");
		rewardShippingInfoPage.selectAddressType(AddressType.COMPANY_ADDRESS);
		rewardShippingInfoPage.fillShippingAddressExceptField(address, field);
		rewardShippingInfoPage.clickContinueLink();

		Logger.verify("Verify a warning message with below information:" + field.getValue() + ": " + Messages.getRequiredMessageByField(field));
		assertEquals(rewardShippingInfoPage.getErrorMessageByField(field), Messages.getRequiredMessageByField(field), field.getValue());
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRN_002 Error message displays for each mandatory field empty in Shipping Information page");
		Logger.to("TO_CRN_007	In Shipping Information page: Warning message \"Please -- it's a required field\" displays when clicking \"Continue\" without filling \"First Name\" field");
		Logger.to("TO_CRN_008	In Shipping Information page: Warning message \"Ok -- we can't deliver without a last name - it's a required field.\" displays when clicking \"Continue\" without filling \"Last Name\" field");
		Logger.to("TO_CRN_009	In Shipping Information page: Warning message \"Address for delivery -- it's a must! Please provide.\" displays when clicking \"Continue\" without filling \"Address Line 1\" field");
		Logger.to("TO_CRN_010	In Shipping Information page: Warning message \"City, State and Zip code... all required fields.\" displays when clicking \"Continue\" without filling \"Zip\" field");
		Logger.to("TO_CRN_011	In Shipping Information page: Warning message \"City, State and Zip code... all required fields.\" displays when clicking \"Continue\" without filling \"City\" field");
		Logger.to("TO_CRN_012	In Shipping Information page: Warning message \"City, State and Zip code... all required fields.\" displays when clicking \"Continue\" without filling \"State\" field");
		Logger.to("TO_CRN_013	In Shipping Information page: Warning message \"A company name is required.\" displays when clicking \"Continue\" without filling \"Company\" field");
		mySKU.init(Recipient.MYSELF, 10);
		address = lstAddresses.getDefaultShippingAddress();
		address.removeCompanyAddressNotRequiredFields();
		address.zipCode = "CA" + address.zipCode;
	}
}
