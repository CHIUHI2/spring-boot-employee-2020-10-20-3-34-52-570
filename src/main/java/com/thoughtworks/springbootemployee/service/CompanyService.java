package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("companyService")
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    public Page<Company> findAllWithPagination(Pageable pageable) {
        return this.companyRepository.findAll(pageable);
    }

    public Company findCompanyById(String id) throws CompanyNotFoundException {
        return this.companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findCompanyEmployeesById(String id) throws CompanyNotFoundException {
        Optional<Company> company = this.companyRepository.findById(id);
        if(!company.isPresent()) {
            throw new CompanyNotFoundException();
        }

        return this.employeeRepository.findAllByCompanyId(id);
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
