package com.omahasteaks.page.shipping.address;
/*package pages.ShippingAddressPageImp;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;

import data.ENUM.AddressFields;
import data.ENUM.Recipient;
import data.objects.CustomerAddress;
import data.objects.SKU;
import pages.ShippingAddressPage;
import pages.GeneralPageImp.GeneralPage_Desktop;
import utils.common.Common;
import utils.common.Constants;

public class ShippingAddressPage_Desktop extends GeneralPage_Desktop implements ShippingAddressPage {
    // ================================================================================
    // Locators
    // ================================================================================
    protected Element eleShippingInformationTitle = new Element(interfaces.get("eleShippingInformationTitle"));
    protected ComboBox cbbCopyBilling = new ComboBox(interfaces.get("cbbCopyBilling"));
    protected CheckBox chkboxCopyBilling = new CheckBox(interfaces.get("chkboxCopyBilling"));
    protected CheckBox cbPhoneLandlineOnly = new CheckBox(interfaces.get("cbALWPHNCNT"));
    protected CheckBox cbEmailNotify = new CheckBox(interfaces.get("cbALWEMLCNT"));
    protected CheckBox cbNoNotify = new CheckBox(interfaces.get("cbNONOTIFY"));
    protected CheckBox cbAddGiftWrap = new CheckBox(interfaces.get("cbAddGiftWrap"));
    protected TextBox txtFirstName = new TextBox(interfaces.get("txtFirstName"));
    protected TextBox txtLastName = new TextBox(interfaces.get("txtLastName"));
    protected TextBox txtCompanyName = new TextBox(interfaces.get("txtCompanyName"));
    protected TextBox txtStreetAddress = new TextBox(interfaces.get("txtStreetAddress"));
    protected TextBox txtStreetAddress2 = new TextBox(interfaces.get("txtStreetAddress2"));
    protected TextBox txtAptSuite = new TextBox(interfaces.get("txtAptSuite"));
    protected TextBox txtZipCode = new TextBox(interfaces.get("txtZipCode"));
    protected TextBox txtCity = new TextBox(interfaces.get("txtCity"));
    protected TextBox txtPhoneNumber = new TextBox(interfaces.get("txtPhoneNumber"));
    protected TextBox txtEmail = new TextBox(interfaces.get("txtEmail"));
    protected TextBox txtConfirmEmail = new TextBox(interfaces.get("txtConfirmEmail"));
    protected TextBox txtPersonalizedGreeting = new TextBox(interfaces.get("txtPersonalizedGreeting"));
    protected ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));
    protected ComboBox cbbCountry = new ComboBox(interfaces.get("cbbCountry"));
    protected Button btnContinueCheckout = new Button(interfaces.get("btnContinueCheckout"));
    protected Button btnConfirmAddress = new Button(interfaces.get("btnConfirmAddress"));
    protected Element eleAddressWarning = new Element(interfaces.get("eleAddressWarning"));

    // ================================================================================
    // Methods
    // ================================================================================
    public void fillShippingAddress(CustomerAddress shippingAddress) {
	// eleShippingInformationTitle.waitForDisplay(Constants.SHORT_TIME);
	Common.waitForDisplayed(txtFirstName);
	Common.modalDialog.closeUnknownModalDialog();
	Common.enter(txtFirstName, shippingAddress.firstName);
	Common.enter(txtLastName, shippingAddress.lastName);
	Common.enter(txtCompanyName, shippingAddress.companyName);
	Common.enter(txtStreetAddress, shippingAddress.streetAddress1);
	Common.enter(txtStreetAddress2, shippingAddress.streetAddress2);
	Common.enter(txtAptSuite, shippingAddress.aptSuite);
	Common.enter(txtCity, shippingAddress.city);
	Common.select(cbbState, shippingAddress.state);
	Common.select(cbbCountry, shippingAddress.country);
	Common.enter(txtPhoneNumber, shippingAddress.phoneNumber);
	Common.enter(txtEmail, shippingAddress.email);
	Common.enter(txtZipCode, shippingAddress.zipCode);
	if (!shippingAddress.zipCode.equals(""))
	    Common.triggerTextBoxChangeEvent(txtZipCode);
    }

    public void continueCheckout() {
	Common.click(btnContinueCheckout);
	if (btnConfirmAddress.isDisplayed(Constants.SLEEP_TIME)) {
	    Common.click(btnConfirmAddress);
	}
	Common.waitForPageLoad();
    }

    public String getRecipientNameInTitle() {
	Common.waitForDisplayed(eleShippingInformationTitle);
	String tmp = eleShippingInformationTitle.getText();
	String result = tmp.replaceAll("'s Shipping Information", "");
	return result;
    }

    public Boolean isSKUDisplayed(SKU sku) {
	Common.waitForDisplayed(cbAddGiftWrap);
	String recipient = sku.getRecipient().getValue();
	if (sku.getRecipient() == Recipient.MYSELF)
	    recipient = "Your";
	Element eleSKUInfo = new Element(interfaces.get("eleSKUInfo"), recipient, sku.getName(), Common.getNumberFromText(sku.getId()));
	return eleSKUInfo.isDisplayed();
    }

    public Boolean isFreeShippingDisplayed(SKU sku) {
	String recipient = sku.getRecipient().getValue();
	if (sku.getRecipient() == Recipient.MYSELF)
	    recipient = "Your";
	Element txtFreeShippingOfRecipient = new Element(interfaces.get("txtFreeShippingOfRecipient"), recipient);
	return txtFreeShippingOfRecipient.isDisplayed();
    }

    public void clickGreetingCardImageByPosition(int position) {
	Element imgGreetingCardImageByPosition = new Element(interfaces.get("imgGreetingCardImageByPosition"), position);
	if (imgGreetingCardImageByPosition.isDisplayed(Constants.SHORT_TIME)) {
	    Common.click(imgGreetingCardImageByPosition, false);
	}
    }

    public void clickButtonAddGreetingCardInModal() {
	Element btnAddGreetingCardInModal = new Element(interfaces.get("btnAddGreetingCardInModal"));
	Common.click(btnAddGreetingCardInModal, false);
    }

    public String getGreetingCardLabelinModal() {
	Element lblGreetingCardLabelInModal = new Element(interfaces.get("lblGreetingCardLabelInModal"));
	if (lblGreetingCardLabelInModal.isDisplayed(Constants.SHORT_TIME)) {
	    return lblGreetingCardLabelInModal.getText();
	} else
	    return "";
    }

    public void enterPersonalizedGreeting(String text) {
	Common.enter(txtPersonalizedGreeting, text);
    }

    public void checkAddGiftWrap() {
	Common.check(cbAddGiftWrap);
    }

    public void uncheckAddGiftWrap() {
	Common.uncheck(cbAddGiftWrap);
    }

    public String getErrorMessageByField(AddressFields field) {
	Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), field.getValue());
	if (eleErrorMessageByField.isDisplayed(Constants.SHORT_TIME)) {
	    return eleErrorMessageByField.getText();
	} else
	    return "";
    }

    public String getInvalidMessageByField(AddressFields field) {
	String fieldName = "";
	switch (field) {
	case PHONE:
	    fieldName = "Phone Number";
	    break;
	default:
	    break;
	}
	Element eleInvalidMessageByField = new Element(interfaces.get("eleErrorMessageByField"), fieldName);
	if (eleInvalidMessageByField.isDisplayed(Constants.SHORT_TIME)) {
	    return eleInvalidMessageByField.getText();
	} else
	    return "";
    }

    public String getAddressWarningMessage() {
	DriverUtils.delay(2);
	if (eleAddressWarning.isDisplayed(Constants.SHORT_TIME)) {
	    return eleAddressWarning.getText();
	} else
	    return "";
    }

    public void checkToShipToTheAddressdAsTheBillingAddress() {
	Common.check(chkboxCopyBilling);
    }
}
*/