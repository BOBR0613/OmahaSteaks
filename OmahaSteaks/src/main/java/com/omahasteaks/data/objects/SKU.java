package com.omahasteaks.data.objects;

import java.util.ArrayList;
import java.util.List;

import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;

public class SKU {
	protected String id;
	protected String name;
	protected SkuType type;
	protected SkuStatus status;
	protected Recipient shipTo;
	protected int quantity;
	protected double price;
	protected List<SKU> listSubItems = new ArrayList<SKU>(); 
	
	public void initByName(Recipient shipTo) {
		this.id = "";
		this.name = "";
		this.type = SkuType.ITEM;
		this.status = SkuStatus.EXISTING;
		this.quantity = 0;
		this.price = 0; 
		this.shipTo = shipTo;
	}

	public void initByName(String name, Recipient shipTo) {
		init(name, shipTo);
	}

	public void init(SkuType skuType, Recipient shipTo) {
		ListSKUs LIST_SKUS = new ListSKUs();
		initFrom(LIST_SKUS.getRandomSKU(skuType, SkuStatus.EXISTING), shipTo);
	}

	public void init(SkuType skuType, SkuStatus skuStatus, Recipient shipTo) {
		ListSKUs LIST_SKUS = new ListSKUs();
		initFrom(LIST_SKUS.getRandomSKU(skuType, skuStatus), shipTo);
	}

	public void init(String name, Recipient shipTo) {
		ListSKUs LIST_SKUS = new ListSKUs();
		initFrom(LIST_SKUS.getSkuByName(name), shipTo);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setType(SkuType type) {
		this.type = type;
	}

	public SkuType getType() {
		return this.type;
	}

	public void setStatus(SkuStatus status) {
		this.status = status;
	}

	public SkuStatus getStatus() {
		return this.status;
	}
	
	public void setRecipient(String name) {
	  this.shipTo = Recipient.valueof(name); 
	}

	public void setRecipient(Recipient recipient) {
		this.shipTo = recipient;
	}

	public Recipient getRecipient() {
		return shipTo;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return this.price;
	}

	public void addSubItems(SKU sku) {
		listSubItems.add(sku);
	}

	public List<SKU> getSubItems() {
		return listSubItems;
	}

	public void cleanUpSubItems() {
		listSubItems.clear();
	}

	private void initFrom(SKU sku, Recipient shipTo) {
		id = sku.id;
		name = sku.name;
		status = sku.status;
		type = sku.type;
		this.shipTo = shipTo;
		quantity = 1;
	}
}
