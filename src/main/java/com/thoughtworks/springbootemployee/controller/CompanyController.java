package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.Company;
import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(this.companyService.findAll());
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public ResponseEntity<List<Company>> getAllWithPagination(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Company> companyPage = this.companyService.findAllWithPagination(pageable);

        return ResponseEntity.ok(companyPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getOne(@PathVariable String id) {
        Optional<Company> company = this.companyService.findCompanyById(id);

        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployees(@PathVariable String id) {
        List<Employee> employees = this.companyService.findCompanyEmployeesById(id);

        return employees == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Company> add(@RequestBody Company company) {
        Company addedCompany = this.companyService.add(company);

        if(addedCompany == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedCompany.getId())
                .toUri();

        return ResponseEntity.created(location).body(addedCompany);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@PathVariable String id, @RequestBody Company company) {
        Company updatedCompany = this.companyService.update(id, company);

        return updatedCompany == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedCompany) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean isDeleted = this.companyService.delete(id);

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build() ;
    }
}
