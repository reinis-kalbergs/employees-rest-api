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
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "titles")
@Entity
@IdClass(TitleKey.class)
public class Title {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employeeNumber;
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
        return employeeNumber != null && Objects.equals(employeeNumber, title1.employeeNumber)
                && title != null && Objects.equals(title, title1.title)
                && fromDate != null && Objects.equals(fromDate, title1.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeNumber, title, fromDate);
    }
}
