package com.revature.service;

import com.revature.models.Transaction;
//import com.revature.exceptions.BusinessException;
import com.revature.models.User;
import com.revature.repo.BankAppDAO;

public class ServiceBankAppImpl implements ServiceBankApp{
	
	BankAppDAO database;
	
	public ServiceBankAppImpl(BankAppDAO database) {
		this.database = database;
	}
	@Override
	public User getAcctByName(String user) {	
		return database.selectAcctByName(user);
	}
	@Override
	public boolean isUserAccount(String name, String password) {
		boolean correctInfo = false;
		
		correctInfo = database.userLogin(name, password);
		
		return correctInfo;
	}
	@Override
	public boolean isEmployeeAccount(String name, String password) {
		boolean correctInfo = false;
		
		correctInfo = database.employeeLogin(name, password);
		
		return correctInfo;
	}
	@Override
	public boolean createAcct(User name) {
		boolean createSuccessful = false;
		
		createSuccessful = database.registerAcct(name);
		
		return createSuccessful;
	}
	@Override
	public boolean postTransfer(Transaction arg) {
		boolean success = false;
		User name = getAcctByName(arg.getRootName()); //get username to determine start/end balance
		String acctType = arg.getRootType(); // determine which account the user is depositing to 
		int endBal = 0;
		int startBal = 0;
		boolean updateUserTable = false;
		boolean updateTransTable = false;
		if (acctType.equals("Checkings")) { //run if user is depositing to checkings acct
			arg.setStartBal(name.getCheckingBalance());//set start balance = to user current balance
			startBal = arg.getStartBal();
			endBal = startBal - arg.getTransAmount(); // calc end balance
			arg.setEndBal(endBal); // set new balance
			updateTransTable = database.insertTransaction(arg);//add deposit data to transactions table	
			updateUserTable = database.updateAccountBalance(arg);//update user's balance in SQL database with new value
		}else if (acctType.equals("Savings")) { // run if user is depositing to savings acct
			arg.setStartBal(name.getSavingBalance());
			startBal = arg.getStartBal();
			endBal = startBal - arg.getTransAmount();
			arg.setEndBal(endBal);
			updateTransTable = database.insertTransaction(arg);
			updateUserTable = database.updateAccountBalance(arg); 
		}else {
			System.out.println("An Error Has Occured!");
		}
		success = (updateUserTable && updateTransTable);
		System.out.println("Amount Has Been Withdrawn From Your Account. The Reciever Will Now Have To Accept The Transfer.");
		return success;
	}

	@Override
	public boolean acceptTransfer(String recName) {
		// TODO Auto-generated method stub
		boolean transfer = false;
		boolean updateApproveStatus = false;
		boolean updateTransTable = false;
		boolean updateUserTable = false;

		Transaction pendingTransaction = database.selectPendingTranfer(recName);// retrieve pending transaction from database and use data to create a new separate transaction
		int transId = pendingTransaction.getTransId();							// get transaction ID. Use Later to change approved status
		int transAmount = pendingTransaction.getTransAmount();					// get trans amount. Use later to update rec balance
		String depAcctType =pendingTransaction.getDestinationType();			// get the acct type to send funds to
		User name = getAcctByName(recName);										// get User account data so that we can update balance with correct amount
		
		pendingTransaction.setRootName(recName);								// set transaction name = to the receiver
		pendingTransaction.setRootType(depAcctType);							// set transaction type = checkings or savings

		pendingTransaction.setDestinationName(null);							// set destination of new trans to null
		pendingTransaction.setDestinationType(null);							// set destination type to null
		pendingTransaction.setSentStatus(true);									// set sent status to approved

		if (depAcctType.equals("Checkings")) { 									// run if user is depositing to checking acct
			
			pendingTransaction.setStartBal(name.getCheckingBalance());			// set start balance = to user current balance
			int startBal = pendingTransaction.getStartBal();
			int endBal = startBal + transAmount;								// calc end balance
			pendingTransaction.setEndBal(endBal); 								// set new balance
			
			updateTransTable = database.insertTransaction(pendingTransaction);	// add deposit data to transactions table	
			updateUserTable = database.updateAccountBalance(pendingTransaction);// update user's balance in SQL database with new value
			
			System.out.println("\nTransfer To Checkings Is Being Processed");

		} else if(depAcctType.equals("Savings")) { 								// run if user is depositing to savings acct
			
			pendingTransaction.setStartBal(name.getSavingBalance());
			int startBal = pendingTransaction.getStartBal();
			int endBal = startBal + transAmount; 
			pendingTransaction.setEndBal(endBal); 
			updateTransTable = database.insertTransaction(pendingTransaction);
			updateUserTable = database.updateAccountBalance(pendingTransaction);
			System.out.println("\nTransfer To Savings Is Being Processed");
		}

		if (updateUserTable && updateTransTable) { 								// if both tables have been successfully updated then run this section
			updateApproveStatus = database.updateTransferStatus(transId);		// Update initial transaction to approved
		}else {
			System.out.println("An Error Occured When Updating Database");
		}
	
		transfer = updateApproveStatus;
		return transfer;
	}
	

