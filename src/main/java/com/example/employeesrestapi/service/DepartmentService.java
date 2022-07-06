package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Department;
import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.repository.DepartmentRepository;
import com.example.employeesrestapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final EmployeeRepository employeeRepository;

    public List<Department> getAllDepartments(String order) {
        return departmentRepository.findAll()
                .stream()
                .sorted(
                        order.equalsIgnoreCase("asc") ? Comparator.comparing(Department::getName) : Comparator.comparing(Department::getName).reversed()
                )
                .toList();
    }

    public Page<Employee> findAllEmployeesByDepartment(String titleName, Gender gender, LocalDate hireDateBefore, LocalDate hireDateAfter, Integer page, Integer pageSize, Sort.Direction sort, String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, pageSize, sort, sortBy);

        if (hireDateAfter != null && hireDateBefore != null) {
            return employeeRepository.findAllEmployeesByDepartmentAndHireDateBeforeAndAfter(titleName, gender, hireDateBefore, hireDateAfter, pageRequest);
        } else if (hireDateAfter != null) {
            return employeeRepository.findAllEmployeesByDepartmentAndHireDateAfter(titleName, gender, hireDateAfter, pageRequest);
        } else if (hireDateBefore != null) {
            return employeeRepository.findAllEmployeesByDepartmentAndHireDateBefore(titleName, gender, hireDateBefore, pageRequest);
        } else {
            return employeeRepository.findAllEmployeesByDepartment(titleName, gender, pageRequest);
        }
    }

}
