package com.example.batch7.ch3.lamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MethodReference {
    public static void main(String[] args) {
        // Lambda expression
        Consumer<String> print1 = str -> System.out.println(str);
        // Method reference
        Consumer<String> print2 = System.out::println;

        // return value yang kita set
        Supplier<Integer> integerLamda = () -> 1;
        // Method reference ?


        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        // Lambda expression
                names.forEach(name -> System.out.println(name));
        // Method reference
                names.forEach(System.out::println);





    }
}
