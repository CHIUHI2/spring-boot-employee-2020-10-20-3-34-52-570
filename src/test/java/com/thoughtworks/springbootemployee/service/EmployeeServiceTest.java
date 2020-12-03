package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
                new Employee("1", "Sam", "20", "Male", "20000"),
                new Employee("2", "Ken", "20", "Male", "30000")
        );

        when(this.employeeRepository.findAll()).thenReturn(employees);

        //when
        List<Employee> returnedEmployees = this.employeeService.findAll();

        //then
        assertEquals(employees, returnedEmployees);
    }

    @Test
    public void should_return_all_male_employees_when_get_all_by_gender_given_all_employees() {
        //given
        List<Employee> employees = Arrays.asList(
                new Employee("1", "Sam", "20", "Male", "20000"),
                new Employee("2", "Ken", "20", "Male", "30000")
        );

        when(this.employeeRepository.findAllByGender("Male")).thenReturn(employees);

        //when
        List<Employee> returnedEmployees = this.employeeService.findAllByGender("Male");

        //then
        assertEquals(employees, returnedEmployees);
    }

    @Test
    public void should_return_last_two_employees_when_get_all_with_pagination_given_employees_4_page_index_2_page_size_2() {
        //given
        List<Employee> employees = Arrays.asList(
                new Employee("1", "Sam", "20", "Male", "20000"),
                new Employee("2", "Ken", "20", "Male", "30000")
        );

        Pageable pagable = PageRequest.of(2,2);

        Page<Employee> employeesPage = new PageImpl<>(employees, pagable, employees.size());

        when(this.employeeRepository.findAll(pagable)).thenReturn(employeesPage);

        //when
        Page<Employee> returnedEmployeesPage = this.employeeService.findAllWithPagination(pagable);

        //then
        assertEquals(employeesPage, returnedEmployeesPage);
    }

    @Test
    public void should_return_correct_employee_when_find_employee_by_id_given_found_id() {
        //given
        Optional<Employee> employee = Optional.of(new Employee("1", "Sam", "20", "Male", "20000"));

        when(this.employeeRepository.findById("1")).thenReturn(employee);

        //when
        Optional<Employee> returnedEmployee = this.employeeService.findEmployeeById("1");

        //then
        assertEquals(employee, returnedEmployee);
    }

    @Test
    public void should_return_null_when_find_employee_by_id_given_not_found_id() {
        //given
        when(this.employeeRepository.findById("1")).thenReturn(Optional.empty());

        //when
        Optional<Employee> returnedEmployee = this.employeeService.findEmployeeById("1");

        //then
        assertEquals(Optional.empty(), returnedEmployee);
    }

    @Test
    public void should_return_correct_employee_when_add_given_not_existed_employee() {
        //given
        Employee employee = new Employee("1", "Sam", "20", "Male", "20000");

        when(this.employeeRepository.save(employee)).thenReturn(employee);

        //when
        Employee returnedEmployees = this.employeeService.add(employee);

        //then
        assertEquals(employee.getId(), returnedEmployees.getId());
        assertEquals(employee.getAge(), returnedEmployees.getAge());
        assertEquals(employee.getGender(), returnedEmployees.getGender());
        assertEquals(employee.getName(), returnedEmployees.getName());
        assertEquals(employee.getSalary(), returnedEmployees.getSalary());
    }

    @Test
    public void should_return_null_when_add_given_existed_employee() {
        //given
        Employee employee = new Employee("1", "Sam", "20", "Male", "20000");

        when(this.employeeRepository.insert(employee)).thenReturn(null);

        //when
        Employee returnedEmployee = this.employeeService.add(employee);

        //then
        assertNull(returnedEmployee);
    }

    @Test
    public void should_return_correct_employee_when_update_given_found_employee() {
        //given
        Employee employee = new Employee("1", "Sam", "20", "Male", "20000");

        when(this.employeeRepository.findById("1")).thenReturn(Optional.of(employee));
        when(this.employeeRepository.save(employee)).thenReturn(employee);

        //when
        Employee returnedEmployee = this.employeeService.update("1", employee);

        //then
        assertEquals(employee.getId(), returnedEmployee.getId());
        assertEquals(employee.getAge(), returnedEmployee.getAge());
        assertEquals(employee.getGender(), returnedEmployee.getGender());
        assertEquals(employee.getName(), returnedEmployee.getName());
        assertEquals(employee.getSalary(), returnedEmployee.getSalary());
    }

    @Test
    public void should_return_null_when_update_given_not_found_employee() {
        //given
        Employee employee = new Employee("1", "Sam", "20", "Male", "20000");

        when(this.employeeRepository.findById("1")).thenReturn(Optional.empty());

        //when
        Employee returnedEmployee = employeeService.update("1", employee);

        //then
        assertNull(returnedEmployee);
    }

    @Test
    public void should_return_true_when_delete_given_found_id() {
        //given
        Employee employee = new Employee("1", "Sam", "20", "Male", "20000");

        when(this.employeeRepository.findById("1")).thenReturn(Optional.of(employee));

        //when
        boolean result = employeeService.delete("1");

        //then
        assertTrue(result);
    }

    @Test
    public void should_return_false_when_delete_given_not_found_id() {
        //given
        when(this.employeeRepository.findById("1")).thenReturn(Optional.empty());

        //when
        boolean result = employeeService.delete("1");

        //then
        assertFalse(result);
    }
}
