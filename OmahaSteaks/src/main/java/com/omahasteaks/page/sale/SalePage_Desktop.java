package com.omahasteaks.page.sale;

import org.openqa.selenium.WebDriverException;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.enums.ProgressiveOfferTab;
import com.omahasteaks.page.SalePage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;

public class SalePage_Desktop extends GeneralPage_Desktop implements SalePage {
    // ================================================================================
    // Locators
    // ================================================================================
    protected Element eleProgressiveOffersByClass = new Element(interfaces.get("eleProgressiveOffersByClass"));
    protected TextBox txtEmailInProgressiveOffersTab = new TextBox(interfaces.get("txtEmailInProgressiveOffersTab"));
    protected CheckBox cbIAgree = new CheckBox(interfaces.get("cbIAgree"));
    protected Button btnSubmit = new Button(interfaces.get("btnSubmit"));
    protected Element eleYouareInModal = new Element(interfaces.get("eleYouareInModal"));
    protected Element eleEmailError = new Element(interfaces.get("eleEmailError"));
    protected Element eleIAgreeError = new Element(interfaces.get("eleIAgreeError"));

    // ================================================================================
    // Methods
    // ================================================================================
    public boolean isProgressiveOffersTabClickable() {
	try {
	    for (int i = eleProgressiveOffersByClass.getElements().size(); i > 0; i--) {
		Element eleTab = new Element(interfaces.get("eleProgressiveOffersByIndex"), i);
		Common.click(eleTab);
		Common.waitForDOMChange();
	    }
	} catch (WebDriverException e) {
	    e.getMessage().contains("Clickable");
	    return false;
	}
	return true;
    }

    public void clickProgressiveOfferTabByName(ProgressiveOfferTab progressiveOfferName) {
	Element eleTab = new Element(interfaces.get("eleProgressiveOffersByName"), progressiveOfferName.getValue());
	Common.click(eleTab);
	Common.waitForDOMChange();
    }

    public void fillEmailAddressInProgressiveOffersTab(String email) {
	Common.enter(txtEmailInProgressiveOffersTab, email);
    }

    public void clickIAgreeCheckbox() {
	Common.check(cbIAgree);
    }

    public void clickSubmitInSalePage() {
	Common.click(btnSubmit);
	Common.waitForDOMChange();
    }

    public String getEmailErrorMessage() {
	return Common.getText(eleEmailError);
    }

    public String getIAgreeErrorMessage() {
	return Common.getText(eleIAgreeError);
    }

    public boolean isYouAreInModalDisplayed() {
	return eleYouareInModal.isDisplayed();
    }
}
