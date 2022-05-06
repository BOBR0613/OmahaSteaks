package com.omahasteaks.page;

import java.util.List;

import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.enums.LinksFooter;
import com.omahasteaks.data.enums.PromotionLocation;
import com.omahasteaks.data.enums.Sections;
import com.omahasteaks.data.objects.ImageLink;
import com.omahasteaks.data.objects.Package;

public interface HomePage extends GeneralPage {

    /**
     * get all links and images display by each section
     */
    ListImageLink getAllLinksAndImageBySection(Sections section);

    /**
     * check the mapping between images and links by each section
     */
    boolean checkMappingBetweenImageAndLink(ImageLink imgLink, Sections section);

    /**
     * get all departments name displays in Home page
     */
    List<String> getDepartmentsName();

    /**
     * check the center stage tabs clickable
     */
    boolean isCenterStageTabClickable();

    /**
     * click Term Agreement links in footer
     */
    void clickTermAgreementLinkInFooter(LinksFooter nameLink);

    /**
     * check Term Of Use popup in footer displayed
     */
    boolean isTermsOfUsePopupInFooterDisplayed();

    /**
     * check Privacy Policy popup in footer displayed
     */
    boolean isPrivacyPolicyPopupInFooterDisplayed();

    /**
     * check Privacy Policy Page displayed
     */
    boolean isPrivacyPolicyPageDisplayed();
    
    /**
     * click link of section in footer
     */
    void clickLinkInFooterBySection(String nameSection, LinksFooter nameLink);

    /**
     * fill email address into Email address text box in footer
     */
    void fillEmailAddressInFooter(String email);

    /**
     * Tick I agree check box
     */
    void clickIAgreeCheckbox();

    /**
     * click Join button in footer
     */
    void clickJoinButtonInFooter();

    /**
     * check You Are In modal displayed
     */
    boolean isYouAreInModalDisplayed();

    /**
     * Navigate to Contact Preferences page
     */
    void goToContactPreferences();

    /**
     * fill Email Address in Email Address text box in Contact Preferences page
     */
    void fillEmailAddressInContactPreferencesPage(String emailAddress);

    /**
     * select option 'Do not send me any emails' to unsubscribe email in Contact
     * Preferences page
     */
    void unsubscribeEmailInContactPrefencesPage();

    /**
     * check Contact Preferences Update modal displayed when updated successful
     */
    boolean isContactPreferencesUpdateModalDisplayed();

    /**
     * get error message displayed in the Email address text box in footer when
     * filling invalid email address
     */
    String getEmailErrorMessage();

    /**
     * get error message displayed in the I agree check box in footer when I agree
     * check box does not tick
     */
    String getIAgreeErrorMessage();
    
    /**
     * Get text on the promotion bar
     * @param location: - left: get text of the left promotion bar
     *                  - right: get text of the right promotion bar
     */
    String getPromotionText(PromotionLocation location);

    boolean isPromotionTextBold(String text, PromotionLocation location);
    
    void clickPromotionLink(PromotionLocation location);
    
    String getTitleOfPromotionPopup();

	void addFirstSkuToCart(Package myPkg, boolean b);
}
