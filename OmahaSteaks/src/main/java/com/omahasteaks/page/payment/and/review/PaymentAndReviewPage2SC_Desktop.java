package com.omahasteaks.page.payment.and.review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.helper.Logger;
import com.omahasteaks.utils.common.Constants;

public class PaymentAndReviewPage2SC_Desktop extends GeneralPage_Desktop implements PaymentAndReviewPage2SC {

	// ================================================================================
	// Locators
	// ================================================================================

	protected TextBox txtFirstNameInModal = new TextBox(interfaces.get("txtFirstNameInModal"));
	protected TextBox txtLastNameInModal = new TextBox(interfaces.get("txtLastNameInModal"));
	protected TextBox txtCompanyInModal = new TextBox(interfaces.get("txtCompanyInModal"));
	protected TextBox txtPhoneInModal = new TextBox(interfaces.get("txtPhoneInModal"));
	protected TextBox txtEmailInModal = new TextBox(interfaces.get("txtEmailInModal"));
	protected TextBox txtStreetAddressInModal = new TextBox(interfaces.get("txtStreetAddressInModal"));
	protected TextBox txtAptSuiteEtcInModal = new TextBox(interfaces.get("txtAptSuiteEtcInModal"));
	protected TextBox txtZipCodeInModal = new TextBox(interfaces.get("txtZipCodeInModal"));
	protected TextBox txtCityInModal = new TextBox(interfaces.get("txtCityInModal"));
	protected TextBox txtFirstName = new TextBox(interfaces.get("txtFirstName"));
	protected TextBox txtLastName = new TextBox(interfaces.get("txtLastName"));
	protected TextBox txtCompany = new TextBox(interfaces.get("txtCompany"));
	protected TextBox txtPhoneNumber = new TextBox(interfaces.get("txtPhoneNumber"));
	protected TextBox txtEmail = new TextBox(interfaces.get("txtEmail"));
	protected TextBox txtStreetAddress = new TextBox(interfaces.get("txtStreetAddress"));
	protected TextBox txtAptSuite = new TextBox(interfaces.get("txtAptSuite"));
	protected TextBox txtZipCode = new TextBox(interfaces.get("txtZipCode"));
	protected TextBox txtCity = new TextBox(interfaces.get("txtCity"));
	protected TextBox txtCardNumber = new TextBox(interfaces.get("txtCardNumber"));
	protected TextBox txtCardExpire = new TextBox(interfaces.get("txtCardExpire"));
	protected TextBox txtCardCVV = new TextBox(interfaces.get("txtCardCVV"));
	protected TextBox txtCardCVV2 = new TextBox(interfaces.get("txtCardCVV2"));
	protected TextBox txtFirstCardAmount = new TextBox(interfaces.get("txtFirstCardAmount"));
	protected TextBox txtSecondCreditCardNumber = new TextBox(interfaces.get("txtSecondCreditCardNumber"));
	protected TextBox txtSecondCreditCardExpire = new TextBox(interfaces.get("txtSecondCreditCardExpire"));
	protected TextBox txtSecondCardAmount = new TextBox(interfaces.get("txtSecondCardAmount"));
	protected TextBox txtGiftMessageInModal = new TextBox(interfaces.get("txtGiftMessageInModal"));
	protected TextBox txtSignatureInModal = new TextBox(interfaces.get("txtSignatureInModal"));

	protected Button btnPlaceOrder = new Button(interfaces.get("btnPlaceOrder"));
	protected Button btnNoThanksGifting = new Button(interfaces.get("btnNoThanksGifting"));
	protected Button btnSubmitGiftMessage = new Button(interfaces.get("btnSubmitGiftMessage"));
	protected Button btnSaveAndContinueInModal = new Button(interfaces.get("btnSaveAndContinueInModal"));
	protected Button btnCancelInModal = new Button(interfaces.get("btnCancelInModal"));
	protected Button btnUpdateContactInModal = new Button(interfaces.get("btnUpdateContactInModal"));
	protected Button btnCheckOutWithPayPal = new Button(interfaces.get("btnCheckOutWithPayPal"));

	protected Image imgSecondGreetingCardImageInModal = new Image(interfaces.get("imgSecondGreetingCardImageInModal"));
	protected Image cbSecondGreetingCardImageInModal = new Image(interfaces.get("cbSecondGreetingCardImageInModal"));

	protected ComboBox cbbCountryInModal = new ComboBox(interfaces.get("cbbCountryInModal"));
	protected ComboBox cbbStateInModal = new ComboBox(interfaces.get("cbbStateInModal"));
	protected ComboBox cbbState = new ComboBox(interfaces.get("cbbState"));
	protected ComboBox cbbCountry = new ComboBox(interfaces.get("cbbCountry"));
	protected ComboBox cbbGiftMessageInModal = new ComboBox(interfaces.get("cbbGiftMessageInModal"));

