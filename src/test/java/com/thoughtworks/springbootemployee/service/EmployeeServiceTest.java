package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    public void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        List<Employee> expectedEmployees = Arrays.asList(
                new Employee(1, "Sam", 20, "Male", 20000),
                new Employee(2, "Ken", 20, "Male", 30000)
        );

        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        //when
        List<Employee> returnedEmployees = employeeService.findAll();

        //then
        assertEquals(expectedEmployees, returnedEmployees);
    }

    @Test
    public void should_return_all_male_employees_when_get_all_by_gender_given_all_employees() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(employeeRepository.findEmployeesByGender(any())).thenCallRealMethod();

        //when
        List<Employee> returnedEmployees = employeeService.findEmployeesByGender("Male");

        //then
        assertEquals(Arrays.asList(employee1, employee2), returnedEmployees);
    }

    @Test
    public void should_return_last_employee_when_get_all_with_pagination_given_employees_3_page_index_2_page_size_2() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(employeeRepository.findEmployeesWithPagination(2, 2)).thenCallRealMethod();

        //when
        List<Employee> returnedEmployees = employeeService.findEmployeesWithPagination(2, 2);

        //then
        assertEquals(Collections.singletonList(employee3), returnedEmployees);
    }

    @Test
    public void should_return_correct_employee_when_find_employee_by_id_given_found_id() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(employeeRepository.findEmployeeById(1)).thenCallRealMethod();

        //when
        Employee returnedEmployees = employeeService.findEmployeeById(1);

        //then
        assertEquals(employee1, returnedEmployees);
    }

    @Test
    public void should_return_null_when_find_employee_by_id_given_not_found_id() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(employeeRepository.findEmployeeById(4)).thenCallRealMethod();

        //when
        Employee returnedEmployees = employeeService.findEmployeeById(4);

        //then
        assertNull(returnedEmployees);
    }

    @Test
    public void should_return_correct_employee_when_create_given_employee() {
        //given
        Employee expectedEmployees = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.save(expectedEmployees)).thenReturn(expectedEmployees);

        //when
        Employee returnedEmployees = employeeService.create(expectedEmployees);

        //then
        assertEquals(expectedEmployees.getId(), returnedEmployees.getId());
        assertEquals(expectedEmployees.getAge(), returnedEmployees.getAge());
        assertEquals(expectedEmployees.getGender(), returnedEmployees.getGender());
        assertEquals(expectedEmployees.getName(), returnedEmployees.getName());
        assertEquals(expectedEmployees.getSalary(), returnedEmployees.getSalary());
    }

    @Test
    public void should_return_correct_employee_when_update_given_found_employee() {
        //given
        Employee expectedEmployees = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.update(1, expectedEmployees)).thenReturn(expectedEmployees);

        //when
        Employee returnedEmployees = employeeService.update(1, expectedEmployees);

        //then
        assertEquals(expectedEmployees.getId(), returnedEmployees.getId());
        assertEquals(expectedEmployees.getAge(), returnedEmployees.getAge());
        assertEquals(expectedEmployees.getGender(), returnedEmployees.getGender());
        assertEquals(expectedEmployees.getName(), returnedEmployees.getName());
        assertEquals(expectedEmployees.getSalary(), returnedEmployees.getSalary());
    }

    @Test
    public void should_return_null_when_update_given_not_found_employee() {
        //given
        Employee notFoundEmployee = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.update(1, notFoundEmployee)).thenReturn(null);

        //when
        Employee returnedEmployees = employeeService.update(1, notFoundEmployee);

        //then
        assertNull(returnedEmployees);
    }

    @Test
    public void should_return_true_when_delete_given_found_id() {
        //given
        when(employeeRepository.delete(1)).thenReturn(true);

        //when
        boolean result = employeeService.delete(1);

        //then
        assertTrue(result);
    }

    @Test
    public void should_return_false_when_delete_given_not_found_id() {
        //given
        when(employeeRepository.delete(2)).thenReturn(false);

        //when
        boolean result = employeeService.delete(2);

        //then
        assertFalse(result);
    }
}
