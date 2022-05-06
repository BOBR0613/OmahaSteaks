package com.omahasteaks.page.home;

import java.util.ArrayList;
import java.util.List;

import com.logigear.control.common.imp.Element;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class HomePage_Mobile extends HomePage_Desktop {

	protected Element eleOpenMenu_mb = new Element(interfaces.get("eleOpenMenu_mb"));
	protected Element btnCloseMenu_mb = new Element(interfaces.get("btnCloseMenu_mb"));
	protected Element eleDepartmentMenu_mb = new Element(interfaces.get("eleDepartmentMenu_mb"));
	protected Element eleProgressiveOffersByClass_mb = new Element(interfaces.get("eleProgressiveOffersByClass_mb"));
	protected Element eleItemsOfProgressiveOffers_mb = new Element(interfaces.get("eleItemsOfProgressiveOffers_mb"));
	protected Element eleExpanedText_LeftBanner_mb = new Element(interfaces.get("eleExpanedText_LeftBanner_mb"));
	protected Element elePromotion_LeftBar_mb = new Element(interfaces.get("elePromotion_LeftBar_mb"));
	protected Element elePromotion_RightBar_mb = new Element(interfaces.get("elePromotion_RightBar_mb"));

	public HomePage_Mobile() {
		super();
	}

	// ================================================================================
	// Methods
	// ================================================================================
	@Override
	protected Element getElementPromotionBar(int index) {
		return new Element(interfaces.get("elePromotionBar_mb"), index);
	}

	@Override
	protected Element getLinkSocialMedia(String href) {
		return new Element(interfaces.get("linkSocialMedia_mb"), href);
	}

	private void closeMenu() {
		Common.click(btnCloseMenu_mb);
	}

	private void openMenu() {
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		Common.scrollToTop();
		if (!eleOpenMenu_mb.isDisplayed()) {
			goToHomePage();
		}
		while (!txtSearch.isDisplayed() && endTime < timeout) {
			Common.scrollToTop();
			Common.click(eleOpenMenu_mb);
			DriverUtils.delay(1);
			endTime = System.currentTimeMillis() - startTime;
		}
	}

	@Override
	public List<String> getDepartmentsName() {
		openMenu();
		Element eleDepartmentName = new Element(eleDepartmentMenu_mb, interfaces.get("eleAllLinks"));
		List<String> lstDepartmentsName = new ArrayList<String>();
		for (int i = 0; i < eleDepartmentName.getElements().size(); i++) {

			lstDepartmentsName.add(eleDepartmentName.getElements().get(i).getText().trim());
		}
		closeMenu();
		return lstDepartmentsName;
	}

	@Override
	protected ListImageLink getAllLinkAndImageInProgressiveOffers() {

		ListImageLink listResult = new ListImageLink();

		for (int i = eleProgressiveOffersByClass_mb.getElements().size(); i > 0; i--) {

			Element eleParent = new Element(interfaces.get("eleProgressiveOffersByIndex_mb"), i);

			ListImageLink listImageLink = new ListImageLink();

			eleParent.click();

			Common.waitForDOMChange();
			Common.scrollElementToCenterScreen(eleItemsOfProgressiveOffers_mb);

			Common.waitForPageLoad();

			listImageLink = getAllLinksAndImageByElementSection(eleItemsOfProgressiveOffers_mb, false);

			listResult.getListImageLink().addAll(listImageLink.getListImageLink());

		}
		return listResult;
	}

	@Override
	public boolean isCenterStageTabClickable() {
		return true;
	}

	@Override
	public String getPromotionText(PromotionLocation location) {
		String promotionText = "";
		if (location.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			Common.click(elePromotion_LeftBar_mb);
			promotionText = Common.getText(eleExpanedText_LeftBanner_mb);
		} else if (location.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			promotionText = Common.getText(elePromotion_RightBar_mb);
		}
		return promotionText;
	}

	@Override
	public boolean isPromotionTextBold(String text, PromotionLocation location) {
		String fontWeight = "";
		Element eleBoldText = null;
		if (location.equals(PromotionLocation.TOP_LEFT_BANNER))
			eleBoldText = new Element(eleExpanedText_LeftBanner_mb, interfaces.get("elePromotionBoldText_LeftBanner_mb"));
		else
			eleBoldText = new Element(elePromotion_RightBar_mb, interfaces.get("elePromotionBoldText_RightBanner_mb"));

		fontWeight = eleBoldText.getElement().getCssValue("font-weight");

		if (fontWeight.equals("bold") || fontWeight.equals("bolder") || Integer.parseInt(fontWeight) >= 700)
			return true;
		return false;
	}

	@Override
	public void clickPromotionLink(PromotionLocation location) {
		if (location.equals(PromotionLocation.TOP_LEFT_BANNER)) {
			if (!eleExpanedText_LeftBanner_mb.isDisplayed())
				Common.click(elePromotion_LeftBar_mb);
			Common.click(eleExpanedText_LeftBanner_mb);
		} else if (location.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			Common.click(elePromotion_RightBar_mb, false);
		}
		Common.waitForPageLoad();
	}
}
