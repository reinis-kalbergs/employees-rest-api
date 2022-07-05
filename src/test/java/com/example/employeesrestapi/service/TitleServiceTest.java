package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.model.Title;
import com.example.employeesrestapi.repository.EmployeeRepository;
import com.example.employeesrestapi.repository.TitleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TitleServiceTest {

    @Autowired
    TitleService titleService;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    final Employee employee_1 = new Employee(10001, LocalDate.of(1982, 12, 12),
            "John", "Smith", Gender.M, LocalDate.of(2020, 1, 1));
    final Employee employee_2 = new Employee(10002, LocalDate.of(1999, 11, 1),
            "Alice", "Green", Gender.F, LocalDate.of(2020, 9, 9));

    final Employee employee_3 = new Employee(10003, LocalDate.of(1995, 1, 1),
            "Will", "Green", Gender.M, LocalDate.of(2021, 1, 1));

    final Employee employee_4 = new Employee(10004, LocalDate.of(2000, 1, 1),
            "Peter", "White", Gender.M, LocalDate.of(2022, 1, 1));


    final List<Employee> employees = List.of(new Employee[]{employee_1, employee_2, employee_3, employee_4});

    final Title title_1 = new Title(employee_1, "Senior Engineer", LocalDate.now(), LocalDate.now().plusYears(1));
    final Title title_2 = new Title(employee_2, "Engineer", LocalDate.now(), LocalDate.now().plusYears(1));
    final Title title_3 = new Title(employee_3, "Manager", LocalDate.now(), LocalDate.now().plusYears(1));
    final Title title_4 = new Title(employee_4, "Engineer", LocalDate.now(), LocalDate.now().plusYears(1));

    final List<Title> titles = List.of(new Title[]{title_1, title_2, title_3, title_4});

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        titleRepository.deleteAll();

        employeeRepository.saveAll(employees);
        titleRepository.saveAll(titles);
    }

    @Test
    void shouldGetTitlesInAscendingOrder() {
        List<String> expectedResult = List.of(new String[]{"Engineer", "Manager", "Senior Engineer"});

        List<String> result = titleService.getAllTitles("ASC");

        Assertions.assertEquals(expectedResult, result);
    }


}