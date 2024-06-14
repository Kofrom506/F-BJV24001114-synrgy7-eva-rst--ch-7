package com.example.batch7.ch4.service;

import com.example.batch7.ch4.entity.Employee;

import java.util.Map;

public interface EmployeeService {

    Map save(Employee request);
    Map edit(Employee request);
    Map delete(Employee request);
    Map list();

    Map getById(Long id);

    Map getAllData();

    Map getByName(String name);

    Map pagination(int page, int size);

    Map paginationByName(int page, int size, String name);


}
