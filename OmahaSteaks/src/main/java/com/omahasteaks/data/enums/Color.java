package com.omahasteaks.data.enums;

public enum Color {
    RED("#890419"), DARK_GRAY("#434446"), ORANGE("#af4600");
    
    String asHex;
    
    Color(String asHex) {
	this.asHex = asHex;
    }
    
    public String asHex() {
	return asHex;
    }
}
