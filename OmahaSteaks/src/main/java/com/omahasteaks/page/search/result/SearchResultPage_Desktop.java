package com.omahasteaks.page.search.result;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;

public class SearchResultPage_Desktop extends GeneralPage_Desktop implements SearchResultPage {
	protected Element lstSearchResult = new Element(interfaces.get("lstSearchResult"));
	protected Element eleFirstSearchResult = new Element(interfaces.get("eleFirstSearchResult"));

	public void selectFirstItem() {
		Common.click(eleFirstSearchResult);
		Common.waitForTitleChange();
	}

	public void clickTab(SkuType tab) {
		Element eleTab = new Element(interfaces.get("eleTab"), tab.getValue());
		waitForLoadingIconInvisible();
		Common.click(eleTab);
		waitForLoadingIconInvisible();
	}

	public boolean isTabDisplayed(String tab) {
		Element eleTab = new Element(interfaces.get("eleTab"), tab);
		return eleTab.isDisplayed();
	}

	public boolean isSortTypeDisplayed(String sortType) {
		Element eleSortType = new Element(interfaces.get("eleSortType"), sortType);
		return eleSortType.isDisplayed();
	}

	public String getCountNumberByTab(SkuType tab) {
		String tval="";
		if (tab.getValue().equalsIgnoreCase("Packages")) tval = "Combos";
		else tval = tab.getValue();
		Element eleCountNumberByTab = new Element(interfaces.get("eleCountNumberByTab"), tab.getValue(),tval);
		return Common.getText(eleCountNumberByTab).trim().replace("(", "").replace(")", "").replace(" ", "");
	}

	public boolean isCountNumberByTabGreaterThanZero(SkuType tab) {
		if (Integer.parseInt(getCountNumberByTab(tab)) > 0) {
			return Integer.parseInt(getCountNumberByTab(tab)) > 0;
		} else {
			return Integer.parseInt(getCountNumberByTab(tab)) <= 0;
		}
	}
}
