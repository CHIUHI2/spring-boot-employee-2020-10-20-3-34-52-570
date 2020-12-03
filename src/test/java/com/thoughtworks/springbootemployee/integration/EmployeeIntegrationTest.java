package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
       this.employeeRepository.deleteAll();
    }

    @Test
    public void should_return_all_employees_when_get_all_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Sam", 18, "Male", 20000);
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Sam"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(20000));
    }

    @Test
    public void should_return_employee_when_add_given_employee() throws Exception {
        //given
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Sam");
        requestBody.put("age", 18);
        requestBody.put("gender", "Male");
        requestBody.put("salary", 20000);

        //when
        //then
        mockMvc.perform(post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody.toString())
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Sam"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(20000));

        List<Employee> employees = this.employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("Sam", employees.get(0).getName());
        assertEquals(18, employees.get(0).getAge());
        assertEquals("Male", employees.get(0).getGender());
        assertEquals(20000, employees.get(0).getSalary());
    }
}
