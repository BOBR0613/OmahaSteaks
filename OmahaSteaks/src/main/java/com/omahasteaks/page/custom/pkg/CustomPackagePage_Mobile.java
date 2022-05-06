package com.omahasteaks.page.custom.pkg;

import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CustomPackagePage;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class CustomPackagePage_Mobile extends CustomPackagePage_Desktop implements CustomPackagePage {
    public CustomPackagePage_Mobile() {
	super();
	lbPackageName = new Label(interfaces.get("lbPackageName_mb"));
	eleNumberOfRemainItem = new Element(interfaces.get("eleNumberOfRemainItem_mb"));

    }

    @Override
    public void createCustomPackage() {	
	waitForListOfGroupLoadSuccessfull();
	int numberOfitemGroup = lstItemGroups.getElements().size();
	for (int i = numberOfitemGroup; i > 0; i--) {
	    addTheItem(i, 1);	  
	    Common.modalDialog.closeModalDialog();
	}
    }

    @Override
    public void editCustomPackageAndSaveEdits(SKU sku) {
	Label lbEditPackage = new Label(interfaces.get("lbEditPackage_mb"));
	Common.waitForElementExistedInDOM(lbEditPackage);
	removeTheItem(1, 1);
	addTheItem(1, 2);
	getSubItems(sku);
	Common.scrollElementToCenterScreen(btnSaveEdits);
	Common.click(btnSaveEdits);
	DriverUtils.delay(Constants.SLEEP_TIME);

    }

    @Override // int groupIndex, itemIndex start with 1
    protected void removeTheItem(int groupIndex, int itemIndex) {
	Link lnkRemoveItems = new Link(interfaces.get("btnRemoveItemByGroupIndex_mb"), groupIndex);
	Common.waitForElementExistedInDOM(lnkRemoveItems);
	Common.click(lnkRemoveItems.getElements().get(itemIndex - 1));
	Common.waitForDOMChange();
	DriverUtils.delay(2);
    }

    // int groupIndex, itemIndex start with 1
    protected void addTheItem(int groupIndex, int itemIndex) {
	Link lnkSelectItems = new Link(interfaces.get("lstBtnSelectItems_mb"), groupIndex);
	Common.waitForDisplayed(lnkSelectItems);
	Common.scrollElementToCenterScreen(lnkSelectItems);
	Common.modalDialog.closeModalDialog();
	Common.click(lnkSelectItems.getElements().get(itemIndex - 1),false);
	DriverUtils.delay(2);
    }
}
