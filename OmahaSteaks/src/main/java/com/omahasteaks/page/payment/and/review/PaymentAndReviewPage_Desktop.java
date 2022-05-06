package com.omahasteaks.page.payment.and.review;
/*package pages.PaymentAndReviewPageImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;

import data.ENUM.AddressFields;
import data.ENUM.Recipient;
import pages.PaymentAndReviewPage;
import pages.GeneralPageImp.GeneralPage_Desktop;
import utils.common.Common;
import utils.common.Constants;

public class PaymentAndReviewPage_Desktop extends GeneralPage_Desktop implements PaymentAndReviewPage {
    // ================================================================================
    // Locators
    // ================================================================================
    protected TextBox txtGiftNumber = new TextBox(interfaces.get("txtGiftNumber"));
    protected TextBox txtCreditCardNumber = new TextBox(interfaces.get("txtCreditCardNumber"));
    protected Button btnApply = new Button(interfaces.get("btnApply"));
    protected Button btnPlaceMyOrder = new Button(interfaces.get("btnPlaceMyOrder"));
    protected Button btnContinueCheckOut = new Button(interfaces.get("btnContinueCheckOut"));
    protected ComboBox cbbCreditCardType = new ComboBox(interfaces.get("cbbCreditCardType"));
    protected ComboBox cbbCreditCardYear = new ComboBox(interfaces.get("cbbCreditCardYear"));
    protected ComboBox cbbCreditCardMonth = new ComboBox(interfaces.get("cbbCreditCardMonth"));
    protected Element eleBillingAddressContent = new Element(interfaces.get("eleBillingAddressContent"));
    protected Element btnGoToTheNextMonth = new Element(interfaces.get("btnGoToTheNextMonth"));
    protected Element eleAvailableDayList = new Element(interfaces.get("eleAvailableDayList"));
    protected Element eleSelectedDay = new Element(interfaces.get("eleSelectedDay"));
    protected Link btnEditBillingAddress = new Link(interfaces.get("btnEditBillingAddress"));

    protected Element eleShippingAddressContent(String recipient) {
	return new Element(interfaces.get("eleShippingAddressContent"), recipient.replace(" ", "+"));
    }

    protected Link btnEditShippingAddress(Element eleShippingAddressContent) {
	return new Link(eleShippingAddressContent, interfaces.get("btnEditShippingAddress"));
    }

    // ================================================================================
    // Methods
    // ================================================================================
    public void applyGiftNumber(String giftnumber) {
	Common.enter(txtGiftNumber, giftnumber);
	Common.click(btnApply);
    }

    public void clickRecipientEditShippingAddressLink(Recipient recipient) {
	Link lnkRecipientEditShippingAddress = new Link(interfaces.get("lnkRecipientEditShippingAddress"), recipient.getValue());
	Common.click(lnkRecipientEditShippingAddress, false);
    }

    public void clickRecipientViewAllShippingOptions(Recipient recipient) {
	Link lnkRecipientViewAllShippingOptions = new Link(interfaces.get("lnkRecipientViewAllShippingOptions"), recipient.getValue());
	Common.click(lnkRecipientViewAllShippingOptions, false);
    }

    public String getGreetingMessageByRecipient(Recipient recipient) {
	Element eleGreetingMessageByRecipient = new Element(interfaces.get("eleGreetingMessageByRecipient"), recipient.getValue());
	if (eleGreetingMessageByRecipient.isDisplayed()) {
	    Common.focus(eleGreetingMessageByRecipient);
	    return eleGreetingMessageByRecipient.getText();
	} else
	    return "";
    }

    public Boolean isGreetingCardByRecipientAndCardTypeExisted(Recipient recipient, String cardType) {
	Element eleGreetingCardByRecipientAndCardType = new Element(interfaces.get("eleGreetingCardByRecipientAndCardType"), recipient.getValue(), cardType);
	return eleGreetingCardByRecipientAndCardType.isDisplayed();
    }

    public void clickEditBillingAddressLink() {
	Common.click(btnEditBillingAddress, false);
    }

    public void placeMyOrder() {
	Common.click(btnPlaceMyOrder);
	Common.waitForPageLoad();
    }
    
    public void placeMyOrderIgnoreError() {
	placeMyOrder();
	if (btnPlaceMyOrder.isDisplayed(Constants.SLEEP_TIME))
	    placeMyOrder();
    }

    public void fillCreditCardNumber() {
	fillCreditCardNumber(Constants.CREDIT_CARD_NUMBER, true);
    }

    public void fillCreditCardNumber(String creditCardNumber, boolean isFutureDate) {
	Common.enter(txtCreditCardNumber, creditCardNumber);
	if (isFutureDate)
	    Common.select(cbbCreditCardYear, Common.randomYearOfFuture());
	else {
	    Common.select(cbbCreditCardMonth, "01 - January");
	    Common.select(cbbCreditCardYear, Common.getCurrentYear());
	}
    }

    public String[] getBillingAddress() {
	Common.waitForDisplayed(eleBillingAddressContent);
	String[] lstArs = eleBillingAddressContent.getText().trim().split("\n");
	List<String> lstResult = new ArrayList<String>();
	for (String ars : lstArs) {
	    ars = ars.trim();
	    if (!ars.isEmpty())
		lstResult.add(ars);
	}
	return lstResult.toArray(new String[lstResult.size()]);
    }

    public void selectShippingOptionByName(String shippingType) {
	CheckBox chbShippingOptionByName = new CheckBox(interfaces.get("chbShippingOptionByNameInModal"), shippingType);
	Common.check(chbShippingOptionByName);
    }

    public String selectAvailableShippingDay() {
	Common.click(btnGoToTheNextMonth, false);
	Common.waitForDisplayed(eleAvailableDayList);
	String flag = "";
	int count = eleAvailableDayList.getElements().size();
	int index = 0;
	while (count > 0 && flag.equals("")) {
	    Common.click(eleAvailableDayList.getElements().get(index), false);
	    eleSelectedDay.waitForDisplay();
	    if (eleSelectedDay.isDisplayed()) {
		flag = eleSelectedDay.getAttribute("id");
	    }
	    count = count - 1;
	    index = index + 1;
	}
	return flag;
    }

    public String getSelectedDay() {
	Common.waitForDisplayed(eleSelectedDay);
	return eleSelectedDay.getAttribute("id");
    }

    public boolean isCheckboxShippingOptionByNameOfRecipientChecked(Recipient recipient, String shippingType) {
	CheckBox chbShippingOptionByNameOfRecipient = new CheckBox(interfaces.get("chbShippingOptionByNameOfRecipient"), recipient.getValue(), shippingType);
	Common.waitForDisplayed(chbShippingOptionByNameOfRecipient);
	return chbShippingOptionByNameOfRecipient.isChecked();
    }

    public String getShippingDateByNameOfRecipientChecked(Recipient recipient, String shippingType) {
	CheckBox eleShippingDateByNameOfRecipient = new CheckBox(interfaces.get("eleShippingDateByNameOfRecipient"), recipient.getValue(), shippingType);
	Common.waitForDisplayed(eleShippingDateByNameOfRecipient);
	return eleShippingDateByNameOfRecipient.getText().trim();
    }

    public void continueCheckout() {
	Common.click(btnContinueCheckOut, false);
    }

    public String[] getShippingAddress(Recipient recipient) {
	Element ele = eleShippingAddressContent(recipient.getValue());
	Common.waitForDisplayed(ele);
	List<String> lstArs = Arrays.asList(ele.getText().trim().split("\n"));
	List<String> lstResult = new ArrayList<String>();
	for (String ars : lstArs) {
	    ars = ars.trim();
	    if (!ars.isEmpty())
		lstResult.add(ars);
	}
	return lstResult.toArray(new String[lstResult.size()]);
    }

    public String getInvalidMessageByField(AddressFields field) {
	Element eleInvalidMessageByField = new Element(interfaces.get("eleErrorMessageByField"), field.getValue());
	if (eleInvalidMessageByField.isDisplayed(Constants.SHORT_TIME)) {
	    return eleInvalidMessageByField.getText();
	} else
	    return "";
    }
}
*/