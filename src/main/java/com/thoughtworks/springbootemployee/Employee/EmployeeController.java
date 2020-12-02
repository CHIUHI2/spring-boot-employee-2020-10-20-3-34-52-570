package com.thoughtworks.springbootemployee.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(this.employees);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addOne(@RequestBody Employee employee) {
        this.employees.add(employee);

        return employee;
    }
}
