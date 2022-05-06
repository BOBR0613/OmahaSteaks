package com.omahasteaks.data.objects;

import java.util.ArrayList;
import java.util.List;

import com.omahasteaks.data.enums.Recipient;

public class RewardSKU extends SKU {

	protected int point;

	protected List<RewardSKU> listSubRewards = new ArrayList<RewardSKU>();

	public void init(Recipient shipTo) {
		this.shipTo = shipTo;
		quantity = 1;
	}
	
	public void init(Recipient shipTo, int point) {
		this.shipTo = shipTo;
		quantity = 1;
		this.point = point;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public List<RewardSKU> getListSubItems() {
		return listSubRewards;
	}

	@Override
	public boolean equals(Object rewardsSKU) {
		if (rewardsSKU == null)
			return false;
		if (!(rewardsSKU instanceof RewardSKU))
			return false;
		if (rewardsSKU == this)
			return true;
		return (this.id.equals(((RewardSKU) rewardsSKU).id) && this.name.equals(((RewardSKU) rewardsSKU).name) 
				&& this.point == (((RewardSKU) rewardsSKU).point) && this.shipTo.equals(((RewardSKU) rewardsSKU).shipTo)
				&& this.quantity == (((RewardSKU) rewardsSKU).quantity));
	}
}
