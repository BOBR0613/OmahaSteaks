package com.omahasteaks.utils.common;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.logigear.helper.JsonHelper;
import com.omahasteaks.data.ListAccounts;
import com.omahasteaks.utils.helper.PropertiesHelper;

public class Constants {
 
	public static String SITE = "omk";

	public static final String OMAHA_BASE = PropertiesHelper.getPropValue("profile.url.base");
	public static final String OMAHA_URL = PropertiesHelper.getPropValue("profile.url.omastk");
	//public static final String WPS_URL = PropertiesHelper.getPropValue("profile.url.wps");
	public static final String COLLECTTION_CENTER_URL = PropertiesHelper.getPropValue("profile.url.cc");
	public static final String REWARD_COLLECTTION_URL = PropertiesHelper.getPropValue("profile.url.reward.collection");
	public static final String SLR_MONTHLY_FREE_FOOD = "Premium Ground Beef";
	public static AS400DB DB = new AS400DB();           
 
	public static String getRunningURL() {
		return runningURL.get(SITE);
	}

	@SuppressWarnings("serial")
	public static Map<String, String> runningURL = new HashMap<String, String>() {
		{
			//put("wps", WPS_URL);
			put("omk", OMAHA_URL);
			put(null, OMAHA_URL);
		}
	};
	public static String URL_CHECKOUT_DOMAIN = "/servlet/OnlineShopping";
	public static String URL_SHOPPING_CART = "/shop/Shopping-Cart";
	public static String URL_SITEMAP = "/info/Sitemap";
	public static String URL_PRIVACY_POLICY = "/info/Privacy-Policy";
	public static String URL_TERMS_OF_USE = "/info/Terms-Of-Use";
	public static String URL_HELP_CENTER = "/info/Customer-Service";
	public static String URL_STORES = "/info/Stores";
	public static String URL_RECIPE_CENTER = "/recipes/";
	public static String URL_CONTACT_PREFERENCES = "/servlet/Mail?ACTN=emoff&EMAR=1";

	public static final String OMAHA_EMAIL = PropertiesHelper.getPropValue("profile.user.email");
	public static final String OMAHA_PASSWORD = PropertiesHelper.getPropValue("profile.user.password");
	public static String OMAHA_URL_MYACCOUNTPAGE = PropertiesHelper.getPropValue("profile.url.myaccountpage");
	public static String OMAHA_URL_GIFT_CERTIFICATES = PropertiesHelper.getPropValue("profile.url.gift.certificates");
	public static final String OMAHA_URL_2STEP = OMAHA_URL + PropertiesHelper.getPropValue("profile.url.twostep");
	public static final String OMAHA_URL_SPLASHPAGE_DISABLE = OMAHA_URL + PropertiesHelper.getPropValue("profile.url.splashpage.disable");
	public static final String OMAHA_URL_SPLASHPAGE_ENABLE = OMAHA_URL + PropertiesHelper.getPropValue("profile.url.splashpage.enable");
	public static final String MAVEN_HOME_MAC = PropertiesHelper.getPropValue("profile.maven.home.mac");

	public static final String GIFT_NUMBER = "GCTEST99999";
	public static final String CREDIT_CARD_NUMBER = "4111111111111111";
	public static final String INVALID_CREDIT_CARD_NUMBER = "1111111111111111";
	public static final String CREDIT_CARD_NUMBER_FEWER_THAN_16_DIGITS = "4111111111";
	public static final String CREDIT_CARD_NUMBER_2 = "5555555555554444";
	public static final String EMPTY_MESSAGE = "Your Cart Is Currently Empty";
	public static final String CANDIDATE_LETTER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final String CANDIDATE_NUMBER_CHARS = "0123456789";
	public static final String CANDIDATE_SPECIAL_CHARS = "#";
	public static final String CANDIDATE_FULL_CHARS = Constants.CANDIDATE_LETTER_CHARS.concat(Constants.CANDIDATE_NUMBER_CHARS).concat(Constants.CANDIDATE_SPECIAL_CHARS);
	public static final String EMPTY_STRING = "";
	public static final String OMAHA_EMAIL_SOCIAL_NETWORK = "omahasteakjohnqtester@yahoo.com";
	public static final String OMAHA_PASSWORD_SOCIAL_NETWORK = "3Teak0maha2016";
	public static final String SIGN_IN_PAGE_TITLE = "Omaha Steaks Member Benefits - My Account";


	// ================================================================================
	// Action hub
	// ================================================================================
	public static final JsonObject CLICK_HUB = JsonHelper.getJsonObject("src/test/resources/actionHub/click.json");

