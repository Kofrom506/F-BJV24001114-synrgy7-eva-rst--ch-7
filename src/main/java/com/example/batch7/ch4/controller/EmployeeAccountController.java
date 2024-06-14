package com.example.batch7.ch4.controller;

import com.example.batch7.ch4.service.EmployeeAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

//@GetMapping
public class EmployeeAccountController {
    //
    @Autowired
    public EmployeeAccountService employeeAccountService;
}
