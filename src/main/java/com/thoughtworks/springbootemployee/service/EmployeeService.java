package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> findAllByGender(String gender) {
        return this.employeeRepository.findAllByGender(gender);
    }

    public List<Employee> findAllWithPagination(int pageIndex, int pageSize) {
        return this.employeeRepository.findAllWithPagination(pageIndex, pageSize);
    }
}
