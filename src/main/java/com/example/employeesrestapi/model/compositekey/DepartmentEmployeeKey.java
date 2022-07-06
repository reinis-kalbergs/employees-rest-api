package com.example.employeesrestapi.model.compositekey;

import com.example.employeesrestapi.model.Department;
import com.example.employeesrestapi.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeeKey implements Serializable {
    private Department department; // String id;
    private Employee employee; // Integer employeeNo;
}
