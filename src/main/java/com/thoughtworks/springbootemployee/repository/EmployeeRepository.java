package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.dto.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return this.employees;
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return this.findAll().stream()
                .filter(employee -> gender.equalsIgnoreCase(employee.getGender()))
                .collect(Collectors.toList());
    }

    public List<Employee> findEmployeesWithPagination(int pageIndex, int pageSize) {
        int itemAmountToBeSkip = (pageIndex - 1) * pageSize;

        return  this.findAll().stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee findEmployeeById(int id) {
        return  this.findAll().stream()
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
            this.findAll().add(requestEmployee);
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
