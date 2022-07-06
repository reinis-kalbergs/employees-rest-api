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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    final Employee employee_5 = new Employee(10005, LocalDate.of(1997, 1, 1),
            "Eve", "Surname", Gender.F, LocalDate.of(2021, 1, 1));


    final List<Employee> employees = List.of(new Employee[]{employee_1, employee_2, employee_3, employee_4, employee_5});

    final Title title_1 = new Title(employee_1, "Staff", LocalDate.now(), LocalDate.now().plusYears(1));
    final Title title_2 = new Title(employee_2, "Engineer", LocalDate.now(), LocalDate.now().plusYears(1));
    final Title title_3 = new Title(employee_3, "Staff", LocalDate.now(), LocalDate.now().plusYears(1));
    final Title title_4 = new Title(employee_4, "Engineer", LocalDate.now(), LocalDate.now().plusYears(1));

    final Title title_5 = new Title(employee_5, "Engineer", LocalDate.now(), LocalDate.now().plusYears(1));

    final List<Title> titles = List.of(new Title[]{title_1, title_2, title_3, title_4, title_5});

    @BeforeEach
    void setUp() {
        titleRepository.deleteAll();
        employeeRepository.deleteAll();

        employeeRepository.saveAll(employees);
        titleRepository.saveAll(titles);
    }

    @Test
    void shouldGetTitlesInAscendingOrder() {
        List<String> expectedResult = List.of(new String[]{"Engineer", "Staff"});

        List<String> result = titleService.getAllTitles("ASC");

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void shouldFindEmployeesByTitleAndHireDateBeforeAndHireDateBefore() {
        Page<Employee> employeePage = titleService.findAllEmployeesByTitle("Engineer", null,
                LocalDate.of(2023, 1, 1), LocalDate.of(2019, 1, 1),
                0, 50, Sort.Direction.ASC, "lastName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = titles.stream()
                .filter((title) -> title.getTitle().equals("Engineer"))
                .count();
        Employee[] expectedEmployees = {employee_2, employee_5, employee_4};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }

    @Test
    void shouldFindEmployeesByTitleAndHireDateAfterAndGender() {

        Page<Employee> employeePage = titleService.findAllEmployeesByTitle("Engineer", Gender.F,
                null, LocalDate.of(2019, 1, 1),
                0, 50, Sort.Direction.ASC, "firstName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = titles.stream()
                .filter((title) -> title.getEmployeeNo().getGender() == Gender.F)
                .filter((title) -> title.getTitle().equals("Engineer"))
                .count();
        Employee[] expectedEmployees = {employee_2, employee_5};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }

    @Test
    void shouldFindEmployeesByTitleAndHireDateBefore() {

        Page<Employee> employeePage = titleService.findAllEmployeesByTitle("Staff", null,
                LocalDate.of(2023, 1, 1), null,
                0, 50, Sort.Direction.ASC, "firstName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = titles.stream()
                .filter((title) -> title.getTitle().equals("Staff"))
                .count();
        Employee[] expectedEmployees = {employee_1, employee_3};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }

    @Test
    void shouldFindEmployeesAllByJustTitleInDescendingOrder() {

        Page<Employee> employeePage = titleService.findAllEmployeesByTitle("Engineer", null,
                null, null,
                0, 50, Sort.Direction.DESC, "lastName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = titles.stream()
                .filter((title) -> title.getTitle().equals("Engineer"))
                .count();
        Employee[] expectedEmployees = {employee_4, employee_5, employee_2};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }
}