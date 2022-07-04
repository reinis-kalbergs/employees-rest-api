package com.example.employeesrestapi.repository;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.model.Title;
import com.example.employeesrestapi.model.compositekey.TitleKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, TitleKey> {

    @Query("select t.title from Title t group by t.title")
    List<String> findTitleNames();

    @Query("select t.employeeNo from Title t where t.title LIKE :titleName")
    Page<Employee> findAllEmployeesByTitle(String titleName, Pageable pageable);

    @Query("select t.employeeNo from Title t where t.title LIKE :titleName and t.employeeNo.gender = :gender")
    Page<Employee> findAllEmployeesByTitleFilterByGender(String titleName, Gender gender, Pageable pageable);
}
