//package com.example.batch7.ch3.optional.sample1;
//
//
//import java.util.Optional;
//
//public class Main {
//    public static void main(String[] args) {
//        // Membuat objek kategori
//        Category category = new Category("Electronics");
//        // Membuat objek detail produk dengan kategori
//        DetailProduct detailProduct = new DetailProduct("Smartphone", Optional.of(category));
//        // Membuat objek produk dengan detail produk
//        Product product = new Product("iPhone", Optional.of(detailProduct));
////        Product product = new Product();
//
//        // Mendapatkan nama produk
//        String productName = product.getName();
//        System.out.println("Product Name: " + productName);
//
//
//        // Mendapatkan deskripsi produk
//        String productDescription = product.getDetailProduct()
//                .map(DetailProduct::getDescription)
//                .orElse("No description available");
//        System.out.println("Product Description: " + productDescription);
//
//        // Mendapatkan nama kategori (jika tersedia)
//        String categoryName = product.getDetailProduct()
//                .flatMap(DetailProduct::getCategory)
//                .map(Category::getName)
//                .orElse("No category available");
//        System.out.println("Category Name: " + categoryName);
//    }
//}