package com.example.employeesrestapi.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeeKey implements Serializable {
    private String department; // String id;
    private Integer employee; // Integer employeeNo;
}
