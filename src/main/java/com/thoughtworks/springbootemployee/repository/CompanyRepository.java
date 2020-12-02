package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.dto.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return this.companies;
    }
}
