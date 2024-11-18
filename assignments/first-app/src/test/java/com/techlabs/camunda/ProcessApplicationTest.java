package com.techlabs.camunda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.camunda.bpm.extension.*;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@ExtendWith(ProcessEngineCoverageExtension.class)
class ProcessApplicationTest {
	
	
	
	@Deployment(resources = {"atm.bpmn", "withdrawProcess.bpmn", "DepositProcess.bpmn", "PinProcess.bpmn", "BalanceProcess.bpmn"})
	@Test
	void testATM() {
		
		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey("ATM").execute();
		
		
		Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task.getName());
		
		taskService().setVariable(task.getId(), "cardNumber", "12345");
		taskService().complete(task.getId());
		
		task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task.getName());
		taskService().setVariable(task.getId(), "pin", 1234L);
		taskService().complete(task.getId());
		
		task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
		
		System.out.println(task.getName());
		testWithdrawOption(processInstance);
//        testDepositOption(processInstance);
//        testPinChangeOption(processInstance);
//        testCheckBalanceOption(processInstance);
		
        List<Task> remainingTasks = taskService().createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();

        if (!remainingTasks.isEmpty()) {
            System.out.println("Remaining tasks:");
            remainingTasks.forEach(t -> System.out.println(" - Task: " + t.getName()));
        }

		assertThat(processInstance).isEnded();
		
		
		
	}
	
	
	
	void testWithdrawOption(ProcessInstance processInstance) {
        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println(task.getName());
        taskService().setVariable(task.getId(), "option", "withdraw");
        taskService().complete(task.getId());
        
        ProcessInstance secondProcessInstance = runtimeService().createProcessInstanceQuery()
                .processDefinitionKey("withdrawProcess")
                .singleResult();
        
        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "withdrawAmount", 1000L);
        taskService().complete(task.getId());
        
        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "withdrawConfirm", true);
        taskService().complete(task.getId());
        
        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "wantReceipt", true);
        taskService().complete(task.getId());
        
        assertThat(secondProcessInstance).isEnded();
    }

    void testDepositOption(ProcessInstance processInstance) {
        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println(task.getName());
        taskService().setVariable(task.getId(), "option", "deposit");
        taskService().complete(task.getId());
        
        ProcessInstance secondProcessInstance = runtimeService().createProcessInstanceQuery()
                .processDefinitionKey("depositProcess")
                .singleResult();

        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "depositAmount", 1000L);
        taskService().complete(task.getId());
        
        
        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "wantReceipt", true);
        taskService().complete(task.getId());
        
        assertThat(secondProcessInstance).isEnded();

    }
    
    void testPinChangeOption(ProcessInstance processInstance) {
        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "option", "pinChange");
        taskService().complete(task.getId());
        
        ProcessInstance secondProcessInstance = runtimeService().createProcessInstanceQuery()
                .processDefinitionKey("pinProcess")
                .singleResult();

        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "newPin", 2345L);
        taskService().setVariable(task.getId(), "retypeNewPin", 2345L);
        taskService().complete(task.getId());
        
        task = taskService().createTaskQuery().processInstanceId(secondProcessInstance.getId()).singleResult();
        System.out.println(task.getName());
        
        taskService().setVariable(task.getId(), "changeConfirm", true);
        taskService().complete(task.getId());
        
        assertThat(secondProcessInstance).isEnded();
        
    }

    void testCheckBalanceOption(ProcessInstance processInstance) {
        Task task = taskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        System.out.println(task.getName());
        taskService().setVariable(task.getId(), "option", "checkBalance");
        taskService().complete(task.getId());
        
        ProcessInstance secondProcessInstance = runtimeService().createProcessInstanceQuery()
                .processDefinitionKey("balanceProcess")
                .singleResult();
        
        assertThat(secondProcessInstance).isNull();
    }
	
	@Deployment(resources = {"insuranceIndustry.bpmn","insurance.dmn"})
	@Test
	void testInsurance() {
		
		Map<String, Object> variables = new HashMap<>();
		
		variables.put("damageCause", "person");
		variables.put("typeOfDamage", "person");
		
		ProcessInstance processInstance = runtimeService()
		        .createProcessInstanceByKey("insuranceIndustry")
		        .setVariables(variables)
		        .execute();
		
		assertThat(processInstance).isEnded();
		
	}
	
	
//	@Deployment(resources = "login.bpmn")
//	@Test
//	void testLogin() {
//		
//		Map<String, Object> variables = new HashMap<>();
//		
//		variables.put("status", true);
//		
//		ProcessInstance processInstance = runtimeService().createProcessInstanceByKey("Login").execute();
//		
//		
//		List<Task> tasks = taskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
//		
//		tasks.forEach((task)->System.out.println(task.getName()));
//		
//		taskService().setVariable(tasks.get(0).getId(), "username", "agrah");
//		taskService().setVariable(tasks.get(0).getId(), "password", "1234");
//		
//		String username = (String) taskService().getVariable(tasks.get(0).getId(), "username");
//		String password = (String) taskService().getVariable(tasks.get(0).getId(), "password");
//		
//		if(username.equals("agrah") && password.equals("1234")) {
//			variables.put("status", true);
//			runtimeService().setVariablesLocal(processInstance.getId(), variables);
//		}
//		else {
//			variables.put("status", false);
//			runtimeService().setVariablesLocal(processInstance.getId(), variables);
//		}
//		
//		taskService().complete(tasks.get(0).getId());
//		
//		assertThat(processInstance).isEnded();
//		
//		
//		
//	}

}
