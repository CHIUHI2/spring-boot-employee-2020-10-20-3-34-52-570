package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    public Company findCompanyById(Integer id) {
        return this.companyRepository.findCompanyById(id);
    }

    public List<Employee> findCompanyEmplyeesById(Integer id) {
        Company company = this.companyRepository.findCompanyById(id);
        if(company == null) {
            return null;
        }

        return company.getEmployees();
    }
}
