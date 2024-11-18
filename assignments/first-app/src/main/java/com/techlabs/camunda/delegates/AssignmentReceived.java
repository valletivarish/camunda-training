package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AssignmentReceived implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		String answer = (String) execution.getVariable("answer");
		
		execution.getProcessEngineServices()
		.getRuntimeService()
		.createMessageCorrelation("assignmentReceived")
		.setVariable("answer", answer)
		.correlate();
		
	}

}
