package com.revature.service;

//import com.revature.exceptions.BusinessException;
import com.revature.models.User;
import com.revature.repo.BankAppDAO;

public class ServiceBankAppImpl implements ServiceBankApp{
	
	BankAppDAO database;
	
	public ServiceBankAppImpl(BankAppDAO database) {
		this.database = database;
	}
	
	public User getAcctByName(String user) {	
		return database.selectAcctByName(user);
	}
	
	public boolean isAccount(String name, String password) {
		boolean correctInfo = false;
		
		correctInfo = database.userLogin(name, password);
		
		return correctInfo;
	}
	
	public boolean createAcct(User name) {
		boolean createSuccessful = false;
		
		createSuccessful = database.registerAcct(name);
		
		return createSuccessful;
	}
}

