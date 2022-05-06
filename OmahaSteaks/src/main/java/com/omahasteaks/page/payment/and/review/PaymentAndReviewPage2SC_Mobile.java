package com.omahasteaks.page.payment.and.review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class PaymentAndReviewPage2SC_Mobile extends PaymentAndReviewPage2SC_Desktop implements PaymentAndReviewPage2SC {
    public PaymentAndReviewPage2SC_Mobile() {
	super();
	btnPlaceOrder = new Button(interfaces.get("btnPlaceOrder_mb"));
	eleShippingMethodsList = new Element(interfaces.get("eleShippingMethodsList_mb"));
	titleShippingAndDeliveryPopup = new Label(interfaces.get("titleShippingAndDeliveryPopup_mb"));
	eleWeAreSorryModal = new Element(interfaces.get("eleWeAreSorryModal_mb"));
	eleProcessing = new Element(interfaces.get("eleProcessing_mb"));
    }

    // ================================================================================
    // Locators
    // ================================================================================
    Link linkEditShippingAddr = new Link(interfaces.get("linkEditShippingAddr_mb"));
    Button btnCancelInModal_mb = new Button(interfaces.get("btnCancelInModal_mb"));
    // ================================================================================
    // Support Methods
    // ================================================================================

    @Override
    protected String getPromotionsMsgInShippingAndDeliveryPopup(String shippingMethod) {
	Element lbPromotionMsgInShippingAndDeliveryPopup_mb = new Element(interfaces.get("lbPromotionMsgInShippingAndDeliveryPopup_mb"), shippingMethod);
	return Common.getText(lbPromotionMsgInShippingAndDeliveryPopup_mb);
    }

    @Override
    protected String getGiftWrapAndGreetingCard(String recipient) {
	Label lbGiftWrapAndGreetingCard = new Label(interfaces.get("lbGiftWrapAndGreetingCard_mb"), recipient);
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

    @Override
    protected void clearBillingAddress() {
	Common.clear(txtStreetAddress);
	Common.clear(txtZipCode);
	Common.clear(txtCity);
	String tmpState = cbbState.getOptions().contains("Select") ? "Select" : "State *";
	cbbState.select(tmpState);
    }

    @Override
    protected void clearBillingAddressInModal() {
	Common.clear(txtStreetAddressInModal);
	Common.clear(txtZipCodeInModal);
	Common.clear(txtCityInModal);
	String tmpState = cbbStateInModal.getOptions().contains("Select") ? "Select" : "State *";
	cbbStateInModal.select(tmpState);
    }

    // ================================================================================
    // Methods
    // ================================================================================

    @Override
    public void selectEditShippingAddrLink(String recipient) {
	Element ele = eleShippingAddressContent(recipient);
	Common.click(ele);
	Common.click(linkEditShippingAddr);
	Common.waitForNotDisplayed(linkEditShippingAddr);
    }

    @Override
    public String[] getShippingAddress(String recipient) {
	Element eleAddr = eleShippingAddressContent(recipient);
	Element eleName = new Element(interfaces.get("eleShippingAddressName_mb"), recipient);
	Common.waitForDisplayed(eleAddr);
	List<String> lstArs = Arrays.asList(eleAddr.getText().trim().split("\n"));
	List<String> lstResult = new ArrayList<String>();
	lstResult.add(eleName.getText().trim());
	for (String ars : lstArs) {
	    ars = ars.trim();
	    if (!ars.isEmpty())
		lstResult.add(ars);
	}
	return lstResult.toArray(new String[lstResult.size()]);
    }

    @Override
    public String getGiftMessage(String recipient) {
	Label lbGiftMessage = new Label(interfaces.get("lbGiftMessage_mb"), recipient);
	Link lnkAddGiftMessage = new Link(interfaces.get("lnkAddGiftMessage"), recipient);
	long startTime = System.currentTimeMillis();
	long endTime = System.currentTimeMillis() - startTime;
	long timeout = Constants.SHORT_TIME * 1000;
	while (!lbGiftMessage.isDisplayed()) {
	    if (lnkAddGiftMessage.isDisplayed() || endTime > timeout)
		return "";
	    DriverUtils.delay(1);
	    endTime = System.currentTimeMillis() - startTime;
	}
	Common.scrollElementToCenterScreen(lbGiftMessage);
	return lbGiftMessage.getText();
    }

    @Override
    public String getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(String shippingOption) {
	Element eleShippingEstimatedDeliveryByOptionInModal = new Element(interfaces.get("eleShippingEstimatedDeliveryByOptionInModal_mb"), shippingOption);
	return Common.getText(eleShippingEstimatedDeliveryByOptionInModal).split("Plus")[0].trim();
    }

    @Override
    public boolean isViewAllShippingOptionsDisplayed(String recipient) {
	Link lnkViewAllShippingOptionsByRecipientName_mb = new Link(interfaces.get("lnkViewAllShippingOptionsByRecipientName_mb"), recipient);
	return lnkViewAllShippingOptionsByRecipientName_mb.isDisplayed();
    }

    @Override
    public void clickViewAllShippingOptionsByRecipientNameLink(String recipient) {
	Link lnkViewAllShippingOptionsByRecipientName_mb = new Link(interfaces.get("lnkViewAllShippingOptionsByRecipientName_mb"), recipient);
	if (!lnkViewAllShippingOptionsByRecipientName_mb.isDisplayed())
	    lnkViewAllShippingOptionsByRecipientName_mb = new Link(interfaces.get("eleShippingSectionByRecipientName_mb"), recipient);
	Common.click(lnkViewAllShippingOptionsByRecipientName_mb);
	Common.waitForDOMChange();
    }

    @Override
    public String getErrorMessageByField(AddressFields addrField) {
	Element eleErrorMessageByField = new Element(interfaces.get("eleErrorMessageByField_mb"), addrField.getValue2SC());
	Common.waitForDisplayed(eleErrorMessageByField);
	return Common.getText(eleErrorMessageByField);
    }

    @Override
    public String getErrorMessageAtBottom() {
	DriverUtils.delay(1);
	return Common.getTextAndCloseAlert();
    }

    @Override
    public void clickCancelButtonInModal() {
	for (WebElement webelement : btnCancelInModal_mb.getElements()) {
	    if (webelement.isDisplayed()) {
		Common.click(webelement, false);
		return;
	    }
	}
    }

    @Override
    public String getWineInCartMesssage(String recipient) {
	Label lbMessageWineInCart = new Label(interfaces.get("lbMessageWineInCart_mb"), recipient);
	Common.waitForDisplayed(lbMessageWineInCart);
	Common.scrollElementToCenterScreen(lbMessageWineInCart);
	return lbMessageWineInCart.getText();
    }

    @Override
    public String[] convertShippingAddressForMobile(String[] shippingAddress) {
	List<String> lstResult = new ArrayList<String>();
	lstResult.add(shippingAddress[0]);
	lstResult.add(shippingAddress[1]);
	lstResult.add(shippingAddress[2].concat(", ").concat(shippingAddress[3]).concat(", ").concat(shippingAddress[4]).concat(" ").concat(shippingAddress[5]));
	return lstResult.toArray(new String[lstResult.size()]);
    }

    @Override
    public String getNoteOfSKU(SKU sku) {
	Element eleSKUNoteInRecipientSection_mb = new Element(interfaces.get("eleSKUNoteInRecipientSection_mb"), sku.getRecipient().getValue(), sku.getName());
	if (!eleSKUNoteInRecipientSection_mb.isDisplayed(Constants.SHORT_TIME / 6))
	    return "";
	try {
	    return Common.getText(eleSKUNoteInRecipientSection_mb);
	} catch (Exception e) {
	    return "";
	}
    }

    @Override
    public String getShippingMethodCostInShippingDeliveryModal(String shippingMethod) {
	Element eleShippingCostByOptionInModal_mb = new Element(interfaces.get("eleShippingCostByOptionInModal_mb"), shippingMethod);
	Common.waitForDisplayed(eleShippingCostByOptionInModal_mb);
	return eleShippingCostByOptionInModal_mb.getText().trim();
    }

    @Override
    public String getExtraShippingFee(String recipient) {
	Element eleExtraShippingFee_mb = new Element(interfaces.get("eleExtraShippingFee_mb"), recipient);
	Common.waitForDisplayed(eleExtraShippingFee_mb);
	Common.scrollElementToCenterScreen(eleExtraShippingFee_mb);
	return "+ $"+ Common.getText(eleExtraShippingFee_mb).split("\\$")[1].split(" ")[0];
    }

    @Override
    public String getRecipientSectionTitle(String recipient) {
   	Element eleRecipientSectionTitle = new Element(interfaces.get("eleRecipientSectionTitle_mb"), recipient);
   	return Common.getText(eleRecipientSectionTitle);
       }
}
