package com.omahasteaks.page.shopping.cart;

import java.util.ArrayList;
import java.util.List; 

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class ShoppingCartPage_Desktop extends GeneralPage_Desktop implements ShoppingCartPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected ComboBox cbbSpecialCartBonusSavingsShipTo = new ComboBox(interfaces.get("cbbSpecialCartBonusSavingsShipTo"));
	protected ComboBox cbbTheUpsellSkuShipTo = new ComboBox(interfaces.get("cbbTheUpsellSkuShipTo"));
	protected ComboBox cbbShipToInModal = new ComboBox(interfaces.get("cbbShipToInModal"));
	protected ComboBox cbbShipToInSelectStloverRwdModal = new ComboBox(interfaces.get("cbbShipToInSelectStloverRwdModal"));
	protected Link     lnkSendMyselfCart = new Link(interfaces.get("lnkSendMyselfCart"));
 
	protected TextBox txtShipToOnPopup = new TextBox(interfaces.get("txtShipToOnPopup"));
	protected Link lnkSignInToRedeem = new Link(interfaces.get("lnkSignInToRedeem"));
	protected Link lblSpecialCartBonusSavings = new Link(interfaces.get("LblSpecialCartBonusSavings"));
	protected Link lnkCreateYourOwnTrio = new Link(interfaces.get("lnkCreateYourOwnTrio"));
	protected Link lnkEnterAGiftCardOrVoucher = new Link(interfaces.get("lnkEnterAGiftCardOrVoucher"));

	protected Button btnCartBonusSavingsAddToCart = new Button(interfaces.get("btnCartBonusSavingsAddToCart"));
	protected Button btnTheUpsellSkuAddToCart = new Button(interfaces.get("btnTheUpsellSkuAddToCart"));
	protected Button btnCheckout = new Button(interfaces.get("btnCheckout"));
	protected Button btnCartRemoveConfirm = new Button(interfaces.get("btnCartRemoveConfirm"));
	protected Button btnCartRemoveCancel = new Button(interfaces.get("btnCartRemoveCancel"));
	protected Button btnAddToOnPopup = new Button(interfaces.get("btnAddToOnPopup"));
	protected Button btnMoveToOnPopup = new Button(interfaces.get("btnMoveToOnPopup"));
	protected Button btnClosePopupModal = new Button(interfaces.get("btnClosePopupModal"));
	protected Button btnCheckOutWithPayPal = new Button(interfaces.get("btnCheckOutWithPayPal"));
	protected Button btnAddToCart_TreatYourself = new Button(interfaces.get("btnAddToCart_TreatYourself"));
	protected Button btnContinue = new Button(interfaces.get("btnContinue"));
	protected Button btnCreateYourOwnTrio = new Button(interfaces.get("btnCreateYourOwnTrio"));
	
	protected Element eleSkuID = new Element(interfaces.get("eleSkuID"));
	protected Element eleSkuSuffix = new Element(interfaces.get("eleSkuSuffix"));
	protected Element eleSkuName = new Element(interfaces.get("eleSkuName"));
	protected Element eleEmptyCartMessage = new Element(interfaces.get("eleEmptyCartMessage"));
	protected Element formShoppingCart = new Element(interfaces.get("formShoppingCart"));
	protected Element panelShoppingCart = new Element(interfaces.get("panelShoppingCart"));
	protected Element eleCartItem = new Element(interfaces.get("eleCartItem"));
	protected Element chbCartBonusSavings_SKU_List = new Element(interfaces.get("chbCartBonusSavings_SKU_List"));
	protected Element eleCartList = new Element(interfaces.get("eleCartList"));
	protected Element eleErrorMessageOnPage = new Element(interfaces.get("eleErrorMessageOnPage"));
	protected Element eleErrorMessageInSpecialCart = new Element(interfaces.get("eleErrorMessageInSpecialCart"));
	protected Element eleSKUNameInAddedToCart = new Element(interfaces.get("eleSKUNameInAddedToCart"));
	protected Element eleWeAreSorryModal = new Element(interfaces.get("eleWeAreSorryModal"));
	protected Element eleCreateYourOwnTrioModal = new Element(interfaces.get("eleCreateYourOwnTrioModal"));
	protected Element eleTreatYourself = new Element(interfaces.get("eleTreatYourself"));
	protected Element eleSkuID_TreatYourself = new Element(interfaces.get("eleSkuID_TreatYourself"));
	protected Element eleSelectYourSteakloverRewardsModal = new Element(interfaces.get("eleSelectYourSteakloverRewardsModal"));
	protected Element eleYourBalancePts = new Element(interfaces.get("eleYourBalancePts"));
	protected Element eleSteakloverRewardsModal = new Element(interfaces.get("eleSteakloverRewardsModal"));
	protected Element eleSKUNameInStloverRwdModal = new Element(interfaces.get("eleSKUNameInStloverRwdModal"));
	protected Element eleSKUPointInStloverRwdModal = new Element(interfaces.get("eleSKUPointInStloverRwdModal"));
	protected Element elePremiseOfCartSection = new Element(interfaces.get("elePremiseOfCartSection"));
	protected Element eleAvailablePoints = new Element(interfaces.get("eleAvailablePoints"));
	protected Element eleErrorHighlighted = new Element(interfaces.get("eleErrorHighlighted"));
	protected Element eleFirstSLRReward = new Element(interfaces.get("eleFirstSLRReward"));
	protected Element btnJoinSlrGoldModal = new Element(interfaces.get("btnJoinSlrGoldModal"));
	protected Element btnSlrGoldSignup = new Element(interfaces.get("btnSlrGoldSignup"));
	protected Element btnJoinSlrGoldSuccessModal = new Element(interfaces.get("btnJoinSlrGoldSuccessModal"));
	protected Label   lblSlrGoldSuccessMessage = new Label(interfaces.get("lblSlrGoldSuccessMessage"));

	protected CheckBox radAddToOnPopup = new CheckBox(interfaces.get("radAddToOnPopup"));
	protected CheckBox radMoveToOnPopup = new CheckBox(interfaces.get("radMoveToOnPopup"));
	protected Element eleItemSubtotal = new Element(interfaces.get("eleItemSubtotal"));
	protected Label lblAdditionalSavings = new Label(interfaces.get("lblAdditionalSavings"));
	protected Label eleCartSummarySection = new Label(interfaces.get("eleCartSummarySection"));
	protected Label lblMessageInStloverRwd = new Label(interfaces.get("lblMessageInStloverRwd"));
	protected Link lnkSignInOnGoldMembership = new Link(interfaces.get("lnkSignInOnGoldMembership"));
	protected Label eleFreeShippingMembership = new Label(interfaces.get("eleFreeShippingMembership"));
	protected Label lblMinCartErrorMessage = new Label(interfaces.get("lblMinCartErrorMessage"));
	
	// ================================================================================
	// Support Methods
	// ================================================================================
	protected Button getBtnAddToCart_InCartSpecial_By_Recipient(String recipient) {
		return new Button(interfaces.get("btnAddToCart_InCartSpecial_By_Recipient"), recipient);
	}

	protected void selectShipTo(String recipient) {
		Common.waitForDisplayed(cbbSpecialCartBonusSavingsShipTo);
		List<String> optionList = cbbSpecialCartBonusSavingsShipTo.getOptions();
		if (optionList.contains(recipient)) {
			Common.select(cbbSpecialCartBonusSavingsShipTo, recipient);
		}
	}

	protected void selectThePerfectWineShipTo(String recipient) {
		Common.waitForDisplayed(cbbTheUpsellSkuShipTo);
		List<String> optionList = cbbTheUpsellSkuShipTo.getOptions();
		if (optionList.contains(recipient)) {
			Common.select(cbbTheUpsellSkuShipTo, recipient);
		}
	}

	protected Element getSkuElementById(String recipient, String skuId) {
		return new Element(interfaces.get("eleSkuById"), recipient, Common.getNumberFromText(skuId));
	}

	protected Element getItemElement(Item item) {
		
		Element eleItemInCart = new Element(interfaces.get("eleItemInCart"), item.getRecipient().getValue(), item.getName(), item.getSize(), item.getCount());
		if (eleItemInCart.isDisplayed()) {
			return eleItemInCart;
		}
		return new Element(interfaces.get("eleItemByNameInCart"), item.getRecipient().getValue(), item.getName());
	}

	
	protected Element getEleFreeShippingMsg(Recipient recipient) {
		
		Element eleShippingMsg = new Element(interfaces.get("eleFreeShippingMsg"), recipient.getValue());
		return eleShippingMsg;
	}
	
	
	protected void removeSkuByElement(Element eleSku, boolean isClickYesButton) {
		//   Link lnkRemoveBySkuId = new Link(eleSku, interfaces.get("lnkRemoveBySkuId"));
		Common.waitForPageLoad();
		Link lnkRemoveBySkuId = getLnkRemoveBySkuId(eleSku);
		Link lnkRemoveYesBySkuId = new Link(eleSku, interfaces.get("lnkRemoveYesBySkuId"));
		Link lnkRemoveNoBySkuId = new Link(eleSku, interfaces.get("lnkRemoveNoBySkuId"));
		lnkRemoveBySkuId.focus();
		lnkRemoveBySkuId.click();
		Common.waitForDOMChange();
		if (isClickYesButton) {
			// Click Yes
			Common.click(lnkRemoveYesBySkuId);
			waitForLoadingIconInvisible();
			Common.waitForPageLoad();
		} else {
			// Click No
			Common.click(lnkRemoveNoBySkuId);
		}
		Common.waitForDOMChange();
	}

	protected void selectSomeoneElseLink(SKU sku) {
		waitForShoppingCartPageDisplays();
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), Common.getNumberFromText(sku.getId()));
		Common.waitForDisplayed(eleSkuById);
		// Link lnkSendToSomeoneElse = new Link(eleSkuById,
		// interfaces.get("lnkSendToSomeoneElse"));
		Common.click(getLnkSendToSomeoneElse(eleSkuById));
	}

	protected void selectFirstSKUInSpecialCartBonus() {
		// TBD;
		uncheckAllItemInSpecialCartBonus();
		CheckBox chbCartBonusSavings_SKU_By_Index = new CheckBox(interfaces.get("chbCartBonusSavings_SKU_By_Index"), 1);
		Common.check(chbCartBonusSavings_SKU_By_Index);
	}

	protected void uncheckAllItemInSpecialCartBonus() {
		CheckBox chbCartBonusSavings_SKU_By_Index = new CheckBox(interfaces.get("chbCartBonusSavings_SKU_By_Index"), 1);
		Common.waitForDisplayed(chbCartBonusSavings_SKU_By_Index);
		int size = chbCartBonusSavings_SKU_List.getElements().size();
		for (int i = 1; i <= size; i++) {
			chbCartBonusSavings_SKU_By_Index = new CheckBox(interfaces.get("chbCartBonusSavings_SKU_By_Index"), i);
			DriverUtils.delay(1);
			Common.uncheck(chbCartBonusSavings_SKU_By_Index);
		}
	}

	protected Button getButtuonSaveSendTo(Element eleFormSendCartTo) {
		return new Button(eleFormSendCartTo, interfaces.get("buttonSaveSendTo"));
	}

	private void waitForListCartItemSizeChange(int sizeBeforeChange, boolean isIncrease) {
		int itemSize = sizeBeforeChange;
		int curSize = 0;
		if (!isIncrease)
			curSize = itemSize;
		boolean isChanged = false;
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		while (!isChanged && endTime < timeout) {
			curSize = eleCartItem.getElements().size();
			isChanged = (isIncrease) ? curSize > itemSize : curSize < itemSize;
			DriverUtils.delay(1);
			endTime = System.currentTimeMillis() - startTime;
		}
	}

	protected Link getLnkSendToSomeoneElse(Element eleSkuById) {
		return new Link(eleSkuById, interfaces.get("lnkSendToSomeoneElse"));
	}

	protected Link getLnkRemoveBySkuId(Element eleSku) {
		return new Link(eleSku, interfaces.get("lnkRemoveBySkuId"));
	}

	protected Element getElePremiseOfCartSection(Recipient recipient) {
		return new Element(interfaces.get("elePremiseOfCartSection"), recipient.getValue());
	}

	protected Element getSkuElementByName(Recipient recipient, String skuName) {
	   Element rtnSkuElem = new Element(interfaces.get("eleSkuByName"), recipient.getValue(), skuName.trim());
	   return rtnSkuElem;
	}

	protected Element getFreeItem(Recipient recipient, String skuName) {
		Element rtnSkuElem = new Element(interfaces.get("eleFreeItem"), recipient.getValue(), skuName.trim());
		return rtnSkuElem;
	}

	
	protected Element getEleThePriceOfFreeFood(SKU sku) {
		return new Element(interfaces.get("eleThePriceOfFreeFood"),sku.getRecipient().getValue(),sku.getName());
	}

	// ================================================================================
	// Main Methods
	// ================================================================================
	public void removeItem(Item item) {
		waitForShoppingCartPageDisplays();
		Element itemInCart = getItemElement(item);
		removeSkuByElement(itemInCart, true);
	}

	public void removeItem(Item item, boolean confirm) {
		waitForShoppingCartPageDisplays();
		Element itemInCart = getItemElement(item);
		removeSkuByElement(itemInCart, true);
		if (confirm) {
			Common.click(btnCartRemoveConfirm);
			Common.waitForNotDisplayed(btnCartRemoveConfirm);
			Common.waitForNotDisplayed(itemInCart);
		} else {
			Common.click(btnCartRemoveCancel);
			Common.waitForNotDisplayed(btnCartRemoveCancel);
		}
	}

	public boolean isMinThresholdMessageShown() {
	  return lblMinCartErrorMessage.isDisplayed();	
	}
	
	
	public boolean isItemAddedToCart(Item item) {
		Common.waitForPageLoad();
		waitForShoppingCartPageDisplays();
		Element itemInCart = getSkuElementById(item.getRecipient().getValue(), item.getId());
		return itemInCart.isDisplayed(Constants.SLEEP_TIME);
	}

	public void checkOut() {  
		Common.delay(5);
		Common.waitForDisplayed(eleCartSummarySection);
		Common.scrollElementToCenterScreen(eleCartSummarySection);
		Common.waitForDOMChange();
		Common.click(btnCheckout);
		Common.waitForPageLoad();
	}

	public void clickContinue() {
		Common.waitForDisplayed(btnContinue);
		Common.scrollElementToCenterScreen(btnContinue);
		Common.click(btnContinue);
		Common.waitForPageLoad();
	}
	
	public boolean isCheckoutButtonDisplayed() {
	  return btnCheckout.isDisplayed();
	}

	@Override
	public boolean isJoinSlrGoldButtonShown() {
	  return btnSlrGoldSignup.isDisplayed();
	}
	
	@Override
	public void joinSlrGoldMembershipToCart() {
	  Common.tryClickByJs(btnSlrGoldSignup);
	  Common.waitForDOMChange();
	  Common.waitForPageLoad();
	  Common.focus(btnJoinSlrGoldModal);
	  Common.tryClickByJs(btnJoinSlrGoldModal);
	  Common.waitForDOMChange();
	  Common.waitForPageLoad();
	  Common.waitForElementExistedInDOM(btnJoinSlrGoldSuccessModal);
	  Common.focus(btnJoinSlrGoldSuccessModal);
	  Common.tryClickByJs(btnJoinSlrGoldSuccessModal);
	  Common.waitForDOMChange();
	}
	
	@Override
	public boolean joinedSlrGold() {
	  return lblSlrGoldSuccessMessage.isDisplayed();
	}
	
	
	@Override
	public void goToSignInPage() {
		/*
		 * 10/23/2019: Modify by An Nguyen: Due to the Sign In link on the Cart Summary
		 * is changed by the Sign In link in the Gold Membership
		 */

		/*
		 * Common.moveTo(lnkSignInToRedeem); Common.focus(lnkSignInToRedeem);
		 * Common.click(lnkSignInToRedeem); Common.waitForTitleChange();
		 */

		Common.moveTo(lnkSignInOnGoldMembership);
		Common.focus(lnkSignInOnGoldMembership);
		Common.click(lnkSignInOnGoldMembership);
		Common.waitForTitleChange();
	}

	public void addSpecialCartBonus(Recipient recipient) {
		waitForShoppingCartPageDisplays();
		Common.waitForPageLoad();
		Common.focus(btnCartBonusSavingsAddToCart);
		Common.waitForDisplayed(lblSpecialCartBonusSavings);
		selectShipTo(recipient.getValue());
		Common.click(btnCartBonusSavingsAddToCart);
		waitForLoadingIconInvisible();
		// Common.waitForDisplayed(btnCloseModal);
	}

	public void addTheUpsellSku(Recipient recipient) {
		waitForShoppingCartPageDisplays();
		Common.focus(cbbTheUpsellSkuShipTo);
		selectThePerfectWineShipTo(recipient.getValue());
		Common.click(btnTheUpsellSkuAddToCart);
		waitForLoadingIconInvisible();
		Common.waitForDisplayed(btnCloseModal);

	}

	public boolean isTheUpsellSkuDisplayed() {
		waitForShoppingCartPageDisplays();
		return btnTheUpsellSkuAddToCart.isDisplayed();
	}

	public void uncheckAllItemAndAddSpecialCartBonus(Recipient recipient) {
		Common.waitForDisplayed(btnCartBonusSavingsAddToCart);
		Common.scrollElementToCenterScreen(btnCartBonusSavingsAddToCart);
		Common.waitForDisplayed(lblSpecialCartBonusSavings);
		uncheckAllItemInSpecialCartBonus();
		selectShipTo(recipient.getValue());
		Common.click(btnCartBonusSavingsAddToCart);
	}

	public String getErrorMessageInSpecialCartBonus() {
		Common.waitForDisplayed(eleErrorMessageInSpecialCart);
		return eleErrorMessageInSpecialCart.getText();
	}

	public void addFirstSKUInSpecialCartBonus(Recipient recipient) {
		Common.scrollElementToCenterScreen(btnCartBonusSavingsAddToCart);
		Common.waitForDisplayed(lblSpecialCartBonusSavings);
		selectFirstSKUInSpecialCartBonus();
		selectShipTo(recipient.getValue());
		Common.click(btnCartBonusSavingsAddToCart);
		waitForLoadingIconInvisible();
		Common.waitForDisplayed(btnCloseModal);
	}

	public void addInCartSpecial(Recipient recipient) {
		Button btnAddToCart_InCartSpecial_By_Recipient = getBtnAddToCart_InCartSpecial_By_Recipient(recipient.getValue());
		Common.waitForDisplayed(btnAddToCart_InCartSpecial_By_Recipient);
		Common.focus(btnAddToCart_InCartSpecial_By_Recipient);
		Common.click(btnAddToCart_InCartSpecial_By_Recipient);
		waitForLoadingIconInvisible();
	}

	public String getEmptyMessageText() {
		Common.modalDialog.closeModalDialog();
		return Common.getText(eleEmptyCartMessage);
	}

	public String getAddedSkuIdFromModal() {
		Common.waitForDisplayed(eleSkuID);
		Common.waitForDisplayed(eleSkuSuffix);
		String SkuId = eleSkuID.getText() + eleSkuSuffix.getText();
		return SkuId;
	}

	public String getAddedSkuNameFromModal() {
		Common.waitForDisplayed(eleSkuName);
		return eleSkuName.getText();
	}

	public void closeModalDialog() {
		int sizeBeforeAdded = eleCartItem.getElements().size();
		Common.click(btnClosePopupModal, false);
		Common.waitForNotDisplayed(eleSkuID);
		waitForLoadingIconInvisible();
		waitForListCartItemSizeChange(sizeBeforeAdded, true);
	}

	public void removeSkuById(Recipient recipient, String skuId) {
		Element eleSkuById = getSkuElementById(recipient.getValue(), skuId);
		int sizeBeforeRemoved = eleCartItem.getElements().size();
		removeSkuByElement(eleSkuById, true);
		Common.waitForNotDisplayed(eleSkuById);
		waitForListCartItemSizeChange(sizeBeforeRemoved, false);
	}

	public void removeSkuById(SKU sku, boolean isClickYesButton) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		int sizeBeforeRemoved = eleCartItem.getElements().size();
		Common.waitForPageLoad();
		removeSkuByElement(eleSkuById, isClickYesButton);
		Common.waitForDOMChange();
		if (isClickYesButton) {
			Common.waitForNotDisplayed(eleSkuById);
			waitForListCartItemSizeChange(sizeBeforeRemoved, false);
		}
	}

	public Boolean isSkuByIdAdded(Recipient recipient, String skuName) {
		Common.delay(3);
 		Element eleSkuById = getSkuElementById(recipient.getValue(), skuName); 
 	    if (eleSkuById.isDisplayed()) System.out.println("is Displayed");
 	    else System.out.println("is NOT displayed");
		return eleSkuById.isDisplayed();
	}

	public Boolean isSkuByIdAdded(Recipient recipient, String skuId, String deliveryFrequencyAutoShip) {
		waitForShoppingCartPageDisplays();
		Element eleSkuById = getSkuElementById(recipient.getValue(), skuId);
		ComboBox cbbAutoShipFrequency = new ComboBox(eleSkuById, interfaces.get("cbbAutoShipFrequency"));
		CheckBox radAutoshipAndSave = new CheckBox(eleSkuById, interfaces.get("radAutoshipAndSave"));
		if (!deliveryFrequencyAutoShip.isEmpty() && !(radAutoshipAndSave.isChecked() && cbbAutoShipFrequency.getSelected().equals(deliveryFrequencyAutoShip)))
			return false;
		return eleSkuById.isDisplayed();
	}

	public void waitForCartEmpty() {
		Common.waitForNotDisplayed(formShoppingCart);
	}

	public void waitForShoppingCartPageDisplays() {
		Common.waitForPageLoad();
		Common.waitForDisplayed(panelShoppingCart);
	}

	public boolean isOnShoppingCartPage() {
		return panelShoppingCart.isDisplayed();
	}

	public void selectAutoShipOption(SKU sku, boolean isAutoShip, String deliveryFrequency) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		Common.waitForDisplayed(eleSkuById);
		if (isAutoShip) {
			CheckBox radAutoshipAndSave = new CheckBox(eleSkuById, interfaces.get("radAutoshipAndSave"));
			ComboBox cbbAutoShipFrequency = new ComboBox(eleSkuById, interfaces.get("cbbAutoShipFrequency"));
			Common.check(radAutoshipAndSave);
			waitForLoadingIconInvisible();
			Common.select(cbbAutoShipFrequency, deliveryFrequency);
			Common.waitForSelected(cbbAutoShipFrequency, deliveryFrequency);
			waitForLoadingIconInvisible();
		} else {
			CheckBox radOneTimeOrder = new CheckBox(eleSkuById, interfaces.get("radOneTimeOrder"));
			Common.check(radOneTimeOrder);
			waitForLoadingIconInvisible();
		}
	}

	public void updateQuantityOfSku(SKU sku, int quantity) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		Common.waitForDisplayed(eleSkuById);
		ComboBox cbbSkuQuantity = new ComboBox(eleSkuById, interfaces.get("cbbSkuQuantity"));
		Common.select(cbbSkuQuantity, quantity + "");
		Common.waitForSelected(cbbSkuQuantity, quantity + "");
		waitForLoadingIconInvisible();
	}

	public int getQuantityOfSku(SKU sku) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		Common.waitForDisplayed(eleSkuById);
		ComboBox cbbSkuQuantity = new ComboBox(eleSkuById, interfaces.get("cbbSkuQuantity"));
		return Integer.parseInt(cbbSkuQuantity.getSelected());
	}

	public void addSkuToSomeoneElse(SKU sku, Recipient recipient) {
		selectAddToAndSelectShipTo(sku, recipient);
		waitForLoadingIconInvisible();
		Common.waitForNotDisplayed(btnAddToOnPopup);
		Common.click(btnClosePopupModal, false);
		waitForLoadingIconInvisible();
	}

	public void selectAddToAndSelectShipTo(SKU sku, Recipient recipient) {
		selectSomeoneElseLink(sku);
		Common.waitForDOMChange();
		Common.check(radAddToOnPopup);
		selectShipTo(cbbShipToInModal, recipient.getValue(), txtShipToOnPopup);
		Common.click(btnAddToOnPopup, false);
		Common.waitForDOMChange();
	}

	public void selectMoveToAndSelectShipTo(SKU sku, Recipient recipient) {
		selectSomeoneElseLink(sku);
		selectShipTo(cbbShipToInModal, recipient.getValue(), txtShipToOnPopup);
		Common.check(radMoveToOnPopup);
		Common.click(btnMoveToOnPopup, false);
	}

	public void moveSkuToSomeoneElse(SKU sku, Recipient recipient) {
		selectMoveToAndSelectShipTo(sku, recipient);
		waitForLoadingIconInvisible();
		Common.waitForNotDisplayed(btnMoveToOnPopup);
		Common.click(btnClosePopupModal, false);
		Common.waitForNotDisplayed(btnClosePopupModal);
	}

	public void sendCartTo(Recipient fromCart, Recipient toCart) {
		waitForShoppingCartPageDisplays();
		int numberOfCart = eleCartList.getElements().size();
		for (int i = 1; i <= numberOfCart; i++) {
			Element lnkFormSendCartTo = new Element(interfaces.get("lnkFormSendCartTo"),i);
			Common.click(lnkFormSendCartTo);  
			Common.waitForDOMChange();
			Element cbbValues = new Element(interfaces.get("cbbSendTo"),i);
			Common.waitForDOMChange();
			ComboBox cbbSendTo = new ComboBox(cbbValues.getLocator());
			Common.waitForDisplayed(cbbSendTo);
			System.out.println("selected:" + cbbSendTo.getSelected());
			System.out.println("from:" + fromCart.getValue());
			if (cbbSendTo.getSelected().equals(fromCart.getValue())) {
				List<String> optionList = cbbSendTo.getOptions();
				System.out.println("tocart :" + toCart.getValue());
				if (optionList.contains(toCart.getValue())) {
					Common.select(cbbSendTo, toCart.getValue());
					Common.waitForDOMChange();
				} else {
					TextBox txtRecipientName = new TextBox(interfaces.get("txtRecipientName"),i);
					if (!txtRecipientName.isDisplayed())
						Common.select(cbbSendTo, "Someone Else");
					Common.enter(txtRecipientName, toCart.getValue());
					Button buttonSaveSendTo = new Button(interfaces.get("btnSaveSendTo"),i);
					Common.click(buttonSaveSendTo);
					Common.waitForDOMChange();
				}
				return;
			}
		}
	}

	public String getErrorMessageOnPage() {
		return Common.getText(eleErrorMessageOnPage);
	}

	public void getAddedToCartSKUListInShoppingCartPage(ListSKUs addedToCartSKUsList, Recipient recipient) {
		SKU sku = new SKU();
		sku.setRecipient(recipient);
		sku.setId(this.getAddedSkuIdFromModal());
		sku.setName(this.getAddedSkuNameFromModal());
		addedToCartSKUsList.add(sku);
	}

	public String getCartShipmentTitleByRecipient(Recipient recipient, int shpmnt) {
		Element eleCartShipmentTitleByRecipient = new Element(interfaces.get("eleCartShipmentTitleByRecipient"), recipient.getValue());
		return eleCartShipmentTitleByRecipient.getText().trim();
	}

	// This method is specify for mobile
	public boolean isSkuInSpecialCartAdded(Recipient recipient, String skuName) {
		return false;
	}

	// This method is specify for mobile
	public String getSkuNameInSpecialCartBonus(Recipient recipient) {
		return "";
	}

	public String getAddedSkuNameFromAddedToCart() {
		Common.waitForDisplayed(eleSKUNameInAddedToCart);
		return eleSKUNameInAddedToCart.getText();
	}

	public void clickCreateYourOwnTrioLink() {
		Common.click(lnkCreateYourOwnTrio);
	}

	public void addCreateYourOwnTrioItem(SKU Sku) {
		System.out.println("Button location is " + btnCreateYourOwnTrio.getLocatorAsString());
		Common.click(btnCreateYourOwnTrio);
		Common.waitForDOMChange();
		
//		clickCreateYourOwnTrioLink();
//		Common.waitForDisplayed(eleCreateYourOwnTrioModal);
//
//		ComboBox cbbQuantity = new ComboBox(eleCreateYourOwnTrioModal, interfaces.get("cbbQuantity"));
//		cbbQuantity.select(String.valueOf(Sku.getQuantity()));
//
//		ComboBox cbbSpecialCartBonusSavingsShipTo = new ComboBox(eleCreateYourOwnTrioModal, interfaces.get("cbbSpecialCartBonusSavingsShipTo"));
//		cbbSpecialCartBonusSavingsShipTo.select(Sku.getRecipient().getValue());
//
//		selectRandomItemsInCreateYourOwnTrioModal(3);
//
//		Button btnAddToCart = new Button(eleCreateYourOwnTrioModal, interfaces.get("btnAddToCartInShoppingCart"));
//		btnAddToCart.click();
	}

	public void selectRandomItemsInCreateYourOwnTrioModal(int numberOfItems) {
		Element chbItems = new Element(eleCreateYourOwnTrioModal, interfaces.get("chbItems"));
		int tmp = 0;
		ArrayList<Integer> lstIndex = new ArrayList<Integer>(numberOfItems);
		for (int loop = numberOfItems; loop > 0; loop--) {
			do {
				tmp+=1; 
			} while (lstIndex.contains(tmp));
			lstIndex.add(tmp);
		}

		for (int index : lstIndex) {
			Common.click(chbItems.getElements().get(index));
		}
	}

	public void clickCheckOutWithPayPal() {
		Common.click(btnCheckOutWithPayPal);
		Common.waitForTitleChange();
		Common.waitForPageLoad();
	}

	public boolean isCheckOutWithPayPalButtonDisplayed() {
		return btnCheckOutWithPayPal.isDisplayed();
	}

	public void selectEditSku(SKU sku) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		Link editSku = new Link(eleSkuById, interfaces.get("lnkEditBySkuId"));
		Common.click(editSku);
	}

	public boolean isSubItemsDisplayed(SKU sku) {
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), sku.getId());
		Element eleSusItem;
		boolean tmp = false;
		for (SKU skuSubItem : sku.getSubItems()) {
			eleSusItem = new Element(eleSkuById, interfaces.get("eleSubItem"), skuSubItem.getName());

			if (!eleSusItem.isDisplayed())
				return false;
			tmp = true;
		}
		return tmp;
	}

	public void clickEnterGiftCardOrVoucherLink() {
		Common.waitForPageLoad();
		Common.click(lnkEnterAGiftCardOrVoucher);
		Common.waitForPageLoad();
		Common.waitForDOMChange();
	}

	public String getValueOfItemSubtotal() {
		return eleItemSubtotal.getText();
	}

	public boolean isGiftCardByNameAdded(String recipient, String itemName) {
		Common.waitForDOMChange();
		System.out.println("isGiftCardByNameAdded: " + recipient + " " + itemName);
		Element eleGiftCardByName = new Element(interfaces.get("eleGiftCardByName"), recipient, itemName);
		return eleGiftCardByName.isDisplayed();
	}

	public boolean isRewardCardDisplayed(String rewardCardCode) {
		Element lblReWardCardCode = new Element(interfaces.get("lblReWardCardCode"), rewardCardCode);
		return lblReWardCardCode.isDisplayed();
	}

	public void removeRewardCard(String rewardCardCode) {
		Element btnRemoveReWardCardCode = new Element(interfaces.get("btnRemoveReWardCardCode"), rewardCardCode);
		Common.click(btnRemoveReWardCardCode);
		waitForLoadingIconInvisible();
		Common.waitForPageLoad();
	}

	public String getRemainingCardBalanceInPopup() {
		return Common.getText(eleRemainingCardBalance);
	}

	public String getAmountAppliedInPopup() {
		return Common.getText(eleAmountApplied);
	}

	public boolean isLabelAdditionalSavingsDisplayed() {
		return lblAdditionalSavings.isDisplayed();
	}

	public boolean isWeAreSorryModalDisplayed() {
		return eleWeAreSorryModal.isDisplayed();
	}

	public boolean isTreatYourselfModuleDisplayed() {
		waitForShoppingCartPageDisplays(); 
		return eleTreatYourself.isDisplayed();
	}

	public void addTreatYourself() {
		Common.click(btnAddToCart_TreatYourself);
		waitForLoadingIconInvisible();
	}

	public String getSkuID_TreatYourself() {
		Common.scrollElementToCenterScreen(eleSkuID_TreatYourself);
		return Common.getText(eleSkuID_TreatYourself);
	}

	public boolean isLinkSendToSomeOneElseDisplayed(SKU sku) {
		waitForShoppingCartPageDisplays();
		Element eleSkuById = getSkuElementById(sku.getRecipient().getValue(), Common.getNumberFromText(sku.getId()));
		Common.waitForDisplayed(eleSkuById);
		Common.scrollElementToCenterScreen(eleSkuById);
		// Link lnkSendToSomeoneElse = new Link(eleSkuById,
		// interfaces.get("lnkSendToSomeoneElse"));
		return getLnkSendToSomeoneElse(eleSkuById).isDisplayed();
		// return lnkSendToSomeoneElse.isDisplayed();
	}

	public boolean isMyselfCartSectionAddedAtTheTopOfShoppingCart() {
		Common.waitForPageLoad();
		lnkSendMyselfCart.focus();
		String me = lnkSendMyselfCart.getText();
		System.out.println("myself = " + me);
		return lnkSendMyselfCart.isDisplayed();
	}

	public boolean isCartShipmentDisplayed(Recipient recipient) {
		Element eleCartShipmentByRecipient = new Element(interfaces.get("eleCartShipmentByRecipient"), recipient.getValue());
		return eleCartShipmentByRecipient.isDisplayed();
	}

	public void clickSteakloverRewardOption(String option) {
		Element eleStkLoverRwd = new Element(interfaces.get("eleStkLoverRwd"), option);
		Common.click(eleStkLoverRwd);
		Common.waitForPageLoad();
	}

	public boolean isSteakloverRewardOptionDisplayed(String option) {
		Element eleStkLoverRwdSignIn = new Element(interfaces.get("eleStkLoverRwdSignIn"), option);
		
		return eleStkLoverRwdSignIn.isDisplayed();
	}
	
	public boolean isSLROptionDisplayed(String option) {
		Element eleStkLoverRwdSignIn = new Element(interfaces.get("eleSLRSignIn"),option);
		return eleStkLoverRwdSignIn.isDisplayed();
	}
	
	public boolean isSteakloverGoldRewardRedeemPoints(String option) {
		Element eleStkGoldRwdRedeemPoints = new Element(interfaces.get("eleStkGoldRwdRedeemPoints"), option);
		return eleStkGoldRwdRedeemPoints.isDisplayed(); 
	}

	public boolean isSelectSteakloverRewardsModalDisplayed() {
		return eleSelectYourSteakloverRewardsModal.isDisplayed();
	}

	public String getYourBalancePoint() {
		Common.waitForPageLoad();
		return Common.getText(eleYourBalancePts);
	}

	public void addSelectSteakloverRewardsSKU(Recipient recipient) {
		selectFirstSKUInSelectSteakloverRewards();
		selectRecipientInSeletecSteakloverRewardsModal(recipient);
		clickAddToCartInSelectYourSlrRewardsModal();
		Common.waitForDisplayed(eleSteakloverRewardsModal);
	}

	public void selectFirstSKUInSelectSteakloverRewards() {
		CheckBox chkSKUInSelectStloverRwdModal = new CheckBox(eleFirstSLRReward, interfaces.get("chkSKUInSelectStloverRwdModal"));
		Common.click(chkSKUInSelectStloverRwdModal, false);
	}

	public void selectRecipientInSeletecSteakloverRewardsModal(Recipient recipient) {
		Common.select(cbbShipToInSelectStloverRwdModal, recipient.getValue(), false);
	}

	public void clickAddToCartInSelectYourSlrRewardsModal() {
		Button btnAddToCart = new Button(eleSelectYourSteakloverRewardsModal, interfaces.get("btnAddToCartInShoppingCart"));
		Common.click(btnAddToCart, false);
	}

	public String getSKUNameInSelectSteakloverRewardsModal() {
		Element eleSKUNameInSelectStloverRwdModal = new Element(eleFirstSLRReward, interfaces.get("eleSKUNameInSelectStloverRwdModal"));
		return Common.getRewardSkuDescription(Common.getText(eleSKUNameInSelectStloverRwdModal));  
	}

	public String getSKUPointInSelectSteakloverRewardsModal() {
		Element elePointsInSelectStwloverRwdModal = new Element(eleFirstSLRReward, interfaces.get("elePointsInSelectStwloverRwdModal"));
		return Common.getText(elePointsInSelectStwloverRwdModal).split(" ")[0].trim();
	}

	public boolean isSteakloverRewardsModalDisplayed() {
		return eleSteakloverRewardsModal.isDisplayed();
	}

	public String getAddedSKUNameInSteakloverRewardsModal() {
		return Common.getRewardSkuDescription(Common.getText(eleSKUNameInStloverRwdModal));  
	}

	public String getSKUPointInSteakloverRewardsModal() {
		return Common.getText(eleSKUPointInStloverRwdModal);
	}

	public boolean isTheFreeShippingMessageDisplayed(Recipient recipientName) {
		return getEleFreeShippingMsg(recipientName).isDisplayed() | eleFreeShippingMembership.isDisplayed();
	}

	public Boolean isFreeFoodAddedByMembership(Recipient recipient) {
	//	waitForShoppingCartPageDisplays();
		Common.waitForPageLoad();
		Element eleFreeFood = getFreeItem(recipient, "FREE with your Steaklover Rewards");
		Common.waitForDOMChange();
		return eleFreeFood.isDisplayed();
	} 

	public Boolean isFreeFoodAddedByName(Recipient recipient, String nameOfSKU) {
		waitForShoppingCartPageDisplays();
		Common.waitForPageLoad();
		Element eleFreeFood = getFreeItem(recipient, nameOfSKU); 
		Common.waitForDOMChange();
		return eleFreeFood.isDisplayed();
	} 

	public String getMessageInSteakloveRewardsSection() {
		return Common.getText(lblMessageInStloverRwd);
	}

	protected Element getEleTheQtyOfFreeFood(SKU sku) {
		return new Element(interfaces.get("eleTheQtyOfFreeFood"),sku.getRecipient().getValue(),sku.getName());
	}

	public String getTheQuantityOfFreeFood(SKU sku) {
		return Common.getText(getEleTheQtyOfFreeFood(sku)).replace("Qty: ", "");
	}

	public Double getPriceOfFreeFood(SKU sku) {
		return Double.valueOf(Common.getText(getEleThePriceOfFreeFood(sku)).replace("$", ""));
	}

	public boolean isRemoveLinkInFreeFoodDisplayed(Recipient recipient, String skuName) {
		Element eleFreeFood = getSkuElementByName(recipient, skuName);
 		Link lnkRemoveBySkuId = getLnkRemoveBySkuId(eleFreeFood);
		return lnkRemoveBySkuId.isDisplayed();
	}

	public boolean isQuantityComboBoxDisplayed(Recipient recipient, String skuName) {
		Element eleFreeFood = getSkuElementByName(recipient, skuName);
		ComboBox cbbSkuQuantity = new ComboBox(eleFreeFood, interfaces.get("cbbSkuQuantity"));
		return cbbSkuQuantity.isDisplayed();
	}

	public String getAvailablePoints() {
		return Common.getText(eleAvailablePoints);
	}

	public boolean isFreeFoodDisableAfterSelectedOneSKU() {
		Element eleSLRReward = new Element(interfaces.get("eleSLRRewards"));
		int size = eleSLRReward.getElements().size();
		int count = 0;
		for (int i = 1; i < size; i++) {
			if (eleSLRReward.getElements().get(i).getAttribute("style").contains("opacity")) {
				count++;
			}
		}
		if (count == 0)
			return false;
		return true;
	}

	public boolean isChooseYourRewardsHighlighted() {
		return eleErrorHighlighted.isDisplayed();
	}

	public void removeFreeFood(SKU sku) {
		waitForShoppingCartPageDisplays();
		Element itemInCart = getSkuElementByName(sku.getRecipient(), sku.getName());
		removeSkuByElement(itemInCart, true);
	}

	public boolean isSignInLinkDisplayed() {
		Common.waitForPageLoad();
 		return lnkSignInOnGoldMembership.isDisplayed();
	}


}