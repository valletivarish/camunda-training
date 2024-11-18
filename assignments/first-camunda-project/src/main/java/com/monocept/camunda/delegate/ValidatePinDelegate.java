package com.monocept.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ValidatePinDelegate implements JavaDelegate {
    
    private CardDetails[] cards = new CardDetails[]{
        new CardDetails("1234 5678 9012 3456", 1234, "Alice Smith", "12/25", "Visa"),
        new CardDetails("2345 6789 0123 4567", 2345, "Bob Johnson", "05/24", "MasterCard"),
        new CardDetails("3456 7890 1234 5678", 3456, "Charlie Brown", "09/26", "American Express"),
        new CardDetails("4567 8901 2345 6789", 4567, "Diana Prince", "03/23", "Discover"),
        new CardDetails("5678 9012 3456 7890", 5678, "Ethan Hunt", "01/27", "Visa"),
        new CardDetails("6789 0123 4567 8901", 6789, "Fiona Glenanne", "11/25", "MasterCard"),
        new CardDetails("7890 1234 5678 9012", 7890, "George Smith", "04/24", "American Express"),
        new CardDetails("8901 2345 6789 0123", 8901, "Hannah Baker", "10/26", "Discover"),
        new CardDetails("9012 3456 7890 1234", 9012, "Ian Malcolm", "06/25", "Visa"),
        new CardDetails("0123 4567 8901 2345", 1234, "Jack Reacher", "08/24", "MasterCard")
    };

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String accountNumber = (String) execution.getVariable("accountNumber");
        int pin = ((Long) execution.getVariable("pin")).intValue();
        boolean validPin = false;
        for (CardDetails card : cards) {
            if (card.getCardNumber().equals(accountNumber) && card.getPin() == pin) {
                validPin = true;
                break;
            }
        }
        execution.setVariable("validPin", validPin);
        if (!validPin) {
            throw new RuntimeException("Invalid PIN. Please try again.");
        }
        

        
    }
}