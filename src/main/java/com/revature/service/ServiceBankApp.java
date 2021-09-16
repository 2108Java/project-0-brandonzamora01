package com.revature.service;

import com.revature.models.User;

public interface ServiceBankApp {

	public boolean isAccount(String name, String pw);

	public User getAcctBal(String name);
	
}
