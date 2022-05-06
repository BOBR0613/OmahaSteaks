package com.omahasteaks.tests.SearchResultPage;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SRP_001_ItemsAndPackagesAndMealsTabsAreDisplayed extends TestBase_2SC {
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	GeneralPage generalPage;
	@Inject
	Item item;

	@Test
	public void TC_SRP_001_Items_And_Packages_And_Meals_Tabs_Are_Displayed() {

		initTestCaseData();

		searchItem(item);

		verifyTabsAreDisplayed();

		verifyCountNumberOfTabIsGreaterThanZero(SkuType.ITEM);

		verifyCountNumberOfTabIsGreaterThanZero(SkuType.PACKAGES);

		verifyCountNumberOfTabIsGreaterThanZero(SkuType.MEAL);

		verifySortByMostRelevantIsDefaultSortType();
	}

	// ============================================================================
	// Test Methods
	// ============================================================================
	public void verifyCountNumberOfTabIsGreaterThanZero(SkuType tab) {
		Common.waitForDOMChange();
		Logger.verify("Verify Count number of " + tab + " is greater than 0");
		assertTrue(searchResultPage.isCountNumberByTabGreaterThanZero(tab), "Count number of " + tab + " is equal to 0");
	}

	public void verifySortByMostRelevantIsDefaultSortType() {
		Logger.verify("Verify Sort by: Most Relevant is default sort type");
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE))
			assertTrue(searchResultPage.isSortTypeDisplayed(Constants.DEFAULT_SORT_TYPE.split(":")[1].trim()), "Sort by: Most Relevant does not default sort type");
		else
			assertTrue(searchResultPage.isSortTypeDisplayed(Constants.DEFAULT_SORT_TYPE), "Sort by: Most Relevant does not default sort type");

	}

	public void verifyTabsAreDisplayed() {
		for (String tab : Constants.LIST_TABS) {
			Logger.verify("Verify " + tab + " tab is displayed");
			assertTrue(searchResultPage.isTabDisplayed(tab), tab + " tab is not displayed");
		}
	}

	public void searchItem(Item item) {
		Logger.info("From Hompage, enter \"" + item.getName() + "\" into Search textbox and click Search button");
		generalPage.search(item.getName());
	}

	public void initTestCaseData() {
		Logger.tc("TC_SRP_001 Items and Packages and Meals tabs are displayed");
		Logger.to("TO_SRP_01 Tab Item, Packages, Meals display with a count number");
		Logger.to("TO_SRP_02 After searching, Sort by: Most Relevant displays as default in Sort Type ComboBox");
		Logger.to("TO_SRP_03 Count number in Items, Packages, Meals Tabs are greater than zero");

		item.init(SkuType.PACKAGES, Recipient.MYSELF);
	}

}
