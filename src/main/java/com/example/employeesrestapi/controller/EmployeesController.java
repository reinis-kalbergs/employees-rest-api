package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeesService employeesService;

    @GetMapping("/all")
    public Page<Employee> getAllEmployees(
            @RequestParam Integer page,
            @RequestParam Integer pageSize
    ) {
        return employeesService.getAllEmployees(page, pageSize);
    }

    @GetMapping("/filterByGender")
    public Page<Employee> filterEmployeesByGender(
            @RequestParam Gender gender,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize
    ) {
        return employeesService.filterEmployeesByGender(page, pageSize, gender);
    }

    @GetMapping("/filterByHireDate/before")
    public Page<Employee> filterEmployeesByHireDateBefore(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDate,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize
    ) {
        return employeesService.filterEmployeesByHiringDateBefore(page, pageSize, hireDate);
    }

    @GetMapping("/filterByHireDate/after")
    public Page<Employee> filterEmployeesByHireDateAfter(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDate,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize
    ) {
        return employeesService.filterEmployeesByHiringDateAfter(page, pageSize, hireDate);
    }

    @GetMapping("/ordered")
    public Page<Employee> getEmployeesOrdered(
            @RequestParam(defaultValue = "lastName") String by,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize
    ) {
        final List<String> ORDERED_BY = List.of(new String[]{"lastName", "firstName", "hireDate"});
        if (!ORDERED_BY.contains(by)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only be ordered by" + ORDERED_BY);
        }
        if (!(order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only be ordered in 'asc' or 'desc'");
        }
        return employeesService.getEmployeesOrderedBy(by, order, page, pageSize);
    }

}
