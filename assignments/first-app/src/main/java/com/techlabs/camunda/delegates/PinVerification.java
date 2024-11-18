package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PinVerification implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		Long originalPin = (Long) execution.getVariable("originalPin");
		Long enteredPin = (Long) execution.getVariable("pin");

        if (originalPin == null) {
            throw new RuntimeException("Original PIN is missing");
        }
        if (enteredPin == null) {
            throw new RuntimeException("Entered PIN is missing");
        }

        boolean isPinCorrect = originalPin.equals(enteredPin);

        execution.setVariable("correctPin", isPinCorrect);
	}

}
