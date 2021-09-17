package com.revature.models;

public class Transaction {
	private int transId;
	private int transAmount;
	private int startBal;
	private int endBal;
	private boolean sentStatus;
	private String rootName;
	private String rootType;
	private String transType;
	private String destinationName;
	private String destinationType;
	
	public static int numberOfTrans = 0;
	
	public Transaction(String rootName, String rootType, String destinationName, String destinationType, int transAmount) {
		//Constructor for posting transfer
		super();
		numberOfTrans ++;
		this.transId = numberOfTrans;
		this.rootName = rootName;
		this.rootType = rootType;
		this.destinationName = destinationName;
		this.destinationType = destinationType;
		this.transAmount = transAmount;
		this.transType = "Transfer Out";
		this.sentStatus = false;
	}
	
	public Transaction(String rootName, String rootType, int transAmount) {
		//Constructor for accepting transfer
		super();
		numberOfTrans ++;
		this.transId = numberOfTrans;
		this.rootName = rootName;
		this.rootType = rootType;
		this.transAmount = transAmount;
		this.transType = "Transfer In";
		this.sentStatus = true;
	}
	public Transaction(String rootName, String rootType, String transType, int transAmount) {
		//Constructor for withdrawal or deposit
		super();
		numberOfTrans ++;
		this.transId = numberOfTrans;
		this.rootName = rootName;
		this.rootType = rootType;
		this.transAmount = transAmount;
		this.transType = transType;
		this.sentStatus = true;
	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public int getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(int transAmount) {
		this.transAmount = transAmount;
	}

	public int getStartBal() {
		return startBal;
	}

	public void setStartBal(int startBal) {
		this.startBal = startBal;
	}

	public int getEndBal() {
		return endBal;
	}

	public void setEndBal(int endBal) {
		this.endBal = endBal;
	}

	public boolean isSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(boolean sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	
}
