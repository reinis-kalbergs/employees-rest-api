package com.example.employeesrestapi.repository;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findTopByOrderByEmployeeNoDesc();

    Page<Employee> findAllByGender(Gender gender, Pageable pageable);

    Page<Employee> findAllByHireDateAfter(LocalDate hireDate, Pageable pageable);

    Page<Employee> findAllByHireDateBefore(LocalDate hireDate, Pageable pageable);
}
