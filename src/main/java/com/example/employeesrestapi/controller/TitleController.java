package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "50") Integer pageSize
    ) {
        return titleService.findAllEmployeesByTitle(title, page, pageSize);
    }

}
