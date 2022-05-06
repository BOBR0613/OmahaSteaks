package com.omahasteaks.data.enums;

public enum LinksCustomerService {

	PRIVACY_POLICY("Privacy Policy"), GUARANTEE("Guarantee"), FREQUENTLY_ASKED_QUESTIONS("Frequently Asked Questions"), SHIPPING("Shipping"), CONTACT_US("Contact Us"), PRODUCT_SAFETY("Product Safety"), STEAK_CUT_INFORMATION("Steak Cut Information"), POINTS_OF_DISTINCTION("Points of Distinction");

	private String value;

	private LinksCustomerService(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
