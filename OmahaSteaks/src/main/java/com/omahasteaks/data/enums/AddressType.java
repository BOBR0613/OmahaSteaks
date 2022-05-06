package com.omahasteaks.data.enums;

public enum AddressType {
	RESIDENTIAL_ADDRESS("Residential Address"), COMPANY_ADDRESS("Company Address");

	private String value;

	private AddressType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
