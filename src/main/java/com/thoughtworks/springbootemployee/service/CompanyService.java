package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    public Page<Company> findAllWithPagination(Pageable pageable) {
        return this.companyRepository.findAll(pageable);
    }

    public Optional<Company> findCompanyById(String id) {
        return this.companyRepository.findById(id);
    }

    public List<Employee> findCompanyEmployeesById(String id) throws CompanyNotFoundException {
        Optional<Company> company = this.companyRepository.findById(id);

        return company.map(Company::getEmployees).orElseThrow(CompanyNotFoundException::new);
    }

    public Company add(Company company) {
        return this.companyRepository.insert(company);
    }

    public Company replace(String id, Company company) throws CompanyNotFoundException {
        if(!this.companyRepository.existsById(id)) {
            throw new CompanyNotFoundException();
        }

        company.setId(id);

        return this.companyRepository.save(company);
    }

    public void delete(String id) throws CompanyNotFoundException {
        if(!this.companyRepository.existsById(id)) {
            throw new CompanyNotFoundException();
        }

        this.companyRepository.deleteById(id);
    }
}
