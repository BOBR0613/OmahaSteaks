package com.omahasteaks.data.enums;

public enum MenuTabsInStoresPage {
    HALF_OFF_DEALS("50% Off Deals"), STORE_LOCATIONS("Store Locations"), STORE_SPECIALS("Specials"), CAREERS("Careers");
    private String value;

    private MenuTabsInStoresPage(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
