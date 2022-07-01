package com.example.employeesrestapi.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_no")
    private Integer EmployeeNo;
    @Past
    private LocalDate birthDate;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    private LocalDate hireDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee employee = (Employee) o;
        return EmployeeNo != null && Objects.equals(EmployeeNo, employee.EmployeeNo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
