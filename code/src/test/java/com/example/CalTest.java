package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalTest {

    @Test
    public void addTest() {
        Calculator cal = new Calculator();
        assertEquals(3, cal.add(1, 2));
    }

}
