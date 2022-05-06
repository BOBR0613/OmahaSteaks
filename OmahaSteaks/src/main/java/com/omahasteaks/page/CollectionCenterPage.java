package com.omahasteaks.page;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;

public interface CollectionCenterPage extends GeneralPage {

	/**
	 * Submit collection number
	 */
	void submitCollectionNumber(String collectionNumber);

	/**
	 * click on the first SKU in Gourmet Custom Certificate page
	 */
	void selectFirstSKU();

	/**
	 * get first SKU's information in the Gourmet Custom Certificate page
	 */
	SKU getFirstSKUInfoFromGourmetCustomCertificatePage();

	/**
	 * Fill information into all fields in 'Shipping Information' page.
	 */
	void fillShippingInformation(CustomerAddress shippingAddress);

	/**
	 * Select an address type in 'Shipping Information' page.
	 */
	void selectAddressType(AddressType residentialAddress);

	void clickContinueButton();

	String getCurrentTabName();

	/**
	 * Verify shipping method is selected in 'Shipping Options' page
	 */
	Boolean isShippingMethodSelected(String shippingMethod);

	/**
	 * Check delayed arrival date text box displays in 'Shipping Options' page
	 */
	Boolean isDelayedArrivalDateTextboxDisplayed();

	/**
	 * Input delayed arrival date in 'Shipping Options' page
	 */
	void inputDelayedArrivalDate(String delayedArrivalDate);

	/**
	 * In 'Order Review' page, get SKU information.
	 */
	SKU getSKUInformation();

	/**
	 * In 'Shipping Information' page, click on 'Back To Exclusive Offer' Link
	 */
	void clickBackToExclusiveOfferLink();

	/**
	 * In 'Shipping Information' page, get list shipping address fields
	 */
	String[] getListFieldsInShippingInformationPage();

	/**
	 * In 'Order review' page, verify a part displays in the 'Shipping bag' table
	 */
	Boolean isShippingPartDisplaysInShippingTable(String partName);

	/**
	 * Check 'Shopping bag' title in 'Order Review' page
	 */
	Boolean isShoppingBagTitleDisplayedCorrectly(String recipientName);

	/**
	 * Check 'Please Review Your Order' message displays correctly in 'Order Review'
	 * page
	 */
	Boolean isPleaseReviewYourOrderMsgDisplayed();

	/**
	 * In 'Order Review' page, get shipping information
	 */
	String[] getShippingInformation();

	/**
	 * In 'Shipping Options' page, get delayed arrival date
	 */
	String getDelayedArrivalDateInShippingOptionsPage();

	/**
	 * In 'Shipping Information' page, get Shipping Address was filled before
	 */
	String[] getShippingAddressInShippingInformationPage();

	/**
	 * In 'Collection Center' page, click Term Agreement link in footer
	 */
	void clickTermAgreementLinkInFooter(LinksFooter nameLink);

	/**
	 * In 'Shipping Information' page, fill shipping address except field
	 */
	void fillShippingAddressExceptField(CustomerAddress shippingAddress, AddressFields field);

	/**
	 * In 'Shipping Information' page, get error message by field
	 */
	String getErrorMessageByField(AddressFields field);

	/**
	 * In the 'Shipping Information' page, click on each error message after leaving
	 * all fields empty
	 */
	void clickOnAddressFieldLinkInListErrorMessage(AddressFields addressField);

	/**
	 * In the 'Shipping Information' page, check the element is focused
	 */
	Boolean isAddressFieldFocused(AddressFields addressField);

	/**
	 * In the 'Shipping Information' page, enter email address
	 */
	void enterEmailAddress(String email);

	/**
	 * Get the header of the Privacy Policy page after clicking on "Privacy Policy"
	 * link on the footer.
	 */
	Boolean isHeaderOfPrivacyPolicyPageDisplayed();
	
	/**
	 * In the 'Shipping Information' page, get warning message in email address field
	 */
	String getWarningMessage();
	
	/**
	 * In the 'Shipping Options' page, get warning message	 
	 */
	String getErrorgMessageInShippingOptions();

	/**
	 * In Gourmet Custom Certificate page, check button Redeem now displayed.
	 */
	Boolean isButtonRedeemNowDisplayed();

	/**
	 * In Gourmet Custom Certificate page, check "Gourmet Custom Certificate" text displays correctly.
	 */
	Boolean isGourmetCustomCertificateTextDisplayed();

	/**
	 * In Gourmet Custom Certificate page, verify there is no SKU out of stock by checking all SKU have to REDEEM NOW button.
	 */
	Boolean isSKUOutOfStock();
}
