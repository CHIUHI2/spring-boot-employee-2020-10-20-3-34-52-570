package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.dto.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
