package com.thoughtworks.springbootemployee.Employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(this.employees);
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<List<Employee>> getAllWithGender(@RequestParam("gender") String gender) {
        List<Employee> maleEmployees = this.employees.stream()
                .filter(employee -> gender.equalsIgnoreCase(employee.getGender()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(maleEmployees);
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
    public Employee addOne(@RequestBody Employee employee) {
        this.employees.add(employee);

        return employee;
    }


}
