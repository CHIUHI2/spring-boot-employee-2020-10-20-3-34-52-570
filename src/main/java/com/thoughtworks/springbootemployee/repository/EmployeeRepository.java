package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.dto.Employee;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public List<Employee> findAll(String gender, Integer page, Integer pageSize) {
        List<Employee> employees = this.getEmployees();

        if(Strings.isNotEmpty(gender)) {
            employees = this.filterByGender(gender, employees);
        }

        if(page != null && pageSize != null) {
            employees = this.doPagination(page, pageSize, employees);
        }

        return employees;
    }

    private List<Employee> filterByGender(String gender, List<Employee> employees) {
        return employees.stream()
                .filter(employee -> gender.equalsIgnoreCase(employee.getGender()))
                .collect(Collectors.toList());
    }

    private List<Employee> doPagination(Integer page, Integer pageSize, List<Employee> employees) {
        int itemAmountToBeSkip = (page - 1) * pageSize;

        return employees.stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee findEmployeeById(int id) {
        return  this.getEmployees().stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Employee save(Employee employee) {
        if(this.employees.contains(employee)) {
            return null;
        }
        else {
            this.employees.add(employee);

            return employee;
        }
    }

    public Employee update(Integer id, Employee requestEmployee) {
        boolean isDeleted =  this.employees.removeIf(employee -> employee.getId().equals(id));

        if(isDeleted) {
            this.getEmployees().add(requestEmployee);
            return requestEmployee;
        }
        else {
            return null;
        }
    }

    public boolean delete(int id) {
        return  this.employees.removeIf(employee -> employee.getId().equals(id));
    }
}
