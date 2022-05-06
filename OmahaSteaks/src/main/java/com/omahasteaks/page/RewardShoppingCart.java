package com.omahasteaks.page;

import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.objects.RewardSKU;

public interface RewardShoppingCart extends RewardGeneralPage {
	void submitRewardNumber(String rewardNumber);

	void goToShippingInformationPage();

	ListRewardSKUs getListSKUInfoInShoppingCart();

	void deleteSKU(RewardSKU sku);

	void continueShopping();

	String getTheQuantityOfSKU(RewardSKU sku);
	
	String getRecipientName(RewardSKU sku);

	int getNumberOfRewardsNumberTextBox(RewardSKU sku);

	Boolean isEmptyCartMessageDisplayedCorrectly();
	
	public String editCart(RewardSKU sku, String ComboBoxName, String valueUpdate);
	
	public String editCartWithRandomlyValue(RewardSKU sku, String ComboBoxName);
	
	String[] getListErrorMessageByField(String field);

	void gotoHomePage();

}
