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
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_ECN_001_ErrorMessageDisplaysWhenFillBlankOrInvalidEmailAndPhoneInShippingAddress extends TestBase_2SC {

    @Inject
    GeneralPage generalPage;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    CustomerAddress myAddress, invalidAddress;
    @Inject
    Item myEGiftCard;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    CategoryPage categoryPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    ShoppingCartPage shoppingCartPage;

    @Test
    public void TC_ECN_001_Error_Message_Displays_When_Add_SKU_to_Myself_And_Enter_Blank_Or_Invalid_Email_And_Phone_In_ShippingAddress() {

	initTestCaseData();

	searchAndAddEGiftCard(myEGiftCard);

	checkOutFromShoppingCartPage();

	fillInvalidPhoneAndEmailInShippingAddress();

	verifyTheWarningMessagesDisplayCorrectlyWhenFillingInvalidEmailAndPhone();

	leaveEmailFieldEmptyInShippingAddress();

	verifyTheWarningMessagesDisplayCorrectlyWhenLeavingEmailFieldEmpty();

    }

    private void searchAndAddEGiftCard(SKU sku) {
    String url="";
	Logger.info("In Searchpage:" + "- Search a SKU (E-Gift cards)\n");
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
    Common.modalDialog.closeUnknownModalDialog();
	Logger.info("In Gift Card page:" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
	categoryPage.addEGiftCard(sku);
	Logger.info("In \"Added To Cart\" popup:" + "- Click \"CHECKOUT\"");
	generalPage.checkOut();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	shoppingCartPage.checkOut();
    }

    private void fillInvalidPhoneAndEmailInShippingAddress() {
	Logger.info("In Shipping Address page:" + "In Shipping Address section: " + "- Fill all valid information" + " In Optional Delivery Notification section:" + " - Fill invalid  shipping phone value into Land-Line Phone textbox" + " - Fill invalid  email address value into Email address textbox" + "- Click \"Continue\" button");

	shippingAddressPage2SC.fillShippingAddress(myAddress);
	invalidAddress = myAddress.clone();
	invalidAddress.updateFieldValueBy(AddressFields.PHONE, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 9));
	invalidAddress.updateFieldValueBy(AddressFields.EMAIL, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 20));
	shippingAddressPage2SC.fillPhoneShippingAddress(invalidAddress.phoneNumber);
	shippingAddressPage2SC.fillEmailShippingAddress(invalidAddress.email);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void verifyTheWarningMessagesDisplayCorrectlyWhenFillingInvalidEmailAndPhone() {

	Logger.verify("Verify error message \"Please Enter A Valid Phone Number\" displays correctly when filling invalid  shipping phone in Optional Delivery Notification section");
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP)) {
	    assertEquals(shippingAddressPage2SC.getLandLinePhoneErrorMessage(), Messages.SINVALID_PHONE_ERROR_MESSAGE, "Error message \"Please Enter A Valid Phone Number\"is not displayed as expected");
	} else {
	    assertEquals(shippingAddressPage2SC.getErrorMessageByField(AddressFields.PHONE), Messages.SINVALID_PHONE_ERROR_MESSAGE, "Error message \"Please Enter A Valid Phone Number\"is not displayed as expected");
	}

	Logger.verify("Verify error message \"Please Enter A Valid Email Address\" displays correctly when filling invalid  email address in Optional Delivery Notification section");
	assertEquals(shippingAddressPage2SC.getEmailErrorAtBottom(), Messages.WINVALID_EMAIL_ADDRESS_ERROR_MESSAGE, "Error message \""+Messages.WINVALID_EMAIL_ADDRESS_ERROR_MESSAGE+"\" is not displayed as expected");
    }

    private void leaveEmailFieldEmptyInShippingAddress() {
	Logger.info("In Shipping Address page:" + "In Optional Delivery Notification section:" + " - Leave email  address field empty" + " - Fill valid informaiton into Land-Line Phone textbox" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillPhoneShippingAddress(myAddress.phoneNumber);
	shippingAddressPage2SC.fillEmailShippingAddress("      ");
	Common.waitForDOMChange();
	shippingAddressPage2SC.clickContinueButton();
	Common.waitForDOMChange();
    }

    private void verifyTheWarningMessagesDisplayCorrectlyWhenLeavingEmailFieldEmpty() {
	Logger.info("Verify error message \"Error Email Address - Required for sending Electronic Gift Cards. Thank you!\" displays correctly when leaving  email address empty in Optional Delivery Notification section");
	assertEquals(shippingAddressPage2SC.getErrorMessage(), Messages.EMPTY_EMAIL_ADDRESS_EGIFT_CARD_ERROR_MESSAGE, "Warning message is not displayed when leaving email address field empty in Optional Delivery Notification section");
    }

    private void initTestCaseData() {
	Logger.tc("TC_ECN_001 - Error Message displays for blank email or fill invalid email and phone in shipping address page when add only EGift card for myself");
	Logger.to("TO_ECN_01 - Error message is displayed for blank Email textbox in Shipping Address page");
	Logger.to("TO_ECN_03 - Error message is displayed when filling email address value with invalid format into Email textbox in Shipping Address page");
	Logger.to("TO_ECN_04 - Error message is displayed when filling invalid phone number into Lane-Phone textbox in Shipping Address page");

	myAddress = lstAddresses.getDefaultShippingAddress();
	myEGiftCard.setRecipient(Recipient.MYSELF);
	myEGiftCard.setPrice(5);
	myEGiftCard.setQuantity(1);
    }
}
