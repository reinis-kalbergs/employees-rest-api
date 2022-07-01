package com.example.employeesrestapi.model.compositekey;

import com.example.employeesrestapi.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleKey implements Serializable {
    private Employee employeeNo;
    private String title;
    private LocalDate fromDate;
}
