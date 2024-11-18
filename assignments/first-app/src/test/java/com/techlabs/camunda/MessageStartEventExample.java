package com.techlabs.camunda;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.junit.jupiter.api.Assertions.*;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(ProcessEngineCoverageExtension.class)
class MessageStartEventExample {

	@Deployment(resources = {"sendMessageExample.bpmn","messageStartEventExample.bpmn"})
	@Test
	void test() {
		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey("ParentProcess").execute();
	}

}
