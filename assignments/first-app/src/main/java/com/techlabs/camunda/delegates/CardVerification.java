package com.techlabs.camunda.delegates;

import java.util.Map;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techlabs.camunda.entity.CardDetails;
import com.techlabs.camunda.entity.Database;

public class CardVerification implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		try {
            String cardNumber = (String) execution.getVariable("cardNumber");

            if (cardNumber == null) {
                throw new BpmnError("404","Card number is missing");
            }

            Map<String, CardDetails> ATMDatabase = Database.getInstance().getDatabase();

            boolean card;
            if (ATMDatabase.containsKey(cardNumber)) {
                CardDetails cardDetails = ATMDatabase.get(cardNumber);

                execution.setVariable("originalAmount", cardDetails.getAmount());
                execution.setVariable("originalPin", cardDetails.getPin());
                execution.setVariable("cardNumber", cardNumber);
                card = true;

            } else {
                System.out.println("Card number not found: " + cardNumber);
                card = false;
            }

            execution.setVariable("cardValid", card);

        } catch (Exception e) {
            System.err.println("Error during card verification: " + e.getMessage());
            throw new RuntimeException("An error occurred during card verification", e);
        }
		
	}

}
