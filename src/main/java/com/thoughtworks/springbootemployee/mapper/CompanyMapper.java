package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {
    @Autowired
    private CompanyService companyService;

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();

        BeanUtils.copyProperties(companyRequest, company);

        return company;
    }

    public CompanyResponse toReponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();

        BeanUtils.copyProperties(company, companyResponse);

        try{
            List<Employee> employees = this.companyService.findCompanyEmployeesById(company.getId());
            companyResponse.setEmployees(employees);
        }
        catch(CompanyNotFoundException exception) {
            companyResponse.setEmployees(Collections.emptyList());
        }

        return companyResponse;
    }

    public List<CompanyResponse> toResponse(List<Company> companies) {
        return companies.stream()
                .map(this::toReponse)
                .collect(Collectors.toList());
    }
}
