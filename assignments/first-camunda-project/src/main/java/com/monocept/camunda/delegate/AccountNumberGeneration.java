package com.monocept.camunda.delegate;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AccountNumberGeneration implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Random random = new Random();
        StringBuilder accountNumber = new StringBuilder(10);
        
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10)); 
        }
        
        execution.setVariable("accountNumber", accountNumber);

	}

}
