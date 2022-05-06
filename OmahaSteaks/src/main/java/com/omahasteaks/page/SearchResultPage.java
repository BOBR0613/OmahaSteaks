package com.omahasteaks.page;

import com.omahasteaks.data.enums.SkuType;

public interface SearchResultPage extends GeneralPage {
    /**
     * In Search Result page, select the first item displays after searching keyword
     */
    void selectFirstItem();

    /**
     * In Search Result page, after searching keyword, click Tab displays
     * 
     * @param tab
     *            Items, Packages, Meals <br>
     */
    void clickTab(SkuType tab);

    /**
     * In Search Result page, verify Tab displays after searching keyword
     * 
     * @param tab
     *            Items, Packages, Meals <br>
     */
    boolean isTabDisplayed(String tab);

    /**
     * In Search Result page, verify Sort Type displays after searching keyword
     * 
     * @param sortType
     *            Most Relevant <br>
     */
    boolean isSortTypeDisplayed(String sortType);

    /**
     * In Search Result page, get count number by tab displays after searching
     * keyword
     * 
     * @param tab
     *            Items, Packages, Meals <br>
     */
    String getCountNumberByTab(SkuType tab);

    /**
     * In Search Result page, verify count number by tab displays greater than zero
     * after searching keyword
     * 
     * @param tab
     *            Items, Packages, Meals <br>
     */
    boolean isCountNumberByTabGreaterThanZero(SkuType tab);
}
