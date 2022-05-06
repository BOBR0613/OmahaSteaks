package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ManageRewardsData;
import com.omahasteaks.data.enums.SearchType;
import com.omahasteaks.data.objects.RewardData;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_014_SearchByKeyWord extends TestBase_CR {

	@Inject
	ManageRewardsData lstRewardData;

	@Test
	public void TC_CRP_014_Search_By_KeyWord() {
		initTestCaseData();
		
		for (RewardData rewardData : lstRewardData.getLstRewardSearchData()) {
			String keyWord = rewardData.getKeyWord();
			searchSKUWithKeyWord(keyWord);
			verifySearchResultPageDisplaysUpTo10ProductsPerOnePage();
			verifyTheListOfItemDisplaysCorrectly(keyWord);
		}
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================
	private void verifyTheListOfItemDisplaysCorrectly(String keyWord) {
		Logger.verify("Verify the list of item is displayed correctly");
		assertTrue(rewardGeneralPage.isListItemDisplayedCorrectly(lstRewardData.getResultsBySearchKeyword(keyWord)), "The list of item does not display correctly");
	}

	private void searchSKUWithKeyWord(String keyWord) {
		Logger.info("Search with keyword: " + keyWord);
		rewardGeneralPage.search(keyWord, SearchType.KEYWORD);
	}

	private void verifySearchResultPageDisplaysUpTo10ProductsPerOnePage() {
		Logger.verify("Verify that the list of SKU displays when searching with search type \"Point Value\"");
		assertTrue(rewardGeneralPage.getNumberOfProduct() <= 10, "The search result page displays more than 10 products per one page");
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_014   The list of SKU is displayed correctly when searching by keyword");
		Logger.to("TO_CRP_059	The list of SKU displays when searching with search type \"Keyword\"");
		Logger.to("TO_CRP_060	Search result page displays up to 10 products per one page");
	}

}
