package com.omahasteaks.page.rewards.collection.order.review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.page.RewardOrderReviewPage;
import com.omahasteaks.page.rewards.collection.general.RewardGeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class RewardOrderReviewPage_Desktop extends RewardGeneralPage_Desktop implements RewardOrderReviewPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element elePleaseReviewOrderMsg = new Element(interfaces.get("elePleaseReviewOrderMsg"));

	// ================================================================================
	// Support Methods
	// ================================================================================

	protected Element getElementColumnInShippingBagTable(String columnName) {
		return new Element(interfaces.get("eleColumnInShippingBagTable"), columnName);
	}

	protected Element getElementShippingInfomationFields(String firstLastName) {
		return new Element(interfaces.get("eleShippingInfomationFields"), firstLastName);
	}

	protected Element getElementTitleOfShoppingBagTable(String recipientName) {
		return new Element(interfaces.get("eleTitleOfShoppingBagTable"), recipientName);
	}

	protected Element getElementSKUInformationFollowColumn(String firstLastName, int indexOfRow, String columnName) {
		return new Element(interfaces.get("eleSKUInformationFollowColumn"), firstLastName, indexOfRow, firstLastName, columnName);
	}

	protected Element getElementSKUInformationFollowRow(String firstLastName) {
		return new Element(interfaces.get("eleSKUInformationFollowRow"), firstLastName);
	}

	// ================================================================================
	// Main Methods
	// ================================================================================

	public Boolean isPleaseReviewYourOrderMsgDisplayed() {
		return elePleaseReviewOrderMsg.isDisplayed();
	}

	public Boolean isShippingColumnDisplaysInShippingTable(String columnName) {
		return getElementColumnInShippingBagTable(columnName).isDisplayed();
	}

	public Boolean isTitleOfShoppingBagTableDisplayedCorrectly(String recipientName) {
		return getElementTitleOfShoppingBagTable(recipientName).isDisplayed();
	}

	public ListRewardSKUs getListSKU(CustomerAddress shippingAddress) {
		ListRewardSKUs lstSKU = new ListRewardSKUs();
		String firstLastName = shippingAddress.firstName + " " + shippingAddress.lastName;
		int numberOfSKU = getElementSKUInformationFollowRow(firstLastName).getElements().size();
		if (numberOfSKU > 0) {
			for (int i = 1; i <= numberOfSKU; i++) {
				RewardSKU sku = new RewardSKU();
				sku.setId(Common.getText(getElementSKUInformationFollowColumn(firstLastName, i, Constants.SHOPPING_BAG_ITEM)));
				sku.setName(Common.getText(getElementSKUInformationFollowColumn(firstLastName, i, Constants.SHOPPING_BAG_SELECTIONS)));
				sku.setQuantity(Integer.parseInt(Common.getText(getElementSKUInformationFollowColumn(firstLastName, i, Constants.SHOPPING_BAG_QUANTITY))));
				lstSKU.add(sku);
			}
		}
		return lstSKU;
	}

	public String[] getShippingInformation(CustomerAddress address) {
		Common.waitForDisplayed(getElementShippingInfomationFields(address.firstName + " " + address.lastName));

		List<String> lstArs = Arrays.asList(getElementShippingInfomationFields(address.firstName + " " + address.lastName).getText().trim().split("\n"));
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

}
