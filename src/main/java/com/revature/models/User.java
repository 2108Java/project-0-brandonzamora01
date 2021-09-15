package com.revature.models;

public class User {
	
	private int checkingBalance;
	private int savingBalance;
	private String userName;
	private String passWord;
	private boolean isApproved;
	private boolean isEmployee;
	
	
	public User(String userName, String passWord, int checkingBalance, boolean isApproved, boolean isEmployee) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.checkingBalance = checkingBalance;
		this.isApproved = isApproved;
		this.isEmployee = isEmployee;
	}
	
	public User() {
		super();
	}
	
	// get methods
	public int getCheckingBalance() {
		return checkingBalance;
	}
	
	public int getSavingBalance() {
		return savingBalance;
	}
	
	public boolean getIsApproved() {
		return isApproved;
	}
	
	public boolean getIsEmployee() {
		return isEmployee;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return passWord;
	}
	
	// set methods
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	public void setIsEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	public void setCheckingBalance(int checkingBalance) {
		this.checkingBalance = checkingBalance;
	}
	
	public void setSavingBalance(int savingBalance) {
		this.savingBalance = savingBalance;
	}
	
}
