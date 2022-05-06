package com.omahasteaks.page.general;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class GeneralPage_Mobile extends GeneralPage_Desktop implements GeneralPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element eleOpenMenu_mb = new Element(interfaces.get("eleOpenMenu_mb"));
	protected Element eleNavCont_mb = new Element(interfaces.get("eleNavCont_mb"));
	protected Element formSearch_mb = new Element(interfaces.get("formSearch_mb"));
	protected Link lnkMyCart_mb = new Link(interfaces.get("lnkMyCart_mb"));
	protected Link lnkSignIn_mb = new Link(interfaces.get("lnkSignIn_mb"));
	protected Link lnkSideMenuItem_mb = new Link(interfaces.get("lnkSideMenuItem_mb"));
	protected Element eleCartNumber_mb = new Element(interfaces.get("eleCartNumber_mb"));

	public GeneralPage_Mobile() {
		super();
		//eleAddedToCartModal = new Element(interfaces.get("eleAddedToCartModal_mb"));
		//eleAddedToCartShipToLabel = new Element(interfaces.get("eleAddedToCartShipToLabel_mb"));
		//eleSpecialDealUnlockedModal = new Element(interfaces.get("eleSpecialDealUnlockedModal_mb"));
		//eleAddedToCartSKUNameList = new Element(interfaces.get("eleAddedToCartSKUNameList_mb"));
		//eleAddedToCartSKU_iidList = new Element(interfaces.get("eleAddedToCartSKU_iidList_mb"));
		//eleAddedToCartSKU_suffixList = new Element(interfaces.get("eleAddedToCartSKU_suffixList_mb"));
		//btnSpecialDealUnlocked_AddToCart = new Button(eleSpecialDealUnlockedModal, interfaces.get("btnSpecialDealUnlocked_AddToCart_mb"));
		//eleAddedToCartSKUList = new Element(interfaces.get("eleAddedToCartSKUList_mb"));
		eleMyAccountInTopModal = new Element(interfaces.get("eleMyAccountInTopModal_mb"));
	}
	// ================================================================================
	// Support Methods
	// ================================================================================

	private void openMenu() {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		Common.scrollToTop();
		if (!eleOpenMenu_mb.isDisplayed()) {
			goToHomePage();
		}
		while (!txtSearch.isDisplayed() && endTime < timeout) {
			Common.scrollToTop();
			Common.click(eleOpenMenu_mb);
			DriverUtils.delay(1);
			endTime = System.currentTimeMillis() - startTime;
		}
	}

	// ================================================================================
	// Main Methods
	// ================================================================================
	public String getRunningMode() {
		return Constants.PLATFORM_MOBILE;
	}

	@Override
	public Element getCategoryElement(String categoryPath) {
		String[] categoryItems = categoryPath.split("/");
		Common.click(eleOpenMenu_mb);
		// Find sub Category
		Logger.info("Find sub Category");
		Element subCategory_mb = new Element(interfaces.get("subCategory_mb"), categoryItems[1]);
		Common.waitForDisplayed(subCategory_mb);
		return subCategory_mb;
	}

	public void goToCategoryPage(String categoryPath) {
		Element eleCategory = getCategoryElement(categoryPath);
		Common.click(eleCategory, false);
	}

	public void goToSignInPage() {
		Common.waitForElementExistedInDOM(lnkSignIn_mb);
		if (Common.getTitlePage().contains(Constants.CHECKOUT_SHOPPING_CART_PAGE_TITLE) || Common.getCurrentUrl().contains(Constants.URL_SHOPPING_CART)) {
			Common.click(lnkSignIn_mb);
			return;
		}
		Common.scrollToTop();
		Common.click(lnkSignIn_mb);
		Common.waitForTitleChange();
		Common.modalDialog.closeUnknownModalDialog();
	}

	@Override
	public void search(String searchKey) {
		openMenu();
		Common.enter(txtSearch, searchKey);
		// txtSearch.getElement().sendKeys(Keys.ENTER);
		Common.submitForm(formSearch_mb);
		Common.waitForTitleChange();
	}

	@Override
	public void goToMyCart() {
		if (Common.getTitlePage().contains("Shopping-Cart"))
			return;
		Common.waitForElementExistedInDOM(lnkMyCart_mb);
		if (lnkMyCart_mb.getElements().size() == 1) {
			Common.click(lnkMyCart_mb);
			return;
		}
		Common.scrollToTop();
		Common.click(lnkMyCart_mb.getElements().get(1));
		Common.waitForTitleChange();
	}

	@Override
	public void clickSideMenuItemLink(SideMenuItem sideMenuItem) {
		Element lnkSideMenuItem_mb = new Element(interfaces.get("lnkSideMenuItem_mb"), sideMenuItem.getValue());
		openMenu();
		Common.click(lnkSideMenuItem_mb);
		Common.waitForTitleChange();
		Common.scrollDownToHandlePopupError();
	}

	@Override
	public void selectOnSelectPackages() {
		openMenu();
	//	Common.click(eleOnSelectPackages, false);
	}

	@Override
	public void continueShopping() {
		Common.waitForDisplayed(btnCheckOut);
		Common.click(btnCloseAddedToCart);
	}

	@Override
	public void goToDepartmentPage(String categoryPath) {
		openMenu();
		String[] categoryItems = categoryPath.split("/");
		Element mainCategory = new Element(interfaces.get("mainCategory_mb"), categoryItems[0]);
		Common.click(mainCategory);
	}

	@Override
	public void goToCategoryPageByLeftMenu(String categoryPath) {
		goToDepartmentPage(categoryPath);
		String[] categoryItems = categoryPath.split("/");
		// Click Category in Sub Category Section
		Element eleCategoryWithSubCategory_mb = new Element(interfaces.get("eleCategoryWithSubCategory_mb"), categoryItems[1], categoryItems[2]);
		Common.click(eleCategoryWithSubCategory_mb);
		Common.waitForTitleChange();
	}

	@Override
	public void addFirstSKUToCart(SKU sku) {
		Common.waitForDisplayed(ele_FirstSKUForm);
		Common.scrollElementToCenterScreen(ele_FirstSKUForm);
		Common.waitForDOMChange();
		Common.modalDialog.closeUnknownModalDialog();
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUForm);
		Button btnAddToCart = getBtnAddToCart(ele_FirstSKUForm);
		TextBox txtShipTo = getTxtShipTo(ele_FirstSKUForm);
		Common.waitForDisplayed(cbbSelectShipTo);
		if (!btnAddToCart.isDisplayed())
			throw new AssertionError("The \"Add To Cart\" button does not display");
		Common.scrollElementToCenterScreen(cbbSelectShipTo);
		selectShipTo(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
		btnAddToCart.click();
		// Select Size & Count if it's required!
		Element eleSelectSizeAndCountWhenError_mb = new Element(interfaces.get("eleSelectSizeAndCountWhenError_mb"));
		if (eleSelectSizeAndCountWhenError_mb.isDisplayed(Constants.SLEEP_TIME * 2)) {
			Common.click(eleSelectSizeAndCountWhenError_mb);
		}
		if (eleFirstSizeAndCount.isDisplayed(Constants.SLEEP_TIME * 2)) {
			selectFirstSizeAndCount();
			Common.click(btnAddToCart);
		}
	}

	@Override
	public void goToMyAccountPage() {
		if (eleMyAccountInTopModal.getElements().size() == 1) {
			Common.click(eleMyAccountInTopModal);
			return;
		}
		Common.scrollToTop();
		Common.click(eleMyAccountInTopModal.getElements().get(1));
		Common.waitForTitleChange();
	}

	@Override
	public void signOut() {
		Logger.info(" - Sign Out: ");
		if (!lnkSignOut.isDisplayed())
			goToSignInPage();
		Common.click(lnkSignOut);
		Common.waitForPageLoad();
		Common.waitForDOMChange();
	}
}
