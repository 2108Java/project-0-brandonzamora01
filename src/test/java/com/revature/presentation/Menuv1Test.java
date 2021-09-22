package com.revature.presentation;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.revature.models.User;
import com.revature.repo.BankAppDAO;
import com.revature.repo.BankAppDAOImpl;
import com.revature.service.ServiceBankApp;
import com.revature.service.ServiceBankAppImpl;

public class Menuv1Test {
	
	Menu m;
	User u;
	@Before
	public void setupMenu() {
		BankAppDAO db = new BankAppDAOImpl();
		ServiceBankApp serv = new ServiceBankAppImpl(db);
		Menu m = new MenuV1(serv);
	}
	
	@Test
	public void testDefaultDisplay() {
		 
		//assertTrue(isUserAccount("Brandon","password"));
		
	}
	
	
	
}
