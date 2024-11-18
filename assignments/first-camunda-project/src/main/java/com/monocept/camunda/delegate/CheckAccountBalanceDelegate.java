package com.monocept.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckAccountBalanceDelegate implements JavaDelegate {
    
    private final double availableBalance = 2000;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String type =(String) execution.getVariable("dropdown");
        if(type.equals("withdraw")) {
        	long amount = (long) execution.getVariable("amount");
        	if (amount <= availableBalance) {
                execution.setVariable("sufficientBalance", true);
            } else {
                execution.setVariable("sufficientBalance", false);
            }
        }
        else if(type.equals("balanceinquiry")) {
        	execution.setVariable("balance", availableBalance);
        }
        
    }
}
