package com.example.batch7.ch3.lamda;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExLamda {
    public static void main(String[] args) {
        //1. lamda tampa parameter
        Runnable runnable = () -> {
            System.out.println("Hello, World!");
        };

        //Tipe String -> Optional
        Optional<String> optionalValueGet = Optional.ofNullable(null);
        String value3 = optionalValueGet.orElseGet(() -> "Default Value");

        // return value yang kita set
        Supplier<Integer> integerLamda = () -> 1;
        // cara mendapatkan dari supplier?
        System.out.println(integerLamda.get());

        //2. lamda 1 parameter
        // Consumer == void
        Consumer<String> myName = (myname)-> {
            System.out.println("my name ="+myname);
        };
        //call consumer
        myName.accept("Sabili");

       //3. Lamda 2 parameter
        BiConsumer<Integer, Integer> sample1 = (x,y) -> System.out.println("nilai x + y="+(x+y));
        BiConsumer<Integer, Integer> sample2 = (x,y)->{
            System.out.println("nilai ke 2 x + y="+(x+y));
        };
        sample1.accept(1,1);
        sample2.accept(1,2);


    }
}
