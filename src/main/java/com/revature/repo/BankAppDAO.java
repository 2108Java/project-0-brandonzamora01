package com.revature.repo;

import com.revature.models.User;

public interface BankAppDAO {
	
	//SELECT
	
	User selectAcctByName(String name);
	
	boolean userLogin(String name, String pw);
	
	boolean employeeLogin(String name, String pw);
	
	//UPDATE
	
	boolean userWithdraw();
	
	boolean userDeposit();
	
	boolean customerPostTransfer();
	
	boolean customerAcceptTransfer();
	
	//DELETE
	
	//INSERT
	
	boolean registerAcct(User newUser);

	//boolean systemRejectTrans();
	
}
