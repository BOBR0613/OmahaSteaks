package com.omahasteaks.page;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.CustomerAddress;

public interface BillingAddressPage extends GeneralPage {
	void fillBillingAddress(CustomerAddress billingAddress);

	void fillBillingAddressExceptField(CustomerAddress billingAddress, AddressFields field);

	void continueCheckout();

	void confirmAddress();

	String getErrorMessageByField(AddressFields field);

	boolean isRequiredBirthdayLabelDisplayed();

	String getInvalidMessageByField(AddressFields field);

	String getAddressWarningMessage();
}