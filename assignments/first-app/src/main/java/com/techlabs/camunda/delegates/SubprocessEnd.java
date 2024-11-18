package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SubprocessEnd implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		execution.getProcessEngineServices()
        .getRuntimeService()
        .createMessageCorrelation("SubprocessEnd")
        .correlateAll();
		
	}

}
