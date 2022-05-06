package com.omahasteaks.utils.common;

import java.util.HashMap;
import java.util.Map;

import com.omahasteaks.data.enums.AddressFields;

public class Messages {

	// ================================================================================
	// For both sites
	// ================================================================================

	@SuppressWarnings("serial")
	public static Map<String, String> SPECIAL_CART_BONUS_SAVINGS_NOT_DELIVER_ERROR_MESSAGE = new HashMap<String, String>() {
		{
			put("omk", "The sack for %s does not qualify for the item you are attempting to purchase. "
					+ "Wine products, live lobster and gift cards do not qualify for the purchase of bonus savings items.");

			//put("wps", "The sack for %s does not qualify for the item you are attempting to purchase. Wine products and gift cards do not qualify for the purchase of bonus savings items.");
		}
	};

	@SuppressWarnings("serial")
	public static Map<String, String> INVALID_CREDIT_CARD_NUMBER_ERROR_MESSAGE = new HashMap<String, String>() {
		{
			put("omk", "Error Credit Card - Invalid credit card type - please double check your entry.");
			//put("wps", "Error Credit Card - Invalid credit card type--please double check your entry.");
		}
	};

	@SuppressWarnings("serial")
	public static Map<String, String> NOTE_OF_SKU_MESSAGE_DESKTOP = new HashMap<String, String>() {
		{
			put("omk", "This item will ship and bill separately from your food.");
			//put("wps", "This item will ship separately from your food.");
		}
	};

	// ================================================================================
	// Test Data
	// ================================================================================
	public static final String FIRSTNAME_REQUIRED_MESSAGE = "Please -- it's a required field";
	public static final String LASTNAME_REQUIRED_MESSAGE = "Ok -- we can't deliver without a last name - it's a required field.";
	public static final String ADDRESS1_REQUIRED_MESSAGE = "Address for delivery -- it's a must! Please provide.";
	public static final String CITY_REQUIRED_MESSAGE = "City, State and Zip code... all required fields.";
	public static final String STATE_REQUIRED_MESSAGE = "City, State and Zip code... all required fields.";
	public static final String ZIPCODE_REQUIRED_MESSAGE = "City, State and Zip code... all required fields.";
	public static final String PHONE_REQUIRED_MESSAGE = "Just in case we need to speak with you about your order or some fantastic offers, please provide the phone number you'd like us to use.";
	public static final String EMAIL_ADDRESS_REQUIRED_MESSAGE = "The email address you supplied appears to be invalid. Could you please enter it again?";
	public static final String EMAIL_ADDRESS_NOT_MATCH_MESSAGE = "Uh-oh, the confirming email doesn't match, could you try again please.";
	public static final String BIRTHDAY_REQUIRED_MESSAGE = "The birth date you specified is not a valid date.";
	public static final String BIRTHDAY_UNDER_21_MESSAGE = "Your cart contains items that may only be purchased by adults over 21 years of age. Please check the birth date you supplied, or remove the age-restricted items from your cart.";
	public static final String ACCEPTANCE_REQUIRED_MESSAGE = "Please confirm that you agree to the Terms of Use and Privacy Policy by checking the box below.";
	public static final String EMPTY_CART_MESSAGE = "Your Cart Is Currently Empty";
	public static final String INVALID_PHONE_MESSAGE = "The phone number you specified is invalid. Please specify a phone number with the area code.";
	public static final String INVALID_CITY_STATE_ZIP = "Invalid city/state/zip information";
	public static final String ZIPCODE_NOT_MATCH = "Zip code does not match USPS database (95129)";
	public static final String INVALID_ADDRESS = "Insufficient address information";
	public static final String ADDRESS_NOT_FOUND = "Address not found on street";
	public static final String STREET_NOT_FOUND = "Street not found in city";
	public static final String CITY_NOT_MATCH = "City does not match USPS database (San Jose)";
	public static final String STATE_NOT_MATCH = "State does not match USPS database (CA)";
	public static final String INVALID_CREDIT_CARD = "Invalid credit card type - please double check your entry.";
	public static final String INVALID_CREDIT_CARD_DATE = "Oops, that expiration date is not valid. Please verify your card's expiration date and reselect from the drop down menu.";
	public static final String INVALID_RECIPIENT_MESSAGE = "Please Enter Your Recipient's Name";
	public static final String INVALID_RECIPIENT_MESSAGE2 = "Please Select Your Recipient";
	public static final String CHARACTER_LIMITATION_RECIPIENT_MESSAGE_ON_POPUP = "Recipient name must be 15 characters or less";
	public static final String CHARACTER_LIMITATION_RECIPIENT_MESSAGE = "Recipient name is too long: Recipient name must be 15 characters or less";
	public static final String UNCHECK_ALL_WARNING_MESSAGE_IN_SPECIAL_CART = "Please select at least one item from above.";
	public static final String WINE_IN_CART_MESSAGE = "Adult signature required (Age 21+) for delivery. We will make 3 delivery attempts on consecutive business days.";
	public static final String WINE_RESTRICTION_MESSAGE = "We can not ship wine to: AK, AL, DE, HI, KY, ME, MS, MT, PR, SD, UT, VI";
	public static final String COMPANY_NAME_REQUIRED_MESSAGE = "A company name is required.";
	// ================================================================================
	// AccountPage2SC Page
	// ================================================================================
	public static final String EMPTY_EMAIL_ERROR_MESSAGE = "Please enter your email address";
	public static final String INVALID_PASSWORD_ERROR_MESSAGE = "Must include at least: 8 Characters 1 Letter 1 Number";
	public static final String EMPTY_EMAIL_ERROR_MESSAGE_1 = "Please fill out this field.";
	public static final String INVALID_PASSWORD_ERROR_MESSAGE_1 = "Sorry, the passwords do not match. Please enter them again."; 
	public static final String INVALID_PASSWORD_ERROR_MESSAGE_2 = "Password must contain at least 8 characters.";
	public static final String INVALID_PASSWORD_ERROR_MESSAGE_3 = "Password must contain at least 1 letter.";
	public static final String INVALID_PASSWORD_ERROR_MESSAGE_4 = "Password must contain at least 1 number.";

