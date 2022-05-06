package com.omahasteaks.tests;
/*package tests;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.DriverUtils;
import com.logigear.helper.BrowserSettingHelper;

import data.objects.Account;
import utils.common.Common;
import utils.common.Constants;
import utils.config.GuiceInjection;

@Guice(moduleFactory = GuiceInjection.class)
public class TestBase {
    protected Account account;

    @Parameters({ "browser" })
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(String browser, Method method, ITestContext context) throws Throwable {
	DriverProperty property = BrowserSettingHelper.getDriverProperty(Constants.BROWSER_SETTING_FILE, browser, method.getName());
	DriverUtils.getDriver(property);
	DriverUtils.setWaitForAjax(false);
	DriverUtils.setPageLoadTimeout(Constants.LONG_TIME);
	DriverUtils.setTimeOut(Constants.SHORT_TIME);
	DriverUtils.maximizeBrowser();
	DriverUtils.navigate(Constants.OMAHA_URL_OLDMODE);
	Common.waitForPageLoad();
	try {
	    DriverUtils.navigate(Constants.OMAHA_URL);
	} catch (Exception e) {
	    context.setAttribute("isSkipped", true);
	    context.setAttribute("message", e);
	}
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp(ITestResult result) {
	if (DriverUtils.getWebDriver() != null) {
	    try {
		DriverUtils.getWebDriver().close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    DriverUtils.quitBrowser();
	}
    }
}
*/