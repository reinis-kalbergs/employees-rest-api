package com.example.employeesrestapi.model.Response;

import com.example.employeesrestapi.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteEmployeeResponse {
    private HttpStatus status;
    private Integer statusCode;
    private Employee employee;
    private String message;
}
