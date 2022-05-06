/*package com.omahasteaks.page;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;

public interface PaymentAndReviewPage extends GeneralPage {
	void applyGiftNumber(String giftnumber);

	void placeMyOrder();

	void fillCreditCardNumber();

	String[] getBillingAddress();

	String[] getShippingAddress(Recipient recipient);

	void clickRecipientEditShippingAddressLink(Recipient recipient);

	void clickRecipientViewAllShippingOptions(Recipient recipient);

	void clickEditBillingAddressLink();

	void selectShippingOptionByName(String shippingType);

	boolean isCheckboxShippingOptionByNameOfRecipientChecked(Recipient recipient, String shippingType);

	void continueCheckout();

	String getGreetingMessageByRecipient(Recipient recipient);

	Boolean isGreetingCardByRecipientAndCardTypeExisted(Recipient recipient, String cardType);

	String getShippingDateByNameOfRecipientChecked(Recipient recipient, String shippingType);

	String selectAvailableShippingDay();

	String getSelectedDay();

	String getInvalidMessageByField(AddressFields field);

	void fillCreditCardNumber(String creditCardNumber, boolean isFutureDate);

	void placeMyOrderIgnoreError();
}
*/