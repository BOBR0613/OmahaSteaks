package com.omahasteaks.page;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.objects.CustomerAddress;

public interface WinFreeSteaksPage extends GeneralPage {

    /**
     * In Win Free Steaks page, fill all customer address information into all
     * fields
     */
    void fillCustomerAddress(CustomerAddress customerAddress);

    /**
     * In Win Free Steaks page, fill Email address into Email address field
     */
    void fillEmailAddress(String email);

    /**
     * In Win Free Steaks page, tick I Agree check box
     */
    void clickIAgreeCheckbox();

    /**
     * In Win Free Steaks page, click Enter To Win button when signing up
     */
    void clickEnterToWinInWinFreeSteakPage();

    /**
     * In Win Free Steaks page, verify Thank You message displays when signing up
     * successful
     */
    boolean isThankYouMessageDisplayed();

    /**
     * In Win Free Steaks page, get error message by field displays when filling
     * invalid value or blank into field
     */
    String getErrorMessageByField(AddressFields addrField);

    /**
     * In Win Free Steaks page, get error message when does not tick I Agree check
     * box
     */
    String getIAgreeErrorMessage();
}
