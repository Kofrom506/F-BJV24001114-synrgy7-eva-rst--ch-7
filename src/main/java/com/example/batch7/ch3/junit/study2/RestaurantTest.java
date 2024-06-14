package com.example.batch7.ch3.junit.study2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

;

public class RestaurantTest {

    private Restaurant restaurant;

    @Before
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.addMenuItem("Nasi Goreng", 25000);
        restaurant.addMenuItem("Mie Goreng", 20000);
    }

    @Test
    public void testAddToCart() {
        restaurant.addToCart("Nasi Goreng", 2);
        Assert.assertEquals(2, restaurant.getItemStock("Nasi Goreng"));
    }

    @Test
    public void testRemoveFromCart() {
        restaurant.addToCart("Nasi Goreng", 2);
        restaurant.removeFromCart("Nasi Goreng", 1);
        Assert.assertEquals(1, restaurant.getItemStock("Nasi Goreng"));
    }

    @Test
    public void testCalculateTotalPrice() {
        restaurant.addToCart("Nasi Goreng", 2);
        restaurant.addToCart("Mie Goreng", 3);
        Assert.assertEquals(5 * 25000 + 3 * 20000, restaurant.calculateTotalPrice());
    }

    // Unit tests for other cases...
}