package com.omahasteaks.tests.MembershipAccount.PositiveCases;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_MAP_005_UserCanSignInWithSocialNetworkAccount extends TestBase_2SC {
    @Inject
    Account socialNetworkAccount;

    @Inject
    GeneralPage generalPage;

    @Inject
    SignInPage signInPage;

    @Inject
    MyAccountPage myAccountPage;

    @Test
    public void TC_MAP_005_User_Can_Sign_In_With_Social_Network_Account() {
	initTestCaseData();

	goTosignInPage();

	signInAndVerifyWithEachSocialNetwork();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void signInAndVerifyWithEachSocialNetwork() {
	for (String socialNetworkItem : Constants.LIST_SOCIALS_NETWORK_ITEM) {

	    clickSocialNetworkIcon(socialNetworkItem);

	    signInWithSocialNetworkAccount();

	    verifyMyAccountDisplaysOnUpperRightCornerOfTheScreen();

	    verifyMyAccountPagedisplays();

	    signOut();
	}
    }

    private void signOut() {
	Logger.info("In My Account:\r\n" + "- Click \"Sign out\" link");
	// myAccountPage.signOut();
    }

    /**
     * TBD
     */
    private void verifyMyAccountPagedisplays() {
	Logger.verify("Verify that My Account page is displayed");
	// TBD
    }

    private void verifyMyAccountDisplaysOnUpperRightCornerOfTheScreen() {
	Logger.verify("In My Account page:\r\n" + "Verify 'My Account' displays after signed correctly");
	// assertTrue(generalPage.isMyAccountInLinkExisted());
    }

    private void signInWithSocialNetworkAccount() {
	Logger.info("In Social Network Login page:\r\n" + "- Enter all valid information\r\n" + "- Click \"Login\" button");
	myAccountPage.signInWithSocialNetworkAccount(socialNetworkAccount);
    }

    private void clickSocialNetworkIcon(String nameSocialNetworkIcon) {
	Logger.info("In My Account page:\r\n" + "- Click a " + nameSocialNetworkIcon + " icon");
	myAccountPage.clickSocialNetworkIcon(nameSocialNetworkIcon);
    }

    private void goTosignInPage() {
	Logger.info("In Homepage:\r\n" + "- Click Sign In");
	generalPage.goToSignInPage();
    }

    private void initTestCaseData() {
	Logger.tc("TC_MAP_005 - User can use Social Account to sign into website");
	Logger.to("TO_MAP_16 - User can sign in with Facebook account");
	Logger.to("TO_MAP_17 - User can sign in with Yahoo account");
	Logger.to("TO_MAP_18 - User can sign in with Google plus account");
	Logger.to("TO_MAP_19 - User can sign in with Twitter account");
	socialNetworkAccount.setEmail(Constants.OMAHA_EMAIL_SOCIAL_NETWORK);
	socialNetworkAccount.setPassword(Constants.OMAHA_PASSWORD_SOCIAL_NETWORK);
    }
}
