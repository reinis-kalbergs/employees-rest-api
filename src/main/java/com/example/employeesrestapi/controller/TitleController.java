package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.service.TitleService;
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
public class TitleController {

    private final TitleService titleService;

    @GetMapping("/titles")
    public List<String> getAllDistinctTitles(@RequestParam(defaultValue = "asc") String order) {
        if (!(order.equalsIgnoreCase("asc") || order.equalsIgnoreCase("desc"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return titleService.getAllTitles(order);
    }

    @GetMapping("/employees/titles/{title}")
    public Page<Employee> getAllEmployeesByTitle(
            @PathVariable String title,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDateBefore,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDateAfter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize,
            @RequestParam(defaultValue = "ASC") Sort.Direction order,
            @RequestParam(defaultValue = "lastName") String orderBy
    ) {
        final List<String> POSSIBLE_TO_ORDER_BY = List.of(new String[]{"lastName", "firstName", "hireDate"});
        if (!POSSIBLE_TO_ORDER_BY.contains(orderBy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only be ordered by" + POSSIBLE_TO_ORDER_BY);
        }
        return titleService.findAllEmployeesByTitle(title, gender, hireDateBefore, hireDateAfter, page, pageSize, order, orderBy);
    }
}
