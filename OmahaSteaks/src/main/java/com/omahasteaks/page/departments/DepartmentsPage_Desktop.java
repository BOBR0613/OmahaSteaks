package com.omahasteaks.page.departments;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.enums.BoxPlans;
import com.omahasteaks.data.enums.Sections;
import com.omahasteaks.data.objects.ImageLink;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.DepartmentsPage;
import com.omahasteaks.page.home.HomePage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;

public class DepartmentsPage_Desktop extends HomePage_Desktop implements DepartmentsPage {

    // ================================================================================
    // Support Methods
    // ================================================================================

    protected Element getLinkInHeader(String href) {
	return new Element(interfaces.get("eleLinkInHeader"), href);
    }

    protected Element getImgInHeaderBySrc(String src) {
	return new Element(interfaces.get("eleImgInHeaderBySrc"), src);
    }

    protected Element getImgInHeaderByDataOriginal(String dataOriginal) {
	return new Element(interfaces.get("eleImgInHeaderByDataOriginal"), dataOriginal);
    }

    protected Element getLinkFooter(String href) {
	return new Element(interfaces.get("eleLinkInFooter"), href);
    }

    protected Element getImgInFooterBySrc(String src) {
	return new Element(interfaces.get("eleImgInFooterBySrc"), src);
    }

    protected Element getImgInFooterByDataOriginal(String dataOriginal) {
	return new Element(interfaces.get("eleImgInFooterByDataOriginal"), dataOriginal);
    }

    protected ListImageLink getAllLinksAndImageByElementSection(Element eleSection, boolean isScroll) {

	ListImageLink lstImageLink = new ListImageLink();
	Link lnkDepartmentMenu = new Link(eleSection, interfaces.get("lnkDepartmentMenu"));
	String href = "";
	String text = "";

	Image eleAllImages = new Image(eleSection, interfaces.get("eleAllImages"));
	Link eleAllLinks = new Link(eleSection, interfaces.get("eleAllLinks"));

	if (eleAllImages.getElements().size() > 0) {
	    for (int i = 0; i < eleAllImages.getElements().size(); i++) {
		try {
		    if (isScroll)
			Common.scrollElementToCenterScreen(eleAllImages.getElements().get(i));

		    String src;
		    String eleImage = "";

		    src = eleAllImages.getElements().get(i).getAttribute("data-original");
		    if (src != null) {
			eleImage = "eleImageByDataOriginal";

			// Ignore all images in the header section
			if (getImgInHeaderByDataOriginal(src).getElements().size() > 0)
			    continue;
			// Ignore all images in the footer section
			if (getImgInFooterByDataOriginal(src).getElements().size() > 0)
			    continue;
		    }

		    else {
			src = eleAllImages.getElements().get(i).getAttribute("src").replace(Constants.OMAHA_URL, " ");

			// Ignore all images in the header section
			if (getImgInHeaderBySrc(src).getElements().size() > 0)
			    continue;

			// Ignore all images in the footer section
			if (getImgInFooterBySrc(src).getElements().size() > 0)
			    continue;
			eleImage = "eleImageForCheckLinkExist";
		    }

		    if (src.contains("bat.bing.com"))
			continue;

		    Image eleImageForCheckLinkExist = new Image(eleSection, interfaces.get(eleImage), src);

		    if (eleImageForCheckLinkExist.getElements().size() <= 0) {

			ImageLink imgLink = new ImageLink();
			imgLink.setHref("");
			imgLink.setText("");

			String imgSrc = eleAllImages.getElements().get(i).getAttribute("data-original");

			if (imgSrc != null)
			    imgSrc = (imgSrc.contains("http") ? "" : Constants.OMAHA_URL) + imgSrc;
			else
			    imgSrc = eleAllImages.getElements().get(i).getAttribute("src");

			imgLink.setSrc(imgSrc);
			if (!lstImageLink.contains(imgLink))
			    lstImageLink.add(imgLink);
		    }
		} catch (Exception e) {
		    //continue;
		}
	    }
	}

	for (int i = 0; i < eleAllLinks.getElements().size(); i++) {
	    if (lnkDepartmentMenu.getElements().size() > 0)
		text = lnkDepartmentMenu.getText() + "/";

	    href = eleAllLinks.getElements().get(i).getAttribute("href");

	    // Ignore all links in the header section
	    if (getLinkInHeader(href).getElements().size() > 0)
		continue;

	    // Ignore all links in the footer section
	    if (getLinkFooter(href).getElements().size() > 0)
		continue;
	    text = text + eleAllLinks.getElements().get(i).getText().replaceAll("\n", " ").trim();

	    if (href.contains("steakbytes.com"))
		continue;

	    Image eleImage = new Image(eleSection, interfaces.get("eleImage"), href);

	    if (eleAllLinks.getElements().size() > 0) {

		ImageLink imgLink = new ImageLink();

		imgLink.setHref(href);

		if (text.equals("")) {
		    imgLink.setText("");
		} else {
		    imgLink.setText(text);
		}

		if (eleImage.isDisplayed()) {

		    String srcOfImage;

		    srcOfImage = eleImage.getAttribute("data-original");

		    if (srcOfImage != null)
			srcOfImage = (srcOfImage.contains("http") ? "" : Constants.OMAHA_URL) + srcOfImage;

		    else
			srcOfImage = eleImage.getAttribute("src");

		    imgLink.setSrc(srcOfImage);
		} else {
		    imgLink.setSrc("");
		}
		if (!lstImageLink.contains(imgLink))
		    lstImageLink.add(imgLink);
	    }
	}
	return lstImageLink;
    }