	protected Label lblCartChanged = new Label(interfaces.get("lblCartChanged"));
	
	
	protected CheckBox chkboxSameAsMyAddress = new CheckBox(interfaces.get("chkboxSameAsMyAddress"));
	protected CheckBox spanSameAsMyAddress = new CheckBox(interfaces.get("spanSameAsMyAddress"));

	protected Link getLinkEditShippingAddr(String recipient) {
		return new Link(interfaces.get("linkEditShippingAddr"), recipient);
	}

	protected Link linkEditBillingAddress = new Link(interfaces.get("linkEditBillingAddress"));

	protected Link lnkPayWithTwoCards = new Link(interfaces.get("lnkPayWithTwoCards"));
	protected Link lnkRemove2ndCard = new Link(interfaces.get("lnkRemove2ndCard"));

	protected Link lnkApplyAVoucher = new Link(interfaces.get("lnkApplyAVoucher"));
	protected Link lnkEditTotalItems = new Link(interfaces.get("lnkEditTotalItems"));

	protected Link lnkEditCartByRecipient(String recipient) {
		return new Link(interfaces.get("lnkEditCartByRecipient"), recipient);
	}

	protected Element eleShippingAddressContent(String recipient) {
		return new Element(interfaces.get("eleShippingAddressContent"), recipient);
	}

	protected Element eleErrorMessageAtBottom = new Element(interfaces.get("eleErrorMessageAtBottom"));
	protected Element eleTotalPrice = new Element(interfaces.get("eleTotalPrice"));
	protected Element eleErrorMessage = new Element(interfaces.get("eleErrorMessage"));
	protected Element eleErrorMessageInModal = new Element(interfaces.get("eleErrorMessageInModal"));
	protected Element eleAddAGreetingCardInModal = new Element(interfaces.get("eleAddAGreetingCardInModal"));
	protected Element eleAddGiftWrapInModal = new Element(interfaces.get("eleAddGiftWrapInModal"));
	protected Element eleAddBothInModal = new Element(interfaces.get("eleAddBothInModal"));
	protected Element eleAvailableDayList = new Element(interfaces.get("eleAvailableDayList"));
	protected Element eleSelectedDay = new Element(interfaces.get("eleSelectedDay"));
	protected Element elePayPalTab = new Element(interfaces.get("elePayPalTab"));
	protected Element eleTotalShippingFee = new Element(interfaces.get("eleTotalShippingFee"));
	protected Element eleTaxAmount = new Element(interfaces.get("eleTaxAmount"));
	protected Element eleShippingMethodsList = new Element(interfaces.get("eleShippingMethodsList"));
	protected Element eleCreditCardTab = new Element(interfaces.get("eleCreditCardTab"));
	protected Element eleWeAreSorryModal = new Element(interfaces.get("eleWeAreSorryModal"));
	protected Label lblRemainingCardBalance = new Label(interfaces.get("lblRemainingCardBalance"));
	protected Label titleShippingAndDeliveryPopup = new Label(interfaces.get("titleShippingAndDeliveryPopup"));
	protected Element eleProcessing = new Element(interfaces.get("eleProcessing"));
	// ================================================================================
	// Support Methods
	// ================================================================================

	protected void clearBillingAddress() {
		// Do nothing on desktop
	}

	protected void clearBillingAddressInModal() {
		// Do nothing on desktop
	}

	protected String getGiftWrapAndGreetingCard(String recipient) {
		Label lbGiftWrapAndGreetingCard = new Label(interfaces.get("lbGiftWrapAndGreetingCard"), recipient);
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;

		while (!lbGiftWrapAndGreetingCard.isDisplayed()) {
			if (isLinkAddGiftWrapAndGreetingCardDisplayed(recipient) || endTime > timeout)
				return "";
			DriverUtils.delay(1);
			endTime = System.currentTimeMillis() - startTime;
		}
		return lbGiftWrapAndGreetingCard.getText();
	}

	protected boolean isLinkAddGiftWrapAndGreetingCardDisplayed(String recipient) {
		Link lnkAddGiftWrapAndGreetingCard = new Link(interfaces.get("lnkAddGiftWrapAndGreetingCard"), recipient);
		return lnkAddGiftWrapAndGreetingCard.isDisplayed();
	}

	protected String getPromotionsMsgInShippingAndDeliveryPopup(String shippingMethod) {
		Element lbPromotionMsgInShippingAndDeliveryPopup = new Element(
				interfaces.get("lbPromotionMsgInShippingAndDeliveryPopup"), shippingMethod);
		return Common.getText(lbPromotionMsgInShippingAndDeliveryPopup);
	}

