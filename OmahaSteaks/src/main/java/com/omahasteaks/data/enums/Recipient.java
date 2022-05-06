package com.omahasteaks.data.enums;

import java.util.HashMap;
import java.util.Map;

public enum Recipient {
	MYSELF("Myself"), THONG_NGUYEN("Thong Nguyen"), ITQA("IT QA"), THAO_NHO("Thao Nho"), KIM_ANH("Kim Anh"), ALL_CARTS("All Carts"), EMPTY(""), SPACE_CHARACTER("                "), MORE_15_CHARACTER("1234567890123456789"), NEW_RECIPIENT("Newrecipient"), TC_2SCOP_004("Acc_tc2scop004"), DD("Diep"), SHOPPING_NAME_TC_CRN_006("      ");
	private String value;
	private static Map<String, Recipient> map = new HashMap<String, Recipient>();
	static {
		for (Recipient recipient : Recipient.values()) {
			map.put(recipient.value, recipient);
		}
	}

	Recipient(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Recipient valueof(String value) {
		return map.get(value);
	}

}