	public static final String USED_EMAIL_ADDRESS_MESSAGE = "Already in use. Please try again.";

	public static final String EMAIL_EMPTY_MESSAGE = "Please Enter Your Email Address";
	public static final String INVALID_EMAIL_ADDRESS_ERROR_MESSAGE = "Please Enter A Valid Email Address";
	public static final String INVALID_PHONE_ERROR_MESSAGE = "Please Enter A Valid Phone Number";
	public static final String PHONE_EMPTY_MESSAGE = "Please Enter Your Phone Number";
	public static final String DO_NOTT_CALL_ME_MESSAGE = "Note: If you are a member of Omaha Steaks Personal Shopper, selecting this option will opt you out of the program.";
	public static final String NO_ACCOUNT_MESSAGE = "Incorrect login You have entered an invalid username or password. Please try again or reset your password.";
	public static final String VALIDATION_INVALID_EMAIL_FORMAT = "Please include an '@' in the email address. \"%s\" is missing an '@'.";
	public static final String INCORRECT_PASSWORD = "Incorrect login You have entered an invalid username or password. Please try again or reset your password.";
	public static final String CONFIRM_EMAIL_ADDRESS_VALIDATION = "* Confirm Email Address does not match.";
	public static final String SORRY_INVALID_EMAIL_MESSAGE = "Sorry, this is an invalid email address. Please try again.";
	public static final String EMAIL_WAS_NOT_CHANGED = "Email address was not changed.";

	// ================================================================================
	// Sale Page
	// ================================================================================
	public static final String I_AGREE_CHECKBOX_ERROR_MESSAGE = "Please Accept Our Terms Of Use";

