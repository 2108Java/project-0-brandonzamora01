package com.revature.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Transaction;
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
				
			String sql = "INSERT INTO all_users_table VALUES (?,?,?,?,?,?,?,?)";
				
			PreparedStatement ps = connection.prepareStatement(sql);
				
			ps.setString(1, newUser.getUserName());
			ps.setString(2, newUser.getPassword());
			ps.setInt(3, newUser.getCheckingBalance());
			ps.setInt(4, newUser.getSavingBalance());
			ps.setBoolean(5, newUser.getIsApproved());
			ps.setBoolean(6, newUser.getIsEmployee());
			ps.setBoolean(7, newUser.isCreateChecking());
			ps.setBoolean(8, newUser.isCreateSaving());
				
			ps.execute();
				
			success = true;
			connection.close();	
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
			connection.close();
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
			connection.close();
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
			acct.setCreateChecking(rs.getBoolean("create_checking"));	
			acct.setCreateSaving(rs.getBoolean("create_saving"));
											
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return acct;
	}

	@Override
	public boolean updateAccountBalance(Transaction arg) {
		// TODO Auto-generated method stub
		boolean success = false;
		String user = arg.getRootName();
		String acctType = arg.getRootType();
		int amount = arg.getEndBal();
		//1. Connect to database!
		if(acctType.equals("Checkings")) {
			try(Connection connection = DriverManager.getConnection(url,username,password)){
			
				//2. Write a SQL statement String
			
				String sql = "UPDATE all_users_table SET checkingbalance = ? WHERE username = ?";
			
				PreparedStatement ps = connection.prepareStatement(sql);
			
				ps.setInt(1, amount);
				ps.setString(2, user);
				ps.execute();
			
				success = true;
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}else if(acctType.equals("Savings")) {
				try(Connection connection = DriverManager.getConnection(url,username,password)){
				
					//2. Write a SQL statement String
				
					String sql = "UPDATE all_users_table SET savingbalance = ? WHERE username = ?";
				
					PreparedStatement ps = connection.prepareStatement(sql);
				
					ps.setInt(1, amount);
					ps.setString(2, user);
					ps.execute();
				
					success = true;
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
		}else {
			System.out.println("Invalid Account Type. Deposit Failed");	
		}
		return success;
	}

	@Override
	public boolean updateTransferStatus(int transId) {
		// TODO Auto-generated method stub
			boolean success = false;
			//1. Connect to database!
			try(Connection connection = DriverManager.getConnection(url,username,password)){
					//2. Write a SQL statement String
				String sql = "UPDATE all_transactions_table SET approve_status = ? WHERE trans_id = ?";
					
				PreparedStatement ps = connection.prepareStatement(sql);
					
				ps.setBoolean(1, true);
				ps.setInt(2, transId);
				ps.execute();
				
				success = true;
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		return success;
	}
	
	public boolean insertTransaction(Transaction arg){
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){			
			
			String sql = "INSERT INTO all_transactions_table (username,acct_type,trans_amount,trans_type,approve_status,acct_start_balance,acct_end_balance,destination_name,destination_type) VALUES (?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			
			ps.setString(1, arg.getRootName());
			ps.setString(2, arg.getRootType());
			ps.setInt(3, arg.getTransAmount());
			ps.setString(4, arg.getTransType());
			ps.setBoolean(5, arg.isSentStatus());
			ps.setInt(6, arg.getStartBal());
			ps.setInt(7, arg.getEndBal());
			ps.setString(8, arg.getDestinationName());
			ps.setString(9, arg.getDestinationType());
			
			ps.execute();
			
			success = true;
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Transaction selectPendingTranfer(String recName) {
		// TODO Auto-generated method stub
		Transaction pendingTransfer = new Transaction();
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			
			String sql = "SELECT * FROM all_transactions_table WHERE destination_name = ? and approve_status = ? LIMIT 1";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, recName);
			ps.setBoolean(2, false);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			String senderName = rs.getString("username");
			int transId = rs.getInt("trans_id");
			String destType = rs.getString("destination_type");
			int transAmount = rs.getInt("trans_amount");
			
			pendingTransfer.setRootName(senderName);
			pendingTransfer.setTransId(transId);
			pendingTransfer.setDestinationName(recName);
			pendingTransfer.setDestinationType(destType);
			pendingTransfer.setTransAmount(transAmount);
			pendingTransfer.setTransType("Transfer In");
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An Error Has Occured");
		}
		return pendingTransfer;
	}
	
	public int selectPendingCount(String recName) {
		int pendingCount = 0;
		
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			String sql = "SELECT COUNT(*) FROM all_transactions_table WHERE destination_name = ? and approve_status = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, recName);
			ps.setBoolean(2, false);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			pendingCount = rs.getInt("count");
			System.out.println("\nThere Are "+pendingCount+" Pending Transfers");
			
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An Error Has Occured");
		}
		return pendingCount;
	}
	
	public Transaction[] selectAllTransactions(int numbTrans) {
		
		Transaction[] allTransactions = new Transaction[numbTrans];
		
		try(Connection connection = DriverManager.getConnection(url, username, password)){
		
			String sql = "SELECT * FROM all_transactions_table ORDER BY trans_id;";
		
			PreparedStatement ps = connection.prepareStatement(sql);
		
			ResultSet rs = ps.executeQuery();
		
			int i = 0;
			while(rs.next()) {
			
				allTransactions[i] = new Transaction(rs.getInt("trans_id"),rs.getString("username"), 
						rs.getString("acct_type"),rs.getInt("trans_amount"),rs.getString("trans_type"),rs.getBoolean("approve_status"),
						rs.getInt("acct_start_balance"), rs.getInt("acct_end_balance"),rs.getString("destination_name"),rs.getString("destination_type"));
				i++;
			
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allTransactions;
	}
	
	public int selectNumTransactions(){
		int numberTrans = 0;
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			
			String sql = "SELECT COUNT(*) FROM all_transactions_table";
		
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			numberTrans = rs.getInt("count");
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberTrans;
	}

	@Override
	public int selectUnapprovedNum() {
		// TODO Auto-generated method stub
		int numberUnapproved = 0;
		
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			
			String sql = "SELECT COUNT(*) FROM all_users_table WHERE isapproved = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setBoolean(1, false);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			numberUnapproved = rs.getInt("count");
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberUnapproved;
	}
		

	@Override
	public String[] selectUnapprovedCustomer(int num) {
		// TODO Auto-generated method stub
		String[] listUnapproved = new String[num];
		
		try(Connection connection = DriverManager.getConnection(url, username, password)){
			
			String sql = "SELECT * FROM all_users_table WHERE isapproved = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setBoolean(1, false);
			
			ResultSet rs = ps.executeQuery();
			int i = 0;
			while(rs.next()) {
				listUnapproved[i] = rs.getString("username");
				i++;
			}
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listUnapproved;
	}

	@Override
	public boolean updateUserAccess(String name) {
		// TODO Auto-generated method stub
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
				//2. Write a SQL statement String
			String sql = "UPDATE all_users_table SET isapproved = ? WHERE username = ?";
				
			PreparedStatement ps = connection.prepareStatement(sql);
				
			ps.setBoolean(1, true);
			ps.setString(2, name);
			ps.execute();
			
			success = true;
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	return success;
	}

	@Override
	public boolean updateCheckingStatus(String name) {
		// TODO Auto-generated method stub
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
				//2. Write a SQL statement String
			String sql = "UPDATE all_users_table SET create_checking = ? WHERE username = ?";
				
			PreparedStatement ps = connection.prepareStatement(sql);
				
			ps.setBoolean(1, true);
			ps.setString(2, name);
			ps.execute();
			
			success = true;
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return success;
	}

	@Override
	public boolean updateSavingStatus(String name) {
		// TODO Auto-generated method stub
		boolean success = false;
		//1. Connect to database!
		try(Connection connection = DriverManager.getConnection(url,username,password)){
				//2. Write a SQL statement String
			String sql = "UPDATE all_users_table SET create_saving = ? WHERE username = ?";
				
			PreparedStatement ps = connection.prepareStatement(sql);
				
			ps.setBoolean(1, true);
			ps.setString(2, name);
			ps.execute();
			
			success = true;
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return success;
	}
}
