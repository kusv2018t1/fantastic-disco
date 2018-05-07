package com.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class helloworldTest {

    @Test
    public void testcode() {
        helloworld test = new helloworld();
        int output = test.testcode("this is test project.");
        assertEquals(21, output);
    }
}