	// ================================================================================
	// Shipping Page 2SC
	// ================================================================================
	public static final String SFIRSTNAME_EMPTY_MESSAGE = "Please Enter A First Name";
	public static final String SLASTNAME_EMPTY_MESSAGE = "Please Enter A Last Name";
	public static final String SADDRESS_EMPTY_MESSAGE = "Please Enter A Street Address";
	public static final String SZIPCODE_EMPTY_MESSAGE = "Please Enter A Zip Code";
	public static final String SSTATE_EMPTY_MESSAGE = "Please Select A State";
	public static final String SCITY_EMPTY_MESSAGE = "Please Enter A City";
	public static final String LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE = "Last Name Must Be Two Or More Characters";
	public static final String SADDRESS_SHIP_TO_PO_BOX = "We Cannot Ship Food To P.O. Boxes";
	public static final String SADDRESS_PO_BOX_DELIVERY_ERROR_MESSAGE = "Error Address - Delivery to PO Boxes is not supported.";
	public static final String SHIPPING_WINE_RESTRICTION_MESSAGE = "Unfortunately we are unable to ship the following items to %s.";
	public static final String EMPTY_EMAIL_ADDRESS_EGIFT_CARD_ERROR_MESSAGE = "Error Email Address - Required for sending Electronic Gift Cards. Thank you!";
	public static final String SINVALID_EMAIL_ADDRESS_ERROR_MESSAGE = "Please Enter A Valid Email Address";
	public static final String WINVALID_EMAIL_ADDRESS_ERROR_MESSAGE = "Warning! The email address you entered is invalid. Please review before proceeding.";
	public static final String SINVALID_PHONE_ERROR_MESSAGE = "Please Enter A Valid Phone Number";

	// ================================================================================
	// Shopping Cart Page
	// ================================================================================

	public static final String VOUCHER_NUMBER_EMPTY_MESSAGE = "Please Enter A Valid Number";

	// ================================================================================
	// Payment and Review Page 2SC
	// ================================================================================
	public static final String BFIRSTNAME_EMPTY_MESSAGE = "Please Enter A First Name";
	public static final String BLASTNAME_EMPTY_MESSAGE = "Please Enter A Last Name";
	public static final String BADDRESS_EMPTY_MESSAGE = "Please Enter An Address";
	public static final String BZIPCODE_EMPTY_MESSAGE = "Please Enter A Zip Code";
	public static final String BSTATE_EMPTY_MESSAGE = "Please Select Your State";
	public static final String BCITY_EMPTY_MESSAGE = "Please Enter A City";
	public static final String BPHONE_EMPTY_MESSAGE = "Please Enter A Phone Number";
	public static final String BEMAIL_EMPTY_MESSAGE = "Please Enter Your Email Address";
	public static final String BFIRSTNAME_EMPTY_MESSAGE2 = "Please Enter Your First Name";
	public static final String BLASTNAME_EMPTY_MESSAGE2 = "Please Enter Your Last Name";
	public static final String BADDRESS_EMPTY_MESSAGE2 = "Please Enter Your Street Address";
	public static final String BZIPCODE_EMPTY_MESSAGE2 = "Please Enter Your Zip Code";
	public static final String BSTATE_EMPTY_MESSAGE2 = "Please Select Your State";
	public static final String BCITY_EMPTY_MESSAGE2 = "Please Enter Your City";
	public static final String BPHONE_EMPTY_MESSAGE2 = "Please Enter Your Phone Number";
	public static final String BEMAIL_EMPTY_MESSAGE2 = "Please Enter Your Email Address";
	public static final String PCARD_EMPTY_MESSAGE = "Please Enter Your Credit Card";
	public static final String PCARD_DATE_EMPTY_MESSAGE = "Please Enter Your Expiration Date";
	public static final String INVALID_CREDIT_CARD_EXPIRATION_DATE_ERROR_MESSAGE = "Enter a Valid Date MM / YY";
	public static final String BINVALID_PHONE_ERROR_MESSAGE = "Please Enter A Valid Phone Number";
	public static final String BINVALID_PHONE_ERROR_MESSAGE_IN_MODAL = "Error The phone number you specified is invalid. Please specify a phone number with the area code.";
	public static final String BPINVALID_EMAIL_ERROR_MESSAGE = "Please Enter A Valid Email Address";
	public static final String BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_1 = "Warning! The zip code entered does not match USPS database. Please review before proceeding.";
	public static final String BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_2 = "Warning! Invalid city/state/zip information. Please review before proceeding.";
	public static final String BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_3 = "Warning! Address not found on street. Please review before proceeding.";
	public static final String INVALID_CREDIT_CARD_ERROR_MESSAGE_1 = "Error Credit Card Number - Uh-oh... too many or too few credit card numbers. Please re-enter your card number.";
	public static final String NOTE_OF_SKU_MESSAGE_MOBILE = "Some items will ship separately from your food.";

