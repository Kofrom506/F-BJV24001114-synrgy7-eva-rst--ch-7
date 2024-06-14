package com.example.batch7.ch3.junit;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MathServiceTest {

    private MathService mathService;

    @Before
    public void setUp() {
        mathService = new MathService();
        System.out.println("Setup sebelum setiap pengujian");
    }

    @After
    public void tearDown() {
        mathService = null;
        System.out.println("Membersihkan setelah setiap pengujian");
    }

    @Test
    public void testAdd() {
        int result = mathService.add(3, 5);
        Assert.assertEquals(8, result);
    }

    // Contoh pengujian lainnya
}