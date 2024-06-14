package com.example.batch7.ch4.entity;

import lombok.Data;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//@EntityScan
@Data
@Entity
@Table(name = "employee")
@Where(clause = "deleted_date is null")
public class Employee extends AbstractDate implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Column(name = "address", columnDefinition = "TEXT")
    public String address;

    // 2016-01-01
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public  String status = "active"; // Value Default


    //    @JsonIgnore
    @OneToOne (targetEntity = DetailKaryawan.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="id_detail_karyawan", referencedColumnName = "id")
    private DetailKaryawan detailKaryawan;

//    // best practice -> jika data yang ditampilkan , karena akan pengarruh performance
//    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Rekening> rekening;

    // field baru



}

