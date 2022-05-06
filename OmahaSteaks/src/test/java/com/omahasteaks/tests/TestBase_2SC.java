package com.omahasteaks.tests;

import java.lang.reflect.Method;  
import java.util.Map;
 
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import org.testng.annotations.Parameters;

import com.google.inject.Inject;
import com.logigear.driver.DriverProperty;
import com.logigear.driver.DriverUtils;
import com.logigear.helper.BrowserSettingHelper;

import com.omahasteaks.data.objects.Account; 
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;
import com.omahasteaks.utils.config.GuiceInjection; 

@Guice(moduleFactory = GuiceInjection.class)
public class TestBase_2SC {
	protected String userDir;
	protected DriverProperty property; 
	
	@Inject
	protected Account account;

	
	@BeforeSuite() 
    public void start() {
	}
 

	@SuppressWarnings("unchecked")
	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(String browser, Method method, ITestContext context) throws Throwable {
		String mode = context.getCurrentXmlTest().getParameter("mode"); 


		property = BrowserSettingHelper.getDriverProperty(Constants.BROWSER_SETTING_FILE, browser, method.getName());
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
 			DriverUtils.navigate(Constants.OMAHA_BASE);
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
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP) && DriverUtils.getDriverType().contains(Constants.BROWSER_CHROME) && !RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_TABLET)) {
			Capabilities caps = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCapabilities();
			Map<String, String> returnedCapsMap = (Map<String, String>) caps.getCapability(DriverUtils.getDriverType());
			userDir = returnedCapsMap.get("userDataDir");
		} else if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP) && DriverUtils.getDriverType().contains(Constants.BROWSER_FIREFOX)) {
			Capabilities caps = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCapabilities();
			userDir = caps.getCapability("moz:profile").toString();
		}
	}

    @AfterMethod(alwaysRun = true)
    public void cleanUp(ITestResult result) {
	    DriverUtils.quitBrowser();
    }
    
    @AfterSuite()
    public void sendHtmlReport() {
      Constants.DB.sendHTMLReport();
    }
    
    
}
