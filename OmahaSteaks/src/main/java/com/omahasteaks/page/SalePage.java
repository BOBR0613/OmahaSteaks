package com.omahasteaks.page;

import com.omahasteaks.data.enums.ProgressiveOfferTab;

public interface SalePage extends GeneralPage {
    /**
     * Verify Progressive Offers tabs are clickable
     */
    boolean isProgressiveOffersTabClickable();

    /**
     * In Sale page, go to progressive offer tab by progressive offer name
     */
    void clickProgressiveOfferTabByName(ProgressiveOfferTab progressiveOfferName);

    /**
     * In Sale page, fill email in "Earn an Extra 10% off" tab
     */
    void fillEmailAddressInProgressiveOffersTab(String email);

    /**
     * In Sale page, get error message displays when I Agree check box does not tick
     */
    String getIAgreeErrorMessage();

    /**
     * In Sale page, get error message displays when filling invalid Email Address
     */
    String getEmailErrorMessage();

    /**
     * In Sale page, at "Earn an Extra 10% off" tab: click I agree check box
     */
    void clickIAgreeCheckbox();

    /**
     * In Sale page, at "Earn an Extra 10% off" tab: click Submit button
     */
    void clickSubmitInSalePage();

    /**
     * In Sale page, verify You are In modal displays when clicking submit button
     */
    boolean isYouAreInModalDisplayed();
}
