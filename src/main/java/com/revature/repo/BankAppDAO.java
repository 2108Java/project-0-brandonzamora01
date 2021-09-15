package com.revature.repo;

import com.revature.models.User;

public interface BankAppDAO {
	
	boolean registerAcct();
	
	boolean userLogin();
	
	int viewAcctBal();
	
	boolean userWithdraw();
	
	boolean userDeposit();
	
	//boolean systemRejectTrans();
	
	int employeeViewBalance();
	
	boolean customerPostTransfer();
	
	boolean customerAcceptTransfer();
}