	// ================================================================================
	// Browser variables
	// ================================================================================
	public static final String BROWSER_SETTING_FILE = "src/test/resources/browsers.setting.properties";
	public static final String OMAHA_URL_DESKTOP_MODE = "https://www.omastk.com/shop/?setviewmode=ENHANCED";
	public static final String OMAHA_URL_MOBILE_MODE = "https://www.omastk.com/shop/?setviewmode=MOBI_ENH";
	public static final String BROWSER_IE = "ie";
	public static final String BROWSER_CHROME = "chrome";
	public static final String BROWSER_FIREFOX = "firefox";
	public static final String BROWSER_SAFARI = "safari";

	// ================================================================================
	// Platform variables
	// ================================================================================
	public static final String PLATFORM_DESKTOP = "desktop";
	public static final String PLATFORM_MOBILE = "mobile";
	public static final String PLATFORM_TABLET = "tablet";
	public static final String PLATFORM_IPAD = "ipad";
	public static final String PLATFORM_IPHONE = "iphone";
	public static final String PLATFORM_ANDROID = "android";
	public static final String PLATFORM_MAC = "mac";

	// ================================================================================
	// Timeout variables
	// ================================================================================
	public static final int LONG_TIME = 90;
	public static final int MID_TIME = 45;
	public static final int SHORT_TIME = 30;
	public static final int SLEEP_TIME = 3;

	// ================================================================================
	// Test Data
	// ================================================================================
	public static final String SKU006NAME = "Prime Rib Roast";
	public static final String FIRSTNAME_REQUIRED_MESSAGE = "Please -- it's a required field";
	public static final String LASTNAME_REQUIRED_MESSAGE = "Ok -- we can't deliver without a last name - it's a required field.";
	public static final String ADDRESS1_REQUIRED_MESSAGE = "Address for delivery -- it's a must! Please provide.";
	public static final String CITY_REQUIRED_MESSAGE = "City, State and Zip code... all required fields.";
	public static final String STATE_REQUIRED_MESSAGE = "City, State and Zip code... all required fields.";
	public static final String ZIPCODE_REQUIRED_MESSAGE = "City, State and Zip code... all required fields.";
	public static final String PHONE_REQUIRED_MESSAGE = "Just in case we need to speak with you about your order or some fantastic offers, please provide the phone number you'd like us to use.";
	public static final String EMAIL_ADDRESS_REQUIRED_MESSAGE = "The email address you supplied appears to be invalid. Could you please enter it again?";
	public static final String ACCEPTANCE_REQUIRED_MESSAGE = "Please confirm that you agree to the Terms of Use and Privacy Policy by checking the box below.";
	public static final String GIFT_MESSAGE = "TEST Message: " + Common.getRandomString("GIFT") + ". PLEASE Ignore";
	public static final String EDITED_GIFT_MESSAGE = "TEST Edited Message: " + Common.getRandomString("GIFT") + ". PLEASE Ignore";
	public static final String STREETADDRESS_PO_BOX = "P.O. Box 757";
	public static final String STREETADDRESS_NE_VALID = "10909 JOHN GALT BLVD";
	public static final String STREETADDRESS_US_VALID = "5273 Prospect Rd";
	public static final String URL_PAYPAL_PAGE = "https://www.paypal.com";
	public static final String CHECKOUT_SHOPPING_CART_PAGE_TITLE = "Checkout | Shopping-Cart";
	public static final String CHECKOUT_PAYMENT_PAGE_TITLE = "Checkout | Payment";
	public static final String CHECKOUT_SHIPPING_PAGE_TITLE = "Checkout | Shipping";
	public static final String DEFAULT_STATE_OF_SHIPPING_ADDRESS = "New York";
	public static final String RUSH_SHIPTO_ADDRESS = "Florida";
	public static final String DEFAULT_STATE_OF_BILLING_ADDRESS = "California";
	public static final String[] EXEMPT_STATES_OF_SHIPPING_ADDRESS = { "New York", "Alaska", "Delaware", "Kentucky", "Maine" };
	public static final String[] TAXABLE_STATES_OF_SHIPPING_ADDRESS = { "Alabama", "Oklahoma", "South Dakota", "Georgia", "Illinois" };

