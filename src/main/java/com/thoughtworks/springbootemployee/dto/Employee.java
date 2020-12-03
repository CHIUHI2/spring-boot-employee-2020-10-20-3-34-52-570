package com.thoughtworks.springbootemployee.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Employee {
    @MongoId
    private String id;
    private String name;
    private String age;
    private String gender;
    private String salary;

    public Employee() {}

    public Employee(String id, String name, String age, String gender, String salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSalary() {
        return salary;
    }
}
