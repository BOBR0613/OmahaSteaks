package com.omahasteaks.page;

import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.objects.CustomerAddress;

public interface RewardOrderReviewPage extends RewardGeneralPage {
	/**
	 * Verify warning message Please Review Your Order is displayed
	 */
	Boolean isPleaseReviewYourOrderMsgDisplayed();

	/**
	 * Verify Shopping Column is displayed correctly in Shipping Table at Shopping Cart page
	 */
	Boolean isShippingColumnDisplaysInShippingTable(String columnName);

	/**
	 * Verify title of shopping bag table is displayed correctly
	 */
	Boolean isTitleOfShoppingBagTableDisplayedCorrectly(String recipientName);

	/**
	 * Get shipping information displayed under Recipient's Cart
	 */
	String[] getShippingInformation(CustomerAddress address);

	/**
	 * Get list SKU displayed under  Recipient's Cart
	 */
	ListRewardSKUs getListSKU(CustomerAddress shippingAddress);

}
