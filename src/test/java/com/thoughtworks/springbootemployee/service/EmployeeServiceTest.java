package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                new Employee("Sam", 20, "Male", 200000),
                new Employee("Ken", 20, "Male", 300000)
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
                new Employee("Sam", 20, "Male", 200000),
                new Employee("Ken", 20, "Male", 300000)
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
                new Employee("Sam", 20, "Male", 200000),
                new Employee("Ken", 20, "Male", 300000)
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
        Optional<Employee> employee = Optional.of(new Employee("Sam", 20, "Male", 200000));

        when(this.employeeRepository.findById("1")).thenReturn(employee);

        //when
        Optional<Employee> returnedEmployee = this.employeeService.findEmployeeById("1");

        //then
        assertEquals(employee, returnedEmployee);
    }

    @Test
    public void should_return_nothing_when_find_employee_by_id_given_not_found_id() {
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
        Employee employee = new Employee("Sam", 20, "Male", 200000);

        when(this.employeeRepository.insert(employee)).thenReturn(employee);

        //when
        Employee returnedEmployees = this.employeeService.add(employee);

        //then
        assertEquals(employee.getAge(), returnedEmployees.getAge());
        assertEquals(employee.getGender(), returnedEmployees.getGender());
        assertEquals(employee.getName(), returnedEmployees.getName());
        assertEquals(employee.getSalary(), returnedEmployees.getSalary());
    }

    @Test
    public void should_return_correct_employee_when_update_given_found_employee() throws EmployeeNotFoundException {
        //given
        Employee employee = new Employee("Sam", 20, "Male", 200000);

        when(this.employeeRepository.existsById("1")).thenReturn(true);
        when(this.employeeRepository.save(employee)).thenReturn(employee);

        //when
        Employee returnedEmployee = this.employeeService.replace("1", employee);

        //then
        assertEquals(employee.getId(), returnedEmployee.getId());
        assertEquals(employee.getAge(), returnedEmployee.getAge());
        assertEquals(employee.getGender(), returnedEmployee.getGender());
        assertEquals(employee.getName(), returnedEmployee.getName());
        assertEquals(employee.getSalary(), returnedEmployee.getSalary());
    }

    @Test
    public void should_throw_employee_not_found_exception_when_update_given_not_found_employee() {
        //given
        Employee employee = new Employee("Sam", 20, "Male", 200000);

        when(this.employeeRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(EmployeeNotFoundException.class, () -> {
            //when
            this.employeeService.replace("1", employee);
        });
    }

    @Test
    public void should_call_employee_repository_delete_by_id_once_when_delete_given_found_id() throws EmployeeNotFoundException {
        //given
        when(this.employeeRepository.existsById("1")).thenReturn(true);

        //when
        this.employeeService.delete("1");

        //then
        verify(employeeRepository, times(1)).deleteById("1");
    }

    @Test
    public void should_throw_employee_not_found_exception_when_delete_given_not_found_id() {
        //given
        when(this.employeeRepository.existsById("1")).thenReturn(false);

        //then
        assertThrows(EmployeeNotFoundException.class, () -> {
            //when
            this.employeeService.delete("1");
        });
    }
}
