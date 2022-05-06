package com.omahasteaks.page;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;

public interface ShippingAddressPage2SC extends GeneralPage {

    /**
     * In Shipping Address page, click add an email address link
     */
    void clickAddAnEmailAddressLink();

    /**
     * In Shipping Address page, click add a phone number link
     */
    void clickAddAPhoneNumberLink();

    /**
     * In Shipping Address page, click continue button
     */
    void clickContinueButton();

    /**
     * In Shipping Address page, click edit address button in Please confirm address
     * modal
     */
    void clickEditAddressButtonInPleaseConfirmAddressModal();

    /**
     * In Shipping Address page, click Edit cart link
     */
    void clickEditCartLink();

    /**
     * In Shipping Address page, click Edit link beside Email check box
     */
    void clickEditLinkBesideEmailCheckbox();

    /**
     * In Shipping Address page, click edit link beside Phone check box
     */
    void clickEditLinkBesidePhoneCheckbox();

    /**
     * In Shipping Address page, click Edit This Address link
     */
    void clickEditThisAddressLink();

    /**
     * In Shipping Address page, click Enter A New Address link
     */
    void clickEnterANewAddressLink();

    /**
     * In Shipping Address page, click Next button when there are 2 or more Shipping
     * addresses
     */
    void clickNextButton();

    /**
     * In Shipping Address Page, after select an existing Recipient. Clicking 'Edit'
     * link beside Email check box (if this Address does not have email -> click
     * 'add an email address') and edit this address to new address
     */
    void editContactByClickingLinkInEmailSection();

    /**
     * In Shipping Address Page, after select an existing Recipient. Clicking "Edit"
     * link beside Phone check box and edit this address to new address
     */
    void editContactByClickingLinkInPhoneSection();

    /**
     * In Shipping Address page, enter Gift message text into Gift message text box
     */
    void enterGiftMessage(String giftMessage);

    /**
     * In Shipping Address page, fill all informations into all fields
     */
    void fillShippingAddress(CustomerAddress shippingAddress);

    /**
     * In Shipping Address page, fill email address into email address text box
     */
    void fillEmailShippingAddress(String email);

    /**
     * In Shipping Address page,at Shipping Address modal: fill all informations
     * into all fields
     */
    void fillShippingAddressInModal(CustomerAddress shippingAddress);

    /**
     * In Shipping Address page, generate Recipient address information
     */
    String generateRecipientAddressInfo(CustomerAddress address);

    /**
     * In Shipping Address page, get Notify text displayed
     */
    String getNotifyText();

    /**
     * In Shipping Address page, get Recipient address text displayed
     */
    String getRecipientAddressText();

    /**
     * In Shipping Address page, get list Recipient address information
     */
    String[] getRecipientAddress();

    /**
     * In Shipping Address page, get selected Recipient name in Send To drop down
     * list
     */
    String getSelectedRecipientNameInSendToDropDownList();

    /**
     * In Shipping Address page, verify SKU exists in cart
     */
    boolean doesSKUExist(SKU sku);
    
    boolean isSKUExisted(SKU sku);

    /**
     * In Shipping Address page, open Send To drop down list
     */
    void openSendToDropDownList();

    /**
     * In Shipping Address page, select Add New Address from Send To drop down list
     */
    void selectAddNewAddressFromSendToDropDownList();

    /**
     * In Shipping Address page, select the Greeting Card in Greeting Card section
     */
    String selectFirstGreetingCard();

    /**
     * In Shipping Address page, select Gift Options in Gift Wrap check box
     */
    void selectGiftOptionGiftWrap();

    /**
     * In Shipping Address page, select Gift Options in Greeting Card to add
     */
    void selectGiftOptionGreetingCard();

    /**
     * In Shipping Address Page, select an existing Recipient
     */
    void selectNextRecipientAddressBySelected();

    /**
     * In Shipping Address Page, select an existing Recipient in Send To drop down
     * list
     */
    void selectRecipientInSendToDropDownList(Recipient recipient);

    /**
     * In Shipping Address Page, at Shipping Address modal: click Update Contact
     * button
     */
    void updateContactInModal();

    /**
     * In Shipping Address Page, get error message displayed by field when an error
     * occurs at field
     */
    String getErrorMessageByField(AddressFields addrField);

    /**
     * In Shipping Address page, get error message displays at bottom
     */
    String getErrorMessageAtBottom();

    /**
     * In Shipping Address page, click View Items link displays in My Cart section
     */
    void clickViewItemsLink();

    /**
     * In Shipping Address page, close View Items page
     */
    void closeViewItemsPage();

    /**
     * In Shipping Address page, get Progress tabs title
     */
    String getProgressTabsTitle();

    /**
     * In Shipping Address page, get Shipping error message displayed
     */
    String getShippingErrorMessage();

    /**
     * In Shipping Address page, click remove items link of SKU which want to remove
     * from cart
     */
    void clickRemoveItemsLink();

    /**
     * In Shipping Address page, click Cancel button In Remove This Cart modal when
     * does not want to remove SKU from cart
     */
    void clickCancelButtonInRemoveThisCartModal();

    /**
     * In Shipping Address page, click Confirm Update button In Remove This Cart
     * modal when want to remove SKU from cart
     */
    void clickConfirmUpdateButtonInRemoveThisCartModal();

    /**
     * In Shipping Address page, get error message displays
     */
    String getErrorMessage();

    /**
     * In Shipping Address page,Verify The SKUs cannot be delivered are displayed in
     * Shipping Error section
     */
    boolean isSKUExistedInShippingError(SKU sku);

    /**
     * In Shipping Address page, verify Shipping Options displays
     */
    boolean isShippingOptionsDisplayed();

    /**
     * In Shipping Address page, fill Phone Shipping Address into Phone Number text
     * box
     */
    void fillPhoneShippingAddress(String phone);

    /**
     * In Shipping Address page,at Shipping Address modal: fill Phone Number into
     * Phone Number text box
     */
    void fillPhoneShippingAddressInModal(String phone);

    /**
     * In Shipping Address page,at Shipping Address modal: fill Email Address into
     * Phone Number text box
     */
    void fillEmailShippingAddressInModal(String email);

    /**
     * In Shipping Address page, get Land Line Phone error message displayed
     */
    String getLandLinePhoneErrorMessage();
    
    /**
     * In Shipping Address page, Continue button is displayed
     */
    boolean isContinueButtonDisplayed();

    /**
     * In Shipping Address page, Confirm edited address changes
     */
	void clickConfirmAddressButton();

	String getEmailErrorAtBottom();
}
