package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddTest {
    @Test
    public void testcode() {
        Add test = new Add();
        int output = test.add(1, 2);
        assertEquals(3, output);
    }
}
