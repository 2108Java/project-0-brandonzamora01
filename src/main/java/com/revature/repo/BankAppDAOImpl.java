package com.revature.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.User;

public class BankAppDAOImpl implements BankAppDAO {
	
	private String dbLocation = "localhost";
	private String username = "postgres";
	private String password = "Training123";
	private String url = "jdbc:postgresql://" + dbLocation + "/postgres";
	
	@Override
	public boolean registerAcct(User newUser) {
			
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
				
			//2. Write a SQL statement String
				
			String sql = "INSERT INTO all_users_table VALUES (?,?,?,?,?,?)";
				
			PreparedStatement ps = connection.prepareStatement(sql);
				
			ps.setString(1, newUser.getUserName());
			ps.setString(2, newUser.getPassword());
			ps.setInt(3, newUser.getCheckingBalance());
			ps.setInt(4, newUser.getSavingBalance());
			ps.setBoolean(5, newUser.getIsApproved());
			ps.setBoolean(6, newUser.getIsEmployee());
				
			ps.execute();
				
			success = true;
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("User has been added to the database");	
		return success;// TODO Auto-generated method stub
	}

	@Override
	public boolean userLogin(String name, String pw) {
		// TODO Auto-generated method stub
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT * FROM all_users_table WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			String check = rs.getString("password");
			
			//System.out.println(check);
			if (pw.equals(check)) {
				success = true;
				System.out.println("\nUser Login Successful!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return success;
	}

	@Override
	public boolean employeeLogin(String name, String pw) {
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT * FROM all_users_table WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			String check = rs.getString("password");
			boolean verifyEmployee = rs.getBoolean("isemployed");
			
			//System.out.println(check);
			if (pw.equals(check) && verifyEmployee) {
				success = true;
				System.out.println("\nEmployee Login Successful!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	@Override
	public User selectAcctByName(String name) {
		//boolean success = false;
		User acct = new User();
		
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT * FROM all_users_table WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			acct.setUserName(rs.getString("username"));
			acct.setPassWord(rs.getString("password"));
			acct.setCheckingBalance(rs.getInt("checkingbalance"));
			acct.setSavingBalance(rs.getInt("savingbalance"));
			acct.setIsApproved(rs.getBoolean("isapproved"));	
			acct.setIsEmployee(rs.getBoolean("isemployed"));
											
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return acct;
	}

	@Override
	public boolean userWithdraw() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userDeposit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean customerPostTransfer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean customerAcceptTransfer() {
		// TODO Auto-generated method stub
		return false;
	}

}
