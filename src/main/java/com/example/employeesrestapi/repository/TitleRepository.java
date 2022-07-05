package com.example.employeesrestapi.repository;

import com.example.employeesrestapi.model.Title;
import com.example.employeesrestapi.model.compositekey.TitleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, TitleKey> {

    @Query("SELECT t.title FROM Title t GROUP BY t.title")
    List<String> findTitleNames();

}
