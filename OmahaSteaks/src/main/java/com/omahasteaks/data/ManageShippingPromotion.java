package com.omahasteaks.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import com.omahasteaks.data.objects.ShippingOptionPromotion;
import com.omahasteaks.utils.common.Common;

public class ManageShippingPromotion extends Database {

	private String csvFile = "src/test/resources/data/shippingOptionsPromotion.csv";
	private String formatDate = "MM/dd/yy HH:mm:ss";
	private String defaultStartDate = "00/00/00";
	private String defaultEndDate = "99/99/99";
	private String timeZone = "CST";

	public ShippingOptionPromotion getPromotionDataInDateRange(String shippingMethod) {
		ShippingOptionPromotion result = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formatDate, Locale.US);
		Calendar orderedDate = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));

		List<ShippingOptionPromotion> lstPromotion = getListPromotionData();

		for (ShippingOptionPromotion promotion : lstPromotion) {
			if (!promotion.getStartDate().contains(defaultStartDate) && !promotion.getEndDate().contains(defaultEndDate)) {
				Date startDate = convertStringToDate(promotion.getStartDate(), formatter);
				Date endDate = convertStringToDate(promotion.getEndDate(), formatter);
				if (Common.isDateBetweenTwoDates(orderedDate, startDate, endDate) && promotion.getShippingMethod().equals(shippingMethod)) {
					result = promotion;
					break;
				}
			}
		}
		return result;
	}

	public ShippingOptionPromotion getDefaultPromotionData(String shippingMethod) {
		ShippingOptionPromotion result = null;
		List<ShippingOptionPromotion> lstPromotion = getListPromotionData();
		if (lstPromotion.size() == 0)
			return null;
		for (ShippingOptionPromotion pr : lstPromotion) {
			if (pr.getStartDate().contains(defaultStartDate) && pr.getEndDate().contains(defaultEndDate) && pr.getShippingMethod().equals(shippingMethod)) {
				result = pr;
				break;
			}
		}
		return result;
	}

	private List<ShippingOptionPromotion> getListPromotionData() {
		return getListFromCSVFile(csvFile, ShippingOptionPromotion.class);
	}

	public String[] getListShippingOptions() {
		ArrayList<String> lstResult = new ArrayList<String>();
		for (ShippingOptionPromotion pr : getListPromotionData()) {
			if (!pr.getPromotionText().equals(""))
				lstResult.add(pr.getShippingMethod());
		}
		Set<String> s = new HashSet<String>(lstResult);
		return (s.size() > 0) ? s.toArray(new String[s.size()]) : null;
	}

	private Date convertStringToDate(String dateString, SimpleDateFormat formatter) {
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			SimpleDateFormat fm = new SimpleDateFormat("MM/dd/yy", Locale.US);
			try {
				date = fm.parse(dateString);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return date;
	}

}
