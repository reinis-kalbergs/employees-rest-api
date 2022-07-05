package com.example.employeesrestapi.model;

import com.example.employeesrestapi.model.compositekey.TitleKey;
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
@Table(name = "titles")
@Entity
@IdClass(TitleKey.class)
public class Title {
    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", nullable = false)
    private Employee employeeNo;
    @Id
    private String title;
    @Id
    private LocalDate fromDate;
    private LocalDate toDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Title title1 = (Title) o;
        return employeeNo != null && Objects.equals(employeeNo, title1.employeeNo)
                && title != null && Objects.equals(title, title1.title)
                && fromDate != null && Objects.equals(fromDate, title1.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNo, title, fromDate);
    }
}
