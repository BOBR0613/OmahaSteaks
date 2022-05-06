package com.omahasteaks.page.collection.center;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CollectionCenterPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;

public class CollectionCenterPage_Desktop extends GeneralPage_Desktop implements CollectionCenterPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected TextBox txtCollectionNumber = new TextBox(interfaces.get("txtCollectionNumber"));
	protected Button btnRedeem = new Button(interfaces.get("btnRedeem"));
	protected Button btnRedeemNow = new Button(interfaces.get("btnRedeemNow"));
	protected Element eleFirstSKU = new Element(interfaces.get("eleFirstSKU"));
	protected TextBox txtFirstName = new TextBox(interfaces.get("txtFirstName"));
	protected TextBox txtLastName = new TextBox(interfaces.get("txtLastName"));
	protected TextBox txtPhone = new TextBox(interfaces.get("txtPhone"));
	protected TextBox txtCompanyName = new TextBox(interfaces.get("txtCompanyName"));
	protected TextBox txtAddressLine1 = new TextBox(interfaces.get("txtAddressLine1"));
	protected TextBox txtAddressLine2 = new TextBox(interfaces.get("txtAddressLine2"));
	protected TextBox txtAptSuite = new TextBox(interfaces.get("txtAptSuite"));
	protected TextBox txtCity = new TextBox(interfaces.get("txtCity"));
	protected ComboBox cbbCountry = new ComboBox(interfaces.get("cbbCountry"));
	protected ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));
	protected TextBox txtZip = new TextBox(interfaces.get("txtZip"));
	protected TextBox txtEmailAddress = new TextBox(interfaces.get("txtEmailAddress"));
	protected Button btnContinue = new Button(interfaces.get("btnContinue"));
	protected CheckBox rdoResidentialAddress = new CheckBox(interfaces.get("rdoResidentialAddress"));
	protected CheckBox rdoCompanyAddress = new CheckBox(interfaces.get("rdoCompanyAddress"));
	protected Element eleTabNameSrc = new Element(interfaces.get("eleTabNameSrc"));
	protected Element eleGourmetCustomCertificate = new Element(interfaces.get("eleGourmetCustomCertificate"));
	protected TextBox txtDelayDate = new TextBox(interfaces.get("txtDelayDate"));
	protected Element imgBackToExclusiveOffer = new Element(interfaces.get("imgBackToExclusiveOffer"));
	protected Element elePleaseReviewOrderMsg = new Element(interfaces.get("elePleaseReviewOrderMsg"));
	protected Element eleShippingInfomationFields = new Element(interfaces.get("eleShippingInfomationFields"));
	protected Element eleShippingAddressFields = new Element(interfaces.get("eleShippingAddressFields"));
	protected Element eleErrorMessageInShippingOptions = new Element(interfaces.get("eleErrorMessageInShippingOptions"));
	protected Element eleAllSKUs = new Element(interfaces.get("eleAllSKUs"));

	// ================================================================================
	// Support Methods
	// ================================================================================

	protected CheckBox getShippingMethodRadioButton(String shippingMethod) {
		return new CheckBox(interfaces.get("rdShippingMethod"), shippingMethod);
	}

	protected Element getElementTitleOfShoppingBagTable(String recipientName) {
		return new Element(interfaces.get("eleTitleOfShoppingBagTable"), recipientName);
	}

	protected Element getElementSKUInformation(String section) {
		return new Element(interfaces.get("eleSKUInformation"), section);
	}

	protected Element getElementPartInShippingBagSection(String partName) {
		return new Element(interfaces.get("elePartInShippingBagSection"), partName);
	}

	protected Element getLinkAddressFieldInListErrorMessage(AddressFields addressField) {
		return new Element(interfaces.get("linkAddressFieldInListErrorMessage"), addressField.getValue());
	}

	protected Element getElementHeaderOfPrivacyPolicyPage(LinksFooter linkName) {
		return new Element(interfaces.get("eleHeaderOfPrivacyPolicyPage"), linkName.getValue());
	}

	protected Element getButtonRedeemNowByOrderNumberOfSKU(int orderNumber) {
		return new Element(interfaces.get("eleSKUByOrderNumber"), orderNumber);
	}

	// ================================================================================
	// Main Methods
	// ================================================================================

	public void submitCollectionNumber(String collectionNumber) {
		Common.waitForDisplayed(btnRedeem);
		Common.enter(txtCollectionNumber, collectionNumber);
		btnRedeem.click();
		Common.delay(Constants.SLEEP_TIME);
	}

	public void selectFirstSKU() {
		Common.waitForDisplayed(eleFirstSKU);
		Button firstBtn = new Button(eleFirstSKU, interfaces.get("btnRedeemNow"));
		Common.click(firstBtn);
		Common.waitForPageLoad();
	}

	public SKU getFirstSKUInfoFromGourmetCustomCertificatePage() {
		Element skuName = new Element(eleFirstSKU, interfaces.get("eleSkuName"));
		Element skuID = new Element(eleFirstSKU, interfaces.get("eleSkuID"));
		SKU sku = new SKU();
		sku.setId(Common.getText(skuID));
		sku.setName(Common.getText(skuName));
		return sku;
	}

	public void fillShippingInformation(CustomerAddress shippingAddress) {
		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		Common.enter(txtFirstName, shippingAddress.firstName);
		Common.enter(txtLastName, shippingAddress.lastName);
		Common.enter(txtPhone, shippingAddress.phoneNumber);
		Common.enter(txtCompanyName, shippingAddress.companyName);
		Common.enter(txtAddressLine1, shippingAddress.streetAddress1);
		Common.enter(txtAddressLine2, shippingAddress.streetAddress2);
		Common.enter(txtAptSuite, shippingAddress.aptSuite);
		Common.enter(txtCity, shippingAddress.city);
		Common.selectByJs(cbbState, shippingAddress.state);
		Common.enter(txtZip, shippingAddress.zipCode);
		if (!shippingAddress.zipCode.equals(""))
			Common.triggerTextBoxChangeEvent(txtZip);
		Common.select(cbbCountry, shippingAddress.country);
		Common.enter(txtEmailAddress, shippingAddress.email);
	}

	public void selectAddressType(AddressType type) {
		if (type == AddressType.RESIDENTIAL_ADDRESS) {
			Common.check(rdoResidentialAddress);
		} else if (type == AddressType.COMPANY_ADDRESS) {
			Common.check(rdoCompanyAddress);
		}
	}

	public void clickContinueButton() {
		Common.click(btnContinue);
		Common.waitForPageLoad();
	}

	public String getCurrentTabName() {
		String src = eleTabNameSrc.getAttribute("src").split(".com", 2)[1];
		String currentTabName = "";
		switch (src) {
		case Constants.SRC_SHIPPING_INFORMATION:
			currentTabName = Constants.TITLE_SHIPPING_INFORMATION;
			break;
		case Constants.SRC_SHIPPING_OPTIONS:
			currentTabName = Constants.TITLE_INFORMATION_OPTIONS;
			break;
		case Constants.SRC_ORDER_REVIEW:
			currentTabName = Constants.TITLE_ORDER_REVIEW;
			break;
		default:
			currentTabName = "";
		}
		return currentTabName;
	}

	public Boolean isButtonRedeemNowDisplayed() {
		return btnRedeemNow.isDisplayed();
	}

	public Boolean isGourmetCustomCertificateTextDisplayed() {
		return eleGourmetCustomCertificate.isDisplayed();
	}

	public Boolean isDelayedArrivalDateTextboxDisplayed() {
		return txtDelayDate.isDisplayed();
	}

	public Boolean isShippingMethodSelected(String shippingMethod) {
		return getShippingMethodRadioButton(shippingMethod).isChecked();
	}

	public void inputDelayedArrivalDate(String delayedArrivalDate) {
		Common.waitForDisplayed(txtDelayDate);
		Common.enter(txtDelayDate, delayedArrivalDate);
	}

	public void clickBackToExclusiveOfferLink() {
		imgBackToExclusiveOffer.click();
		Common.waitForPageLoad();
	}

	public Boolean isPleaseReviewYourOrderMsgDisplayed() {
		return elePleaseReviewOrderMsg.isDisplayed();
	}

	public Boolean isShoppingBagTitleDisplayedCorrectly(String recipientName) {
		return getElementTitleOfShoppingBagTable(recipientName).isDisplayed();
	}

	public SKU getSKUInformation() {
		SKU sku = new SKU();
		sku.setId(Common.getText(getElementSKUInformation(Constants.SHOPPING_BAG_ITEM)));
		sku.setName(Common.getText(getElementSKUInformation(Constants.SHOPPING_BAG_SELECTIONS)));
		sku.setQuantity(Integer.parseInt(Common.getText(getElementSKUInformation(Constants.SHOPPING_BAG_QUANTITY))));
		return sku;
	}

	public String[] getShippingInformation() {
		Common.waitForDisplayed(eleShippingInfomationFields);

		List<String> lstArs = Arrays.asList(eleShippingInfomationFields.getText().trim().split("\n"));
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public String[] getListFieldsInShippingInformationPage() {
		List<String> lstResult = new ArrayList<String>();
		for (int i = 0; i < eleShippingAddressFields.getElements().size(); i++) {
			lstResult.add(eleShippingAddressFields.getElements().get(i).getText());
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public Boolean isShippingPartDisplaysInShippingTable(String partName) {
		return getElementPartInShippingBagSection(partName).isDisplayed();
	}

	public String getDelayedArrivalDateInShippingOptionsPage() {
		return txtDelayDate.getAttribute("value");
	}

	public String[] getShippingAddressInShippingInformationPage() {
		List<String> lstResult = new ArrayList<String>();
		Common.waitForPageLoad();
		lstResult.add(txtFirstName.getAttribute("value"));
		lstResult.add(txtLastName.getAttribute("value"));
		lstResult.add(txtPhone.getAttribute("value"));
		lstResult.add(txtCompanyName.getAttribute("value"));
		lstResult.add(txtAddressLine1.getAttribute("value"));
		lstResult.add(txtAddressLine2.getAttribute("value"));
		lstResult.add(txtAptSuite.getAttribute("value"));
		lstResult.add(txtCity.getAttribute("value"));
		lstResult.add(cbbState.getAttribute("value"));
		lstResult.add(cbbCountry.getAttribute("value"));
		lstResult.add(txtZip.getAttribute("value"));
		lstResult.add(txtEmailAddress.getAttribute("value"));

		return lstResult.toArray(new String[lstResult.size()]);
	}

	public void clickTermAgreementLinkInFooter(LinksFooter nameLink) {
		Element eleTermAgressmentLink = new Element(interfaces.get("eleTermsAgreementLinkInFooter"), nameLink.getValue());
		String mainWindowHandle = DriverUtils.getWindowHandle();
		Common.click(eleTermAgressmentLink);
		List<String> lstWindow = DriverUtils.getWindowHandles();
		String termAgreementWindow = "";
		for (int i = 0; i < lstWindow.size(); i++) {
			if (!lstWindow.get(i).equals(mainWindowHandle))
				termAgreementWindow = lstWindow.get(i);
		}
		DriverUtils.switchTo(termAgreementWindow);
		Common.waitForPageLoad();
	}

	public void fillShippingAddressExceptField(CustomerAddress shippingAddress, AddressFields field) {
		CustomerAddress tmpShippingAddress = shippingAddress.clone();
		tmpShippingAddress.updateFieldValueBy(field, "");
		fillShippingInformation(tmpShippingAddress);
		switch (field) {
		case CITY:
			Common.enter(txtCity, "");
			break;
		case STATE:
			Common.select(cbbState, "");
			break;
		default:
			break;
		}
	}

	public String getErrorMessageByField(AddressFields field) {
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), field.getValue());
		String tmpField = field.getValueCollectionCenter();
		eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), tmpField);
		if (eleErrorMessageByField.isDisplayed(Constants.SHORT_TIME)) {
			return Common.getText(eleErrorMessageByField);
		} else
			return "";
	}

	public void clickOnAddressFieldLinkInListErrorMessage(AddressFields addressField) {
		Common.click(getLinkAddressFieldInListErrorMessage(addressField), ActionType.JS);
		Common.waitForDOMChange();
	}

	public void enterEmailAddress(String email) {
		Common.enter(txtEmailAddress, email);
	}

	public Boolean isAddressFieldFocused(AddressFields addressField) {
		switch (addressField) {
		case FIRST_NAME:
			return Common.getActiveElement().equals(txtFirstName.getElement());
		case LAST_NAME:
			return Common.getActiveElement().equals(txtLastName.getElement());
		case ADDRESS1:
			return Common.getActiveElement().equals(txtAddressLine1.getElement());
		case PHONE:
			return Common.getActiveElement().equals(txtPhone.getElement());
		case COMPANY_NAME:
			return Common.getActiveElement().equals(txtCompanyName.getElement());
		case CITY:
			return Common.getActiveElement().equals(txtCity.getElement());
		case ZIP_CODE:
			return Common.getActiveElement().equals(txtZip.getElement());
		case STATE:
			return Common.getActiveElement().equals(cbbState.getElement());
		default:
			return false;
		}
	}

	public Boolean isHeaderOfPrivacyPolicyPageDisplayed() {
		return getElementHeaderOfPrivacyPolicyPage(LinksFooter.PRIVACY_POLICY).isDisplayed();
	}

	public String getWarningMessage() {
		String message = txtEmailAddress.getAttribute("validationMessage");
		return message;
	}

	public String getErrorgMessageInShippingOptions() {
		return Common.getText(eleErrorMessageInShippingOptions).trim();
	}

	public Boolean isSKUOutOfStock() {
		int numberOfSKU = eleAllSKUs.getElements().size();
		Boolean isSKUOutOfStock = null;
		for (int i = 1; i <= numberOfSKU; i++) {
			Element redeemNowbtn = new Element(getButtonRedeemNowByOrderNumberOfSKU(i), interfaces.get("btnRedeemNow"));
			Common.scrollElementToCenterScreen(redeemNowbtn);
			if (redeemNowbtn.isDisplayed() == false) {
				isSKUOutOfStock = false;
				break;
			}
			isSKUOutOfStock = true;
		}
		return isSKUOutOfStock;
	}
}
