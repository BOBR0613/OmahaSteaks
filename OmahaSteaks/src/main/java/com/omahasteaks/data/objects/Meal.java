package com.omahasteaks.data.objects;

import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;

public class Meal extends SKU {
	public void initRandom(Recipient shipTo) {
		init(SkuType.MEAL, shipTo);
	}

	public void initRandom(SkuStatus skuStatus, Recipient shipTo) {
		init(SkuType.MEAL, skuStatus, shipTo);
	}

}
