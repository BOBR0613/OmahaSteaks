package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
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

public class TC_2SCON_010_WarningMessageAppearsWhenEnteringOneCharacterOfLastName extends TestBase_2SC {
    CustomerAddress invalidAddress;
    CustomerAddress customerAddress;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item item;
    @Inject
    GeneralPage generalPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage;

    @Test
    public void TC_2SCON_010_Warning_Message_Appears_When_Entering_One_Character_Of_Last_Name() {
	initTestCaseData();

	searchAndAddItem(item);

	checkOutFromShoppingCartPage();

	fillOneCharacterToFieldInShippingAddress(AddressFields.LAST_NAME);

	verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields.LAST_NAME);

	fillValidInformation();

	fillOneCharacterToFieldInBillingAddress(AddressFields.LAST_NAME);

	verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields.LAST_NAME);

	backToShippingAddressPageThenEditThisShippingAddress();

	fillOneCharacterToFieldInShippingAddressModal(AddressFields.LAST_NAME);

	verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields.LAST_NAME);
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void backToShippingAddressPageThenEditThisShippingAddress() {
	Logger.info("In Payment and Review page: \n - Select Edit this address of Shipping Address");
	paymentAndReviewPage.selectEditShippingAddrLink(item.getRecipient().getValue());
	Logger.info("In Shipping Address page: \n - Select Edit this address");
	shippingAddressPage.clickEditThisAddressLink();
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In Shopping Cart Page:\n" + " - Click \"Check Out\"");
	shoppingCartPage.checkOut();
    }

    private void fillOneCharacterToFieldInBillingAddress(AddressFields field) {
	Logger.info("In Payment and Review page: \n" + "- Fill one character to " + field.getValue2SC() + " then click \"CONTINUE\"");
	invalidAddress = customerAddress.clone();
	invalidAddress.updateFieldValueBy(field, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 1).replace(" ", "a"));
	paymentAndReviewPage.fillBillingAddress(invalidAddress);
	paymentAndReviewPage.placeOrder();
    }

    private void fillOneCharacterToFieldInShippingAddress(AddressFields field) {
	Logger.info("In Shipping Address page: \n" + "- Fill one character to " + field.getValue2SC() + " then Click \"CONTINUE\"");
	invalidAddress = customerAddress.clone();
	invalidAddress.updateFieldValueBy(field, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 1).replace(" ", "a"));
	shippingAddressPage.fillShippingAddress(invalidAddress);
	shippingAddressPage.clickContinueButton();
    }

    private void fillOneCharacterToFieldInShippingAddressModal(AddressFields field) {
	Logger.info("In Shipping Address page: \n" + "- Fill one character to " + field.getValue2SC() + " then Click \"CONTINUE\"");
	invalidAddress = customerAddress.clone();
	invalidAddress.updateFieldValueBy(field, Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_FULL_CHARS, 1).replace(" ", "a"));
	shippingAddressPage.fillShippingAddressInModal(invalidAddress);
	shippingAddressPage.updateContactInModal();
    }

    private void fillValidInformation() {
	Logger.info("In Shipping Address page: \n" + "- Fill valid all information\n" + "- Click \"CONTINUE\"");
	shippingAddressPage.fillShippingAddress(customerAddress);
	shippingAddressPage.clickContinueButton();
	Logger.info("In Payment & Review page\r\n" + " - Fill \" 4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n");
	paymentAndReviewPage.fillCreditCardNumber();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCON_010	Warning Message Appears When Entering One Character Of Last Name\n");
	Logger.to("TO_2SCON_31	Warning message when entering Last Name field in Shipping Address one character\n");
	Logger.to("TO_2SCON_32	Warning message when entering Last Name field in Billing Address one character\n");
	Logger.to("TO_2SCON_33	In Edit Billing Address popup, Warning message when entering Last Name field");
	item.initRandom(Recipient.MYSELF);
	customerAddress = lstAddresses.getDefaultShippingAddress();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	generalPage.checkOut();
    }

    private void verifyTheWarningMessageIsDisplayedInPaymentAndReviewPage(AddressFields field) {
	Logger.verify("The warning messages are displayed:\n" + Messages.LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE);
	assertEquals(paymentAndReviewPage.getErrorMessageByField(field), Messages.LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE);
    }

    private void verifyTheWarningMessageIsDisplayedInShippingPage(AddressFields field) {
	Logger.verify("The warning messages are displayed:\n" + Messages.LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE);
	assertEquals(shippingAddressPage.getErrorMessageByField(field), Messages.LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE);
    }

    private void verifyWarningMessageDisplaysCorrectlyInEditingAddressModal(AddressFields field) {
	Logger.verify("The warning messages are displayed:\n" + Messages.LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE);
	assertEquals(shippingAddressPage.getErrorMessageByField(field), Messages.LASTNAME_ONE_CHARACTER_REQUIRED_MESSAGE);
    }
}
