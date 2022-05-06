package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_013_SearchByPoint extends TestBase_CR {
	String pointValue;

	@Test
	public void TC_CRP_013_List_Of_SKU_Which_Has_Points_Less_Than_Searched_Point_Value_Display_Correctly() {
		initTestCaseData();

		searchSKUWithPoint(pointValue);

		verifyListOfSKUDisplays();

		verifySKUsPointDisplaysLessThanSearchedValue();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyListOfSKUDisplays() {
		Logger.verify("Verify that the list of SKU displays when searching with search type \"Point Value\"");
		assertTrue(rewardGeneralPage.isListOfSKUDisplayed(), "The list of SKU does not display after searching with Point value:" + pointValue);
	}

	private void verifySKUsPointDisplaysLessThanSearchedValue() {
		Logger.verify("Verify that the SKU's point displays less than the filled point in Search textbox");
		assertTrue(rewardGeneralPage.isPointOfSKUsDisplayedLessThanSearchedValue(pointValue), "SKU's points does not display less than the field point in the 'search' text box");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_013 - The list of SKU which has point less than searched point value displays correctly");
		Logger.to("TO_CRP_058 - The SKU's point displays less than the filled point in Search textbox");
		Logger.to("TO_CRP_061 - The list of SKU displays when searching with search type \"Point Value\"");

		pointValue = Integer.toString(Common.randBetween(20, 100));
	}

}
