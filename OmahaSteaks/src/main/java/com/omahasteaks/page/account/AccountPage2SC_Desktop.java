package com.omahasteaks.page.account;
/*package pages.AccountPage2SCImp;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.TextBox;

import data.objects.Account;
import pages.AccountPage2SC;
import pages.GeneralPageImp.GeneralPage_Desktop;
import utils.common.Common;
import utils.helper.Logger;

public class AccountPage2SC_Desktop extends GeneralPage_Desktop implements AccountPage2SC {
    // ================================================================================
    // Locators
    // ================================================================================
    protected TextBox txtEmailAddress = new TextBox(interfaces.get("txtEmailAddress"));
    protected TextBox txtPassword = new TextBox(interfaces.get("txtPassword"));
    protected TextBox txtCreatePassword = new TextBox(interfaces.get("txtCreatePassword"));
    protected Button btnSignIn = new Button(interfaces.get("btnSignIn"));
    protected Button btnLogIn = new Button(interfaces.get("btnLogIn"));
    protected Button btnCreateAccountAndCheckout = new Button(interfaces.get("btnCreateAccountAndCheckout"));
    protected Button btnCheckoutAsGuest = new Button(interfaces.get("btnCheckoutAsGuest"));
    protected Button btnProceedAsGuest = new Button(interfaces.get("btnProceedAsGuest"));
    protected CheckBox rdNoAccountYet = new CheckBox(interfaces.get("rdNoAccountYet"));
    protected CheckBox rdIHaveAnAccount = new CheckBox(interfaces.get("rdIHaveAnAccount"));
    protected CheckBox chbShowPassword = new CheckBox(interfaces.get("chbShowPassword"));
    protected Element eleEmailAddressErrorMessage = new Element(interfaces.get("eleEmailAddressErrorMessage"));
    protected Element elePasswordErrorMessage = new Element(interfaces.get("elePasswordErrorMessage"));

    // ================================================================================
    // Support Methods
    // ================================================================================
    protected void showPassword() {
	Common.check(chbShowPassword);
    }

    // ================================================================================
    // Methods
    // ================================================================================
    public void signIn(String email, String password) {
	Logger.info(" - Sign in with account: " + email);
	if (rdIHaveAnAccount.isDisplayed()) {
	    Common.check(rdIHaveAnAccount);
	    Common.enter(txtEmailAddress, email);
	    Common.enter(txtPassword, password);
	    Common.click(btnSignIn);
	} else {
	    Common.enter(txtEmailAddress, email);
	    Common.enter(txtPassword, password);
	    Common.click(btnLogIn);
	}
    }

    public void signIn(Account account) {
	signIn(account.getEmail(), account.getPassword());
	Common.waitForPageLoad();
    }

    public void checkoutAsGuest() {
	Common.click(btnCheckoutAsGuest);
	Common.click(btnProceedAsGuest);
	Common.waitForTitleChange();
    }

    public void createAccount(Account account) {
	Common.check(rdNoAccountYet);
	showPassword();
	Common.enter(txtEmailAddress, account.getEmail());
	Common.enter(txtCreatePassword, account.getPassword());
	Common.click(btnCreateAccountAndCheckout);
	Common.waitForTitleChange();
    }

    public String getEmailAddressErrorMessage() {
	return Common.getText(eleEmailAddressErrorMessage);
    }

    public String getPasswordErrorMessage() {
	return Common.getText(elePasswordErrorMessage);
    }    
}
*/