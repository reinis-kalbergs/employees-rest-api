package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeNo(getCurrentIdNumber());
        return employeeRepository.save(employee);
    }

    private Integer getCurrentIdNumber() {
        Optional<Employee> optionalEmployee = employeeRepository.findTopByOrderByEmployeeNoDesc();
        if (optionalEmployee.isEmpty()) {
            return 10001;
        }
        return optionalEmployee.get().getEmployeeNo() + 1;
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Employee deleteEmployeeById(Integer id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        employeeRepository.deleteById(id);
        return optionalEmployee.get();
    }
}
