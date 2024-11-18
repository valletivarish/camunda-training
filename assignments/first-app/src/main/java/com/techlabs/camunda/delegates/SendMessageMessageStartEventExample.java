package com.techlabs.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendMessageMessageStartEventExample implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getProcessEngineServices()
        .getRuntimeService()
        .createMessageCorrelation("ParentCall")
        .processInstanceId(execution.getProcessInstanceId())
        .correlate();

	}

}
