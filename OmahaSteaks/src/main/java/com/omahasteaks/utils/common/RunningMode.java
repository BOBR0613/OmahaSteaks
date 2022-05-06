package com.omahasteaks.utils.common;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.logigear.driver.DriverUtils;

public class RunningMode {

    /**
     * @return DESKTOP/MOBILE mode
     */
    public String getRunningMode() {
	return DriverUtils.getRunningMode();
    }

    /**
     * @return running platform
     */
    public static String getCurrentPlatform() {
	if (DriverUtils.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
	    Capabilities caps = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCapabilities();
	    if (!caps.toString().contains("udid"))
		for (String item : caps.getPlatform().getPartOfOsName()) {
		    String os = item.toLowerCase();
		    if (os.contains(Constants.PLATFORM_MAC))
			return Constants.PLATFORM_MAC;
		}
	    if ((DriverUtils.getDriverType().equals(Constants.BROWSER_SAFARI)) && (!caps.toString().contains("udid"))) {
		return Constants.PLATFORM_MAC;
	    }
	    if ((DriverUtils.getDriverType().equals(Constants.BROWSER_SAFARI)) && (caps.toString().contains("udid"))) {
		return Constants.PLATFORM_IPAD;
	    }
	    if (caps.toString().contains("deviceName"))
		return Constants.PLATFORM_TABLET;
	    return Constants.PLATFORM_DESKTOP;
	} else if (DriverUtils.getDriverType().equals(Constants.BROWSER_SAFARI)) {
	    return Constants.PLATFORM_IPHONE;
	} else {
	    return Constants.PLATFORM_ANDROID;
	}
    }

}
