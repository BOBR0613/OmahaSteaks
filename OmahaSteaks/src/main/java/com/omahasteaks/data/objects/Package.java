package com.omahasteaks.data.objects;

import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;

public class Package extends SKU {
	public void init(Recipient shipTo) {
		init(SkuType.PACKAGES, shipTo);
	}

	public void initRandom(Recipient shipTo) {
		init(SkuType.PACKAGES, shipTo);
	}

	public void initRandom(SkuStatus skuStatus, Recipient shipTo) {
		init(SkuType.PACKAGES, skuStatus, shipTo);
	}
 	
}