	// ================================================================================
	// Methods
	// ================================================================================

	public void fillBillingAddress(CustomerAddress billingAddress) {
		Common.waitForPageLoad();
		if (!txtFirstName.isDisplayed()) return;
		
		Common.waitForDisplayed(txtFirstName);
		Common.modalDialog.closeUnknownModalDialog();
		clearBillingAddress();
		Common.enter(txtFirstName, billingAddress.firstName);
		Common.enter(txtLastName, billingAddress.lastName);
		Common.enter(txtCompany, billingAddress.companyName);
		Common.enter(txtPhoneNumber, billingAddress.phoneNumber);
		if (txtEmail.isDisplayed()) Common.enter(txtEmail, billingAddress.email);
		Common.enter(txtStreetAddress, billingAddress.streetAddress1);
		Common.enter(txtAptSuite, billingAddress.aptSuite);
		Common.select(cbbCountry, billingAddress.country);
		Common.select(cbbState, billingAddress.state);
		Common.enter(txtCity, billingAddress.city);
		Common.enter(txtZipCode, billingAddress.zipCode);
		Common.delay(1);
		if (!billingAddress.zipCode.equals(""))
			Common.triggerTextBoxChangeEvent(txtZipCode);
	}

	public void fillEmailAndPhone(CustomerAddress billingAddress) {
		Common.modalDialog.closeUnknownModalDialog();
		Common.enter(txtPhoneNumber, billingAddress.phoneNumber);
		Common.enter(txtEmail, billingAddress.email);
	}

	public void selectSameAsMyAddress() {
		Common.click(spanSameAsMyAddress, false);
		Common.waitForChecked(chkboxSameAsMyAddress);
	}

	public void fillCreditCardNumber() {
		Logger.substep("Enter \""+Constants.CREDIT_CARD_NUMBER+"\" into the creditcard field.");
		fillCreditCardNumber(Constants.CREDIT_CARD_NUMBER, true);
	}

	public void fillSecondCreditCardNumber() {
		fillSecondCreditCardNumber(Constants.CREDIT_CARD_NUMBER_2, true);
	}

	public void fillCreditCardNumber(String creditCardNumber, boolean isFutureDate) {
		Common.enter(txtCardNumber, creditCardNumber);
		if (isFutureDate)
			Common.enter(txtCardExpire, "01/" + Common.randomYearOfFuture());
		else {
			Common.enter(txtCardExpire, "12/" + (Common.getCurrentYear() - 1));
		}
		Common.enter(txtCardCVV, "123");
		Common.focus(txtCardExpire);
	}

	/**
	 * @param creditCardNumber
	 * @param date
	 *            should follow this format MM/YY (e.g. 01/23)
	 */
	public void fillCreditCardNumber(String creditCardNumber, String date) {
		Common.enter(txtCardNumber, creditCardNumber);
		Common.enter(txtCardExpire, date);
		Common.enter(txtCardCVV, "555");
		Common.focus(txtCardExpire);
	}

	public String getCreditCardType() {
		Element eleCreditCardType = new Element(interfaces.get("eleCreditCardType"));
		String type = eleCreditCardType.getAttribute("class").split("-")[2];
		switch (type) {
		case "mastercard":
			return "Master Card";
		case "discover":
			return "Discover";
		case "amex":
			return "Amex";
		default:
			return "Visa";
		}
	}

	public void fillSecondCreditCardNumber(String creditCardNumber, boolean isFutureDate) {
		Common.enter(txtSecondCreditCardNumber, creditCardNumber);
		if (isFutureDate)
			Common.enter(txtSecondCreditCardExpire, "01/" + Common.randomYearOfFuture());
		else {
			Common.enter(txtSecondCreditCardExpire, "12/" + (Common.getCurrentYear() - 1));
		}
		Common.enter(txtCardCVV2, "777");
	}

	public void fillFirstCreditCardAmount(double amount) {
		Common.enter(txtFirstCardAmount, String.format("%1$,.2f", amount));
		Common.triggerTextBoxChangeEvent(txtFirstCardAmount);
	}

	public boolean isFirstCreditCardAmountDisplayed() {
		return txtFirstCardAmount.isDisplayed();
	}

	public void fillFirstCreditCardRandomAmount() {
		fillFirstCreditCardAmount((Math.random() * ((Double.parseDouble(getTotalPrice().replace("$", ""))) + 1)));
	}

	public double getFirstCreditCardAmount() {
		Common.waitForDisplayed(txtFirstCardAmount);
		return Double.parseDouble(txtFirstCardAmount.getValue().trim().replace("$", ""));
	}

