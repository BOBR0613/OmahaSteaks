package com.omahasteaks.page.shipping.address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.helper.Logger;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class ShippingAddressPage2SC_Desktop extends GeneralPage_Desktop implements ShippingAddressPage2SC {
	// ================================================================================
	// Locators
	// ================================================================================

	protected Link lnkSendTo = new Link(interfaces.get("lnkSendTo"));
	protected Link lnkEditThisAddress = new Link(interfaces.get("lnkEditThisAddress"));
	protected Link lnkEnterANewAddress = new Link(interfaces.get("lnkEnterANewAddress"));
	protected Link lnkEditCart = new Link(interfaces.get("lnkEditCart"));
	protected Link lnkEditPhoneNumber = new Link(interfaces.get("lnkEditPhoneNumber"));
	protected Link lnkAddAPhoneNumber = new Link(interfaces.get("lnkAddAPhoneNumber"));
	protected Link lnkEditEmail = new Link(interfaces.get("lnkEditEmail"));
	protected Link lnkAddAnEmailAddress = new Link(interfaces.get("lnkAddAnEmailAddress"));
	protected Link lnkNeedItSooner = new Link(interfaces.get("lnkNeedItSooner"));
	protected Link lnkRemoveItems = new Link(interfaces.get("lnkRemoveItems"));
	protected Element eleEditContactOrEditMyAddressForm = new Element(interfaces.get("eleEditContactOrEditMyAddressForm"));
	protected Element eleNextRecipientAddressBySelected = new Element(interfaces.get("eleNextRecipientAddressBySelected"));
	protected Element eleAddNewAddress = new Element(interfaces.get("eleAddNewAddress"));
	protected Element eleNotifyText = new Element(interfaces.get("eleNotifyText"));
	protected Element eleRecipientAddressBySelected = new Element(interfaces.get("eleRecipientAddressBySelected"));
	protected Element eleRecipientAddressField = new Element(interfaces.get("eleRecipientAddressField"));
	protected Element eleAddAGreetingCard = new Element(interfaces.get("eleAddAGreetingCard"));
	protected Element eleAddGiftWrap = new Element(interfaces.get("eleAddGiftWrap"));
	protected Element eleErrorMessageAtBottom = new Element(interfaces.get("eleErrorMessageAtBottom"));
	protected Element eleRecipientValue = new Element(interfaces.get("eleRecipientValue"));
	protected Element eleProgressTabsTitle = new Element(interfaces.get("eleProgressTabsTitle"));
	protected Element eleShippingError = new Element(interfaces.get("eleShippingError"));
	protected Element eleItemsInShippingError = new Element(interfaces.get("eleItemsInShippingError"));
	protected Element eleErrorMessage = new Element(interfaces.get("eleErrorMessage"));
	protected Element eleShippingOptions = new Element(interfaces.get("eleShippingOptions"));
	protected Image imgFirstGreetingCard = new Image(interfaces.get("imgFirstGreetingCard"));
	protected Image cbFirstGreetingCard = new Image(interfaces.get("cbFirstGreetingCard"));
	protected TextBox txtGiftMessageTextArea = new TextBox(interfaces.get("txtGiftMessageTextArea"));
	protected TextBox txtAddressNicknameInModal = new TextBox(interfaces.get("txtAddressNicknameInModal"));
	protected TextBox txtFirstNameInModal = new TextBox(interfaces.get("txtFirstNameInModal"));
	protected TextBox txtLastNameInModal = new TextBox(interfaces.get("txtLastNameInModal"));
	protected TextBox txtCompanyInModal = new TextBox(interfaces.get("txtCompanyInModal"));
	protected TextBox txtPhoneInModal = new TextBox(interfaces.get("txtPhoneInModal"));
	protected TextBox txtEmailInModal = new TextBox(interfaces.get("txtEmailInModal"));
	protected TextBox txtStreetAddressInModal = new TextBox(interfaces.get("txtStreetAddressInModal"));
	protected TextBox txtAptSuiteEtcInModal = new TextBox(interfaces.get("txtAptSuiteEtcInModal"));
	protected TextBox txtZipCodeInModal = new TextBox(interfaces.get("txtZipCodeInModal"));
	protected TextBox txtCityInModal = new TextBox(interfaces.get("txtCityInModal"));
	protected TextBox txtFirstName = new TextBox(interfaces.get("txtFirstName"));
	protected TextBox txtLastName = new TextBox(interfaces.get("txtLastName"));
	protected TextBox txtCompany = new TextBox(interfaces.get("txtCompany"));
	protected TextBox txtPhone = new TextBox(interfaces.get("txtPhone"));
	protected TextBox tbEmail = new TextBox(interfaces.get("tbEmail"));
	protected TextBox txtStreetAddress = new TextBox(interfaces.get("txtStreetAddress"));
	protected TextBox txtAptSuiteEtc = new TextBox(interfaces.get("txtAptSuiteEtc"));
	protected TextBox txtZipCode = new TextBox(interfaces.get("txtZipCode"));
	protected TextBox txtCity = new TextBox(interfaces.get("txtCity"));
	protected ComboBox cbbCountryInModal = new ComboBox(interfaces.get("cbbCountryInModal"));
	protected ComboBox cbbStateInModal = new ComboBox(interfaces.get("cbbStateInModal"));
	protected ComboBox cbbCountry = new ComboBox(interfaces.get("cbbCountry"));
	protected ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));
	protected Button btnUpdateContactInModal = new Button(interfaces.get("btnUpdateContactInModal"));
	protected Button btnContinue = new Button(interfaces.get("btnContinue"));
	protected Button btnNext = new Button(interfaces.get("btnNext"));
	protected Button btnEditAddressInPleaseConfirmAddressModal = new Button(interfaces.get("btnEditAddressInPleaseConfirmAddressModal"));
	protected Button btnConfirmAddressInPleaseConfirmAddressModal = new Button(interfaces.get("btnConfirmAddressInPleaseConfirmAddressModal"));
	protected Button btnCancelInRemoveThisCart = new Button(interfaces.get("btnCancelInRemoveThisCart"));
	protected Button btnConfirmUpdateInRemoveThisCart = new Button(interfaces.get("btnConfirmUpdateInRemoveThisCart"));

	// ================================================================================
	// Support Methods
	// ================================================================================
	protected void clearShippingAddressInModal() {
		// Do nothing on desktop
	}

	public void clickConfirmAddressButton() {
		Common.delay(3);
		Common.waitForDOMChange();
		if (btnConfirmAddressInPleaseConfirmAddressModal.isDisplayed()) {
			Common.click(btnConfirmAddressInPleaseConfirmAddressModal);
			Common.waitForDOMChange();
		}
	}

	protected void clearShippingAddress() {
		// Do nothing on desktop
	}

	protected ComboBox getStateComboBoxByCountry(String countryCode) {
		if (!countryCode.equalsIgnoreCase("US")) 
			return new ComboBox(interfaces.get("cbbProvince"), countryCode.toLowerCase());
		else
			return new ComboBox(interfaces.get("cbbState"), countryCode.toLowerCase());
	}

	protected TextBox getZipCodeTextboxByCountry(String countryCode) {
		if (!countryCode.equalsIgnoreCase("US")) 
			return new TextBox(interfaces.get("txtPostalCode"), countryCode.toLowerCase());
		else
			return new TextBox(interfaces.get("txtZipCode"), countryCode.toLowerCase());
	}

	protected ComboBox getStateComboBoxByCountryInModal(String countryCodeInModal) {
		if (!countryCodeInModal.equalsIgnoreCase("US"))
			return new ComboBox(interfaces.get("cbbProvinceInModal"), countryCodeInModal.toLowerCase());
		else
			return new ComboBox(interfaces.get("cbbStateInModal"), countryCodeInModal.toLowerCase());
	}

	protected TextBox getZipCodeTextboxByCountryInModal(String countryCodeInModal) {
		if (!countryCodeInModal.equalsIgnoreCase("US"))
			return new TextBox(interfaces.get("txtPostalCodeInModal"), countryCodeInModal.toLowerCase());
		else
			return new TextBox(interfaces.get("txtZipCodeInModal"), countryCodeInModal.toLowerCase());
	}

	// ================================================================================
	// Methods
	// ================================================================================
	public void fillShippingAddressInModal(CustomerAddress shippingAddress) {
		Common.waitForDOMChange();
		TextBox txtZipCodeInModal = getZipCodeTextboxByCountryInModal(shippingAddress.countryCodeInModal);
		ComboBox cbbStateInModal = getStateComboBoxByCountryInModal(shippingAddress.countryCodeInModal);
		Common.waitForDOMChange();
		Logger.info("First Name :" + shippingAddress.firstName);
		Common.enter(txtFirstNameInModal, shippingAddress.firstName);
		Common.waitForDOMChange();
		Logger.info("Last Name :" + shippingAddress.lastName);
		Common.enter(txtLastNameInModal, shippingAddress.lastName);
		Common.waitForDOMChange();
		Logger.info("Company :" + shippingAddress.companyName);
		Common.enter(txtCompanyInModal, shippingAddress.companyName);
		Common.waitForDOMChange();
		cbbCountryInModal.select(shippingAddress.country);
		Logger.info("Country :" + shippingAddress.country);
		Common.waitForDOMChange();
		// Remove Phone and Email field due to new design has changed
		/*
		 * Common.enter(txtPhoneInModal, shippingAddress.phoneNumber); // Enter email if
		 * email field is displayed if (txtEmailInModal.isDisplayed()) {
		 * Common.enter(txtEmailInModal, shippingAddress.email); }
		 */
		clearShippingAddressInModal();
		Common.waitForDOMChange();
		Logger.info("Address 1 :" + shippingAddress.streetAddress1);
		Common.enter(txtStreetAddressInModal, shippingAddress.streetAddress1);
		Logger.info("Apt:" + shippingAddress.aptSuite);
		Common.enter(txtAptSuiteEtcInModal, shippingAddress.aptSuite);
		Logger.info("city:" + shippingAddress.city);
		Common.enter(txtCityInModal, shippingAddress.city);
		if (shippingAddress.state == "" || shippingAddress.state == "Select" || shippingAddress.state == "State *") {
			shippingAddress.state = cbbStateInModal.getOptions().contains("Select") ? "Select" : "State *";
			Common.select(cbbStateInModal, shippingAddress.state, ActionType.JS, false);
		} else if (shippingAddress.state == "" || shippingAddress.state == "Select" || shippingAddress.state == "Province *") {
			shippingAddress.state = cbbStateInModal.getOptions().contains("Select") ? "Select" : "Province *";
			Common.select(cbbStateInModal, shippingAddress.state, ActionType.JS, false);
		} else
			Common.select(cbbStateInModal, shippingAddress.state, ActionType.JS, false);
		Logger.info("zip:" + shippingAddress.zipCode);
		Common.enter(txtZipCodeInModal, shippingAddress.zipCode);
		if (!shippingAddress.zipCode.equals("")) {
			Common.triggerTextBoxChangeEvent(txtZipCodeInModal);
		}
		Logger.info("email:" + shippingAddress.email);
		if (!shippingAddress.email.equals("")) {
			fillEmailShippingAddressInModal(shippingAddress.email);
		}
		Common.waitForDOMChange();
	}

	public void fillShippingAddress(CustomerAddress shippingAddress) {
		Common.waitForDOMChange();
		Common.waitForPageLoad();
		clearShippingAddress();
		TextBox txtZipCode = getZipCodeTextboxByCountry(shippingAddress.countryCode);
		ComboBox cbbState = getStateComboBoxByCountry(shippingAddress.countryCode);
		// eleRecipientAddressBySelected.waitForVisibility();
		Common.waitForDOMChange();
		Common.enter(txtFirstName, shippingAddress.firstName);
		Common.waitForDOMChange();
		Common.enter(txtLastName, shippingAddress.lastName);
		Common.waitForDOMChange();
		Common.enter(txtCompany, shippingAddress.companyName);

		Common.waitForDOMChange();
		if (DriverUtils.getDriverType().equals(Constants.BROWSER_IE)) {
			Common.select(cbbCountry, shippingAddress.country, ActionType.JS, false);
			Common.triggerTextBoxChangeEvent(cbbCountry);
		} else {
			cbbCountry.select(shippingAddress.country);
		}
		// Remove Phone and Email field due to new design has changed
		/*
		 * Common.enter(txtPhone, shippingAddress.phoneNumber); // Enter email if email
		 * field is displayed if (txtEmail.isDisplayed()) { Common.enter(txtEmail,
		 * shippingAddress.email); }
		 */
		clearShippingAddress();
		Common.waitForDOMChange();
		Common.enter(txtStreetAddress, shippingAddress.streetAddress1);
		Common.waitForDOMChange();
		Common.enter(txtAptSuiteEtc, shippingAddress.aptSuite);
		Common.waitForDOMChange(); 
		Common.delay(1); 

		if (shippingAddress.state == "" || shippingAddress.state == "Select" || shippingAddress.state == "State *") {
			shippingAddress.state = cbbState.getOptions().contains("Select") ? "Select" : "State *";
			Common.select(cbbState, shippingAddress.state, ActionType.JS, false);
		} else if (shippingAddress.state == "" || shippingAddress.state == "Select" || shippingAddress.state == "Province *") {
			shippingAddress.state = cbbState.getOptions().contains("Select") ? "Select" : "Province *";
			Common.select(cbbState, shippingAddress.state, ActionType.JS, false);
		} else 
		{
			Common.select(cbbState, shippingAddress.state, ActionType.JS, false);
		}

		Common.enter(txtCity, shippingAddress.city); 

		Common.enter(txtZipCode, shippingAddress.zipCode);
		Common.triggerTextBoxChangeEvent(txtZipCode);


		if (tbEmail.isDisplayed()) {
			if (!shippingAddress.email.equals("")) {
				Common.enter(tbEmail, shippingAddress.email);
				Common.triggerTextBoxChangeEvent(tbEmail);
			}
		}
		Common.waitForDOMChange();
	}

	public void fillEmailShippingAddress(String email) { 
	  tbEmail.clear();
	  tbEmail.enter(email);
	  Common.triggerTextBoxChangeEvent(tbEmail); 
	  Common.waitForDOMChange();
	}

	public void fillPhoneShippingAddress(String phone) {
		Common.enter(txtPhone, phone);
		Common.triggerTextBoxChangeEvent(txtPhone);
		Common.waitForDOMChange();
	}

	public void fillEmailShippingAddressInModal(String email) {
		if (txtEmailInModal.isDisplayed()) {
			Common.enter(txtEmailInModal, email);
		}
	}

	public void fillPhoneShippingAddressInModal(String phone) {
		Common.enter(txtPhoneInModal, phone);
	}

	public void updateContactInModal() {
		Common.click(btnUpdateContactInModal,false);
		Common.waitForDOMChange();
		Common.waitForPageLoad();
		Common.modalDialog.closeUnknownModalDialog();
	}

	public void clickEditAddressButtonInPleaseConfirmAddressModal() {
		Common.click(btnEditAddressInPleaseConfirmAddressModal, false);
		Common.waitForNotDisplayed(btnEditAddressInPleaseConfirmAddressModal);
		// Wait for page changes
		Common.waitForPageLoad();
	}

	public void openSendToDropDownList() {
		lnkSendTo.waitForVisibility();
		if (!eleAddNewAddress.isDisplayed()) {
			lnkSendTo.focus();
			eleAddNewAddress.waitForVisibility(Constants.SLEEP_TIME);
			if (!eleAddNewAddress.isDisplayed()) {
				// Handle auto open dropdownlist when focus on element on MAC
				if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
					Common.click(lnkSendTo, false, false);
				else {
					Common.click(lnkSendTo, false);
				}
			}
		}
		Common.waitForDisplayed(eleAddNewAddress);
	}

	public void selectNextRecipientAddressBySelected() {
		Common.waitForPageLoad();
		openSendToDropDownList();
		Common.click(eleNextRecipientAddressBySelected);
	}

	public void selectRecipientInSendToDropDownList(Recipient recipient) {
		Element eleRecipientAddressByRecipientName = new Element(interfaces.get("eleRecipientAddressByRecipientName"), recipient.getValue());
		openSendToDropDownList();
		Common.waitForDOMChange();
		Common.click(eleRecipientAddressByRecipientName);
		Common.waitForDOMChange();
	}

	public void selectAddNewAddressFromSendToDropDownList() {
		Logger.info("Open \"Sendto dropdown list and select \"New Address\"");
		openSendToDropDownList();
		Common.click(eleAddNewAddress);
	}

	public String getSelectedRecipientNameInSendToDropDownList() {
		Common.waitForDisplayed(eleRecipientAddressBySelected);
		// return eleRecipientAddressBySelected.getText().trim();
		return Common.getText(eleRecipientAddressBySelected);
	}

	public void clickEditThisAddressLink() {
		Common.modalDialog.closeUnknownModalDialog();
		Common.click(lnkEditThisAddress);
	}

	public void clickEnterANewAddressLink() {
		Common.click(lnkEnterANewAddress);
	}

	public void clickContinueButton() {
		// Common.waitForPageLoad(); 
		if (btnContinue.isDisplayed()) {
			Common.click(btnContinue);
			Common.waitForPageLoad();
		}
	}

	public void clickNextButton() {
		Common.waitForDOMChange();
		String currentRecipient = eleRecipientValue.getValue();
		Common.click(btnNext);
		Common.waitForValueChange(eleRecipientValue, currentRecipient);
		Common.waitForPageLoad();
	}

	public void clickEditCartLink() {
		Common.click(lnkEditCart);
		Common.waitForTitleChange();
	}

	public void clickEditLinkBesidePhoneCheckbox() {
		Common.click(lnkEditPhoneNumber);
	}

	public void clickAddAPhoneNumberLink() {
		Common.click(lnkAddAPhoneNumber);
	}

	public void editContactByClickingLinkInPhoneSection() {
		if (lnkEditPhoneNumber.isDisplayed(Constants.SLEEP_TIME)) {
			clickEditLinkBesidePhoneCheckbox();
		} else
			clickAddAPhoneNumberLink();
	}

	public void clickEditLinkBesideEmailCheckbox() {
		Common.click(lnkEditEmail);
	}

	public void clickAddAnEmailAddressLink() {
		Common.click(lnkAddAnEmailAddress);
	}

	public void editContactByClickingLinkInEmailSection() {
		if (lnkEditEmail.isDisplayed(Constants.SLEEP_TIME)) {
			clickEditLinkBesideEmailCheckbox();
		} else
			clickAddAnEmailAddressLink();
	}

	public boolean isSKUExisted(SKU sku) {
		clickViewItemsLink();
		Element eleSKUByName = new Element(interfaces.get("eleSKUByName"), sku.getName());
		return eleSKUByName.isDisplayed(Constants.SLEEP_TIME);
	}

	public boolean doesSKUExist(SKU sku) {
		clickViewItemsLink(); 
		Element eleSKUByName = new Element(interfaces.get("eleSKUByText"), sku.getName());
		return eleSKUByName.isDisplayed(Constants.SLEEP_TIME);
	}

	public String getNotifyText() {
		Common.waitForDisplayed(eleNotifyText);
		return Common.getText(eleNotifyText);
	}

	public String getRecipientAddressText() {
		Common.waitForDisplayed(eleRecipientAddressField);
		// return eleRecipientAddressField.getText().trim();
		return Common.getText(eleRecipientAddressField);
	}

	public String[] getRecipientAddress() {
		Common.waitForDisplayed(eleRecipientAddressField);
		List<String> lstArs = Arrays.asList(eleRecipientAddressField.getText().trim().split("\n"));
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public String generateRecipientAddressInfo(CustomerAddress address) {
		StringBuilder sb = new StringBuilder("");
		sb.append(address.firstName).append(" ").append(address.lastName);
		if (!address.companyName.equals("")) {
			sb.append(" " + address.companyName);
		}
		sb.append(" " + address.streetAddress1);
		if (!address.streetAddress2.equals("")) {
			sb.append(" " + address.streetAddress2);
		} else if (!address.aptSuite.equals("")) {
			sb.append(" " + address.aptSuite);
		}
		sb.append(" " + address.city);
		sb.append(", " + address.stateStandfor);
		sb.append(" " + address.zipCode);
		if (address.country.equals("United States")) {
			sb.append(" USA");
		}
		return sb.toString();
	}

	public void enterGiftMessage(String giftMessage) {
		Common.enter(txtGiftMessageTextArea, giftMessage);
	}

	public void selectGiftOptionGreetingCard() {
		// This step have been skipped in desktop mode
		// Common.click(eleAddAGreetingCard);
		// Common.waitForDisplayed(imgFirstGreetingCard);
	}

	public void selectGiftOptionGiftWrap() {
		Common.click(eleAddGiftWrap);
	}

	public String selectFirstGreetingCard() {
		Common.waitForDisplayed(imgFirstGreetingCard);
		String str = imgFirstGreetingCard.getAlt() + " - " + imgFirstGreetingCard.getSource();
		if (DriverUtils.getDriverType().equals(Constants.BROWSER_IE)) {
			Common.click(cbFirstGreetingCard);
		} else
			Common.click(imgFirstGreetingCard);
		return str;
	}

	public String getErrorMessageByField(AddressFields addrField) {
 		Common.waitForPageLoad();
 		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), addrField.getValue2SC());
 		Common.waitForDisplayed(eleErrorMessageByField); 
 		return Common.getText(eleErrorMessageByField);
	}

	public String getEmailErrorAtBottom() {
		return eleErrorMessageAtBottom.getText();
	}
	public String getErrorMessageAtBottom() {
	//	Common.waitForDisplayed(eleErrorMessageAtBottom);
	//  Delay 1s for waiting animation effect!
	//	DriverUtils.delay(1);
	//	List<WebElement> lwe = eleErrorMessageAtBottom.getElements();
	//	System.out.println("Error Message is " + lwe.get(lwe.size() - 1).getText().replace("\n", " ").trim().split(" Shipping Policy")[0]);
		return eleErrorMessageAtBottom.getText().replace("\n", " ").trim().split(" Shipping Policy")[0];
	}

	public void clickViewItemsLink() {
		// Do nothing on Desktop
	}

	public void closeViewItemsPage() {
		// Do nothing on Desktop
	}

	public String getProgressTabsTitle() {
		return Common.getText(eleProgressTabsTitle);
	}

	public String getShippingErrorMessage() {
		return Common.getText(eleShippingError);
	}

	public String getErrorMessage() {
		Common.waitForDOMChange();
		return Common.getText(eleErrorMessage);
	}

	public void clickRemoveItemsLink() {
		Common.click(lnkRemoveItems);
		Common.waitForDOMChange();
		lnkRemoveItems.waitForInvisibility(Constants.SLEEP_TIME);
	}

	public boolean isSKUExistedInShippingError(SKU sku) {
		List<WebElement> lstItem = eleItemsInShippingError.getElements();
		for (WebElement ele : lstItem) {
			if (ele.getText().contains(sku.getName()))
				return true;
		}
		return false;
	}

	public void clickCancelButtonInRemoveThisCartModal() {
		Common.click(btnCancelInRemoveThisCart, false);
		Common.waitForNotDisplayed(btnCancelInRemoveThisCart);
	}

	public void clickConfirmUpdateButtonInRemoveThisCartModal() {
		Common.click(btnConfirmUpdateInRemoveThisCart, false);
		Common.waitForNotDisplayed(btnConfirmUpdateInRemoveThisCart);
	}

	public boolean isShippingOptionsDisplayed() {
		return eleShippingOptions.isDisplayed();
		// return (eleShippingOptions.getElements().size() > 0);
	}

	public String getLandLinePhoneErrorMessage() {
		Element eleErrorMessage = new Element(interfaces.get("eleErrorMessageByField"), "Phone");
		Common.waitForDisplayed(eleErrorMessage);
		return Common.getText(eleErrorMessage);
	}

	public boolean isContinueButtonDisplayed() {
		return btnContinue.isDisplayed();
	}
}
