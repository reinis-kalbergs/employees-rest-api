package com.example.employeesrestapi.controller;

import com.example.employeesrestapi.service.TitleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class TitleControllerTest {

    @InjectMocks
    TitleController titleController;

    @Mock
    TitleService titleService;

    @ParameterizedTest
    @ValueSource(strings = {"", "ascending", "abc"})
    void shouldThrowWhenIncorrectOrder(String input) {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> titleController.getAllDistinctTitles(input));
        Assertions.assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(strings = {"lastName", "firstName", "hireDate"})
    void shouldPassWhenForPossibleSortingColumns(String input) {
        titleController.getAllEmployeesByTitle(null, null, null, null, 0, 50, Sort.Direction.ASC, input);

        Mockito.verify(titleService).findAllEmployeesByTitle(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void shouldThrowWhenSortingColumn() {
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                titleController.getAllEmployeesByTitle(null, null, null, null, 0, 50, Sort.Direction.ASC, "something")
        );

        Assertions.assertEquals(exception.getStatus(), HttpStatus.BAD_REQUEST);
    }
}