	public static final String NOTE_OF_SKU_MESSAGE = getNoteOfSkuMessageByPlatform();
	public static final String THANKSGIVING_ESTIMATED_DELIVERY = "Arrives in time for the holiday";
	public static final String INCENTIVE_OFFER_TERMS_AND_CONDITIONS_MSG = "You will receive one $20 E-Reward Card code per shipment when placing an order of $69 or more between 11/05/18 and 12/09/18 and selecting \"Standard Shipping\" or \"Christmas Delivery\" at checkout. Your E-Reward Card code(s) will be emailed to you within 48 hours of your order shipping, valid email address required. Omaha Steaks Gift Cards, E-Gift Cards, Wine, Gift Baskets, Grills, Fresh Vegetables and Live Lobsters do not count toward the $69 minimum order requirement for the earned reward card. When redeeming online there is a limit of one E-Reward Card code per order of $69 or more. Reward cards may not be used on any order that also redeems Steaklover Rewards points. This offer cannot be combined with any other offer, is redeemable online only and is not valid on pending or prior purchases. E-Reward Card Expiration Date: 2/15/19.";
	public static final String GIFT_OR_REWARD_CARD_ERROR_MESSAGE = "Error Gift/Reward card not allowed - We're sorry, you may not use gift cards, e-gift cards, or rewards cards toward the purchase of gift cards or e-gift cards.";

	// ================================================================================
	// Confirmation Page 2SC
	// ================================================================================
	public static final String THANK_YOU_MESSAGE = "Thank you for your order! It is being prepared to ship";

	// ================================================================================
	// Win Free Steaks Page
	// ================================================================================
	public static final String WFS_EMAIL_EMPTY_MESSAGE = "Please Enter Your Email Address";
	public static final String WFS_FIRSTNAME_EMPTY_MESSAGE = "Please Enter Your First Name";
	public static final String WFS_LASTNAME_EMPTY_MESSAGE = "Please Enter Your Last Name";
	public static final String WFS_STATE_EMPTY_MESSAGE = "Please Select Your State";
	public static final String WFS_CITY_EMPTY_MESSAGE = "Please Enter Your City";
	public static final String WFS_PHONE_EMPTY_MESSAGE = "Please Enter Your Phone Number";
	public static final String WFS_INVALID_PHONE_ERROR_MESSAGE = "Please Enter A Valid Phone Number";
	public static final String WFS_INVALID_EMAIL_ERROR_MESSAGE = "Please Enter A Valid Email Address";
	public static final String WFS_I_AGREE_CHECKBOX_ERROR_MESSAGE = "Please Accept Our Terms And Conditions";

	// ================================================================================
	// Stores Page
	// ================================================================================
	public static final String INVALID_ZIP_CODE_ERROR_MESSAGE = "Invalid Address: The zip code or address you entered does not appear to be valid.";
	public static final String INVALID_FORMAT_ERROR_MESSAGE = "Application Server Error: Unexpected communication error occured. Please try again later.";

