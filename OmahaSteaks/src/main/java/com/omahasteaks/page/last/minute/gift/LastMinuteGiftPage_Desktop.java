package com.omahasteaks.page.last.minute.gift;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.enums.DeliveryTab; 
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.LastMinuteGiftPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class LastMinuteGiftPage_Desktop extends GeneralPage_Desktop implements LastMinuteGiftPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element ele_FirstSKUFormInFreeExpressDeliveryTab = new Element(interfaces.get("ele_FirstSKUFormInFreeExpressDeliveryTab"));
	protected Element ele_FirstSKUFormInFreeRushDeliveryTab = new Element(interfaces.get("ele_FirstSKUFormInFreeRushDeliveryTab"));

	protected Element eleFirstSku = new Element(interfaces.get("eleFirstSku"));
	protected Element eleFirstIID  = new Element(eleFirstSku,interfaces.get("eleFirstIID"));
	protected Element eleFirstName = new Element(eleFirstSku,interfaces.get("eleFirstName"));
	protected Element eleFirstPrice = new Element(eleFirstSku,interfaces.get("eleFirstPrice"));

	// ================================================================================
	// Methods
	// ================================================================================

	public void clickProgressTab(String progressTabName) {
		Element eleProgressTab = new Element(interfaces.get("eleProgressTab"), progressTabName);
		Common.click(eleProgressTab);
		Common.waitForDOMChange();
	}

	public boolean isPromotionMessageDisplayed(String promotionMessage) {
		Element elePromotionMessage = new Element(interfaces.get("elePromotionMessage"), promotionMessage);
		return elePromotionMessage.isDisplayed();
	}

	public void addFirstItemToCart(SKU sku, boolean doAddExclusiveOffer) {
		Common.waitForPageLoad();
		Common.modalDialog.closeSavorDialog();
		Common.waitForDOMChange();
		sku.setId(eleFirstIID.getText());
 		sku.setName(eleFirstName.getText());
		sku.setPrice(Common.getPriceFromText(eleFirstPrice.getText()));
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(eleFirstSku);
		TextBox txtShipTo = getTxtShipTo(eleFirstSku);
		Button btnAddToCart = getBtnAddToCart(eleFirstSku);
		selectShipTo(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
		Common.click(btnAddToCart);
		selectExclusiveOffer(doAddExclusiveOffer);
	}
 
	public void addFirstSKUToCartFromProgressTab(SKU sku, DeliveryTab progressTab) {
		if (progressTab == DeliveryTab.FREE_EXPRESS_DELIVERY) {
			Common.waitForDisplayed(ele_FirstSKUFormInFreeExpressDeliveryTab);
			Common.scrollElementToCenterScreen(ele_FirstSKUFormInFreeExpressDeliveryTab);
			Common.waitForDOMChange();
			Common.modalDialog.closeUnknownModalDialog();
			ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUFormInFreeExpressDeliveryTab);
			Button btnAddToCart = getBtnAddToCart(ele_FirstSKUFormInFreeExpressDeliveryTab);
			TextBox txtShipTo = getTxtShipTo(ele_FirstSKUFormInFreeExpressDeliveryTab);

			if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD)) {
				cbbSelectShipTo = new ComboBox(interfaces.get("cbbSelectShipTo_iPad"));
				btnAddToCart = new Button(interfaces.get("btnAddToCart_iPad"));
				txtShipTo = new TextBox(interfaces.get("txtShipTo_iPad"));
			}
			Common.waitForDisplayed(cbbSelectShipTo);
			if (!btnAddToCart.isDisplayed())
				throw new AssertionError("The \"Add To Cart\" button does not display");
			Common.scrollElementToCenterScreen(cbbSelectShipTo);
			selectShipTo(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
			Common.click(btnAddToCart);
		} else if (progressTab == DeliveryTab.FREE_RUSH_DELIVERY) {
			Common.waitForDisplayed(ele_FirstSKUFormInFreeRushDeliveryTab);
			Common.scrollElementToCenterScreen(ele_FirstSKUFormInFreeRushDeliveryTab);
			Common.waitForDOMChange();
			Common.modalDialog.closeUnknownModalDialog();
			ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUFormInFreeRushDeliveryTab);
			Button btnAddToCart = getBtnAddToCart(ele_FirstSKUFormInFreeRushDeliveryTab);
			TextBox txtShipTo = getTxtShipTo(ele_FirstSKUFormInFreeRushDeliveryTab);

			if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD)) {
				cbbSelectShipTo = new ComboBox(interfaces.get("cbbSelectShipTo_iPad"));
				btnAddToCart = new Button(interfaces.get("btnAddToCart_iPad"));
				txtShipTo = new TextBox(interfaces.get("txtShipTo_iPad"));
			}
			Common.waitForDisplayed(cbbSelectShipTo);
			if (!btnAddToCart.isDisplayed())
				throw new AssertionError("The \"Add To Cart\" button does not display");
			Common.scrollElementToCenterScreen(cbbSelectShipTo);
			selectShipTo(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
			Common.click(btnAddToCart);
		}
	}

	@Override
	public boolean isFirstSKUDisplayedOutOfStock() {
		boolean tmp = true;
		if (ele_FirstSKUFormInFreeExpressDeliveryTab.isDisplayed()) {
			Element ele_FirstSKUOutOfStock = new Element(ele_FirstSKUFormInFreeExpressDeliveryTab, interfaces.get("eleSKUOutOfStock"));
			tmp = ele_FirstSKUOutOfStock.isDisplayed();
		} else if (ele_FirstSKUFormInFreeRushDeliveryTab.isDisplayed()) {
			Element ele_FirstSKUOutOfStock = new Element(ele_FirstSKUFormInFreeExpressDeliveryTab, interfaces.get("eleSKUOutOfStock"));
			tmp = ele_FirstSKUOutOfStock.isDisplayed();
		}
		return tmp;
	}

}
