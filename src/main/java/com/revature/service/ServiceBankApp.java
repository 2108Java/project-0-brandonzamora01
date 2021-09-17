package com.revature.service;

import com.revature.models.User;

public interface ServiceBankApp {

	public boolean isAccount(String name, String pw);

	public User getAcctByName(String name);

	public boolean createAcct(User currentUser);
	
	
}
