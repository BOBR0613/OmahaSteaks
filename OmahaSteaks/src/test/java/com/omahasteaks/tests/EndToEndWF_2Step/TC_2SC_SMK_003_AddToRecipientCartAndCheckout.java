package com.omahasteaks.tests.EndToEndWF_2Step;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SC_SMK_003_AddToRecipientCartAndCheckout extends TestBase_SMK {
    @Inject
    Item item;

    @Test
    public void TC_2SC_SMK_003_Add_To_Recipient_Cart_And_Checkout() {
	Logger.tc("TC_2SC_SMK_003 Add To Recipient Cart And Checkout");

	item.setRecipient(Recipient.THONG_NGUYEN);
	item.setName(Constants.SKU006NAME);

	initTestCaseData();

	searchAndAddItem(item, false);

	checkOut();

	fillShippingAddressAndContinueCheckout(shippingAddress);

	verifyShippingAddressesDisplayCorrectly(Recipient.THONG_NGUYEN, shippingAddress);

	fillCreditCardNumberAndPlaceMyOrder();

	verifyBillingAddressAndThankYouMessageDisplays();

	verifyOrderNumberInCorrectFormat();
    }
}
