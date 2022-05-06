package com.omahasteaks.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.data.objects.BannerPromotion;
import com.omahasteaks.utils.common.Common;

public class ManageBannerPromotion extends Database {
	private String csvFile = "src/test/resources/data/promotion.csv";
	private String formatDate = "MM/dd/yy HH:mm:ss";
	private String defaultStartDate = "00/00/00";
	private String defaultEndDate = "99/99/99";
	private String timeZone = "CST";

	 
	public List<BannerPromotion> getPromotionDataInDateRange() {
		List<BannerPromotion> result = new ArrayList<BannerPromotion>();
		SimpleDateFormat formatter = new SimpleDateFormat(formatDate, Locale.US);
		Calendar orderedDate = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));

		List<BannerPromotion> lstPromotion = getListPromotionData();

		for (BannerPromotion promotion : lstPromotion) {
			if (!promotion.getStartingDate().contains(defaultStartDate)
					&& !promotion.getEndingDate().contains(defaultEndDate)) {
				Date startDate = convertStringToDate(promotion.getStartingDate(), formatter);
				Date endDate = convertStringToDate(promotion.getEndingDate(), formatter);
				if (Common.isDateBetweenTwoDates(orderedDate, startDate, endDate)) {
					result.add(promotion);
				}
			}
		}
		return result;
	}

	public BannerPromotion getDefaultPromotionData(PromotionLocation lc) {
		BannerPromotion result = null;
		List<BannerPromotion> lstPromotion = getListPromotionData();
		if (lstPromotion.size() == 0)
			return null;
		for (BannerPromotion pr : lstPromotion) {
			if (pr.getStartingDate().contains(defaultStartDate) && pr.getEndingDate().contains(defaultEndDate)) {
				result = pr;
				break;
			}
		}
		return result;
	}

	private List<BannerPromotion> getListPromotionData() {
		return getListFromCSVFile(csvFile, BannerPromotion.class);
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