	// ================================================================================
	// Support method
	// ================================================================================
	public static final String getRequiredMessageByField(AddressFields field) {
		switch (field) {
		case FIRST_NAME:
			return FIRSTNAME_REQUIRED_MESSAGE;
		case LAST_NAME:
			return LASTNAME_REQUIRED_MESSAGE;
		case COMPANY_NAME:
			return COMPANY_NAME_REQUIRED_MESSAGE;
		case ADDRESS1:
			return ADDRESS1_REQUIRED_MESSAGE;
		case ADDRESS2:
			return "";
		case CITY:
			return CITY_REQUIRED_MESSAGE;
		case STATE:
			return STATE_REQUIRED_MESSAGE;
		case ZIP_CODE:
			return ZIPCODE_REQUIRED_MESSAGE;
		case PHONE:
			return PHONE_REQUIRED_MESSAGE;
		case EMAIL:
			return EMAIL_ADDRESS_REQUIRED_MESSAGE;
		case CONFIRM_EMAIL:
			return EMAIL_ADDRESS_NOT_MATCH_MESSAGE;
		case BIRTHDAY:
			return BIRTHDAY_REQUIRED_MESSAGE;
		case BIRTHDAY_UNDER_21:
			return BIRTHDAY_UNDER_21_MESSAGE;
		case ACCEPTANCE:
			return ACCEPTANCE_REQUIRED_MESSAGE;
		default:
			return "";
		}
	}

	public static final String getInvalidMessageByField(AddressFields field) {
		switch (field) {
		case FIRST_NAME:
			return "";
		case LAST_NAME:
			return "";
		case COMPANY_NAME:
			return "";
		case ADDRESS1:
			return SADDRESS_SHIP_TO_PO_BOX;
		case ADDRESS2:
			return "";
		case CITY:
			return "";
		case STATE:
			return "";
		case ZIP_CODE:
			return "";
		case PHONE:
			return INVALID_PHONE_MESSAGE;
		case EMAIL:
			return "";
		case CONFIRM_EMAIL:
			return "";
		case BIRTHDAY:
			return "";
		case BIRTHDAY_UNDER_21:
			return "";
		case ACCEPTANCE:
			return "";
		case CREDIT_CARD:
			return INVALID_CREDIT_CARD;
		case CREDIT_CARD_DATE:
			return INVALID_CREDIT_CARD_DATE;
		default:
			return "";
		}
	}

	public static final String getEmptyErrorMessageByShippingField(AddressFields field) {
		switch (field) {
		case FIRST_NAME:
			return SFIRSTNAME_EMPTY_MESSAGE;
		case LAST_NAME:
			return SLASTNAME_EMPTY_MESSAGE;
		case STREET_ADDRESS:
			return SADDRESS_EMPTY_MESSAGE;
		case CITY:
			return SCITY_EMPTY_MESSAGE;
		case STATE:
			return SSTATE_EMPTY_MESSAGE;
		case ZIP_CODE:
			return SZIPCODE_EMPTY_MESSAGE;
		case EMAIL:
			return "";
		default:
			return "";
		}
	}

	public static final String getEmptyErrorMessageInPaymentAndReviewPageInModal(AddressFields field) {
		switch (field) {
		case FIRST_NAME:
			return BFIRSTNAME_EMPTY_MESSAGE;
		case LAST_NAME:
			return BLASTNAME_EMPTY_MESSAGE;
		case STREET_ADDRESS:
			return BADDRESS_EMPTY_MESSAGE;
		case CITY:
			return BCITY_EMPTY_MESSAGE;
		case STATE:
			return BSTATE_EMPTY_MESSAGE;
		case ZIP_CODE:
			return BZIPCODE_EMPTY_MESSAGE;
		case PHONE:
			return BPHONE_EMPTY_MESSAGE;
		case EMAIL:
			return BEMAIL_EMPTY_MESSAGE;
		case CREDIT_CARD:
			return PCARD_EMPTY_MESSAGE;
		case CREDIT_CARD_DATE:
			return PCARD_DATE_EMPTY_MESSAGE;
		default:
			return "";
		}
	}

