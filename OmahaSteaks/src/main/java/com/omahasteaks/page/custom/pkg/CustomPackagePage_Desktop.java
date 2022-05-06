package com.omahasteaks.page.custom.pkg;

import org.openqa.selenium.WebElement;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CustomPackagePage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class CustomPackagePage_Desktop extends GeneralPage_Desktop implements CustomPackagePage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element eleNumberOfRemainItem = new Element(interfaces.get("eleNumberOfRemainItem"));
	protected Element lstItemGroups = new Element(interfaces.get("lstItemGroups"));
	protected Element lstSelectedItems = new Element(interfaces.get("lstSelectedItems"));

	protected Label lbPackageName = new Label(interfaces.get("lbPackageName"));

	protected ComboBox cbbShipTo = new ComboBox(interfaces.get("cbbShipTo"));

	protected TextBox txtNewShipTo = new TextBox(interfaces.get("txtNewShipTo"));

	protected Button btnAddToCart = new Button(interfaces.get("btnAddToCart"));
	protected Button btnSaveEdits = new Button(interfaces.get("btnSaveEdits"));
	
	protected Button btnBuildYourOwn = new Button(interfaces.get("btnBuildYourOwn"));
	protected Label  txtItemNumber = new Label(interfaces.get("txtItemNumber"));
	protected Label  txtItemName = new Label(interfaces.get("txtItemName"));
	protected Label  txtItemPrice = new Label(interfaces.get("txtItemPrice"));
 	
	// ================================================================================
	// Support Methods
	// ================================================================================

	public void waitForListOfGroupLoadSuccessfull() {
		Common.waitForDisplayed(eleNumberOfRemainItem);
		int expectedNumber = Integer.parseInt(Common.getNumberFromText(eleNumberOfRemainItem.getText()));
		int numberOfitemGroup = lstItemGroups.getElements().size();
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		while (numberOfitemGroup != expectedNumber && endTime < timeout) {
			expectedNumber = Integer.parseInt(Common.getNumberFromText(eleNumberOfRemainItem.getText()));
			numberOfitemGroup = lstItemGroups.getElements().size();
			DriverUtils.delay(1);
			endTime = System.currentTimeMillis() - startTime;
		}
	}

	// int groupIndex, itemIndex start with 1
	protected void addTheItem(int groupIndex, int itemIndex) {
		Link lnkSelectItems = new Link(interfaces.get("lstBtnSelectItems"), groupIndex);
		Common.click(lnkSelectItems.getElements().get(itemIndex - 1));
		DriverUtils.delay(2);
	}

	// int groupIndex, itemIndex start with 1
	protected void removeTheItem(int groupIndex, int itemIndex) {
		Link lnkRemoveItems = new Link(interfaces.get("btnRemoveItemByGroupIndex"), groupIndex);
		Common.click(lnkRemoveItems.getElements().get(itemIndex - 1));
		DriverUtils.delay(2);
	}

	protected void getSubItems(SKU sku) {
		sku.cleanUpSubItems();
		for (WebElement eleSubItem : lstSelectedItems.getElements()) {
			String tmpName = eleSubItem.getText();
			SKU subItem = new SKU();
			//subItem.setName(tmpName.split("\\)")[1].trim());
			subItem.setName(tmpName);
			sku.addSubItems(subItem);
		}
	}

	// ================================================================================
	// Main Methods
	// ================================================================================

	public void createCustomPackage() {
		waitForListOfGroupLoadSuccessfull();
		int numberOfitemGroup = lstItemGroups.getElements().size();
		for (int i = 1; i <= numberOfitemGroup; i++) {
			addTheItem(i, 1);
		}
	}

	public void editCustomPackageAndSaveEdits(SKU sku) {
		if (lbPackageName.isDisplayed(Constants.SHORT_TIME)) {
			Common.waitForDisplayed(lbPackageName);
			Common.waitForText(lbPackageName, sku.getName());
			removeTheItem(1, 1);
			addTheItem(1, 2);
			getSubItems(sku);
			Common.scrollElementToCenterScreen(btnSaveEdits);
			Common.click(btnSaveEdits);
			DriverUtils.delay(Constants.SLEEP_TIME);
		}
	}

	public void createCustomPackageAndAddToCart(SKU sku, boolean doAddExclusiveOffer) {
		Common.waitForDisplayed(lbPackageName);
		Common.waitForText(lbPackageName, sku.getName());
		createCustomPackage();
		selectShipTo(cbbShipTo, sku.getRecipient().getValue(), txtNewShipTo);
		Common.scrollElementToCenterScreen(btnAddToCart);
		getSubItems(sku);
		Common.click(btnAddToCart);
		selectExclusiveOffer(doAddExclusiveOffer);
	}

	public void addFirstSKUToCart(SKU sku, boolean doAddExclusiveOffer) { 
		Common.modalDialog.closeSavorDialog();
		Common.waitForDOMChange();
		sku.setId(txtItemNumber.getText().trim().substring(0));
		sku.setName(txtItemName.getText());
		sku.setPrice(Common.getPriceFromText(txtItemPrice.getText()));
		Common.click(btnBuildYourOwn);
		createCustomPackage();
		selectShipTo(cbbShipTo, sku.getRecipient().getValue(), txtNewShipTo);
		Common.scrollElementToCenterScreen(btnAddToCart);
		getSubItems(sku);
		Common.click(btnAddToCart);
		selectExclusiveOffer(doAddExclusiveOffer);
	}
	
 
}
