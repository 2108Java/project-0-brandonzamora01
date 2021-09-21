package com.revature.repo;

import com.revature.models.Transaction;
import com.revature.models.User;

public interface BankAppDAO {
	
	//SELECT
	
	User selectAcctByName(String name);
	
	boolean userLogin(String name, String pw);
	
	boolean employeeLogin(String name, String pw);
	
	Transaction selectPendingTranfer(String recName);
	
	int selectPendingCount(String recName);
	
	Transaction[] selectAllTransactions(int numbTrans);
	
	public int selectNumTransactions();
	
	public String[] selectUnapprovedCustomer(int num);
	
	public int selectUnapprovedNum();
	
	//UPDATE
	
	boolean updateAccountBalance(Transaction arg);
	
	boolean updateTransferStatus(int transId);
	
	boolean updateUserAccess(String name);
	
	boolean updateCheckingStatus(String name);
	
	boolean updateSavingStatus(String name);
	
	//DELETE
	
	//INSERT
	
	boolean registerAcct(User newUser);
	
	boolean insertTransaction(Transaction arg);

	//boolean systemRejectTrans();
	
}
