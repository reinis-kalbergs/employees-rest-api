package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.repository.EmployeeRepository;
import com.example.employeesrestapi.service.EmployeesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
public class EmployeesControllerTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    EmployeesController employeesController;

    final Employee employee_1 = new Employee(10001, LocalDate.of(1982, 12, 12),
            "John", "Smith", Gender.M, LocalDate.of(2020, 1, 1));
    final Employee employee_2 = new Employee(10002, LocalDate.of(1999, 11, 1),
            "Alice", "Green", Gender.F, LocalDate.of(2020, 9, 9));

    final Employee employee_3 = new Employee(10003, LocalDate.of(1995, 1, 1),
            "Will", "Green", Gender.M, LocalDate.of(2021, 1, 1));

    final Employee employee_4 = new Employee(10004, LocalDate.of(2000, 1, 1),
            "Peter", "White", Gender.M, LocalDate.of(2022, 1, 1));

    final Employee employee_5 = new Employee(10005, LocalDate.of(1995, 1, 1),
            "Amelia", "Snow", Gender.F, LocalDate.of(2019, 1, 1));

    final List<Employee> employees = List.of(new Employee[]{employee_1, employee_2, employee_3, employee_4, employee_5});

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        employeeRepository.saveAll(employees);
    }

    @Test
    void shouldGetAllEmployees() {
        Page<Employee> employeePage = employeesController.getAllEmployees(0, 50);
        List<Employee> result = employeePage.get().toList();
        Assertions.assertEquals(employees, result);
    }

    @Test
    void shouldFilterEmployeesByGender() {
        List<Employee> expectedResult = Arrays.asList(employee_1, employee_3, employee_4);

        Page<Employee> employeePage = employeesController.filterEmployeesByGender(Gender.M, 0, 50);
        List<Employee> result = employeePage.get().toList();

        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void shouldFilterEmployeesByHiringDateAfter() {
        List<Employee> expectedResult = Arrays.asList(employee_3, employee_4);

        Page<Employee> employeePage = employeesController.filterEmployeesByHireDateAfter(LocalDate.of(2020, 12, 12), 0, 50);
        List<Employee> result = employeePage.get().toList();

        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void shouldFilterEmployeesByHiringDateBefore() {
        List<Employee> expectedResult = Arrays.asList(employee_1, employee_2, employee_5);

        Page<Employee> employeePage = employeesController.filterEmployeesByHireDateBefore(LocalDate.of(2020, 12, 12), 0, 50);
        List<Employee> result = employeePage.get().toList();

        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void shouldOrderByFirstNameAsc() {
        List<Employee> expectedResult = employees.stream().sorted(Comparator.comparing(Employee::getFirstName)).toList();

        Page<Employee> employeePage = employeesController.getEmployeesOrdered("firstName", "asc", 0, 50);
        List<Employee> result = employeePage.get().toList();

        for (int i = 0; i < employeePage.getTotalElements(); i++) {
            Assertions.assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void shouldOrderByLastNameDesc() {
        List<Employee> expectedResult = employees.stream().sorted(Comparator.comparing(Employee::getLastName).reversed()).toList();

        Page<Employee> employeePage = employeesController.getEmployeesOrdered("lastName", "desc", 0, 50);
        List<Employee> result = employeePage.get().toList();

        for (int i = 0; i < employeePage.getTotalElements(); i++) {
            Assertions.assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void shouldOrderByHireDateAsc() {
        List<Employee> expectedResult = employees.stream().sorted(Comparator.comparing(Employee::getHireDate)).toList();

        Page<Employee> employeePage = employeesController.getEmployeesOrdered("hireDate", "asc", 0, 50);
        List<Employee> result = employeePage.get().toList();

        for (int i = 0; i < employeePage.getTotalElements(); i++) {
            Assertions.assertEquals(expectedResult.get(i), result.get(i));
        }
    }

    @Test
    void shouldThrowWhenSortingByUnknownField() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> employeesController.getEmployeesOrdered("something", "asc", 0, 50));

        Assertions.assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldThrowWhenSortingByWrongOrder() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> employeesController.getEmployeesOrdered("hireDate", "aaa", 0, 50));

        Assertions.assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
    }

}
