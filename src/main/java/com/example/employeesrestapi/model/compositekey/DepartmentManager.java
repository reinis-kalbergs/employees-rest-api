package com.example.employeesrestapi.model.compositekey;

import com.example.employeesrestapi.model.AbstractDepartmentEmployee;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dept_manager")
public class DepartmentManager extends AbstractDepartmentEmployee {
}
