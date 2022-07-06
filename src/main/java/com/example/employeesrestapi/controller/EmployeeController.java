package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Response.DeleteEmployeeResponse;
import com.example.employeesrestapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public Employee addEmployee(@RequestBody @NotNull @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    public DeleteEmployeeResponse deleteEmployee(@PathVariable("id") Integer id) {
        Employee employee = employeeService.deleteEmployeeById(id);
        return DeleteEmployeeResponse.builder()
                .employee(employee).message("Employee was deleted")
                .status(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                .build();
    }
}
