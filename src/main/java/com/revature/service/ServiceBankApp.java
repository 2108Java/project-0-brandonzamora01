package com.revature.service;

import com.revature.models.Transaction;
import com.revature.models.User;

public interface ServiceBankApp {

	public boolean isUserAccount(String name, String pw);
	
	public boolean isEmployeeAccount(String name, String pw);

	public User getAcctByName(String name);

	public boolean createAcct(User currentUser);
	
	public boolean createCheckingAcct(String name);
	
	public boolean createSavingAcct(String name);
	
	public boolean approveUserAccess(String name);
	
	public boolean postTransfer(Transaction arg);
	
	public boolean acceptTransfer(String recName);
	
	public boolean userDeposit(Transaction arg);
	
	public boolean userWithdrawal(Transaction arg);
	
	public boolean pendingCount(String name);
	
	public void displayTransferInfo(String name);
	
	public Transaction[] getTransactionLog(int num);
	
	public int getTransactionCount();
	
	public int getUnapprovedCount();
	
	public String[] getUnapprovedList(int num);
	
	public boolean isValidAmount(int amount);

	public boolean isValidWithdrawal(int amount, int balance);
	
}
