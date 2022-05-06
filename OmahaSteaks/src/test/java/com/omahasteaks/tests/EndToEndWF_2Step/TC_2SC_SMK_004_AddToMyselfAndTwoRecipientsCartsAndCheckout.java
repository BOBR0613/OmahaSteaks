package com.omahasteaks.tests.EndToEndWF_2Step;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SC_SMK_004_AddToMyselfAndTwoRecipientsCartsAndCheckout extends TestBase_SMK {

	CustomerAddress shippingAddress1st;
	CustomerAddress shippingAddress2nd;
	@Inject
	Item itemMyself;
	@Inject
	Item item1stRecipient;
	@Inject
	Item item2ndRecipient;

	@Test
	public void TC_2SC_SMK_004_Add_To_Myself_And_Two_Recipients_Carts_And_Checkout() {
		Logger.tc("TC_2SC_SMK_004 Add To Myself And Two Recipients Carts And Checkout");

		initTestCaseData();

		searchAndAddItem(itemMyself, false);

		searchAndAddItem(item1stRecipient, false);

		searchAndAddItem(item2ndRecipient, false);

		checkOut();

		fillShippingAddressAndClickNextAddressButton(billingAddress);

		fillShippingAddressAndClickNextAddressButton(shippingAddress1st);
        
		fillShippingAddressAndContinueCheckout(shippingAddress2nd);

		verifyShippingAddress();

		fillCreditCardNumberAndPlaceMyOrder();

		verifyBillingAddressAndThankYouMessageDisplays();

		verifyOrderNumberInCorrectFormat();
	}

	// ================================================================================
	// Test Case Methods
	// ================================================================================

	private void fillShippingAddressAndClickNextAddressButton(CustomerAddress address) {
		Logger.info("'In Shipping Address form:\n" + " - Enter valid information\n" + " - Click \"Next Address\" button");
		shippingAddressPage.fillShippingAddress(address);
		shippingAddressPage.clickContinueButton();
	}

	@Override
	protected void initTestCaseData() {
		Logger.tc("TC_2SC_SMK_004 Add To Myself And Two Recipients Carts And Checkout");
		billingAddress = lstAddresses.getRandomBillingAddress();
		shippingAddress1st = lstAddresses.getDefaultShippingAddress();
		shippingAddress2nd = shippingAddress1st;
		itemMyself.init(SkuType.OVER100, Recipient.MYSELF); 
		item1stRecipient.init(SkuType.PACKAGES, Recipient.THONG_NGUYEN); 
		item2ndRecipient.init(SkuType.PACKAGES, Recipient.THAO_NHO); 
	}

	private void verifyShippingAddress() {
		Logger.verify("In Payment Option and Review page, verify that:\n" + " - Added Billing address displays correctly\n" + " - Added Shipping address of Recipient 1 and Recipient 2 display correctly");
		assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.MYSELF.getValue()), billingAddress.toShippingArray(), "Myselfs shipping address is not displayed as expected");
		assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.THONG_NGUYEN.getValue()), shippingAddress1st.toShippingArray(), "Thong Nguyen's shipping address is not displayed as expected");
		assertEquals(paymentAndReviewPage.getShippingAddress(Recipient.THAO_NHO.getValue()), shippingAddress2nd.toShippingArray(), "The Nho's shipping address is not displayed as expected");
	}
}
