package com.omahasteaks.data.objects;

import com.omahasteaks.data.enums.AccountType;
import com.omahasteaks.utils.common.Common;

public class Account {
	String email;
	String password;
	AccountType type;
	String platform;

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public AccountType getType() {
		return this.type;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void initRandomAccount() {
		this.email = Common.getRandomString("email") + "@omahasteaks.com";
		this.password = Common.getRandomString("pwd");
	}
}
