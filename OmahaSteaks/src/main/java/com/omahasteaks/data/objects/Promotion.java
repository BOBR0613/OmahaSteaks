package com.omahasteaks.data.objects;

public class Promotion {
	private String startingDate;
	private String endingDate;
	private String textToLocate;
	private String expectedText;
	private String titleText;
	private String url;

	public String getUrl() {
		return url;
	}

	public String getTitleText() {
		return titleText;
	}

	public String getTextToLocate() {
		return textToLocate;
	}

	public String getStartingDate() {
		return startingDate;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public String getExpectedText() {
		return expectedText;
	}

	@Override
	public String toString() {
		return "Promotion [startingDate=" + startingDate + ", endingDate=" + endingDate + ", textToLocate=" + textToLocate + ", expectedText=" + expectedText + ", titleText=" + titleText + ", url=" + url +"]";
	}

}
