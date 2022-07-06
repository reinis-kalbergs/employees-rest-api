package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.model.Response.DeleteEmployeeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    EmployeeController employeeController;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    private final Employee UNFINISHED_EMPLOYEE = Employee.builder()
            .gender(Gender.M).firstName("Jack")
            .hireDate(LocalDate.of(2020, 12, 12))
            .build();

    @Test
    void shouldThrowIfBlankLastName() throws Exception {

        Employee employee = UNFINISHED_EMPLOYEE.toBuilder().lastName("").birthDate(LocalDate.of(1992, 12, 12)).build();

        this.mvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowIfNonIdFieldIsNull() throws Exception {

        this.mvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UNFINISHED_EMPLOYEE)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowIfBirthDateIsFromFuture() throws Exception {

        Employee employee = UNFINISHED_EMPLOYEE.toBuilder().lastName("Green").birthDate(LocalDate.now().plusDays(12)).build();

        this.mvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnDeleteEmployeeResponseOnDeletion() {
        Employee employee = UNFINISHED_EMPLOYEE.toBuilder().lastName("Surname").birthDate(LocalDate.of(2000, 12, 12)).build();
        Employee addedEmployee = employeeController.addEmployee(employee);

        DeleteEmployeeResponse deleteEmployeeResponse = employeeController.deleteEmployee(addedEmployee.getEmployeeNo());

        Assertions.assertEquals(deleteEmployeeResponse.getEmployee(), addedEmployee);
        Assertions.assertEquals(deleteEmployeeResponse.getStatus(), HttpStatus.OK);
        Assertions.assertEquals(deleteEmployeeResponse.getStatusCode(), 200);
        Assertions.assertEquals(deleteEmployeeResponse.getMessage(), "Employee was deleted");
    }

}
