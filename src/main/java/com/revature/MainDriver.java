package com.revature;

import com.revature.models.User;
import com.revature.presentation.Menu;
import com.revature.presentation.MenuV1;
import com.revature.repo.BankAppDAO;
import com.revature.repo.BankAppDAOImpl;
import com.revature.service.ServiceBankApp;
import com.revature.service.ServiceBankAppImpl;


public class MainDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankAppDAO database = new BankAppDAOImpl();
		
		//User bob = new User("Bob", "Password1", 100, true, false);
		//User newUser = new User("Main", "password", 200, 1000, true, true);
		
		//database.registerAcct(newUser);
		ServiceBankApp service = new ServiceBankAppImpl(database);
		
		Menu mainMenu = new MenuV1(service);
		
		mainMenu.defaultDisplay();
	}

}
