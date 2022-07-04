package com.example.employeesrestapi.service;

import com.example.employeesrestapi.model.Employee;
import com.example.employeesrestapi.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    public List<String> getAllTitles(String order) {
        return titleRepository.findTitleNames()
                .stream()
                .sorted(order.equalsIgnoreCase("asc") ? Comparator.naturalOrder() : Comparator.reverseOrder())
                .toList();
    }

    public Page<Employee> findAllEmployeesByTitle(String titleName, Integer page, Integer pageSize) {
        return titleRepository.findAllEmployeesByTitle(titleName, PageRequest.of(page, pageSize));
    }
    
}
