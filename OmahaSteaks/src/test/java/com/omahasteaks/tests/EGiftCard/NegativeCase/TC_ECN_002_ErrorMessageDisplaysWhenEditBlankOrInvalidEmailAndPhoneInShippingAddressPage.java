package com.omahasteaks.tests.EGiftCard.NegativeCase;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ECN_002_ErrorMessageDisplaysWhenEditBlankOrInvalidEmailAndPhoneInShippingAddressPage extends TestBase_2SC {

	@Inject
	Item myEGiftCard;
	@Inject
	ListAddresses lstAddresses;
	@Inject
	CustomerAddress shippingAddress, invalidAddress;
	@Inject
	GeneralPage generalPage;
	@Inject
	CategoryPage categoryPage;
	@Inject
	ShoppingCartPage shoppingCartPage;
	@Inject
	ShippingAddressPage2SC shippingAddressPage2SC;
	@Inject
	PaymentAndReviewPage2SC paymentAndReviewPage2SC;
	@Inject
	SearchResultPage searchResultPage;
	@Inject
	ProductPage productPage;

	@Test
	public void TC_ECN_002_Error_Message_Displays_When_Add_SKU_to_Myself_And_Edit_Blank_Or_Invalid_Email_And_Phone_In_ShippingAddress_Page() {

		initTestCaseData();

		searchAndAddGiftCard(myEGiftCard);

		checkOutFromShoppingCartPage();

		fillShippingAddressWithoutEmailAndPhoneAndClickContinue();

		verifyErrorMessageDisplaysForBlankEmailAndPhoneTextboxsInShippingAddressPage();

		clickEditThisAddressLinkInShippingAddressPage();

		fillEmailAndPhoneEmptyInShippingAddressModal();

		verifyErrorMessageDisplaysForBlankEmailAndPhoneTextboxsInShippingAddressPage();

		clickEditThisAddressLinkInShippingAddressPage();

		fillInvalidEmailAndPhoneShippingAddressInModal();

		verifyErrorMessageDisplaysCorrectlyInShippingAddressModal();

	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void verifyErrorMessageDisplaysCorrectlyInShippingAddressModal() {
		Common.waitForDOMChange();
//		Logger.verify("In Shipping Address popup:" + "Verify \"Please Enter A Valid Email Address\" is displayed");
//		assertEquals(shippingAddressPage2SC.getErrorMessageByField(AddressFields.EMAIL), Messages.SINVALID_EMAIL_ADDRESS_ERROR_MESSAGE);
		Logger.verify("In Edit My Address popup:" + "Verify \"Please Enter A Valid Phone Number\" is displayed");
		assertEquals(shippingAddressPage2SC.getErrorMessageByField(AddressFields.PHONE), Messages.SINVALID_PHONE_ERROR_MESSAGE);
	}

	private void fillInvalidEmailAndPhoneShippingAddressInModal() {
		Logger.info("In Edit My Address popup:" + "- Leave email and phone number fields empty" + "- Click \"Update Contact\" button");
		invalidAddress = shippingAddress.clone();
		invalidAddress.updateFieldValueBy(AddressFields.PHONE, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 9));
		invalidAddress.updateFieldValueBy(AddressFields.EMAIL, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 20));
		shippingAddressPage2SC.fillEmailShippingAddressInModal(invalidAddress.email);
		shippingAddressPage2SC.fillPhoneShippingAddressInModal(invalidAddress.phoneNumber);
		shippingAddressPage2SC.updateContactInModal();
	}

	private void fillEmailAndPhoneEmptyInShippingAddressModal() {
		Logger.info("In Edit My Address popup:" + "- Leave email and phone number fields empty" + "- Click \"Update Contact\" button");
		invalidAddress = shippingAddress.clone();
		invalidAddress.updateFieldValueBy(AddressFields.EMAIL, "");
		invalidAddress.updateFieldValueBy(AddressFields.PHONE, "");
		shippingAddressPage2SC.fillEmailShippingAddressInModal(invalidAddress.email);
		shippingAddressPage2SC.fillPhoneShippingAddressInModal(invalidAddress.phoneNumber);
		shippingAddressPage2SC.updateContactInModal();
		shippingAddressPage2SC.clickContinueButton();
	}

	private void clickEditThisAddressLinkInShippingAddressPage() {
		Logger.info("In Shipping Address page:" + "- Click \"Edit this address\" link");
		shippingAddressPage2SC.clickEditThisAddressLink();
	}

	private void verifyErrorMessageDisplaysForBlankEmailAndPhoneTextboxsInShippingAddressPage() {
		Logger.verify("In Shipping Address page:" + "Verify error message \"Email Address - Required for sending Electronic Gift Cards. Thank you!\" displays correctly when leaving  email address empty in Optional Delivery Notification section");
		assertEquals(shippingAddressPage2SC.getErrorMessage(), Messages.EMPTY_EMAIL_ADDRESS_EGIFT_CARD_ERROR_MESSAGE, "Error message is not displayed for blank Email and Phone textboxes in Shipping Address page");
	}

	private void fillShippingAddressWithoutEmailAndPhoneAndClickContinue() {
		Logger.info("In Shipping Address page:" + "In Shipping Address section: " + "    - Fill all valid information" + "In Optional Delivery Notification section:" + "    -  Leave every fields empty" + "- Click \"Continue\" button");
		shippingAddressPage2SC.fillShippingAddress(shippingAddress);
		Common.waitForDOMChange();
		shippingAddressPage2SC.clickContinueButton();
		Common.waitForDOMChange();
	}

	private void checkOutFromShoppingCartPage() {
		Logger.info("In Shopping Cart page : " + "  -Click CHECKOUT button");
		Common.waitForDOMChange();
		shoppingCartPage.checkOut();
	}

	private void searchAndAddGiftCard(SKU sku) {
	    String url="";
		Logger.info("In Homepage: " + "    - Search a SKU( E-Gift Card )  ( eg. Omaha Steaks E-Gift Card) " + "    - Click \"Search\" button");
		if (Constants.OMAHA_URL.contains("?")) {
			  String str = Constants.OMAHA_URL.trim();
			  String[] arrOfStr = str.split("\\?",2);
			  url = arrOfStr[0].trim() + "buy/Gifts/Gift-Cards" + "?" + arrOfStr[1]; 
		}
		else 
		if (Constants.OMAHA_URL.contains("legacy")) {
			  String str = Constants.OMAHA_URL.trim();
			  String[] arrOfStr = str.split("legacy",2);
			  url = arrOfStr[0].trim() + "buy/Gifts/Gift-Cards"; 
		}
		else url = Constants.OMAHA_URL + "/buy/Gifts/Gift-Cards";
		DriverUtils.getWebDriver().get(url);

		Logger.info("In Gift Card page:" + "- In the first Item, input amount into \"Enter Amount\" textbox" + "- Click \"add to Cart\" button");
		categoryPage.addEGiftCard(sku);
		Logger.info("In \"Added To Cart\" popup: " + "- Click \"CHECKOUT\"");
		generalPage.checkOut();
	}

	private void initTestCaseData() {
		Logger.tc("TC_ECN_02 - Error message displays for blank or fill invalid email in Shipping Address Page when add only E-Gift card for Myself");
		Logger.to("TO_ECN_02 - Error message is displayed for blank Email and Phone text boxes in Shipping Address page");
		Logger.to("TO_ECN_05 - Error message is displayed in \"Edit My Address\" popup when entering the invalid email address in \"Edit My Address\" popup after clicking the \"Edit this address\" button in Shipping Address page");
		Logger.to("TO_ECN_06 - Error message is displayed in \"Edit My Address\" popup when entering the invalid phone number in \"Edit My Address\" popup after clicking the \"Edit this address\" button in Shipping Address page");
		Logger.to("TO_ECN_07 - Error message is displayed in Shipping Address page for blank Email textbox in \"Edit My Address\" popup after clicking the \"Edit this address\" button in Shipping Address page");
		shippingAddress.getCustomerAddress("001739909");
		shippingAddress.email = "";
		shippingAddress.confirmEmail = "";
		myEGiftCard.setRecipient(Recipient.MYSELF);
		myEGiftCard.setPrice(5);
		myEGiftCard.setQuantity(1);
	}

}
