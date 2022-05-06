package com.omahasteaks.page;

import com.omahasteaks.data.enums.LinksCustomerService;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.enums.SearchType;
import com.omahasteaks.data.objects.RewardSKU;

public interface RewardGeneralPage {

	/**
	 * In Search section, fill keyword into Search text box, select type to search
	 * 
	 * @param searchKey
	 *            keyword need to search
	 * @param type
	 *            Keyword: when keyword is the word </br>
	 *            Point Value: when keyword is the number
	 */
	void search(String searchKey, SearchType type);

	/**
	 * After search and click a SKU link, in the Product page, click "Select" button
	 * to add SKU to cart
	 */
	RewardSKU addSKUToCart(RewardSKU sku);

	/**
	 * Get current tab name after select a shopping option in Shopping Categories
	 * section
	 */
	String getCurrentTabName();

	void clickContinueLink();

	/**
	 * In Shopping Cart section, click Check out link or cart icon to go to Shopping
	 * Cart page
	 * 
	 * @param isClickCheckOutLink
	 */
	void goToShoppingCartPage(Boolean isClickCheckOutLink);

	/**
	 * Select first item after searching keyword
	 */
	void selectFirstItem();

	/**
	 * Select second item after searching keyword
	 */
	void selectSecondItem();

	/**
	 * Select third item after searching keyword
	 */
	void selectThirdItem();
 

	/**
	 * Select third item after searching keyword
	 */
	void selectFourthItem();
 

	/**
	 * Select third item after searching keyword
	 */
	void selectFifthItem();
 

	/**
	 * Select third item after searching keyword
	 */
	void selectSixthItem();
 

	/**
	 * Select third item after searching keyword
	 */
	void selectSeventhItem();
 

	/**
	 * Select third item after searching keyword
	 */
	void selectNinthItem();
 

	/**
	 * Update Sku information
	 */
	RewardSKU updateSKUInfo(RewardSKU sku);

	/**
	 * In Shopping Categories section, select the shopping option
	 * 
	 * @param nameCategory
	 *            name of category
	 */
	void selectCategory(String nameCategory);

	/**
	 * Add SKU in Blazing Bargains page to cart
	 */
	RewardSKU addSKUFromBlazingBargainsPage(RewardSKU sku);

	/**
	 * Get the title of category
	 */
	String getCategoryName();

	/**
	 * Get category name from Shopping Categories section
	 */
	String getCategoryNameFromCategoriesSection(String orderCategory);

	/**
	 * Edit the recipient name when selecting the SKU to cart
	 */
	String editRecipient();

	/**
	 * Get the selected value of the 'Send To' package box in the center pane which
	 * shows detailed information of SKU
	 */
	String getSelectedValueOfSendToComboBox();

	String getNameOfTheFirstSKUInCategoryPage();

	boolean isYourCartEmptyInShoppingCartSectionDisplayed();

	boolean isCheckOutLnkInShoppingCartSectionDisplayed();

	boolean isCartIconInShoppingCartSectionDisplayed();

	boolean isSKUDisplayedInShoppingCartPage(RewardSKU sku);

	Boolean isPointOfSKUsDisplayedLessThanSearchedValue(String searchedValue);

	Boolean isListOfSKUDisplayed();

	int getNumberOfProduct();

	String getSubTotal();

	void selectTabInTopMenu(String tab);

	void selectLinkInCustomerServicePage(LinksCustomerService nameLink);

	String getHeaderOfPage();

	void fillInformationToContactUsPage(String email, String text);

	void clickResetLink();

	String getTextInEmailField();
	
	String getTextInEmailMsgField();
	
	boolean isListItemDisplayedCorrectly(String[] lstResults);
	
	public String[] getListShoppingCategories();
	
	public boolean isItemOutOfStockMsgDisplayed();
	
	public void clickTermAgreementLinkInFooter(LinksFooter nameLink);
	
	public Boolean isHeaderOfPrivacyPolicyPageDisplayed();
	
	String getWarningMessageInEmailAddressField();
	
	String getWarningMessageInEmailMsgField();
	
	String getErrorMessageByField(String nameField);
	
	void clickSubmitLink();
	
	void clickOnEmailAddressFieldLinkInListErrorMessage();
	
	Boolean isEmailAddressFieldFocused();
	
	boolean isNoSearchResultsFoundDisplayed();

	void selectFirstItem4PT(); 

	void selectFirstItem6PT();

	String getNameOfTheFifthSKUInCategoryPage();

	void addFirstAvailableItem(RewardSKU mySKU);
}
