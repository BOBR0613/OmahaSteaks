package com.omahasteaks.data.objects;

import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.utils.common.Constants;
import com.opencsv.bean.CsvBindByName;

public class BannerPromotion {
	@CsvBindByName(column = "Starting Time")
	private String startingDate;

	@CsvBindByName(column = "Ending Time")
	private String endingDate;

	@CsvBindByName(column = "Red Banner Left Bold Text")
	private String redBannerLeftBoldText;

	@CsvBindByName(column = "Red Banner Left Standard Text")
	private String redBannerLeftStandardText;

	@CsvBindByName(column = "Red Banner Left Link")
	private String redBannerLeftLink;

	@CsvBindByName(column = "Red Banner Right Bold Text")
	private String redBannerRightBoldText;

	@CsvBindByName(column = "Red Banner Right Standard Text")
	private String redBannerRightStandardText;

	@CsvBindByName(column = "Red Banner Right Link")
	private String redBannerRightLink;

	@CsvBindByName(column = "Mobile Global Text")
	private String mobileGlobalText;

	@CsvBindByName(column = "Mobile Expanded Text")
	private String mobileExpandedText;

	@CsvBindByName(column = "Mobile Top Link")
	private String mobileTopLink;

	@CsvBindByName(column = "Mobile Text Under Search Bar")
	private String mobileTextUnderSearchBar;

	@CsvBindByName(column = "Mobile Bottom Link")
	private String mobileBottomLink;

	public BannerPromotion() {
		super();
	}

	public String getStartingDate() {
		return startingDate;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public String getRedBannerLeftBoldText() {
		return redBannerLeftBoldText.trim();
	}

	public String getRedBannerLeftStandardText() {
		return redBannerLeftStandardText.trim();
	}

	public String getRedBannerLeftLink() {
		return redBannerLeftLink.trim();
	}

	public String getRedBannerRightBoldText() {
		return redBannerRightBoldText.trim();
	}

	public String getRedBannerRightStandardText() {
		return redBannerRightStandardText.trim();
	}

	public String getRedBannerRightLink() {
		return redBannerRightLink.trim();
	}

	public String getMobileGlobalText() {
		return mobileGlobalText.trim();
	}

	public String getMobileExpandedText() {
		return mobileExpandedText.trim();
	}

	public String getMobileTopLink() {
		return mobileTopLink.trim();
	}

	public String getMobileTextUnderSearchBar() {
		return mobileTextUnderSearchBar.trim();
	}

	public String getMobileBottomLink() {
		return mobileBottomLink.trim();
	}

	public String getBoldText(PromotionLocation lc) {
		String boldText = "";
		String runningMode = DriverUtils.getRunningMode();
		if (runningMode.equals(Constants.PLATFORM_MOBILE) && lc.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			boldText = getMobileGlobalText();
		} else if (runningMode.equals(Constants.PLATFORM_MOBILE) && lc.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			boldText = getMobileTextUnderSearchBar();
		} else if (runningMode.equals(Constants.PLATFORM_DESKTOP) && lc.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			boldText = getRedBannerLeftBoldText();
		} else if (runningMode.equals(Constants.PLATFORM_DESKTOP) && lc.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			boldText = getRedBannerRightBoldText();
		}
		return boldText;
	}

	public String getExpectedTopBannerText(PromotionLocation lc) {
		String expectedBannerText = "";
		String runningMode = DriverUtils.getRunningMode();
		if (runningMode.equals(Constants.PLATFORM_MOBILE) && lc.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			expectedBannerText = getMobileGlobalText() + " " + getMobileExpandedText();
		} else if (runningMode.equals(Constants.PLATFORM_MOBILE) && lc.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			expectedBannerText = getMobileTextUnderSearchBar();
		} else if (runningMode.equals(Constants.PLATFORM_DESKTOP) && lc.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			expectedBannerText = getRedBannerLeftBoldText() + " " + getRedBannerLeftStandardText();
		} else if (runningMode.equals(Constants.PLATFORM_DESKTOP) && lc.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			expectedBannerText = getRedBannerRightBoldText() + " " + getRedBannerRightStandardText();
		}
		return expectedBannerText;
	}

	public String getBannerLink(PromotionLocation lc) {
		String bannerLink = "";
		String runningMode = DriverUtils.getRunningMode();
		if (runningMode.equals(Constants.PLATFORM_MOBILE) && lc.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			bannerLink = getMobileTopLink();
		} else if (runningMode.equals(Constants.PLATFORM_DESKTOP) && lc.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			bannerLink = getRedBannerLeftLink();
		}else if (runningMode.equals(Constants.PLATFORM_MOBILE) && lc.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			bannerLink = getMobileBottomLink();
		}else if (runningMode.equals(Constants.PLATFORM_DESKTOP) && lc.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			bannerLink = getRedBannerRightLink();
		}
		return bannerLink;
	}

	@Override
	public String toString() {
		return "PromotionData [startingDate=" + startingDate + ", endingDate=" + endingDate + ", redBannerLeftBoldText=" + redBannerLeftBoldText + ", redBannerLeftStandardText=" + redBannerLeftStandardText + ", redBannerLeftLink=" + redBannerLeftLink + ", redBannerRightBoldText=" + redBannerRightBoldText + ", redBannerRightStandardText=" + redBannerRightStandardText + ", redBannerRightLink=" + redBannerRightLink + ", mobileGlobalText=" + mobileGlobalText + ", mobileExpandedText=" + mobileExpandedText + ", mobileTopLink=" + mobileTopLink + ", mobileTextUnderSearchBar=" + mobileTextUnderSearchBar + ", mobileBottomLink=" + mobileBottomLink + "]";
	}
}
