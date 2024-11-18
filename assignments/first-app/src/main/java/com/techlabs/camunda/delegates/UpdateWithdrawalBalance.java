package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import com.techlabs.camunda.entity.CardDetails;
import com.techlabs.camunda.entity.Database;

import java.util.Map;

public class UpdateWithdrawalBalance implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        
        String cardNumber = (String) execution.getVariable("cardNumber");
        Long withdrawAmount = (Long) execution.getVariable("withdrawAmount");

        // Get the ATM Database instance and retrieve card details
        Map<String, CardDetails> ATMDatabase = Database.getInstance().getDatabase();

        if (ATMDatabase.containsKey(cardNumber)) {
            CardDetails cardDetails = ATMDatabase.get(cardNumber);
            Long currentBalance = (long) cardDetails.getAmount();

            if (currentBalance >= withdrawAmount) {
                Long updatedBalance = currentBalance - withdrawAmount;
                cardDetails.setAmount(updatedBalance);
                execution.setVariable("updatedBalance", updatedBalance);
            } else {
                throw new RuntimeException("Unable to update balance. Insufficient funds.");
            }
        } else {
            throw new RuntimeException("Card not found: " + cardNumber);
        }
    }
}
