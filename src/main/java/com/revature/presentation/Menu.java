package com.revature.presentation;

import com.revature.models.User;

public interface Menu {
	
	public void employeeDisplay(User currentUser);

	public void defaultDisplay();

	public void customerDisplay(User currentUser);
	
}
