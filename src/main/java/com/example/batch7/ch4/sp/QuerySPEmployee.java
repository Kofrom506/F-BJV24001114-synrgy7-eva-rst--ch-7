package com.example.batch7.ch4.sp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan
public class QuerySPEmployee {

    public String getData ="CREATE OR REPLACE FUNCTION public.getemployee6()\n" +
            " RETURNS TABLE(resid bigint, resname varchar, resaddress text)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "\tbegin\n" +
            "\tRETURN QUERY\n" +
            "      select id bigint, name varchar, address from employee e;\n" +
            "\tEND;\n" +
            "$function$\n" +
            ";\n";

    public String getDataEmployeeLikeName ="CREATE OR REPLACE FUNCTION public.listemployee2(rqnama varchar)\n" +
            " RETURNS TABLE(resid bigint, resname varchar, resaddress varchar)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "           DECLARE \n" +
            "               var_r record;\n" +
            "           BEGIN\n" +
            "               FOR var_r IN(SELECT \n" +
            "                           id,\n" +
            "                           name,\n" +
            "                           address \n" +
            "                           FROM employee\n" +
            "                           WHERE name ILIKE  rqNama)  \n" +
            "               LOOP\n" +
            "                   resid :=var_r.id  ;\n" +
            "                   resname :=var_r.name;\n" +
            "                   resaddress :=var_r.address;\n" +
            "                   RETURN NEXT;\n" +
            "               END LOOP;\n" +
            "           end $function$\n" +
            ";\n";

    public String insertEmployee ="CREATE OR REPLACE PROCEDURE public.insert1(inout resid bigint,inout rqnama character varying)\n" +
            "             LANGUAGE plpgsql\n" +
            "            AS $procedure$\n" +
            "            begin\n" +
            "            if rqnama is null Then \n" +
            "              raise notice 'nama kosong';\n" +
            "             rqnama = 'nama wajib diisi';\n" +
            "             return;\n" +
            "            else \n" +
            "             raise notice 'nama  ada';\n" +
            "            end if;  \n" +
            "                 INSERT into public.employee\n" +
            "                 (id, created_date, updated_date, address, dob, \"name\", status) \n" +
            "                 SELECT nextval('employee_id_seq'),\n" +
            "                       now(),\n" +
            "                       now(),\n" +
            "                      'jakarta',\n" +
            "                      '1970-01-15',\n" +
            "                       rqnama,\n" +
            "                       'active'\n" +
            "                RETURNING id INTO resid; \n" +
            "                 commit;\n" +
            "            END;\n" +
            "            $procedure$;\n";

    public String updateEmployee = "CREATE OR REPLACE PROCEDURE public.update_employee(INOUT resid bigint, INOUT rqnama varchar)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "            begin\n" +
            "            if rqnama is null Then \n" +
            "              raise notice 'nama kosong';\n" +
            "             rqnama = 'nama wajib diisi';\n" +
            "             return;\n" +
            "            else \n" +
            "             raise notice 'nama  ada';\n" +
            "            end if;  \n" +
            "                 update employee\n" +
            "                 set name=rqnama\n" +
            "                 where id = resid;\n" +
            "               rqnama=rqnama;\n" +
//            "                 commit;\n" +  -- Tidak perlu COMMIT di sini, biarkan aplikasi Anda yang mengelola transaksi.
            "            END;\n" +
            "            $procedure$\n" +
            ";\n";

    public String deletedEmployee ="CREATE OR REPLACE PROCEDURE public.deleted_employee1(INOUT resid bigint)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $procedure$\n" +
            "            begin \n" +
            "                 update employee\n" +
            "                 set created_date =now()\n" +
            "                 where id = resid;\n" +
//            "                 commit;\n" +  -- Tidak perlu COMMIT di sini, biarkan aplikasi Anda yang mengelola transaksi.
            "            END;\n" +
            "            $procedure$\n" +
            ";\n";

    public  String getByID = "CREATE OR REPLACE FUNCTION public.getemployeebyid(rqid bigint)\n" +
            " RETURNS TABLE(resid bigint, resname character varying, resaddress text)\n" +
            " LANGUAGE plpgsql\n" +
            "AS $function$\n" +
            "\tbegin\n" +
            "\t\treturn QUERY\n" +
            "\t\tselect id, name, address from employee where id =rqid;\n" +
            "\tEND;\n" +
            "$function$\n" +
            ";\n";
}
