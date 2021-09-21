package com.revature.presentation;

import java.util.Formatter;
import java.util.Scanner;

//import com.revature.exceptions.BusinessException;
import com.revature.service.ServiceBankApp;
import com.revature.models.Transaction;
import com.revature.models.User;

public class MenuV1 implements Menu {
	
	ServiceBankApp service;
	
	User currentUser = new User();
	
	public MenuV1(ServiceBankApp service) {
		this.service = service;
	}
	
	private void prettyDisplayOfTransactions(Transaction[] array) {
		Formatter fmt = new Formatter();  
		fmt.format("%10s %10s %15s %10s %20s %10s\n", "Trans ID", "Username", "Account Type", "Amount", "Transaction Type", "Balance"); 
		for(int i = 0; i< array.length; i++) {	
			if(array[i] != null) {
				fmt.format("%10s %10s %15s %10s %20s %10s\n", 
				array[i].getTransId(),array[i].getRootName(),
				array[i].getRootType(),array[i].getTransAmount(),
				array[i].getTransType(),array[i].getEndBal());
			}		
		}
		System.out.println(fmt);  
	}
	
	private void customerMenu() {
		System.out.println("\n***CUSTOMER MENU***\n");
		System.out.println("Logged in as " + currentUser.getUserName());
		System.out.println("\n1) View Account Balance");
		System.out.println("2) Make Withdrawal");
		System.out.println("3) Make Deposit");
		System.out.println("4) Post Money Transfer");
		System.out.println("5) Accept Money Transfer");
		System.out.println("6) Apply For A Checking Account");
		System.out.println("7) Apply For A Saving Account");
		System.out.println("8) Logout\n");
	}
	
