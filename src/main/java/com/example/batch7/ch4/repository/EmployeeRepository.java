package com.example.batch7.ch4.repository;


import com.example.batch7.ch4.entity.Employee;
import com.example.batch7.ch4.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
//public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    /*
    1. Query JPA : referensi https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
     */
    // select semua data where nama == apa?
    public List<Employee>  findByName(String nama);

    // select semua data where nama == apa and status
    public List<Employee>  findByNameAndStatus(String nama, String status);

    /*
    2. Query Object nya , param : Query Projection
     */
    @Query(value = "select uk from Employee uk WHERE uk.id = :id")
    public Optional<Employee> getById(@Param("id") UUID id);

    @Query(value = "select uk from Employee uk WHERE uk.name = :nameParameter")
    public Optional<Employee> getByName(@Param("nameParameter") String name);

    @Query("FROM Employee u WHERE LOWER(u.name) like LOWER(:nameParam)")
    public List<Employee> findByNameLike(String nameParam);

    /*
    1. Native Query
     */
    // Pelajari Sendiri ->  nama tabel, column yang di db
    /*
     @Query(value = "SELECT (COUNT(DISTINCT up.id_event)) \n" +
         "                  FROM event_participant_task up\n" +
         "                  INNER JOIN master_event_task met ON up.id_master_event_task = met.id\n" +
         "                  INNER JOIN master_event me ON me.id = met.id_master_event\n" +
         "                  WHERE up.id_participant =:participant \n" +
         "                    AND up.is_complete_grade_question =true\n" +
         "                   and up.status_task='FINISHED' \n" +
         "                   and met.deleted_date is null;",nativeQuery = true)
 public Integer countFinishedEventALLJOURNEY(@Param("participant") Long participant );
     */

    /*
    4. Pagination -> Memecah data -> 1000 -> 10 data ada 100 halaman
     */
//    menampilkan semua data : pagination
    @Query("FROM Employee u")
    public Page<Employee> getAllDataPage(Pageable pageable);

    // menampikan semua data : tapi filter base name
    @Query("FROM Employee u where LOWER(u.name) like LOWER(:nameParam)")
    public Page<Employee> getAllDataByName(@Param("nameParam") String name, Pageable pageable);

    //JPA
    @Query(value = "select e from Employee e")
    public List<Employee> getALlPage();

    //    Store prosedure
    @Query(value = "select * from getemployee6()",nativeQuery = true)
    public List<Object> getListSP();

    @Procedure("insert1")
    @Transactional
    void saveEmployeeSP(@Param("resid") Long resid, @Param("rqnama") String rqnama);

    @Transactional
    @Procedure("update_employee")
    void updateEmployeeSP(@Param("resid") Long resid,@Param("rqnama") String rqnama);

    @Transactional
    @Procedure("deleted_employee1")
    void deleted_employee1(@Param("resid") Long resid);

    @Query(value = "select * from getemployeebyid(:rqid)",nativeQuery = true)
    Object getemployeebyid(@Param("rqid") Long rqid);

}

