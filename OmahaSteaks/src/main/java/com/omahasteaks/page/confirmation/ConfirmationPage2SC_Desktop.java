package com.omahasteaks.page.confirmation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.Messages;

public class ConfirmationPage2SC_Desktop extends GeneralPage_Desktop implements ConfirmationPage2SC {
	// ================================================================================
	// Locators
	// ================================================================================
	protected Element elThankYou = new Element(interfaces.get("elThankYou"));
	protected Element eleBillingAddressContent = new Element(interfaces.get("eleBillingAddressContent"));
	protected Element elePaymentMethodContent = new Element(interfaces.get("elePaymentMethodContent"));
	protected Element eleContactInfoContent = new Element(interfaces.get("eleContactInfoContent"));
	protected Element eleSpecialBonusBuySkuPrice = new Element(interfaces.get("eleSpecialBonusBuySkuPrice"));
	protected Element eleSpecialBonusBuySkuName = new Element(interfaces.get("eleSpecialBonusBuySkuName"));
	protected Element eleRecipientShipment = new Element(interfaces.get("eleRecipientShipment"));
	protected Element eleSpecialBonusBuyModal = new Element(interfaces.get("eleSpecialBonusBuyModal"));
	protected Element eleTotalPrice = new Element(interfaces.get("eleTotalPrice"));
	protected Element eleListSkuAdded = new Element(interfaces.get("eleListSkuAdded"));
	protected Element eleOrderNumber = new Element(interfaces.get("eleOrderNumber"));
	protected Element eleDeliveriedDateByShippingAddressSection = new Element(
			interfaces.get("eleDeliveriedDateByShippingAddressSection"));
	protected Button btnAddToOrder = new Button(interfaces.get("btnAddToOrder"));
	protected Element eleOrderTotalShippingFee = new Element(interfaces.get("eleOrderTotalShippingFee"));
	

	// ================================================================================
	// Methods
	// ================================================================================
	public boolean isThankYouMessageDisplayed() {
		Common.waitForPageLoad();
		if (elThankYou.isDisplayed(Constants.MID_TIME)) {
			//Common.modalDialog.closeModalDialog();
			return elThankYou.getText().contains(Messages.THANK_YOU_MESSAGE);
		}
		return false;
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

	public String[] getShippingAddressContent(CustomerAddress shippingAddress) {
		String last="";
		Element eleShippingAddressContent = new Element(interfaces.get("eleShippingAddressContent"),
				shippingAddress.firstName + " " + shippingAddress.lastName);
		Common.waitForDisplayed(eleShippingAddressContent);
		String[] lstArs = eleShippingAddressContent.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			last = ars;
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		if (!shippingAddress.phoneNumber.equals("") && !last.equalsIgnoreCase(shippingAddress.phoneNumber))
			lstResult.add(shippingAddress.phoneNumber);
	//	if (!shippingAddress.email.equals(""))
	//		lstResult.add(shippingAddress.email);
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public String[] getPaymentMethodContent() {
		Common.waitForDisplayed(elePaymentMethodContent);
		String[] lstArs = elePaymentMethodContent.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public String[] getContactInfoContent() {
		Common.waitForDisplayed(eleContactInfoContent);
		String[] lstArs = eleContactInfoContent.getText().trim().split("\n");
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	/**
	 * @see pages.ConfirmationPage2SC#isSKUDisplayed(data.objects.SKU)
	 * @author diep.duong
	 * @throws [Diep
	 *             08/20/2018]: Waiting for client's answer Q_37
	 */
	public boolean isSKUDisplayed(CustomerAddress shippingAddress, SKU sku) {
		Element eleSkuInShippingAddressSection = new Element(interfaces.get("eleSkuInShippingAddressSection"),
				shippingAddress.firstName + " " + shippingAddress.lastName, sku.getName());
		System.out.println(eleSkuInShippingAddressSection.getLocatorAsString());
		Common.waitForDisplayed(eleSkuInShippingAddressSection);
		return eleSkuInShippingAddressSection.isDisplayed(Constants.SLEEP_TIME);
	}

	public String getShippingSectionName(CustomerAddress shippingAddress) {
		Element eleShippingSectionName = new Element(interfaces.get("eleShippingSectionName"),
				shippingAddress.firstName + " " + shippingAddress.lastName);
		return Common.getText(eleShippingSectionName);
	}

	public String getShippingMethodSection(CustomerAddress shippingAddress) {
		Element eleShippingMethodSection = new Element(interfaces.get("eleShippingMethodSection"),
				shippingAddress.firstName + " " + shippingAddress.lastName);
		return Common.getText(eleShippingMethodSection);
	}

	public String getDeliveriedDateByShippingAddressSection(CustomerAddress shippingAddress) {
		Element eleDeliveriedDateByShippingAddressSection = new Element(
				interfaces.get("eleDeliveriedDateByShippingAddressSection"),
				shippingAddress.firstName + " " + shippingAddress.lastName);
		return Common.getText(eleDeliveriedDateByShippingAddressSection);
	}

	public String getOrderNumberText() {
		Common.waitForDisplayed(eleOrderNumber);
		return Common.getText(eleOrderNumber);
	}

	public boolean isOrderNumberCorrectFormat(String orderNumber) {
		String regex = "^([A-Z]{1})([0-9]{7})([A-Z]{4})$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(orderNumber);
		if (m.matches())
			return true;
		return false;
	}

	public Calendar getShippingDeliveryDate(CustomerAddress shippingAddress) {
		Calendar currentDate = Calendar.getInstance();

		String shippingMethodSection = getDeliveriedDateByShippingAddressSection(shippingAddress);
		String[] lst = shippingMethodSection.replace(",", "").split(" ", 3);

		int date = Integer.parseInt(lst[2]);
		int month = Common.getValueOfTheMONTH(lst[1]);
		int year = currentDate.get(Calendar.YEAR);
		if (currentDate.get(Calendar.MONTH) == Calendar.DECEMBER && month == Calendar.JANUARY) {
			year++;
		}
		Calendar tmpCal = (Calendar) currentDate.clone();
		tmpCal.set(year, month, date);
		return tmpCal;
	}

	public String getShippingFeeInOrderTotalSection() {
		return Common.getText(eleOrderTotalShippingFee);
	}
}
