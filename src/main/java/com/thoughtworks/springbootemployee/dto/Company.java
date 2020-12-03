package com.thoughtworks.springbootemployee.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Company {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String companyName;
    private Integer employeesNumber;
    private List<Employee> employees;

    public Company() {
        this.employees = new ArrayList<>();
        this.employeesNumber = 0;
    }

    public Company(String id, String companyName) {
        this.id = id;
        this.companyName = companyName;
        this.employees = new ArrayList<>();
        this.employeesNumber = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