	public double getSecondCreditCardAmount() {
		Common.waitForDisplayed(txtSecondCardAmount);
		return Double.parseDouble(txtSecondCardAmount.getValue().trim().replace("$", ""));
	}

	public void fillSecondCreditCardAmount(double amount) {
		Common.waitForDisplayed(txtSecondCardAmount);
		Common.enter(txtSecondCardAmount, String.format("%1$,.2f", amount));
	}

	public void placeOrder() {
		Logger.substep("Click \"Place Order\" button.");
		Common.tryClickByJs(btnPlaceOrder);  
		Common.waitForPageLoad();
		Common.waitForNotDisplayed(eleProcessing, Constants.LONG_TIME);
		Common.modalDialog.closeModalDialog();
	 	Common.waitForDOMChange();
	}

	public void placeOrderIgnoreError() {
		Common.delay(3);
		placeOrder();
		DriverUtils.delay(Constants.SLEEP_TIME);
		if (btnPlaceOrder.isDisplayed(Constants.SLEEP_TIME))
			placeOrder();
	}

	public void selectEditShippingAddrLink(String recipient) {
		Link linkEditShippingAddr = getLinkEditShippingAddr(recipient);
		Common.click(linkEditShippingAddr);
		Common.waitForNotDisplayed(linkEditShippingAddr);
	}

	public String getRecipientSectionTitle(String recipient) {
		Element eleRecipientSectionTitle = new Element(interfaces.get("eleRecipientSectionTitle"), recipient);
		return Common.getText(eleRecipientSectionTitle);
	}

	public void selectEditBillingAddressLink() {
		Common.click(linkEditBillingAddress);

	}

