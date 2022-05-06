package com.omahasteaks.page.product;

import java.util.Random;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class ProductPage_Desktop extends GeneralPage_Desktop implements ProductPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element eleSelectedSize = new Element(interfaces.get("eleSelectedSize"));
	protected Element eleSelectedQty = new Element(interfaces.get("eleSelectedQty"));
	protected Element lnkProductSelectSizeAndCount = new Element(interfaces.get("lnkProductSelectSizeAndCount"));
	protected Element lbProductSizeAndCount = new Element(interfaces.get("lbProductSizeAndCount"));
	protected ComboBox cbbProductSelectSizeAndCount = new ComboBox(interfaces.get("cbbProductSelectSizeAndCount"));
	protected Element lbProductName = new Element(interfaces.get("lbProductName"));
	protected ComboBox cbbProductShipTo = new ComboBox(interfaces.get("cbbProductShipTo"));
	protected ComboBox cbbDeliveryFrequency = new ComboBox(interfaces.get("cbbDeliveryFrequency"));
	protected CheckBox radAutoshipAndSave = new CheckBox(interfaces.get("radAutoshipAndSave"));
	protected TextBox txtProductNewShipTo = new TextBox(interfaces.get("txtProductNewShipTo"));
	protected Button btnProductAddToCart = new Button(interfaces.get("btnProductAddToCart"));
	protected Element eleSizeList = new Element(interfaces.get("eleSizeList"));
	protected Element eleCountList = new Element(interfaces.get("eleCountList"));
	protected Element eleFirstSize = new Element(interfaces.get("eleFirstSize"));
	protected Element eleSizeAndRating = new Element(interfaces.get("eleSizeAndRating"));
	protected Element btnProductAddToCartPopup = new Element(interfaces.get("btnProductAddToCartPopup"));
	protected Element eleUpsellForm = new Element(interfaces.get("eleUpsellForm"));
	protected Element eleTDCount = new Element(interfaces.get("eleTDCount"));

	// ================================================================================
	// Support Methods
	// ================================================================================
	private void getSizeAndCountFromProductPage(Item item) {
		// Return the Item's name
		item.setName(lbProductName.getText());
		item.setSize(eleSelectedSize.getValue());
		item.setCount(Integer.parseInt(eleSelectedQty.getValue()));
	}

	protected void selectRandomSizeAndCount(Item item) {
		// Click to open Size pop-over
		Common.waitForDisplayed(lnkProductSelectSizeAndCount);
		String type = lnkProductSelectSizeAndCount.getText();
		// Handle auto open dropdownlist when focus on element on MAC
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
			Common.click(lnkProductSelectSizeAndCount, true, false);
		else {
			Common.click(lnkProductSelectSizeAndCount);
		}
		Random rd = new Random();
		if (type.contains("Size")) {
			// Select Random Size
			Common.waitForDisplayed(eleFirstSize);
			int rdIndex = rd.nextInt(eleSizeList.getElements().size());
			Common.click(eleSizeList.getElements().get(rdIndex));
		}
		// Note: Wait for common action completed
		Common.waitForDisplayed(eleFirstCount);
		int rdIndex = rd.nextInt(eleCountList.getElements().size());
		Common.click(eleCountList.getElements().get(rdIndex));
		Common.waitForNotDisplayed(eleSizeList);
		Common.waitForNotDisplayed(eleCountList);
		getSizeAndCountFromProductPage(item);
	}

	protected void selectFirstSizeAndCount(Item item) {
		// Handle auto open dropdownlist when focus on element on MAC
		if (!lnkProductSelectSizeAndCount.isDisplayed()) return;
		
		Common.click(lnkProductSelectSizeAndCount, ActionType.BUILT_IN, true, false);
		// Wait 3s for animation effect
		//	DriverUtils.delay(Constants.SLEEP_TIME);
		//	Common.waitForDisplayed(eleFirstSizeAndCount, Constants.SLEEP_TIME);
		Common.waitForDOMChange();
		selectFirstSizeAndCount();
		getSizeAndCountFromProductPage(item);
	}

	private void addSkuToCartWithoutSize(SKU sku, boolean doAddExclusiveOffer) {
		selectShipToAndSelectAddToCart(sku);
		Common.waitForPageLoad();
		selectExclusiveOffer(doAddExclusiveOffer);	
	}
	
	// ================================================================================
	// Main Methods
	// ================================================================================

	public void addItemToCart(Item item, boolean doAddExclusiveOffer, boolean isRandom) {
		if (isRandom)
			selectRandomSizeAndCount(item);
		else
			selectFirstSizeAndCount(item);
		addSkuToCartWithoutSize(item, doAddExclusiveOffer);
	}

 

	public void selectShipToAndSelectAddToCart(SKU sku) { 
		selectShipTo(cbbProductShipTo, sku.getRecipient().getValue(), txtProductNewShipTo);
		Common.tryClickByJs(btnProductAddToCart);
		Common.waitForPageLoad();
	}

	@Override
	public void addSKUToCart(SKU sku, boolean doAddExclusiveOffer) {
 		selectShipToAndSelectAddToCart(sku);
		Common.waitForPageLoad();
		selectExclusiveOffer(doAddExclusiveOffer);
	}

 
	
	
}