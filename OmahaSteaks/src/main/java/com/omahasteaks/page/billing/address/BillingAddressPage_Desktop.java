package com.omahasteaks.page.billing.address;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.BillingAddressPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;  


public class BillingAddressPage_Desktop extends GeneralPage_Desktop implements BillingAddressPage {
    // ================================================================================
    // Locators
    // ================================================================================
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
    protected ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));
    protected ComboBox cbbCountry = new ComboBox(interfaces.get("cbbCountry"));
    protected ComboBox cbbbirthdayMonth = new ComboBox(interfaces.get("cbbbirthdayMonth"));
    protected ComboBox cbbbirthdayDay = new ComboBox(interfaces.get("cbbbirthdayDay"));
    protected ComboBox cbbbirthdayYear = new ComboBox(interfaces.get("cbbbirthdayYear"));
    protected CheckBox cbIAgree = new CheckBox(interfaces.get("cbIAgree"));
    protected Button btnContinueCheckout = new Button(interfaces.get("btnContinueCheckout"));
    protected Button btnConfirmAddress = new Button(interfaces.get("btnConfirmAddress"));
    protected Label lblBirthday = new Label(interfaces.get("lblBirthday"));
    protected Element eleAddressWarning = new Element(interfaces.get("eleAddressWarning"));

    // ================================================================================
    // Methods
    // ================================================================================
    @SuppressWarnings("deprecation")
    public void fillBillingAddress(CustomerAddress billingAddress) {
	Common.waitForDisplayed(txtFirstName);
	Common.modalDialog.closeUnknownModalDialog();
	Common.enter(txtFirstName, billingAddress.firstName);
	Common.enter(txtLastName, billingAddress.lastName);
	Common.enter(txtCompanyName, billingAddress.companyName);
	Common.enter(txtStreetAddress, billingAddress.streetAddress1);
	Common.enter(txtStreetAddress2, billingAddress.streetAddress2);
	Common.enter(txtAptSuite, billingAddress.aptSuite);
	Common.enter(txtCity, billingAddress.city);
	Common.select(cbbState, billingAddress.state);
	Common.select(cbbCountry, billingAddress.country);
	Common.enter(txtPhoneNumber, billingAddress.phoneNumber);
	Common.enter(txtEmail, billingAddress.email);
	Common.enter(txtConfirmEmail, billingAddress.confirmEmail);
	// Select Birthday if required!"
	if (billingAddress.birthday != null) {
	    if (cbbbirthdayMonth.isEnabled()) {
		cbbbirthdayMonth.select(billingAddress.birthday.getMonth() + 1);
		cbbbirthdayDay.select(billingAddress.birthday.getDate());
		if (cbbbirthdayYear.isDisplayed()) {
		    StringBuilder sb = new StringBuilder();
		    sb.append("");
		    sb.append(billingAddress.birthday.getYear() + 1900);
		    String strI = sb.toString();
		    cbbbirthdayYear.select(strI);
		}
	    }
	}
	// Check [x] "I agree to the Terms of Use and Privacy Policy"
	Common.check(cbIAgree, ActionType.JS, true);
	Common.enter(txtZipCode, billingAddress.zipCode);
	if (!billingAddress.zipCode.equals(""))
	    Common.triggerTextBoxChangeEvent(txtZipCode);
    }

    public void fillBillingAddressExceptField(CustomerAddress billingAddress, AddressFields field) {
	CustomerAddress tmpBillingAddress = billingAddress.clone();
	tmpBillingAddress.updateFieldValueBy(field, "");
	fillBillingAddress(tmpBillingAddress);
	switch (field) {
	case ACCEPTANCE:
	    Common.uncheck(cbIAgree);
	    break;
	case CITY:
	    Common.enter(txtCity, "");
	    break;
	case STATE:
	    Common.select(cbbState, "");
	    break;
	default:
	    break;
	}
    }

    public void continueCheckout() {
	Common.click(btnContinueCheckout);
	confirmAddress();
	Common.waitForPageLoad();
    }

    public void confirmAddress() {
	if (btnConfirmAddress.isDisplayed(Constants.SLEEP_TIME)) {
	    Common.click(btnConfirmAddress);
	    Common.waitForNotDisplayed(btnConfirmAddress);
	}
    }

    public String getErrorMessageByField (AddressFields field) {
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
	// Delay for old message disappear
	DriverUtils.delay(2);
	if (eleAddressWarning.isDisplayed(Constants.SHORT_TIME)) {
	    return eleAddressWarning.getText();
	} else
	    return "";
    }

    public boolean isRequiredBirthdayLabelDisplayed() {
	Common.modalDialog.closeUnknownModalDialog();
	if (lblBirthday.isDisplayed(Constants.SHORT_TIME)) {
	    return lblBirthday.getText().contains("(required)");
	}
	return false;
    }
 
}