package com.omahasteaks.page;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.objects.CustomerAddress;

public interface RewardShippingInfoPage extends RewardGeneralPage {

	/**
	 * In Shipping Information page, select address type before fill all fields
	 */
	void selectAddressType(AddressType addressType);

	/**
	 * In Shipping Information page, fill the information of recipient into all
	 * fields
	 */
	void fillShippingInformation(CustomerAddress shippingAddress);

	/**
	 * Get list fields displays in Shipping Information page
	 */
	String[] getListFieldsInShippingInformationPage();

	/**
	 * In Shipping Information page, get all the information has been filled
	 */
	String[] getShippingAddressInShippingInformationPage();

	String getErrorMessageByField(AddressFields field);

	void fillShippingAddressExceptField(CustomerAddress shippingAddress, AddressFields field);

	void clickOnAddressFieldLinkInListErrorMessage(AddressFields addressField);

	Boolean isAddressFieldFocused(AddressFields addressField);
}
