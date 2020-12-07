package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getAll() {
        return this.companyMapper.toResponse(this.companyService.findAll());
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public List<CompanyResponse> getAllWithPagination(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        Pageable pageable = PageRequest.of((page > 0 ? page - 1 : 0), pageSize);
        Page<Company> companyPage = this.companyService.findAllWithPagination(pageable);

        return this.companyMapper.toResponse(companyPage.getContent());
    }

    @GetMapping("/{id}")
    public CompanyResponse getOne(@PathVariable String id) throws CompanyNotFoundException {
        return this.companyMapper.toResponse(this.companyService.findCompanyById(id));
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployees(@PathVariable String id) throws CompanyNotFoundException {
        return this.employeeMapper.toResponse(this.companyService.findCompanyEmployeesById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse add(@RequestBody CompanyRequest companyRequest) {
        return this.companyMapper.toResponse(this.companyService.add(this.companyMapper.toEntity(companyRequest)));
    }

    @PutMapping("/{id}")
    public CompanyResponse replace(@PathVariable String id, @RequestBody CompanyRequest companyRequest) throws CompanyNotFoundException {
        return this.companyMapper.toResponse(this.companyService.replace(id, this.companyMapper.toEntity(companyRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws CompanyNotFoundException {
        this.companyService.delete(id);
    }
}
