package com.omahasteaks.page.shipping.address;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class ShippingAddressPage2SC_Mobile extends ShippingAddressPage2SC_Desktop implements ShippingAddressPage2SC {
	public ShippingAddressPage2SC_Mobile() {
		super();
		btnContinue = new Button(interfaces.get("btnContinue_mb"));
		btnNext = new Button(interfaces.get("btnNext_mb"));
		lnkSendTo = new Link(interfaces.get("lnkSendTo_mb"));
		eleAddAGreetingCard = new Element(interfaces.get("eleAddAGreetingCard_mb"));
		eleAddGiftWrap = new Element(interfaces.get("eleAddGiftWrap_mb"));
	}

	// ================================================================================
	// Locators
	// ================================================================================
	protected Link lnkCart_mb = new Link(interfaces.get("lnkCart_mb"));
	protected Link lnkViewItems_mb = new Link(interfaces.get("lnkViewItems_mb"));
	protected Button btnClose_mb = new Button(interfaces.get("btnClose_mb"));

	// ================================================================================
	// Methods
	// ================================================================================
	@Override
	public void clickEditCartLink() {
		Common.click(lnkCart_mb);
		Common.waitForTitleChange();
	}

	@Override
	public void clickViewItemsLink() {
		Common.click(lnkViewItems_mb);
		Common.waitForPageLoad();
	}

	@Override
	public void closeViewItemsPage() {
		Common.click(btnClose_mb);
		Common.waitForPageLoad();
	}

	@Override
	public String getErrorMessageByField(AddressFields addrField) {
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField_mb"),
				addrField.getValue2SC());
		Common.waitForDisplayed(eleErrorMessageByField);
		return Common.getText(eleErrorMessageByField);
	}

	@Override
	protected void clearShippingAddressInModal() {
		Common.clear(txtStreetAddressInModal);
		Common.clear(txtZipCodeInModal);
		Common.clear(txtCityInModal);
		String tmpState = cbbStateInModal.getOptions().contains("Select") ? "Select" : "State *";
		cbbStateInModal.select(tmpState);
	}

	@Override
	protected void clearShippingAddress() {
		Common.clear(txtStreetAddress);
		Common.clear(txtZipCode);
		Common.clear(txtCity);
		String tmpState;
		if (cbbState.getOptions().contains("State *")) {
			tmpState = cbbState.getOptions().contains("Select") ? "Select" : "State *";
		} else {
			tmpState = cbbState.getOptions().contains("Select") ? "Select" : "Province *";
		}
		cbbState.select(tmpState);
	}

	@Override
	public String getErrorMessageAtBottom() {
		DriverUtils.delay(1);
		return Common.getTextAndCloseAlert();
	}

	@Override
	public void selectGiftOptionGreetingCard() {
		Common.click(eleAddAGreetingCard);
		Common.waitForDisplayed(imgFirstGreetingCard);
	}

	@Override
	public void selectGiftOptionGiftWrap() {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SLEEP_TIME * 1000;
		while (eleAddGiftWrap.getElements().size() < 2 && !eleAddGiftWrap.getElements().get(0).isDisplayed()
				&& !eleAddGiftWrap.getElements().get(1).isDisplayed() && (endTime < timeout)) {
			endTime = System.currentTimeMillis() - startTime;
		}
		if (eleAddGiftWrap.getElements().get(0).isDisplayed())
			Common.click(eleAddGiftWrap.getElements().get(0));
		else
			Common.click(eleAddGiftWrap.getElements().get(1));
	}

	@Override
	public String getLandLinePhoneErrorMessage() {
		Element eleErrorMessage = new Element(interfaces.get("eleErrorMessageByField_mb"), "Land-Line Phone");
		Common.waitForDisplayed(eleErrorMessage);
		return Common.getText(eleErrorMessage);
	}
}
