package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.dto.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return this.companies;
    }

    public Company findCompanyById(Integer id) {
        return this.findAll().stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Company> findCompaniesWithPagination(Integer pageIndex, Integer pageSize) {
        int itemAmountToBeSkip = (pageIndex - 1) * pageSize;

        return this.findAll().stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company save(Company company) {
        if(this.companies.contains(company)) {
            return null;
        }
        else {
            this.companies.add(company);

            return company;
        }
    }

    public Company update(Integer id, Company requestCompany) {
        boolean isDeleted =  this.companies.removeIf(company -> company.getId().equals(id));

        if(isDeleted) {
            this.findAll().add(requestCompany);
            return requestCompany;
        }
        else {
            return null;
        }
    }

    public boolean delete(int id) {
        return  this.companies.removeIf(company -> company.getId().equals(id));
    }
}
