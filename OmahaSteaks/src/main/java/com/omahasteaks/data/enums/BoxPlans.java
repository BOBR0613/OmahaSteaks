package com.omahasteaks.data.enums;

public enum BoxPlans {
    STEAK_BOX_PLANS("Steak Box Plans"), GRILL_BOX_PLANS("Grill Box Plans"), STOCK_UP_ALL_AT_ONCE("Stock Up All At Once"),  GIFT_ALL_AT_ONCE("Gift All At Once");
    private String value;

    private BoxPlans(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
