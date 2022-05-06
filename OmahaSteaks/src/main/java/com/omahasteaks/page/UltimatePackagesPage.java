package com.omahasteaks.page;

import com.omahasteaks.data.objects.Item;

public interface UltimatePackagesPage extends GeneralPage {
    /**
     * In Ultimate Packages page, Click on "Shop Now" on the first item
     */
    void clickFirstShopNowButton();

    /**
     * In Ultimate Packages page, at the first item: If having "Select your Free Gift"
     * drop down , select the first item and select "Ship To Myself then click "Add
     * To Cart" button
     */
    void addFirstSKUToCart(Item item);
}
