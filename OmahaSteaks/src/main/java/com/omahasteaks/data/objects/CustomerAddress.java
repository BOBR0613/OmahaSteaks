package com.omahasteaks.data.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class CustomerAddress {
	public String firstName = "";
	public String lastName = "";
	public String companyName = "";
	public String streetAddress1;
	public String streetAddress2 = "";
	public String aptSuite = "TEST ORDER - PLEASE DELETE";
	public String zipCode = "";
	public String city = "";
	public String state = "";
	public String stateStandfor = "";
	public String phoneNumber = "";
	public String country = "";
	public String email = "";
	public String confirmEmail = "";
	public Date birthday;
	public String countryCode = "";
	public String countryCodeInModal = "";
	
	
	public void getCustomerAddress(String cuno) {
	  ResultSet rs = Constants.DB.getAddressByCuno(cuno);	
	  try {
		rs.next();
		if (rs.getString("natl").equalsIgnoreCase("00")) {
			this.firstName = "";
			this.lastName = "";
			this.companyName = rs.getString("na").trim();
		}
		else {
			this.firstName = rs.getString("naf").trim();
			this.lastName = rs.getString("nal").trim();
			this.companyName = "";
		}
		this.streetAddress1 = rs.getString("naadst").trim();
		this.aptSuite = rs.getString("naad2").trim();
		this.zipCode = rs.getString("nazp5").trim();
		this.city = rs.getString("nacity");
		this.state = rs.getString("nast");
		this.phoneNumber = rs.getString("natach").trim() + "-" + rs.getString("natexh").trim() + "-" + 
				           rs.getString("natnbh").trim();
		this.country = Constants.DB.getCountry(this.state);
		if (this.country.equalsIgnoreCase("US")) this.country = "United States";
		else this.country = "Canada";
		this.email = Constants.DB.getCustEmail(cuno);
		this.confirmEmail = this.email;
		setStateStandfor();
		setCountryCode();
		setCountryCodeInModal();
	  } catch (SQLException e) { 
		  initRandomInformation();  
	  	}
	}
		

	public void setRandomBirthday(boolean over21) {
		if (over21) {
			birthday = Common.randomNewBirthday(true).getTime();
		} else {
			birthday = Common.randomNewBirthday(false).getTime();
		}
	}

	public void setRandomBirthday() {
		birthday = Common.randomNewBirthday().getTime();
	}

	public void initInformation(String firstName, String lastName, String companyName, String streetAdress1,
			String streetAdress2, String aptSuite, String zipCode, String city, String state, String phoneNumber,
			String country, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.streetAddress1 = streetAdress1;
		this.streetAddress2 = streetAdress2;
		this.aptSuite = aptSuite;
		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.email = email;
		confirmEmail = email;
		setStateStandfor();
		setCountryCode();
		setCountryCodeInModal();
	}

	public void initInformation(String firstName, String lastName, String streetAdress1, String zipCode, String city,
			String state, String phoneNumber, String country, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAddress1 = streetAdress1;
		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.email = email;
		confirmEmail = email;
		setStateStandfor();
		setCountryCode();
		setCountryCodeInModal();
	}


	public void initRandomInformation() {
		ListAddresses lstAddresses = new ListAddresses();
		CustomerAddress ca = lstAddresses.getDefaultShippingAddress();
		this.firstName = Common.getRandomString("First");
		this.lastName = Common.getRandomString("Last");
		this.streetAddress1 = ca.streetAddress1;
		this.aptSuite = "TEST ORDER - PLEASE DELETE";
		this.companyName = ca.companyName;
		this.zipCode = ca.zipCode;
		this.city = ca.city;
		this.state = ca.state;
		this.phoneNumber = ca.phoneNumber;
		this.country = ca.country;
		this.email = ca.email;
		this.confirmEmail = ca.email;
		this.stateStandfor = ca.stateStandfor;
		this.countryCode = ca.countryCode;
		this.countryCodeInModal = ca.countryCodeInModal;

	}

	public void removeShippingAddressOptionalFields() {
		this.aptSuite = "";
		this.companyName = "";
		this.phoneNumber = "";
		this.email = "";
		this.confirmEmail = "";
	}

	public void removeBillingAddressMandatoryFields() {
		this.firstName = "";
		this.lastName = "";
		this.phoneNumber = "";
		this.streetAddress1 = "";
		this.zipCode = "";
		this.city = "";
		this.state = "Select";
	}
	
	public void removeResidentialAddressNotRequiredFields() {
		this.companyName = "";
		this.streetAddress2 = "";
		this.aptSuite = "";
		this.confirmEmail = "";
	}
	
	public void removeResidentialAddressNotRequiredFieldsForRewardsCollectionNumber() {
		removeResidentialAddressNotRequiredFields();
		this.email = "";
		this.phoneNumber = "";
	}
	
	public void removeCompanyAddressNotRequiredFields() {
		this.streetAddress2 = "";
		this.aptSuite = "";
		this.confirmEmail = "";
	}

	public void setStateStandfor() {
		switch (this.state) {
		case "Alabama":
			stateStandfor = "AL";
			break;
		case "AE":
			stateStandfor = "AE";
			break;
		case "Alaska":
			stateStandfor = "AK";
			break;
		case "APO/FPO":
			stateStandfor = "AP";
			break;
		case "Nebraska":
			stateStandfor = "NE";
			break;
		case "California":
			stateStandfor = "CA";
			break;
		case "Texas":
			stateStandfor = "TX";
			break;
		case "New York":
			stateStandfor = "NY";
			break;
		case "Ontario":
			stateStandfor = "ON";
			break;
		case "Delaware":
			stateStandfor = "DE";
			break;
		case "Hawaii":
			stateStandfor = "HI";
			break;
		case "Kentucky":
			stateStandfor = "KY";
			break;
		case "Maine":
			stateStandfor = "ME";
			break;
		case "Mississippi":
			stateStandfor = "MS";
			break;
		case "Montana":
			stateStandfor = "MT";
			break;
		case "Oklahoma":
			stateStandfor = "OK";
			break;
		case "Puerto Rico":
			stateStandfor = "PR";
			break;
		case "South Dakota":
			stateStandfor = "SD";
			break;
		case "Utah":
			stateStandfor = "UT";
			break;
		case "Georgia":
			stateStandfor = "GA";
			break;
		case "Virgin Islands":
			stateStandfor = "VI";
			break;
		case "Illinois":
			stateStandfor = "IL";
			break;
		default:
			stateStandfor = "";
		}
	}

	public void setCountryCode() {
		switch (this.country) {

		case "Canada":
			countryCode = "CA";
			break;
		case "United States":
			countryCode = "US";
			break;
		default:
			countryCode = "";
		}
	}

	public void setCountryCodeInModal() {
		switch (this.country) {

		case "Canada":
			countryCodeInModal = "INT";
			break;
		case "United States":
			countryCodeInModal = "US";
			break;
		default:
			countryCodeInModal = "";
		}
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
		this.setStateStandfor();
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setStreetAdress1(String streetAdress1) {
		this.streetAddress1 = streetAdress1;
	}

	public void setStreetAdress2(String streetAdress2) {
		this.streetAddress2 = streetAdress2;
	}

	public void setAptSuite(String aptSuite) {
		this.aptSuite = aptSuite;
	}

	public void setStateStandfor(String stateStandfor) {
		this.stateStandfor = stateStandfor;
	}

	public String[] toArray() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		if (companyName != null && companyName.length() > 0)
			result.add(companyName);
		result.add(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add("Apt/Suite #" + aptSuite);
		if (streetAddress2 != null && streetAddress2.length() > 0)
			result.add(streetAddress2);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		result.add(country);
		result.add(email);
		result.add(phoneNumber);
		return result.toArray(new String[result.size()]);
	}

	private String[] toShippingArrayMac() {
		List<String> result = new ArrayList<String>();
		StringBuilder strTemp = new StringBuilder();
		strTemp.append(firstName + " " + lastName + " ");
		if (companyName != null && companyName.length() > 0)
			strTemp.append(companyName);
		strTemp.append(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			strTemp.append(aptSuite);
		strTemp.append(city + ", " + stateStandfor + " " + zipCode);
		switch (country) {
		case "United States":
			strTemp.append("USA");
			break;
		case "Canada":
			strTemp.append("TBD");
			break;
		default:
			break;
		}
		result.add(strTemp.toString());
		return result.toArray(new String[result.size()]);
	}

	private String[] toShippingArrayDesktop() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		if (companyName != null && companyName.length() > 0)
			result.add(companyName);
		result.add(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add(aptSuite);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		switch (country) {
		case "United States":
			result.add("USA");
			break;
		case "Canada":
			result.add("TBD");
			break;
		default:
			break;
		}
		return result.toArray(new String[result.size()]);
	}

	private String[] toShippingArrayMobile() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		if (companyName != null && companyName.length() > 0)
			result.add(companyName);
		StringBuilder strTemp = new StringBuilder();
		strTemp.append(streetAddress1 + ", ");
		if (aptSuite != null && aptSuite.length() > 0)
			strTemp.append(aptSuite + ", ");
		strTemp.append(city + ", " + stateStandfor + " " + zipCode + " ");
		switch (country) {
		case "United States":
			strTemp.append("USA");
			break;
		case "Canada":
			strTemp.append("TBD");
			break;
		default:
			break;
		}
		result.add(strTemp.toString());
		return result.toArray(new String[result.size()]);
	}

	public String[] toShippingArray() {
		if (RunningMode.getCurrentPlatform().equals("mac"))
			return toShippingArrayMac();

		if (Common.MODE.getRunningMode().equals("desktop"))
			return toShippingArrayDesktop();

		return toShippingArrayMobile();
	}

	public String[] toShippingArrayForConfirmationPage() {
		List<String> result = new ArrayList<String>();
		result.add(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add(aptSuite);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		switch (country) {
		case "United States":
			result.add("United States");
			break;
		case "Canada":
			result.add("TBD");
			break;
		default:
			break;
		}
		if (email != null && email.length() > 0)
			result.add(email);
		if (!phoneNumber.equals(""))
			result.add(phoneNumber);
		return result.toArray(new String[result.size()]);
	}

	public String[] toShippingArrayForConfirmationPageWithoutPhone() {
		List<String> result = new ArrayList<String>();
		result.add(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add(aptSuite);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		switch (country) {
		case "United States":
			result.add("United States");
			break;
		case "Canada":
			result.add("TBD");
			break;
		default:
			break;
		}
		if (email != null && email.length() > 0)
			result.add(email);
		return result.toArray(new String[result.size()]);
	}

	public String[] toBillingArray() {
		//
		// if (Common.getCurrrentPlatform().equals("mac")) {
		// return toBillingArrayMac();
		// }
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		result.add(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add(aptSuite);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		result.add(country);
		return result.toArray(new String[result.size()]);
	}

	public String[] toBillingInformationArray() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		result.add(streetAddress1);
		if (streetAddress2 != null && streetAddress2.length() > 0)
			result.add(streetAddress2);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add("Apt/Suite #" + " " + aptSuite);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		result.add(country);
		return result.toArray(new String[result.size()]);
	}

	public String[] toBillingArrayMac() {
		List<String> result = new ArrayList<String>();
		StringBuilder strTemp = new StringBuilder();
		strTemp.append(firstName + " " + lastName);
		strTemp.append(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			strTemp.append(aptSuite);
		strTemp.append(city + ", " + stateStandfor + " " + zipCode);
		strTemp.append(country);
		result.add(strTemp.toString());
		return result.toArray(new String[result.size()]);
	}

	@Override
	public CustomerAddress clone() {
		CustomerAddress newObj = new CustomerAddress();
		newObj.firstName = firstName;
		newObj.lastName = lastName;
		newObj.companyName = companyName;
		newObj.streetAddress1 = streetAddress1;
		newObj.streetAddress2 = streetAddress2;
		newObj.aptSuite = aptSuite;
		newObj.zipCode = zipCode;
		newObj.city = city;
		newObj.state = state;
		newObj.phoneNumber = phoneNumber;
		newObj.country = country;
		newObj.email = email;
		newObj.confirmEmail = confirmEmail;
		newObj.setStateStandfor();
		newObj.setCountryCode();
		newObj.setCountryCodeInModal();
		return newObj;
	}

	//
	// test book address -New
	// Account-----------------------------------------------------------
	//
	public String[] toAddressArrayForMyAccountPage() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		result.add(streetAddress1);
		if (streetAddress2 != null && streetAddress2.length() > 0)
			result.add(streetAddress2 + " " + aptSuite);
 		result.add(city + " " + stateStandfor + " " + zipCode);
		result.add("Phone:" + " " + phoneNumber);
		return result.toArray(new String[result.size()]);
	}

	public void updateFieldValueBy(AddressFields field, String newValue) {
		switch (field) {
		case FIRST_NAME:
			firstName = newValue;
			break;
		case LAST_NAME:
			lastName = newValue;
			break;
		case COMPANY_NAME:
			companyName = newValue;
			break;
		case ADDRESS1:
			streetAddress1 = newValue;
			break;
		case STREET_ADDRESS:
			streetAddress1 = newValue;
			break;
		case CITY:
			city = newValue;
			break;
		case STATE:
			state = newValue;
			break;
		case ZIP_CODE:
			zipCode = newValue;
			break;
		case PHONE:
			phoneNumber = newValue;
			break;
		case EMAIL:
			email = newValue;
			break;
		case CONFIRM_EMAIL:
			confirmEmail = newValue;
			break;
		case BIRTHDAY:
			birthday = null;
			break;
		default:
			break;
		}
	}

	public String setStateByStateStandFor(String stateStandFor) {
		switch (stateStandFor) {
		case "AK":
			state = "Alaska";
			break;
		case "AL":
			state = "Alabama";
			break;
		case "DE":
			state = "Deleware";
			break;
		case "HI":
			state = "Hawaii";
			break;
		case "KY":
			state = "Kentucky";
			break;
		case "ME":
			state = "Maine";
			break;
		case "MS":
			state = "Mississippo";
			break;
		case "MT":
			state = "Montana";
			break;
		case "NE":
			state = "Nebraska";
			break;
		case "OK":
			state = "Oklahoma";
			break;
		case "PR":
			state = "Puerto Rico";
			break;
		case "SD":
			state = "South Dakota";
			break;
		case "UT":
			state = "Utah";
			break;
		case "VI":
			state = "Virgin Islands";
			break;
		default:
			state = "";
			break;
		}
		return state;
	}

	public void setRandomEmail() {
		email = Common.getRandomString("email") + "@gmail.com";
	}

	public String[] toRecipientAddressArrayForMyAccountPage() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		result.add(streetAddress1);
		if (streetAddress2 != null && streetAddress2.length() > 0)
			result.add(streetAddress2 + " " + aptSuite);
		result.add(city + " " + stateStandfor + " " + zipCode);
		result.add("Phone:" + " " + phoneNumber);
		return result.toArray(new String[result.size()]);
	}
	
	public String[] toShippingAddressForRewardsCollectionCenterPage() {
		List<String> result = new ArrayList<String>();
		result.add(firstName + " " + lastName);
		if (companyName != null && companyName.length() > 0)
			result.add(companyName);
		result.add(streetAddress1);
		if (aptSuite != null && aptSuite.length() > 0)
			result.add("Apt/Suite #" + aptSuite);
		if (streetAddress2 != null && streetAddress2.length() > 0)
			result.add(streetAddress2);
		result.add(city + ", " + stateStandfor + " " + zipCode);
		result.add(country);
		if (email != null && email.length() > 0)
			result.add(email);
		if (phoneNumber != null && phoneNumber.length() > 0)
			result.add(phoneNumber);
		return result.toArray(new String[result.size()]);
	}
}
