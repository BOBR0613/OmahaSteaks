package com.omahasteaks.data.enums;

public enum SearchType {
	KEYWORD("Keyword"), POINT_VALUE("Point Value");

	private String value;

	SearchType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
