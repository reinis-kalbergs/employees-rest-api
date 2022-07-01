package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(@RequestBody @NotNull @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/")
    public Employee getEmployeeById(@PathVariable("id") Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/")
    public Employee deleteEmployee(@PathVariable("id") Integer id) {
        //todo should this be a string or something else
        return employeeService.deleteEmployeeById(id);
    }
}
