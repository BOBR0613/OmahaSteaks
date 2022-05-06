package com.omahasteaks.page.rewards.collection.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.logigear.locator.Interface;
import com.omahasteaks.data.enums.LinksCustomerService;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SearchType;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.page.RewardGeneralPage;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.common.RunningMode;
import com.omahasteaks.utils.helper.Logger;

public class RewardGeneralPage_Desktop implements RewardGeneralPage {

	protected Interface interfaces = new Interface(this.getClass(), Constants.LOCATOR_PATH);
	// ================================================================================
	// Locators
	// ================================================================================
	protected TextBox txtSearch = new TextBox(interfaces.get("txtSearch"));
	protected Button btnSearch = new Button(interfaces.get("btnSearch"));
	protected Element eleSearchForm = new Element(interfaces.get("eleSearchForm"));
	protected Label lblResultsHdg = new Label(interfaces.get("lblResultsHdg")); 
	protected Link lnkCheckOut = new Link(interfaces.get("lnkCheckOut"));
	protected Link lnkFirstSKU = new Link(interfaces.get("lnkFirstSKU"));
	protected Link lnkSecondSKU = new Link(interfaces.get("lnkSecondSKU"));
	protected Link lnkThirdSKU = new Link(interfaces.get("lnkThirdSKU"));
	protected Link lnkFourthSKU = new Link(interfaces.get("lnkFourthSKU"));
	protected Link lnkFifthSKU = new Link(interfaces.get("lnkFifthSKU"));
	protected Link lnkSixthSKU = new Link(interfaces.get("lnkSixthSKU"));
	protected Link lnkSeventhSKU = new Link(interfaces.get("lnkSeventhSKU"));
	protected Link lnkNinthSKU = new Link(interfaces.get("lnkNinthSKU"));
	protected Link lnkFirstSKU4PT = new Link(interfaces.get("lnkFirstSKU4PT"));
	protected Link lnkSecondSKU4PT = new Link(interfaces.get("lnkSecondSKU4PT"));
	protected Link lnkThirdSKU4PT = new Link(interfaces.get("lnkThirdSKU4PT"));
	protected Link lnkFourthSKU4PT = new Link(interfaces.get("lnkFourthSKU4PT"));
	protected Link lnkFirstSKU6PT = new Link(interfaces.get("lnkFirstSKU6PT"));
	protected Link lnkSecondSKU6PT = new Link(interfaces.get("lnkFirstSKU6PT"));
	protected Link lnkThirdSKU6PT = new Link(interfaces.get("lnkThirdSKU6PT"));
	protected Link lnkFourthSKU6PT = new Link(interfaces.get("lnkFourthSKU6PT"));
	protected Element eleFirstSKUName = new Element(interfaces.get("eleFirstSKUName"));
	protected ComboBox cbbSelectShipTo = new ComboBox("cbbSelectShipTo");
	protected Button btnSelect = new Button(interfaces.get("btnSelect"));
	protected TextBox txtRecipientName = new TextBox(interfaces.get("txtRecipientName"));
	protected Button btnSelectSomeoneElse = new Button(interfaces.get("btnSelectSomeoneElse"));
	protected Element eleSentToForm = new Element(interfaces.get("eleSentToForm"));
	protected Element eleTabNameSrc = new Element(interfaces.get("eleTabNameSrc"));
	protected Link lnkContinue = new Link(interfaces.get("lnkContinue"));
	protected Element eleListCategories = new Element(interfaces.get("eleListCaterogies"));
	protected Element eleSKUOfBlazingBartgains = new Element(interfaces.get("eleSKUOfBlazingBartgains"));
	protected Element lbShoppingCategory = new Element(interfaces.get("lbShoppingCategory"));
	protected Element lbYourCartEmptyInShoppingCartSection = new Element(interfaces.get("lbYourCartEmptyInShoppingCartSection"));
	protected Element eleCartIcon = new Element(interfaces.get("eleCartIcon"));
	protected Element eleSubtotal = new Element(interfaces.get("eleSubtotal"));
	protected Element eleSearchResultPoints = new Element(interfaces.get("eleSearchResultPoints"));
	protected Element eleRowWhichContainsSKU = new Element(interfaces.get("eleRowWhichContainsSKU"));
	protected Label lblHeaderPage = new Label(interfaces.get("lblHeaderPage"));
	protected TextBox txtEmail = new TextBox(interfaces.get("txtEmail"));
	protected TextBox txtEmailMsg = new TextBox(interfaces.get("txtEmailMsg"));
	protected Link lnkReset = new Link(interfaces.get("lnkReset"));
	protected Link lnkSubmit = new Link(interfaces.get("lnkSubmit"));
	protected Element eleDescriptionOfItem = new Element(interfaces.get("eleDescriptionOfItem"));
	protected Element lnkSKU = new Element(interfaces.get("lnkSKU"));
	protected Element eleOutOfStockMsg = new Element(interfaces.get("eleOutOfStockMsg"));
	protected Element lblNoSearchResultsFound = new Element(interfaces.get("lblNoSearchResultsFound"));
	protected Label lblOutOfStock = new Label(interfaces.get("lblOutOfStock"));

