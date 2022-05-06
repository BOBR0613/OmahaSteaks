package com.omahasteaks.data.enums;

import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public enum SideMenuItem {
    PRIVATE_RESERVE, STOCK_UP, PORK, MEALS, FREE_SHIPPING_PACKAGES, VALUE_PACKAGES, LAST_MINUTE_GIFT, SLOW_COOKER_AND_SKILLET_MEALS, CUSTOM_PACKAGE, DOG_TREATS, SALE, ULTIMATE_PACKAGES;

    public String getValue() {
	switch (this) {
	case PRIVATE_RESERVE:
	    return "Private Reserve";
	case FREE_SHIPPING_PACKAGES:
	    return "Free Shipping Packages";
	case STOCK_UP:
	    return "Stock-Up";
	case MEALS:
		return "Meals";
	case PORK:
		return "Pork";
	case VALUE_PACKAGES:
	    if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
		return "Value Packages";
	    } else
		return "Value Assortments";
	case LAST_MINUTE_GIFT:
	    if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
		return "LAST-MINUTE GIFT";
	    } else if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE)) {
		return "Last Minute Gift";
	    }
	case SLOW_COOKER_AND_SKILLET_MEALS:
	    return "Slow Cooker & Skillet Meals";
	case CUSTOM_PACKAGE:
	    if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
		return "Custom Packages";
	    } else
		return "Custom Packages & Meals";
	case DOG_TREATS:
	    return "Dog Treats";
	case SALE:
	    return "SALE";
	case ULTIMATE_PACKAGES:
	    if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE))
		return "Ultimate Packages";
	default:
	    return "";
	}
    }

}
