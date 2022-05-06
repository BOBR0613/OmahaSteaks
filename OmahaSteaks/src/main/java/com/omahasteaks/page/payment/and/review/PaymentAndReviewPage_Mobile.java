package com.omahasteaks.page.payment.and.review;
/*package pages.PaymentAndReviewPageImp;

import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;

import pages.PaymentAndReviewPage;
import utils.common.Common;

public class PaymentAndReviewPage_Mobile extends PaymentAndReviewPage_Desktop implements PaymentAndReviewPage {
    public PaymentAndReviewPage_Mobile() {
	super();
	eleBillingAddressContent = new Element(interfaces.get("eleBillingAddressContent_mb"));
	btnEditBillingAddress = new Link(interfaces.get("eleBillingAddressContent_mb"));
    }

    // ================================================================================
    // Methods
    // ================================================================================
    protected Element eleShippingAddressContent(String recipient) {
	return new Element(interfaces.get("eleShippingAddressContent_mb"), recipient);
    }

    protected Link btnEditShippingAddress(Element btnEditShippingAddress) {
	return new Link(btnEditShippingAddress, interfaces.get("btnEditShippingAddress_mb"));
    }

    @Override
    public void fillCreditCardNumber(String creditCardNumber, boolean isFutureDate) {
	Common.select(cbbCreditCardType, "Visa");
	Common.waitForSelected(cbbCreditCardType, "Visa");
	Common.enter(txtCreditCardNumber, creditCardNumber);
	if (isFutureDate)
	    Common.select(cbbCreditCardYear, Common.randomYearOfFuture());
	else {
	    Common.select(cbbCreditCardMonth, "01 - January");
	    Common.select(cbbCreditCardYear, Common.getCurrentYear());
	}
    }
}
*/