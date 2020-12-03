package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(this.employeeService.findAll());
    }

    @GetMapping(params = {
            "gender"
    })
    public ResponseEntity<List<Employee>> getAllByGender(@RequestParam String gender) {
        return ResponseEntity.ok(this.employeeService.findAllByGender(gender));
    }

    @GetMapping(params = {
            "page",
            "pageSize"
    })
    public ResponseEntity<List<Employee>> getAllWithPagination(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Employee> employees = this.employeeService.findAllWithPagination(pageable);

        return ResponseEntity.ok(employees.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOne(@PathVariable String id) {
        Optional<Employee> employee = this.employeeService.findEmployeeById(id);

        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Employee> add(@RequestBody Employee employee) {
        Employee addedEmployee = this.employeeService.add(employee);

        if(addedEmployee == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedEmployee.getId())
                .toUri();

        return ResponseEntity.created(location).body(addedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> replace(@PathVariable String id, @RequestBody Employee employee) {
       Employee updatedEmployee = this.employeeService.replace(id, employee);

       return updatedEmployee == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employee) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean isDeleted = this.employeeService.delete(id);

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build() ;
    }
}
