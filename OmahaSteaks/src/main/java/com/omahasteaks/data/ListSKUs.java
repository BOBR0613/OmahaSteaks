package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.reflect.TypeToken;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuStatus;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.utils.common.Constants;

public class ListSKUs extends Database {
    private List<SKU> lstSkus;
    private String jsonFile = "src/test/resources/data/%s/sku_data.json";

    public ListSKUs() {	
	lstSkus = new ArrayList<SKU>();
    }

    @SuppressWarnings("serial")
    public void getListSKUs() {
	lstSkus = getListData(new TypeToken<List<SKU>>() {
	}.getType(),String.format(jsonFile, Constants.SITE));
    }

    public SKU getRandomSKU() {
	getListSKUs();
	Random rd = new Random();
	SKU sku = lstSkus.get(rd.nextInt(lstSkus.size()));
	return sku;
    }

    public SKU getRandomSKU(SkuType skuType, SkuStatus skuStatus) {
	getListSKUs();
	Random rd = new Random();
	List<SKU> lstFilter = new ArrayList<SKU>();
	for (SKU sku : lstSkus) {
	    if (sku != null)
		if (sku.getType().equals(skuType) && sku.getStatus().equals(skuStatus))
		    lstFilter.add(sku);
	}
	SKU sku = lstFilter.get(rd.nextInt(lstFilter.size()));
	return sku;
    }

    public SKU getSkuByName(String skuName) {
	getListSKUs();
	for (SKU sku : lstSkus) {
	    if (sku != null)
		if (sku.getName().equals(skuName))
		    return sku;
	}
	return null;
    }

    public void initEmpty() {
	lstSkus = new ArrayList<SKU>();
    }

    public List<SKU> getList() {
	return lstSkus;
    }

    public void add(SKU sku) {
	lstSkus.add(sku);
    }

    public void remove(SKU sku) {
	lstSkus.remove(sku);
    }

    public void removeSkuByID(String SkuID) {
    	List<SKU> toRemove = new ArrayList<SKU>();
    	
		for (SKU sku : lstSkus) {
		    if (sku.getId().equals(SkuID)) {
		    	//lstSkus.remove(sku);
		    	toRemove.add(sku);
		    }
		}
		lstSkus.removeAll(toRemove);
    }

    public void updateRecipient(Recipient recipientFrom, Recipient recipientTo) {
	if (lstSkus != null) {
	    for (SKU sku : lstSkus) {
		if (sku.getRecipient() == recipientFrom) {
		    sku.setRecipient(recipientTo);
		}
	    }
	}
	// return this;
    }

    public int size() {
	return lstSkus.size();
    }
}
