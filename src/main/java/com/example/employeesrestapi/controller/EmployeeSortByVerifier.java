package com.example.employeesrestapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class EmployeeSortByVerifier {

    private final List<String> ORDERED_BY = List.of(new String[]{"lastName", "firstName", "hireDate"});

    public void verify(String orderBy) {
        if (!ORDERED_BY.contains(orderBy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can only be ordered by" + ORDERED_BY);
        }
    }

}