	public static final String REWARD_CARD = "RWTEST";
	public static final String REWARD_CARD_PRICE_500 = "RWTEST500";
	public static final String REWARD_CARD_PRICE_100 = "RWTEST100";
	public static final String REWARD_CARD_PRICE_75 = "RWTEST75";
	public static final String REWARD_CARD_PRICE_30 = "RWTEST30";
	public static final String REWARD_CARD_PRICE_50 = "RWTEST50";
	public static final String REWARD_CARD_PRICE_5 = "RWTEST5";
	public static final String REWARD_CARD_PRICE_15 = "RWTEST15";
	public static final String REWARD_CARD_PRICE_20 = "RWTEST20";
	public static final String PAYMENT_METHOD_CONTENT = "Gift Card";
	
	public static final int QUANTITY_DEFAULT = 1;
	public static final int QUANTITY_MULTIPLE = 2;
	public static final String LIVE_REWARD_URL = "https://rewards.omahasteaks.com/servlet/redirect?fromUri=/collection";

	// ================================================================================
	// Steak lover Rewards Data
	// ================================================================================

	public static final String REDEEM_POINT = "REDEEM POINTS";
	public static final String SIGN_IN = "JOIN & SHIP FREE";

	@SuppressWarnings("serial")
	public static Map<String, String> SIGN_IN_OR_SIGN_UP = new HashMap<String, String>() {
		{
			put("desktop", "SIGN IN / SIGN UP");
			put("mobile", "SIGN UP");
		}
	};

	// ================================================================================
	// Homepage Page Data
	// ================================================================================
	public static final String CUSTOMER_SERVICE = "Customer Service";
	public static final String OUR_STORY = "Our Story";
	public static final String COOKING = "Cooking";
	public static final String PROGRAMS = "Programs";
	public static final String CONTACT_PREFERENCES_OPTION = "Do not send me any emails";

	// ================================================================================
	// Search Page Data
	// ================================================================================
	public static final String DEFAULT_SORT_TYPE = "Sort By: Most Relevant";
	public static final String[] LIST_TABS = { "Items", "Packages", "Meals" };

	// ================================================================================
	// Last-Minute Gift Page Data
	// ================================================================================
	public static final String FREE_EXPRESS_DELIVERY = "Free Express Delivery";
	public static final String FREE_RUSH_DELIVERY = "Free Rush Delivery";
	public static final String EXPRESS_DELIVERY_MESSAGE = "Price Includes Express Shipping";
	public static final String RUSH_DELIVERY_MESSAGE = "Price Includes Rush Shipping";

	// ================================================================================
	// Shipping Data
	// ================================================================================
	public static final String STANDARD_SHIPPING = "Standard Shipping";
	public static final String RUSH_SHIPPING = "Rush Delivery";
	public static final String CUSTOM_SHIPPING = "Custom Delivery Date";
	public static final String EXPRESS_SHIPPING = "Express Delivery";
	public static final String THANKSGIVING_SHIPPING = "For Thanksgiving delivery";
	public static final String PREFERRED_SHIPPING = "Preferred Shipping";
	public static final String CHRISTMAS_DELIVERY = "Christmas Delivery";
	public static final String SATURDAY_SHIPPING = "Saturday delivery";
	public static final String SATURDAY_RUSH_SHIPPING = "Saturday Rush Delivery";
	public static final String SHIPPING_COST_FREE = "FREE";
	public static final String PROMOTIONS_MSG = "Choose an option below and get a $20 E-Reward Card! details";
	public static final String PROMOTIONS_MSG_IN_MODAL = "Plus, get a $20 E-Reward Card";
	public static final String[] LIST_SHIPPING_METHOD = { Constants.STANDARD_SHIPPING, Constants.RUSH_SHIPPING, Constants.EXPRESS_SHIPPING, Constants.CUSTOM_SHIPPING, Constants.SATURDAY_SHIPPING };

	// ================================================================================
	// Account Data
	// ================================================================================
	public static final ListAccounts LIST_ACCOUNTS = new ListAccounts();
	public static final String WELCOME_TEXT = "Welcome ";
	public static final String CONTACT_PREFERENCES = "Contact Preferences";
	public static final String CHANGE_EMAIL_ADDRESS = "Change Email Address";
	public static final String CHANGE_PASSWORD = "Change Password";
	public static final String DELETE_MY_ACCOUNT = "Delete My Account";
	public static final String ERROR_MESSAGE = "No account found Please check your email address or create a new account.";
	public static final String APT_SUITE = "12345";
	public static final String STEAKLOVER_REWARDS = "Steaklover Rewards";

	public static final String FACEBOOK = "Facebook";
	public static final String YAHOO = "Yahoo";
	public static final String GOOGLE = "Google";
	public static final String TWITTER = "Twitter";
	public static final String[] LIST_SOCIALS_NETWORK_ITEM = { Constants.FACEBOOK, Constants.YAHOO, Constants.GOOGLE, Constants.TWITTER };

