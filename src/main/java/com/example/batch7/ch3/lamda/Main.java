package com.example.batch7.ch3.lamda;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<People> people = new ArrayList<>();
        people.add(new People("Alice"));
        people.add(new People("Bob"));
        people.add(new People("Charlie"));
        people.add(new People("Name1"));
        people.add(new People("name2"));
        
        for(People p : people){
            //cetak eksekusi
        }

        List<String> stringName = new ArrayList<>();
        // ini dilakukan cetak value dari data looping
        people.forEach(peopleSample -> {
            // ekseskusi disini
            System.out.println("data name="+peopleSample.getName());
            stringName.add(peopleSample.getName());
        });


        //Method reference : di obejct
        people.forEach(People::getName);

        //removeIF
        people.removeIf(peopleSample-> peopleSample.getName().length() > 3);

    }
}
