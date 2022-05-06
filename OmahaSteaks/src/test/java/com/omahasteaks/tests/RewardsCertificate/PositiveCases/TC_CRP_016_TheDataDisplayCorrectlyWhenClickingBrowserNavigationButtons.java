package com.omahasteaks.tests.RewardsCertificate.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.tests.RewardsCertificate.TestBase_CR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_CRP_016_TheDataDisplayCorrectlyWhenClickingBrowserNavigationButtons extends TestBase_CR {
	CustomerAddress address;

	String dateTime;
	String[] infomationInShippingInformationPage;

	@Inject
	ListRewardSKUs lstSKU;

	@Inject
	RewardSKU mySKU;

	@Inject
	ListAddresses lstAddresses;

	@Test
	public void TC_CRP_016_The_Data_Display_Correctly_When_Clicking_Browser_Navigation_Buttons() {
		initTestCaseData();

		addSKUToCartAndGoToShoppingCart();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);

		fillShippingInformation();

		clickBackButtonOnBrowser();
		Common.waitForPageLoad();
		
		clickGoForwardOfBrowser();
		Common.waitForPageLoad();

		verifyShippingInformationAreNotLost();

		clickBackButtonOnBrowser();
		Common.waitForPageLoad();

//		verifyShoppingCartPageDisplays();

		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);

		fillResidentialAddressAndClickContinue(address);

		fillDelayedArrivalDate(dateTime);

		clickBackButtonOnBrowser();

		verifyShippingInformationPageDisplays();

		clickGoForwardOfBrowser();

		verifyInformationInShippingOptionPageAreNotLost();
		
		clickContinuelink();
		
		clickBackButtonOnBrowser();
		
		verifyShippingOptionsPageDisplays();		
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================	
	private void verifyShippingOptionsPageDisplays() {
		Logger.verify("Verify \"Shipping Options' page\" displays correctly after clicking go back button on browser");
		assertEquals(rewardGeneralPage.getCurrentTabName(), Constants.TITLE_INFORMATION_OPTIONS, "Shipping Options page does not display.");
	}
	
	private void clickGoForwardOfBrowser() {
		Logger.info("Click \"Go Forward\" button on browser");
		Common.goForward();
	}

	private void verifyInformationInShippingOptionPageAreNotLost() {
		Logger.verify("Verify the information in filled fields in \"Shipping Information\" page are not lost");
		assertTrue(rewardShippingOptionPage.isShippingMethodSelected(Constants.STANDARD_SHIPPING), "Standard shipping option does not select.");
		assertEquals(rewardShippingOptionPage.getDelayedArrivalDateInShippingOptionsPage(), dateTime, "The date time is not the same.");
	}

	private void verifyShippingInformationPageDisplays() {
		Logger.verify("Verify \"Shipping Information' page\" displays correctly after clicking go back button on browser");
		assertEquals(rewardGeneralPage.getCurrentTabName(), Constants.TITLE_SHIPPING_INFORMATION, "Shipping Information page does not display.");
	}

	private void fillShippingInformation() {
		fillShippingInformation(address);
		infomationInShippingInformationPage = rewardShippingInfoPage.getShippingAddressInShippingInformationPage();
	}

	private void verifyShippingInformationAreNotLost() {
		Logger.verify("Verify the information in filled fields in \"Shipping Information\" page are not lost by clicking forward button on browser");
		assertEquals(rewardShippingInfoPage.getShippingAddressInShippingInformationPage(), infomationInShippingInformationPage, "The shipping information are not the same.");
	}

	private void clickBackButtonOnBrowser() {
		Logger.info("Click \"Go Back\" button on browser");
		Common.goBack();
	}

	private void initTestCaseData() {
		Logger.tc("TC_CRP_016 - The data display correctly when clicking browser navigation buttons");
		Logger.to("TO_CRP_036	The information in filled fields in 'Shipping Information' page are not lost by clicking forward button of browser");
		Logger.to("TO_CRP_037	In the shopping information page, user can go to back the 'Shopping cart' page after clicking on 'back' button of browser");
		Logger.to("TO_CRP_038	In Shipping Option Page: User can go to back 'Shipping Information' page by clicking back button of browser");
		Logger.to("TO_CRP_039	In Shipping Option Page: The information in filled fields in 'Shipping Information' page are not lost when clicking back button of browser");
		Logger.to("TO_CRP_040	In Order review page:User can go to back 'Shipping Option' page after clicking back button of browser");
		mySKU.init(Recipient.MYSELF, 10);
		address = lstAddresses.getDefaultShippingAddress();
		dateTime = Common.randomNewDate("MM/dd/yyyy", 20);
	}
	
	private void addSKUToCartAndGoToShoppingCart() {
		searchSKUWithPoint("10");
		rewardGeneralPage.selectFirstItem();
		addSKUToCart(mySKU);  
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();  
			rewardGeneralPage.selectSecondItem();
			addSKUToCart(mySKU); 
		}
		addSKUToCart(mySKU); 
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back(); 
			DriverUtils.getWebDriver().navigate().back(); 
			rewardGeneralPage.selectThirdItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();  
			rewardGeneralPage.selectFourthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();  
			rewardGeneralPage.selectFourthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();  
			rewardGeneralPage.selectFifthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();  
			rewardGeneralPage.selectSixthItem();
			addSKUToCart(mySKU); 
		}
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();  
			rewardGeneralPage.selectSeventhItem();
			addSKUToCart(mySKU); 
		}
		goToShoppingCartPage();
	}
}
