package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.model.Gender;
import com.example.employeesrestapi.repository.EmployeeRepository;
import com.example.employeesrestapi.repository.TitleRepository;
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
public class TitleService {

    private final TitleRepository titleRepository;
    private final EmployeeRepository employeeRepository;

    public List<String> getAllTitles(String order) {
        return titleRepository.findTitleNames()
                .stream()
                .sorted(order.equalsIgnoreCase("asc") ? Comparator.naturalOrder() : Comparator.reverseOrder())
                .toList();
    }

    public Page<Employee> findAllEmployeesByTitle(String titleName, Gender gender, LocalDate hireDateBefore, LocalDate hireDateAfter, Integer page, Integer pageSize, Sort.Direction sort, String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, pageSize, sort, sortBy);

        if (hireDateAfter != null && hireDateBefore != null) {
            return employeeRepository.findAllEmployeesByTitleAndHireDateBeforeAndAfter(titleName, gender, hireDateBefore, hireDateAfter, pageRequest);
        } else if (hireDateAfter != null) {
            return employeeRepository.findAllEmployeesByTitleAndHireDateAfter(titleName, gender, hireDateAfter, pageRequest);
        } else if (hireDateBefore != null) {
            return employeeRepository.findAllEmployeesByTitleAndHireDateBefore(titleName, gender, hireDateBefore, pageRequest);
        } else {
            return employeeRepository.findAllEmployeesByTitle(titleName, gender, pageRequest);
        }
    }

}
