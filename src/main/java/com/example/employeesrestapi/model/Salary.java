package com.example.employeesrestapi.model;

import com.example.employeesrestapi.model.compositekey.SalaryKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SalaryKey.class)
@Table(name = "salaries")
public class Salary {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employeeNumber;
    private Integer Salary;
    @Id
    private LocalDate fromDate;
    private LocalDate toDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Salary salary = (Salary) o;
        return employeeNumber != null && Objects.equals(employeeNumber, salary.employeeNumber)
                && fromDate != null && Objects.equals(fromDate, salary.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber, fromDate);
    }
}
