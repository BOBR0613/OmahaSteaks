package com.omahasteaks.page;

import com.omahasteaks.data.objects.SKU;

public interface CategoryPage extends GeneralPage {
    /**
     * Enter keyword into Zip code text box in When When will it arrive? section
     * Submit search form
     */
    void searchWithZipCode(String zipCode);

    /**
     * Select first SKU in Modal And add it to cart
     */
    void addFirstSKUToCartInModal(SKU sku);

    /**
     * Select the category according to the path
     */
    void goToMeatsCategoryPage(String mealCategoryName);

    /**
     * Select a quick view for sku's information
     */
    void clickFirstSKUQuickViewLink();

    /**
     * Click Add to cart in QuickView Modal
     */
    void addQuickViewItemToCart();
    
    /**
     * Click 'Buy Gift Card' button in "Gifts/Gifting Options/Gift Cards" page
     */
    void clickBuyGiftCardButton();

    /**
     * Click 'Buy E Gift Card' button in "Gifts/Gifting Options/Gift Cards" page
     */
    void clickBuyEGiftCardButton();

    /**
     * Add Gift Card
     * 
     * @param giftCardType
     *            is one of ["Filet Mignon Gift Card","Thank You Gift
     *            Card","Birthday Gift Card"]<br>
     */
    void addGiftCard(String giftCardType, SKU sku);

    /**
     * Add Gift Card
     */
    void addGiftCard(SKU sku);

    /**
     * Add E-Gift Card
     */
    void addEGiftCard(SKU sku);

    /**
     * Fill a new Recipient's name when selecting Ship to in Modal
     */
    void enterNewShipToInModal(String recipient);
}
