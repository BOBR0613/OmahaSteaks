package com.omahasteaks.page.signin;

import com.logigear.control.common.imp.Button;
import com.omahasteaks.page.SignInPage;

public class SignInPage_Mobile extends SignInPage_Desktop implements SignInPage {
	public SignInPage_Mobile() {
		super();
		btnLogin = new Button(interfaces.get("btnLogin_mb"));
		btnCreateNewAccount = new Button(interfaces.get("btnCreateNewAccount_mb"));
	}
	// ================================================================================
	// Locators
	// ================================================================================
	// ================================================================================
	// Methods
	// ================================================================================
}
