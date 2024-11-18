package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class NewAssignment implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println(execution.getVariable("assignmentName"));
		System.out.println(execution.getVariable("dueDate"));
		
	}
	

}
