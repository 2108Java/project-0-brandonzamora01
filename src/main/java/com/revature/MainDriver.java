package com.revature;

import com.revature.models.User;
import com.revature.presentation.Menu;
import com.revature.presentation.MenuV1;
import com.revature.repo.BankAppDAO;
import com.revature.repo.BankAppDAOImpl;
import com.revature.service.ServiceBankApp;
import com.revature.service.ServiceBankAppImpl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class MainDriver {
	
	public final static Logger loggy = Logger.getLogger(MainDriver.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loggy.setLevel(Level.WARN);
		loggy.warn("Starting the application");
		
		BankAppDAO database = new BankAppDAOImpl();
		
		ServiceBankApp service = new ServiceBankAppImpl(database);
		
		Menu mainMenu = new MenuV1(service);
		
		mainMenu.defaultDisplay();
		
		//User bob = new User("Bob", "Password1", 100, true, false);
		//User newUser = new User("Main", "password", 200, 1000, true, true);
		//database.registerAcct(newUser);
	}

}
