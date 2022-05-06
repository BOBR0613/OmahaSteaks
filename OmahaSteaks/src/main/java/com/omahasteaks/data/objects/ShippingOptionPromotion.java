package com.omahasteaks.data.objects;

import com.opencsv.bean.CsvBindByName;

public class ShippingOptionPromotion {

	@CsvBindByName(column = "Starting Time")
	private String startDate;

	@CsvBindByName(column = "Ending Time")
	private String endDate;

	@CsvBindByName(column = "Shipping Method")
	private String shippingMethod;

	@CsvBindByName(column = "Promotion Text")
	private String promotionText;

	
	public ShippingOptionPromotion() {
		super();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate.trim();
	}

	public String getShippingMethod() {
		return shippingMethod.trim();
	}

	public String getPromotionText() {
		return promotionText.trim();
	}

	@Override
	public String toString() {
		return "ShippingOptionPromotion [startDate=" + startDate + ", endDate=" + endDate + ", shippingMethod=" + shippingMethod + ", promotionText=" + promotionText + "]";
	}

}
