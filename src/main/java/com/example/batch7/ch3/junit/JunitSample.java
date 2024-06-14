package com.example.batch7.ch3.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class JunitSample {

    private MathService mathService = new MathService();
    @Before
    public void init(){
        System.out.println("akan diajalankan pertama kali");
        //define value yang kita inginkan
    }

    @Before
    public void init2(){
        System.out.println("akan diajalankan pertama kali 2");
    }


    @Test
    public void testAdd() {
        int result = mathService.add(3, 5);// 8
        Assert.assertEquals(87, result); // positif apa negatif ?
        System.out.println("cetak test add="+result);

    }

    @After
    public void afterSample(){
        System.out.println("akan diajalankan terakhir kali 2");
        // value dafaule ,
    }

    @Test
    public  void test2(){
        // kode program testing
    }
}
