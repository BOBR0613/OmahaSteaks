package com.omahasteaks.page.account;
/*package pages.AccountPage2SCImp;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Link;
import com.logigear.control.common.imp.TextBox;

import data.objects.Account;
import pages.AccountPage2SC;
import utils.common.Common;
import utils.helper.Logger;

public class AccountPage2SC_Mobile extends AccountPage2SC_Desktop implements AccountPage2SC {
    public AccountPage2SC_Mobile() {
	super();
	btnCreateAccountAndCheckout = new Button(interfaces.get("btnCreateAccountAndCheckout_mb"));
	rdNoAccountYet = new CheckBox(interfaces.get("rdCreateAccount_mb"));
	rdIHaveAnAccount = new CheckBox(interfaces.get("rdSignIn_mb"));
	eleEmailAddressErrorMessage = new Element(interfaces.get("eleEmailAddressErrorMessage_mb"));
	elePasswordErrorMessage = new Element(interfaces.get("elePasswordErrorMessage_mb"));
    }

    // ================================================================================
    // Locators
    // ================================================================================
    protected Link lnkShowPassword_mb = new Link(interfaces.get("lnkShowPassword_mb"));
    protected TextBox txtFirstName_mb = new TextBox(interfaces.get("txtFirstName_mb"));
    protected TextBox txtLastName_mb = new TextBox(interfaces.get("txtLastName_mb"));

    // ================================================================================
    // Support Methods
    // ================================================================================
    @Override
    protected void showPassword() {
	Common.click(lnkShowPassword_mb);
    }

    // ================================================================================
    // Methods
    // ================================================================================
    @Override
    public void createAccount(Account account) {
	Common.click(rdNoAccountYet);
	showPassword();
	Common.enter(txtEmailAddress, account.getEmail());
	Common.enter(txtCreatePassword, account.getPassword());
	Common.enter(txtFirstName_mb, Common.getRandomString("First"));
	Common.enter(txtLastName_mb, Common.getRandomString("Last"));
	Common.click(btnCreateAccountAndCheckout);
    }

    @Override
    public void signIn(String email, String password) {
	Logger.info(" - Sign in with account: " + email);
	Common.click(rdIHaveAnAccount);
	Common.enter(txtEmailAddress, email);
	Common.enter(txtPassword, password);
	Common.click(btnSignIn);
    }
}
*/