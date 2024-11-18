package com.monocept.camunda;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.camunda.bpm.extension.process_test_coverage.junit5.ProcessEngineCoverageExtension;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@ExtendWith(ProcessEngineCoverageExtension.class)
class ApplicationTest {

    @Deployment(resources = "Login Process.bpmn")
    @Test
    void testLogin() {
        Map<String, Object> variables = new HashMap<>();
        //ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("login", variables);
        ProcessInstance processInstance=runtimeService().createProcessInstanceByKey("login").execute();
        List<Task> tasks = taskService().createTaskQuery().processInstanceId(processInstance.getId()).list();
        
        tasks.forEach((task) -> System.out.println("Task Name: " + task.getName()));
        
        taskService().setVariable(tasks.get(0).getId(), "username", "Varish");
        taskService().setVariable(tasks.get(0).getId(), "password", "1234");

        String username = (String) taskService().getVariable(tasks.get(0).getId(), "username");
        String password = (String) taskService().getVariable(tasks.get(0).getId(), "password");

        if ("Varish".equals(username) && "1234".equals(password)) {
            variables.put("valid", true);
        } else {
            variables.put("valid", false);
        }

        taskService().complete(tasks.get(0).getId(), variables);
        assertThat(processInstance).isEnded();
    }
}
