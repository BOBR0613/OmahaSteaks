package com.omahasteaks.page.confirmation;
/*package pages.ConfirmationImp;

import com.logigear.control.common.imp.Element;

import pages.ConfirmationPage;
import pages.GeneralPageImp.GeneralPage_Desktop;
import utils.common.Common;
import utils.common.Constants;

public class ConfirmationPage_Desktop extends GeneralPage_Desktop implements ConfirmationPage {
    String thankYouMessage = "Thank you! Your order is being prepared";
    // ================================================================================
    // Locators
    // ================================================================================
    protected Element elThankYou = new Element(interfaces.get("elThankYou"));

    // ================================================================================
    // Methods
    // ================================================================================
    public String getThankYouLabel() {
	Common.waitForDisplayed(elThankYou);
	return elThankYou.getText();
    }

    public boolean isThankYouMessageDisplayed() {
	if (elThankYou.isDisplayed(Constants.SHORT_TIME)) {
	    Common.modalDialog.closeModalDialog();
	    return elThankYou.getText().contains(thankYouMessage);
	}
	return false;
    }
}
*/