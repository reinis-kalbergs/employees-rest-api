package com.example.employeesrestapi.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dept_emp")
@NoArgsConstructor
public class DepartmentEmployee extends AbstractDepartmentEmployee {
}
