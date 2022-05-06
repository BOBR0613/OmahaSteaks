package com.omahasteaks.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonObject;
import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.data.objects.Promotion;
import com.omahasteaks.utils.common.Common;

public class ManagePromotions extends Database {

	private List<Promotion> listPromotionInTopLeftBanner;
	private List<Promotion> listPromotionInTopRightBanner;
	private List<Promotion> listPromotionInDeliveryOption;

	private static String jsonFile = "src/test/resources/data/omk/promotion.json";
	private static String formatDate = "MM/dd/yy";
	private static String defaultStartDate = "00/00/00";
	private static String defaultEndDate = "99/99/99";

	public ManagePromotions() {
		getListPromotions();
	}

	@SuppressWarnings("serial")
	private void getListPromotions() {
		JsonObject promotion = getData(new TypeToken<JsonObject>() {
		}.getType(), jsonFile);
		listPromotionInTopLeftBanner = getListFromJsonObj(promotion.get(PromotionLocation.TOP_LEFT_BANNER.getLocation()), new TypeToken<List<Promotion>>() {
		}.getType());
		listPromotionInTopRightBanner = getListFromJsonObj(promotion.get(PromotionLocation.TOP_RIGHT_BANNER.getLocation()), new TypeToken<List<Promotion>>() {
		}.getType());
		listPromotionInDeliveryOption = getListFromJsonObj(promotion.get(PromotionLocation.DELIVERY_OPTIONS.getLocation()), new TypeToken<List<Promotion>>() {
		}.getType());
	}

	public Promotion getPromotionByDefault(PromotionLocation location) {
		switch (location) {
		case TOP_LEFT_BANNER:
			return getPromotionByDefault(listPromotionInTopLeftBanner);

		case TOP_RIGHT_BANNER:
			return getPromotionByDefault(listPromotionInTopRightBanner);

		case DELIVERY_OPTIONS:
			return getPromotionByDefault(listPromotionInDeliveryOption);

		default:
			return null;
		}
	}

	public Promotion getPromotionsInTheDateRange(PromotionLocation location) {
		switch (location) {
		case TOP_LEFT_BANNER:
			return getPromotionInTheDateRange(listPromotionInTopLeftBanner);

		case TOP_RIGHT_BANNER:
			return getPromotionInTheDateRange(listPromotionInTopRightBanner);

		case DELIVERY_OPTIONS:
			return getPromotionInTheDateRange(listPromotionInDeliveryOption);

		default:
			return null;
		}
	}

	public Promotion getPromotionOfDeliveryOptionsInDateRange(String shippingMethod) {
		/* Get promotion data by shippingMethod in the valid date range */
		Promotion result = null;
		for (Promotion promotion : getListPromotionsInTheDateRange(listPromotionInDeliveryOption)) {
			if (promotion.getTextToLocate().equals(shippingMethod)) {
				result = promotion;
				break;
			}
		}
		return result;
	}

	public Promotion getPromotionOfDeliveryOptionByDefault(String shippingMethod) {
		/* Get promotion data by shippingMethod in the default range */
		Promotion result = null;
		for (Promotion promotion : getListPromotionsByDefault(listPromotionInDeliveryOption)) {
			if (promotion.getTextToLocate().equals(shippingMethod) && promotion.getStartingDate().equals(defaultStartDate) && promotion.getEndingDate().equals(defaultEndDate)) {
				result = promotion;
				break;
			}
		}
		return result;
	}

	public String[] getListDeliveryOption() {
		/* Get list delivery option in the default range and valid date range */
		ArrayList<String> lstResult = new ArrayList<String>();
		String[] lst = getListDeliveryOptionInDateRange();
		for (Promotion promotion : listPromotionInDeliveryOption) {
			if (!promotion.getExpectedText().equals("") && promotion.getStartingDate().equals(defaultStartDate) && promotion.getEndingDate().equals(defaultEndDate))
				lstResult.add(promotion.getTextToLocate());
		}
		if( lst != null) {
			for (String string : lst) {
				lstResult.add(string);
			}
		}
		Set<String> s = new HashSet<String>(lstResult);
		return (s.size() > 0) ? s.toArray(new String[s.size()]) : null;
	}

	private String[] getListDeliveryOptionInDateRange() {
		ArrayList<String> lstResult = new ArrayList<String>();
		List<Promotion> lst = getListPromotionsInTheDateRange(listPromotionInDeliveryOption);
		if (lst != null) {
			for (Promotion promotion : getListPromotionsInTheDateRange(listPromotionInDeliveryOption)) {
				lstResult.add(promotion.getTextToLocate());
			}
		}
		Set<String> s = new HashSet<String>(lstResult);
		return (s.size() > 0) ? s.toArray(new String[s.size()]) : null;
	}

	private List<Promotion> getListPromotionsByDefault(List<Promotion> lstPromotion) {
		ArrayList<Promotion> lstResult = new ArrayList<>();
		for (Promotion promotion : lstPromotion) {
			/*
			 * Get list promotion data in the default range when the startDate = 00/00/00,
			 * endDate = 99/99/99 and the expected text is not blank
			 */
			if (promotion.getStartingDate().equals(defaultStartDate) && promotion.getEndingDate().equals(defaultEndDate) && !promotion.getExpectedText().equals("")) {
				lstResult.add(promotion);
			}
		}
		return (lstResult.size() > 0) ? lstResult : null;
	}

	private Promotion getPromotionByDefault(List<Promotion> lstPromotion) {
		List<Promotion> lst = getListPromotionsByDefault(lstPromotion);
		return (lst != null) ? lst.get(0) : null;
	}

	private Promotion getPromotionInTheDateRange(List<Promotion> lstPromotion) {
		List<Promotion> lst = getListPromotionsInTheDateRange(lstPromotion);
		return (lst != null) ? lst.get(0) : null;
	}

	private List<Promotion> getListPromotionsInTheDateRange(List<Promotion> lstPromotion) {
		ArrayList<Promotion> lstResult = new ArrayList<Promotion>();
		SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
		Calendar orderedDate = Calendar.getInstance();
		for (Promotion promotion : lstPromotion) {
			if (!promotion.getStartingDate().equals(defaultStartDate) && !promotion.getEndingDate().equals(defaultEndDate)) {
				try {
					if (Common.isDateBetweenTwoDates(orderedDate, formatter.parse(promotion.getStartingDate()), formatter.parse(promotion.getEndingDate()))) {
						lstResult.add(promotion);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return (lstResult.size() > 0) ? lstResult : null;
	}
}
