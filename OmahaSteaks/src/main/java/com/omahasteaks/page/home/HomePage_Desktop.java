package com.omahasteaks.page.home;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriverException;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.data.enums.Sections;
import com.omahasteaks.data.objects.ImageLink;
import com.omahasteaks.data.objects.Package;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class HomePage_Desktop extends GeneralPage_Desktop implements HomePage {

	// ================================================================================
	// Locators
	// ================================================================================
	protected Element eleHeader = new Element(interfaces.get("eleHeader"));
	protected Element eleFooter = new Element(interfaces.get("eleFooter"));
	protected Element eleDepartmentMenu = new Element(interfaces.get("eleDepartmentMenu"));
	protected Element eleCenterStage = new Element(interfaces.get("eleCenterStage"));
	protected Element eleProgressiveOffers = new Element(interfaces.get("eleProgressiveOffers"));
	protected Element eleAllSections = new Element(interfaces.get("eleAllSections"));
	protected Element eleMenuByClass = new Element(interfaces.get("eleMenuByClass"));
	protected Element eleProgressiveOffersByClass = new Element(interfaces.get("eleProgressiveOffersByClass"));
	protected Element eleItemsOfProgressiveOffers = new Element(interfaces.get("eleItemsOfProgressiveOffers"));
	protected Element eleCenterStagesByClass = new Element(interfaces.get("eleCenterStagesByClass"));
	protected Element eleTermsOfUseModal = new Element(interfaces.get("eleTermsOfUseModal"));
	protected Element elePrivacyPolicyModal = new Element(interfaces.get("elePrivacyPolicyModal"));
	protected Element elePrivacyPolicyPage = new Element(interfaces.get("elePrivacyPolicyPage"));
	protected Element eleYouareInModal = new Element(interfaces.get("eleYouareInModal"));
	protected Element eleContactPreferencesUpdatedModal = new Element(interfaces.get("eleContactPreferencesUpdatedModal"));
	protected Element eleEmailError = new Element(interfaces.get("eleEmailError"));
	protected Element eleIAgreeError = new Element(interfaces.get("eleIAgreeError"));
	
	protected Element eleFirstSku = new Element(interfaces.get("eleFirstSku"));
	protected Element eleFirstIID  = new Element(eleFirstSku,interfaces.get("eleFirstIID"));
	protected Element eleFirstName = new Element(eleFirstSku,interfaces.get("eleFirstName"));
	protected Element eleFirstPrice = new Element(eleFirstSku,interfaces.get("eleFirstPrice"));
	protected ComboBox cbbSelectShipTo = new ComboBox(eleFirstSku,interfaces.get("cbbSelectShipTo"));
	protected TextBox txtShipTo = new TextBox(eleFirstSku,interfaces.get("txtShipTo"));
	protected Element btnFirstAddToCart = new Element(eleFirstSku,interfaces.get("btnFirstAddToCart"));
	
	protected Button btnSignUp = new Button(interfaces.get("btnSignUp"));
	protected Label   txtEmailLabel = new Label(interfaces.get("txtEmailLabel"));
	protected TextBox txtEmailInput = new TextBox(interfaces.get("txtEmailInput"));
	protected TextBox txtEmailAddressInContactPreferences = new TextBox(interfaces.get("txtEmailAddressInContactPreferences"));

	protected CheckBox cbIAgree = new CheckBox(interfaces.get("cbIAgree"));

	protected Button btnJoin = new Button(interfaces.get("btnJoin"));
	protected Button btnUpdatePreferences = new Button(interfaces.get("btnUpdatePreferences"));
	protected Element elePromotionPopup = new Element(interfaces.get("elePromotionPopup"));

	protected Element getLinkSocialMedia(String href) {
		return new Element(interfaces.get("linkSocialMedia"), href);
	}

	// ================================================================================
	// Support Methods
	// ================================================================================
	/**
	 * This method is used to get all links and images Return an List<ImageLink>
	 * Parameters : - eleSection : parent element of the area to getting link and
	 * image - isScroll : scroll to element when getting images
	 */
	protected ListImageLink getAllLinksAndImageByElementSection(Element eleSection, boolean isScroll) {
		ListImageLink lstImageLink = new ListImageLink();
		Link lnkDepartmentMenu = new Link(eleSection, interfaces.get("lnkDepartmentMenu"));
		String href = "";
		String text = "";

		Image eleAllImages = new Image(eleSection, interfaces.get("eleAllImages"));
		Link eleAllLinks = new Link(eleSection, interfaces.get("eleAllLinks"));

		// Get all images which are without links and text
		if (eleAllImages.getElements().size() > 0) {
			for (int i = 0; i < eleAllImages.getElements().size(); i++) {
				try {
					if (isScroll)
						Common.scrollElementToCenterScreen(eleAllImages.getElements().get(i));

					String src;
					String eleImage = "";
					src = eleAllImages.getElements().get(i).getAttribute("data-original");
					if (src != null)
						eleImage = "eleImageByDataOriginal";
					else {
						src = eleAllImages.getElements().get(i).getAttribute("src").replace(Constants.OMAHA_URL, " ");

						eleImage = "eleImageForCheckLinkExist";
					}
					// ignore the bat.bing images
					if (src.contains("bat.bing.com"))
						continue;
					
					// ignore the 3rd party api calls to baynote
					if (src.contains("www.baynote")) continue;
					
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
					continue;
				}
			}
		}

		// Get all links , images and text
		for (int i = 0; i < eleAllLinks.getElements().size(); i++) {

			// Add department name into "text" of 'ImageLink' object
			if (lnkDepartmentMenu.getElements().size() > 0)
				text = lnkDepartmentMenu.getText() + "/";

			href = eleAllLinks.getElements().get(i).getAttribute("href");
			text = text + eleAllLinks.getElements().get(i).getText().replaceAll("\n", " ").trim();

			// ignore all links and images in Socila Meadia Section
			if (getLinkSocialMedia(href).getElements().size() > 0)
				continue;

			// ignore the steakbytes.com domain urls
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

	private ListImageLink getAllLinkAndImageInDepartmentMenu() {

		ListImageLink listResult = new ListImageLink();

		for (int i = 1; i <= eleMenuByClass.getElements().size(); i++) {

			Element eleParent = new Element(interfaces.get("eleMenuByIndex"), i);

			ListImageLink listImageLink = new ListImageLink();

			eleParent.moveTo();
			Common.waitForDOMChange();

			listImageLink = getAllLinksAndImageByElementSection(eleParent, false);

			listResult.getListImageLink().addAll(listImageLink.getListImageLink());

		}
		return listResult;
	}

	protected ListImageLink getAllLinkAndImageInProgressiveOffers() {

		ListImageLink listResult = new ListImageLink();
		System.out.println("offers tab = " + eleProgressiveOffersByClass.getElements().size());
		for (int i = eleProgressiveOffersByClass.getElements().size(); i > 0; i--) {

			Element eleParent = new Element(interfaces.get("eleProgressiveOffersByIndex"), i);

			ListImageLink listImageLink = new ListImageLink();

			Common.click(eleParent);

//			Common.waitForDOMChange();
//			Common.scrollElementToCenterScreen(eleItemsOfProgressiveOffers);

			Common.waitForPageLoad();

			listImageLink = getAllLinksAndImageByElementSection(eleItemsOfProgressiveOffers, false);

			listResult.getListImageLink().addAll(listImageLink.getListImageLink());

		}
		return listResult;
	}

	protected CheckBox getCheckboxContactPreferencesOptionByValue(String value) {
		return new CheckBox(interfaces.get("eleOptionsContactPreferences"), value);
	}

	protected Element getElementPromotionBar(int index) {
		return new Element(interfaces.get("elePromotionBar"), index);
	}
	// ================================================================================
	// Methods
	// ================================================================================

	public ListImageLink getAllLinksAndImageBySection(Sections section) {

		Element eleSection = new Element(interfaces.get(section.eleSection()));

		switch (section) {
		case DEPARTMENT_MENU:
			return getAllLinkAndImageInDepartmentMenu();
		case PROGRESSIVE_OFFERS:
			return getAllLinkAndImageInProgressiveOffers();
		case ALL_SECTIONS:
			ListImageLink listImageLink = getAllLinksAndImageByElementSection(eleSection, true);
			listImageLink.addAll(getAllLinkAndImageInDepartmentMenu());
			listImageLink.addAll(getAllLinkAndImageInProgressiveOffers());
			return listImageLink;
		default:
			return getAllLinksAndImageByElementSection(eleSection, true);
		}
	}

	public boolean checkMappingBetweenImageAndLink(ImageLink imgLink, Sections section) {
		ListImageLink listImageLink = new ListImageLink();
		return listImageLink.getImageLink(section).contains(imgLink);
	}

	public List<String> getDepartmentsName() {
		Element eleDepartmentName = new Element(eleDepartmentMenu, interfaces.get("eleAllLinks"));
		List<String> lstDepartmentsName = new ArrayList<String>();
		for (int i = 0; i < eleDepartmentName.getElements().size(); i++) {
 		   lstDepartmentsName.add(eleDepartmentName.getElements().get(i).getText().trim());
		}
		return lstDepartmentsName;
	}

	public boolean isCenterStageTabClickable() {
		try {
			for (int i = eleCenterStagesByClass.getElements().size(); i > 0; i--) {
				Element eleTab = new Element(interfaces.get("eleCenterStagesByIndex"), i);
				Common.click(eleTab);
				Common.waitForDOMChange();
			}
		} catch (WebDriverException e) {
			e.getMessage().contains("Clickable");
			return false;
		}
		return true;
	}

	public void clickTermAgreementLinkInFooter(LinksFooter nameLink) {
		Element eleTermAgressmentLink = new Element(interfaces.get("eleTermsAgreementLinkInFooter"), nameLink.getValue());
		Common.click(eleTermAgressmentLink);
		Common.waitForDOMChange();
	}

	public boolean isTermsOfUsePopupInFooterDisplayed() {
		return eleTermsOfUseModal.isDisplayed();
	}

	public boolean isPrivacyPolicyPopupInFooterDisplayed() {
		return elePrivacyPolicyModal.isDisplayed();
	}
	
	public boolean isPrivacyPolicyPageDisplayed() {
		return elePrivacyPolicyPage.isDisplayed();
	}

	public void clickLinkInFooterBySection(String nameSection, LinksFooter nameLink) {
		Element eleLink = new Element(interfaces.get("eleLinkInFooterBySection"), nameLink.getValue());
		Common.click(eleLink);
		Common.waitForPageLoad();
	}

	public void fillEmailAddressInFooter(String email) {
		Common.click(btnSignUp);
		Common.waitForPageLoad();
	    Common.tryClickByJs(txtEmailLabel);
		Common.tryEnter(txtEmailInput, email);
	}

	public void clickIAgreeCheckbox() {
		Common.check(cbIAgree);
	}

	public void clickJoinButtonInFooter() {
		Common.click(btnJoin);
		Common.waitForDOMChange();
	}

	public boolean isYouAreInModalDisplayed() {
		return eleYouareInModal.isDisplayed();
	}

	public void goToContactPreferences() {
	  String url="";
	  	// check for /?prodejna=shop2
		if (Constants.getRunningURL().contains("?")) {
		  String str = Constants.getRunningURL().trim();
		  String[] arrOfStr = str.split("\\?",2);
		  url = arrOfStr[0].trim() + Constants.URL_CONTACT_PREFERENCES.substring(1) + "&" + arrOfStr[1]; 
		}
		else url = Constants.getRunningURL() + Constants.URL_CONTACT_PREFERENCES;
		DriverUtils.navigate(url);
		Common.waitForPageLoad();
	}

	public void fillEmailAddressInContactPreferencesPage(String emailAddress) {
		Common.waitForDisplayed(txtEmailAddressInContactPreferences);
		Common.enter(txtEmailAddressInContactPreferences, emailAddress);
	}

	public void unsubscribeEmailInContactPrefencesPage() {
		Common.check(getCheckboxContactPreferencesOptionByValue(Constants.CONTACT_PREFERENCES_OPTION));
		Common.click(btnUpdatePreferences);
		Common.waitForDOMChange();
	}

	public boolean isContactPreferencesUpdateModalDisplayed() {
		return eleContactPreferencesUpdatedModal.isDisplayed();
	}

	public String getEmailErrorMessage() {
		return Common.getText(eleEmailError);
	}

	public String getIAgreeErrorMessage() {
		return Common.getText(eleIAgreeError);
	}

	public String getPromotionText(PromotionLocation location) {
		String promotionText = "";
		if (location.equals(PromotionLocation.TOP_LEFT_BANNER))
			promotionText = Common.getText(getElementPromotionBar(1));
		else if (location.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			promotionText = Common.getText(getElementPromotionBar(2));
		}
		return promotionText;
	}

	public boolean isPromotionTextBold(String text, PromotionLocation lc) {
		Element ele = null;
		if (lc.equals(PromotionLocation.TOP_LEFT_BANNER))
			ele = new Element(getElementPromotionBar(1), interfaces.get("elePromotionBoldText"), text);
		else
			ele = new Element(getElementPromotionBar(2), interfaces.get("elePromotionBoldText"), text);
		String fontWeight = ele.getElement().getCssValue("font-weight");
		if (fontWeight.equals("bold") || fontWeight.equals("bolder") || Integer.parseInt(fontWeight) >= 700)
			return true;
		return false;
	}

	public void clickPromotionLink(PromotionLocation location) {
		if (location.equals(PromotionLocation.TOP_LEFT_BANNER))
			Common.click(getElementPromotionBar(1));
		else if (location.equals(PromotionLocation.TOP_RIGHT_BANNER)) {
			Common.click(getElementPromotionBar(2));
		}
		Common.waitForPageLoad();
	}

	public String getTitleOfPromotionPopup() {
		return Common.getText(elePromotionPopup).split("\n")[0];
	}
	
	
	public void addFirstSkuToCart(Package pkg, boolean doAddExclusiveOffer) {
		Common.waitForPageLoad();
		Common.waitForDisplayed(eleFirstSku);
		System.out.println("pkg.id = " + eleFirstIID.getText());
		System.out.println("name = " + eleFirstName.getText());
		System.out.println("price = " + eleFirstPrice.getText());
		pkg.setId(eleFirstIID.getText());
 		pkg.setName(eleFirstName.getText());
		pkg.setPrice(Common.getPriceFromText(eleFirstPrice.getText()));
  		selectShipTo(cbbSelectShipTo, pkg.getRecipient().getValue(), txtShipTo);
		Common.click(btnFirstAddToCart);
		selectExclusiveOffer(doAddExclusiveOffer);
	}

}
