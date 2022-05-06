package com.omahasteaks.data.enums;

public enum LinksTopMenu {
    STORES("Stores"), BLOG("Blog"), FUNDRAISING("Fundraising"), REWARDS("Rewards"), SIGN_IN("Sign In");

    private String value;

    private LinksTopMenu(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