    // ================================================================================
    // Methods
    // ================================================================================

    public ListImageLink getAllLinksAndImageBySection(String deparmentName) {
	Element eleSection = new Element(interfaces.get(Sections.ALL_SECTIONS.eleSection()));
	ListImageLink lstResult = getAllLinksAndImageByElementSection(eleSection, true);

	for (ImageLink imgLnk : lstResult.getListImageLink()) {
	    imgLnk.setText(deparmentName + "/" + imgLnk.getText());
	}
	return lstResult;
    }

    public void addFirstSKUToCartByBoxPlans(SKU sku, BoxPlans box) {
	Element ele_FirstSKUFormByBoxPlan = new Element(interfaces.get("ele_FirstSKUFormByBoxPlan"), box.getValue());
	Common.waitForDisplayed(ele_FirstSKUFormByBoxPlan);
	Common.scrollElementToCenterScreen(ele_FirstSKUFormByBoxPlan);
	Common.waitForDOMChange();
	Common.modalDialog.closeUnknownModalDialog();
	ComboBox cbbSelectShipTo = getCbbSelectShipTo(ele_FirstSKUFormByBoxPlan);
	Button btnAddToCart = getBtnAddToCart(ele_FirstSKUFormByBoxPlan);
	TextBox txtShipTo = getTxtShipTo(ele_FirstSKUFormByBoxPlan);
	if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_IPAD)) {
	    cbbSelectShipTo = new ComboBox(interfaces.get("cbbSelectShipTo_iPad"));
	    btnAddToCart = new Button(interfaces.get("btnAddToCart_iPad"));
	    txtShipTo = new TextBox(interfaces.get("txtShipTo_iPad"));
	}
	Common.waitForDisplayed(cbbSelectShipTo);
	if (!btnAddToCart.isDisplayed())
	    throw new AssertionError("The \"Add To Cart\" button does not display");
	Common.scrollElementToCenterScreen(cbbSelectShipTo);
	selectShipTo(cbbSelectShipTo, sku.getRecipient().getValue(), txtShipTo);
	Common.click(btnAddToCart);
	// Select Size & Count if it's required!
	Element eleSelectSizeAndCountWhenError_mb = new Element(interfaces.get("eleSelectSizeAndCountWhenError_mb"));
	if (eleSelectSizeAndCountWhenError_mb.isDisplayed(Constants.SLEEP_TIME * 2)) {
	    Common.click(eleSelectSizeAndCountWhenError_mb);
	}
	if (eleFirstSizeAndCount.isDisplayed(Constants.SLEEP_TIME * 2)) {
	    selectFirstSizeAndCount();
	}
    }

    public boolean isEstArrivalDateByShippingMethodDisplayed(String shippingMethod) {
	Common.waitForDOMChange();
	Element ele_ShippingMethod = new Element(interfaces.get("ele_ShippingMethod"), shippingMethod);
	Element ele_EstArrivalDateByShippingMethod = new Element(interfaces.get("ele_EstArrivalDateByShippingMethod"), shippingMethod);

	if (ele_ShippingMethod.isDisplayed()) {
	    return ele_EstArrivalDateByShippingMethod.isDisplayed();
	} else {
	    return true;
	}

    }

}
