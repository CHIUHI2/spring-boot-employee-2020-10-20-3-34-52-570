package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAll(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(this.companyService.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getOne(@PathVariable Integer id) {
        Company company = this.companyService.findCompanyById(id);

        return company == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(company);
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployees(@PathVariable Integer id) {
        List<Employee> employees = this.companyService.findCompanyEmployeesById(id);

        return employees == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employees);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company add(@RequestBody Company company) {
        return this.companyService.add(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable Integer id, @RequestBody Company company) {
        Company updatedCompany = this.companyService.update(id, company);

        return updatedCompany == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedCompany) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean isDeleted = this.companyService.delete(id);

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build() ;
    }
}
