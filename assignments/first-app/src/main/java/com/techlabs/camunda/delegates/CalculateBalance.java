package com.techlabs.camunda.delegates;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techlabs.camunda.entity.CardDetails;
import com.techlabs.camunda.entity.Database;

public class CalculateBalance implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		 String cardNumber = (String) execution.getVariable("cardNumber");
		 
		 Map<String, CardDetails> ATMDatabase = Database.getInstance().getDatabase();


	        if (ATMDatabase.containsKey(cardNumber)) {
	            CardDetails cardDetails = ATMDatabase.get(cardNumber);
	            Long currentBalance = (long) cardDetails.getAmount();
	            
	            execution.setVariable("originalAmount", currentBalance);
	        } else {
	            throw new RuntimeException("Card not found: " + cardNumber);
	        }
	}

}
