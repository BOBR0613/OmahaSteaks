package com.omahasteaks.page.shopping.cart;

import java.util.List;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class ShoppingCartPage_Mobile extends ShoppingCartPage_Desktop implements ShoppingCartPage {

	protected Button btnContinue_mb = new Button(interfaces.get("btnContinue_mb"));

	public ShoppingCartPage_Mobile() {
		super();
		cbbSpecialCartBonusSavingsShipTo = new ComboBox(interfaces.get("cbbProductShipTo_mb"));
		btnCheckout = new Button(interfaces.get("btnCheckout_mb"));
		btnCartBonusSavingsAddToCart = new Button(interfaces.get("btnCartBonusSavingsAddToCart_mb"));
		lnkSignInToRedeem = new Link(interfaces.get("lnkSignInToRedeem_mb"));
		eleErrorMessageInSpecialCart = new Element(interfaces.get("eleErrorMessageInSpecialCart_mb"));
		lnkCreateYourOwnTrio = new Link(interfaces.get("lnkCreateYourOwnTrio_mb"));
		eleCreateYourOwnTrioModal = new Element(interfaces.get("eleCreateYourOwnTrioModal_mb"));
		eleWeAreSorryModal = new Element(interfaces.get("eleWeAreSorryModal_mb"));
		lnkEnterAGiftCardOrVoucher = new Link(interfaces.get("lnkEnterAGiftCardOrVoucher_mb"));
		btnAddToCart_TreatYourself = new Button(interfaces.get("btnAddToCart_TreatYourself_mb"));
		eleSkuID_TreatYourself = new Element(interfaces.get("eleSkuID_TreatYourself_mb"));
		eleTreatYourself = new Element(interfaces.get("eleTreatYourself_mb"));
		lblSpecialCartBonusSavings = new Link(interfaces.get("lblSpecialCartBonusSavings_mb"));
		eleSelectYourSteakloverRewardsModal = new Element(interfaces.get("eleSelectYourSteakloverRewardsModal_mb"));
		eleErrorHighlighted = new Element(interfaces.get("eleErrorHighlighted_mb"));
		eleAvailablePoints = new Element(interfaces.get("eleAvailablePoints_mb"));
	}

	// ================================================================================
	// Locators
	// ================================================================================

	// private Link lnkCloseViewItemsModal_mb = new
	// Link(interfaces.get("lnkCloseViewItemsModal_mb"));

	@Override
	protected Element getSkuElementById(String recipient, String skuId) {
		return new Element(interfaces.get("eleSkuById_mb"), recipient, Common.getNumberFromText(skuId));
	}

	@Override
	protected Link getLnkSendToSomeoneElse(Element eleSkuById) {
		return new Link(eleSkuById, interfaces.get("lnkSendToSomeoneElse_mb"));
	}

	@Override
	protected Link getLnkRemoveBySkuId(Element eleSku) {
		return new Link(eleSku, interfaces.get("lnkRemoveBySkuId_mb"));
	}

	@Override
	protected Element getSkuElementByName(Recipient recipient, String skuName) {
		return new Element(interfaces.get("eleSkuByName_mb"), recipient.getValue(), skuName);
	}
	// ================================================================================
	// Main Methods
	// ================================================================================

	@Override
	protected Button getButtuonSaveSendTo(Element eleFormSendCartTo) {
		return new Button(eleFormSendCartTo, interfaces.get("buttonSaveSendTo_mb"));
	}

	@Override
	public void addSpecialCartBonus(Recipient recipient) {
		Common.click(lblSpecialCartBonusSavings);
		selectShipTo(recipient.getValue());
		Common.click(btnCartBonusSavingsAddToCart);
		Common.waitForDOMChange();
		// Common.waitForDisplayed(btnCloseModal);
	}

	@Override
	public void addFirstSKUInSpecialCartBonus(Recipient recipient) {
		Common.click(lblSpecialCartBonusSavings);
		selectFirstSKUInSpecialCartBonus();
		selectShipTo(recipient.getValue());
		Common.click(btnCartBonusSavingsAddToCart);
		Common.waitForDisplayed(btnCloseModal);
	}

	@Override
	protected Button getBtnAddToCart_InCartSpecial_By_Recipient(String recipient) {
		return new Button(interfaces.get("btnAddToCart_InCartSpecial_By_Recipient_mb"), recipient);
	}

	@Override
	public void checkOut() {
		if (DriverUtils.getDriverType().equals(Constants.BROWSER_SAFARI)) {
			Common.click(btnCheckout);
			Common.waitForDOMChange();
			if (btnContinue_mb.isDisplayed())
				Common.click(btnContinue_mb);
		} else {
			// List<WebElement> btnCheckouts = btnCheckout.getElements();
			for (int i = 0; i < btnCheckout.getElements().size(); i++) {
				if (btnCheckout.getElements().get(i).isDisplayed()) {
					Common.focus(btnCheckout);
					Common.click(btnCheckout.getElements().get(i));
					Common.waitForDOMChange();
					if (btnContinue_mb.isDisplayed())
						Common.click(btnContinue_mb);
					return;
				}
			}
		}
		Common.waitForTitleChange();
	}

	@Override
	protected void uncheckAllItemInSpecialCartBonus() {
		CheckBox chbCartBonusSavings_SKU_By_Index = new CheckBox(interfaces.get("chbCartBonusSavings_SKU_By_Index"), 1);
		Common.waitForDisplayed(chbCartBonusSavings_SKU_By_Index);
		int size = chbCartBonusSavings_SKU_List.getElements().size();
		for (int i = 1; i <= size; i++) {
			chbCartBonusSavings_SKU_By_Index = new CheckBox(interfaces.get("chbCartBonusSavings_SKU_By_Index"), i);
			Common.uncheck(chbCartBonusSavings_SKU_By_Index);
		}
	}

	@Override
	public void uncheckAllItemAndAddSpecialCartBonus(Recipient recipient) {
		Common.click(lblSpecialCartBonusSavings);
		uncheckAllItemInSpecialCartBonus();
		selectShipTo(recipient.getValue());
		Common.click(btnCartBonusSavingsAddToCart);
	}

	@Override
	public boolean isSkuInSpecialCartAdded(Recipient recipient, String skuName) {
		Element eleSkuInCartSpecial = new Element(interfaces.get("eleAddedSkuName_InCartSpecial_By_Recipient_mb"), recipient.getValue(), skuName);
		return eleSkuInCartSpecial.isDisplayed();
	}

	@Override
	public String getSkuNameInSpecialCartBonus(Recipient recipient) {
		Element eleSkuNameInCartSpecial = new Element(interfaces.get("eleSkuName_InCartSpecial_By_Recipient_mb"), recipient.getValue());
		Common.waitForDisplayed(eleSkuNameInCartSpecial);
		return eleSkuNameInCartSpecial.getText().replace("In-cart Special - ", "");
	}

	/*
	 * @Override public boolean isSubItemsDisplayed(SKU sku) { Element eleSkuById =
	 * getSkuElementById(sku.getRecipient().getValue(), sku.getId()); Link
	 * lnkViewItem = new Link(eleSkuById, interfaces.get("lnkViewItems_mb"));
	 * Element eleSusItem;
	 * 
	 * Common.click(lnkViewItem);
	 * Common.waitForDisplayed(lnkCloseViewItemsModal_mb);
	 * 
	 * for (SKU skuSubItem : sku.getSubItems()) { eleSusItem = new
	 * Element(interfaces.get("eleSubItem_mb"), skuSubItem.getName());
	 * Logger.info(skuSubItem.getName()); if (!eleSusItem.isDisplayed()) {
	 * Common.click(lnkCloseViewItemsModal_mb); return false; } }
	 * Common.click(lnkCloseViewItemsModal_mb); return true; }
	 */

	@Override
	public void clickCreateYourOwnTrioLink() {
		Common.click(lblSpecialCartBonusSavings);
		Common.click(lnkCreateYourOwnTrio);
	}

	@Override
	public void addCreateYourOwnTrioItem(SKU Sku) {
		clickCreateYourOwnTrioLink();
		Common.waitForDisplayed(eleCreateYourOwnTrioModal);

		ComboBox cbbSpecialCartBonusSavingsShipTo = new ComboBox(eleCreateYourOwnTrioModal, interfaces.get("cbbSendTo"));
		selectShipTo(cbbSpecialCartBonusSavingsShipTo, Sku.getRecipient().getValue());

		selectRandomItemsInCreateYourOwnTrioModal(3);

		Button btnAddToCart = new Button(eleCreateYourOwnTrioModal, interfaces.get("btnAddToCartInShoppingCart"));
		Common.scrollToTop();
		btnAddToCart.click();
	}

	@Override
	public boolean isGiftCardByNameAdded(String recipient, String itemName) {
		Element eleGiftCardByName = new Element(interfaces.get("eleGiftCardByName_mb"), recipient, itemName);
		return eleGiftCardByName.isDisplayed();
	}

	@Override
	public void selectEditSku(SKU sku) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		Link editSku = new Link(eleSkuById, interfaces.get("lnkEditBySkuId_mb"));
		Common.click(editSku);
		Common.waitForDOMChange();
	}

	@Override
	public boolean isCartShipmentDisplayed(Recipient recipient) {
		Element eleCartShipmentByRecipient = new Element(interfaces.get("eleCartShipmentByRecipient_mb"), recipient.getValue());
		return eleCartShipmentByRecipient.isDisplayed();
	}

	private void clickSelectEditCart(Recipient recipient) {
		Element eleSelectEditCart_mb = new Element(interfaces.get("eleSelectEditCart_mb"), recipient.getValue());
		Common.click(eleSelectEditCart_mb);
		waitForShoppingCartPageDisplays();
	}

	@Override
	public void sendCartTo(Recipient fromCart, Recipient toCart) {
		waitForShoppingCartPageDisplays();
		clickSelectEditCart(fromCart);
		int numberOfCart = eleCartList.getElements().size();
		for (int i = 1; i <= numberOfCart; i++) {
			Element eleFormSendCartTo = new Element(interfaces.get("eleFormSendCartTo"), i);
			ComboBox cbbSendTo = new ComboBox(eleFormSendCartTo, interfaces.get("cbbSendTo"));
			Common.waitForDisplayed(cbbSendTo);
			if (cbbSendTo.getSelected().equals(fromCart.getValue())) {
				List<String> optionList = cbbSendTo.getOptions();
				if (optionList.contains(toCart.getValue())) {
					Common.select(cbbSendTo, toCart.getValue());
					Common.waitForDOMChange();
				} else {
					TextBox txtRecipientName = new TextBox(eleFormSendCartTo, interfaces.get("txtRecipientName"));
					Button buttonSaveSendTo = getButtuonSaveSendTo(eleFormSendCartTo);
					if (!txtRecipientName.isDisplayed())
						Common.select(cbbSendTo, "Someone Else");
					Common.enter(txtRecipientName, toCart.getValue());
					Common.click(buttonSaveSendTo);
					Common.waitForDOMChange();
				}
				return;
			}
		}
	}

	@Override
	protected Element getItemElement(Item item) {
		Element eleItemInCart = new Element(interfaces.get("eleItemInCart"), item.getRecipient().getValue(), item.getName(), item.getSize(), item.getCount());
		if (eleItemInCart.isDisplayed()) {
			return eleItemInCart;
		}
		return new Element(interfaces.get("eleItemByNameInCart_mb"), item.getRecipient().getValue(), item.getName());
	}

	@Override
	public boolean isMyselfCartSectionAddedAtTheTopOfShoppingCart() {
		clickSelectEditCart(Recipient.MYSELF);
		Element eleFormSendCartTo = new Element(interfaces.get("eleFormSendCartTo"), 1);
		ComboBox cbbSendTo = new ComboBox(eleFormSendCartTo, interfaces.get("cbbSendTo"));
		return cbbSendTo.getSelected().equals(Recipient.MYSELF.getValue());
	}

	@Override
	public String getCartShipmentTitleByRecipient(Recipient recipient, int shpmnt) {
		Element eleCartShipmentTitleByRecipient = new Element(interfaces.get("eleCartShipmentTitleByRecipient_mb"), recipient.getValue());
		return Common.getText(eleCartShipmentTitleByRecipient).split("-")[0].trim();
	}

	@Override
	protected Element getElePremiseOfCartSection(Recipient recipient) {
		return new Element(interfaces.get("elePremiseOfCartSection_mb"), recipient.getValue());
	}

	@Override
	public void clickAddToCartInSelectYourSlrRewardsModal() {
		Button btnAddToCart = new Button(eleSelectYourSteakloverRewardsModal, interfaces.get("btnAddToCartInShoppingCart_mb"));
		Common.click(btnAddToCart, false);
	}

	@Override
	public String getAddedSKUNameInSteakloverRewardsModal() {
		return Common.getText(eleSKUNameInStloverRwdModal);
	}

	@Override
	public String getSKUNameInSelectSteakloverRewardsModal() {
		Element eleSKUNameInSelectStloverRwdModal = new Element(eleFirstSLRReward, interfaces.get("eleSKUNameInSelectStloverRwdModal"));
		return Common.getText(eleSKUNameInSelectStloverRwdModal);
	}

	@Override
	public String getSKUPointInSelectSteakloverRewardsModal() {
		Element elePointsInSelectStwloverRwdModal = new Element(eleFirstSLRReward, interfaces.get("elePointsInSelectStwloverRwdModal_mb"));
		return Common.getText(elePointsInSelectStwloverRwdModal).split(" ")[0].trim();
	}
}
