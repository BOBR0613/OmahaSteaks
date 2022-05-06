package com.omahasteaks.page.rewards.collection.shipping.option;

import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.page.RewardShippingOptionPage;
import com.omahasteaks.page.rewards.collection.general.RewardGeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;

public class RewardShippingOptionPage_Desktop extends RewardGeneralPage_Desktop implements RewardShippingOptionPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected TextBox txtDelayDate = new TextBox(interfaces.get("txtDelayDate"));
	protected Element eleErrorMessageInShippingOptions = new Element(interfaces.get("eleErrorMessageInShippingOptions"));

	// ================================================================================
	// Support Methods
	// ================================================================================
	protected CheckBox getCategoryFromShoppingCatergoriesSection(String shippingMethod) {
		return new CheckBox(interfaces.get("rdShippingMethod"), shippingMethod);
	}

	// ================================================================================
	// Methods
	// ================================================================================
	public Boolean isShippingMethodSelected(String shippingMethod) {
		return getCategoryFromShoppingCatergoriesSection(shippingMethod).isChecked();
	}

	public Boolean isDelayedArrivalDateTextboxDisplayed() {
		return txtDelayDate.isDisplayed();
	}

	public void fillDelayedArrivalDate(String date) {
		Common.waitForDisplayed(txtDelayDate);
		Common.enter(txtDelayDate, date);
	}

	public String getDelayedArrivalDateInShippingOptionsPage() {
		return txtDelayDate.getAttribute("value");
	}

	public String getErrorgMessageInShippingOptions() {
		return Common.getText(eleErrorMessageInShippingOptions).trim();
	}
}
