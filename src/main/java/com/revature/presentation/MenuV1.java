package com.revature.presentation;

import java.util.Scanner;

//import com.revature.exceptions.BusinessException;
import com.revature.service.ServiceBankApp;
import com.revature.models.User;

public class MenuV1 implements Menu {
	
	ServiceBankApp service;
	
	User currentUser = new User();
	
	public MenuV1(ServiceBankApp service) {
		this.service = service;
	}
	
	private void prettyDisplayOfArray(User[] array) {
		for(int i = 0; i< array.length; i++) {
					
			if(array[i] != null) {
				System.out.println(array[i].getUserName());
				System.out.println(array[i].getCheckingBalance());
				System.out.println(array[i].getSavingBalance());
				System.out.println(array[i].getIsEmployee());
				System.out.println("");
			}
					
		}
	}
	
	private void customerMenu() {
		System.out.println("\n***CUSTOMER MENU***");
		System.out.println("Logged in as " + currentUser.getUserName());
		System.out.println("1) View Account Balance");
		System.out.println("2) Make Withdrawal");
		System.out.println("3) Make Deposit");
		System.out.println("4) Post Money Transfer");
		System.out.println("5) Accept Money Transfer");
		System.out.println("6) Apply For A New Account");
		System.out.println("7) Logout");
	}
	
	private void employeeMenu() {
		System.out.println("***EMPLOYEE MENU***");
		System.out.println("Logged in as "+ currentUser.getUserName());
		System.out.println("\n1) View Customer Account Balance");
		System.out.println("2) Approve Account Registration");
		System.out.println("3) View Transaction Logs");
		System.out.println("4) Navigate to Customer Menu");
		//System.out.println("5) View All Account Balances");
		//System.out.println("6) Create New Employee");
		System.out.println("5) Logout");
	}
	
	private void defaultMenu() {
		System.out.println("WELCOME");
		System.out.println("1) Login as Customer");
		System.out.println("2) Login as Employee");
		System.out.println("3) Register New Customer Account");
		System.out.println("4) Apply for a Joint Account");
		System.out.println("5) Exit");
	}
	
	
	public void defaultDisplay() {
		System.out.println("");
		
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while (running) {
			//call default menu to display
			defaultMenu();
			String result = scanner.nextLine();
			
			switch(result){
			case "1":
				System.out.println("\nLogin as Customer");
				System.out.print("\nEnter Username: ");
				String name = scanner.nextLine();
				System.out.print("Enter Password: ");
				String pw = scanner.nextLine();
				
				if (service.isAccount(name, pw)) {
					currentUser = service.getAcctBal(name);
					display();
				}else {
					System.out.println("Login Has Failed");
				}
				break;
				
			case "5":
				System.out.println("Thank you for banking with BZ!");
				running = false;
				break;
			}
				
		}
	}
	
	public void employeeDisplay() {
		System.out.println("");
	}
	
	@Override
	public void display(){
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while(running){
			
			customerMenu();
			
			String result = scanner.nextLine();
			
			switch(result){
			case "1":
					//ToDoItem[] items = service.getAllToDos();
					System.out.println("Your balances are:");
					//prettyDisplayOfArray(items);
					System.out.println("\nCheckings Balance: " + currentUser.getCheckingBalance());
					System.out.println("Savings Balance: " + currentUser.getSavingBalance());

				//}catch(BusinessException e) {
					//System.out.println("Our database is down!");
//					e.printStackTrace();
				break;
			case "7":
				System.out.println("Thank you for banking with BZ!");
				running = false;
				break;
			default:
				System.out.println("That's not a valid input!");
				System.out.println("Try again!");
			}
		}
	}
}
