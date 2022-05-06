package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_004_BillingAddressWithInvalidZipcodeCityAndState extends TestBase_2SC {
    CustomerAddress customerAddress, invalidAddress;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    Item item;
    @Inject
    GeneralPage generalPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage;

    @Test
    public void TC_2SCON_004_Billing_Address_With_Invalid_ZipCode_City_And_State() {
	initTestCaseData();

	searchAndAddItem(item);

	checkOutFromShoppingCartPage();

	fillValidInformation();

	fillInvalidZipCode();

	verifyWarningMessageIsDisplayed(Messages.BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_2);

	fillNotMatchZipCode();

	verifyWarningMessageIsDisplayed(Messages.BINVALID_ZIPCODE_ERROR_MESSAGE_AT_BOTTOM_1);
    }

    private void checkOutFromShoppingCartPage() {
	Logger.info("In My Cart, click CHECKOUT button");
	Common.modalDialog.closeSavorDialog();
	shoppingCartPage.checkOut();
    }

    private void fillInvalidZipCode() {
	Logger.info("In Payment & Review page: \n" + "- Fill valid all information except Zip Code fields in Shipping Address");
	invalidAddress.updateFieldValueBy(AddressFields.STATE, "California");
	invalidAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "00000");
	paymentAndReviewPage.fillBillingAddress(invalidAddress);
    }

    private void fillNotMatchZipCode() {
	Logger.info("In Payment & Review page: \n" + "- Fill valid all information but Zip Code City, State and Country code does not match together in Billing address");
	invalidAddress.updateFieldValueBy(AddressFields.STATE, Constants.DEFAULT_STATE_OF_SHIPPING_ADDRESS);
	invalidAddress.updateFieldValueBy(AddressFields.ZIP_CODE, "95129");
	paymentAndReviewPage.fillBillingAddress(invalidAddress);
    }

    private void fillValidInformation() {
	Logger.info(" In Shipping Address page: \n" + "- Fill valid all information\n" + "- Click \"CONTINUE\"");
	shippingAddressPage.fillShippingAddress(customerAddress);
	shippingAddressPage.clickContinueButton();
	Logger.info("In Payment & Review page\r\n" + " - Fill \"4111111111111111\" number at Credit / Debit section\n" + " - Card Expiration: we will generate randomly a date in future (MM/YY)\n");
	paymentAndReviewPage.fillCreditCardNumber();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCON_004 - Warning message with suitable information appears when filling invalid Zipcode, State and city in Billing Address");
	Logger.to("TO_2SCON_8 - Warning message when filling invalid zip code in Billing address");
	Logger.to("TO_2SCON_10 - Warning message when filling Zip Code, City, State and Country code does not match together in Billing address");
	item.init(SkuType.OVER100,Recipient.MYSELF);
	customerAddress = lstAddresses.getDefaultShippingAddress();
	invalidAddress = customerAddress.clone();
    }

    private void searchAndAddItem(Item item) {
	String keyword = Common.getNumberFromText(item.getId());
	Logger.info("From Hompage, enter \"" + keyword + "\" into Search textbox and click Search button");
	generalPage.search(keyword);
	Logger.info("In the second dropdown, select \"Ship To " + item.getRecipient() + "\n" + "- Click \"ADD TO CART\" button\"\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"");
	productPage.addSKUToCart(item, false);
	productPage.checkOut();
    }

    private void verifyWarningMessageIsDisplayed(String message) {
	Logger.verify("The warning messages are displayed:\n" + "- " + message);
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
	    assertEquals(paymentAndReviewPage.getErrorMessageAtBottom(), message);
	assertTrue(paymentAndReviewPage.getErrorMessageAtBottom().contains(message));
    }
}
