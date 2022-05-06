package com.omahasteaks.page.stores;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.enums.MenuTabsInStoresPage;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.StoresPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;

public class StoresPage_Desktop extends GeneralPage_Desktop implements StoresPage {
    // ================================================================================
    // Locators
    // ================================================================================
    protected TextBox txtZipCode = new TextBox(interfaces.get("txtZipCode"));
    protected Button btnSubmit = new Button(interfaces.get("btnSubmit"));
    protected Label eleNearestStoresAddress = new Label(interfaces.get("eleNearestStoresAddress"));
    protected Element eleNoStoresFound = new Element(interfaces.get("eleNoStoresFound"));
    protected Element eleErrorMessage = new Element(interfaces.get("eleErrorMessage"));

    // ================================================================================
    // Methods
    // ================================================================================
    public boolean isMenuTabdisplayed(MenuTabsInStoresPage tabInStoresPage) {
	Element eleMenuTab = new Element(interfaces.get("eleMenuTab"), tabInStoresPage.getValue());
	return eleMenuTab.isDisplayed();
    }

    public void clickMenuTabdisplayed(MenuTabsInStoresPage tabInStoresPage) {
	Element eleMenuTab = new Element(interfaces.get("eleMenuTab"), tabInStoresPage.getValue());
	Common.click(eleMenuTab);
	Common.waitForDOMChange();
    }

    public void fillZipCode(CustomerAddress address) {
	Common.enter(txtZipCode, address.zipCode);
	if (!address.zipCode.equals(""))
	    Common.triggerTextBoxChangeEvent(txtZipCode);

    }

    public void clickSubmitButton() {
	Common.click(btnSubmit);
	Common.waitForPageLoad();
    }

    public boolean isNearestStoresAddressDisplayed() {
	return eleNearestStoresAddress.isDisplayed();
    }

    public boolean isNoStoresFoundDisplayed() {
	return eleNoStoresFound.isDisplayed();
    }

    public String getErrorMessage() {
	return Common.getText(eleErrorMessage);
    }
}
