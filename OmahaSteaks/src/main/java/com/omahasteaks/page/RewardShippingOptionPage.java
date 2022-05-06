package com.omahasteaks.page;

public interface RewardShippingOptionPage extends RewardGeneralPage{
	
	/**
	 * In Shipping Options page, verify shipping method is selected
	 */
	Boolean isShippingMethodSelected(String shippingMethod);
	
	/**
	 * In Shipping Options page, verify "Delayed Arrival Date" text box is displayed
	 */
	Boolean isDelayedArrivalDateTextboxDisplayed();
	
	/**
	 * In Shipping Options page, fill the date into "Delayed Arrival Date" textb ox
	 */
	void fillDelayedArrivalDate(String date);
	
	String getDelayedArrivalDateInShippingOptionsPage();
	
	String getErrorgMessageInShippingOptions();

}
