package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AuthenticationDelegates implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String username = (String) execution.getVariable("username");
		
		String password = (String) execution.getVariable("password");
		
		boolean status;
		if(username.equals("agrah") && password.equals("1234")) {
			System.out.print("Login Success");
			status = true;
		}
		else {
			System.out.print("Login failed");
			status = false;
			throw new RuntimeException("Invalid Credentials");
		}
		
		execution.setVariable("status", status);
		
	}

}