	public static final String PASSWORD = "Password";

	public static final String COMPANY_ADDRESS = "Company Address";
	public static final String RESIDENTIAL_ADDRESS = "Residential Address";
	public static final String EMAIL_ADDRESS = "Email Address";

	public static final String USER_STATUS_GOLD = "Gold";
	public static final String USER_STATUS_SLR = "Reward Member";

	// ================================================================================
	// SKU Data
	// ================================================================================
	// public static final ListSKUs LIST_SKUS = new ListSKUs();

	// ================================================================================
	// Email Preferences : See Deals options
	// ================================================================================

	public static final String HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_1 = "Send me ALL your bargains";
	public static final String HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_2 = "Send me your best WEEKLY offer";
	public static final String HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_3 = "Send me your best MONTHLY offer";
	public static final String HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_4 = "Send me your HOLIDAY & GIFTING deals";
	public static final String HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_5 = "Send me ONLY CHRISTMAS deals";
	public static final List<String> LIST_HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTIONS = Arrays.asList(HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_1, HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_2, HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_3, HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_4, HOW_OFTEN_DO_YOU_WANT_TO_SEE_DEALS_OPTION_5);

	// ================================================================================
	// Email Preferences : Exclusive Offters options
	// ================================================================================
	public static final String EXCLUSIVE_OFFERS_1 = "The best email offers";
	public static final String EXCLUSIVE_OFFERS_2 = "Once a week";
	public static final String EXCLUSIVE_OFFERS_3 = "Once a month";
	public static final String EXCLUSIVE_OFFERS_4 = "Holiday reminders";
	public static final String EXCLUSIVE_OFFERS_5 = "Christmas only";
	public static final String EXCLUSIVE_OFFERS_6 = "Do not email me exclusive offers";
	public static final List<String> LIST_EXCLUSIVE_OFFERS_OPTIONS = Arrays.asList(EXCLUSIVE_OFFERS_1, EXCLUSIVE_OFFERS_2, EXCLUSIVE_OFFERS_3, EXCLUSIVE_OFFERS_4, EXCLUSIVE_OFFERS_5, EXCLUSIVE_OFFERS_6);

	// ================================================================================
	// Email Preferences : Steaklover Rewards options
	// ================================================================================
	public static final String STEAKLOVER_REWARDS_OPTION_1 = "I would still like my Steaklover Rewards updates.";
	public static final String STEAKLOVER_REWARDS_OPTION_2 = "Do not send me reward updates";
	public static final String STEAKLOVER_REWARDS_OPTION_3 = "Send me my reward updates";
	public static final List<String> LIST_STEAKLOVER_REWARDS_OPTIONS = Arrays.asList(STEAKLOVER_REWARDS_OPTION_1, STEAKLOVER_REWARDS_OPTION_2);
	public static final List<String> LIST_STEAKLOVER_REWARDS_OPTIONS_MB = Arrays.asList(STEAKLOVER_REWARDS_OPTION_2, STEAKLOVER_REWARDS_OPTION_3);

	// ================================================================================
	// Phone Preferences : Steaklover Rewards options
	// ================================================================================

	public static final String PHONE_PREFERENCES_OPTION_1 = "All the best offers";
	public static final String PHONE_PREFERENCES_OPTION_2 = "Four times a year";
	public static final String PHONE_PREFERENCES_OPTION_3 = "Twice a year";
	public static final String PHONE_PREFERENCES_OPTION_4 = "Christmas only";
	public static final String PHONE_PREFERENCES_OPTION_5 = "Do not call me with exclusive offers";
	public static final List<String> LIST_PHONE_PREFERENCES_OPTIONS = Arrays.asList(PHONE_PREFERENCES_OPTION_1, PHONE_PREFERENCES_OPTION_2, PHONE_PREFERENCES_OPTION_3, PHONE_PREFERENCES_OPTION_4, PHONE_PREFERENCES_OPTION_5);

	// ================================================================================
	// Extra Shipping Fee
	// ================================================================================

	public static final String EXTRA_SHIPPING_FEE_HAWAII = "+ $39.99";
	public static final String EXTRA_SHIPPING_FEE_ALASKA = "+ $39.99";
	public static final String EXTRA_SHIPPING_FEE_PUERTO_RICO = "+ $39.00";
	public static final String EXTRA_SHIPPING_FEE_VIRGIN_ISLANDS = "+ $125.00";
	public static final String EXTRA_SHIPPING_FEE_CANADA = "+ $42.99";

