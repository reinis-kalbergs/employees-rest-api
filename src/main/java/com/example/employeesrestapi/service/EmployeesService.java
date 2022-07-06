package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> getAllEmployees(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Page<Employee> filterEmployeesByGender(Integer page, Integer pageSize, Gender gender) {
        return employeeRepository.findAllByGender(gender, PageRequest.of(page, pageSize));
    }

    public Page<Employee> filterEmployeesByHiringDateAfter(Integer page, Integer pageSize, LocalDate hireDate) {
        return employeeRepository.findAllByHireDateAfter(hireDate, PageRequest.of(page, pageSize));
    }

    public Page<Employee> filterEmployeesByHiringDateBefore(Integer page, Integer pageSize, LocalDate hireDate) {
        return employeeRepository.findAllByHireDateBefore(hireDate, PageRequest.of(page, pageSize));
    }

    public Page<Employee> getEmployeesOrderedBy(String sortBy, String order, Integer page, Integer pageSize) {
        if (order.equalsIgnoreCase("asc")) {
            return employeeRepository.findAll(PageRequest.of(page, pageSize, Sort.by(sortBy).ascending()));
        } else {
            return employeeRepository.findAll(PageRequest.of(page, pageSize, Sort.by(sortBy).descending()));
        }
    }
    
}
