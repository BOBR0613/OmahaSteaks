package com.omahasteaks.page;

import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;

public interface ProductPage extends GeneralPage {

    void addItemToCart(Item item);

    /**
     * In Product page, add Item to cart when clicking add to cart button If
     * Exclusive Offer (Upsell offer) appears,
     * 
     * @param doAddExclusiveOffer
     *            If yes, click "Add To Cart" button to add it to cart. <br>
     *            If no, click "No Thanks" button not to add it to cart <br>
     * @param isRandom
     *            If yes, the size and count are selected random. <br>
     *            If no, the size and count are select the first value <br>
     */
    void addItemToCart(Item item, boolean doAddExclusiveOffer, boolean isRandom);

    /**
     * In Product page, add SKU to cart when clicking add to cart button. If
     * Exclusive Offer (Upsell offer) appears,
     * 
     * @param doAddExclusiveOffer
     *            If yes, click "Add To Cart" button to add it to cart. <br>
     *            If no, click "No Thanks" button not to add it to cart <br>
     */
    void addSKUToCart(SKU sku, boolean doAddExclusiveOffer);

    /**
     * In Product page, select Recipient in Ship to package box and enter Recipient's
     * name into Recipient text box before clicking add to cart
     */
    void selectShipToAndSelectAddToCart(SKU sku);
}
