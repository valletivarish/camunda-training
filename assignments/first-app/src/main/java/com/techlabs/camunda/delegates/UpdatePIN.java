package com.techlabs.camunda.delegates;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techlabs.camunda.entity.CardDetails;
import com.techlabs.camunda.entity.Database;

public class UpdatePIN implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        
        String cardNumber = (String) execution.getVariable("cardNumber");
        Long newPin = (Long) execution.getVariable("newPin");

        // Get the ATM Database instance and retrieve card details
        Map<String, CardDetails> ATMDatabase = Database.getInstance().getDatabase();

        if (ATMDatabase.containsKey(cardNumber)) {
            CardDetails cardDetails = ATMDatabase.get(cardNumber);
            cardDetails.setPin(newPin);

        } else {
            throw new RuntimeException("Card not found: " + cardNumber);
        }
    }
}
