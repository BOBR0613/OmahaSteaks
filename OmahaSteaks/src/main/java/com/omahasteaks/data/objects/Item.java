package com.omahasteaks.data.objects;

import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;

public class Item extends SKU {
	private String size;
	private int count;

	public void initRandom(Recipient shipTo) {
		initRandom(shipTo, false);
	}

	public void initRandom(Recipient shipTo, boolean isWine) {
		if (isWine)
			init(SkuType.WINE, shipTo);
		else
			init(SkuType.ITEM, shipTo);
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSize() {
		return this.size;
	}

	public int getCount() {
		return this.count;
	}
}
