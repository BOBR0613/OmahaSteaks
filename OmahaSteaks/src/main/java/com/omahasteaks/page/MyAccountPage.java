package com.omahasteaks.page;

import java.util.List;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.data.objects.CustomerAddress;

public interface MyAccountPage extends GeneralPage {

	/**
	 * click My Account link in home page after signed in with valid account. In My
	 * Account page, click Address Book icon to go Address Book page
	 */
	void goToMyAccountAddressBook();

	/**
	 * click Edit button in Recipient section to go Edit Address page of that
	 * recipient
	 */
	void goToEditAddressPageByRecipient(Recipient recipient);

	/**
	 * get list of address informations displays in Edit Address page
	 */
	CustomerAddress getAddressInEditAddressPage();

	/**
	 * delete all address exist in Address Book
	 */
	void cleanUpAddressBook();

	/**
	 * click Edit in Billing Address section to go Edit Billing Address page
	 */
	void goToEditBillingAddressPage();

	/**
	 * fill all information into all fields in Edit Billing Address page
	 */
	void fillBillingAddress(CustomerAddress billingAddress);

	/**
	 * get all information display in Billing Address section
	 */
	String[] getBillingAddress();

	/**
	 * get all information display at Myself in Shipping Address section
	 */
	String[] getMyselfShippingAddress();

	/**
	 * create new account with email and password
	 */
	void createNewAccount(String email, String password, boolean joinSteakloverRewards);

	/**
	 * create new account with email, password and confirm password
	 */
	void createNewAccount(String email, String password, String confirmPassword, boolean joinSteakloverRewards);

	/**
	 * create new account
	 */
	void createNewAccount(Account account);

	void editBookAdress(CustomerAddress billingAddress);

	/**
	 * @param option
	 *            value should be as below <br>
	 *            1. Address Book <br>
	 *            2. Contact Preferences <br>
	 *            3. Change Email Address <br>
	 *            4. Change Password <br>
	 *            5. Delete My Account <br>
	 */
	void selectAccountSettingOption(String option);
	/**
	 * @param option
	 *            value should be as below <br>
	 *            1. Gourmet Favorites <br>
	 *            2. Steaklover Rewards 
	 */
	void selectMyOmahaSteaksOption(String steakloverRewards);

	/**
	 * get Welcome Text in My Account page after signed in with valid account
	 * successful
	 */
	String getWelcomeText();

	/**
	 * In My Account page, click Delete My Account in Account Settings section. Fill
	 * password into Password field
	 */
	void fillPasswordToDeleteAccount(String password);

	/**
	 * After filled password, click Yes Delete My Information button to delete
	 * account
	 */
	void clickYesDeleteMyInformation();

	/**
	 * get Error message display in My Account when an error occurs
	 */
	String getErrorMessage();

	/**
	 * After filling value into the fields in Edit Billing Address page and Edit
	 * Shipping Address page, click Update button to update the information
	 */
	void clickUpdateButton();

	/**
	 * After filling value into the fields in Edit Billing Address page and Edit
	 * Shipping Address page, click Cancel button not to update the information
	 */
	void clickCancelButton();

	/**
	 * fill all information into all fields in Edit Shipping Address page
	 */
	void fillShippingAddress(CustomerAddress shippingAddress);

	/**
	 * fill email into Email Address field when clicking Change Email Address option
	 * in Account Setting section
	 */
	void changeEmailAddress(String email);

	/**
	 * fill email and confirm email into Email Address, and Confirm Email Address
	 * fields when clicking Change Email Address option in Account Setting section
	 */
	void changeEmailAddress(String newEmail, String confirmEmail);

	/**
	 * get Welcome membership text displays in My Account page when signed in with
	 * valid account
	 */
	String getWelcomeMembershipText();

	/**
	 * fill new email into email address text box in Email Preferences pop up when
	 * clicking Contact Preferences option in Account Setting section and clicking
	 * Update Preferences button to update
	 */
	List<String> updateRandomEmailPreferences();

	/**
	 * fill new phone number into phone number text box in Phone Preferences pop up
	 * when clicking Contact Preferences option in Account Setting section and
	 * clicking Update Preferences button to update
	 */
	String updateRandomPhonePreferences(String phoneNumber);

	/**
	 * verify Contact Preferences option check box is checked
	 */
	boolean isCheckboxContactPreferencesOptionByValueChecked(String value);

	/**
	 * click Phone Preferences tab in Contact Preferences pop up
	 */
	void clickPhonePreferencesTab();

	/**
	 * click social network icon display to sign in
	 */
	void clickSocialNetworkIcon(String nameSocialNetworkIcon);

	/**
	 * sign in with social network account when clicking social network icon
	 */
	void signInWithSocialNetworkAccount(Account account);

