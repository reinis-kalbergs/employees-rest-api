package com.example.employeesrestapi.repository;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findTopByOrderByEmployeeNoDesc();

    Page<Employee> findAllByGender(Gender gender, Pageable pageable);

    Page<Employee> findAllByHireDateAfter(LocalDate hireDate, Pageable pageable);

    Page<Employee> findAllByHireDateBefore(LocalDate hireDate, Pageable pageable);

    //TitleService
    @Query("SELECT e FROM Employee e JOIN Title t on t.employeeNo = e "
            + "WHERE ((t.title LIKE :titleName) AND (:gender is NULL or :gender LIKE e.gender))")
    Page<Employee> findAllEmployeesByTitle(String titleName, Gender gender, Pageable pageable);

    @Query("SELECT e FROM Employee e JOIN Title t on t.employeeNo = e "
            + "WHERE ("
            + "(t.title LIKE :titleName) AND :hireDate < e.hireDate "
            + "AND (:gender IS null OR :gender LIKE e.gender)"
            + ")"
    )
    Page<Employee> findAllEmployeesByTitleAndHireDateAfter(String titleName, Gender gender, LocalDate hireDate, Pageable pageable);

    @Query("SELECT e FROM Employee e JOIN Title t on t.employeeNo = e "
            + "WHERE ("
            + "(t.title LIKE :titleName) AND :hireDate > e.hireDate "
            + "AND (:gender IS null OR :gender LIKE e.gender)"
            + ")"
    )
    Page<Employee> findAllEmployeesByTitleAndHireDateBefore(String titleName, Gender gender, LocalDate hireDate, Pageable pageable);

    @Query("SELECT e FROM Employee e JOIN Title t on t.employeeNo = e "
            + "WHERE ("
            + "(t.title LIKE :titleName) "
            + "AND :hireDateAfter < e.hireDate AND :hireDateBefore > e.hireDate "
            + "AND (:gender IS null OR :gender LIKE e.gender)"
            + ")"
    )
    Page<Employee> findAllEmployeesByTitleAndHireDateBeforeAndAfter(String titleName, Gender gender, LocalDate hireDateBefore, LocalDate hireDateAfter, Pageable pageable);

    //DepartmentService
    @Query("SELECT e FROM Employee e JOIN DepartmentEmployee de on  de.employee = e JOIN Department d on d.id = de.department "
            + "WHERE ((d.id LIKE :department) AND (:gender is NULL or :gender LIKE e.gender))")
    Page<Employee> findAllEmployeesByDepartment(String department, Gender gender, Pageable pageable);

    @Query("SELECT e FROM Employee e JOIN DepartmentEmployee de on  de.employee = e JOIN Department d on d.id = de.department "
            + "WHERE ((d.id LIKE :department) AND :hireDate < e.hireDate "
            + "AND (:gender is NULL or :gender LIKE e.gender))")
    Page<Employee> findAllEmployeesByDepartmentAndHireDateAfter(String department, Gender gender, LocalDate hireDate, Pageable pageable);

    @Query("SELECT e FROM Employee e JOIN DepartmentEmployee de on  de.employee = e JOIN Department d on d.id = de.department "
            + "WHERE ((d.id LIKE :department) AND :hireDate > e.hireDate "
            + "AND (:gender is NULL or :gender LIKE e.gender))")
    Page<Employee> findAllEmployeesByDepartmentAndHireDateBefore(String department, Gender gender, LocalDate hireDate, Pageable pageable);

    @Query("SELECT e FROM Employee e JOIN DepartmentEmployee de on  de.employee = e JOIN Department d on d.id = de.department "
            + "WHERE ((d.id LIKE :department) "
            + "AND :hireDateAfter < e.hireDate AND :hireDateBefore > e.hireDate "
            + "AND (:gender is NULL or :gender LIKE e.gender))")
    Page<Employee> findAllEmployeesByDepartmentAndHireDateBeforeAndAfter(String department, Gender gender, LocalDate hireDateBefore, LocalDate hireDateAfter, Pageable pageable);
}
