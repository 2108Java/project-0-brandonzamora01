package com.revature.service;

//import com.revature.exceptions.BusinessException;
import com.revature.models.User;
import com.revature.repo.BankAppDAO;

public class ServiceBankAppImpl implements ServiceBankApp{
	
	BankAppDAO database;
	
	public ServiceBankAppImpl(BankAppDAO database) {
		this.database = database;
	}
	
	public User getAcctBal(String user) {	
		
		return database.selectAcctBal(user);
	}
	
	public boolean isAccount(String name, String password) {
		boolean correctInfo = false;
		
		correctInfo = database.userLogin(name, password);
		
		return correctInfo;
	}
	
}

