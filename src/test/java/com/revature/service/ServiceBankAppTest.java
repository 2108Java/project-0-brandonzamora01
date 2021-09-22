package com.revature.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.revature.models.User;
import com.revature.repo.BankAppDAO;
import com.revature.repo.BankAppDAOImpl;

public class ServiceBankAppTest {
	
	private String dbLocation = "localhost";
	private String username = "postgres";
	private String password = "Training123";
	private String url = "jdbc:postgresql://" + dbLocation + "/postgres";
	
	BankAppDAO db;
	ServiceBankApp serv;
	User us;

	@Before
	public void setupService() {
		db = new BankAppDAOImpl();
		serv = new ServiceBankAppImpl(db);
	}
	//Test #1
	@Test
	public void testIsUserAccount() {
		//Given The Proper User name and Password, this method should return True
		assertTrue(serv.isUserAccount("Bob","bobspw"));
		//Given The Correct User name but Incorrect Password, this method should return False
		//Also Testing case sensitivity of method
		assertFalse(serv.isUserAccount("Bob","Bobspw"));
		//Given The Incorrect User name and Correct password, this method should return False
		//Also Testing case sensitivity of method
		assertFalse(serv.isUserAccount("bob","bobspw"));
		//Given The Incorrect User name and password, this method should return False
		assertFalse(serv.isUserAccount("notUser","notPassword"));
		
	}
	//Test #2
	@Test
	public void testIsEmployeeAccount() {
		//Given The Proper User name and Password, this method should return True
		assertTrue(serv.isEmployeeAccount("Main","password"));

		//Given The Incorrect User name and password, this method should return False
		assertFalse(serv.isEmployeeAccount("notUser","notPassword"));
		
	}
	//Test #3
	@Test
	public void testCreateCheckingAcct() {
		//Given The Proper User name, this method should return True after the checking acct has been created
		assertTrue(serv.createCheckingAcct("Bob"));
		
		//DoubleChecking
		//Once Users Checking Account Has Been Created, check database and see if create checking is Actually true for that user
		boolean isCreateChecking = false;
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT * FROM all_users_table WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "Bob");
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			isCreateChecking = rs.getBoolean("create_checking");
			
			//Check For Users Checking Account after DB query
			assertTrue(isCreateChecking);
			
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An Error Has Occured");
		}
		
		
		//Given The Incorrect User name, this method should return False
		assertFalse(serv.createCheckingAcct("notUser"));
		
	}
	//Test #4
	@Test
	public void testCreateSavingAcct() {
		//Given The Proper User name, this method should return True after the savings acct has been created
		assertTrue(serv.createSavingAcct("Bob"));
		
		//DoubleChecking
		//Once Users Savings Account Has Been Created, check database and see if create_savings is Actually true for that user
		boolean isCreateSaving = false;
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT * FROM all_users_table WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "Bob");
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			isCreateSaving = rs.getBoolean("create_saving");
			
			//Check For Users Savings Account after DB query
			assertTrue(isCreateSaving);
			
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An Error Has Occured");
		}
		
		
		//Given The Incorrect User name, this method should return False
		assertFalse(serv.createSavingAcct("notUser"));
		
	}
	//Test #5
	@Test
	public void testApproveUserAccess() {
		//Given The Proper User name, this method should return True after the user has been approved by an employee
		assertTrue(serv.approveUserAccess("Bob"));
		
		//DoubleChecking
		//Once Users Account Has Been Approved By An Employee, check database and see if is_approved is Actually true for that user
		boolean isApproved = false;
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT * FROM all_users_table WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, "Bob");
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			isApproved = rs.getBoolean("isApproved");
			
			//Check Users access after DB query
			assertTrue(isApproved);
			
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An Error Has Occured");
		}
		
		
		//Given The Incorrect User name, this method should return False
		assertFalse(serv.approveUserAccess("notUser"));
		
		
	}
	//Test #6
	@Test
	public void testValidWithdrawal() {
		//Method Should Return True If The User Is Withdrawing Less Than The Current Account Balance
		assertTrue(serv.isValidWithdrawal(50,100));
		
		//Method Should Return True If The User Is Withdrawing The Exact Balance
		assertTrue(serv.isValidWithdrawal(100,100));
		
		//Method Should Return True If The User Is Withdrawing 0 amount
		//Note This operation would not normally be allowed since the menu will not permit a value of 0 but lets just test anyways
		assertTrue(serv.isValidWithdrawal(0,100));
		assertTrue(serv.isValidWithdrawal(0,0));
		
		//Method Should Return True If The User Is Withdrawing a negative amount
		//Note This operation would not normally be allowed since the menu will not permit negative numbers but lets just test anyways
		assertTrue(serv.isValidWithdrawal(-300,100));
		
		//Method Should Return False If The Amount Being Withdrawn 
		assertFalse(serv.isValidWithdrawal(300,100));
		assertFalse(serv.isValidWithdrawal(300,0));
		
	}
	//Test #7
	@Test
	public void testIsValidAmount() {
		//Method Should Return True If The User Has Input An Amount Greater Than 0. 
		assertTrue(serv.isValidAmount(1));
		assertTrue(serv.isValidAmount(1000));
		assertTrue(serv.isValidAmount(17687578));
		
		//Method Should Return False If The User Has Input An Amount Less Than Or Equal To 0. 
		assertFalse(serv.isValidAmount(0));
		assertFalse(serv.isValidAmount(-1));
		assertFalse(serv.isValidAmount(-100));
		assertFalse(serv.isValidAmount(-10786690));
	}
	//Test #8
	@Test
	public void testPendingCount() {
		
		int count = 0;
		//Change Name To Test Other Accounts
		String name = "Bob";
		
		count = db.selectPendingCount(name);
		
		if (count > 0) {
			//Method Should Return True If The User Has A Pending Count Greater Than 0.
			assertTrue(serv.pendingCount(name));
		}else {
			//Method Should Return False If The User Has A Pending Count Greater Than 0.
			assertFalse(serv.pendingCount(name));
			//Method Should Return False If The User Does Not Exist.
			assertFalse(serv.pendingCount(name));
		}
	}
	//Test #9
	public void testGetAcctByName() {
		//Testing If The Method Will Return This User With The Same Name
		String name = "Bob";
		//Testing If This Method Will Return The Correct User
		User testUser = serv.getAcctByName(name);
		//Get Name Returned By This Method
		String testName = testUser.getUserName();
		//Compare The Name That Is Returned By The Method With The Name That We Gave In The Test
		assertTrue((name == testName));
		
		//Test Should Return False If The Names Do Not Match
		assertFalse(!(name == testName));
	}
	//Test #10
	@Test
	public void testGetUnapprovedCount() {
		
		int count = 0;
		
		count = serv.getUnapprovedCount();
		
		boolean goodCount = false;
		
		if (count > 0) {
			//Test Should Return True If Pending Count Is Greater Than 0.
			assertTrue(goodCount);
		}else {
			//Test Should Return False If Pending Count Is 0.
			assertFalse(goodCount);

		}
	}
}
