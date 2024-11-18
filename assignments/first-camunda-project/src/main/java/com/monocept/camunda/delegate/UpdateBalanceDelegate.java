package com.monocept.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class UpdateBalanceDelegate implements JavaDelegate {

    private final double availableBalance = 2000;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        long amount = (long) execution.getVariable("amount");
        String type =(String) execution.getVariable("dropdown");
        double updatedBalance;
        if(type.equals("withdraw")) {
        	updatedBalance = availableBalance - (double) amount;
            execution.setVariable("updatedBalance", updatedBalance);
        }
        else if(type.equals("deposit")) {
        	updatedBalance = availableBalance + (double) amount;
            execution.setVariable("updatedBalance", updatedBalance);
        }
        
        
    }
}
