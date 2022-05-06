package com.omahasteaks.page.my.account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class MyAccountPage_Mobile extends MyAccountPage_Desktop implements MyAccountPage {

	public MyAccountPage_Mobile() {
		super();
		btnJoin = new Button(interfaces.get("btnJoin_mb"));
		btnEditBillingAddress = new Button(interfaces.get("btnEditBillingAddress_mb"));
		btnCancel = new Button(interfaces.get("btnCancel_mb"));
		txtPhoneForPhonePrerences = new TextBox(interfaces.get("txtPhoneForPhonePrerences_mb"));
		eleInformationBillingAddress = new Element(interfaces.get("eleInformationBillingAddress_mb"));
		eleInformationMyselfShippingAddress = new Element(interfaces.get("eleInformationMyselfShippingAddress_mb"));
		btnUpdateChangeEmail = new Button(interfaces.get("btnUpdateChangeEmail_mb"));
		btnMakeAdditionalChanges = new Button(interfaces.get("btnMakeAdditionalChanges_mb"));
		elePhonePreferencesTab = new Element(interfaces.get("elePhonePreferencesTab_mb"));
		btnYesDelete = new Button(interfaces.get("btnYesDelete_mb"));
		eleRewardsPoint = new Element(interfaces.get("eleRewardsPoint_mb"));
		btnCreateNewAddress = new Button(interfaces.get("btnCreateNewAddress_mb"));
		eleErrorMsgInEmailPreferencesTab = new TextBox(interfaces.get("eleErrorMsgInEmailPreferencesTab_mb"));
		eleErrorForPhoneTxtInPhonePreferencesTab = new Element(interfaces.get("eleErrorForPhoneTxtInPhonePreferencesTab_mb"));
		eleWarningDoNotCallMe = new Element(interfaces.get("eleWarningDoNotCallMe_mb"));
		eleErrorEmailAddressWasNotChanged = new Element(interfaces.get("eleErrorEmailAddressWasNotChanged_mb"));
		eleErrorBox = new Element(interfaces.get("eleErrorBox_mb"));
		btnJoinToday = new Button(interfaces.get("btnJoinToday_mb"));
	}

	protected Element eleAccountSettings_mb = new Element(interfaces.get("eleAccountSettings_mb"));
	protected Element eleMyOmahaSteaks_mb = new Element(interfaces.get("eleMyOmahaSteaks_mb"));
	protected Element elePhonePreferencesContent_mb = new Element(interfaces.get("elePhonePreferencesContent_mb"));
	protected Element eleRewardTab_mb = new Element(interfaces.get("eleRewardTab_mb"));

	protected CheckBox getCheckboxContactPreferencesOptionByValue(String value) {
		return new CheckBox(interfaces.get("eleOptionsContactPreferences_mb"), value);
	}

	protected CheckBox getCheckboxSteakloverRewardsAtContactPreferencesOptionByValue(String value) {
		return new CheckBox(interfaces.get("eleSteakloverRewardsOptionsContactPreferences_mb"), value);
	}

	protected CheckBox getCheckboxPhonePreferencesOptionByValue(String value) {
		return new CheckBox(interfaces.get("eleOptionsPhonePreferences_mb"), value);
	}

	@Override
	protected Element getLstBtnDeleteAddressByRecipientName() {
		return new Element(interfaces.get("lstBtnDeleteAddressByRecipientName_mb"), "First");
	}

	@Override
	protected Element getBtnDeleteAddressByRecipientName() {
		return new Element(interfaces.get("btnDeleteAddressByRecipientName_mb"), "First");
	}

	@Override
	public void goToEditAddressPageByRecipient(Recipient recipient) {
		Element btnEditAddressByRecipientName_mb = new Element(interfaces.get("btnEditAddressByRecipientName_mb"), recipient.getValue());
		Common.click(btnEditAddressByRecipientName_mb);
		Common.waitForNotDisplayed(btnEditAddressByRecipientName_mb);
		Common.waitForTitleChange();
	}

	@Override
	public void selectAccountSettingOption(String option) {
		Link lnkAccountSettings = new Link(interfaces.get("lnkAccountSettings_mb"), option);
		if (!lnkAccountSettings.isDisplayed()) {
			if (option.equals(Constants.STEAKLOVER_REWARDS)) {
				Common.click(eleMyOmahaSteaks_mb, false);
			} else {
				Common.click(eleAccountSettings_mb, false);
			}
		}
		Common.click(lnkAccountSettings);
		if (option.equals(Constants.CONTACT_PREFERENCES))
			Common.waitForDOMChange();
		else
			Common.waitForTitleChange();
	}

	@Override
	public String[] getBillingAddress() {
		Common.waitForDisplayed(eleInformationBillingAddress);
		String[] lstArs = eleInformationBillingAddress.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty() && !ars.equals("Edit"))
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	@Override
	public String[] getMyselfShippingAddress() {
		Common.waitForDisplayed(eleInformationMyselfShippingAddress);
		String[] lstArs = eleInformationMyselfShippingAddress.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty() && !ars.equals("Edit") && !ars.equals("Myself"))
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	@Override
	public List<String> updateRandomEmailPreferences() {
		List<String> lstRefer = new ArrayList<String>();
		lstRefer.add(getRandomContactPreferences(Constants.LIST_EXCLUSIVE_OFFERS_OPTIONS));
		Common.check(getCheckboxContactPreferencesOptionByValue(lstRefer.get(0).toString()), false);

		lstRefer.add(getRandomContactPreferences(Constants.LIST_STEAKLOVER_REWARDS_OPTIONS_MB));
		Common.check(getCheckboxSteakloverRewardsAtContactPreferencesOptionByValue(lstRefer.get(1).toString()), false);

		Common.click(btnUpdatePreferences, false);
		Common.click(btnMakeAdditionalChanges, false);

		return lstRefer;
	}

	@Override
	public String updateRandomPhonePreferences(String phoneNumber) {
		Common.enter(txtPhoneForPhonePrerences, phoneNumber);
		String phonePrerencesOption = getRandomContactPreferences(Constants.LIST_PHONE_PREFERENCES_OPTIONS);
		Common.check(getCheckboxPhonePreferencesOptionByValue(phonePrerencesOption), false);
		Common.click(btnUpdatePhonePreferences, false);
		Common.waitForDOMChange();
		Common.click(btnMakeAdditionalChanges, false);
		Common.waitForDisplayed(btnUpdatePhonePreferences);
		return phonePrerencesOption;
	}

	@Override
	public boolean isCheckboxContactPreferencesOptionByValueChecked(String value) {
		if (elePhonePreferencesContent_mb.isDisplayed()) {
			return getCheckboxPhonePreferencesOptionByValue(value).isChecked();
		} else {
			if (value.equals(Constants.STEAKLOVER_REWARDS_OPTION_2) || value.equals(Constants.STEAKLOVER_REWARDS_OPTION_3)) {
				return getCheckboxSteakloverRewardsAtContactPreferencesOptionByValue(value).isChecked();
			} else {
				return getCheckboxContactPreferencesOptionByValue(value).isChecked();
			}
		}
	}

	@Override
	public String getErrorMessageByCreateAccountField(String field) {
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByCreateAccountField_mb"), field);
		if (eleErrorMessageByField.isDisplayed(Constants.SHORT_TIME)) {
			return eleErrorMessageByField.getText().split(":")[1].trim();
		} else
			return "";
	}

	@Override
	public String getErrorMessageByField(AddressFields field) {
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField_mb"), field.getValue());
		String tmpField = field.getValue();
		if (field.equals(AddressFields.PHONE))
			tmpField = "Phone";
		eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField_mb"), tmpField);
		if (eleErrorMessageByField.isDisplayed(Constants.SHORT_TIME)) {
			return Common.getText(eleErrorMessageByField).split(":")[1].trim();
		} else
			return "";
	}

	@Override
	public void selectTypeAddress(String typeAddress) {
		Element radioBtnCompanyAddress = new Element(interfaces.get("radioBtnCompanyAddress_mb"));
		Element radioBtnResidentialAddress = new Element(interfaces.get("radioBtnResidentialAddress_mb"));
		if (typeAddress.equals("Company Address"))
			Common.click(radioBtnCompanyAddress);
		else if (typeAddress.equals("Residential Address"))
			Common.click(radioBtnResidentialAddress);
	}

	@Override
	public void selectCallMeWithExlusiveOffers(String option) {
		CheckBox eleOptionsContactPreferences = new CheckBox(interfaces.get("eleOptionsPhonePreferences_mb"), option);
		Common.waitForDisplayed(eleOptionsContactPreferences);
		Common.click(eleOptionsContactPreferences, false);
	}

	@Override
	public String getErrorEmailAddressWasNotChanged() {
		Common.waitForDisplayed(eleErrorEmailAddressWasNotChanged);
		return Common.getText(eleErrorEmailAddressWasNotChanged).split(":")[1].trim();
	}

	@Override
	public String[] getShippingAddressByRecipient(Recipient recipient) {
		Element eleInformationRecipientShippingAddress = new Element(interfaces.get("eleInformationRecipientShippingAddress_mb"), recipient.getValue());
		Common.waitForDisplayed(eleInformationRecipientShippingAddress);
		String[] lstArs = eleInformationRecipientShippingAddress.getText().replace("Edit | Delete\n" + "Newrecipient\n", "").trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	@Override
	public void deleteAddressByRecipientName(Recipient recipient) {
		Button btnDeleteAddress = new Button(interfaces.get("btnDeleteAddressByRecipientName_mb"), recipient.getValue());
		Button btnDeleteAddressBook = new Button(interfaces.get("btnDeleteAddressBook"));
		Common.click(btnDeleteAddress);
		Common.waitForPageLoad();
		Common.click(btnDeleteAddressBook);
	}

	@Override
	public String getMonthlyFreeFoodName() {
		Common.click(eleRewardTab_mb, false);
		Common.waitForDOMChange();
		return Common.getText(eleNameOfMonthlyFreeFood);
	}

	@Override
	public Integer[] getListPointofRewardSKU() {
		Common.click(eleRewardTab_mb, false);
		Common.waitForDOMChange();
		List<Integer> lstReward = new ArrayList<Integer>();
		for (int i = 0; i < eleMinPoint.getElements().size(); i++) {
			eleMinPoint.getElements().get(i).getText();

			Pattern pattern = Pattern.compile("\\d*");
			Matcher matcher = pattern.matcher(eleMinPoint.getElements().get(i).getText());
			if (matcher.matches()) {
				if (!eleMinPoint.getElements().get(i).getText().isEmpty())
					lstReward.add(Integer.parseInt(eleMinPoint.getElements().get(i).getText()));
			} else {
				continue;
			}
		}
		return lstReward.toArray(new Integer[lstReward.size()]);
	}

	@Override
	public boolean isTheMonthlyFreeFoodDisplayed() {
		Common.click(eleRewardTab_mb, false);
		Common.waitForDOMChange();
		return eleNameOfMonthlyFreeFood.isDisplayed();
	}
}
