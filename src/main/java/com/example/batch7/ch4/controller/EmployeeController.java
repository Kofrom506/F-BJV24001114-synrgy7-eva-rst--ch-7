package com.example.batch7.ch4.controller;


import com.example.batch7.ch4.config.Config;
import com.example.batch7.ch4.entity.Employee;
import com.example.batch7.ch4.service.AbsractFactoryService;
import com.example.batch7.ch4.service.EmployeeService;
import com.example.batch7.ch4.repository.EmployeeRepository;
import com.example.batch7.ch4.service.impl.BarangServiceImpl;
import com.example.batch7.ch4.service.impl.SupplierServiceImpl;
import com.example.batch7.ch4.sp.QuerySPEmployee;
import com.example.batch7.ch4.utils.Response;
import javax.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@Hidden
@RequestMapping("/v1/employee")//BCA
public class EmployeeController {

    /*
    factory
     */
    @Autowired
    public AbsractFactoryService absractFactoryServiceBarang = new BarangServiceImpl() ;

    @Autowired
    public AbsractFactoryService absractFactoryServiceSupplier = new SupplierServiceImpl() ;



    //CURD : method CURD ada dimana?
    @Autowired
    private EmployeeService employeeService;

    Config config = new Config(); // tampa DI -> Depedency injection
    @Autowired
    public Response response;

    //JIKa pake DI
    @Autowired
    private Config configWithDI;

    @GetMapping(value = "")
    public String hello(){
        return "Hello Word";

    }

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping(value = {"/list-employee", "/list-employee/"})
    public ResponseEntity<Map> getListEmpoyee() {
        return new ResponseEntity<Map>(response.sukses(employeeService.pagination(0,10)), HttpStatus.OK);
    }



    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> saveEmployee(@RequestBody Employee request) {
        return new ResponseEntity<Map>(response.sukses(employeeService.save(request)), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> updateEmployee(@RequestBody Employee request) {
        return new ResponseEntity<Map>(response.sukses(employeeService.edit(request)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted", "/deleted/"})
    public ResponseEntity<Map> deleteEmployee(@RequestBody Employee request) {
        return new ResponseEntity<Map>(response.sukses(employeeService.delete(request)), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted/{id}", "/deleted/{id}"})
    public ResponseEntity<Map> deleteEmployeeById(@PathVariable Long id) {
        //validate notnull
        Employee emp=  new Employee();
        emp.setId(id);
        return new ResponseEntity<Map>(response.sukses(employeeService.delete(emp)), HttpStatus.OK);
    }

    @GetMapping(value = "/get-data/{id}")
    public ResponseEntity<Map> getById(@PathVariable Long id) {
        return new ResponseEntity<Map>(response.sukses(employeeService.getById(id)), HttpStatus.OK);
    }

    @PutMapping(path = {"/get-data-path/{id}", "/get-data-path/{id}/"})
    public ResponseEntity<Map> getByIdPath(@PathVariable Long id) {
        return new ResponseEntity<Map>(response.sukses(employeeService.getById(id)), HttpStatus.OK);
    }
    @PutMapping(value = {"/api/users","/api/users/","/api/users123" }
//            ,
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map> getByIdPath2() {
        return new ResponseEntity<Map>(response.sukses("123"), HttpStatus.OK);
    }


    @PostMapping(value = "/save-db")
    public Map hello(@RequestBody Employee request){
        Map map = new HashMap();
        try {
            logger.info("Request:",request);
            map =  employeeService.save(request);
            return map;
        }catch (Exception e){
            logger.info("Eror hello :",e.getMessage());
            return map;
        }

    }



    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public QuerySPEmployee querySPEmployee;

    @GetMapping(value = {"/list", "/list/"})
    public ResponseEntity<Map> getById() {
        Map map = new HashMap();
        map.put("list",employeeRepository.getListSP());
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
    @GetMapping(value = {"/inject", "/inject/"})
    public ResponseEntity<String> inject() {

        jdbcTemplate.execute(querySPEmployee.getData);
        jdbcTemplate.execute(querySPEmployee.getDataEmployeeLikeName);
        jdbcTemplate.execute(querySPEmployee.insertEmployee);
        jdbcTemplate.execute(querySPEmployee.updateEmployee);
        jdbcTemplate.execute(querySPEmployee.deletedEmployee);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping(value = {"/response-integer", "/response-integer/"})
    public Integer exInteger() {
        return 1;
    }

    @GetMapping(value = {"/response-string", "/response-string/"})
    public String exString() {
        return "12";
    }

    @PostMapping(value = {"/response-object-emp", "/response-object-emp/"})
    public Employee exString(@RequestBody Employee req) {
        return req;
    }

    @GetMapping(value = { "/list-user", "/list-user/" })
    public ResponseEntity<Map> list(@RequestParam(required = false, name = "name") String name,
                                    @RequestParam(required = false, name = "address") String address,
                                    @RequestParam(required = false, name = "nik") String nik,
                                    @PageableDefault(page = 0, size = 10) Pageable pageable) {


        Specification<Employee> spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (address != null && !address.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
            }
            if (nik != null && !nik.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("detailKaryawan").get("nik")), "%" + nik.toLowerCase() + "%"));
            }

//            Aaa = Aaa
            // AaaA=aAaA
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        Page<Employee> clientCompanyList = employeeRepository.findAll(spec, pageable);
        return new ResponseEntity<Map>(response.sukses(clientCompanyList), HttpStatus.OK);
    }




}
