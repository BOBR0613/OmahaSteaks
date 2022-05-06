package com.omahasteaks.page.search.result;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.utils.common.Common;

public class SearchResultPage_Mobile extends SearchResultPage_Desktop implements SearchResultPage {

	@Override
	public void clickTab(SkuType tab) {
		Element eleTab = new Element(interfaces.get("eleTab_mb"), tab.getValue());
		waitForLoadingIconInvisible();
		Common.click(eleTab);
		waitForLoadingIconInvisible();
	}

	@Override
	public boolean isTabDisplayed(String tab) {
		Element eleTab = new Element(interfaces.get("eleTab_mb"), tab);
		return eleTab.isDisplayed();
	}
	
	@Override
	public String getCountNumberByTab(SkuType tab) {
		Element eleCountNumberByTab = new Element(interfaces.get("eleCountNumberByTab_mb"), tab.getValue());
		return Common.getText(eleCountNumberByTab).split(" ")[1].replace("(", "").replace(")", "").replace(" ", "");
	}
}
