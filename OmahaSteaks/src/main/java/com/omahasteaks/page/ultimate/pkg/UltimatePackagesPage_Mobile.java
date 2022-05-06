package com.omahasteaks.page.ultimate.pkg;

import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.UltimatePackagesPage;
import com.omahasteaks.utils.common.Common;

public class UltimatePackagesPage_Mobile extends UltimatePackagesPage_Desktop implements UltimatePackagesPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element btnShopNowByIndex_mb = new Element(interfaces.get("btnShopNowByIndex_mb"));
	// ================================================================================
	// Private Methods
	// ================================================================================

	private void clickItemByIndex(int index) {
		Element btnShopNowByIndex = new Element(interfaces.get("btnShopNowByIndex_mb"), index);
		Common.click(btnShopNowByIndex);
		Common.waitForTitleChange();
	}

	// ================================================================================
	// Main Methods
	// ================================================================================
	@Override
	public void clickFirstShopNowButton() {
		clickItemByIndex(1);
	}

	@Override
	public void addFirstSKUToCart(Item item) {
		Common.waitForDisplayed(eleFirstSKUForm);
		Common.scrollElementToCenterScreen(eleFirstSKUForm);
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(eleFirstSKUForm);
		TextBox txtShipTo = getTxtShipTo(eleFirstSKUForm);
		selectShipTo(cbbSelectShipTo, item.getRecipient().getValue(), txtShipTo);
		Element lnkSelectYourFreeGift = new Element(eleFirstSKUForm, interfaces.get("lnkSelectYourFreeGift_mb"));
		if (lnkSelectYourFreeGift.isDisplayed()) {
			Common.click(lnkSelectYourFreeGift, false);
			Element eleFirstSKUGift = new Element(interfaces.get("eleFirstSKUGift_mb"));
			Common.click(eleFirstSKUGift, false);
		}
		Common.click(getBtnAddToCart(eleFirstSKUForm));
	}
}
