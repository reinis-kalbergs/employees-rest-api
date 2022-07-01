package com.example.employeesrestapi.model.compositekey;

import com.example.employeesrestapi.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryKey implements Serializable {
    private Employee employeeNo;
    private LocalDate fromDate;
}
