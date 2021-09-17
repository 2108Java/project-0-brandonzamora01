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
		System.out.println("7) Logout\n");
	}
	
	private void employeeMenu() {
		System.out.println("\n***EMPLOYEE MENU***");
		System.out.println("Logged in as "+ currentUser.getUserName());
		System.out.println("\n1) View Customer Account Balance");
		System.out.println("2) Approve Account Registration");
		System.out.println("3) View Transaction Logs");
		System.out.println("4) Navigate to Customer Menu");
		//System.out.println("5) View All Account Balances");
		//System.out.println("6) Create New Employee");
		System.out.println("5) Logout\n");
	}
	
	private void defaultMenu() {
		System.out.println("WELCOME");
		System.out.println("1) Login as Customer");
		System.out.println("2) Login as Employee");
		System.out.println("3) Register New Customer Account");
		System.out.println("4) Apply for a Joint Account");
		System.out.println("5) Exit\n");
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
					currentUser = service.getAcctByName(name);
					customerDisplay();
				}else {
					System.out.println("Login Has Failed");
				}
				break;
			case "2":
				System.out.println("\nLogin as Employee");
				System.out.print("\nEnter Username: ");
				String employeeName = scanner.nextLine();
				System.out.print("Enter Password: ");
				String employeePw = scanner.nextLine();
				
				if (service.isAccount(employeeName, employeePw)) {
					currentUser = service.getAcctByName(employeeName);
					if(currentUser.getIsEmployee()) {
						employeeDisplay();
					}else {
						System.out.println("Current User Is Not An Authorized Employee");
					}
					
				}else {
					System.out.println("Login Has Failed");
				}
				break;
			case "3":
				System.out.println("\nRegister New Customer Account\n");
				
				boolean match = false;
				
				for (int i = 0; i <= 3; i++) {
					System.out.print("Enter Username: ");
					String newName = scanner.nextLine();
					System.out.print("Enter Password: ");
					String newPw = scanner.nextLine();
					System.out.print("Confirm Password: ");
					String confirmPw = scanner.nextLine();
					match = newPw.equals(confirmPw);
				
					if (match) {
						currentUser.setUserName(newName);
						currentUser.setPassWord(newPw);
						service.createAcct(currentUser);
						i = 3;
					}else {
						System.out.println("\nPassword Does Not Match. Try Again");
					}
				}	
				System.out.println("\nAccount Creation Successful. Please Login to Access Features");
				running = false;
				break;
			case "5":
				System.out.println("Thank you for banking with BZ!");
				running = false;
				break;
			}
				
		}
	}
	
	public void employeeDisplay() {
		
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while (running) {
			//call default menu to display
			employeeMenu();
			
			String result = scanner.nextLine();
			
			switch(result){
			case "1":
				//User viewUser = new User();
				
				System.out.println("\nView Customer Account Balance");
				System.out.println("\nEnter Customer Name To View Accont Balances:");
				String findUser = scanner.nextLine();
				
				User viewUser = service.getAcctByName(findUser);
				System.out.println("\nCheckings Balance: " + viewUser.getCheckingBalance());
				System.out.println("Savings Balance: " + viewUser.getSavingBalance());
				
				
				break;
			case "2":
				System.out.println("\nApprove Account Registration");
				break;
			case "3":
				System.out.println("\nView Transaction Logs");
				break;
			case "4":
				System.out.println("\nNavigating to Customer Menu");
				customerDisplay();
				running = false;
				break;
			case "5":
				System.out.println("You have been logged out\nReturning To Main Menu\n");
				running = false;
				break;
			default:
				System.out.println("That's not a valid input!");
				System.out.println("Try again!");
			}
		}
	}
	
	@Override
	public void customerDisplay(){
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while(running){
			
			customerMenu();
			
			String result = scanner.nextLine();
			
			switch(result){
			case "1":
					boolean viewBal = true;
					while(viewBal) {
						System.out.println("\nSelect An Account To View:");
						System.out.println("1) Checkings");
						System.out.println("2) Savings");
						String acctType = scanner.nextLine();
						if(acctType.equals("1")) {
							System.out.println("\nCheckings Balance: " + currentUser.getCheckingBalance());
							System.out.println("\nWould You Like To View Another Account? (y/n)");
							String viewAnother = scanner.nextLine();
								if (viewAnother.equals("n")) {
									//only exit if user input == n. Otherwise run case 1 again. 
									viewBal = false;
								}
						} else if (acctType.equals("2")){
							System.out.println("Savings Balance: " + currentUser.getSavingBalance());
							System.out.println("\nWould You Like To View Another Account? (y/n)");
							String viewAnother = scanner.nextLine();
								if (viewAnother.equals("n")) {
									//only exit if user input == n. Otherwise run case 1 again. 
									viewBal = false;
								}
						} else {
							System.out.println("Invalid Input. Please Enter 1 or 2");
						}
					}

				//}catch(BusinessException e) {
					//System.out.println("Our database is down!");
//					e.printStackTrace();
				break;
			case "7":
				System.out.println("You have been logged out\nReturning To Main Menu\n");
				running = false;
				break;
			default:
				System.out.println("That's not a valid input!");
				System.out.println("Try again!");
			}
		}
	}
}
