package com.omahasteaks.page;

import com.omahasteaks.data.objects.SKU;

public interface CustomPackagePage extends GeneralPage {
    /**
     * Create a new combo . Add products to each group on the cart.
     */
    void createCustomPackage();

    /**
     * Create a new combo Add products to each group on the cart. After that, add
     * SKU of Exclusive Offer model or not.
     */
    void createCustomPackageAndAddToCart(SKU sku, boolean doAddExclusiveOffer);

    /**
     * Click edit link in the cart edit a SKU in Custom Combo. After ending, click
     * save .
     */
    void editCustomPackageAndSaveEdits(SKU sku);
 
    void addFirstSKUToCart(SKU pkg, boolean doAddExclusiveOffer);
}
