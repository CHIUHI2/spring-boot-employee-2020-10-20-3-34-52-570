package com.thoughtworks.springbootemployee.dto;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    Company(String companyName) {
        this.companyName = companyName;
        this.employees = new ArrayList<>();
        this.employeesNumber = 0;
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
}
