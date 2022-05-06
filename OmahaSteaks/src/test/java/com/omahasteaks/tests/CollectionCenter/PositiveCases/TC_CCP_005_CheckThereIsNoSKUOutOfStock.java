package com.omahasteaks.tests.CollectionCenter.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.tests.TestBase_CollectionCenter; 
import com.omahasteaks.utils.helper.Logger;

public class TC_CCP_005_CheckThereIsNoSKUOutOfStock extends TestBase_CollectionCenter {

	@Inject
	CollectionCenterPage collectionCenterPage;

	@Inject
	GeneralPage generalPage;

	@Test
	public void TC_CCP_005_Check_There_Is_No_SKU_Out_Of_Stock() {

		initTestCaseData();

		fillValidCollectionNumber();

		verifyThereIsNoSKUOutOfStock();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyThereIsNoSKUOutOfStock() {
		Logger.verify("Verify there is no SKU out of stock");
		assertTrue(collectionCenterPage.isSKUOutOfStock(), "REDEEM NOW button does not display correctly");
	}

	private void fillValidCollectionNumber() {
		Logger.info("From Homepage: Enter valid collection number and click 'REDEEM' button");
		collectionCenterPage.submitCollectionNumber(generalPage.getGCID("PPD"));
	}

	private void initTestCaseData() {
		Logger.tc("TC_CCP_005 - Check there is no SKU out of stock");
		Logger.to("TO_CCP_30 - Verify there is no SKU out of stock");
	}
}
