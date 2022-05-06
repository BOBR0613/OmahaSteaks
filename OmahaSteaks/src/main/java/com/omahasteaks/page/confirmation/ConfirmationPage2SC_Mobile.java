package com.omahasteaks.page.confirmation;

import java.util.ArrayList;
import java.util.List;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.utils.common.Common;

public class ConfirmationPage2SC_Mobile extends ConfirmationPage2SC_Desktop implements ConfirmationPage2SC {
	public ConfirmationPage2SC_Mobile() {
		super();
		eleOrderNumber = new Element(interfaces.get("eleOrderNumber_mb"));
		eleSpecialBonusBuySkuPrice = new Element(interfaces.get("eleSpecialBonusBuySkuPrice_mb"));
		elePaymentMethodContent = new Element(interfaces.get("elePaymentMethodContent_mb"));
		eleContactInfoContent = new Element(interfaces.get("eleContactInfoContent_mb"));
	}

	public String[] getPaymentMethodContent() {
		Common.waitForDisplayed(elePaymentMethodContent);
		String[] lstArs = elePaymentMethodContent.getText().replace("Expires", "Exp:").trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.replaceAll("\\s+", " ").trim();
			if (!ars.isEmpty())
				if (ars.contains("*")) {
					lstResult.add(ars.split(" ")[0]);
					lstResult.add(ars.split(" ")[1]);
				} else
					lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	@Override
	public String getDeliveriedDateByShippingAddressSection(CustomerAddress shippingAddress) {
		Element eleDeliveriedDateByShippingAddressSection_mb = new Element(
				interfaces.get("eleDeliveriedDateByShippingAddressSection_mb"),
				shippingAddress.firstName + " " + shippingAddress.lastName);
		return Common.getText(eleDeliveriedDateByShippingAddressSection_mb);
	}
}
