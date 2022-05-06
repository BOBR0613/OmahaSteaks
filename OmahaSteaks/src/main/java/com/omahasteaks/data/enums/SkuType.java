package com.omahasteaks.data.enums;

public enum SkuType {
	ITEM("Items"), PACKAGES("Packages"), MEAL("Meals"), WINE(""), UNDER50(""), OVER100(""), SEAFOOD("Seafood"), 
	FREESHIP("Free Shipping"), DESSERT("Dessert"), BURGERS("Burgers");

	private String value;

	private SkuType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}