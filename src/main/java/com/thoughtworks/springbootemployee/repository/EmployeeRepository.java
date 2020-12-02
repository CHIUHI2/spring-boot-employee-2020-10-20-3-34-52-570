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

    public List<Employee> findAllByGender(String gender) {
        return this.employees.stream()
                .filter(employee -> gender.equalsIgnoreCase(employee.getGender()))
                .collect(Collectors.toList());
    }

    public List<Employee> findAllWithPagination(int pageIndex, int pageSize) {
        int itemAmountToBeSkip = (pageIndex - 1) * pageSize;

        return this.employees.stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee findEmployeeById(int id) {
        return this.employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
