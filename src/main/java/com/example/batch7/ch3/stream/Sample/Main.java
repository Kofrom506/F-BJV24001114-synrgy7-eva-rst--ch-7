package com.example.batch7.ch3.stream.Sample;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<FoodOrder> orders = Arrays.asList(
                new FoodOrder("Nasi Goreng", 25000, 2),
                new FoodOrder("Mie Goreng", 20000, 1),
                new FoodOrder("Ayam Goreng", 30000, 3),
                new FoodOrder("Kopi Susu Keluarga", 50000, 10)
        );

        // 1. MAP
        // Menggunakan map untuk mendapatkan daftar nama makanan dari setiap pesanan
        List<String> foodNames = orders.stream()
                .map(FoodOrder::getFoodName)
                .collect(Collectors.toList());
        System.out.println("Daftar nama makanan dari setiap pesanan: " + foodNames);


        List<Double> harga = orders.stream()
                .map(FoodOrder::getPrice)
                .collect(Collectors.toList());
        System.out.println("Daftar harga makanan dari setiap pesanan: " + harga);

        //2. Flatmap Menggunakan flatMap untuk mendapatkan semua nama makanan dari semua pesanan
        List<String> allFoodNames = orders.stream()
                .flatMap(order -> {
                    String[] foodNamesArray = new String[order.getQuantity()];
                    Arrays.fill(foodNamesArray, order.getFoodName());
                    return Arrays.stream(foodNamesArray);
                })
                .sorted(Comparator.naturalOrder()) // descending reverseOrder , naturalOrder : asc
                .collect(Collectors.toList());
        System.out.println("Semua nama makanan dari semua pesanan: " + allFoodNames);

        // 3. reduce
        // Menggunakan reduce untuk menghitung total harga pesanan
        double totalOrderPrice = orders.stream()
                .mapToDouble(order -> order.getPrice() * order.getQuantity())
                .reduce(10000, Double::sum); // identity nilai awal :
        System.out.println("Total harga pesanan: " + totalOrderPrice);

        //4. Max
        // Menggunakan max untuk mencari pesanan dengan harga tertinggi
        Optional<FoodOrder> maxPriceOrder = orders.stream()
                .max((order1, order2) -> Double.compare(order1.getPrice(), order2.getPrice()));
        maxPriceOrder.ifPresent(order -> System.out.println("Pesanan dengan harga tertinggi: " + order.getFoodName()));

        // 5. FIlter
        // Menggunakan filter untuk mendapatkan pesanan dengan harga di atas 25000
        List<FoodOrder> expensiveOrders = orders.stream()
                .filter(order -> order.getPrice() > 25000)
                .sorted(Comparator.comparingDouble(FoodOrder::getPrice).reversed())
                .collect(Collectors.toList());
        System.out.println("Pesanan dengan harga di atas 25000: " + expensiveOrders);

    }

}
