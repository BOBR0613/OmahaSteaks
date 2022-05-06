package com.omahasteaks.page;

import com.logigear.control.common.imp.Element;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;

public interface GeneralPage {

    /**
     * click My Cart link to go MyCart page
     */
    void goToMyCart();

    void viewMyCart();
    
    Element getCategoryElement(String categoryPath);

    void goToCategoryPage(String categoryPath);

    void addFirstSKUToCart(SKU sku);

    void addItemToCart(Item item);

    void addItemToCart(Item item, boolean doAddExclusiveOffer);

	String getGCID(String gcType);
	
    /**
     * click continue Shopping button in Added to cart modal
     */
    void continueShopping();

    /**
     * click checkout button in Added to cart modal
     */
    void checkOut();

    /**
     * click Sign In link to go Sign in page
     */
    void goToSignInPage();

    /**
     * enter the keyword in Search text box
     */
    void search(String searchKey);

    /**
     * get count of SKU displays in the My Cart icon
     */
    int getCartNumber();

    /**
     * get list SKU added to cart
     */
    void getAddedToCartSKUList(ListSKUs addedToCartSKUsList);

    /**
     * add SKU of Special Deal Unclocked section
     */
    void addSpecialDealUnlocked(boolean addSpecialDealUnlocked);

    /**
     * select add SKU in Exclusive Offer modal or not
     */
    void selectExclusiveOffer(boolean doAddExclusiveOffer);

    /**
     * click 'x' button to close Added to cart modal
     */
    void closeAddedToCartModal();

    void selectFreeShipPackages();

    /**
     * get warning message displays Recipient's text box
     */
    String getRecipientWarningMessage();

    /**
     * get message displays on Pop up after that, click 'x' button to close pop up
     */
    String getMessageOnPopupAndClose();

    /**
     * Wait for the invisible icons to display completely
     */
    void waitForLoadingIconInvisible();

    /**
     * Wait for the invisible icons to show completely during the given time
     */
    void waitForLoadingIconInvisible(int timeout);

    /**
     * This method is used for Desktop Mode
     * 
     * @param categoryPath
     *            should follow the format: "Department/Sub-Category/Category"
     * @see e.g. "Gifts/Gifting Options/Gift Cards"
     */
    void goToCategoryPageByLeftMenu(String categoryPath);

    /**
     * select category name to go department page by categoryPath
     */
    void goToDepartmentPage(String categoryPath);

    /**
     * click Side Menu Item link display in the Side Menu
     */
    void clickSideMenuItemLink(SideMenuItem sideMenuItem);

    /**
     * select the size and count displays under SKU
     */
    void selectFirstSizeAndCount();

    /**
     * click My Account link to go My account page after signed in valid account
     */
    void goToMyAccountPage();

    /**
     * click 'x' button to close the modal displays
     */
    void closeModal();

    /**
     * click 'continue' button display in modal after add Gift Card
     */
    void continueInModal();

    /**
     * check My Account link existed after signed up a new account or signed in a
     * valid account successfully
     */
    boolean isMyAccountInLinkExisted();

    /**
     * click 'sign out' link when signed in with valid account
     */
    void signOut();

    /**
     * get list wine Restriction display in added to cart modal
     */
    String getWineRestrictionLabel();

    /**
     * check added to cart modal displays when adding a SKU to cart
     */
    boolean isAddedToCartModalDisplayed();

    /**
     * Go to Homepage by clicking logo
     */
    void goToHomePage();

    /**
     * Fill a voucher code click submit button in voucher modal
     */
    void enterVoucherAndClickSubmitButtonInVoucherModal(String value);

    /**
     * Get the error message display in the Voucher modal when filling invalid
     * voucher code
     */
    String getErrorMessageInVoucherModal();

    /**
     * Navigate to Gift Certificates page
     */
    void goToGiftCertificatesPage();

    /**
     * Check the first SKU in all sections, pages does not display Out of Stock
     */
    boolean isFirstSKUDisplayedOutOfStock();

    /**
     * Click Win Free Steaks tab to Win Free Steaks page
     */
    void goToWinFreeSteaksPage();

    /**
     * go to store page when clicking Stores link in top menu
     */
    void goToStoresPage();
 
    void closeStoresPage();

	String getFirstItemName();
}
