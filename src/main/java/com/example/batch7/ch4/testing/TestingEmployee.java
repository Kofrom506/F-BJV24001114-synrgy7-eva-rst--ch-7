package com.example.batch7.ch4.testing;

import com.example.batch7.ch4.dto.*;
import com.example.batch7.ch4.entity.Employee;
import com.example.batch7.ch4.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingEmployee {

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    public EmployeeService employeeService;

    @Test
    public void saveEmployee(){
        Employee save = new Employee();
        save.setName("Aldi");
        save.setAddress("Jakarta");
        save.setStatus("active");

       Map map =  employeeService.save(save);
        int responseCode = (Integer) map.get("status");
        Assert.assertEquals(200, responseCode);

    }


    @Test
    public void listSukses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<ProvinsiResponse> exchange = restTemplate.exchange("http://dev.farizdotid.com/api/daerahindonesia/provinsi",
                HttpMethod.GET, null, ProvinsiResponse.class);
        System.out.println("response  =" + exchange.getBody());

        ProvinsiResponse provinsiResponse =  exchange.getBody();
        if (provinsiResponse != null && provinsiResponse.getProvinsi() != null) {
            for (Provinsi provinsi : provinsiResponse.getProvinsi()) {
                System.out.println("Id: " + provinsi.getId() + ", Nama: " + provinsi.getNama());
            }
        }
        // get value
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    @Test
    public void listSuksesJSONObject() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<String> exchange = restTemplate.exchange(
                "http://dev.farizdotid.com/api/daerahindonesia/provinsi",
                HttpMethod.GET,
                null,
                String.class
        );

        String responseBody = exchange.getBody();
        if (responseBody != null) {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray provinsiArray = jsonResponse.getJSONArray("provinsi");

            for (int i = 0; i < provinsiArray.length(); i++) {
                JSONObject provinsiObject = provinsiArray.getJSONObject(i);
                int id = provinsiObject.getInt("id");
                String nama = provinsiObject.getString("nama");

                System.out.println("Id: " + id + ", Nama: " + nama);
            }
        }
        // get value
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

    @Test
    public void listSuksesEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<Object> exchange = restTemplate.exchange("http://localhost:8083/v1/employee/list-employee", HttpMethod.GET, null, Object.class);
        System.out.println("response  =" + exchange.getBody());

        // get value
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }



}
