package com.example.batch7.ch3.junit.study2;

import java.util.HashMap;
import java.util.Map;

public class Restaurant {

    private Map<String, Double> menu = new HashMap<>();
    private Map<String, Integer> cart = new HashMap<>();

    public void addMenuItem(String itemName, double price) {
        menu.put(itemName, price);
    }

    public void addToCart(String itemName, int quantity) {
        if (menu.containsKey(itemName)) {
            cart.put(itemName, cart.getOrDefault(itemName, 0) + quantity);
        }
    }

    public void removeFromCart(String itemName, int quantity) {
        if (cart.containsKey(itemName)) {
            int updatedQuantity = cart.get(itemName) - quantity;
            if (updatedQuantity > 0) {
                cart.put(itemName, updatedQuantity);
            } else {
                cart.remove(itemName);
            }
        }
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += menu.get(itemName) * quantity;
        }
        return totalPrice;
    }

    public boolean isItemAvailable(String itemName) {
        return menu.containsKey(itemName);
    }

    public int getItemStock(String itemName) {
        return menu.containsKey(itemName) ? cart.get(itemName) : 0;
    }

    // Metode lainnya untuk pengelolaan menu dan keranjang belanja
}

