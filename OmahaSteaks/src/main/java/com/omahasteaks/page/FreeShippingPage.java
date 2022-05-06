package com.omahasteaks.page;

import com.omahasteaks.data.objects.Package;

public interface FreeShippingPage extends GeneralPage {

    /**
     * Select a sku and add it to Recipient's cart. After that, add SKU of Exclusive
     * Offer model or not.
     */
    void addFirstSkuToCart(Package packageMyself, boolean doAddExclusiveOffer);
    

	void addFirstSkuToCart(boolean doAddExclusiveOffer);
}
