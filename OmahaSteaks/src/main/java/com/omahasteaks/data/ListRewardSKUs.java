package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;

import com.omahasteaks.data.objects.RewardSKU;

public class ListRewardSKUs extends Database {
	
	private List<RewardSKU> lstRewardSkus;
	
	public ListRewardSKUs() {
		lstRewardSkus = new ArrayList<RewardSKU>();
	}
	public void initEmpty() {
		lstRewardSkus = new ArrayList<RewardSKU>();
	}


	public void add(RewardSKU sku) {
		lstRewardSkus.add(sku);
	}

	public void remove(RewardSKU sku) {
		lstRewardSkus.remove(sku);
	}
	
    public List<RewardSKU> getList() {
    	return lstRewardSkus;
    }

	public int size() {
		return lstRewardSkus.size();
	}
	
	public boolean contains(RewardSKU sku) {
		return lstRewardSkus.contains(sku);
	}
}
