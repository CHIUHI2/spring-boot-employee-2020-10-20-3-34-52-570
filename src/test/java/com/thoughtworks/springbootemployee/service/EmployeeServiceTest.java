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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Sam", 20, "Male", 20000),
                new Employee(2, "Ken", 20, "Male", 30000)
        );

        when(this.employeeRepository.getEmployees()).thenReturn(employees);
        when(this.employeeRepository.findAll(null, null, null)).thenCallRealMethod();

        //when
        List<Employee> returnedEmployees = this.employeeService.findAll(null, null, null);

        //then
        assertEquals(employees, returnedEmployees);
    }

    @Test
    public void should_return_all_male_employees_when_get_all_by_gender_given_all_employees() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(this.employeeRepository.getEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(this.employeeRepository.findAll("Male", null, null)).thenCallRealMethod();

        //when
        List<Employee> returnedEmployees = this.employeeService.findAll("Male", null, null);

        //then
        assertEquals(Arrays.asList(employee1, employee2), returnedEmployees);
    }

    @Test
    public void should_return_last_two_employees_when_get_all_with_pagination_given_employees_4_page_index_2_page_size_2() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);
        Employee employee4 = new Employee(4, "Winnie", 20, "Female", 250000);

        when(this.employeeRepository.getEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3, employee4));
        when(this.employeeRepository.findAll(null, 2, 2)).thenCallRealMethod();

        //when
        List<Employee> returnedEmployees = this.employeeService.findAll(null, 2, 2);

        //then
        assertEquals(Arrays.asList(employee3, employee4), returnedEmployees);
    }

    @Test
    public void should_return_correct_employee_when_find_employee_by_id_given_found_id() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(this.employeeRepository.getEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(this.employeeRepository.findEmployeeById(1)).thenCallRealMethod();

        //when
        Employee returnedEmployee = this.employeeService.findEmployeeById(1);

        //then
        assertEquals(employee1, returnedEmployee);
    }

    @Test
    public void should_return_null_when_find_employee_by_id_given_not_found_id() {
        //given
        Employee employee1 = new Employee(1, "Sam", 20, "Male", 20000);
        Employee employee2 = new Employee(2, "Ken", 20, "Male", 30000);
        Employee employee3 = new Employee(3, "Anna", 20, "Female", 250000);

        when(this.employeeRepository.getEmployees()).thenReturn(Arrays.asList(employee1, employee2, employee3));
        when(this.employeeRepository.findEmployeeById(4)).thenCallRealMethod();

        //when
        Employee returnedEmployee = this.employeeService.findEmployeeById(4);

        //then
        assertNull(returnedEmployee);
    }

    @Test
    public void should_call_employee_repository_save_once_and_return_correct_employee_when_add_given_not_existed_employee() {
        //given
        Employee employee = new Employee(1, "Sam", 20, "Male", 20000);

        when(this.employeeRepository.save(employee)).thenReturn(employee);

        //when
        Employee returnedEmployees = this.employeeService.add(employee);

        //then
        verify(this.employeeRepository, times(1)).save(employee);
        assertEquals(employee.getId(), returnedEmployees.getId());
        assertEquals(employee.getAge(), returnedEmployees.getAge());
        assertEquals(employee.getGender(), returnedEmployees.getGender());
        assertEquals(employee.getName(), returnedEmployees.getName());
        assertEquals(employee.getSalary(), returnedEmployees.getSalary());
    }

    @Test
    public void should_return_null_when_add_given_existed_employee() {
        //given
        Employee employee = new Employee(1, "Sam", 20, "Male", 20000);

        when(this.employeeRepository.save(employee)).thenReturn(employee);

        //when
        Employee returnedEmployee = this.employeeService.add(employee);

        //then
        assertEquals(employee.getId(), returnedEmployee.getId());
        assertEquals(employee.getAge(), returnedEmployee.getAge());
        assertEquals(employee.getGender(), returnedEmployee.getGender());
        assertEquals(employee.getName(), returnedEmployee.getName());
        assertEquals(employee.getSalary(), returnedEmployee.getSalary());
    }

    @Test
    public void should_call_employee_repository_update_once_and_return_correct_employee_when_update_given_found_employee() {
        //given
        Employee employees = new Employee(1, "Sam", 20, "Male", 20000);

        when(this.employeeRepository.update(1, employees)).thenReturn(employees);

        //when
        Employee returnedEmployee = this.employeeService.update(1, employees);

        //then
        verify(this.employeeRepository, times(1)).update(1, employees);
        assertEquals(employees.getId(), returnedEmployee.getId());
        assertEquals(employees.getAge(), returnedEmployee.getAge());
        assertEquals(employees.getGender(), returnedEmployee.getGender());
        assertEquals(employees.getName(), returnedEmployee.getName());
        assertEquals(employees.getSalary(), returnedEmployee.getSalary());
    }

    @Test
    public void should_return_null_when_update_given_not_found_employee() {
        //given
        Employee employee = new Employee(1, "Sam", 20, "Male", 20000);

        when(employeeRepository.update(1, employee)).thenReturn(null);

        //when
        Employee returnedEmployee = employeeService.update(1, employee);

        //then
        assertNull(returnedEmployee);
    }

    @Test
    public void should_call_employee_repository_delete_once_and_return_true_when_delete_given_found_id() {
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
        when(employeeRepository.delete(1)).thenReturn(false);

        //when
        boolean result = employeeService.delete(1);

        //then
        assertFalse(result);
    }
}
