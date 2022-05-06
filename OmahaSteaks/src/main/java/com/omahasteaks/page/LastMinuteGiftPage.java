package com.omahasteaks.page;
 
import com.omahasteaks.data.enums.DeliveryTab; 
import com.omahasteaks.data.objects.SKU;

public interface LastMinuteGiftPage extends GeneralPage {

	/**
	 * click Progress tab displays on the Progress tab menu in Last minute Gift page
	 */
	void clickProgressTab(String progressTabName);

	/**
	 * check promotion message displayed under the SKU of tab in the Last-Minute
	 * Gifts
	 */
	boolean isPromotionMessageDisplayed(String promotionMessage);

	void addFirstItemToCart(SKU sku, boolean doAddExclusiveOffer );

	void addFirstSKUToCartFromProgressTab(SKU sku, DeliveryTab freeRushDelivery); 
	
}
