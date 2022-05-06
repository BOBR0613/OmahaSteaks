package com.omahasteaks.tests;

import java.lang.reflect.Method; 

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;
 
import com.logigear.driver.DriverProperty;
import com.logigear.driver.DriverUtils;
import com.logigear.helper.BrowserSettingHelper;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;
import com.omahasteaks.utils.config.GuiceInjection;

@Guice(moduleFactory = GuiceInjection.class)
public class TestBase_RewardCollection {
	
	@BeforeSuite()
    public void start() {
    
	}

	
	 @Parameters({ "browser" })
	    @BeforeMethod(alwaysRun = true)
	    public void beforeMethod(String browser, Method method, ITestContext context) throws Throwable {
		String mode = context.getCurrentXmlTest().getParameter("mode");
		
		DriverProperty property = BrowserSettingHelper.getDriverProperty(Constants.BROWSER_SETTING_FILE, browser, method.getName());
		DriverUtils.getDriver(property);
		DriverUtils.setRunningMode(mode);
		DriverUtils.setWaitForAjax(false);
		DriverUtils.setPageLoadTimeout(Constants.LONG_TIME);
		DriverUtils.setTimeOut(Constants.SHORT_TIME);
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_ANDROID) || RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_TABLET)) {
		    DriverUtils.setPageLoadTimeout(Constants.SHORT_TIME);
		}
		DriverUtils.maximizeBrowser();
		try {
		    context.setAttribute("isSkipped", false);
		    DriverUtils.navigate(Constants.REWARD_COLLECTTION_URL);
		    if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_ANDROID) || RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_TABLET)) {
			DriverUtils.getWebDriver().navigate().refresh();
			DriverUtils.setPageLoadTimeout(Constants.LONG_TIME);
		    }
		    Common.waitForPageLoad();
		} catch (Exception e) {
		    context.setAttribute("isSkipped", true);
		    context.setAttribute("message", e);
		}
		Common.modalDialog.closeAppBanner();	
		   
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
