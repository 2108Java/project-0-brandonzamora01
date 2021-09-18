package com.revature.service;

import com.revature.models.Transaction;
import com.revature.models.User;

public interface ServiceBankApp {

	public boolean isUserAccount(String name, String pw);
	
	public boolean isEmployeeAccount(String name, String pw);

	public User getAcctByName(String name);

	public boolean createAcct(User currentUser);
	
	public boolean postTransfer(Transaction arg);
	
	public boolean acceptTransfer(String recName);
	
	public boolean userDeposit(Transaction arg);
	
	public boolean userWithdrawal(Transaction arg);
	
	public boolean pendingCount(String name);
	
	public void displayTransferInfo(String name);
	
}
