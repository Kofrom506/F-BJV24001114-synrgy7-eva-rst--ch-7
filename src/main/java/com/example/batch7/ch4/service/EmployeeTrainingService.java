package com.example.batch7.ch4.service;

import com.example.batch7.ch4.entity.Employee;

import java.util.Map;

public interface EmployeeTrainingService {

    Map save(Employee request);
    Map edit(Employee request);
    Map delete(Employee request);
    Map list();

}
