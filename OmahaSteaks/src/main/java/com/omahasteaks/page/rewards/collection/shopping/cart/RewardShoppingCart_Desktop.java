package com.omahasteaks.page.rewards.collection.shopping.cart;

import java.util.ArrayList;
import java.util.List;

import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.ListRewardSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.page.RewardShoppingCart;
import com.omahasteaks.page.rewards.collection.general.RewardGeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class RewardShoppingCart_Desktop extends RewardGeneralPage_Desktop implements RewardShoppingCart {

	// ================================================================================
	// Locators
	// ================================================================================
	protected Element lnkCheckout = new Element(interfaces.get("lnkCheckout"));
	protected Element lnkResumeShopping = new Element(interfaces.get("lnkResumeShopping"));
	protected TextBox txtRewardNumber = new TextBox(interfaces.get("txtRewardNumber"));
	protected Element btnHomePage = new Element(interfaces.get("btnHomePage"));
	protected Element eleSKURow = new Element(interfaces.get("eleSKURow"));
	protected Element eleEmptyCartMessage = new Element(interfaces.get("eleEmptyCartMessage"));
	protected Element txtAllRewardNumbers = new Element(interfaces.get("txtAllRewardNumbers"));

	// ================================================================================
	// Support Methods
	// ================================================================================

	protected Element getElementItemSelectionAndPtsOfSKU(int row, String fieldName) {
		return new Element(interfaces.get("eleItemSelectionAndPtsOfSKU"), row, fieldName);
	}

	protected ComboBox getElementQtyAndSendToOfSKU(int row, String fieldName) {
		return new ComboBox(getElementItemSelectionAndPtsOfSKU(row, fieldName), interfaces.get("eleQtyAndSendToOfSKU"));
	}

	protected Element getElementSKUByName(RewardSKU sku) {
		return new Element(interfaces.get("eleSKUByName"), sku.getName());
	}

	protected ComboBox getcbbShipToOfSKU(RewardSKU sku) {
		return new ComboBox(getElementSKUByName(sku), interfaces.get("cbbShipToOfSKU"));
	}

	protected ComboBox getcbbQtyOfSKU(RewardSKU sku) {
		return new ComboBox(getElementSKUByName(sku), interfaces.get("cbbQtyOfSKU"));
	}

	// ================================================================================
	// Main Methods
	// ================================================================================
	public void submitRewardNumber(String rewardNumber) {
		Common.enter(txtRewardNumber, rewardNumber);
	}

	public void goToShippingInformationPage() {
		Common.click(lnkCheckout);
		Common.waitForPageLoad();
	}

	public ListRewardSKUs getListSKUInfoInShoppingCart() {
		ListRewardSKUs lstSKU = new ListRewardSKUs();
		int numberOfSKU = eleSKURow.getElements().size();
		if (numberOfSKU > 0) {
			for (int i = 1; i <= numberOfSKU; i++) {
				RewardSKU sku = new RewardSKU();
				sku.setId(Common.getText(getElementItemSelectionAndPtsOfSKU(i, Constants.REWARDS_SHOPPING_CART_ITEM_FIELD)));
				if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
					sku.setName(getElementItemSelectionAndPtsOfSKU(i, Constants.REWARDS_SHOPPING_CART_SELECTION_FIELD).getText().split("\\n\\n")[0].replace("\\t", "").replace("\\n", "").toString().trim());
				else
					sku.setName(getElementItemSelectionAndPtsOfSKU(i, Constants.REWARDS_SHOPPING_CART_SELECTION_FIELD).getText().split("\\n")[0].toString().trim());

				sku.setPoint(Integer.parseInt(Common.getText(getElementItemSelectionAndPtsOfSKU(i, Constants.REWARDS_SHOPPING_CART_PTS_FIELD))));
				sku.setQuantity(Integer.parseInt(getElementQtyAndSendToOfSKU(i, Constants.REWARDS_SHOPPING_CART_QTY_FIELD).getSelected()));
				sku.setRecipient(Recipient.valueof(getElementQtyAndSendToOfSKU(i, Constants.REWARDS_SHOPPING_CART_SEND_TO_FIELD).getSelected()));
				lstSKU.add(sku);
			}
		}
		return lstSKU;
	}

	public void deleteSKU(RewardSKU sku) {
		Element linkDelete = new Element(getElementSKUByName(sku), interfaces.get("linkDelete"));
		Common.click(linkDelete);
		Common.waitForPageLoad();
	}

	public void continueShopping() {
		Common.click(lnkResumeShopping);
		Common.waitForPageLoad();
	}

	public void gotoHomePage() {
		Common.click(btnHomePage);
		Common.waitForPageLoad();
	}

	public String editCart(RewardSKU sku, String ComboBoxName, String valueUpdate) {
		if (ComboBoxName.equals(Constants.REWARDS_SHOPPING_CART_QTY_FIELD)) {
			Common.select(getcbbQtyOfSKU(sku), valueUpdate);
		} else if (ComboBoxName.equals(Constants.REWARDS_SHOPPING_CART_SEND_TO_FIELD)) {
			Common.select(getcbbShipToOfSKU(sku), valueUpdate);
		}
		Common.waitForDOMChange();
		return valueUpdate;
	}

	public String editCartWithRandomlyValue(RewardSKU sku, String ComboBoxName) {
		String valueUpdate = "";
		if (ComboBoxName.equals(Constants.REWARDS_SHOPPING_CART_QTY_FIELD)) {
			valueUpdate = editValueInComboBox(getcbbQtyOfSKU(sku));
		} else if (ComboBoxName.equals(Constants.REWARDS_SHOPPING_CART_SEND_TO_FIELD)) {
			valueUpdate = editValueInComboBox(getcbbShipToOfSKU(sku));
		}
		return valueUpdate;
	}

	public String getTheQuantityOfSKU(RewardSKU sku) {
		return getcbbQtyOfSKU(sku).getSelected();
	}

	public String getRecipientName(RewardSKU sku) {
		return getcbbShipToOfSKU(sku).getSelected();
	}

	public int getNumberOfRewardsNumberTextBox(RewardSKU sku) {
		return txtAllRewardNumbers.getElements().size();
	}

	public Boolean isEmptyCartMessageDisplayedCorrectly() {
		return eleEmptyCartMessage.isDisplayed();
	}

	public String[] getListErrorMessageByField(String field) {
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), field);
		List<String> lstErr = new ArrayList<String>();
		if (eleErrorMessageByField.isDisplayed(Constants.SHORT_TIME)) {
			for (int i = 0; i < eleErrorMessageByField.getElements().size(); i++)
				if (!eleErrorMessageByField.getElements().get(i).getText().equals(""))
					lstErr.add(eleErrorMessageByField.getElements().get(i).getText().replace("     ", "").replace("  ", " "));
		}
		return lstErr.toArray(new String[lstErr.size()]);
	}
}
