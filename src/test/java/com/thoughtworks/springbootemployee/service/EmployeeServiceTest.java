package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);

    @InjectMocks
    EmployeeService employeeService = new EmployeeService(employeeRepository);

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
        List<Employee> expectedEmployees = Arrays.asList(
                new Employee(1, "Sam", 20, "Male", 20000),
                new Employee(2, "Ken", 20, "Male", 30000),
                new Employee(3, "Anna", 20, "Female", 250000)
        );

        when(employeeRepository.findEmployeesByGender("Male")).thenReturn(expectedEmployees);

        //when
        List<Employee> returnedEmployees = employeeService.findEmployeesByGender("Male");

        //then
        assertEquals(expectedEmployees, returnedEmployees);
    }

    @Test
    public void should_return_one_employee_when_get_all_with_pagination_given_four_employees_page_index_2_page_size_3() {
        //given
        List<Employee> expectedEmployees = Arrays.asList(
                new Employee(1, "Sam", 20, "Male", 20000)
        );

        when(employeeRepository.findEmployeesWithPagination(2, 3)).thenReturn(expectedEmployees);

        //when
        List<Employee> returnedEmployees = employeeService.findEmployeesWithPagination(2, 3);

        //then
        assertEquals(expectedEmployees, returnedEmployees);
    }

    @Test
    public void should_return_correct_employee_when_find_employee_by_id_given_found_id() {
        //given
        Employee expectedEmployee = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.findEmployeeById(1)).thenReturn(expectedEmployee);

        //when
        Employee returnedEmployees = employeeService.findEmployeeById(1);

        //then
        assertEquals(expectedEmployee, returnedEmployees);
    }

    @Test
    public void should_return_null_when_find_employee_by_id_given_not_found_id() {
        //given
        Employee employee = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.findEmployeeById(1)).thenReturn(employee);

        //when
        Employee returnedEmployees = employeeService.findEmployeeById(2);

        //then
        assertNull(returnedEmployees);
    }

    @Test
    public void should_return_correct_employee_when_create_given_employee() {
        //given
        Employee expectedEmployees = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.create(expectedEmployees)).thenReturn(expectedEmployees);

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
    public void should_return_correct_employee_when_replace_given_found_employee() {
        //given
        Employee expectedEmployees = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.replace(expectedEmployees)).thenReturn(expectedEmployees);

        //when
        Employee returnedEmployees = employeeService.replace(expectedEmployees);

        //then
        assertEquals(expectedEmployees.getId(), returnedEmployees.getId());
        assertEquals(expectedEmployees.getAge(), returnedEmployees.getAge());
        assertEquals(expectedEmployees.getGender(), returnedEmployees.getGender());
        assertEquals(expectedEmployees.getName(), returnedEmployees.getName());
        assertEquals(expectedEmployees.getSalary(), returnedEmployees.getSalary());
    }

    @Test
    public void should_return_null_when_replace_given_not_found_employee() {
        //given
        Employee expectedEmployees = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.replace(expectedEmployees)).thenReturn(expectedEmployees);

        //when
        Employee returnedEmployees = employeeService.replace(new Employee(2, "Ken", 20, "Male", 20000));

        //then
        assertNull(returnedEmployees);
    }
}
