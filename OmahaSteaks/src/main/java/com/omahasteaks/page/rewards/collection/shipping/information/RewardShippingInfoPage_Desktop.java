package com.omahasteaks.page.rewards.collection.shipping.information;

import java.util.ArrayList;
import java.util.List;

import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.RewardShippingInfoPage;
import com.omahasteaks.page.rewards.collection.general.RewardGeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class RewardShippingInfoPage_Desktop extends RewardGeneralPage_Desktop implements RewardShippingInfoPage {

	// ================================================================================
	// Locators
	// ================================================================================
	protected CheckBox rdoResidentialAddress = new CheckBox(interfaces.get("rdoResidentialAddress"));
	protected CheckBox rdoCompanyAddress = new CheckBox(interfaces.get("rdoCompanyAddress"));
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
	protected Element eleShippingAddressFields = new Element(interfaces.get("eleShippingAddressFields"));

	// ================================================================================
	// Support Methods
	// ================================================================================
	protected Element getLinkAddressFieldInListErrorMessage(AddressFields addressField) {
		return new Element(interfaces.get("linkAddressFieldInListErrorMessage"), addressField.getValue());
	}

	// ================================================================================
	// Methods
	// ================================================================================
	public void selectAddressType(AddressType addressType) {
		Common.waitForPageLoad();
		if (addressType == AddressType.RESIDENTIAL_ADDRESS) {
			Common.check(rdoResidentialAddress);
		} else if (addressType == AddressType.COMPANY_ADDRESS) {
			Common.check(rdoCompanyAddress);
		}
	}

	public void fillShippingInformation(CustomerAddress address) {
		Common.waitForPageLoad();
		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		Common.enter(txtFirstName, address.firstName);
		Common.enter(txtLastName, address.lastName);
		Common.enter(txtPhone, address.phoneNumber);
		Common.enter(txtCompanyName, address.companyName);
		Common.enter(txtAddressLine1, address.streetAddress1);
		Common.enter(txtAddressLine2, address.streetAddress2);
		Common.enter(txtAptSuite, address.aptSuite);
		Common.select(cbbCountry, address.country);
		Common.enter(txtCity, address.city);
 		Common.enter(txtZip, address.zipCode);
		if (!address.zipCode.equals(""))
			Common.triggerTextBoxChangeEvent(txtZip);
		Common.select(cbbState, address.state);
	}

	public String[] getListFieldsInShippingInformationPage() {
		List<String> lstResult = new ArrayList<String>();
		for (int i = 0; i < eleShippingAddressFields.getElements().size(); i++) {
			lstResult.add(eleShippingAddressFields.getElements().get(i).getText());
		}
		return lstResult.toArray(new String[lstResult.size()]);
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

		return lstResult.toArray(new String[lstResult.size()]);
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

	public void clickOnAddressFieldLinkInListErrorMessage(AddressFields addressField) {
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
			Common.click(getLinkAddressFieldInListErrorMessage(addressField), ActionType.JS);
		else
			Common.click(getLinkAddressFieldInListErrorMessage(addressField));
		Common.waitForDOMChange();
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
}
