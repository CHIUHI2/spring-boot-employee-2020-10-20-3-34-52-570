package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    List<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(this.employeeService.findAll());
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<List<Employee>> getAllByGender(@RequestParam("gender") String gender) {
        return ResponseEntity.ok(this.employeeService.findEmployeesByGender(gender));
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity<List<Employee>> getAllWithPagination(@RequestParam("page") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return ResponseEntity.ok(this.employeeService.findEmployeesWithPagination(pageIndex, pageSize));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getOne(@PathVariable Integer employeeId) {
        Employee targetEmployee = this.employees.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst()
                .orElse(null);

        return targetEmployee == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(targetEmployee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        this.employees.add(employee);

        return employee;
    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody Employee requestEmployee) {
        boolean isDeleted = this.employees.removeIf(employee -> employee.getId().equals(requestEmployee.getId()));

        if(isDeleted) {
            this.employees.add(requestEmployee);

            return ResponseEntity.ok(requestEmployee);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable Integer employeeId) {
        boolean isDeleted = this.employees.removeIf(employee -> employee.getId().equals(employeeId));

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build() ;
    }
}
