package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> findAllByGender(String gender) {
        return this.employeeRepository.findAllByGender(gender);
    }

    public Page<Employee> findAllWithPagination(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }

    public Optional<Employee> findEmployeeById(String id) {
        return this.employeeRepository.findById(id);
    }

    public Employee add(Employee employee) {
        return this.employeeRepository.insert(employee);
    }

    public Employee replace(String id, Employee requestEmployee) throws EmployeeNotFoundException {
        if(!this.employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }

        requestEmployee.setId(id);

        return this.employeeRepository.save(requestEmployee);
    }

    public void delete(String id) throws EmployeeNotFoundException {
        if(!this.employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }

        this.employeeRepository.deleteById(id);
    }
}
