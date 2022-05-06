package com.omahasteaks.page.product;

import java.util.Random;

import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.utils.common.Common;

public class ProductPage_Mobile extends ProductPage_Desktop implements ProductPage {
    public ProductPage_Mobile() {
	super();
	cbbProductShipTo = new ComboBox(interfaces.get("cbbProductShipTo_mb"));
	txtProductNewShipTo = new TextBox(interfaces.get("txtProductNewShipTo_mb"));
	lnkProductSelectSizeAndCount = new Element(interfaces.get("lnkProductSelectSizeAndCount_mb"));
    }

    // ================================================================================
    // Locators
    // ================================================================================
    protected ComboBox cboProductSelectSizeAndCount_mb = new ComboBox(interfaces.get("cboProductSelectSizeAndCount_mb"));

    // ================================================================================
    // Support Methods
    // ================================================================================
    private void getSizeAndCountFromProductPage(Item item) {
	int count = 0;
	String size;
	// Return the Size and Count
	// Common.waitForTextContains(lnkProductSelectSizeAndCount, "count");
	String selectedSizeAndCount = cboProductSelectSizeAndCount_mb.getSelected();
	if (selectedSizeAndCount.contains("oz.")) {
	 String[] selectSizeAndCount = selectedSizeAndCount.trim().split("\\(");
	    String strNumber = selectSizeAndCount[0].trim();
	    if (strNumber.isEmpty() == false) {
		try {
		    count = Integer.parseInt(strNumber);
		} catch (Exception e) {
		    count = 0;
		}
	    }
	    size = selectSizeAndCount[1].split("\\)")[0].trim();	   
	} else {
	    size = eleSizeAndRating.getText().trim().split("oz.")[0].trim() + " oz.";
	    String strNumber = Common.getNumberFromText(selectedSizeAndCount.trim());
	    if (strNumber.isEmpty() == false) {
		try {
		    count = Integer.parseInt(strNumber);
		} catch (Exception e) {
		    count = 0;
		}
	    }
	}
	// Return the Item's name
	item.setName(lbProductName.getText());
	item.setSize(size);
	item.setCount(count);
    }

    @Override
    protected void selectRandomSizeAndCount(Item item) {
	// Click to open Size pop-over
	Common.waitForDisplayed(lnkProductSelectSizeAndCount);
	String type = cboProductSelectSizeAndCount_mb.getSelected();
	// Handle auto open dropdownlist when focus on element on MAC

	Common.click(lnkProductSelectSizeAndCount);

	Random rd = new Random();
	if (type.contains("Size")) {
	    // Select Random Size
	    Common.waitForDisplayed(eleSizeList);
	    int rdIndex = rd.nextInt(eleSizeList.getElements().size());
	    Common.click(eleSizeList.getElements().get(rdIndex));
	}
	// Note: Wait for common action completed
	Common.waitForDisplayed(eleCountList);
	int rdIndex = rd.nextInt(eleCountList.getElements().size());
	Common.click(eleCountList.getElements().get(rdIndex));
	Common.waitForNotDisplayed(eleSizeList);
	Common.waitForNotDisplayed(eleCountList);
	getSizeAndCountFromProductPage(item);
    }

    @Override
    protected void selectFirstSizeAndCount(Item item) {
	// Click to open Size pop-over
	Common.waitForDisplayed(lnkProductSelectSizeAndCount);
	String type = cboProductSelectSizeAndCount_mb.getSelected();
	// Handle auto open dropdownlist when focus on element on MAC

	Common.click(lnkProductSelectSizeAndCount);

	if (type.contains("Size")) {
	    // Select Random Size
	    Common.waitForDisplayed(eleSizeList);
	    Common.click(eleSizeList.getElements().get(0));
	}
	// Note: Wait for common action completed
	Common.waitForDisplayed(eleCountList);
	Common.click(eleCountList.getElements().get(0));
	Common.waitForNotDisplayed(eleSizeList);
	Common.waitForNotDisplayed(eleCountList);
	getSizeAndCountFromProductPage(item);
    }

    // ================================================================================
    // Main Methods
    // ================================================================================
    @Override
    public void continueShopping() {
	Common.waitForDisplayed(btnCheckOut);
	Common.click(btnCloseAddedToCart);
    }
}
