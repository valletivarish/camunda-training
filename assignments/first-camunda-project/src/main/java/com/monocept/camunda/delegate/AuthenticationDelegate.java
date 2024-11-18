package com.monocept.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AuthenticationDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String username=(String) execution.getVariable("username");
		String password=(String) execution.getVariable("password");
		
		if(username.equals("Varish") && password.equals("1234")) {
			execution.setVariable("valid", true);
		}
		else {
			execution.setVariable("valid", false);
		}
	}

}
