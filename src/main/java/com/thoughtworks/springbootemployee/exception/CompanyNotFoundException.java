package com.thoughtworks.springbootemployee.exception;

public class CompanyNotFoundException extends Exception {
    public CompanyNotFoundException() {
        super("Company not found.");
    }
}
