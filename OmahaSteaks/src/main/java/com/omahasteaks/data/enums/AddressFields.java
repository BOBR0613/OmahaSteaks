package com.omahasteaks.data.enums;

public enum AddressFields {
    FIRST_NAME("First name"), LAST_NAME("Last name"), COMPANY_NAME("Company name"), ADDRESS1("Address1"), ADDRESS2("Address2"), CITY("City"), STATE("State"), ZIP_CODE("Zip Code"), PHONE("Phone Required"), EMAIL("Email Address"), CONFIRM_EMAIL("Email Address"), BIRTHDAY("Invalid Birth Date"), BIRTHDAY_UNDER_21("Restricted Item"), ACCEPTANCE("Acceptance Required"), CREDIT_CARD("Credit Card"), CREDIT_CARD_DATE("Credit Card Date"), STREET_ADDRESS("Street Address");
    private String value;

    AddressFields(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

    public String getValue2SC() {
	switch (this) {
	case FIRST_NAME:
	    return "First Name";
	case LAST_NAME:
	    return "Last Name";
	case ADDRESS1:
	    return "Street Address";
	case PHONE:
	    return "Phone";
	case EMAIL:
	    return "Email";
	case CREDIT_CARD:
	    return "Card Number";
	case CREDIT_CARD_DATE:
	    return "Card Expiration";
	default:
	    return getValue();
	}
    }
	
	public String getValueCollectionCenter() {
		switch (this) {
		case PHONE:
		    return "Phone Number";
		case EMAIL:
		    return "Email Address";
		default:
		    return getValue();
	}
    }
}
