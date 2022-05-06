package com.omahasteaks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.reflect.TypeToken;
import com.omahasteaks.data.enums.AccountType;
import com.omahasteaks.data.objects.Account;
import com.omahasteaks.utils.common.Common;

public class ListAccounts extends Database {
    private List<Account> lstAccounts;
    private static String jsonFile = "src/test/resources/data/account_data.json";

    public ListAccounts() {
	getListAccounts();
    }

    @SuppressWarnings("serial")
    public void getListAccounts() {
	lstAccounts = getListData(new TypeToken<List<Account>>() {
	}.getType(), jsonFile);
    }

    public Account getRandomAccount() {
	Random rd = new Random();
	List<Account> lstFilter = new ArrayList<Account>();
	String mode = Common.MODE.getRunningMode();
	for (Account acc : lstAccounts) {
	    if (acc != null)
		if (acc.getPlatform().equals(mode) && acc.getType().equals(AccountType.EXISTING))
		    lstFilter.add(acc);
	}
	Account acc = lstFilter.get(rd.nextInt(lstFilter.size()));
	return acc;
    }

    public Account setAccountByEmail(String email) {
	Account account = new Account();
    account.setEmail(email);
    account.setPassword(email.substring(0,email.indexOf("@")));
	return account;
    }
    
    public Account getAccountByEmail(String email) {
	Account account = new Account();
	for (Account acc : lstAccounts) {
	    if (acc != null)
		if (acc.getEmail().equals(email))
		    account = acc;
	}
	return account;
    } 
    public Account getRandomAccount(AccountType accountType) {
	Random rd = new Random();
	List<Account> lstFilter = new ArrayList<Account>();
	for (Account acc : lstAccounts) {
	    if (acc != null)
		if (acc.getType().equals(accountType))
		    lstFilter.add(acc);
	}
	Account acc = lstFilter.get(rd.nextInt(lstFilter.size()));
	return acc;
    }

    public void remove(Account account) {
	lstAccounts.remove(account);
    }

    public void add(Account account) {
	lstAccounts.add(account);
    }
}
