package com.omahasteaks.data.enums;

public enum PromotionLocation {
	TOP_LEFT_BANNER("topLeftBanner"), TOP_RIGHT_BANNER("topRightBanner"), DELIVERY_OPTIONS("deliveryOptions");
	
	String location;

	PromotionLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
}
