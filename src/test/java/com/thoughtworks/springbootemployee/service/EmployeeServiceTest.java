package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
}
