package com.omahasteaks.page.signin;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.TextBox;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.page.general.GeneralPage_Desktop;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Common.ActionType;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class SignInPage_Desktop extends GeneralPage_Desktop implements SignInPage {
	// ================================================================================
	// Locators
	// ================================================================================
	protected TextBox txtEmailAddress = new TextBox(interfaces.get("txtEmailAddress"));
	protected TextBox txtPassword = new TextBox(interfaces.get("txtPassword"));
	protected Button btnLogin = new Button(interfaces.get("btnLogin"));
	protected Button btnCreateNewAccount = new Button(interfaces.get("btnCreateNewAccount"));

	// ================================================================================
	// Methods
	// ================================================================================
	public void signIn(String email, String password) {
		Common.waitForPageLoad();
		Logger.substep("Sign in with account: " + email);
		Common.enter(txtEmailAddress, email);
		Common.enter(txtPassword, password);
		Common.click(btnLogin);
		Common.waitForDOMChange();
		Common.waitForPageLoad();
	}

	public void clickCreateNewAccountButton() {
		Common.waitForDisplayed(btnCreateNewAccount);
		if (Common.getCurrrentBrowser().equals(Constants.BROWSER_SAFARI))
			Common.click(btnCreateNewAccount, ActionType.JS);
		else
			Common.click(btnCreateNewAccount);
		Common.waitForPageLoad();
		Common.waitForNotDisplayed(btnCreateNewAccount);
	}

	public void signIn(Account account) {
		signIn(account.getEmail(), account.getPassword());
	}

	public String getWarningMessage() {
		String message = txtEmailAddress.getAttribute("validationMessage");
		return message;
	}

	public boolean isSignInPageDisplayed() {
		return Common.getTitlePage().equals(Constants.SIGN_IN_PAGE_TITLE);
	}
}
