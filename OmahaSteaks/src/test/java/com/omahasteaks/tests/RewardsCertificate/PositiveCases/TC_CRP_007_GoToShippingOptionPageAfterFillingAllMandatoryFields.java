package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_007_GoToShippingOptionPageAfterFillingAllMandatoryFields extends TestBase_CR {
	String[] listFieldInResidentialSection;
	String[] addressInResidentialSection;
	String[] addressInCompanySection;

	CustomerAddress myResidentAddress, recipientResidentAddresss;

	@Inject
	CustomerAddress myCompanyAddress;
	
	@Inject
	RewardSKU mySKU, recipientSKU;
	
	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRP_007_User_Can_Go_To_Shipping_Option_Page_After_Filling_Valid_Information_Into_All_Mandatory_Fields_In_Residential_And_Company_Address() {
		initTestCaseData();

		addSKUToMyselfAndSomeoneElseAndClickCheckOut();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);

		fillValidInformationIntoAllFieldsInResidentialAddressSection();

		changeAddressTypeToCompanyAddress();

		verifyResidentialAddressAndCompanyAddressSectionsHaveExactlyTheSameFields();

		verifyInformationInResidentialAddressAndCompanyAddressSectionsAreTheSame();

		fillValidInformationIntoAllFieldsInCompanyAddressSection();

		verifyShippingOptionsPageDisplays();
		
		Common.waitForDOMChange(Constants.SHORT_TIME);

		fillDelayedArrivalDateAndClickContinueLink(Constants.DEFAULT_DATE);

		fillResidentialAddressAndClickContinue(recipientResidentAddresss);
		
 		Common.waitForDOMChange(Constants.MID_TIME);

		verifyOrderReviewPageDisplays();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyShippingOptionsPageDisplays() {
		Logger.verify("Verify \"Shipping Options\" page displays");
		Common.waitForDOMChange(Constants.SHORT_TIME);
		assertEquals(rewardGeneralPage.getCurrentTabName(), Constants.TITLE_INFORMATION_OPTIONS, "\"Shipping Options\" does not page display");
	}
	
	private void verifyOrderReviewPageDisplays() {
		Logger.verify("Verify \"Order Review\" page displays");
		assertEquals(rewardGeneralPage.getCurrentTabName(), Constants.TITLE_ORDER_REVIEW, "\"Order Review\" does not page display");
	}

	private void fillValidInformationIntoAllFieldsInCompanyAddressSection() {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all mandatory in Company section." + " - Click \"Continue\" button");
		rewardShippingInfoPage.fillShippingInformation(myCompanyAddress);
		rewardShippingInfoPage.clickContinueLink();
	}

	private void verifyInformationInResidentialAddressAndCompanyAddressSectionsAreTheSame() {
		Logger.verify("Verify that the information in filled fields are not lost when changing from \"Residential Address\" option to \"Company Address\" option");
		assertEquals(addressInResidentialSection, addressInCompanySection, "Shipping information in \"Residential Address\" and \"Company Address\" sections are not the same.");
	}

	private void verifyResidentialAddressAndCompanyAddressSectionsHaveExactlyTheSameFields() {
		addressInCompanySection = rewardShippingInfoPage.getShippingAddressInShippingInformationPage();

		Logger.verify("Verify that \"Residential Address\" and \"Company Address\" options have exactly the same fields.");
		assertEquals(rewardShippingInfoPage.getListFieldsInShippingInformationPage(), listFieldInResidentialSection, " Number of field in \"Residential Address\" and \"Company Address\" sections are not the same.");
	}

	private void changeAddressTypeToCompanyAddress() {
		Logger.info("Select Company Address");
		rewardShippingInfoPage.selectAddressType(AddressType.COMPANY_ADDRESS);
	}

	private void fillValidInformationIntoAllFieldsInResidentialAddressSection() {
		rewardShippingInfoPage.selectAddressType(AddressType.RESIDENTIAL_ADDRESS);
		rewardShippingInfoPage.fillShippingInformation(myResidentAddress);
		listFieldInResidentialSection = rewardShippingInfoPage.getListFieldsInShippingInformationPage();
		addressInResidentialSection = rewardShippingInfoPage.getShippingAddressInShippingInformationPage();
	}

	private void addSKUToMyselfAndSomeoneElseAndClickCheckOut() {
		addSKUFromCategory(mySKU, "Poultry Entrees");
		addSKUToCart(mySKU);
		addSKUToCart(mySKU);
		addSKUToCart(mySKU);
	//	addSKUFromCategory(recipientSKU, "Specialty Selections");
	//	addSKUToCart(recipientSKU);
		goToShoppingCartPage();
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_007 - User can go to \"Shipping Option\" page after filling valid information into all mandatory fields in Residential and Company Address");
		Logger.to("TO_CRP_25 - In Shipping Information page: \"Residential Address\" and \"Company Address\" options have exactly the same fields");
		Logger.to("TO_CRP_26 - In Shipping Information page: When changing from \"Residential Address\" option to \"Company Address\" option , the information in filled fields are not lost");
		Logger.to("TO_CRP_27 - In Shipping Information page: User can go to the 'Shipping Options' page after filling valid information into all mandatory fields in 'Residential Address' section");
		Logger.to("TO_CRP_28 - In Shipping Information page: User can go to the 'Shipping Options' page after filling valid information into all mandatory fields in 'Company Address' section");
		myResidentAddress = lstAddresses.getDefaultShippingAddress();
		myResidentAddress.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();
		recipientResidentAddresss = lstAddresses.getDefaultShippingAddress();
		recipientResidentAddresss.removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber();
		myCompanyAddress = lstAddresses.getDefaultBillingAddress();

		mySKU.init(Recipient.MYSELF);
		recipientSKU.init(Recipient.KIM_ANH);
	}

}