	private void employeeMenu() {
		System.out.println("\n***EMPLOYEE MENU***\n");
		System.out.println("Logged in as "+ currentUser.getUserName());
		System.out.println("\n1) View Customer Account Balance");
		System.out.println("2) Approve Account Registration");
		System.out.println("3) View Transaction Logs");
		System.out.println("4) Navigate to Customer Menu");
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
				
				if (service.isUserAccount(name, pw)) {
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
				
				if (service.isEmployeeAccount(employeeName, employeePw)) {
					currentUser = service.getAcctByName(employeeName);
					employeeDisplay();
					}else {
						System.out.println("Current User Is Not An Authorized Employee");
					}
				break;
			case "3":
				
				System.out.println("\nRegister New Customer Account\n");
				System.out.println("1) Register For Checking Account ");
				System.out.println("2) Register For Saving Account");
				System.out.println("3) Register For Checking And Saving Account");
				System.out.println("4) Exit");
				
				String option = scanner.nextLine();
				boolean validOption = false;
				boolean match = false;
				
				switch(option){
				case "1":
					currentUser.setCreateChecking(true);
					currentUser.setCreateSaving(false);
					System.out.println("Registering For Checkings Account");
					validOption = true;
					break;
				case "2":
					currentUser.setCreateSaving(true);
					currentUser.setCreateChecking(false);
					System.out.println("Registering For Savings Account");
					validOption = true;
					break;
				case "3":
					currentUser.setCreateChecking(true);
					currentUser.setCreateSaving(true);
					System.out.println("Registering For Checkings And Savings Account");
					validOption = true;
					break;
				case "4":
					System.out.println("Returning To Main Menu");
					break;
				default:
					System.out.println("That's not a valid input!\nTry again!\n");
					break;
				}	
				if (validOption) {
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
				}
					break;
			case "4":
				System.out.println("\nPost Money Transfer\n");
				System.out.println("Select Account To Transfer From");
				System.out.println("1) Checkings");
				System.out.println("2) Savings");
				String selectAcct = scanner.nextLine();
				if (selectAcct.equals("1")){
					String sendType = new String("checking");
					System.out.println("\nTransfering From Your Checking Account");
					System.out.println("Available Balance: " + currentUser.getCheckingBalance());
					System.out.println("Please Enter The Username The Will Recieve The Transfer:\n");
					String sendTo = scanner.nextLine();
					System.out.println("Please Enter The Account Type Of The Reciever (checkings or savings):\n");
					String recType = scanner.nextLine();
					System.out.println("Please Enter The Amount Being Transfered:\n");
					int sendAmount = Integer.parseInt(scanner.nextLine());
					System.out.println("\nSummary: Sending $"+sendAmount+"To "+sendTo+"'s"+recType+" Account.");
					System.out.println("Do You Wish To Procede? (y/n)");
					Transaction userTransfer = new Transaction (currentUser.getUserName(), sendType, sendTo, recType, sendAmount);
				}
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
				int approveNum = service.getUnapprovedCount();
				if (approveNum > 0) {
					boolean updateAccess = true;
					while(updateAccess) {
					System.out.println("\nGathering List...");
					String[] approveList = service.getUnapprovedList(approveNum);
					
					for(int i = 0; i< approveList.length; i++) {	
						if(approveList[i] != null) {
							int showNum = i+1;
							System.out.println(showNum +") "+approveList[i]);
						}
					}	
					
					System.out.println("\nEnter A User To Approve Online Access.");
					String approveUser = scanner.nextLine();
					
					if(service.approveUserAccess(approveUser)){
						System.out.println("\nUser Access Has Been Approved.");
						System.out.println("Would You Like To Approve Another User? (y/n)");
						String approveAnother = scanner.nextLine();
						updateAccess = approveAnother.equals("y");
					}else {
						System.out.println("\nAn Error Occured During Approval.");
					}
					}
				} else {
					System.out.println("There Are No Accounts To Approve.");
				}
				break;
			case "3":
				System.out.println("\nView Transaction Logs");
				System.out.println("\nRetrieving Transactions Log...\n");
				int numberOfTransactions = service.getTransactionCount();
				if (numberOfTransactions > 0) {
					prettyDisplayOfTransactions(service.getTransactionLog(numberOfTransactions));
				} else {
					System.out.println("Transaction Log Is Empty.");
				}
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
				System.out.println("That's not a valid input!\nTry again!\n");
				break;
			}
		}
	}
	
	@Override
	public void customerDisplay(){
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		boolean featureAccess = currentUser.getIsApproved();		// Has account been approved for access by bank employee
		boolean checkingAccess = currentUser.isCreateChecking();	// Does User have a cheking account?
		boolean savingAccess = currentUser.isCreateSaving();		// Does User have a savings account?
		
		boolean running = featureAccess;
		if (!running) {
			System.out.println("\nAccount Features Have Not Been Enabled Yet.\nPlease Contact Bank Employee To Approve User Account.\n");
		}
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
					System.out.println("3) Exit");
					String acctType = scanner.nextLine();
					if(acctType.equals("1")) {
						if(checkingAccess) {
							System.out.println("\nCheckings Balance: " + currentUser.getCheckingBalance());
							System.out.println("\nWould You Like To View Another Account? (y/n)");
							String viewAnother = scanner.nextLine();
							if (viewAnother.equals("n")) {
								//only exit if user input == n. Otherwise run case 1 again. 
								viewBal = false;
							}
						} else {
							System.out.println("Checkings Account Has Not Been Setup Yet.");
						}
					} else if (acctType.equals("2")){
						if(savingAccess) {
							System.out.println("Savings Balance: " + currentUser.getSavingBalance());
							System.out.println("\nWould You Like To View Another Account? (y/n)");
							String viewAnother = scanner.nextLine();
							if (viewAnother.equals("n")) {
								//only exit if user input == n. Otherwise run case 1 again. 
								viewBal = false;
							}
						} else {
							System.out.println("Savings Account Has Not Been Setup Yet.");
						}
					} else if (acctType.equals("3")){
						System.out.println("Returning To Previous Menu.");
						viewBal = false;
					}else{
						System.out.println("Invalid Input. Please Enter 1-3");
					}
				}
				break;
			case "2":
				boolean makeWithdrawal = true;
				while(makeWithdrawal) {
					String transType = "Withdrawal";
					String withdrawalAccount = "";
					int amount = 0;
					boolean tryTrans = false;
					System.out.println("\nSelect An Account To Withdraw From:");
					System.out.println("1) Checkings");
					System.out.println("2) Savings");
					System.out.println("3) Exit");
					String acctType = scanner.nextLine();
					if(acctType.equals("1")) {
						withdrawalAccount = new String("Checkings");
						System.out.println("\nAvailable Checkings Balance: " + currentUser.getCheckingBalance());
						System.out.println("\nPlease Enter Withdraw Amount: ");
						amount = Integer.parseInt(scanner.nextLine());
						tryTrans = true;
				
					} else if (acctType.equals("2")){
						withdrawalAccount = new String("Savings");
						System.out.println("\nAvailable Savings Balance: " + currentUser.getSavingBalance());
						System.out.println("\nPlease Enter Withdraw Amount: ");
						amount = Integer.parseInt(scanner.nextLine());
						tryTrans = true;
					} else if(acctType.equals("3")){
						System.out.println("Returning To Previous Menu");
						makeWithdrawal = false;
					} else {
						System.out.println("Invalid Input. Please Enter 1-3");
					}
					
					if (tryTrans && makeWithdrawal) {
						Transaction withdrawal = new Transaction(currentUser.getUserName(), withdrawalAccount, transType, amount);
						makeWithdrawal = !(service.userWithdrawal(withdrawal));
					}
				}
				break;
			case "3":
				boolean makeDeposit = true;
				boolean tryTrans = false;
				
				while(makeDeposit) {
					String transType = "Deposit";
					String depositAcct = "";
					int amount = 0;
					System.out.println("\nSelect An Account To Deposit To:");
					System.out.println("1) Checkings");
					System.out.println("2) Savings");
					System.out.println("3) Exit");
					String acctType = scanner.nextLine();
					if(acctType.equals("1")) {
						depositAcct = new String("Checkings");
						System.out.println("\nCurrent Checkings Balance: " + currentUser.getCheckingBalance());
						System.out.println("\nPlease Enter Deposit Amount: ");
						amount = Integer.parseInt(scanner.nextLine());
						tryTrans = true;
					} else if (acctType.equals("2")){
						depositAcct = new String("Savings");
						System.out.println("\nCurrent Savings Balance: " + currentUser.getSavingBalance());
						System.out.println("\nPlease Enter Deposit Amount: ");
						amount = Integer.parseInt(scanner.nextLine());
						tryTrans = true;
					} else if(acctType.equals("3")){
						System.out.println("Returning To Previous Menu");
						tryTrans = true;
					}else {
						System.out.println("Invalid Input. Please Enter 1-3");
					}
					if (tryTrans && (acctType.equals("1") || acctType.equals("2"))) {
						Transaction deposit = new Transaction(currentUser.getUserName(), depositAcct, transType, amount);
						tryTrans = service.userDeposit(deposit);
						if (tryTrans){
							System.out.println("Depositing "+amount+" To Your "+depositAcct+" Account.");
						}
					}
					makeDeposit = !(tryTrans);
				}
				break;
			case "4":
				boolean postTransfer = false;
				while(!postTransfer) {
					System.out.println("\nPost Money Transfer");
					System.out.println("\nSelect Account To Transfer From");
					System.out.println("1) Checkings");
					System.out.println("2) Savings");
					System.out.println("3) Exit");
					String selectAcct = scanner.nextLine();
					if (selectAcct.equals("1")){
						String sendType = new String("Checkings");
						System.out.println("\nTransfering From Your Checking Account");
						System.out.println("Available Balance: " + currentUser.getCheckingBalance());
						System.out.println("Please Enter The Username That Will Recieve The Transfer:\n");
						String sendTo = scanner.nextLine();
						System.out.println("Please Enter The Account Type Of The Reciever (Checkings or Savings):\n");
						System.out.println("1) Checkings");
						System.out.println("2) Savings");
						String recType = scanner.nextLine();
						if (recType.equals("1")) {
							recType = "Checkings";
						}else if (recType.equals("2")) {
							recType = "Savings";
						}else {
							System.out.println("An Error Occured. Please Enter 1 or 2. Try Again");
						}
						System.out.println("Please Enter The Amount Being Transfered:\n");
						int sendAmount = Integer.parseInt(scanner.nextLine());
						System.out.println("\nSummary: Sending $"+sendAmount+" To "+sendTo+"'s "+recType+" Account.");
						System.out.println("Do You Wish To Procede? (y/n)");
						Transaction userTransfer = new Transaction (currentUser.getUserName(), sendType, sendTo, recType, sendAmount);
						postTransfer = service.postTransfer(userTransfer);
					} else if (selectAcct.equals("2")){
						String sendType = new String("Savings");
						System.out.println("\nTransfering From Your Savings Account");
						System.out.println("Available Balance: " + currentUser.getSavingBalance());
						System.out.println("Please Enter The Username That Will Recieve The Transfer:\n");
						String sendTo = scanner.nextLine();
						System.out.println("Please Enter The Account Type Of The Reciever (Checkings or Savings):\n");
						String recType = scanner.nextLine();
						System.out.println("Please Enter The Amount Being Transfered:\n");
						int sendAmount = Integer.parseInt(scanner.nextLine());
						System.out.println("\nSummary: Sending $"+sendAmount+" To "+sendTo+"'s "+recType+" Account.");
						System.out.println("Do You Wish To Procede? (y/n)");
						Transaction userTransfer = new Transaction (currentUser.getUserName(), sendType, sendTo, recType, sendAmount);
						postTransfer = service.postTransfer(userTransfer);
						//Note: Clean up this whole part later. Could be simpler but for now it kind of works.	
					} else if (selectAcct.equals("3")){
						System.out.println("\nExiting The Transfer Menu");
						postTransfer = true;
					} else {
						System.out.println("Invalid Input. Please Enter 1-3");
					}
					
				}
				break;
			case "5":
				System.out.println("\nAccept Money Transfer");
				boolean approveTransfer = true;
				boolean transferSuccess = false;
				while(approveTransfer) {
					System.out.println("\nSearching For Available Transfers...");
					if(service.pendingCount(currentUser.getUserName())){
						
						service.displayTransferInfo(currentUser.getUserName());
						System.out.println("\nWill You Approve This Transfer? (y/n)");
						String approving = scanner.nextLine();
						
						if(approving.equals("y")) {
							transferSuccess = service.acceptTransfer(currentUser.getUserName());
						}else if(approving.equals("n")) {
							transferSuccess = true;
							System.out.println("Transfer Has Been Rejected. Funds Will Be Returned To Sender.");
						}else {
							System.out.println("An Error Occured During Approval. Please Try Again.");
							approveTransfer = false;
						}
					}else {
						approveTransfer = false;
						//System.out.println("\nReturning To Customer Menu");
					}
					
					if(transferSuccess){
						System.out.println("Transfer Has Been Approved");
						System.out.println("\nSearch For Another Transfer? (y/n)");
						String approveAnother = scanner.nextLine();
						approveTransfer = approveAnother.equals("y");
					}
				}
				break;
			case "6":
				System.out.println("\nApply For A Checking Account\n");
				if (!checkingAccess) {
					System.out.println("Would You Like To Create A Checkings Account? (y/n)");
					String applyChecking = scanner.nextLine();
					if (applyChecking.equals("y")) {
						boolean createChecking = service.createCheckingAcct(currentUser.getUserName());
						System.out.println("\nAccount Has Been Initialized");
						System.out.println("Would You Like Make An Initial Deposit? (y/n)");
						String makeCheckingDep = scanner.nextLine();
						if (makeCheckingDep.equals("y")) {
							System.out.println("Enter Amount That You Would You Like To Deposit?");
							int intDepAmount =  Integer.parseInt(scanner.nextLine());
							boolean tryCheckingDep = service.isValidAmount(intDepAmount);
							if (createChecking && tryCheckingDep) {
								Transaction newChecking = new Transaction(currentUser.getUserName(), "Checkings", "Deposit", intDepAmount);
								tryCheckingDep = service.userDeposit(newChecking);
								if (tryCheckingDep){
									System.out.println("Depositing "+intDepAmount+" To Your Checkings Account.");
								}
							}
						}else {
							System.out.println("You Have Decline To Make An Initial Deposit. Current Checking Balance is $0");
						}
					}else {
						System.out.println("Checking Account Will Not Be Activated.\nReturning To Previous Menu.");
					}
				}else {
					System.out.println("Account Already Exists.\nReturning To Previous Menu.\n");
				}
				break;
			case "7":
				System.out.println("\nApply For A Savings Account\n");
				if (!savingAccess) {
					System.out.println("Would You Like To Create A Checkings Account? (y/n)");
					String applySaving = scanner.nextLine();
					if (applySaving.equals("y")) {
						boolean createSaving = service.createSavingAcct(currentUser.getUserName());
						System.out.println("\nAccount Has Been Initialized");
						System.out.println("Would You Like Make An Initial Deposit? (y/n)");
						String makeSavingDep = scanner.nextLine();
						if (makeSavingDep.equals("y")) {
							System.out.println("Enter Amount That You Would You Like To Deposit?");
							int intDepAmount =  Integer.parseInt(scanner.nextLine());
							boolean trySavingDep = service.isValidAmount(intDepAmount);
							if (createSaving && trySavingDep) {
								Transaction newSaving = new Transaction(currentUser.getUserName(), "Savings", "Deposit", intDepAmount);
								trySavingDep = service.userDeposit(newSaving);
								if (trySavingDep){
									System.out.println("Depositing "+intDepAmount+" To Your Savings Account.");
								}
							}
						}else {
							System.out.println("You Have Decline To Make An Initial Deposit. Current Savings Balance is $0");
						}
					}else {
						System.out.println("Savings Account Will Not Be Activated.\nReturning To Previous Menu.");
					}
				}else {
					System.out.println("Account Already Exists.\nReturning To Previous Menu.\n");
				}
				break;
			case "8":
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
