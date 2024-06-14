package com.example.batch7.ch3.optional.sample1;

import java.util.Optional;

public class Main2 {
    public static void main(String[] args) {
        // 4. Membuat Objek kategori type
        CategoryType categoryType = new CategoryType("Iphone");
        // 3. Membuat objek kategori
        Category category = new Category("Electronics",Optional.of(categoryType));
        // 2 .Membuat objek detail produk dengan kategori
        DetailProduct detailProduct = new DetailProduct("Smartphone", Optional.of(category));
        // 1 :Membuat objek produk dengan detail produk
        Product product = new Product("iPhone", Optional.of(detailProduct));

        //A. OrElse String ?
        Optional<String> optionalValue = Optional.ofNullable(null);
        String value = optionalValue.orElse("Default Value");
        System.out.println("or else : "+value);

        // OrElse Object ?
        Optional<DetailProduct> detailProduct1 = Optional.ofNullable(detailProduct);
        DetailProduct detailProductNew = new DetailProduct("VIVO", Optional.of(category));
        DetailProduct value1 = detailProduct1.orElse(detailProductNew);
        System.out.println("or else di Objek :"+value1);


        // B . OrElseGet
        Optional<String> optionalValueGet = Optional.ofNullable(null);
        String value3 = optionalValue.orElseGet(() -> "Default Value");
        System.out.println("or else get: "+value3);

        //orelseGet versi 2
        String value4 = optionalValue.orElseGet(() ->{
            //tulis logic yang dibutuhkan
            return "Default Value dari value 4";
        });
        System.out.println("or else get value4 : "+value4);

        //orelseGet Object 3 tampa logic
        DetailProduct value5 = detailProduct1.orElseGet(()->detailProductNew);
        System.out.println("or else get object: "+value5);

        //orelseGet Object dengan logic
        DetailProduct value6 = detailProduct1.orElseGet(()->{
            // menuliskan logic
            return detailProductNew;
        });
        System.out.println("or else get object 2: "+value6);

        // C. GET
        Optional<String> optionalValue5 = Optional.of("Hello");
        String value7 = optionalValue5.get();
        System.out.println("GET="+value7);

//        GET object
        DetailProduct value8 = detailProduct1.get();
        System.out.println("GET with Object="+value8);

        //D. MAP
        Optional<String> optionalValue8 = Optional.of("Hello");
        Optional<Integer> length = optionalValue8.map(String::length);

//        Map Object ?
        Optional<String> productDescription = detailProduct1.map(DetailProduct::getDescription);
        System.out.println("Value description="+productDescription);

//        map Object 2
        Optional<Optional<Category>> categoryProduct = detailProduct1.map(DetailProduct::getCategory);
        System.out.println("Value categoryProduct="+categoryProduct);

        //map Category -> Name
        Optional<String> categoryProduct5 = detailProduct1.flatMap(DetailProduct::getCategory).map(Category::getName);
        System.out.println("Value categoryProduct5="+categoryProduct5);

        // map category -> name -> length ?
        Optional<Integer> categoryProduct6 = detailProduct1.flatMap(DetailProduct::getCategory).map(Category::getName).map(String::length);
        System.out.println("Value categoryProduct6 length="+categoryProduct6);







    }
}
