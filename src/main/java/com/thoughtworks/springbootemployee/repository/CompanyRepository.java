package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.dto.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    List<Company> companies = new ArrayList<>();

    public List<Company> getCompanies() {
        return this.companies;
    }

    public List<Company> findAll(Integer page, Integer pageSize) {
        List<Company> companies = this.getCompanies();

        if(page != null && pageSize != null) {
            companies = this.doPagination(page, pageSize, companies);
        }

        return companies;
    }

    private List<Company> doPagination(Integer page, Integer pageSize, List<Company> companies) {
        int itemAmountToBeSkip = (page - 1) * pageSize;

        return companies.stream()
                .skip(itemAmountToBeSkip)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company findCompanyById(Integer id) {
        return this.getCompanies().stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Company save(Company requestCompany) {
        boolean isExisted = this.companies.stream()
                .allMatch(company -> company.getId().equals(requestCompany.getId()));

        if(isExisted) {
            return null;
        }
        else {
            this.companies.add(requestCompany);

            return requestCompany;
        }
    }

    public Company update(Integer id, Company requestCompany) {
        boolean isDeleted =  this.companies.removeIf(company -> company.getId().equals(id));

        if(isDeleted) {
            this.getCompanies().add(requestCompany);
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
