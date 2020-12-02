package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Employee.Employee;
import com.thoughtworks.springbootemployee.Employee.EmployeeRepository;
import com.thoughtworks.springbootemployee.Employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @Test
    public void should_return_all_employees_when_get_all_given_all_employees() {
        //given
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        List<Employee> expected = Arrays.asList(new Employee(1, "test", 18, "male", 10000));

        when(employeeRepository.findAll()).thenReturn(expected);

        //when
        List<Employee> employees = employeeService.findAll();

        //then
        assertEquals(expected,employees);
    }
}
