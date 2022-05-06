package com.omahasteaks.page;

import java.util.Calendar;

import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;


public interface ConfirmationPage2SC extends GeneralPage {

    /**
     * Get all informations in all fields in Billing Address
     */
    String[] getBillingAddress();

    /**
     * Get the informations that are displayed at the Payment Method section
     */
    String[] getPaymentMethodContent();

    /**
     * Get the informations that are displayed at Contact Info section
     */
    String[] getContactInfoContent();

    /**
     * Get shipping address by First name and Last name
     */
    String[] getShippingAddressContent(CustomerAddress shippingAddress);

    /**
     * Get OrderNumber
     */
    String getOrderNumberText();

    /**
     * Get shipping section by First name and Last name
     */
    String getShippingSectionName(CustomerAddress shippingAddress);

    /**
     * Check Order Number displays correct format
     */
    boolean isOrderNumberCorrectFormat(String orderNumber);

    /**
     * Check SKU by First name and Last name displays
     */
    boolean isSKUDisplayed(CustomerAddress shippingAddress, SKU sku);
 
    /**
     * Check Thank You message displays
     */
    boolean isThankYouMessageDisplayed();
    
    /**
     * Get shipping method by First name and Last name
     */
    String getShippingMethodSection(CustomerAddress shippingAddress);

    /**
     * Get shipping delivery date by First name and Last name
     */
    Calendar getShippingDeliveryDate(CustomerAddress shippingAddress);
   
    /**
     * Get deliveried date in Shipping Address section by First name and Last name
     */
    String getDeliveriedDateByShippingAddressSection(CustomerAddress shippingAddress);
    
    /**
     * Get deliveried date in Shipping Address section by First name and Last name
     */
    String getShippingFeeInOrderTotalSection();
}
