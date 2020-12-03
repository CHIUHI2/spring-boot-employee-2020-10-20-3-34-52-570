package com.thoughtworks.springbootemployee.dto;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private Integer id;
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    public Company() {
        this.employees = new ArrayList<>();
        this.employeesNumber = 0;
    }

    public Company(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
        this.employees = new ArrayList<>();
        this.employeesNumber = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return this.employees.size();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}
