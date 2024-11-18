package com.monocept.camunda;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskService;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ProcessEngineCoverageExtension.class)
class AtmTest {

    @Deployment(resources = "Atm Transaction.bpmn")
    @Test
    void testAtmProcess() {
        ProcessInstance processInstance = startProcess();
        authenticateUser(processInstance);
        balanceInquiry(processInstance);

        assertThat(processInstance).isEnded();
    }

    private ProcessInstance startProcess() {
        ProcessInstance processInstance = runtimeService().createProcessInstanceByKey("Process_0jyyi1u").execute();
        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Task Name: " + task.getName());
        taskService().complete(task.getId());
        return processInstance;
    }

    private void authenticateUser(ProcessInstance processInstance) {
        Task nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "accountNumber", "1234 5678 9012 3456");
        taskService().setVariable(nextTask.getId(), "pin", 1234);
        taskService().complete(nextTask.getId());
    }

    private void withdraw(ProcessInstance processInstance) {
        Task nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "dropdown", "withdraw");
        taskService().complete(nextTask.getId());

        nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "amount", 200L);
        taskService().complete(nextTask.getId());

        nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "receipt", true);
        taskService().complete(nextTask.getId());
    }

    private void deposit(ProcessInstance processInstance) {
        Task nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "dropdown", "deposit");
        taskService().complete(nextTask.getId());

        nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "amount", 1000L);
        taskService().complete(nextTask.getId());

        nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "receipt", true);
        taskService().complete(nextTask.getId());
    }

    private void balanceInquiry(ProcessInstance processInstance) {
        Task nextTask = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println("Next Task Name: " + nextTask.getName());
        taskService().setVariable(nextTask.getId(), "dropdown", "balanceinquiry");
        taskService().complete(nextTask.getId());
    }
}