	// ================================================================================
	// Account with Order History
	// ================================================================================
	public static final String EMAIL_ORDER_HISTORY_ACCOUNT_1 = "marym@omahasteaks.com";
	public static final String PASS_ORDER_HISTORY_ACCOUNT_1 = "muckey";
	public static final String EMAIL_ORDER_HISTORY_ACCOUNT_2 = "charlesj@omahasteaks.com";
	public static final String PASS_ORDER_HISTORY_ACCOUNT_2 = "testtest1";
	public static final String REWARDS_POINT_TOTAL = "50";

	// ================================================================================
	// Extra Shipping Options
	// ================================================================================

	public static final String[] HOLIDAY_DELIVERY_SHIPPING = { "For Thanksgiving Delivery", "New Year Delivery", "Christmas Delivery", "St. Patrick's Day", "etc." };

	// ================================================================================
	// Valid HTTP Status-Code
	// ================================================================================

	public static final List<String> LIST_VALID_HTTP_STATUS_CODE = Arrays.asList(String.valueOf(HttpURLConnection.HTTP_OK), String.valueOf(HttpURLConnection.HTTP_MOVED_PERM), String.valueOf(HttpURLConnection.HTTP_MOVED_TEMP), String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR),String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));

	// ================================================================================
	// Ignore link
	// ================================================================================

	public static final String IGNORE_PRESS_LINK = "https://www.omastk.com/press/";
	public static final String IGNORE_DAD_LINK = "https://www.omastk.com/dads/";
	public static final String IGNORE_BLOG_LINK = "https://www.omastk.com/blog/";

	public static final String LOCATOR_PATH = "src/test/resources/locators/";

	// ================================================================================
	// Invalid data for Shipping/Billing Address
	// ================================================================================

	public static final String INVALID_STREET = "Prospect Rd";

	// ================================================================================
	// Collection Center page
	// ================================================================================

	public static final String VALID_COLLECTION_NUMBER = "9368094CMVV";
	public static final String SRC_SHIPPING_INFORMATION = "/gifs/checkoutbar_shipinfo.gif";
	public static final String SRC_SHIPPING_OPTIONS = "/gifs/checkoutbar_shipopt.gif";
	public static final String SRC_ORDER_REVIEW = "/gifs/checkoutbar_review.gif";

	public static final String TITLE_SHIPPING_INFORMATION = "Shipping Information";
	public static final String TITLE_INFORMATION_OPTIONS = "Shipping Options";
	public static final String TITLE_ORDER_REVIEW = "Order Review";
	public static final String TITLE_ORDER_CONFIRMATION = "Order Confirmation";

	public static final String SHOPPING_BAG_ITEM = "ITEM";
	public static final String SHOPPING_BAG_SELECTIONS = "SELECTIONS";
	public static final String SHOPPING_BAG_QUANTITY = "QTY";
	public static final String SHOPPING_BAG_SHIPPING_INFORMATION = "SHIPPING INFO";

	public static final String COLLECTION_CENTER_TITLE = "Omaha Steaks Redemption Center";

	// ================================================================================
	// Reward Collection Center page
	// ================================================================================

	public static final String SKU_POINT_20 = "20";
	public static final String SKU_POINT_10 = "10";
	public static final String REWARDS_NUMBER_VALID = "SVTEST10";
	public static final String REWARDS_NUMBER_VALID2 = Constants.DB.getGCID("BOR");
	
	// ================================================================================
	// Reward Collection Center page
	// ================================================================================
	public static final String DEFAULT_DATE = "";

	public static final String REWARDS_SHOPPING_CART_ITEM_FIELD = "Item";
	public static final String REWARDS_SHOPPING_CART_SELECTION_FIELD = "Selection";
	public static final String REWARDS_SHOPPING_CART_PTS_FIELD = "Pts";
	public static final String REWARDS_SHOPPING_CART_QTY_FIELD = "Qty";
	public static final String REWARDS_SHOPPING_CART_SEND_TO_FIELD = "Send To";
	public static final String REWARDS_SHOPPING_CART_REWARD_NUMBER_FIELD = "Reward Number";

	public static final String FIRST_CATEGORY = "firstCategory";
	public static final String LAST_CATEGORY = "lastCategory";
	public static final String RANDOMLY_CATEGORY = "Roasts";

	public static final String REWARD_TAB_COMPANY_INFO = "Company Info";
	public static final String GIFT_ACCOUNT = "Gift account";

	public static final String HOME = "Home";
}