	// ================================================================================
	// Support Methods
	// ================================================================================

	protected String editValueInComboBox(ComboBox ComboBoxName) {
		List<String> lstOption = ComboBoxName.getOptions();
		String valueUpdate;
		int loop = 0;
		do {
			valueUpdate = lstOption.get(Common.randBetween(0, lstOption.size() - 1)).toString();
			loop++;
		} while (valueUpdate.equals(ComboBoxName.getSelected()) && loop < 3);
		Common.select(ComboBoxName, valueUpdate);
		Common.waitForDOMChange();
		return valueUpdate;
	}

	private String[] getListPointsInSearchResultPage() {
		List<String> lstArs = new ArrayList<String>();
		for (int i = 0; i < eleSearchResultPoints.getElements().size(); i++) {
			lstArs.add(eleSearchResultPoints.getElements().get(i).getText());
		}
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	protected Element getElementsOfSKU(String fieldName) {
		return new Element(interfaces.get("eleSKU"), fieldName);
	}

	protected ComboBox getComboBoxSendTo() {
		return new ComboBox(getElementsOfSKU(Constants.REWARDS_SHOPPING_CART_SEND_TO_FIELD), interfaces.get("cbbSelectShipTo"));
	}

	protected Element getElementHeaderOfPrivacyPolicyPage(LinksFooter linkName) {
		return new Element(interfaces.get("eleHeaderOfPage"), linkName.getValue());
	}

	private void addNameForNewRecipient(RewardSKU sku) {
		Common.select(getComboBoxSendTo(), "Send Gift");
		Common.click(btnSelect);
		Common.waitForPageLoad();
		Common.enter(txtRecipientName, sku.getRecipient().getValue());
		Common.click(btnSelectSomeoneElse);
		Common.waitForPageLoad();
	}

	private boolean isItemNameContainsResultsText(String actualResults, String[] lstResults) {
		actualResults = actualResults.toLowerCase();
		for (int i = 0; i < lstResults.length; i++) {
			if (actualResults.contains(lstResults[i]))
				return true;
			else
				continue;
		}
		return false;
	}

	protected Element getLinkEmailAddressField() {
		return new Element(interfaces.get("lnkAddressFieldInListErrorMessage"));
	}
	// ================================================================================
	// Methods
	// ================================================================================

	public void search(String searchKey, SearchType type) {
		Common.enter(txtSearch, searchKey);
		ComboBox cbbtype = new ComboBox(eleSearchForm, interfaces.get("cbbType"));
		Common.select(cbbtype, type.getValue());
		Common.click(btnSearch);
	}
	
	public void addFirstAvailableItem(RewardSKU sku) {
	   selectFirstItem();
	   addSKUToCart(sku); 

	   if (isOutOfStock() | isOnSearchResults()) {
		 selectSecondItem();
		 addSKUToCart(sku);
	   }
	   if (isOutOfStock() | isOnSearchResults()) {
		 selectThirdItem();
		 addSKUToCart(sku);
	   }
	   if (isOutOfStock() | isOnSearchResults()) {
		 selectFourthItem();
		 addSKUToCart(sku);
	   }
	   if (isOutOfStock() | isOnSearchResults()) {
		 selectFifthItem();
		 addSKUToCart(sku);
	   }
	   if (isOutOfStock() | isOnSearchResults()) {
		 selectSixthItem();
		 addSKUToCart(sku);
	   }
	   if (isOutOfStock() | isOnSearchResults()) {
		 selectSeventhItem();
		 addSKUToCart(sku);
	   }
	   if (isOutOfStock() | isOnSearchResults()) {
		 selectNinthItem();
		 addSKUToCart(sku);
	   }
	}
	
	private boolean isOutOfStock() {
		if (lblOutOfStock.isDisplayed()) {
			DriverUtils.getWebDriver().navigate().back();
			DriverUtils.getWebDriver().navigate().back();
			return true;
		}
		return false;
	}

	private boolean isOnSearchResults() {
		if (lblResultsHdg.isDisplayed()) return true; 
		return false;		
	}
	
	
	public void selectFirstItem() {
		Common.click(lnkFirstSKU);
		Common.waitForPageLoad();
	}

	public void selectSecondItem() {
		Common.click(lnkSecondSKU);
		Common.waitForPageLoad();
	}

	public void selectThirdItem() {
		Common.click(lnkThirdSKU);
		Common.waitForPageLoad();
	}

	public void selectFourthItem() {
		Common.click(lnkFourthSKU);
		Common.waitForPageLoad();
	}

	public void selectFifthItem() {
		Common.click(lnkFifthSKU);
		Common.waitForPageLoad();
	}

	public void selectSixthItem() {
		Common.click(lnkSixthSKU);
		Common.waitForPageLoad();
	}

	public void selectSeventhItem() {
		Common.click(lnkSeventhSKU);
		Common.waitForPageLoad();
	}

	public void selectNinthItem() {
		Common.click(lnkNinthSKU);
		Common.waitForPageLoad();
	}

	public void selectFirstItem4PT() {
		Common.click(lnkFirstSKU4PT);
		Common.waitForPageLoad();
	}

	public void selectSecondItem4PT() {
		Common.click(lnkSecondSKU4PT);
		Common.waitForPageLoad();
	}

	public void selectThirdItem4PT() {
		Common.click(lnkThirdSKU4PT);
		Common.waitForPageLoad();
	}

	public void selectFourthItem4PT() {
		Common.click(lnkFourthSKU4PT);
		Common.waitForPageLoad(); 
	}

	public void selectFirstItem6PT() {
		Common.click(lnkFirstSKU6PT);
		Common.waitForPageLoad();
	}

	public RewardSKU addSKUToCart(RewardSKU sku) {
		updateSKUInfo(sku);
		if (sku.getRecipient().equals(Recipient.MYSELF)) {
			Common.select(getComboBoxSendTo(), "Myself");
			Common.click(btnSelect);
			Common.waitForPageLoad();
		} else {
			addNameForNewRecipient(sku);
		}
		return sku;
	}

	public String getCurrentTabName() {
		String src = eleTabNameSrc.getAttribute("src").split(".com", 2)[1];
		String currentTabName = "";
		switch (src) {
		case Constants.SRC_SHIPPING_INFORMATION:
			currentTabName = Constants.TITLE_SHIPPING_INFORMATION;
			break;
		case Constants.SRC_SHIPPING_OPTIONS:
			currentTabName = Constants.TITLE_INFORMATION_OPTIONS;
			break;
		case Constants.SRC_ORDER_REVIEW:
			currentTabName = Constants.TITLE_ORDER_REVIEW;
			break;
		default:
			currentTabName = "";
		}
		return currentTabName;
	}

	public void clickContinueLink() {
		if (lnkContinue.isDisplayed()) {
			Common.click(lnkContinue);
			Common.waitForPageLoad();
		}
	}

	public RewardSKU updateSKUInfo(RewardSKU sku) {
		sku.setName(Common.getText(eleFirstSKUName));
		sku.setId(Common.getText(getElementsOfSKU(Constants.REWARDS_SHOPPING_CART_ITEM_FIELD)).split("#")[1].trim());
		sku.setPoint(Integer.parseInt(Common.getText(getElementsOfSKU(Constants.REWARDS_SHOPPING_CART_PTS_FIELD)).split(" ")[1].trim()));
		return sku;
	}

	public void goToShoppingCartPage(Boolean isClickCheckOutLink) {
		if (isClickCheckOutLink == true) {
			Common.click(lnkCheckOut);
		} else {
			Common.click(eleCartIcon);
		}
		Common.waitForPageLoad();
	}

	public String[] getListShoppingCategories() {
		List<String> lstArs = Arrays.asList(eleListCategories.getText().trim().split("\n"));
		List<String> lstResult = new ArrayList<String>();
		for (String ars : lstArs) {
			ars = ars.trim();
			if (!ars.isEmpty())
				lstResult.add(ars);
		}
		return lstResult.toArray(new String[lstResult.size()]);
	}

	public void selectTabInTopMenu(String tab) {
		Element eleTopMenu = new Element(interfaces.get("eleTopMenu"), tab);
		Common.click(eleTopMenu);
		Common.waitForPageLoad();
	}

	public RewardSKU addSKUFromBlazingBargainsPage(RewardSKU sku) {
		selectTabInTopMenu("Blazing Bargains");
		selectFirstSKUInBlazingBartgains();
		addSKUToCart(sku);
		return sku;
	}

	private void selectFirstSKUInBlazingBartgains() {
		Common.click(eleSKUOfBlazingBartgains, ActionType.JS);
		Common.waitForPageLoad();
	}

	private String getRandomCategory() {
		String[] lstCategory = getListShoppingCategories();
		return lstCategory[(int) (1 + Math.random() * (lstCategory.length - 1))];
	}

	public void selectCategory(String nameCategory) {
		Element eleCategory = new Element(interfaces.get("eleCaterogy"), nameCategory);
		Common.click(eleCategory);
		Common.waitForDOMChange();
	}

	public String getCategoryNameFromCategoriesSection(String orderCategory) {
		String[] lstCategory = getListShoppingCategories();
		String nameCategory = "";
		switch (orderCategory) {
		case "firstCategory":
			nameCategory = lstCategory[0];
			break;
		case "lastCategory":
			nameCategory = lstCategory[lstCategory.length - 1];
			break;
		case "Steak Selections":
			nameCategory = lstCategory[0];
			break;
		case "Variety Assortments":
			nameCategory = lstCategory[1];
			break;
		case "Burgers & Specialty Items":
			nameCategory = lstCategory[2];
			break;
		case "Seafood Selections":
			nameCategory = lstCategory[3];
			break;
		case "Roasts":
			nameCategory = lstCategory[4];
			break;
		case "Poultry Selections":
			nameCategory = lstCategory[5];
			break;
		case "Appetizers and Deserts":
			nameCategory = lstCategory[6];
			break; 
		case "Kitchenwares":
			nameCategory = lstCategory[7];
			break; 
		default:
			nameCategory = getRandomCategory();
			break;
		}
		return nameCategory;
	}

	public String getCategoryName() {
		return Common.getText(lbShoppingCategory);
	}

	public String editRecipient() {
		String recipientName;
		int loop = 0;
		do {
			recipientName = editValueInComboBox(getComboBoxSendTo());
			loop++;
		} while (recipientName.equals("Send Gift") && loop < 2);
		return recipientName;
	}

	public String getSelectedValueOfSendToComboBox() {
		return getComboBoxSendTo().getSelected();
	}

	public String getNameOfTheFirstSKUInCategoryPage() {
		return Common.getText(lnkFirstSKU);
	}

	public String getNameOfTheFifthSKUInCategoryPage() {
		return Common.getText(lnkFifthSKU);
	}

	public boolean isYourCartEmptyInShoppingCartSectionDisplayed() {
		return lbYourCartEmptyInShoppingCartSection.isDisplayed();
	}

	public boolean isCheckOutLnkInShoppingCartSectionDisplayed() {
		return lnkCheckOut.isDisplayed();
	}

	public boolean isCartIconInShoppingCartSectionDisplayed() {
		return eleCartIcon.isDisplayed();
	}

	public boolean isSKUDisplayedInShoppingCartPage(RewardSKU rewardSKU) {
		Element eleSKUInShoppingCartSection = new Element(interfaces.get("eleSKUInShoppingCartSection"), rewardSKU.getQuantity(), rewardSKU.getId(), rewardSKU.getPoint());
		return eleSKUInShoppingCartSection.isDisplayed();
	}

	public String getSubTotal() {
		return Common.getText(eleSubtotal).split(" ")[1].toString();
	}

	public Boolean isPointOfSKUsDisplayedLessThanSearchedValue(String searchedValue) {
		Boolean check = true;
		for (int i = 0; i < getListPointsInSearchResultPage().length; i++) {
			if (Integer.parseInt(getListPointsInSearchResultPage()[i]) > Integer.parseInt(searchedValue)) {
				check = false;
			}
		}
		return check;
	}

	public Boolean isListOfSKUDisplayed() {
		return eleRowWhichContainsSKU.isDisplayed();
	}

	public int getNumberOfProduct() {
		return eleRowWhichContainsSKU.getElements().size();
	}

	public void selectLinkInCustomerServicePage(LinksCustomerService nameLink) {
		Element eleLink = new Element(interfaces.get("eleLinkInCustomerService"), nameLink.getValue());
		Common.click(eleLink);
		Common.waitForPageLoad();
	}

	public String getHeaderOfPage() {
		return Common.getText(lblHeaderPage).trim();
	}

	public void fillInformationToContactUsPage(String email, String text) {
		Common.enter(txtEmail, email);
		Common.enter(txtEmailMsg, text);
	}

	public void clickResetLink() {
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
			Common.click(lnkReset, ActionType.JS);
		else
			Common.click(lnkReset);
		Common.waitForDOMChange();
	}

	public void clickSubmitLink() {
		Common.click(lnkSubmit);
		Common.waitForDOMChange();
	}

	public String getTextInEmailField() {
		return txtEmail.getAttribute("value");
	}

	public String getTextInEmailMsgField() {
		return txtEmailMsg.getAttribute("value");
	}

	public boolean isListItemDisplayedCorrectly(String[] lstResults) {
		int count = 0;
		List<String> lstIncorrectItems = new ArrayList<String>();
		for (int i = 0; i < eleRowWhichContainsSKU.getElements().size(); i++) {
			String resultString = eleRowWhichContainsSKU.getElements().get(i).getText();
			if (!isItemNameContainsResultsText(resultString, lstResults)) {
				String itemName = lnkSKU.getElements().get(i).getText();
				Common.click(lnkSKU.getElements().get(i));
				Common.waitForPageLoad();
				resultString = Common.getText(eleDescriptionOfItem);
				if (!isItemNameContainsResultsText(resultString, lstResults)) {
					lstIncorrectItems.add(itemName);
					count++;
				}
				Common.goBack();
				Common.waitForPageLoad();
			}
		}
		if (lstIncorrectItems.size() > 0) {
			Logger.warning("Below are the list of item names which do not contain serach results.");
			for (int i = 0; i < lstIncorrectItems.size(); i++) {
				Logger.warning(lstIncorrectItems.get(i).toString());
			}
		}
		if (count > 0)
			return false;
		return true;
	}

	public boolean isItemOutOfStockMsgDisplayed() {
		return eleOutOfStockMsg.isDisplayed();
	}

	public void clickTermAgreementLinkInFooter(LinksFooter nameLink) {
		Element eleTermAgressmentLink = new Element(interfaces.get("eleTermsAgreementLinkInFooter"), nameLink.getValue());
		String mainWindowHandle = DriverUtils.getWindowHandle();
		Common.click(eleTermAgressmentLink);
		List<String> lstWindow = DriverUtils.getWindowHandles();
		String termAgreementWindow = "";
		for (int i = 0; i < lstWindow.size(); i++) {
			if (!lstWindow.get(i).equals(mainWindowHandle))
				termAgreementWindow = lstWindow.get(i);
		}

		DriverUtils.switchTo(termAgreementWindow);
		Common.waitForPageLoad();
	}

	public Boolean isHeaderOfPrivacyPolicyPageDisplayed() {
		return getElementHeaderOfPrivacyPolicyPage(LinksFooter.PRIVACY_POLICY).isDisplayed();
	}

	public String getWarningMessageInEmailAddressField() {
		String message = txtEmail.getAttribute("validationMessage");
		return message;
	}

	public String getWarningMessageInEmailMsgField() {
		String message = txtEmailMsg.getAttribute("validationMessage");
		return message;
	}

	public String getErrorMessageByField(String nameField) {
		Element eleErrorMessageInContactUspage = new Element(interfaces.get("eleErrorMessageInContactUspage"), nameField);
		if (eleErrorMessageInContactUspage.isDisplayed(Constants.SHORT_TIME)) {
			return Common.getText(eleErrorMessageInContactUspage);
		} else
			return "";
	}

	public void clickOnEmailAddressFieldLinkInListErrorMessage() {
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
			Common.click(getLinkEmailAddressField(), ActionType.JS);
		else
			Common.click(getLinkEmailAddressField());
		Common.waitForDOMChange();
	}

	public Boolean isEmailAddressFieldFocused() {
		return Common.getActiveElement().equals(txtEmail.getElement());
	}

	public boolean isNoSearchResultsFoundDisplayed() {
		return lblNoSearchResultsFound.isDisplayed();
	}


}
