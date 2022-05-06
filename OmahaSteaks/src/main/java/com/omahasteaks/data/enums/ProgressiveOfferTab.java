package com.omahasteaks.data.enums;

import java.util.HashMap;
import java.util.Map;

import com.omahasteaks.utils.common.Constants;

public enum ProgressiveOfferTab {
    EXCLUSIVE_OFFERS, $19_99_SALE, SHIPPED_FREE_PACKAGES, SKILLET_MEALS, EARN_AN_EXTRA_25_DOLLARS_OFF, SUMMER_GRILLING, BULK_PROTEINS, BEST_SELLERS, SURF_TURF, WORLD_PORT_MEALS;

    public String getValue() {
	switch (this) {
	case EXCLUSIVE_OFFERS:
	    return "Exclusive Offers";
	case $19_99_SALE:
	    return "$19.99";
	case SHIPPED_FREE_PACKAGES:
	    return "Shipped Free Packages";
	case SKILLET_MEALS:
	    return "Skillet Meals";
	case EARN_AN_EXTRA_25_DOLLARS_OFF:
	    return "New Sign Ups - $40 off";
	case SUMMER_GRILLING:
	    return "Summer Grilling";
	case BULK_PROTEINS:
	    return "Bulk Proteins";
	case BEST_SELLERS:
	    return "Best Sellers";
	case SURF_TURF:
	    return "Surf & Turf";
	case WORLD_PORT_MEALS:
	    return "World Port Meals";
	default:
	    return "";
	}
    }

    @SuppressWarnings("serial")
    private static Map<String, ProgressiveOfferTab[]> progressiveOfferTab = new HashMap<String, ProgressiveOfferTab[]>() {
	{
	    //put("wps", new ProgressiveOfferTab[] { BULK_PROTEINS, BEST_SELLERS, SURF_TURF, WORLD_PORT_MEALS });
	    put("omk", new ProgressiveOfferTab[] { EXCLUSIVE_OFFERS, $19_99_SALE, SHIPPED_FREE_PACKAGES, EARN_AN_EXTRA_25_DOLLARS_OFF });
	}
    };
    
    public static ProgressiveOfferTab[] asArrays() {
	return progressiveOfferTab.get(Constants.SITE);
    }
}
