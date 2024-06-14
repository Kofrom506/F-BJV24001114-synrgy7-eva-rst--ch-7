package com.example.batch7.ch4.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Data
public class Config {

    public  static String APP_NAME ="name";

    public static  String yourName(){
        return "riki";
    }

    public String convertDateToString(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public  String code ="status";

    public  String message= "message";

}
