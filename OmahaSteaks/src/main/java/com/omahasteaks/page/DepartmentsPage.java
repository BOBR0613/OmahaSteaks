package com.omahasteaks.page;

import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.enums.BoxPlans;
import com.omahasteaks.data.objects.SKU;

public interface DepartmentsPage {

    /**
     * In Department page, get all links and images get all links and images by
     * section
     */
    ListImageLink getAllLinksAndImageBySection(String deparmentName);

    /**
     * Find Box Plans name Select the First SKU in this and add it to cart
     */
    void addFirstSKUToCartByBoxPlans(SKU sku, BoxPlans box);

    /**
     * check the EstArrivalDate of shipping method display behind each shipping
     * method that appears
     */
    boolean isEstArrivalDateByShippingMethodDisplayed(String shippingMethod);
}
