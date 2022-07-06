package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Department;
import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeSortByVerifier employeeSortByVerifier;

    @GetMapping("/departments")
    public List<Department> getAllDepartments(@RequestParam(defaultValue = "asc") String order) {
        if (!(order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return departmentService.getAllDepartments(order);
    }

    @GetMapping("/departments/{department}/employees")
    public Page<Employee> getAllEmployeesByDepartment(
            @PathVariable String department,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDateBefore,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDateAfter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "ASC") Sort.Direction order,
            @RequestParam(defaultValue = "lastName") String orderBy
    ) {
        employeeSortByVerifier.verify(orderBy);
        return departmentService.findAllEmployeesByDepartment(department, gender, hireDateBefore, hireDateAfter, page, pageSize, order, orderBy);
    }

}
