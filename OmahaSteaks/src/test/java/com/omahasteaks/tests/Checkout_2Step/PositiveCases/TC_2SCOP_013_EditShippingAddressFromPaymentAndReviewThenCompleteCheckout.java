package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_013_EditShippingAddressFromPaymentAndReviewThenCompleteCheckout extends TestBase_2SC {
    CustomerAddress billingAddress, shippingAddress, shippingAddressEdited;
    @Inject
    Package pkg;
    @Inject
    ListAddresses lstAddress;
    @Inject
    GeneralPage generalPage;
    @Inject
    SignInPage signInPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage;

    @Test
    public void TC_2SCOP_013_Edit_Shipping_Address_From_Payment_And_Review_Then_Complete_Checkout() {
	initTestCaseData();

	searchAndAddpackageThenCheckout();

//	verifySKUInfoInMyCartSection();

	fillShippingAddress();

	editShippingAddress();

	verifyRecipientsAddressHasBeenUpdated();

	continueToPaymentAndReviewPage();

	verifyEditedShippingaddressDisplays();

	fillCreditCardNumberAndPlaceMyOrder();

	verifyThankYouMessageDisplays();

	verifyOrderNumberInCorrectFormat();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private void verifyOrderNumberInCorrectFormat() {
	String getOrderNumberText = confirmationPage.getOrderNumberText();
	Logger.verify("Verify the " + getOrderNumberText + " displays as format [Order Number: <1-Character><7-numbers><4-Characters>]");
	assertEquals(getOrderNumberText.split(":")[0].trim(), "Order Number");
	assertTrue(confirmationPage.isOrderNumberCorrectFormat(getOrderNumberText.split(":", 2)[1].trim()));
    }

    private void verifyRecipientsAddressHasBeenUpdated() {
	Logger.verify("In Shipping Address Page\n" + " - Verify the new address is updated");
	assertEquals(shippingAddressPage2SC.getRecipientAddressText(), shippingAddressPage2SC.generateRecipientAddressInfo(shippingAddressEdited));
    }

    private void continueToPaymentAndReviewPage() {
	shippingAddressPage2SC.clickContinueButton();
    }

    private void editShippingAddress() {
	Logger.info("\"In New Payment and Review Page:\n" + " - Click \"Click the 'Edit this address' link in My Cart section to update Shipping Address.");
	paymentAndReviewPage2SC.selectEditShippingAddrLink(Recipient.MYSELF.getValue());
	Logger.info("In  Shipping Address Page:\n" + "- Update information based on existing data.\n" + "- Click Click 'UPDATE CONTACT' button.\n");
	shippingAddressPage2SC.clickEditThisAddressLink();
	shippingAddressPage2SC.fillShippingAddressInModal(shippingAddressEdited);
	shippingAddressPage2SC.updateContactInModal();
    }

    private void fillCreditCardNumberAndPlaceMyOrder() {
	Logger.info("In New Payment and Review Page:\n" + "- Fill \" 4111111111111111\" number at Credit / Debit section\n" + "- Card Expiration: we will generate randomly a date in future (MM/YY)\n" + "- Fill valid mandatory information at Billing Address section\n" + "- Click \"Place My Order\" button");
	paymentAndReviewPage2SC.fillCreditCardNumber();
	paymentAndReviewPage2SC.fillBillingAddress(billingAddress);
	paymentAndReviewPage2SC.placeOrder();
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address page:\n" + " - Select both checkbox (Greeting Card and Gift Wrap) \n" + " - Enter Gift Message\n" + " - Click \"Continue\" button");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	continueToPaymentAndReviewPage();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_013 - Edit Shipping Address From Payment And Review Then Complete Checkout\n");
	Logger.to("TO_2SCOP_25 - In Payment & Review, user can edit Shipping Address by clicking \"Edit this address\" link then complete Checkout\n");
	Logger.to("TO_2SCOP_34 - Information in \"Shipment Summary\" match with number of SKUs of Recipient in My Cart\n");
 	pkg.init(SkuType.PACKAGES, Recipient.MYSELF);
	billingAddress = lstAddress.getRandomBillingAddress();
	shippingAddress = billingAddress;
	shippingAddressEdited = lstAddress.getDefaultShippingAddress();
    }

    private void searchAndAddpackageThenCheckout() {
	Logger.info("Search a SKU by id (randomly)");
	generalPage.search(Common.getNumberFromText(pkg.getId()));
	Logger.info("In Product Page:\n" + " - Leave \"Ship To Myself\"\n" + " - Click \"ADD TO CART\" button\n" + "If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- If on desktop platform: Click \"Check Out\"\n" + "- If on mobile device: Click \"View My Cart\"");
	productPage.addSKUToCart(pkg, false);
	generalPage.viewMyCart();
	shoppingCartPage.clickContinue();
      }

    private void verifyEditedShippingaddressDisplays() {
	Logger.verify("In New Payment and Review Page, verify that:\n" + "- Edited Shipping address displays correctly");
	assertEquals(shippingAddressEdited.toShippingArray(), paymentAndReviewPage2SC.getShippingAddress(Recipient.MYSELF.getValue()), "The shipping address is not displayed as expected");
    }

    private void verifySKUInfoInMyCartSection() {
	Logger.verify("In Shipping Address page, verify that:\n" + pkg.getId() + "- SKU's Info is displayed correctly in \"My Cart\" section");
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
	    assertEquals(shippingAddressPage2SC.isSKUExisted(pkg), true, "The shipment summary is not displayed as expected");
    }

    private void verifyThankYouMessageDisplays() {
	Logger.verify("In Order Receipt Page\n" + "Verify that a message appears with \"Thank you for your order! It is being prepared to ship.\" in its content.");
	assertEquals(confirmationPage.isThankYouMessageDisplayed(), true, "The Thank You message is not displayed as expected");
    }
}
