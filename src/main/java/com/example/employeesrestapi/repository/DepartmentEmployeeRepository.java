package com.example.employeesrestapi.repository;

import com.example.employeesrestapi.model.DepartmentEmployee;
import com.example.employeesrestapi.model.compositekey.DepartmentEmployeeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployeeKey> {
}
