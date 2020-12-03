package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    void should_return_all_employees_when_get_all_given_employees() throws Exception {
        //given
        Employee employee = new Employee("Sam", 18, "Male", 20000);
        this.employeeRepository.save(employee);

        //when
        //then
        this.mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Sam"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(20000));
    }

    @Test
    void should_return_all_male_employees_when_get_all_by_gender_given_employees_and_required_gender_male() throws Exception {
        //given
        Employee employee1 = new Employee("Sam", 18, "Male", 20000);
        this.employeeRepository.save(employee1);

        Employee employee2 = new Employee("Ken", 20, "Male", 30000);
        this.employeeRepository.save(employee2);

        Employee employee3 = new Employee("Anna", 18, "Female", 20000);
        this.employeeRepository.save(employee3);

        //when
        //then
        this.mockMvc.perform(get("/employees")
                    .param("gender", "Male")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Sam"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(20000))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Ken"))
                .andExpect(jsonPath("$[1].age").value(20))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(30000));
    }

    @Test
    void should_return_last_two_employees_when_get_all_with_pagination_given_employees_4_and_page_1_and_page_size_2() throws Exception {
        //given
        Employee employee1 = new Employee("Anna", 18, "Female", 20000);
        this.employeeRepository.save(employee1);

        Employee employee2 = new Employee("Yvonne", 19, "Female", 30000);
        this.employeeRepository.save(employee2);

        Employee employee3 = new Employee("Sam", 18, "Male", 20000);
        this.employeeRepository.save(employee3);

        Employee employee4 = new Employee("Ken", 20, "Male", 30000);
        this.employeeRepository.save(employee4);

        //when
        //then
        this.mockMvc.perform(get("/employees")
                .param("page", "1")
                .param("pageSize", "2")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Sam"))
                .andExpect(jsonPath("$[0].age").value(18))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value(20000))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Ken"))
                .andExpect(jsonPath("$[1].age").value(20))
                .andExpect(jsonPath("$[1].gender").value("Male"))
                .andExpect(jsonPath("$[1].salary").value(30000));
    }

    @Test
    void should_return_employee_when_get_one_given_found_employee() throws Exception {
        //given
        Employee employee = new Employee("Sam", 18, "Male", 20000);
        Employee addedEmployee = this.employeeRepository.save(employee);

        //when
        //then
        this.mockMvc.perform(get("/employees/" + addedEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Sam"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(20000));
    }

    @Test
    void should_return_404_when_get_one_given_not_found_employee() throws Exception {
        //given
        Employee employee = new Employee("Sam", 18, "Male", 20000);
        Employee addedEmployee = this.employeeRepository.save(employee);
        this.employeeRepository.deleteAll();

        //when
        //then
        this.mockMvc.perform(get("/employees/" + addedEmployee.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_added_employee_when_add_given_employee() throws Exception {
        //given
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Sam");
        requestBody.put("age", 18);
        requestBody.put("gender", "Male");
        requestBody.put("salary", 20000);

        //when
        //then
        this.mockMvc.perform(post("/employees")
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

    @Test
    void should_return_updated_employee_when_replace_given_found_id_and_employee() throws Exception {
        //given
        Employee employee = new Employee("Ken", 20, "Male", 30000);
        Employee addedEmployee = this.employeeRepository.save(employee);

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Sam");
        requestBody.put("age", 18);
        requestBody.put("gender", "Male");
        requestBody.put("salary", 20000);

        //when
        //then
        this.mockMvc.perform(put("/employees/" + addedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString())
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(addedEmployee.getId()))
                .andExpect(jsonPath("$.name").value("Sam"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.salary").value(20000));

        List<Employee> employees = this.employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals(addedEmployee.getId(), employees.get(0).getId());
        assertEquals("Sam", employees.get(0).getName());
        assertEquals(18, employees.get(0).getAge());
        assertEquals("Male", employees.get(0).getGender());
        assertEquals(20000, employees.get(0).getSalary());
    }

    @Test
    void should_return_404_when_replace_given_not_found_id_and_employee() throws Exception {
        //given
        Employee employee = new Employee("Ken", 20, "Male", 30000);
        Employee addedEmployee = this.employeeRepository.save(employee);
        this.employeeRepository.deleteAll();

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Sam");
        requestBody.put("age", 18);
        requestBody.put("gender", "Male");
        requestBody.put("salary", 20000);

        //when
        //then
        this.mockMvc.perform(put("/employees/" + addedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString())
                    ).andExpect(status().isNotFound());
    }

    @Test
    void should_return_204_when_delete_given_found_id() throws Exception {
        //given
        Employee employee = new Employee("Sam", 18, "Male", 20000);
        Employee addedEmployee = this.employeeRepository.save(employee);

        //when
        //then
        this.mockMvc.perform(delete("/employees/" + addedEmployee.getId()))
                .andExpect(status().isNoContent());

        List<Employee> employees = this.employeeRepository.findAll();
        assertEquals(0, employees.size());
    }

    @Test
    void should_return_404_when_delete_given_not_found_id() throws Exception {
        //given
        Employee employee = new Employee("Sam", 18, "Male", 20000);
        Employee addedEmployee = this.employeeRepository.save(employee);
        this.employeeRepository.deleteAll();

        //when
        //then
        this.mockMvc.perform(delete("/employees/" + addedEmployee.getId()))
                .andExpect(status().isNotFound());
    }
}
