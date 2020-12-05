package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
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
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return this.employeeMapper.toResponse(this.employeeService.findAll());
    }

    @GetMapping(params = {
            "gender"
    })
    public List<EmployeeResponse> getAllByGender(@RequestParam String gender) {
        return this.employeeMapper.toResponse(this.employeeService.findAllByGender(gender));
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public List<EmployeeResponse> getAllWithPagination(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of((page > 0 ? page - 1 : 0), pageSize);
        Page<Employee> employees = this.employeeService.findAllWithPagination(pageable);

        return this.employeeMapper.toResponse(employees.getContent());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getOne(@PathVariable String id) throws EmployeeNotFoundException {
        return this.employeeMapper.toResponse(this.employeeService.findEmployeeById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse add(@RequestBody EmployeeRequest employeeRequest) throws CompanyNotFoundException {
        return this.employeeMapper.toResponse(this.employeeService.add(this.employeeMapper.toEntity(employeeRequest)));

    }

    @PutMapping("/{id}")
    public EmployeeResponse replace(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest) throws EmployeeNotFoundException, CompanyNotFoundException {
        return this.employeeMapper.toResponse(this.employeeService.replace(id, this.employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) throws EmployeeNotFoundException {
        this.employeeService.delete(id);
    }
}
