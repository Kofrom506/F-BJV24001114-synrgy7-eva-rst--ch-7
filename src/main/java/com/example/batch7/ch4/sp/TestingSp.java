package com.example.batch7.ch4.sp;


import com.example.batch7.ch4.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingSp {
    @Autowired
    private DataSource dataSource;
    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public  QuerySPEmployee querySPEmployee;
    @Before
    public void init() {
        try {
            jdbcTemplate.execute(querySPEmployee.getData);
            jdbcTemplate.execute(querySPEmployee.getDataEmployeeLikeName);
            jdbcTemplate.execute(querySPEmployee.insertEmployee);
            jdbcTemplate.execute(querySPEmployee.updateEmployee);
            jdbcTemplate.execute(querySPEmployee.deletedEmployee);
        } finally {
//            session.close();
        }
    }

    @Test
    public void listSP(){
        List<Object> obj =  employeeRepository.getListSP();
        System.out.println(obj);
    }
    @Test
    public void getIDSP(){
        Object obj =  employeeRepository.getemployeebyid(6L);
        System.out.println(obj);
    }

//    @Test
//    public void getIdSp(){
//        Object obj =  employeeRepository.getemployeebyid(6L);
//        System.out.println(obj);
//    }
//
//    @Test
//    public void saveSp(){
//        employeeRepository.saveEmployeeSP(null, "spring boot1");
//    }
//
//    @Test
//    public void udpateSP(){
//        employeeRepository.updateEmployeeSP(6L, "spring boot1");
//    }
//    @Test
//    public void deletedSp(){
//        employeeRepository.deleted_employee1(8L);
//    }

}
