package com.omahasteaks.page.free.shipping;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.FreeShippingPage;
import com.omahasteaks.utils.common.Common;

public class FreeShippingPage_Mobile extends FreeShippingPage_Desktop implements FreeShippingPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element eleListFreeShippingSkus = new Element(interfaces.get("eleListFreeShippingSkus"));
	protected Element eleFirstSku = new Element(interfaces.get("eleFirstSku"));
	protected Element eleFirstIID  = new Element(eleFirstSku,interfaces.get("eleFirstIID"));
	protected Element eleFirstName = new Element(eleFirstSku,interfaces.get("eleFirstName"));
	protected Element eleFirstPrice = new Element(eleFirstSku,interfaces.get("eleFirstPrice"));

	// ================================================================================
	// Methods
	// ================================================================================
	public void addFirstSkuToCart(Package pkg, boolean doAddExclusiveOffer) {
		Common.waitForPageLoad();
		Common.waitForDisplayed(eleFirstSku);
		pkg.setId(eleFirstIID.getText());
 		pkg.setName(eleFirstName.getText());
		pkg.setPrice(Common.getPriceFromText(eleFirstPrice.getText()));
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(eleFirstSku);
		TextBox txtShipTo = getTxtShipTo(eleFirstSku);
		Button btnAddToCart = getBtnAddToCart(eleFirstSku);
		selectShipTo(cbbSelectShipTo, pkg.getRecipient().getValue(), txtShipTo);
		Common.click(btnAddToCart);
		selectExclusiveOffer(doAddExclusiveOffer);
	}
}