	/**
	 * get Steak Lover Rewards point when clicking Steaklover Rewards option in My
	 * Omaha Steaks section
	 */
	String getSteakLoverRewardsPoint();

	/**
	 * click create new address button to add new address into Shipping Address
	 * section
	 */
	void clickCreateNewAddressButton();

	/**
	 * fill Shipping Address for the recipient
	 */
	void fillShippingAddressByRecipient(CustomerAddress shippingAddress, Recipient recipient);

	/**
	 * get all Shipping Address information by the recipient
	 */
	String[] getShippingAddressByRecipient(Recipient recipient);

	/**
	 * click delete button in Recipient section which want to delete
	 */
	void deleteAddressByRecipientName(Recipient recipient);

	/**
	 * verify the Shipping Address information of Recipient displays
	 */
	boolean isRecipientShippingAddressDisplayed(Recipient recipient);

	/**
	 * get Error message display
	 */
	String getErrorMessageByCreateAccountField(String field);

	/**
	 * fill Billing Address informations into all fields
	 */
	void fillBillingAddressExceptField(CustomerAddress billingAddress, AddressFields field);

	/**
	 * get error message by field displays when an error occurs
	 */
	String getErrorMessageByField(AddressFields field);

	/**
	 * select type address is Company address or Residential Address
	 */
	void selectTypeAddress(String typeAddress);

	/**
	 * get address warning message display when zip code, state, city and Street
	 * does not match
	 */
	String getAddressWarningMessage();

	/**
	 * fill Billing Address informations into all fields
	 */
	void fillShippingAddressExceptField(CustomerAddress shippingAddress, AddressFields field);

	/**
	 * get error message display in Email Preferences tab
	 */
	String getErrorMsgInEmailPreferencesTab();

	/**
	 * get error message display in Email Preferences tab
	 */
	void selectContactPreferencesOption();

	/**
	 * fill Email address into email address text box in Email Preferences tab
	 */
	void fillEmailAddressInEmailPreferencesTab(String emailAddress);

	/**
	 * click Update Preferences Button in Email Preferences tab
	 */
	void clickUpdatePreferencesButton();

	/**
	 * get error message display in Phone Preferences tab
	 */
	String getErrorMsgForPhoneTxtInScheduleYourCallTimesPopup();

	/**
	 * click Schedule Your Call Times link in Phone Preferences tab
	 */
	void clickScheduleYourCallTimesLink();

	/**
	 * get warning message when selecting Do not call me option in Phone Preferences
	 * tab
	 */
	String getWarningMsgWhenSelectingDoNotCallMeOption();

	/**
	 * select Call Me With Exclusive Offers options in Phone Preferences tab
	 */
	void selectCallMeWithExlusiveOffers(String option);

	/**
	 * get Error message for Phone number field in Phone Preferences tab
	 */
	String getErrorMsgForPhoneTxtInPhoneNumberPreferencesTab();

	/**
	 * fill phone number into Phone number field in Phone Preferences tab
	 */
	void fillPhoneNumberInPhonePreferencesTab(String phoneNumber);

	/**
	 * click Update Preferences button in Phone Preferences tab
	 */
	void clickUpdatePreferencesButtonInPhonePreferencesTab();

	/**
	 * click Submit button in Schedule Your Call Times pop up
	 */
	void clickSubmitButtonInScheduleYourCallTimesPopup();

	/**
	 * get Error message in error box when logging in with invalid data
	 */
	String getErrorMessageInErrorBox();

	/**
	 * get Error message when filling new email address and confirm email address
	 * are not the same
	 */
	String getValidationConfirmEmailAddress();

	/**
	 * get Error message when edits email address with email which is the same as
	 * the current email
	 */
	String getErrorEmailAddressWasNotChanged();

	/**
	 * Only use for Post Condition
	 */
	void deleteAccount(Account account);

	/**
	 * Get the name of monthly SKU which has
	 */
	public String getMonthlyFreeFoodName();

	/**
	 * Get the mininum Steaklover Rewards point
	 */
	String getMinSteakloverRewardsPoint();

	/**
	 * Verify Join Steaklover Reward pop up is displayed after lick sign up link in
	 * Shopping Cart page
	 */
	boolean isJoinSteakloverRewardsModalDisplayed();

	/**
	 * Get list points of reward food
	 */
	Integer[] getListPointofRewardSKU();

	/**
	 * Get heading 1
	 */
	public String getHeading1();

	/**
	 * Regular user join to steaklover membership
	 */
	void joinInSteakloverRewardMembership();

	String getUserStatusInSLRPage();

	boolean isTheMonthlyFreeFoodDisplayed();

	boolean isUserStatusDisplayedCorrectly(String status);

	boolean freeItemExists();

	boolean isRenewGoldMbrModalShown();

	void clickRewewMembershipButton();

}
