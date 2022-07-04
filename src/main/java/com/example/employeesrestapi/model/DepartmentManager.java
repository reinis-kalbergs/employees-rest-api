package com.example.employeesrestapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dept_manager")
@Data
@NoArgsConstructor
public class DepartmentManager extends AbstractDepartmentEmployee {
}
