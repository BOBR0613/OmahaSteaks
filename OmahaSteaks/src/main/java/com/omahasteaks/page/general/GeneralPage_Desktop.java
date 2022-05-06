package com.omahasteaks.page.general;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.logigear.locator.Interface;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.utils.common.AS400DB;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class GeneralPage_Desktop implements GeneralPage {
	protected Interface interfaces = new Interface(this.getClass(), Constants.LOCATOR_PATH);
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element ele_FirstSKUForm = new Element(interfaces.get("ele_FirstSKUForm"));
	protected Element ele_FirstSKUName = new Element(interfaces.get("ele_FirstSKUName"));
	protected Element ele_FirstSKUiD = new Element(interfaces.get("ele_FirstSKUiD"));
	protected Element eleSkuPrice = new Element(interfaces.get("eleSkuPrice"));
	protected Element eleLoadingIcon = new Element(interfaces.get("eleLoadingIcon"));
	protected Element eleNavTabs = new Element(interfaces.get("eleNavTabs"));
	protected Element elePopModal = new Element(interfaces.get("elePopModal"));
	protected Element eleExclusiveOfferPopup = new Element(interfaces.get("eleExclusiveOfferPopup"));
	protected Element eleExclusiveOfferTitle = new Element(interfaces.get("eleExclusiveOfferTitle"));
	protected Element eleExclusiveOfferIID = new Element(interfaces.get("eleExclusiveOfferIID"));
	protected Element eleExclusiveOfferPrice = new Element(interfaces.get("eleExclusiveOfferPrice"));
	protected Element eleExclusiveOfferAddToCart = new Element(interfaces.get("eleExclusiveOfferAddToCart"));
	protected Element eleExclusiveOfferNoThanks = new Element(interfaces.get("eleExclusiveOfferNoThanks"));
	protected Element eleCartNumber = new Element(interfaces.get("eleCartNumber"));

	protected Element eleAddToCartPopup = new Element(interfaces.get("eleAddToCartPopup"));
	protected Element eleAddToCartShipto = new Element(interfaces.get("eleAddToCartShipto"));
	protected Element eleAddToCartItemTitle = new Element(interfaces.get("eleAddToCartItemTitle"));
	protected Element eleAddToCartItemList = new Element(interfaces.get("eleAddToCartItemList"));
	protected Element eleAddToCartItemIID = new Element(interfaces.get("eleAddToCartItemIID"));
	protected Element eleAddToCartItemSuffix = new Element(interfaces.get("eleAddToCartItemSuffix"));
	protected Element eleAddToCartItemPrice = new Element(interfaces.get("eleAddToCartItemPrice"));
	protected Element eleAddToCartItemQty = new Element(interfaces.get("eleAddToCartItemQty"));
	protected Element eleAddToCartContinueShopping = new Element(interfaces.get("eleAddToCartContinueShopping"));
	protected Element eleAddToCartViewCart = new Element(interfaces.get("eleAddToCartViewCart"));
	protected Element eleAddToCartCheckout = new Element(interfaces.get("eleAddToCartCheckout"));
	protected Element eleAddToCartUnlock = new Element(interfaces.get("eleAddToCartUnlock"));
	protected Element eleAddToCartUnlockNoThanks = new Element(interfaces.get("eleAddToCartUnlockNoThanks"));
	protected Element eleAddToCartUnlockAddItem = new Element(interfaces.get("eleAddToCartUnlockAddItem"));
	protected Element eleAddToCartUnlockItemTitle = new Element(interfaces.get("eleAddToCartUnlockItemTitle"));
	protected Element eleAddToCartUnlockItemPrice = new Element(interfaces.get("eleAddToCartUnlockItemPrice"));
	
	
	protected Element eleOnFreeShipPackages = new Element(interfaces.get("eleOnFreeShipPackages"));
	protected Element btnProductNoThanksPopup = new Element(interfaces.get("btnNoThanksOnPopup"));
	protected Element formSearch = new Element(interfaces.get("formSearch"));
	protected Element eleEmptyRecipientWarning = new Element(interfaces.get("eleEmptyRecipientWarning"));
	protected Element eleMessagePopup = new Element(interfaces.get("eleMessagePopup"));
	protected Element eleMyAccountInTopModal = new Element(interfaces.get("eleMyAccountInTopModal"));
	protected Element btnCloseAddedToCart = new Element(interfaces.get("btnCloseAddedToCart"));
	protected Element eleFirstSizeAndCount = new Element(interfaces.get("eleFirstSizeAndCount"));
	protected Element eleFirstCount = new Element(interfaces.get("eleFirstCount"));
	protected Button btnContinueShopping = new Button(interfaces.get("btnContinueShopping"));
	protected Button btnAddToCartOnPopup = new Button(interfaces.get("btnAddToCartOnPopup"));
	protected Button btnNoThanksOnPopup = new Button(interfaces.get("btnNoThanksOnPopup"));
	protected Button btnCheckOut = new Button(interfaces.get("btnCheckOut"));
	protected Button btnSearch = new Button(interfaces.get("btnSearch"));
	protected Button btnViewMyCart = new Button(interfaces.get("btnViewMyCart"));
	protected Button btnCloseAddedToCartPage_mb = new Button(interfaces.get("btnCloseAddedToCartPage_mb"));
	protected Button btnCloseModal = new Button(interfaces.get("btnCloseModal"));
	protected Button btnContinueInModal = new Button(interfaces.get("btnContinueInModal")); 
	protected TextBox txtSearch = new TextBox(interfaces.get("txtSearch"));
	protected Link lnkMyCart = new Link(interfaces.get("lnkMyCart"));
	protected Link lnkSignIn = new Link(interfaces.get("lnkSignIn"));
	protected Link lnkSignOut = new Link(interfaces.get("lnkSignOut"));
	protected Link lnkStores = new Link(interfaces.get("lnkStores"));
	protected Element eleWineRestrictionLabel = new Element(interfaces.get("eleWineRestrictionLabel"));
	protected Element eleRemainingCardBalance = new Element(interfaces.get("eleRemainingCardBalance"));
	protected Element eleAmountApplied = new Element(interfaces.get("eleAmountApplied"));
	protected Link lnkLogo = new Link(interfaces.get("lnkLogo"));
	protected TextBox txtNumberInVoucherModal = new TextBox(interfaces.get("txtNumberInVoucherModal"));
	protected Button btnSubmitInVoucherModal = new Button(interfaces.get("btnSubmitInVoucherModal"));
	protected Element JFYModal = new Element(interfaces.get("JFYModal"));
	protected Element eleFirstSKUOutOfStock = new Element(interfaces.get("eleFirstSKUOutOfStock"));
	protected Element eleWinFreeSteaksPage = new Element(interfaces.get("eleWinFreeSteaksPage"));
	private String gcid="";

	protected Element getCbbSelectSizeAndCount(Element eleItem) {
		return new Element(eleItem, interfaces.get("cbbSelectSizeAndCount"));
	}

	protected ComboBox getCbbSelectShipTo(Element eleSku) {
		return new ComboBox(eleSku, interfaces.get("cbbSelectShipTo"));
	}

	protected TextBox getTxtShipTo(Element eleSku) {
		return new TextBox(eleSku, interfaces.get("txtShipTo"));
	}

	protected Button getBtnAddToCart(Element eleSku) {
		return new Button(eleSku, interfaces.get("btnAddToCart"));
	}

	protected Element getElePrice(Element eleSku) {
		return new Element(eleSku, interfaces.get("elePrice"));
	}

	protected Element getEleSku(String skuName) {
		return new Element(interfaces.get("eleSku"), skuName);
	}

	public String getGCID(String gctype) {
	  if (this.gcid.equalsIgnoreCase("")) this.gcid = AS400DB.getGCID(gctype);
	  return this.gcid;	
	}
	
	
	// ================================================================================
	// Support Methods
	// ================================================================================
	private void selectSizeAndCount(Element eleItem, String itemSize, int itemCount) {
		Element cbbSelectSizeAndCount = getCbbSelectSizeAndCount(eleItem);
		// Handle auto open dropdownlist when focus on element on MAC
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
			Common.click(cbbSelectSizeAndCount, true, false);
		else {
			Common.click(cbbSelectSizeAndCount);
		}
		if (itemSize != null && itemSize.trim().length() > 0) {
			Element eleSize = new Element(eleItem, interfaces.get("eleSizeItem"), itemSize);
			Common.click(eleSize);
			Common.waitForNotDisplayed(eleSize);
		}
		Element eleCount = new Element(eleItem, interfaces.get("eleCountItem"), itemCount);
		Common.click(eleCount);
		Common.waitForNotDisplayed(eleCount);
	}
	
	
	public void viewMyCart() {
		Common.tryClickByJs(btnViewMyCart);
		Common.waitForDOMChange();
	}

	
	protected void selectShipTo(ComboBox cbbSelectShipTo, String recipient, TextBox txtShipTo) {
		Common.waitForDOMChange();
		Common.waitForDisplayed(cbbSelectShipTo);
		String selected = "";

		// There is no default option selected
		try {
			selected = cbbSelectShipTo.getSelected();
		} catch (Exception e) {
			selected = "";
		}
		if (!selected.equals("Ship To " + recipient) && !selected.equals("Send to " + recipient) ) {
			List<String> optionList = cbbSelectShipTo.getOptions();
			Common.waitForDOMChange();
			if (optionList.contains(recipient)) {
				Common.select(cbbSelectShipTo, recipient.trim());
			} else {
				if (!txtShipTo.isDisplayed()) {
					Common.select(cbbSelectShipTo, "Someone Else");
					Common.waitForDOMChange();
				}
				if (!recipient.equalsIgnoreCase(Recipient.EMPTY.getValue())) {
				  Common.enter(txtShipTo, recipient.trim());
				}
			}
		}
	}

	protected void selectShipTo(ComboBox cbbSelectShipTo, String recipient) {
		Common.waitForDisplayed(cbbSelectShipTo);
		String selected = "";

		// There is no default option selected
		try {
			selected = cbbSelectShipTo.getSelected();
		} catch (Exception e) {
			selected = "";
		}
		if (!selected.equals("Ship To " + recipient)) {
		  if (!recipient.equalsIgnoreCase(Recipient.EMPTY.getValue())) 
			Common.select(cbbSelectShipTo, recipient);
		}
	}

	protected void selectShipToInModal(ComboBox cbbSelectShipTo, String recipient, TextBox txtShipTo) {
		Common.waitForDisplayed(cbbSelectShipTo);
		String selected = cbbSelectShipTo.getSelected();
		if (!selected.equals("Ship To " + recipient)) {
			List<String> optionList = cbbSelectShipTo.getOptions();
			if (optionList.contains(recipient)) {
				Common.select(cbbSelectShipTo, recipient, false);
			} else {
				if (!txtShipTo.isDisplayed())
					Common.select(cbbSelectShipTo, "Someone Else", false);
				if (!recipient.equalsIgnoreCase(Recipient.EMPTY.getValue())) 
				  Common.enter(txtShipTo, recipient);
			}
		}
	}

	
	// ================================================================================
	// EXCLUSIVE OFFER MODAL
	// ================================================================================
	public void selectExclusiveOffer(boolean doAddExclusiveOffer) {
		Common.waitForDisplayed(elePopModal);
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		
		while (!eleExclusiveOfferNoThanks.isDisplayed() && endTime < timeout) {
			if (eleAddToCartCheckout.isDisplayed())
				return;
			DriverUtils.delay(1);
			endTime = System.currentTimeMillis() - startTime;
		}

        Common.waitForDOMChange();
		if (doAddExclusiveOffer) { 
		  eleExclusiveOfferAddToCart.clickByJs();
		}
		else {
			eleExclusiveOfferNoThanks.clickByJs(); 
		}
		
        Common.waitForDOMChange();
		Common.waitForNotDisplayed(eleExclusiveOfferNoThanks);
		Common.waitForDOMChange();
	}
	public String getExclusiveOfferIID() {
		return eleExclusiveOfferIID.getValue();
	}
	public String getExclusiveOfferTitle() {
		return eleExclusiveOfferTitle.getText();
	}
	public String getExclusiveOfferPrice() {
		return eleExclusiveOfferPrice.getText();
	}
	
	// ================================================================================
	// ADD TO CART MODAL
	// ================================================================================
	public void addSpecialDealUnlocked(boolean addSpecialDealUnlocked) {
		Common.waitForDisplayed(eleAddToCartUnlock);
		if (!eleAddToCartUnlock.isDisplayed()) return;
		Common.waitForDOMChange();
		if (addSpecialDealUnlocked) {
			if (eleAddToCartUnlock.isDisplayed()) {
				Common.focus(eleAddToCartUnlockAddItem);
				eleAddToCartUnlockAddItem.click(); 
			}
		} else {
			if (eleAddToCartUnlockNoThanks.isDisplayed()) {
				Common.focus(eleAddToCartUnlockNoThanks);
				Common.click(eleAddToCartUnlockNoThanks);
			}
		}
	}

	public void getAddedToCartSKUList(ListSKUs addedToCartSKUsList) {
		String recipientName = "";
		//selectExclusiveOffer(false);
		Common.waitForDOMChange();
 		 
		Common.waitForDisplayed(eleAddToCartShipto);  
		recipientName = eleAddToCartShipto.getText(); 		
		if (recipientName.contains(":")) recipientName = recipientName.split(":")[1];
		else if (recipientName.contains("Ship To")) recipientName = recipientName.split("Ship To")[1];		Common.waitForDOMChange();
		Common.waitForDisplayed(eleAddToCartItemList);
		int size = eleAddToCartItemList.getElements().size(); 
		if (size > 0) {
			for (Integer index = 1; index <= size; index++) {
				SKU sku = new SKU();
				Element eid = new Element(interfaces.get("eleAddToCartItemIID"), index,index,index); 
				sku.setId(eid.getText());
				//sku.setQuantity(1); 
				sku.setRecipient(recipientName.trim());
				Element eTitle = new Element(interfaces.get("eleAddToCartItemTitle"), index,index,index);
				sku.setName(eTitle.getText()); 
				Element ePrice = new Element(interfaces.get("eleAddToCartItemPrice"), index,index,index); 
				if (ePrice.getText().equalsIgnoreCase("")) ePrice = new Element(interfaces.get("eleAddToCartItemPrice2"), index,index); 
  			    sku.setPrice(Common.getPriceFromText(ePrice.getText()));					
				addedToCartSKUsList.add(sku);
			}
		}
	}

	public boolean isAddedToCartModalDisplayed() {
		eleAddToCartPopup.waitForDisplay(Constants.SLEEP_TIME);
		return eleAddToCartPopup.isDisplayed();
	}

	
	
	// ================================================================================
	// Main Methods
	// ================================================================================
	public void waitForLoadingIconInvisible() {
		waitForLoadingIconInvisible(Constants.SLEEP_TIME);
	}

	public void waitForLoadingIconInvisible(int timeout) {
		Common.waitForPageLoad();
		if (eleLoadingIcon.isDisplayed(Constants.SLEEP_TIME)) {
			try {
				Common.scrollElementToCenterScreen(eleLoadingIcon);
				eleLoadingIcon.waitForInvisibility(timeout);
			} catch (Exception e) {
			}
		}
	}


	public void goToMyCart() {
		if (Common.getTitlePage().contains("Shopping-Cart"))
			return;
		Common.waitForElementExistedInDOM(lnkMyCart);
		Common.scrollToTop();
		Common.click(lnkMyCart);
		Common.waitForTitleChange();
	}

	public void goToHomePage() {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		Common.waitForPageLoad();
		while (!lnkLogo.isDisplayed() && endTime < timeout) {
			Common.scrollToTop();
			endTime = System.currentTimeMillis() - startTime;
		}
		Common.click(lnkLogo);
		Common.waitForPageLoad();
	}

	public void goToMyAccountPage() {
		Common.modalDialog.closeUnknownModalDialog();
		if (!Common.getTitlePage().contains("My Account")) {
			Common.waitForClickable(eleMyAccountInTopModal);
			Common.click(eleMyAccountInTopModal, false);
			Common.waitForTitleChange();
		}
	}

	public Element getCategoryElement(String categoryPath) {
		String[] categoryItems = categoryPath.split("/"); 
		// Get main Category 
		Element linkMainCategory = new Element(interfaces.get("linkMainCategory"), categoryItems[0]);
		Common.waitForClickable(linkMainCategory);
		Common.modalDialog.closeUnknownModalDialog();
		Common.scrollElementToCenterScreen(linkMainCategory);
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD) || RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_TABLET)) {
			Common.click(linkMainCategory);
		} else {
			if (categoryItems.length == 1)
				return linkMainCategory;
			DriverUtils.moveToControl(linkMainCategory);
		}
		if (categoryItems.length == 1)
			return linkMainCategory;
		// Find sub Category
		Element mainCategory = new Element(interfaces.get("mainCategory"), categoryItems[0]);
		Element subCategory = new Element(mainCategory, interfaces.get("subCategory"), categoryItems[1]);
		Common.waitForDisplayed(subCategory);
		return subCategory;
	}

	public void goToCategoryPage(String categoryPath) {
		Element ele = getCategoryElement(categoryPath);
		Common.modalDialog.closeUnknownModalDialog();
		ele.waitForVisibility();
		Common.click(ele);
		Common.waitForTitleChange();
	}

	public void addItemToCart(Item item) {
		addItemToCart(item, false);
	}

	public void addItemToCart(Item item, boolean doAddExclusiveOffer) {
		Element eleItem = getEleSku(item.getName());
		System.out.println(eleItem);
		Common.waitForDisplayed(eleItem);
		selectSizeAndCount(eleItem, item.getSize(), item.getCount());
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(eleItem);
		TextBox txtShipTo = getTxtShipTo(eleItem);
		selectShipTo(cbbSelectShipTo, item.getRecipient().getValue(), txtShipTo);
		Button btnAddToCart = getBtnAddToCart(eleItem);
		Common.click(btnAddToCart);
		selectExclusiveOffer(doAddExclusiveOffer);
	}

	public void continueShopping() {
		Common.waitForDOMChange();
		if (eleAddToCartContinueShopping.isDisplayed()) {
				eleAddToCartContinueShopping.scrollToView();
				Common.clickByAction(eleAddToCartContinueShopping);
				Common.waitForDOMChange(); 
		}
		//Common.waitForNotDisplayed(eleAddToCartContinueShopping);
		//Common.waitForNotDisplayed(btnCloseAddedToCartPage_mb);
	}

	
	public void checkOut() {
		Common.waitForDOMChange();
		Common.waitForDisplayed(eleAddToCartPopup);
		Common.scrollElementToCenterScreen(eleAddToCartPopup);
		Common.waitForDOMChange();
		if (eleAddToCartCheckout.isDisplayed()) {
		  Common.scrollElementToCenterScreen(eleAddToCartCheckout);
	      Common.tryClickByJs(eleAddToCartCheckout);
		}
		Common.waitForDOMChange();
		Common.modalDialog.closeUnknownModalDialog();
		Common.waitForPageLoad();
 	}
	

	public void goToSignInPage() { 
		Common.waitForClickable(lnkSignIn);
		Common.click(lnkSignIn); 
		if (!Common.getCurrentUrl().equals(Constants.getRunningURL() + Constants.OMAHA_URL_MYACCOUNTPAGE)) {
			Common.click(lnkSignIn, ActionType.JS);
			Common.waitForPageLoad();
		}
		Common.waitForPageLoad();
	}

	public void search(String searchKey) {
		// Enter searchKey
	    Common.focus(txtSearch);
		//txtSearch.enter(searchKey);
		Common.enter(txtSearch, searchKey);
		// Click Search button
		Common.click(btnSearch);
		Common.waitForDOMChange();
		Common.modalDialog.closeSavorDialog();
		Common.waitForTitleChange();
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD)) {
			if (txtSearch.getAttribute("value").contains(searchKey)) {
				Common.submitForm(formSearch);
				Common.waitForTitleChange();
			}
		}
	}

	public int getCartNumber() {
		int number = 0;
		for (WebElement ele : eleCartNumber.getElements()) {
			if (!ele.getText().equals("")) {
				number = Integer.parseInt(ele.getText());
			}
		}
		return number;
	}

	public void closeAddedToCartModal() {
		Common.click(btnCloseModal, false);
		Common.waitForDOMChange();
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD)) {
			if (btnCloseModal.isDisplayed()) {
				{
					Common.click(btnCloseModal, ActionType.JS);
				}
			}
		}
		Common.waitForNotDisplayed(btnCloseModal);
		Common.waitForDOMChange();
	}

	public void closeModal() {
		if (btnCloseModal.isDisplayed(Constants.SHORT_TIME / 6)) {
			Common.click(btnCloseModal, false);
		}
		Common.waitForDOMChange();
		if (elePopModal.isDisplayed()) {
			Common.modalDialog.closeModalDialog();
		}
	}

	public void continueInModal() {
		if (btnContinueInModal.isDisplayed(Constants.SHORT_TIME / 6)) {
			Common.click(btnContinueInModal, false);
		}
		waitForLoadingIconInvisible();
	}


	public void selectFreeShipPackages() {
		Common.click(eleOnFreeShipPackages);
		Common.waitForTitleChange();
	}

	public String getRecipientWarningMessage() {
		Common.waitForDOMChange();
		return Common.getText(eleEmptyRecipientWarning);
	}

	public String getMessageOnPopupAndClose() {
//		Common.waitForDisplayed(eleMessagePopup);
		Common.waitForDOMChange();
		String message = "";
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		if (eleMessagePopup.isDisplayed()) {
			while ((message.equals("")) && endTime < timeout) {
				message = eleMessagePopup.getText();
				endTime = System.currentTimeMillis() - startTime;
			}
		}
		Common.modalDialog.closeModalDialog();
		return message;
	}
	
	public void goToDepartmentPage(String categoryPath) {
		String[] categoryItems = categoryPath.split("/");
		goToCategoryPage(categoryItems[0]);
	}

	public void goToCategoryPageByLeftMenu(String categoryPath) {
		goToDepartmentPage(categoryPath);
		String[] categoryItems = categoryPath.split("/");
		// Click Category in Sub Category Section
		Element eleCategoryWithSubCategory = new Element(interfaces.get("eleCategoryWithSubCategory"), categoryItems[1], categoryItems[2]);
		Common.click(eleCategoryWithSubCategory);
		Common.waitForTitleChange();
	}

	public void clickSideMenuItemLink(SideMenuItem sideMenuItem) {
		Element lnkSideMenuItem = new Element(interfaces.get("lnkSideMenuItem"), sideMenuItem.getValue());
		Common.click(lnkSideMenuItem);
		Common.waitForTitleChange();
		
	}

	public void selectFirstSizeAndCount() {
		eleFirstSizeAndCount.waitForDisplay(Constants.SLEEP_TIME);
		// Select Size if existed
		if (eleFirstSizeAndCount.isDisplayed()) {
			Common.click(eleFirstSizeAndCount, ActionType.BUILT_IN, false, true);
			// Waiting for animation effect
			DriverUtils.delay(Constants.SLEEP_TIME * 3);
		}
		// Select Count if existed
		if (eleFirstCount.isDisplayed()) {
			Common.click(eleFirstCount, ActionType.BUILT_IN, false, true);
			// Waiting for animation effect
			DriverUtils.delay(Constants.SLEEP_TIME);
		}
	}
	
	public String getFirstItemName () {
		Common.waitForDisplayed(ele_FirstSKUForm);
		Common.scrollElementToCenterScreen(ele_FirstSKUForm);
		Common.waitForDOMChange();
		Common.modalDialog.closeUnknownModalDialog();
		Common.waitForDOMChange();
		return ele_FirstSKUName.getText().trim();
	}

	public void addFirstSKUToCart(SKU sku) {
		System.out.println("locator of form :" + ele_FirstSKUForm.getLocatorAsString());
		Common.waitForDisplayed(ele_FirstSKUForm);
		Common.scrollElementToCenterScreen(ele_FirstSKUForm);
		Common.waitForDOMChange(); 
		Common.modalDialog.closeSavorDialog();
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUForm);
		Button btnAddToCart = getBtnAddToCart(ele_FirstSKUForm);
		TextBox txtShipTo = getTxtShipTo(ele_FirstSKUForm);
		Common.waitForDOMChange(3);
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD)) {
			cbbSelectShipTo = new ComboBox(interfaces.get("cbbSelectShipTo_iPad"));
			// btnAddToCart = new Button(interfaces.get("btnAddToCart_iPad"));
			txtShipTo = new TextBox(interfaces.get("txtShipTo_iPad"));
		}		 
		Common.waitForDOMChange();
		if (!btnAddToCart.isDisplayed())
			throw new AssertionError("The \"Add To Cart\" button does not display");
		Common.scrollElementToCenterScreen(cbbSelectShipTo);
		selectShipTo(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
		Common.click(btnAddToCart);
		
		Common.delay(Constants.SLEEP_TIME);
		// Select Size & Count if it's required!
		Element eleSelectSizeAndCountWhenError_mb = new Element(interfaces.get("eleSelectSizeAndCountWhenError_mb"));
		if (eleSelectSizeAndCountWhenError_mb.isDisplayed(Constants.SLEEP_TIME * 2)) {
			Common.click(eleSelectSizeAndCountWhenError_mb);
		}
		if (eleFirstSizeAndCount.isDisplayed(Constants.SLEEP_TIME * 2)) {
			selectFirstSizeAndCount();
			/*if (Constants.SITE.contains("wps") && DriverUtils.getDriverType().contains(Constants.BROWSER_IE)) {
				Common.click(btnAddToCart);
			}*/
		}
		Common.waitForDOMChange(3);
	}

	public boolean isMyAccountInLinkExisted() {
		return eleMyAccountInTopModal.isDisplayed();
	}

	public void signOut() {
		if (!lnkSignOut.isDisplayed())
			goToMyAccountPage();
		Common.click(lnkSignOut);
		Common.waitForPageLoad();
		Common.waitForDOMChange();
	}

	public String getWineRestrictionLabel() {
	  String wine_restrictions = Common.getText(eleWineRestrictionLabel);
		return wine_restrictions;
	}

	public void enterVoucherAndClickSubmitButtonInVoucherModal(String value) {
		Common.tryEnter(txtNumberInVoucherModal, value);
		Common.tryClickByJs(btnSubmitInVoucherModal);
		Common.waitForDOMChange();
	}

	public String getErrorMessageInVoucherModal() {
		Element eleErrorMessageInVoucherModal = new Element(interfaces.get("eleErrorMessageInVoucherModal"));
		Common.waitForDisplayed(eleErrorMessageInVoucherModal);
		return Common.getText(eleErrorMessageInVoucherModal);
	}

	public void goToGiftCertificatesPage() { 
		DriverUtils.navigate(Constants.COLLECTTION_CENTER_URL);
		Common.waitForPageLoad();
	}

	public boolean isFirstSKUDisplayedOutOfStock() {
		return eleFirstSKUOutOfStock.isDisplayed();
	}

	public void goToWinFreeSteaksPage() {
		Common.click(eleWinFreeSteaksPage);
		Common.waitForPageLoad();
	}

	public void goToStoresPage() {
		Common.click(lnkStores);
		Common.delay(2);
		List<String> lstWindow = DriverUtils.getWindowHandles();
		String storesWindow = lstWindow.get(1);
		DriverUtils.switchTo(storesWindow);

	}

	public void closeStoresPage() { 
		List<String> lstWindow = DriverUtils.getWindowHandles();
		String storesWindow = lstWindow.get(1);
		if (!storesWindow.equalsIgnoreCase("")) {
			DriverUtils.switchTo(storesWindow);
			if (DriverUtils.getWebDriver().getTitle().contains("Omaha Steaks Retail Stores")) {
			  DriverUtils.getWebDriver().close();
			}
		}
		storesWindow = lstWindow.get(0);
		DriverUtils.switchTo(storesWindow);
	}

	public void selectOnSelectPackages() {
		// TODO Auto-generated method stub
		
	}
}