	public String[] getShippingAddress(String recipient) {
		Element ele = eleShippingAddressContent(recipient);
		ele.waitForDisplay(Constants.SHORT_TIME);
		List<String> lstArs = Arrays.asList(ele.getText().trim().split("\n"));
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public String getWineInCartMesssage(String recipient) {
		Label lbMessageWineInCart = new Label(interfaces.get("lbMessageWineInCart"), recipient);
		Common.waitForDisplayed(lbMessageWineInCart);
		Common.scrollElementToCenterScreen(lbMessageWineInCart);
		return lbMessageWineInCart.getText();
	}

	public String getGiftMessage(String recipient) {
		Label lbGiftMessage = new Label(interfaces.get("lbGiftMessage"), recipient);
		Link lnkAddGiftMessage = new Link(interfaces.get("lnkAddGiftMessage"), recipient);

		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;

//		while (!lbGiftMessage.isDisplayed()) {
//			if (lnkAddGiftMessage.isDisplayed() || endTime > timeout)
//				return "";
//			DriverUtils.delay(1);
//			endTime = System.currentTimeMillis() - startTime;
//		}

		Common.scrollElementToCenterScreen(lbGiftMessage);
		String x = lbGiftMessage.getText();
		System.out.println("gift message :" + x);
		return lbGiftMessage.getText();
	}

	public void editGiftMessage(String recipient, String message, String signature) {
		Link lnkEditGiftMessage = new Link(interfaces.get("lnkEditGiftMessageByRecipientName"), recipient);
		Common.click(lnkEditGiftMessage);
		Common.enter(txtGiftMessageInModal, message);
		Common.enter(txtSignatureInModal, signature);
		Common.click(btnSubmitGiftMessage);
		Common.waitForNotDisplayed(btnSubmitGiftMessage);
	}

	public void selectShippingMethodInShippingAndDeliveryModal(String shippingMethod) {
		CheckBox chbShippingMethodByOption = new CheckBox(interfaces.get("chbShippingMethodByOption"), shippingMethod);
		Element eleShippingMethodInModalByOption = new Element(interfaces.get("eleShippingMethodInModalByOption"),
				shippingMethod);
		if (!chbShippingMethodByOption.isChecked()) {
			Common.click(eleShippingMethodInModalByOption, false);
			Common.waitForChecked(chbShippingMethodByOption);
		}
	}

	public String selectRandomShippingMethodInShippingAndDeliveryModal() {
		List<String> shippingMethodsList = getShippingMethodsListInShippingAndDeliveryModal();
		return selectRandomShippingMethodInShippingAndDeliveryModal(shippingMethodsList);
	}

	public String selectRandomShippingMethodInShippingAndDeliveryModal(List<String> shippingMethodsList) {
		Random rand = new Random();
		String shippingMethod = shippingMethodsList.get(rand.nextInt(shippingMethodsList.size() - 1) + 1);
		selectShippingMethodInShippingAndDeliveryModal(shippingMethod);
		return shippingMethod;
	}

	public List<String> getShippingMethodsListInShippingAndDeliveryModal() {
		List<String> lstResult = new ArrayList<String>();
		Common.waitForDisplayed(eleShippingMethodsList);
		for (WebElement webElement : eleShippingMethodsList.getElements()) {
			lstResult.add(webElement.getText().trim());
		}
		return lstResult;
	}

	public void clickViewAllShippingOptionsByRecipientNameLink(String recipient) {
		Link lnkViewAllShippingOptionsByRecipientName = new Link(
				interfaces.get("lnkViewAllShippingOptionsByRecipientName"), recipient);
		Common.waitForDOMChange();
		Common.click(lnkViewAllShippingOptionsByRecipientName);
		Common.waitForDOMChange();
	}

	public void removeGiftingOption(String recipient) {
		clickEditGiftOptionLinkByRecipientName(recipient);
		Common.click(btnNoThanksGifting);
		Common.waitForNotDisplayed(btnNoThanksGifting);
	}

	public void clickEditGiftOptionLinkByRecipientName(String recipient) {
		Link lnkEditGiftOptionByRecipientName = new Link(interfaces.get("lnkEditGiftOptionByRecipientName"), recipient);
		Common.waitForDOMChange();
		Common.click(lnkEditGiftOptionByRecipientName);
	}

	public boolean isGiftWrapAdded(String recipient) {
		return getGiftWrapAndGreetingCard(recipient).contains("Gift Wrap");
	}

	public boolean isGreetingCardAdded(String recipient, String cardName) {
		return getGiftWrapAndGreetingCard(recipient).contains(cardName + " Card");
	}

	public String getShippingInfo(String recipient) {
		Element eleShippingSectionByRecipientName = new Element(interfaces.get("eleShippingSectionByRecipientName"),
				recipient);
		Common.waitForDisplayed(eleShippingSectionByRecipientName);
		Common.scrollElementToCenterScreen(eleShippingSectionByRecipientName);
		DriverUtils.delay(1);
		return Common.getText(eleShippingSectionByRecipientName).replace(" to ", " - ");
	}

	public Calendar getDeliveryDate(String recipient) {
		Calendar deliveryDate = null;
		try {
			Element eleShippingSectionByRecipientName = new Element(interfaces.get("eleShippingSectionByRecipientName"),
					recipient);
			Element eleDeliveryDate = new Element(eleShippingSectionByRecipientName, interfaces.get("eleDeliveryDate"));
			Common.waitForDisplayed(eleShippingSectionByRecipientName);
			Common.scrollElementToCenterScreen(eleDeliveryDate);
			DriverUtils.delay(1);
 			deliveryDate = Common
					.dateToCalendar(new SimpleDateFormat("EEE, MMM dd").parse(Common.getText(eleDeliveryDate)));
			deliveryDate.set(Calendar.YEAR, Common.getCurrentYear());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return deliveryDate;
	}

	public String getTotalPrice() {
		Common.waitForDisplayed(eleTotalPrice);
		Common.scrollElementToCenterScreen(eleTotalPrice);
		return eleTotalPrice.getText().trim();
	}

	public void clickPayWithTwoCardsLink() {
		Common.click(lnkPayWithTwoCards);
	}

	public boolean isPayWithTwoCardsLinkDisplayed() {
		return lnkPayWithTwoCards.isDisplayed();
	}

	public void clickRemove2ndCardLink() {
		Common.click(lnkRemove2ndCard);
	}

	public boolean isRemove2ndCardLinkDisplayed() {
		return lnkRemove2ndCard.isDisplayed();
	}

	public void clickSaveAndContinueButtonInModal() {
		Common.click(btnSaveAndContinueInModal, false);
		Common.waitForNotDisplayed(btnCancelInModal);
	}

	public String getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(String shippingMethod) {
		Element eleShippingEstimatedDeliveryByOptionInModal = new Element(
				interfaces.get("eleShippingEstimatedDeliveryByOptionInModal"), shippingMethod);
		Common.waitForDisplayed(eleShippingEstimatedDeliveryByOptionInModal);
		return Common.getText(eleShippingEstimatedDeliveryByOptionInModal).split("Plus")[0].trim();
	}

	public String getShippingMethodCostInShippingDeliveryModal(String shippingMethod) {
		Element eleShippingCostByOptionInModal = new Element(interfaces.get("eleShippingCostByOptionInModal"),
				shippingMethod);
		Common.waitForDisplayed(eleShippingCostByOptionInModal);
		return eleShippingCostByOptionInModal.getText().trim().toUpperCase();
	}

	public String getGreetingCardImageByRecipientName(String recipient) {
		Image imgGreetingCardImageByRecipientName = new Image(interfaces.get("imgGreetingCardImageByRecipientName"),
				recipient);
		Common.moveTo(imgGreetingCardImageByRecipientName);
		String src = imgGreetingCardImageByRecipientName.getSource();
		int loop = Constants.SHORT_TIME;
		while (loop > 0 && !src.contains("www.omastk.com")) {
			loop -= Constants.SLEEP_TIME;
			src = imgGreetingCardImageByRecipientName.getSource();
		}
		return imgGreetingCardImageByRecipientName.getAlt() + " - " + imgGreetingCardImageByRecipientName.getSource();
	}

	public void selectGiftOptionGreetingCardInModal() {
		Common.click(eleAddAGreetingCardInModal, false);
	}

	public String selectSecondGreetingCardInModal() {
		String str = imgSecondGreetingCardImageInModal.getAlt() + " - " + imgSecondGreetingCardImageInModal.getSource();
		if (DriverUtils.getDriverType().equals(Constants.BROWSER_IE)) {
			Common.click(cbSecondGreetingCardImageInModal, false);
		} else
			Common.click(imgSecondGreetingCardImageInModal, false);
		Common.waitForPageLoad();
		return str;
	}

	public String getErrorMessageByField(AddressFields addrField) {

		String tmpField = addrField.getValue2SC();
		if (addrField.equals(AddressFields.PHONE))
			tmpField = "Phone";
		Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField"), tmpField);
		Common.waitForDisplayed(eleErrorMessageByField);
		return Common.getText(eleErrorMessageByField);
	}

	public String getErrorMessage() {
		return Common.getText(eleErrorMessage);
	}

	public String getErrorMessageInModal() {
		return Common.getText(eleErrorMessageInModal);
	}

	public void fillBillingAddressInModal(CustomerAddress address) {
		Common.waitForPageLoad();
		Common.enter(txtFirstNameInModal, address.firstName);
		Common.enter(txtLastNameInModal, address.lastName);
		Common.enter(txtCompanyInModal, address.companyName);
		Common.enter(txtPhoneInModal, address.phoneNumber);
		if (txtEmailInModal.isDisplayed()) {
			Common.enter(txtEmailInModal, address.email);
		}
		clearBillingAddressInModal();
		Common.enter(txtStreetAddressInModal, address.streetAddress1);
		Common.enter(txtAptSuiteEtcInModal, address.aptSuite);
		Common.enter(txtCityInModal, address.city);
		cbbCountryInModal.select(address.country);
		if (address.state == "" || address.state == "Select" || address.state == "State *") {
			address.state = cbbStateInModal.getOptions().contains("Select") ? "Select" : "State *";
			Common.select(cbbStateInModal, address.state, ActionType.JS, false);
		} else
			Common.selectByJs(cbbStateInModal, address.state);
		Common.enter(txtZipCodeInModal, address.zipCode);
		if (!address.zipCode.equals("")) {
			Common.triggerTextBoxChangeEvent(txtZipCodeInModal);
		}
	}

	public void updateContactInModal() {
		Common.click(btnUpdateContactInModal, false);
		Common.waitForPageLoad();
	}

	public String getErrorMessageAtBottom() {
		Common.waitForDisplayed(eleErrorMessageAtBottom);
		// Delay 1s for waiting animation effect!
		DriverUtils.delay(2);
		List<WebElement> lwe = eleErrorMessageAtBottom.getElements();
		return lwe.get(lwe.size() - 1).getText().replace("\n", " ").trim().split(" Shipping Policy")[0];
	}

	public void clickCancelButtonInModal() {
		Common.click(btnCancelInModal, false);
		Common.waitForNotDisplayed(btnCancelInModal);
	}

	public void clickPayPalTab() {
		elePayPalTab.waitForVisibility();
		Common.click(elePayPalTab);
	}

	public void clickCheckOutWithPayPal() {
		btnCheckOutWithPayPal.waitForVisibility();
		Common.click(btnCheckOutWithPayPal);
		Common.waitForPageLoad();
	}

	public boolean isPayPalTabDisplayed() {
		return elePayPalTab.isDisplayed();
	}

	public boolean isSKUExisted(SKU sku) {
		Element eleSKUInRecipientSection = new Element(interfaces.get("eleSKUInRecipientSection"),
				sku.getRecipient().getValue(), sku.getName());
		return eleSKUInRecipientSection.isDisplayed();
	}

	public String getNoteOfSKU(SKU sku) {
		Element eleSKUNoteInRecipientSection = new Element(interfaces.get("eleSKUNoteInRecipientSection"),
				sku.getRecipient().getValue(), sku.getName());
		return Common.getText(eleSKUNoteInRecipientSection);
	}

	public String[] convertShippingAddressForMobile(String[] shippingAddress) {
		return shippingAddress;
	}

	public void clickApplyARewardCardGiftCardOrVoucherLink() { 
		Common.waitForDOMChange();
		//lnkApplyAVoucher.waitForVisibility();
		Common.click(lnkApplyAVoucher);
		Common.waitForDOMChange();
		Common.waitForPageLoad();
	}

	public void selectLinkEditCartByRecipient(Recipient recipient) {
		Common.click(lnkEditCartByRecipient(recipient.getValue()));
	}

	public String getExtraShippingFee(String recipient) {
		Element eleExtraShippingFee = new Element(interfaces.get("eleExtraShippingFee"), recipient);
		Common.waitForDisplayed(eleExtraShippingFee);
		Common.scrollElementToCenterScreen(eleExtraShippingFee);
		return Common.getText(eleExtraShippingFee);
	}

	public String getColorOfShippingCostInOrderTotalsSection() {
		Common.waitForDisplayed(eleTotalShippingFee);
		String tmpColor = eleTotalShippingFee.getElement().getCssValue("color");
		return Color.fromString(tmpColor).asHex();
	}

	public String getTotalShippingFee() {
		Common.waitForPageLoad();
		Common.waitForDisplayed(eleTotalShippingFee);
		return Common.getText(eleTotalShippingFee).toUpperCase();
	}

	public boolean isViewAllShippingOptionsDisplayed(String recipient) {
		Link lnkViewAllShippingOptionsByRecipientName = new Link(
				interfaces.get("lnkViewAllShippingOptionsByRecipientName"), recipient);
		Common.waitForDOMChange();
		return lnkViewAllShippingOptionsByRecipientName.isDisplayed();
	}

	public boolean isShippingOptionsDisplayedCorrectly() {
		List<String> lstShippingMethods = getShippingMethodsListInShippingAndDeliveryModal();

		if (!lstShippingMethods.get(0).equals(Constants.STANDARD_SHIPPING))
			return false;

		List<String> lstHolidayShippingMethods = Arrays.asList(Constants.HOLIDAY_DELIVERY_SHIPPING);
		if (!lstHolidayShippingMethods.contains(lstShippingMethods.get(1)))
			return false;
		return true;
	}

	public String getTaxAmountInOrderTotal() {
		Common.waitForDisplayed(eleTaxAmount);
		return Common.getText(eleTaxAmount).trim();
	}

	public boolean isPayWithCreditCardSectionDisplayed() {
		return eleCreditCardTab.isDisplayed();
	}

	public boolean isRewardCardDisplayed(String rewardCardCode) {
		Element lblReWardCardCode = new Element(interfaces.get("lblReWardCardCode"), rewardCardCode);
		Common.waitForDOMChange();
		return lblReWardCardCode.isDisplayed();
	}

	public void removeRewardCard(String rewardCardCode) {
		Element btnRemoveReWardCardCode = new Element(interfaces.get("btnRemoveReWardCardCode"), rewardCardCode);
		Common.waitForClickable(btnRemoveReWardCardCode);
		Common.click(btnRemoveReWardCardCode);
		Common.waitForPageLoad();
	}

	public String getSelectedShippingMethod(String recipent) {
		CheckBox cbShippingOptionsLst = new CheckBox(interfaces.get("cbShippingOptionsLst"), recipent);
		Element eleShippingMethodLst = new Element(interfaces.get("eleShippingMethodLst"), recipent);
		for (int i = 0; i < cbShippingOptionsLst.getElements().size(); i++) {
			if (cbShippingOptionsLst.getElements().get(i).isSelected())
				return eleShippingMethodLst.getText();
		}
		return "";
	}

	public String getPromotionMessage(String recipient) {
		Element elePromotionMsg = new Element(interfaces.get("elePromotionMsg"), recipient);
		Common.scrollElementToCenterScreen(elePromotionMsg);
		return Common.getText(elePromotionMsg);
	}

	public String selectRandomShippingMethod(String recipient) {
		List<String> shippingMethodsList = getShippingMethodsList(recipient);
		Random rand = new Random();
		String shippingMethod = shippingMethodsList.get(rand.nextInt(shippingMethodsList.size()));
		selectShippingMethod(recipient, shippingMethod);
		return shippingMethod;
	}

	public List<String> getShippingMethodsList(String recipient) {
		List<String> lstResult = new ArrayList<String>();
		Element eleShippingMethodLst = new Element(interfaces.get("eleShippingMethodLst"), recipient);
		Common.waitForDisplayed(eleShippingMethodLst);
		for (WebElement webElement : eleShippingMethodLst.getElements()) {
			lstResult.add(webElement.getText().trim());
		}
		return lstResult;
	}

	public void selectShippingMethod(String recipient, String shippingMethod) {
		CheckBox cbShippingMethodByOption = new CheckBox(interfaces.get("cbShippingMethodByOption"), recipient,
				shippingMethod);
		Element eleShippingMethodByOption = new Element(interfaces.get("eleShippingMethodByOption"), recipient,
				shippingMethod);
		if (!cbShippingMethodByOption.isChecked()) {
			Common.click(eleShippingMethodByOption);
			Common.waitForChecked(cbShippingMethodByOption);
		}
	}

	public boolean isShippingMethodDisplayed(String recipient, String shippingMethod) {
		Element eleShippingMethodByOption = new Element(interfaces.get("eleShippingMethodByOption"), recipient,
				shippingMethod);
		return eleShippingMethodByOption.isDisplayed();
	}

	public boolean isPromotionsMsgDisplayedInShippingAndDeliveryPopup(List<String> lstShippingOptions) {
		for (int i = 0; i < lstShippingOptions.size(); i++) {
			if (!getPromotionsMsgInShippingAndDeliveryPopup(lstShippingOptions.get(i).toString())
					.equalsIgnoreCase(Constants.PROMOTIONS_MSG_IN_MODAL))
				return false;
		}
		return true;
	}

	public void clickDetailsLink(String recipient) {
		Element linkDetail = new Element(interfaces.get("linkDetail"), recipient);
		Common.click(linkDetail, false);
		Common.waitForDOMChange();
	}

	public boolean isDetailsPopupDisplayed() {
		return elePopModal.isDisplayed();
	}

	public String getIncentiveOfferTermsAndConditionsMessage() {
		Common.scrollElementToCenterScreen(elePopModal);
		return Common.getText(elePopModal);
	}

	public boolean getCheckedShippingMethod(String recipent, String shippingMethod) {
		CheckBox cbShippingOption = new CheckBox(interfaces.get("cbShippingOption"), recipent, shippingMethod);
		Common.scrollElementToCenterScreen(cbShippingOption);
		return cbShippingOption.isChecked();
	}

	public boolean isWeAreSorryModalDisplayed() {
		return eleWeAreSorryModal.isDisplayed();
	}

	public boolean isLabelRemainingCardBalanceDisplayed() {
		return lblRemainingCardBalance.isDisplayed();
	}

	public String getPromotionMsgAfterUpdatingShippingMethod(String recipent) {
		Element elePromotionMsgAfterUpdateShippingMethod = new Element(
				interfaces.get("elePromotionMsgAfterUpdateShippingMethod"), recipent);
		Common.waitForDisplayed(elePromotionMsgAfterUpdateShippingMethod);
		Common.scrollElementToCenterScreen(elePromotionMsgAfterUpdateShippingMethod);
		return Common.getText(elePromotionMsgAfterUpdateShippingMethod);
	}

	public boolean isShippingMethodInShippingAndDeliveryPopupSelected(String shippingMethod) {
		CheckBox chbShippingMethodByOption = new CheckBox(interfaces.get("chbShippingMethodByOption"), shippingMethod);
		return chbShippingMethodByOption.isChecked();
	}

	public boolean isTitleOfShippingAndDeliveryDisplayedCorrectly() {

		for (WebElement element : titleShippingAndDeliveryPopup.getElements()) {
			if (element.isDisplayed())
				return true;
		}
		return titleShippingAndDeliveryPopup.isDisplayed();
	}

	public String getShippingMethodByDayOfWeek() {
		int curDateOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if (curDateOfWeek >= 2 && curDateOfWeek <= 6) {
			return Constants.RUSH_SHIPPING;
		} else {
			return Constants.SATURDAY_RUSH_SHIPPING;
		}
	}

	public boolean isShippingMethodDisplaysOnPopup(String shippingMethod) {
		Common.waitForDOMChange();
		Element eleShippingMethodInModalByOption = new Element(interfaces.get("eleShippingMethodInModalByOption"),
				shippingMethod);
		System.out.println("Delivery:" + eleShippingMethodInModalByOption.getText());
		return eleShippingMethodInModalByOption.isDisplayed();
	}

	public String getShippingCostByShippingMethod(String recipient, String shippingMethod) {
		Element eleShippingMethodByOption = new Element(interfaces.get("eleShippingCostByOption"), recipient,
				shippingMethod);
		return eleShippingMethodByOption.getText().trim();
	}

	public String getPromotionTextOfShippingMethod(String shippingMethod) {
		Element promotionText = new Element(interfaces.get("promotionMsgForShippingOption"), shippingMethod);
		return Common.getText(promotionText);
	}

}
