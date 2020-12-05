package com.thoughtworks.springbootemployee.dto;

public class EmployeeRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private String companyId;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getCompanyId() {
        return companyId;
    }
}
