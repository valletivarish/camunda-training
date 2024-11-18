package com.techlabs.camunda.delegates;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techlabs.camunda.entity.CardDetails;
import com.techlabs.camunda.entity.Database;

public class UpdateDepositAmount implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        
        String cardNumber = (String) execution.getVariable("cardNumber");
        Long depositAmount = (Long) execution.getVariable("depositAmount");

        // Get the ATM Database instance and retrieve card details
        Map<String, CardDetails> ATMDatabase = Database.getInstance().getDatabase();

        if (ATMDatabase.containsKey(cardNumber)) {
            CardDetails cardDetails = ATMDatabase.get(cardNumber);
            Long currentBalance = (long) cardDetails.getAmount();

            Long updatedBalance = currentBalance + depositAmount;
            cardDetails.setAmount(updatedBalance);
            execution.setVariable("updatedBalance", updatedBalance);
        } else {
            throw new RuntimeException("Card not found: " + cardNumber);
        }
    }
}
