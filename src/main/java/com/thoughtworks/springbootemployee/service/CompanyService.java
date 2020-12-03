package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll(Integer page, Integer pageSize) {
        return this.companyRepository.findAll(page, pageSize);
    }

    public Company findCompanyById(Integer id) {
        return this.companyRepository.findCompanyById(id);
    }

    public List<Employee> findCompanyEmployeesById(Integer id) {
        Company company = this.companyRepository.findCompanyById(id);
        if(company == null) {
            return null;
        }

        return company.getEmployees();
    }

    public Company add(Company company) {
        return this.companyRepository.save(company);
    }

    public Company update(Integer id, Company company) {
        return this.companyRepository.update(id, company);
    }

    public boolean delete(Integer id) {
        return this.companyRepository.delete(id);
    }
}
