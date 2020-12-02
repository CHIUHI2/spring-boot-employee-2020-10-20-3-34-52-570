package com.thoughtworks.springbootemployee.Employee;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @GetMapping
    public ResponseEntity<Employee> getAll() {
        return ResponseEntity.ok(null);
    }
}
