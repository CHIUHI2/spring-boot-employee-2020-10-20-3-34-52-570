package com.thoughtworks.springbootemployee.dto;

public class CompanyRequest {
    private String companyName;

    public CompanyRequest(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