	@Override
	public boolean userDeposit(Transaction arg) {
		//Using a Transaction object, the method return a boolean representing the success of the operation.
		boolean success = false;
		User name = getAcctByName(arg.getRootName()); //get username to determine start/end balance
		String acctType = arg.getRootType(); // determine which account the user is depositing to 
		int endBal = 0;
		int startBal = 0;
		boolean updateUserTable = false;
		boolean updateTransTable = false;
		boolean validAmount = isValidAmount(arg.getTransAmount()); //check for negative deposit value
		
		if (acctType.equals("Checkings") && validAmount) { //run if user is depositing to checkings acct
			arg.setStartBal(name.getCheckingBalance());//set start balance = to user current balance
			startBal = arg.getStartBal();
			endBal = arg.getTransAmount() + startBal; // calc end balance
			arg.setEndBal(endBal); // set new balance
			updateTransTable = database.insertTransaction(arg);//add deposit data to transactions table	
			updateUserTable = database.updateAccountBalance(arg);//update user's balance in SQL database with new value
		}else if (acctType.equals("Savings") && validAmount) { // run if user is depositing to savings acct
			arg.setStartBal(name.getSavingBalance());
			startBal = arg.getStartBal();
			endBal = arg.getTransAmount() + startBal;
			arg.setEndBal(endBal);
			updateTransTable = database.insertTransaction(arg);
			updateUserTable = database.updateAccountBalance(arg); 
		}else {
			System.out.println("An Error Has Occured!");
		}
		
		success = (updateUserTable && updateTransTable);
		if (success) {
			System.out.println("Deposit Successful!");
		}
		return success;
	}

	@Override
	public boolean userWithdrawal(Transaction arg) {
		//Using a Transaction object, the method return a boolean representing the success of the operation.
		boolean success = false;
		User name = getAcctByName(arg.getRootName());					 //get username to determine start/end balance
		String acctType = arg.getRootType(); 							// determine which account the user is withdrawing from 
		int endBal = 0;
		int startBal = 0;
		boolean updateUserTable = false;
		boolean updateTransTable = false;
		if (isValidAmount(arg.getTransAmount())) {						//check for valid withdraw amount (no negative numbers)
			if (acctType.equals("Checkings")) { 						//run if user is depositing to checkings acct
				startBal = name.getCheckingBalance();					//get start balance from users current balance
			}else if(acctType.equals("Savings")) {						//run if user is depositing to savings acct
				startBal = name.getSavingBalance();
			}else {
				System.out.println("An Error Occured. Unable To Get Starting Balance. Withdraw Cancelled.");
			}
			if (isValidWithdrawal(arg.getTransAmount(), startBal)) {	//check to see if withdraw amount is more than current balance
				arg.setStartBal(startBal);								//set start 
				endBal = startBal - arg.getTransAmount();				//calc end balance
				arg.setEndBal(endBal);									//set end balance
				updateTransTable = database.insertTransaction(arg);		//add deposit data to transactions table	
				updateUserTable = database.updateAccountBalance(arg);	//update user's balance in SQL database with new value
			}
		}
		success = (updateUserTable && updateTransTable);				//return successful if the transaction can process and if tables are updated. 
		if (success) {
			System.out.println("Withdraw Successful!");
		}
		return success;
	}

	public boolean isValidAmount(int amount) {
		boolean valid = false;
		if (amount > 0) {
			valid = true;
		}else {
			System.out.println("Invalid Amount. Please Try Again");
		}
		return valid;
	}
	
	public boolean isValidWithdrawal(int amount, int balance) {
		boolean valid = false;
		if (amount < balance) {
			valid = true;
		}else {
			System.out.println("You Cannot Withdraw More Than The Current Balance. Please Try Again");
		}
		return valid;
	}
	public boolean pendingCount(String name) {
		boolean isPending = false;
		int count = database.selectPendingCount(name);
		if (count > 0) {
			isPending = true;
		}
		return isPending;
	}
	
	public void displayTransferInfo(String name) {
		
		Transaction displayTransfer = database.selectPendingTranfer(name);   
		int transAmount = displayTransfer.getTransAmount();					
		String depAcctType = displayTransfer.getDestinationType();			
		String senderName = displayTransfer.getRootName();
		
		System.out.println(senderName+" Would Like To Send "+ transAmount+ " To Your "+ depAcctType +" Account");
	}
	
	public Transaction[] getTransactionLog(int num) {
		return database.selectAllTransactions(num);
	}
	
	public int getTransactionCount() { 
	 return database.selectNumTransactions();
	}
	@Override
	public int getUnapprovedCount() {
		// TODO Auto-generated method stub
		return database.selectUnapprovedNum();
	}
	@Override
	public String[] getUnapprovedList(int num) {
		// TODO Auto-generated method stub
		return database.selectUnapprovedCustomer(num);
	}
	@Override
	public boolean approveUserAccess(String name) {
		// TODO Auto-generated method stub
		return database.updateUserAccess(name);
	}
	@Override
	public boolean createCheckingAcct(String name) {
		// TODO Auto-generated method stub
		return database.updateCheckingStatus(name);
	}
	@Override
	public boolean createSavingAcct(String name) {
		// TODO Auto-generated method stub
		return database.updateSavingStatus(name);
	}
}




