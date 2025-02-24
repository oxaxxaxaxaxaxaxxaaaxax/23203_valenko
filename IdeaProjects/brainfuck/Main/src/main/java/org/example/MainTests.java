package org.example;

import org.junit.*;

public class MainTests {
    @Test // считается тестом
    public void test1(){
        Assert.assertEquals(2+2, 4);
    }
}
