package com.omahasteaks.data;

import java.util.List;
import java.util.Random;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.utils.common.Constants;

public class ListAddresses extends Database {
	private List<CustomerAddress> listBilling;
	private List<CustomerAddress> listShipping;
	private List<CustomerAddress> listShippingCanada;

	private static String jsonFile = "src/test/resources/data/address_data.json";

	public ListAddresses() {
	    getListAddresses();
	}

	@SuppressWarnings("serial")
	public void getListAddresses() {
		JsonObject allAddresses = getData(new TypeToken<JsonObject>() {
		}.getType(),jsonFile);
		listBilling = getListFromJsonObj(allAddresses.get("billingAddress"), new TypeToken<List<CustomerAddress>>() {
		}.getType());
		listShipping = getListFromJsonObj(allAddresses.get("shippingAddress"), new TypeToken<List<CustomerAddress>>() {
		}.getType());
		listShippingCanada = getListFromJsonObj(allAddresses.get("shippingAddressToCanada"),
				new TypeToken<List<CustomerAddress>>() {
				}.getType());
	}

	/*
	 * @SuppressWarnings("serial") public CustomerAddress
	 * getListAddressByStateCode(String stateCode) { JsonObject allAddresses =
	 * getData(new TypeToken<JsonObject>() { }.getType()); listShipping =
	 * getListFromJsonObj(allAddresses.get("stateCode" + stateCode), new
	 * TypeToken<List<CustomerAddress>>() { }.getType()); Random rd = new Random();
	 * CustomerAddress address = listShipping.get(rd.nextInt(listShipping.size()));
	 * return address; }
	 */

	public CustomerAddress getRandomBillingAddress() {
		Random rd = new Random();
		CustomerAddress address = listBilling.get(rd.nextInt(listBilling.size()));
		address.setCountryCode();
		address.setStateStandfor();
		address.setCountryCodeInModal();
		return address;
	}

	public CustomerAddress getRandomShippingAddress() {
		Random rd = new Random();
		CustomerAddress address = listShipping.get(rd.nextInt(listShipping.size()));
		address.setCountryCode();
		address.setStateStandfor();
		address.setCountryCodeInModal();
		return address;
	}

	public CustomerAddress getDefaultShippingAddress() {
		return getShippingAddressByState(Constants.DEFAULT_STATE_OF_SHIPPING_ADDRESS);
	}

	public CustomerAddress getRushShippingAddress() {
		return getShippingAddressByState(Constants.RUSH_SHIPTO_ADDRESS);
	}

	public CustomerAddress getDefaultBillingAddress() {
		return getBillingAddressByState(Constants.DEFAULT_STATE_OF_BILLING_ADDRESS);
	}

	public CustomerAddress getRandomShippingAddressByState(String state) {
		for (CustomerAddress address : listShipping) {
			if (address.state.equals(state)) {
				return address;
			}
		}
		return null;
	}

	public CustomerAddress getRandomShippingAddressCanada() {
		Random rd = new Random();
		CustomerAddress address = listShippingCanada.get(rd.nextInt(listShippingCanada.size()));
		address.setCountryCode();
		address.setStateStandfor();
		address.setCountryCodeInModal();
		return address;
	}

	/*
	 * public CustomerAddress getShippingAddressByStateCode(String stateCode) {
	 * CustomerAddress address = getListAddressByStateCode(stateCode);
	 * address.setCountryCode(); address.setStateStandfor();
	 * address.setCountryCodeInModal(); return address;
	 * 
	 * }
	 */
	public CustomerAddress getShippingAddressByState(String state) {
		for (CustomerAddress address : listShipping) {
			if (address.state.equals(state)) {
				address.setCountryCode();
				address.setCountryCodeInModal();
				address.setStateStandfor();
				return address;
			}
		}
		return null;
	}

	public CustomerAddress getBillingAddressByState(String state) {
		for (CustomerAddress address : listBilling) {
			if (address.state.equals(state)) {
				address.setCountryCode();
				address.setCountryCodeInModal();
				address.setStateStandfor();
				return address;
			}
		}
		return null;
	}
}
