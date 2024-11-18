package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendMessage implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		String selectedOption = (String) execution.getVariable("option");
		String cardNumber = (String) execution.getVariable("cardNumber");

        switch (selectedOption) {
            case "withdraw":
                execution.getProcessEngineServices()
                         .getRuntimeService()
                         .createMessageCorrelation("WithdrawSelected")
                         .processInstanceId(execution.getProcessInstanceId())
                         .setVariable("cardNumber", cardNumber)
                         .correlate();
                break;
            case "deposit":
                execution.getProcessEngineServices()
                         .getRuntimeService()
                         .createMessageCorrelation("DepositSelected")
                         .processInstanceId(execution.getProcessInstanceId())
                         .setVariable("cardNumber", cardNumber)
                         .correlate();
                break;
            case "pinChange":
                execution.getProcessEngineServices()
                         .getRuntimeService()
                         .createMessageCorrelation("ChangePINSelected")
                         .processInstanceId(execution.getProcessInstanceId())
                         .setVariable("cardNumber", cardNumber)
                         .correlate();
                break;
            case "checkBalance":
                execution.getProcessEngineServices()
                         .getRuntimeService()
                         .createMessageCorrelation("CheckBalanceSelected")
                         .processInstanceId(execution.getProcessInstanceId())
                         .setVariable("cardNumber", cardNumber)
                         .correlate();
                break;
            default:
                throw new RuntimeException("Invalid option selected: " + selectedOption);
        }
	}
}