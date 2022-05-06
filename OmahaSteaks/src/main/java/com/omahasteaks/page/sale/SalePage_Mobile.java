package com.omahasteaks.page.sale;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.enums.ProgressiveOfferTab;
import com.omahasteaks.utils.common.Common;

public class SalePage_Mobile extends SalePage_Desktop {
	
	public void clickProgressiveOfferTabByName(ProgressiveOfferTab progressiveOfferName) {
		Element eleTab = new Element(interfaces.get("eleProgressiveOffersByName_mb"), progressiveOfferName.getValue());
		Common.click(eleTab);
		Common.waitForDOMChange();
	}
}
