package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_009_DetailPageDisplaysCorrectlyAndUserCanAddNewRecipientName extends TestBase_CR {

	String nameCategory, nameOfTheFirstSKU;

	@Inject
	RewardSKU recipientSKU;

	@Test
	public void TC_CRP_009_Detail_Page_Displays_Correctly_And_User_Can_Add_New_Recipient_Name() {
		initTestCaseData();

		goToTheDetailSKUPage();

		verifyTheDetailPageIsDisplayedWithCorrectlyName();

		addSKUToCart(recipientSKU);

		verifyTheRecipientNameIsDisplayedCorrectly();
	}
	
	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheRecipientNameIsDisplayedCorrectly() {
		Logger.verify("Verify the recipient name is displayed correctly in the 'Send To' package box after adding SKU to new recipient");
		assertEquals(rewardGeneralPage.getSelectedValueOfSendToComboBox(), recipientSKU.getRecipient().getValue(), "The recipient name does not display correctly");
	}

	private void goToTheDetailSKUPage() {
		nameCategory = rewardGeneralPage.getCategoryNameFromCategoriesSection(Constants.RANDOMLY_CATEGORY);
		Logger.info("Select " + nameCategory + " tab from Shopping Categories section");
		rewardGeneralPage.selectCategory(nameCategory);
		nameOfTheFirstSKU = rewardGeneralPage.getNameOfTheFifthSKUInCategoryPage();
		Logger.info("Select item: " + nameOfTheFirstSKU);
		rewardGeneralPage.selectFifthItem();
	}

	private void verifyTheDetailPageIsDisplayedWithCorrectlyName() {
		Logger.verify("Verify that the detail SKU page is displayed correctly with the name of previously selected SKU in the category page");
		assertEquals(rewardGeneralPage.updateSKUInfo(recipientSKU).getId() + " " + rewardGeneralPage.updateSKUInfo(recipientSKU).getName(), nameOfTheFirstSKU, "The detail page does not display correctly");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_009 - Detail page displays correctly and user can add new recipient name");
		Logger.to("TO_CRP_024 - The content of the center pane is displayed correctly with the information of the selected item in the category page");
		Logger.to("TO_CRP_023 - In the detail product page, user can add new recipient name for recipient");

		recipientSKU.init(Recipient.KIM_ANH);
	}
}
