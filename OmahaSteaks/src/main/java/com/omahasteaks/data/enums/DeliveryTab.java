package com.omahasteaks.data.enums;

public enum DeliveryTab {
    FREE_EXPRESS_DELIVERY("Free Express Delivery"), FREE_RUSH_DELIVERY("Free Rush Delivery");
    private String value;

    private DeliveryTab(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
