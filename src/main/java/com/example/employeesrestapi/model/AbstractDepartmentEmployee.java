package com.example.employeesrestapi.model;

import com.example.employeesrestapi.model.compositekey.DepartmentEmployeeKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DepartmentEmployeeKey.class)
@MappedSuperclass
public abstract class AbstractDepartmentEmployee {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;
    private LocalDate fromDate;
    private LocalDate toDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractDepartmentEmployee that = (AbstractDepartmentEmployee) o;
        return department != null && Objects.equals(department, that.department)
                && employee != null && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employee);
    }
}
