package com.omahasteaks.page.category;

import java.util.List;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class CategoryPage_Desktop extends GeneralPage_Desktop implements CategoryPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected TextBox txtZipCode = new TextBox(interfaces.get("txtZipCode"));
	protected Button btnSubmitZipCode = new Button(interfaces.get("btnSubmitZipCode"));
	protected Button btnBuyGiftCard = new Button(interfaces.get("btnBuyGiftCard"));
	protected Button btnBuyEGiftCard = new Button(interfaces.get("btnBuyEGiftCard"));
	protected Element ele_FirstSKUFormInModal = new Element(interfaces.get("ele_FirstSKUFormInModal"));
	protected Button btnAddToCartInQuickViewModal =  new Button(interfaces.get("btnAddToCartInQuickViewModal"));
	protected TextBox tbGiftCardAmount =  new TextBox(interfaces.get("tbGiftCardAmount"));
	protected ComboBox cmbGiftCardShipto =  new ComboBox(interfaces.get("cmbGiftCardShipto"));
	protected TextBox tbGiftCardShiptoName =  new TextBox(interfaces.get("tbGiftCardShiptoName"));
	protected ComboBox cmbGiftCardQty =  new ComboBox(interfaces.get("cmbGiftCardQty"));
	protected Button btnGiftCardAdd =  new Button(interfaces.get("btnGiftCardAdd"));

	// ================================================================================
	// Support Methods
	// ================================================================================

	// ================================================================================
	// Main Methods
	// ================================================================================
	public void searchWithZipCode(String zipCode) {
		Common.enter(txtZipCode, zipCode);
		Common.click(btnSubmitZipCode);
	}

	public void addQuickViewItemToCart() {  
		Common.click(btnAddToCartInQuickViewModal, false);
	}

	public void addFirstSKUToCartInModal(SKU sku) {
		Common.waitForDisplayed(ele_FirstSKUFormInModal);
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUFormInModal);
		TextBox txtShipTo = getTxtShipTo(ele_FirstSKUFormInModal);
		Common.waitForDisplayed(cbbSelectShipTo);
		selectShipToInModal(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
		Button btnAddToCart = getBtnAddToCart(ele_FirstSKUFormInModal);
		Common.click(btnAddToCart, false);
		// Select Size & Count if it's required!
		if (eleFirstSizeAndCount.isDisplayed(Constants.SLEEP_TIME)) {
			selectFirstSizeAndCount();
		}
	}

	public void goToMeatsCategoryPage(String meatsCategoryName) {
		Element lnkMeatsCategory = new Element(interfaces.get("lnkMeatsCategory"), meatsCategoryName);
		Common.click(lnkMeatsCategory);
	}

	public void clickFirstSKUQuickViewLink() {
		Common.modalDialog.closeSavorDialog();
		Element eleFirstSKUQuickViewLink = new Element(interfaces.get("eleFirstSKUQuickViewLink"));
		Common.click(eleFirstSKUQuickViewLink);
		Common.waitForDOMChange();
	}

	public void clickBuyGiftCardButton() {
		Common.click(btnBuyGiftCard);
		Common.waitForPageLoad();
	}

	public void clickBuyEGiftCardButton() {
		Common.click(btnBuyEGiftCard);
		Common.waitForPageLoad();
	}

	public void addGiftCard(String giftCardType, SKU sku) {
		Element eleGiftCardFormByType = null; 
		ComboBox cboGiftCardShipTo;

		Common.modalDialog.closeSavorDialog();
		eleGiftCardFormByType = new Element(interfaces.get("eleMainGiftCard"), giftCardType);
		cboGiftCardShipTo = new ComboBox(eleGiftCardFormByType, interfaces.get("cboGiftCardShipTo"));
		Common.waitForDisplayed(cboGiftCardShipTo);
		String selected = cboGiftCardShipTo.getSelected().split(" ")[2];
		Common.modalDialog.closeSavorDialog();
		if (!selected.equals(sku.getRecipient().getValue())) {
			List<String> optionList = cboGiftCardShipTo.getOptions();
			if (optionList.contains(sku.getRecipient().getValue())) {
				Common.select(cboGiftCardShipTo, sku.getRecipient().getValue());
			} else
				Common.select(cboGiftCardShipTo, "Someone Else");
		}

		TextBox txtGiftCardAmount = new TextBox(eleGiftCardFormByType, interfaces.get("tbGiftCardAmount"));
		Common.enter(txtGiftCardAmount, String.valueOf((int) sku.getPrice()));

		if (cboGiftCardShipTo.getSelected().contains("Someone Else")) {
			TextBox txtNewShipTo = new TextBox(eleGiftCardFormByType, interfaces.get("txtNewShipTo"));
			System.out.println("Shipto :"+txtNewShipTo.getLocatorAsString());
			Common.enter(txtNewShipTo, sku.getRecipient().getValue());
		}

		Button btnAddToCartInGiftCardForm = new Button(eleGiftCardFormByType, interfaces.get("btnGiftCardAdd"));
		Common.waitForDOMChange();
		Common.waitForClickable(btnAddToCartInGiftCardForm);
		Common.tryClickByJs(btnAddToCartInGiftCardForm);
	}	


	public void addGiftCard(SKU sku) {
		addGiftCard("Omaha Steaks Gift Card", sku);
	}

	public void addEGiftCard(SKU sku) {
		addGiftCard("Omaha Steaks E-Gift Card", sku);
	}

	public void enterNewShipToInModal(String recipient) {
		TextBox txtNewShipToInModal = new TextBox(interfaces.get("txtNewShipToInModal"));
		Common.enter(txtNewShipToInModal, recipient);
	}

}
