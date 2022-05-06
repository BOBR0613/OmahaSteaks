package com.omahasteaks.tests.SteakloverRewardsMembership;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.logigear.exception.DriverCreationException;
import com.logigear.helper.BrowserSettingHelper;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_SLR_013_RecognizedGoldAccount_SignInLinkDisplaysInShoppingCartPage extends TestBase_SLR {

	@Inject
	SKU sku;

	Set<Cookie> allCookies;

	Map<String, String> ieCaps;

	@Test
	public void TC_SLR_013_Recognized_Gold_Account_SignInLinkDisplaysInShoppingCartPage() throws DriverCreationException {
		initTestCaseData();

		signIn(account);

		Common.closeBrowser();

		getCookiesOfPreviousVisiting();

		navigateToOmastkSite();

		searchAndAddSKUToCart(sku);

		verifyTheSignInLinkIsDisplayed();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void navigateToOmastkSite() throws DriverCreationException {
		Logger.info("Re-navigate to Omastk.com site ");
		if (Common.getCurrrentBrowser().equals(Constants.BROWSER_IE))
			property = BrowserSettingHelper.updateCapabilities(property, ieCaps);
		DriverUtils.getDriver(property);
		DriverUtils.setRunningMode("desktop");
		DriverUtils.setWaitForAjax(false);
		DriverUtils.setPageLoadTimeout(Constants.LONG_TIME);
		DriverUtils.maximizeBrowser();
		DriverUtils.navigate(Constants.runningURL.get(Constants.SITE));

		addCookies();
	}

	private void getCookiesOfPreviousVisiting() {
		if (!Common.getCurrrentBrowser().equals(Constants.BROWSER_IE)) {
		//	WebDriver driver = Common.navigateToOmastkSiteWithExistingBrowserProfile(userDir);
		//	allCookies = driver.manage().getCookies();
		//	driver.close();
		}
	}

	private void verifyTheSignInLinkIsDisplayed() {
		Logger.verify("Verify the 'Sign In' link is displayed on the 'Steaklover Reward' section in the 'Shopping Cart' page when re-navigating to 'omastk.com' site with the recognized Gold account");
		System.out.println("Account email: " + account.getEmail());
		assertTrue(shoppingCartPage.isJoinSlrGoldButtonShown(), "\"SIGN In\" link does not display in \"Steaklover Rewards\" section");
	}

	private void addCookies() {
		if (Common.getCurrrentBrowser().equals(Constants.BROWSER_IE))
			return;
		Logger.info("Add stored cookies on the prepare steps to driver");
		WebDriver driver = DriverUtils.getWebDriver();
		if (Common.getCurrrentBrowser().equals(Constants.BROWSER_FIREFOX))
			driver.manage().deleteAllCookies();
	//	for (Cookie cookie : allCookies) {
	//		driver.manage().addCookie(cookie);
	//	}
		Logger.info("Refresh browser to apply all cookies");
		driver.navigate().refresh();
	}

	private void initTestCaseData() {
		Logger.tc("RECOGNIZED Gold account - The 'SIGN IN' link is displayed on the 'Shopping Cart' page");
		Logger.to("Verify the 'Sign In' link is displayed on the 'Steaklover Reward' section in the 'Shopping Cart' page when re-navigating to 'omastk.com' site with the recognized Gold account");
		sku.init(SkuType.ITEM, Recipient.MYSELF);
		account = Constants.LIST_ACCOUNTS.getAccountByEmail("slrgold01@omahasteaks.com");
		ieCaps = new HashMap<String, String>();
		ieCaps.put("ie.ensureCleanSession", "false");
		Common.modalDialog.closeSavorDialog();
	}
}