	public static final String getEmptyErrorMessageInPaymentAndReviewPage(AddressFields field) {
		switch (field) {
		case FIRST_NAME:
			return BFIRSTNAME_EMPTY_MESSAGE2;
		case LAST_NAME:
			return BLASTNAME_EMPTY_MESSAGE2;
		case STREET_ADDRESS:
			return BADDRESS_EMPTY_MESSAGE2;
		case CITY:
			return BCITY_EMPTY_MESSAGE2;
		case STATE:
			return BSTATE_EMPTY_MESSAGE2;
		case ZIP_CODE:
			return BZIPCODE_EMPTY_MESSAGE2;
		case PHONE:
			return BPHONE_EMPTY_MESSAGE2;
		case EMAIL:
			return BEMAIL_EMPTY_MESSAGE2;
		case CREDIT_CARD:
			return PCARD_EMPTY_MESSAGE;
		case CREDIT_CARD_DATE:
			return PCARD_DATE_EMPTY_MESSAGE;
		default:
			return "";
		}
	}

	public static String getNoteOfSkuMessageByPlatform() {
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			return NOTE_OF_SKU_MESSAGE_DESKTOP.get(Constants.SITE);
		else
			return NOTE_OF_SKU_MESSAGE_MOBILE;
	}

	public static final String getEmptyErrorMessageInWinFreeSteaksPage(AddressFields field) {
		switch (field) {
		case FIRST_NAME:
			return WFS_FIRSTNAME_EMPTY_MESSAGE;
		case LAST_NAME:
			return WFS_LASTNAME_EMPTY_MESSAGE;
		case CITY:
			return WFS_CITY_EMPTY_MESSAGE;
		case STATE:
			return WFS_STATE_EMPTY_MESSAGE;
		case PHONE:
			return WFS_PHONE_EMPTY_MESSAGE;
		case EMAIL:
			return WFS_EMAIL_EMPTY_MESSAGE;
		default:
			return "";
		}
	}

	// ================================================================================
	// Collection Center
	// ================================================================================
	public static final String COLLECTION_CENTER_EMPTY_EMAIL_ERROR_MESSAGE = "Please fill out this field.";

	public static final String INVALID_REQUESTED_DATE_MESSAGE = "Requested Date invalid format";
	public static final String REQUESTED_DATE_MORE_THAN_365_DAYS_MESSAGE = "Requested Date You can't delay your shipment more than 365 days.";
	public static final String REQUESTED_DATE_BEFORE_TODAY_MESSAGE = "Requested Date Can't arrange a shipment date in the past";

	// ================================================================================
	// Rewards Collection Center
	// ================================================================================

	public static final String YOUR_CART_EMPTY_MESSAGE = "Your Cart Is Empty!";
	
	public static final String INVALID_REWARD_CODE_MESSAGE_1 ="More certificates are required.";
	public static final String INVALID_REWARD_CODE_MESSAGE_2 ="Please call Customer Service for assistance.";
	public static final String INVALID_REWARD_CODE_MESSAGE_3 ="More certificates are required.";
	public static final String INVALID_REWARD_CODE_MESSAGE_4 ="Please call for assistance.";
	
	public static final String INVALID_EMAIL_ADRESS_MESSAGE = "Valid Email Address Required";
	public static final String INAVLID_RECIPIENT_NAME_MESSAGE="Please let us know who you're sending this to... it's a required field when sending a gift.";

	// ================================================================================
	// Steaklover Rewards Membership Gold
	// ================================================================================

	public static final String SLR_GOLD_FREE_SHIP_MSG = "This cart qualifies for FREE SHIPPING";

	public static final String RWM0031 = "Reward card RWTEST20 not allowed with SLR Gold Membership purchase.";
}
