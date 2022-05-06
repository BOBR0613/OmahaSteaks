package com.omahasteaks.data.enums;

public enum LinksFooter {
    TERMS_OF_USE("Terms of Use"), PRIVACY_POLICY("Privacy"), HELP_CENTER("Help Center"), PRIVACY("Privacy"), SITEMAP("Sitemap"), RECIPE_CENTER("Recipe Center");

    private String value;

    private LinksFooter(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }
}
