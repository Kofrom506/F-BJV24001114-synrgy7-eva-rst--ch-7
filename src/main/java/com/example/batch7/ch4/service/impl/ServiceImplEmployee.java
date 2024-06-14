package com.example.batch7.ch4.service.impl;

import com.example.batch7.ch4.entity.Employee;
import com.example.batch7.ch4.repository.EmployeeRepository;
import com.example.batch7.ch4.service.EmployeeService;
import com.example.batch7.ch4.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImplEmployee implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplEmployee.class);

    @Autowired // Depedency Injection ->
    private EmployeeRepository employeeRepository;

    @Autowired
    private Response response;

    @Override
    public Map save(Employee request) {
        /*
        1. validasi field apa : Wajib sesuai flow bisnis
         */
        if(response.chekNull(request.getName())){
            return  response.error("Name is required.",402);
        }
        if(StringUtils.isEmpty(request.getName())){
            return  response.error("Name is required.",402);
        }

        return response.sukses(employeeRepository.save(request));
    }

    @Override
    public Map edit(Employee request) {
        try {
        /*
        1. get id  -> cehk data ke db, ?
        2. save
         */
        if(response.chekNull(request.getId())){
            return  response.error("Id is required.",402);
        }

        Optional<Employee> getId = employeeRepository.findById(request.getId());
        if(!getId.isPresent()){
            return response.error("id not found", 404);
        }

        Employee edit = getId.get();
        edit.setAddress(request.getAddress());
        edit.setName(request.getName());
        edit.setDob(request.getDob());
        edit.setDetailKaryawan(request.getDetailKaryawan());

        return response.sukses(employeeRepository.save(edit));
        }catch (Exception e){
            return response.error(e.getMessage(), 500);
        }
    }

    @Override
    public Map delete(Employee request) {
        return null;
    }

    @Override
    public Map list() {
        return null;
    }

    @Override
    public Map getById(Long id) {
        Map map = new HashMap();
        Optional<Employee> getId = employeeRepository.findById(id);
        if(!getId.isPresent()){
            return response.error("id not found", 404);
        }
        return response.sukses(getId.get());
    }

    @Override
    public Map getAllData() {

        return null;
    }

    @Override
    public Map getByName(String name) {
        Map map = new HashMap();
        Optional<Employee> getByName = employeeRepository.getByName(name);
        if(!getByName.isPresent()){
            return response.error("Name not found", 404);
        }
        return response.sukses(getByName.get());
    }

    @Override
    public Map pagination(int page, int size) {
        Pageable show_data = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Employee> list = employeeRepository.getAllDataPage(show_data);
        return response.sukses(list);
    }

    @Override
    public Map paginationByName(int page, int size, String name) {
        Pageable show_data = PageRequest.of(page, size, Sort.by("created_date").descending());
        Page<Employee> list = employeeRepository.getAllDataByName(name,show_data);
        /*
        generate manual UUID
         */
        System.out.println(UUID.randomUUID());  //
        return response.sukses(list);
    }
}
