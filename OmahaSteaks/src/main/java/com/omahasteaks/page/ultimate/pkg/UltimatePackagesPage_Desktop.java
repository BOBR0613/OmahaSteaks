package com.omahasteaks.page.ultimate.pkg;

import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.UltimatePackagesPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;

public class UltimatePackagesPage_Desktop extends GeneralPage_Desktop implements UltimatePackagesPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element btnShopNow_List = new Element(interfaces.get("btnShopNow_List"));
	protected Element eleFirstSKUForm = new Element(interfaces.get("eleFirstSKUForm"));

	// ================================================================================
	// Private Methods
	// ================================================================================
	private void clickShopNowButtonByIndex(int index) {
		Element btnShopNowByIndex = new Element(interfaces.get("btnShopNowByIndex"), index);
		Common.click(btnShopNowByIndex);
		Common.waitForTitleChange();
	}

	// ================================================================================
	// Public Methods
	// ================================================================================
	public void clickFirstShopNowButton() {
		clickShopNowButtonByIndex(1);
	}

	public void addFirstSKUToCart(Item item) {
		Common.waitForDisplayed(eleFirstSKUForm);
		Common.scrollElementToCenterScreen(eleFirstSKUForm);
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(eleFirstSKUForm);
		TextBox txtShipTo = getTxtShipTo(eleFirstSKUForm);
		selectShipTo(cbbSelectShipTo, item.getRecipient().getValue(), txtShipTo);
		Element lnkSelectYourFreeGift = new Element(eleFirstSKUForm, interfaces.get("lnkSelectYourFreeGift"));
		if (lnkSelectYourFreeGift.isDisplayed()) {
			Common.click(lnkSelectYourFreeGift, ActionType.BUILT_IN, true, false);
			Element eleFirstSKUGift = new Element(interfaces.get("eleFirstSKUGift"));
			Common.click(eleFirstSKUGift, false);
			Common.waitForNotDisplayed(eleFirstSKUGift);
		}
		Common.click(getBtnAddToCart(eleFirstSKUForm));
	}
}
