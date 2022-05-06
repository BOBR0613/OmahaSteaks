package com.omahasteaks.page;

import com.omahasteaks.data.objects.SKU;

public interface PromotionModal extends GeneralPage {

    /**
     * In Just For You modal, Fill valid email into text box and check "I Agree"
     * check box and click "Get The Deals" button
     */
    void selectGetTheDealsButtonWithRandomEmail();

    /**
     * In Today-Only Deals modal, at the first SKU: select "Ship To Myself" and
     * click "ADD TO CART" button
     */
    void addFirstSkuOnTodayOnlyDealsModal(SKU sku);

    /**
     * Verify Today-Only Deals modal pops up with three items
     */
    boolean isTodayOnlyDealsModalDisplayed();

    /**
     * Verify that sub items are rotated when clicking rotator in each item
     */
    boolean areSubItemsRotated();

}
