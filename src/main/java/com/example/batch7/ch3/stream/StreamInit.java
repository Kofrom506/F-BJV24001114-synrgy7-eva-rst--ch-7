package com.example.batch7.ch3.stream;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamInit {
    public static void main(String[] args) {
        Stream<String> stream4= Stream.of("riki","aldi","pari","nama teman saya");
        Stream<String> stream5= Arrays.asList("riki","aldi","pari").stream();
        stream5.forEach(System.out::println);
        stream4.forEach(System.out::println);

    }
}
