package com.omahasteaks.page.my.account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class MyAccountPage_Desktop extends GeneralPage_Desktop implements MyAccountPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element eleAddressBook = new Element(interfaces.get("eleAddressBook"));
	protected TextBox txtFirstName = new TextBox(interfaces.get("txtFirstName"));
	protected TextBox txtLastName = new TextBox(interfaces.get("txtLastName"));
	protected TextBox txtPhone = new TextBox(interfaces.get("txtPhone"));
	protected TextBox txtCompanyName = new TextBox(interfaces.get("txtCompanyName"));
	protected TextBox txtAddress1 = new TextBox(interfaces.get("txtAddress1"));
	protected TextBox txtAddress2 = new TextBox(interfaces.get("txtAddress2"));
 	protected TextBox txtCity = new TextBox(interfaces.get("txtCity"));
	protected TextBox txtZip = new TextBox(interfaces.get("txtZip"));
	protected ComboBox cbbCountry = new ComboBox(interfaces.get("cbbCountry"));
	protected ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));
	protected ComboBox cbbbirthdayMonth = new ComboBox(interfaces.get("cbbbirthdayMonth"));
	protected ComboBox cbbbirthdayDay = new ComboBox(interfaces.get("cbbbirthdayDay"));
	protected Button btnEditBillingAddress = new Button(interfaces.get("btnEditBillingAddress"));
	protected CheckBox cbCheckShippingAddressSameBillingAddress = new CheckBox(interfaces.get("cbCheckShippingAddressSameBillingAddress"));
	protected CheckBox cbJoinReward = new CheckBox(interfaces.get("cbJoinReward"));
	protected Button btnUpdate = new Button(interfaces.get("btnUpdate"));
	protected Element eleInformationMyselfShippingAddress = new Element(interfaces.get("eleInformationMyselfShippingAddress"));
	protected Element eleInformationBillingAddress = new Element(interfaces.get("eleInformationBillingAddress"));
	protected Button btnLogIn = new Button(interfaces.get("btnLogIn"));
	protected TextBox txtEmailAddress = new TextBox(interfaces.get("txtEmailAddress"));
	protected TextBox txtPassword = new TextBox(interfaces.get("txtPassword"));
	protected TextBox txtConfirmPassword = new TextBox(interfaces.get("txtConfirmPassword"));
	protected Button btnJoin = new Button(interfaces.get("btnJoin"));
	protected Button btnYesDelete = new Button(interfaces.get("btnYesDelete"));
	protected Element eleErrorMessage = new Element(interfaces.get("eleErrorMessage"));
	protected Element eleContactPreferences = new Element(interfaces.get("eleContactPreferences"));
	protected Button btnUpdatePreferences = new Button(interfaces.get("btnUpdatePreferences"));
	protected Element eleChangeEmailAddress = new Element(interfaces.get("eleChangeEmailAddress"));
	protected TextBox txtNewEmailForChangeEmailAddress = new TextBox(interfaces.get("txtNewEmailForChangeEmailAddress"));
	protected TextBox txtConfirmEmailForChangeEmailAddress = new TextBox(interfaces.get("txtConfirmEmailForChangeEmailAddress"));
	protected Button btnUpdateChangeEmail = new Button(interfaces.get("btnUpdateChangeEmail"));
	protected Element eleWelcomeMembership = new Element(interfaces.get("eleWelcomeMembership"));
	protected Element elePhonePreferencesTab = new Element(interfaces.get("elePhonePreferencesTab"));
	protected Button btnUpdatePhonePreferences = new Button(interfaces.get("btnUpdatePhonePreferences"));
	protected Button btnMakeAdditionalChanges = new Button(interfaces.get("btnMakeAdditionalChanges"));
	protected TextBox txtPhoneForPhonePrerences = new TextBox(interfaces.get("txtPhoneForPhonePrerences"));
	protected Element eleCloseContactModal = new Element(interfaces.get("eleCloseContactModal"));
	protected Element eleRewardsPoint = new Element(interfaces.get("eleRewardsPoint"));
	protected Button btnCreateNewAddress = new Button(interfaces.get("btnCreateNewAddress"));
	protected TextBox txtShoppingName = new TextBox(interfaces.get("txtShoppingName"));
	protected Button btnCancel = new Button(interfaces.get("btnCancel"));
	protected Element eleAddressWarning = new Element(interfaces.get("eleAddressWarning"));
	protected Label lblBillingAddress = new Label(interfaces.get("lblBillingAddress"));
	protected TextBox eleErrorMsgInEmailPreferencesTab = new TextBox(interfaces.get("eleErrorMsgInEmailPreferencesTab"));
	protected TextBox txtEmailAddressInEmailPreferencesTab = new TextBox(interfaces.get("txtEmailAddressInEmailPreferencesTab"));
	protected Element eleErrorForPhoneTxtScheduleYourCallTimesPopup = new Element(interfaces.get("eleErrorForPhoneTxtScheduleYourCallTimesPopup"));
	protected Element linkScheduleMyCallTimes = new Element(interfaces.get("linkScheduleMyCallTimes"));
	protected Element eleWarningDoNotCallMe = new Element(interfaces.get("eleWarningDoNotCallMe"));
	protected Element eleErrorForPhoneTxtInPhonePreferencesTab = new Element(interfaces.get("eleErrorForPhoneTxtInPhonePreferencesTab"));
	protected TextBox txtPhoneInPhoneNumberPreferencesTab = new TextBox(interfaces.get("txtPhoneInPhoneNumberPreferencesTab"));
	protected Button btnSubmitInScheduleYourCallTimesPopup = new Button(interfaces.get("btnSubmitInScheduleYourCallTimesPopup"));
	protected Element eleErrorBox = new Element(interfaces.get("eleErrorBox"));
	protected Element eleValidationConfirmEmailAddress = new Element(interfaces.get("eleValidationConfirmEmailAddress"));
	protected Element eleErrorEmailAddressWasNotChanged = new Element(interfaces.get("eleErrorEmailAddressWasNotChanged"));
	protected Element eleNameOfMonthlyFreeFood = new Element(interfaces.get("eleNameOfMonthlyFreeFood"));
	protected Element eleMinPoint = new Element(interfaces.get("eleMinPoint"));
	protected Element eleJoinSteakloverRewardsModal = new Element(interfaces.get("eleJoinSteakloverRewardsModal"));
	protected Element eleHeading1 = new Element(interfaces.get("eleHeading1"));
	protected Button btnJoinToday = new Button(interfaces.get("btnJoinToday"));
	protected Label eleUserStatusInSLRPage = new Label(interfaces.get("eleUserStatusInSLRPage"));
	protected Label eleUserStatusInSLRSection = new Label(interfaces.get("eleUserStatusInSLRSection"));
	protected Element elegoldstatus = new Element(interfaces.get("elegoldstatus"));
	protected Element eleFreeFoodItemPoints = new Element(interfaces.get("eleFreeFoodItemPoints"));
	protected Button btnRenewGoldMbr = new Button(interfaces.get("btnRenewGoldMbr"));
	
	// ================================================================================
	// Support Methods
	// ================================================================================
	protected Element getLstBtnDeleteAddressByRecipientName() {
		return new Element(interfaces.get("lstBtnDeleteAddressByRecipientName"), "First");
	}

	protected Element getBtnDeleteAddressByRecipientName() {
		return new Element(interfaces.get("btnDeleteAddressByRecipientName"), "First");
	}

	protected Element getEleOptionSeeDeal(String index) {
		return new Element(interfaces.get("eleOptionSeeDeal"), index);
	}

	protected CheckBox getCheckboxContactPreferencesOptionByValue(String value) {
		return new CheckBox(interfaces.get("eleOptionsContactPreferences"), value);
	}

	// ================================================================================
	// Methods
	// ================================================================================
	public void goToMyAccountAddressBook() {
		Logger.substep("Click on \"Address Book\" link on left navigation.");
		Common.click(eleAddressBook);
		Common.waitForNotDisplayed(eleAddressBook);
		Common.waitForTitleChange();
	}
	
	public void clickRewewMembershipButton() {
		Common.tryClickByJs(btnRenewGoldMbr);
		Common.waitForDOMChange();
	}

	public void goToEditBillingAddressPage() {
		Logger.substep("Click on \"Edit Billing Address\" button.");
		Common.click(btnEditBillingAddress);
		Common.waitForPageLoad();
	}

	public void fillBillingAddress(CustomerAddress billingAddress) {
		if (txtShoppingName.isDisplayed()) Common.enter(txtShoppingName, "x");
		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		Logger.substep("Enter \""+billingAddress.firstName+"\" into first name.");
		Common.enter(txtFirstName, billingAddress.firstName);
		Logger.substep("Enter \""+billingAddress.lastName+"\" into last name.");
		Common.enter(txtLastName, billingAddress.lastName);
		Logger.substep("Enter \""+billingAddress.phoneNumber+"\" into phone number.");
		Common.enter(txtPhone, billingAddress.phoneNumber);
		Logger.substep("Enter \""+billingAddress.companyName+"\" into company name.");
		Common.enter(txtCompanyName, billingAddress.companyName);
		Logger.substep("Enter \""+billingAddress.streetAddress1+"\" into address1.");
		Common.enter(txtAddress1, billingAddress.streetAddress1);
		Logger.substep("Enter \""+billingAddress.streetAddress2+"\" into address2.");
		Common.enter(txtAddress2, billingAddress.streetAddress2);
		Logger.substep("Enter \""+billingAddress.city+"\" into city.");
 		Common.enter(txtCity, billingAddress.city);
 		Logger.substep("Select \""+billingAddress.state+"\" as the state.");
		Common.select(cbbState, billingAddress.state);
		Logger.substep("Select \""+billingAddress.country+"\" as the country");
		Common.select(cbbCountry, billingAddress.country);
		Logger.substep("Enter \""+billingAddress.zipCode+"\" as the zipcode.");
		Common.enter(txtZip, billingAddress.zipCode);
		if (!billingAddress.zipCode.equals(""))
			Common.triggerTextBoxChangeEvent(txtZip);
		Logger.substep("Ensure \"Shipping Addres is Same As Billing Addres\" checkbox is unchecked.");
		if (cbCheckShippingAddressSameBillingAddress.isChecked())
			Common.uncheck(cbCheckShippingAddressSameBillingAddress);
	}

	public boolean isRenewGoldMbrModalShown() {
	  return btnRenewGoldMbr.isDisplayed();
	}
	
	public void fillShippingAddress(CustomerAddress shippingAddress) {

		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		Logger.substep("Enter \""+shippingAddress.firstName+"\" into the firstname field.");
		Common.enter(txtFirstName, shippingAddress.firstName);
		Logger.substep("Enter \""+shippingAddress.lastName+"\" into the lastname field.");
		Common.enter(txtLastName, shippingAddress.lastName);
		Logger.substep("Enter \""+shippingAddress.phoneNumber+"\" into the phonenumber field.");
		Common.enter(txtPhone, shippingAddress.phoneNumber);
		Logger.substep("Enter \""+shippingAddress.companyName+"\" into the companyname field.");
		Common.enter(txtCompanyName, shippingAddress.companyName);
		Logger.substep("Enter \""+shippingAddress.streetAddress1+"\" into the address1 field.");
		Common.enter(txtAddress1, shippingAddress.streetAddress1);
		Logger.substep("Enter \""+shippingAddress.streetAddress2+"\" into the address2 field.");
		Common.enter(txtAddress2, shippingAddress.streetAddress2);
		Logger.substep("Enter \""+shippingAddress.city+"\" into the city field.");
 		Common.enter(txtCity, shippingAddress.city);
		Logger.substep("Select \""+shippingAddress.state+"\" as the state.");
		Common.select(cbbState, shippingAddress.state);
		Logger.substep("Select \""+shippingAddress.country+"\" as the country.");
		Common.select(cbbCountry, shippingAddress.country);
		Logger.substep("Enter \""+shippingAddress.zipCode+"\" into the zipcode field.");
		Common.enter(txtZip, shippingAddress.zipCode);
		if (!shippingAddress.zipCode.equals(""))
			Common.triggerTextBoxChangeEvent(txtZip);
	}

	
	public void clickUpdateButton() {
		Logger.substep("Click \"Update\" button.");
		Common.click(btnUpdate);
		Common.waitForDOMChange();
	}

	public void clickCancelButton() {
		Common.click(btnCancel);
	}

	public void goToEditAddressPageByRecipient(Recipient recipient) {
		Logger.substep("Click on \"Edit Address\" button for " + recipient.getValue() + ".");
		Element btnEditAddressByRecipientName = new Element(interfaces.get("btnEditAddressByRecipientName"), recipient.getValue());
		Common.waitForPageLoad();
		Common.click(btnEditAddressByRecipientName);
		Common.waitForNotDisplayed(btnEditAddressByRecipientName);
		Common.waitForPageLoad();
	}

	public CustomerAddress getAddressInEditAddressPage() {
		CustomerAddress ca = new CustomerAddress();
		Common.waitForEnabled(txtFirstName);
		ca.firstName = txtFirstName.getValue();
		ca.lastName = txtLastName.getValue();
		ca.companyName = txtCompanyName.getValue();
		ca.streetAddress1 = txtAddress1.getValue();
		ca.streetAddress2 = txtAddress2.getValue();
 		ca.zipCode = txtZip.getValue();
		ca.city = txtCity.getValue();
		ca.phoneNumber = txtPhone.getValue();
		ca.country = cbbCountry.getSelected();
		ca.state = cbbState.getSelected();
		ca.stateStandfor = cbbState.getValue();
		return ca;
	}

	public void cleanUpAddressBook() {
		try {
			DriverUtils.navigate(Constants.getRunningURL() + Constants.OMAHA_URL_MYACCOUNTPAGE);
			goToMyAccountAddressBook();
			Element lstBtnDeleteAddressByRecipientName = getLstBtnDeleteAddressByRecipientName();
			Element btnDeleteAddressByRecipientName = getBtnDeleteAddressByRecipientName();
			Element btnDeleteAddressBook = new Element(interfaces.get("btnDeleteAddressBook"));
			int sizeOfList = lstBtnDeleteAddressByRecipientName.getElements().size();

			for (int count = 0; count < sizeOfList; count++) {
				System.out.println(count);
				Common.click(btnDeleteAddressByRecipientName);
				Common.waitForDisplayed(txtFirstName);
				Common.click(btnDeleteAddressBook);
				Common.waitForPageLoad();
			}
		} catch (Exception e) {
			return;
		}
	}

	public String[] getBillingAddress() {
		Common.waitForDisplayed(lblBillingAddress);
		String[] lstArs = eleInformationBillingAddress.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public String[] getMyselfShippingAddress() {
		Common.waitForDisplayed(eleInformationMyselfShippingAddress);
		String[] lstArs = eleInformationMyselfShippingAddress.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public void signIn(String email, String password) {
		Logger.info(" - Sign in with account: " + email);
		Common.waitForDisplayed(txtEmailAddress);
		Common.enter(txtEmailAddress, email);
		Common.enter(txtPassword, password);
		Common.click(btnLogIn);
	}

	public void signIn(Account account) {
		signIn(account.getEmail(), account.getPassword());
		Common.waitForPageLoad();
	}

	public void createNewAccount(String email, String password, boolean joinSteakloverRewards) {
		Common.waitForDisplayed(txtEmailAddress);
		Common.enter(txtEmailAddress, email);
		Common.enter(txtPassword, password);
		Common.enter(txtConfirmPassword, password);
		if (!joinSteakloverRewards) {
			Common.uncheck(cbJoinReward);
		}
		Common.click(btnJoin);
		Common.waitForPageLoad();
	}

	public void createNewAccount(Account account) {
		createNewAccount(account.getEmail(), account.getPassword(), true);
	}

	public void editBookAdress(CustomerAddress bookAddess) {
		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		Common.clear(txtFirstName);
		Common.enter(txtFirstName, bookAddess.firstName);
		Common.clear(txtLastName);
		Common.enter(txtLastName, bookAddess.lastName);
		Common.clear(txtPhone);
		Common.enter(txtPhone, bookAddess.phoneNumber);
		Common.clear(txtCompanyName);
		Common.enter(txtCompanyName, bookAddess.companyName);
		Common.clear(txtAddress1);
		Common.enter(txtAddress1, bookAddess.streetAddress1);
		Common.clear(txtAddress2);
		Common.enter(txtAddress2, bookAddess.streetAddress2);
  		Common.enter(txtCity, bookAddess.city);
		Common.select(cbbState, bookAddess.state);
		Common.select(cbbCountry, bookAddess.country);
		Common.enter(txtZip, bookAddess.zipCode);
	}

	public void selectAccountSettingOption(String option) {
		Link lnkAccountSettings = new Link(interfaces.get("lnkAccountSettings"), option);
		Common.click(lnkAccountSettings);
		if (option.equals(Constants.CONTACT_PREFERENCES))
			Common.waitForDOMChange();
		else
			Common.waitForTitleChange();
	}

	public void selectMyOmahaSteaksOption(String option) {
		Link lnkMyOmahaSteaks = new Link(interfaces.get("lnkMyOmahaSteaks"), option);
		Common.click(lnkMyOmahaSteaks);
		Common.waitForTitleChange();
	}

	public String getWelcomeText() {
		return eleWelcomeMembership.getText().trim();
	}

	public void fillPasswordToDeleteAccount(String password) {
		Common.enter(txtPassword, password);
	}

	public void clickYesDeleteMyInformation() {
		Common.click(btnYesDelete);
	}

	public String getErrorMessage() {
		eleErrorMessage.waitForDisplay();
		return Common.getText(eleErrorMessage);
	}

	public String getWelcomeMembershipText() {
		return Common.getText(eleWelcomeMembership);
	}

	public void changeEmailAddress(String email) {
		changeEmailAddress(email, email);
	}

	public void changeEmailAddress(String newEmail, String confirmEmail) {
		Common.enter(txtNewEmailForChangeEmailAddress, newEmail);
		Common.enter(txtConfirmEmailForChangeEmailAddress, confirmEmail);
		btnUpdateChangeEmail.click();
		Common.waitForDOMChange();
 	}

	public void clickPhonePreferencesTab() {
		Common.click(elePhonePreferencesTab, false);
	}

	public String getRandomContactPreferences(List<String> listOptions) {
		return listOptions.get(Common.randBetween(0, listOptions.size() - 1)).toString();
	}

	public List<String> updateRandomEmailPreferences() {
		List<String> lstRefer = new ArrayList<String>();
		lstRefer.add(getRandomContactPreferences(Constants.LIST_HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTIONS));
		Common.check(getCheckboxContactPreferencesOptionByValue(lstRefer.get(0).toString()), false);

		lstRefer.add(getRandomContactPreferences(Constants.LIST_STEAKLOVER_REWARDS_OPTIONS));
		Common.check(getCheckboxContactPreferencesOptionByValue(lstRefer.get(1).toString()), false);

		Common.click(btnUpdatePreferences, false);
		Common.click(btnMakeAdditionalChanges, false);

		return lstRefer;
	}

	public String updateRandomPhonePreferences(String phoneNumber) {
		Common.enter(txtPhoneForPhonePrerences, phoneNumber);
		String phonePrerencesOption = getRandomContactPreferences(Constants.LIST_PHONE_PREFERENCES_OPTIONS);
		Common.check(getCheckboxContactPreferencesOptionByValue(phonePrerencesOption), false);
		Common.click(btnUpdatePhonePreferences, false);
		Common.waitForDOMChange();
		Common.click(btnMakeAdditionalChanges, false);
		Common.waitForDisplayed(btnUpdatePhonePreferences);
		return phonePrerencesOption;
	}

	public boolean isCheckboxContactPreferencesOptionByValueChecked(String value) {
		return getCheckboxContactPreferencesOptionByValue(value).isChecked();
	}

	public void clickSocialNetworkIcon(String nameSocialNetworkIcon) {
		Element eleSocialNetworkIconByName = new Element(interfaces.get("eleSocialNetworkIconByName"), nameSocialNetworkIcon);
		Common.click(eleSocialNetworkIconByName);
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		List<String> lstWindow = DriverUtils.getWindowHandles();
		while (lstWindow.size() == 1 && endTime < Constants.SHORT_TIME) {
			lstWindow = DriverUtils.getWindowHandles();
			endTime = System.currentTimeMillis() - startTime;
		}
		// Wait 3s for new Social Network page loading
		Common.delay(Constants.SLEEP_TIME);
	}

	public void signInWithSocialNetworkAccount(Account account) {
		List<String> lstWindow = DriverUtils.getWindowHandles();
		String mainWindow = lstWindow.get(0);
		String socialNetworkWindow = lstWindow.get(1);
		DriverUtils.switchTo(socialNetworkWindow);
		if (Common.getTitlePage().contains("Error")) {
			DriverUtils.getWebDriver().close();
			DriverUtils.switchTo(mainWindow);
		} else {
			DriverUtils.getWebDriver().close();
			DriverUtils.switchTo(mainWindow);
		}
	}

	public String getSteakLoverRewardsPoint() {
		Common.waitForDisplayed(eleRewardsPoint);
		Common.scrollElementToCenterScreen(eleRewardsPoint);
		return eleRewardsPoint.getText();
	}

	public void clickCreateNewAddressButton() {
		Common.click(btnCreateNewAddress);
	}

	public void fillShippingAddressByRecipient(CustomerAddress shippingAddress, Recipient recipient) {

		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		Common.enter(txtShoppingName, recipient.getValue());
		Common.enter(txtFirstName, shippingAddress.firstName);
		Common.enter(txtLastName, shippingAddress.lastName);
		Common.enter(txtPhone, shippingAddress.phoneNumber);
		Common.enter(txtCompanyName, shippingAddress.companyName);
		Common.enter(txtAddress1, shippingAddress.streetAddress1);
		Common.enter(txtAddress2, shippingAddress.streetAddress2);
 		Common.enter(txtCity, shippingAddress.city);
		Common.select(cbbState, shippingAddress.state);
		Common.select(cbbCountry, shippingAddress.country);
		Common.enter(txtZip, shippingAddress.zipCode);
	}

	public String[] getShippingAddressByRecipient(Recipient recipient) {
		Element eleInformationRecipientShippingAddress = new Element(interfaces.get("eleInformationRecipientShippingAddress"), recipient.getValue());
		Common.waitForDisplayed(eleInformationRecipientShippingAddress);
		String[] lstArs = eleInformationRecipientShippingAddress.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public void deleteAddressByRecipientName(Recipient recipient) {
		Button btnDeleteAddress = new Button(interfaces.get("btnDeleteAddressByRecipientName"), recipient.getValue());
		Button btnDeleteAddressBook = new Button(interfaces.get("btnDeleteAddressBook"));
		Common.click(btnDeleteAddress);
		Common.waitForPageLoad();
		Common.click(btnDeleteAddressBook);
	}

	public boolean isRecipientShippingAddressDisplayed(Recipient recipient) {
		Element lblRecipientShippingAddress = new Element(interfaces.get("lblRecipientShippingAddress"), recipient.getValue());
		return lblRecipientShippingAddress.isDisplayed();
	}

	public void createNewAccount(String email, String password, String confirmPassword, boolean joinSteakloverRewards) {
		Common.enter(txtEmailAddress, email);
		Common.enter(txtPassword, password);
		Common.enter(txtConfirmPassword, confirmPassword);
		if (!joinSteakloverRewards) {
			Common.uncheck(cbJoinReward);
		}
		Common.click(btnJoin);
		Common.waitForPageLoad();
	}

	public String getErrorMessageByCreateAccountField(String field) {
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), field);
		Common.waitForDisplayed(eleErrorMessageByField);
		Common.focus(eleErrorMessageByField);
		if (eleErrorMessageByField.isDisplayed()) {
			return eleErrorMessageByField.getText();
		} else
			return " ";
	}

	public void fillBillingAddressExceptField(CustomerAddress billingAddress, AddressFields field) {
		CustomerAddress tmpBillingAddress = billingAddress.clone();
		tmpBillingAddress.updateFieldValueBy(field, "");
		fillBillingAddress(tmpBillingAddress);
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
		String tmpField = field.getValue();
		if (field.equals(AddressFields.PHONE))
			tmpField = "Phone";
		eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), tmpField);
		if (eleErrorMessageByField.isDisplayed(Constants.SHORT_TIME)) {
			return Common.getText(eleErrorMessageByField);
		} else
			return "";
	}

	public void selectTypeAddress(String typeAddress) {
		Element radioBtnCompanyAddress = new Element(interfaces.get("radioBtnCompanyAddress"));
		Element radioBtnResidentialAddress = new Element(interfaces.get("radioBtnResidentialAddress"));
		if (typeAddress.equals("Company Address"))
			Common.click(radioBtnCompanyAddress);
		else if (typeAddress.equals("Residential Address"))
			Common.click(radioBtnResidentialAddress);
	}

	public String getAddressWarningMessage() {
		DriverUtils.delay(2);
		if (eleAddressWarning.isDisplayed(Constants.SHORT_TIME)) {
			return Common.getText(eleAddressWarning);
		} else
			return "";
	}

	public void fillShippingAddressExceptField(CustomerAddress shippingAddress, AddressFields field) {
		CustomerAddress tmpShippingAddress = shippingAddress.clone();
		tmpShippingAddress.updateFieldValueBy(field, "");
		fillBillingAddress(tmpShippingAddress);
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

	public String getErrorMsgInEmailPreferencesTab() {
		Common.waitForDisplayed(eleErrorMsgInEmailPreferencesTab);
		return Common.getText(eleErrorMsgInEmailPreferencesTab);
	}

	public void selectContactPreferencesOption() {
		selectAccountSettingOption(Constants.CONTACT_PREFERENCES);
	}

	public void fillEmailAddressInEmailPreferencesTab(String emailAddress) {
		Common.waitForDisplayed(txtEmailAddressInEmailPreferencesTab);
		Common.enter(txtEmailAddressInEmailPreferencesTab, emailAddress);
	}

	public void clickUpdatePreferencesButton() {
		Common.waitForDisplayed(btnUpdatePreferences);
		Common.click(btnUpdatePreferences, false);
		Common.waitForDOMChange();
	}

	public String getErrorMsgForPhoneTxtInScheduleYourCallTimesPopup() {
		Common.waitForDisplayed(eleErrorForPhoneTxtScheduleYourCallTimesPopup);
		return Common.getText(eleErrorForPhoneTxtScheduleYourCallTimesPopup);
	}

	public void clickScheduleYourCallTimesLink() {
		Common.click(linkScheduleMyCallTimes, false);
		Common.waitForDOMChange();
	}

	public String getWarningMsgWhenSelectingDoNotCallMeOption() {
		Common.waitForDisplayed(eleWarningDoNotCallMe);
		return Common.getText(eleWarningDoNotCallMe);
	}

	public void selectCallMeWithExlusiveOffers(String option) {
		CheckBox eleOptionsContactPreferences = new CheckBox(interfaces.get("eleOptionsContactPreferences"), option);
		Common.waitForDisplayed(eleOptionsContactPreferences);
		Common.click(eleOptionsContactPreferences, false);
	}

	public String getErrorMsgForPhoneTxtInPhoneNumberPreferencesTab() {
		Common.waitForDisplayed(eleErrorForPhoneTxtInPhonePreferencesTab);
		return Common.getText(eleErrorForPhoneTxtInPhonePreferencesTab);
	}

	public void fillPhoneNumberInPhonePreferencesTab(String phoneNumber) {
		Common.waitForDisplayed(txtPhoneInPhoneNumberPreferencesTab);
		Common.enter(txtPhoneInPhoneNumberPreferencesTab, phoneNumber);
	}

	public void clickUpdatePreferencesButtonInPhonePreferencesTab() {
		Common.waitForDisplayed(btnUpdatePhonePreferences);
		Common.click(btnUpdatePhonePreferences, false);
		Common.waitForDOMChange();
	}

	public void clickSubmitButtonInScheduleYourCallTimesPopup() {
		Common.click(btnSubmitInScheduleYourCallTimesPopup, false);
		Common.waitForDOMChange();
	}

	public String getErrorMessageInErrorBox() {
		Common.waitForDisplayed(eleErrorBox);
		return Common.getText(eleErrorBox);
	}

	public String getValidationConfirmEmailAddress() {
		Common.waitForDisplayed(eleValidationConfirmEmailAddress);
		return Common.getText(eleValidationConfirmEmailAddress);
	}

	public String getErrorEmailAddressWasNotChanged() {
		Common.waitForDisplayed(eleErrorEmailAddressWasNotChanged);
		return Common.getText(eleErrorEmailAddressWasNotChanged);
	}

	/**
	 * Only use for post condition
	 */
	public void deleteAccount(Account account) {
		try {
			DriverUtils.navigate(Constants.getRunningURL() + Constants.OMAHA_URL_MYACCOUNTPAGE);
			Common.waitForPageLoad();
			selectAccountSettingOption(Constants.DELETE_MY_ACCOUNT);
			fillPasswordToDeleteAccount(account.getPassword());
			clickYesDeleteMyInformation();
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * Get the name of the Monthly Free Food.
	 */
	public String getMonthlyFreeFoodName() {		
		return Common.getRewardSkuDescription(eleNameOfMonthlyFreeFood.getText());
	}

	public String getMinSteakloverRewardsPoint() {
		Common.waitForPageLoad();
		return Common.getText(eleMinPoint);
	}

	public Integer[] getListPointofRewardSKU() {
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

	public boolean isJoinSteakloverRewardsModalDisplayed() {
		return eleJoinSteakloverRewardsModal.isDisplayed();
	}

	public void joinInSteakloverRewardMembership() {
		Common.click(btnJoinToday, false);
	}

	public String getHeading1() {
		return Common.getText(eleHeading1);
	}

	public String getUserStatusInSLRPage() {
		Common.focus(eleUserStatusInSLRPage);
		String str = Common.getText(eleUserStatusInSLRPage);
		if (str.equalsIgnoreCase(""))  {
		  String atr = elegoldstatus.getAttribute("src");
		  if (atr.contains("slr_gold.svg")) str = Constants.USER_STATUS_GOLD;
		}
		return str;
	}

	public boolean isTheMonthlyFreeFoodDisplayed() {
		return eleNameOfMonthlyFreeFood.isDisplayed();
	}

	public boolean isUserStatusDisplayedCorrectly(String status) {
		switch (status) {
		case Constants.USER_STATUS_GOLD:
			String str = elegoldstatus.getAttribute("src");
			return str.contains("slr_gold.svg");					
		case Constants.USER_STATUS_SLR:
			return Common.getText(eleUserStatusInSLRSection).contains(Constants.USER_STATUS_SLR);
		default:
			System.out.println("Steaklover Rewards popup does not display with Regular Account");
			return false;
		}

	}

	@Override
	public boolean freeItemExists() { 
	  return eleFreeFoodItemPoints.isDisplayed();
	}
}
