package com.example.employeesrestapi.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dept_emp")
public class DepartmentEmployee extends AbstractDepartmentEmployee {
}
