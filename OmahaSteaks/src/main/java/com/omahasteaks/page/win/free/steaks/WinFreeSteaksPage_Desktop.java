package com.omahasteaks.page.win.free.steaks;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.WinFreeSteaksPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class WinFreeSteaksPage_Desktop extends GeneralPage_Desktop implements WinFreeSteaksPage {
    protected TextBox txtFirstName = new TextBox(interfaces.get("txtFirstName"));
    protected TextBox txtLastName = new TextBox(interfaces.get("txtLastName"));
    protected TextBox txtPhone = new TextBox(interfaces.get("txtPhone"));
    protected TextBox txtEmail = new TextBox(interfaces.get("txtEmail"));
    protected TextBox txtCity = new TextBox(interfaces.get("txtCity"));

    protected CheckBox cbIAgree = new CheckBox(interfaces.get("cbIAgree"));

    protected Button btnEnterToWin = new Button(interfaces.get("btnEnterToWin"));

    protected Element eleThankYou = new Element(interfaces.get("eleThankYou"));
    protected Element eleIAgree = new Element(interfaces.get("eleIAgree"));
    protected Element eleErrorMessageByEmail = new Element(interfaces.get("eleErrorMessageByEmail"));
    protected Element eleErrorMessageByFirstName = new Element(interfaces.get("eleErrorMessageByFirstName"));
    protected Element eleErrorMessageByLastName = new Element(interfaces.get("eleErrorMessageByLastName"));
    protected Element eleErrorMessageByCity = new Element(interfaces.get("eleErrorMessageByCity"));
    protected Element eleErrorMessageByState = new Element(interfaces.get("eleErrorMessageByState"));
    protected Element eleErrorMessageByPhone = new Element(interfaces.get("eleErrorMessageByPhone"));
    protected Element eleErrorMessageByIAgree = new Element(interfaces.get("eleErrorMessageByIAgree"));

    // ================================================================================
    // Methods
    // ================================================================================
    public void fillCustomerAddress(CustomerAddress customerAddress) {
	ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));

	Common.enter(txtFirstName, customerAddress.firstName);
	Common.enter(txtLastName, customerAddress.lastName);
	Common.select(cbbState, customerAddress.state);
	Common.enter(txtCity, customerAddress.city);
	Common.enter(txtPhone, customerAddress.phoneNumber);
	Common.waitForDOMChange(Constants.SLEEP_TIME);
    }

    public void fillEmailAddress(String email) {
	Common.enter(txtEmail, email);
    }

    public void clickIAgreeCheckbox() {
	Common.click(eleIAgree);
	Common.check(cbIAgree);
	Common.waitForChecked(cbIAgree);
    }

    public void clickEnterToWinInWinFreeSteakPage() {
	Common.click(btnEnterToWin);
	Common.waitForDOMChange();
    }

    public boolean isThankYouMessageDisplayed() {
	Common.modalDialog.closeModalDialog();
	return eleThankYou.isDisplayed();
    }

    public String getErrorMessageByField(AddressFields addrField) {
	switch (addrField) {
	case EMAIL:
	    Common.waitForDisplayed(eleErrorMessageByEmail);
	    return Common.getText(eleErrorMessageByEmail);
	case FIRST_NAME:
	    Common.waitForDisplayed(eleErrorMessageByFirstName);
	    return Common.getText(eleErrorMessageByFirstName);
	case LAST_NAME:
	    Common.waitForDisplayed(eleErrorMessageByLastName);
	    return Common.getText(eleErrorMessageByLastName);
	case CITY:
	    Common.waitForDisplayed(eleErrorMessageByCity);
	    return Common.getText(eleErrorMessageByCity);
	case STATE:
	    Common.waitForDisplayed(eleErrorMessageByState);
	    return Common.getText(eleErrorMessageByState);
	case PHONE:
	    Common.waitForDisplayed(eleErrorMessageByPhone);
	    return Common.getText(eleErrorMessageByPhone);
	default:
	    return "";
	}
    }

    public String getIAgreeErrorMessage() {
	return Common.getText(eleErrorMessageByIAgree);
    }
}
