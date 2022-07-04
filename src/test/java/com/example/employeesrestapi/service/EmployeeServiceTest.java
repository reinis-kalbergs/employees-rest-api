package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    final Employee employee_1 = new Employee(10001, LocalDate.of(1982, 12, 12),
            "John", "Smith", Gender.M, LocalDate.now());
    final Employee employee_2 = new Employee(10002, LocalDate.of(1999, 11, 1),
            "Alice", "Green", Gender.F, LocalDate.now().minusMonths(3));

    final Employee employee_3 = new Employee(null, LocalDate.of(1995, 1, 1),
            "Will", "Green", Gender.M, LocalDate.now());

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        employeeRepository.save(employee_1);
        employeeRepository.save(employee_2);
    }

    @Test
    void shouldAddAndGetEmployee() {

        Employee addedEmployee = employeeService.addEmployee(employee_3);

        Employee expectedEmployee = employee_3.toBuilder().employeeNo(10003).build();

        Assertions.assertEquals(addedEmployee, expectedEmployee);
    }

    @Test
    void shouldThrowWhenNotFound() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> employeeService.getEmployeeById(1));

        Assertions.assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldGenerateId() {
        Employee addedEmployee = employeeService.addEmployee(employee_3);

        Assertions.assertEquals(addedEmployee.getEmployeeNo(), 10003);
    }

    @Test
    void shouldGenerateIdWhenNoEmployeesDetected() {
        employeeRepository.deleteAll();

        Employee addedEmployee = employeeService.addEmployee(employee_3);

        Assertions.assertEquals(addedEmployee.getEmployeeNo(), 10001);
    }
}
