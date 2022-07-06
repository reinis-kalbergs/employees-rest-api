package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Department;
import com.example.employeesrestapi.model.DepartmentEmployee;
import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.repository.DepartmentEmployeeRepository;
import com.example.employeesrestapi.repository.DepartmentRepository;
import com.example.employeesrestapi.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentEmployeeRepository departmentEmployeeRepository;

    final Employee employee_1 = new Employee(10001, LocalDate.of(1982, 12, 12),
            "John", "Smith", Gender.M, LocalDate.of(2020, 1, 1));
    final Employee employee_2 = new Employee(10002, LocalDate.of(1999, 11, 1),
            "Alice", "Green", Gender.F, LocalDate.of(2020, 9, 9));

    final Employee employee_3 = new Employee(10003, LocalDate.of(1995, 1, 1),
            "Will", "Blue", Gender.M, LocalDate.of(2021, 1, 1));

    final Employee employee_4 = new Employee(10004, LocalDate.of(2000, 1, 1),
            "Peter", "White", Gender.M, LocalDate.of(2022, 1, 1));

    final Employee employee_5 = new Employee(10005, LocalDate.of(1997, 1, 1),
            "Eve", "Surname", Gender.F, LocalDate.of(2021, 1, 1));


    final List<Employee> employees = List.of(new Employee[]{employee_1, employee_2, employee_3, employee_4, employee_5});


    final Department department_1 = new Department("d005", "Development");
    final Department department_2 = new Department("d003", "Human Resources");

    final LocalDate mockDate = LocalDate.of(1999, 12, 31);

    final DepartmentEmployee departmentEmployee_1 = new DepartmentEmployee(department_1, employee_1, mockDate, mockDate);
    final DepartmentEmployee departmentEmployee_2 = new DepartmentEmployee(department_1, employee_2, mockDate, mockDate);
    final DepartmentEmployee departmentEmployee_3 = new DepartmentEmployee(department_1, employee_3, mockDate, mockDate);
    final DepartmentEmployee departmentEmployee_4 = new DepartmentEmployee(department_2, employee_4, mockDate, mockDate);
    final DepartmentEmployee departmentEmployee_5 = new DepartmentEmployee(department_2, employee_5, mockDate, mockDate);

    final List<DepartmentEmployee> departmentEmployees = List.of(new DepartmentEmployee[]{departmentEmployee_1, departmentEmployee_2, departmentEmployee_3, departmentEmployee_4, departmentEmployee_5});

    @BeforeEach
    void setUp() {
        employeeRepository.saveAll(employees);
        departmentRepository.save(department_1);
        departmentRepository.save(department_2);
        departmentEmployeeRepository.saveAll(departmentEmployees);
    }

    @AfterEach
    void tearDown() {
        departmentEmployeeRepository.deleteAll();
        departmentRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void shouldGetTitlesInDescendingOrder() {
        List<Department> expectedResult = List.of(new Department[]{department_2, department_1});

        List<Department> result = departmentService.getAllDepartments("DESC");

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void shouldFindEmployeesByDepartmentAndHireDateBeforeAndHireDateBefore() {
        Page<Employee> employeePage = departmentService.findAllEmployeesByDepartment(department_1.getId(), null,
                LocalDate.of(2023, 1, 1), LocalDate.of(2019, 1, 1),
                0, 50, Sort.Direction.ASC, "lastName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = departmentEmployees.stream()
                .filter((emp) -> emp.getDepartment().equals(department_1))
                .count();
        Employee[] expectedEmployees = {employee_3, employee_2, employee_1};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }

    @Test
    void shouldFindEmployeesByDepartmentAndHireDateAfterAndGender() {
        Page<Employee> employeePage = departmentService.findAllEmployeesByDepartment(department_1.getId(), Gender.M,
                null, LocalDate.of(2019, 1, 1),
                0, 50, Sort.Direction.ASC, "firstName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = departmentEmployees.stream()
                .filter((emp) -> emp.getDepartment().equals(department_1))
                .filter((emp) -> emp.getEmployee().getGender() == Gender.M)
                .count();
        Employee[] expectedEmployees = {employee_1, employee_3};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }

    @Test
    void shouldFindEmployeesByDepartmentAndHireDateBefore() {
        Page<Employee> employeePage = departmentService.findAllEmployeesByDepartment(department_2.getId(), null,
                LocalDate.of(2023, 1, 1), null,
                0, 50, Sort.Direction.ASC, "firstName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = departmentEmployees.stream()
                .filter((emp) -> emp.getDepartment().equals(department_2))
                .count();
        Employee[] expectedEmployees = {employee_5, employee_4};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }

    @Test
    void shouldFindEmployeesAllByJustDepartmentInDescendingOrder() {

        Page<Employee> employeePage = departmentService.findAllEmployeesByDepartment(department_2.getId(), null,
                null, null,
                0, 50, Sort.Direction.DESC, "lastName");
        List<Employee> result = employeePage.get().toList();

        Long expectedCount = departmentEmployees.stream()
                .filter((emp) -> emp.getDepartment().equals(department_2))
                .count();
        Employee[] expectedEmployees = {employee_4, employee_5};
        Assertions.assertEquals(expectedCount, employeePage.getTotalElements());
        for (int i = 0; i < expectedCount; i++) {
            Assertions.assertEquals(expectedEmployees[i], result.get(i));
        }
    }
}
