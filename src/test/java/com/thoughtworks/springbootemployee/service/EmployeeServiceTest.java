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

        when(employeeRepository.findAllByGender("Male")).thenReturn(expectedEmployees);

        //when
        List<Employee> returnedEmployees = employeeService.findAllByGender("Male");

        //then
        assertEquals(expectedEmployees, returnedEmployees);
    }

    @Test
    public void should_return_one_employee_when_get_all_with_pagination_given_four_employees_page_index_2_page_size_3() {
        //given
        List<Employee> expectedEmployees = Arrays.asList(
                new Employee(1, "Sam", 20, "Male", 20000)
        );

        when(employeeRepository.findAllWithPagination(2, 3)).thenReturn(expectedEmployees);

        //when
        List<Employee> returnedEmployees = employeeService.findAllWithPagination(2, 3);

        //then
        assertEquals(expectedEmployees, returnedEmployees);
    }
}
