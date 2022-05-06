package com.omahasteaks.page;

import com.omahasteaks.data.objects.Account;

public interface SignInPage extends GeneralPage {
 
    /**
     * In Sign in page, sign in by entering your email and password
     */
    void signIn(String email, String password);

    /**
     * In Sign in page, sign in by entering your account
     */
    void signIn(Account account);

    /**
     * In Sign in page, click Create New Account button to create a new account
     */
    void clickCreateNewAccountButton();

    /**
     * In Sign in page, get warning message displays
     */
    String getWarningMessage();
    
    /**
     * Verify the Sign In Page is displayed
     */
    public boolean isSignInPageDisplayed();
}
