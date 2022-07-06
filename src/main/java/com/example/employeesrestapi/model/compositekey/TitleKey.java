package com.example.employeesrestapi.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleKey implements Serializable {
    private Integer employeeNo;
    private String title;
    private LocalDate fromDate;
}
