package com.omahasteaks.page;

import com.omahasteaks.data.enums.MenuTabsInStoresPage;
import com.omahasteaks.data.objects.CustomerAddress;

public interface StoresPage extends GeneralPage {
    /**
     * check menu tabs displayed in Stores page
     */
    boolean isMenuTabdisplayed(MenuTabsInStoresPage tabInStoresPage);

    /**
     * select Tab of menu tab in Stores page
     */
    void clickMenuTabdisplayed(MenuTabsInStoresPage tabInStoresPage);

    /**
     * fill valid Zip code into Zip code field in Stores page
     */
    void fillZipCode(CustomerAddress address);

    /**
     * click Submit button in Stores page
     */
    void clickSubmitButton();

    /**
     * check Nearest Stores address displayed in Stores page
     */
    boolean isNearestStoresAddressDisplayed();

    /**
     * check No Stores Found within 50 meters after filling valid Zip code
     */
    boolean isNoStoresFoundDisplayed();

    /**
     * get warning message displays when filling invalid zip code in Store page
     */
    String getErrorMessage();
}
