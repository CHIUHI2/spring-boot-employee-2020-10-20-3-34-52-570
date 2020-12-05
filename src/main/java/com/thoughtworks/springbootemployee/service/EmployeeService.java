package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.apache.logging.log4j.util.Strings;
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

    @Autowired
    private CompanyRepository companyRepository;

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> findAllByGender(String gender) {
        return this.employeeRepository.findAllByGender(gender);
    }

    public Page<Employee> findAllWithPagination(Pageable pageable) {
        return this.employeeRepository.findAll(pageable);
    }

    public Employee findEmployeeById(String id) throws EmployeeNotFoundException {
        return this.employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public Employee add(Employee employee) throws CompanyNotFoundException {
        this.validateEntity(employee);

        return this.employeeRepository.insert(employee);
    }

    public Employee replace(String id, Employee employee) throws EmployeeNotFoundException, CompanyNotFoundException {
        if (!this.employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }

        this.validateEntity(employee);

        employee.setId(id);

        return this.employeeRepository.save(employee);
    }

    public void delete(String id) throws EmployeeNotFoundException {
        if (!this.employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }

        this.employeeRepository.deleteById(id);
    }

    private void validateEntity(Employee employee) throws CompanyNotFoundException {
        if(Strings.isNotEmpty(employee.getCompanyId()) && !this.companyRepository.existsById(employee.getCompanyId())) {
            throw new CompanyNotFoundException();
        }
    }
}
