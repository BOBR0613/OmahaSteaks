package com.omahasteaks.page.promotion.modal;

import org.openqa.selenium.WebElement;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.PromotionModal;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class PromotionModal_Desktop extends GeneralPage_Desktop implements PromotionModal {

	// ================================================================================
	// Locators
	// ================================================================================
	protected Button btnGetTheDeals = new Button(interfaces.get("btnGetTheDeals"));
	protected Button btnNoThanksOnToDayDeals = new Button(interfaces.get("btnNoThanksOnToDayDeals"));

	protected TextBox txtEmail = new TextBox(interfaces.get("txtEmail"));

	protected CheckBox cbIAgree = new CheckBox(interfaces.get("cbIAgree"));

	protected Element ele_FirstSKUFormInModal = new Element(interfaces.get("ele_FirstSKUFormInModal"));
	protected Element lstSKUFormInModal = new Element(interfaces.get("lstSKUFormInModal"));
	protected Element eleTodayOnlyDealsModal = new Element(interfaces.get("eleTodayOnlyDealsModal"));
	// ================================================================================
	// Support Methods
	// ================================================================================

	// ================================================================================
	// Main Methods
	// ================================================================================

	public void selectGetTheDealsButtonWithRandomEmail() {
		Common.triggerJustForYouModal();
		String email = Common.getRandomString("email") + "@omahasteaks.com";
		Common.enter(txtEmail, email);
		Common.check(cbIAgree, false);
		Common.click(btnGetTheDeals, false);
		Common.waitForDisplayed(lstSKUFormInModal);
	}

	public void addFirstSkuOnTodayOnlyDealsModal(SKU sku) {
		Common.waitForDisplayed(ele_FirstSKUFormInModal);
		Common.scrollElementToCenterScreen(ele_FirstSKUFormInModal);
		ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUFormInModal);
		TextBox txtShipTo = getTxtShipTo(ele_FirstSKUFormInModal);
		Button btnAddToCart = getBtnAddToCart(ele_FirstSKUFormInModal);
		if (!btnAddToCart.isDisplayed())
			throw new AssertionError("The \"Add To Cart\" button does not display");

		Common.scrollElementToCenterScreen(cbbSelectShipTo);
		selectShipToInModal(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
		Common.click(btnAddToCart, false);

		// Select Size & Count if it's required!
		if (eleFirstSizeAndCount.isDisplayed(Constants.SLEEP_TIME * 2)) {
			selectFirstSizeAndCount();
			if (DriverUtils.getDriverType().equals(Constants.BROWSER_IE)) {
				Common.click(btnAddToCart);
			}
		}
	}

	public boolean isTodayOnlyDealsModalDisplayed() {
		Logger.info(lstSKUFormInModal.getElements().size() + "");
		return (eleTodayOnlyDealsModal.isDisplayed() && lstSKUFormInModal.getElements().size() == 3);
	}

	public boolean areSubItemsRotated() {

		Image imgCurrentItemImage = new Image(ele_FirstSKUFormInModal, interfaces.get("imgCurrentItemImage"));
		Element lstCycleIcons = new Element(ele_FirstSKUFormInModal, interfaces.get("lstEleCycleIcon"));
		for (WebElement eleCycleIcon : lstCycleIcons.getElements()) {

			eleCycleIcon.click();
			DriverUtils.delay(1);

			String actual = eleCycleIcon.getAttribute("imgp");
			String expected = imgCurrentItemImage.getAttribute("src");
			if (!expected.contains(actual)) {
				Logger.info(actual + "-" + expected);
				return false;
			}
		}
		return true;
	}

}
