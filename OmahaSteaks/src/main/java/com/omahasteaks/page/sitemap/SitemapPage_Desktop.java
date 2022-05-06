package com.omahasteaks.page.sitemap;

import com.logigear.control.common.imp.Link;
import com.omahasteaks.page.SitemapPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;

public class SitemapPage_Desktop extends GeneralPage_Desktop implements SitemapPage {
	// ================================================================================
	// Locators
	// ================================================================================

	// ================================================================================
	// Methods
	// ================================================================================

	public void goToCategoryByName(String categoryName) {
		Link lnkCategoryByName = new Link(interfaces.get("lnkCategoryByName"), categoryName);
		Common.click(lnkCategoryByName);
	}

	/**
	 * @param fullPath
	 *            should following format: "Department/Sub-Category/Category" <br>
	 *            (e.g. "Wine/Shop By/Ultimate Packages")
	 */
	public void goToCategoryByFullPath(String fullPath) {
		String[] categoryItems = fullPath.split("/");
		Link lnkCategoryByFullPath = new Link(interfaces.get("lnkCategoryByFullPath"), categoryItems[0],
				categoryItems[1], categoryItems[2]);
		Common.click(lnkCategoryByFullPath);
	}
}
