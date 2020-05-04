package com.yana.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest4 {
    @Test
    @DisplayName("check swap method")
    void swapGeneric() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        List<String> stringList = new ArrayList<>();
        stringList.add("yana");
        stringList.add("cool");
        stringList.add("tester");

        boolean result = com.yana.lab4.Main.swapGeneric(0, 1, integerList);
        boolean result2 = com.yana.lab4.Main.swapGeneric(0, 1, stringList);
        assertTrue(result);
        assertTrue(result2);

        assertEquals(integerList.get(1), 1);
        assertEquals(integerList.get(0), 2);

        assertEquals(stringList.get(1), "yana");
        assertEquals(stringList.get(0), "cool");
    }

    @Test
    @DisplayName("check swap method with incorrect index")
    void swapIndexOutOfBounds(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        List<String> stringList = new ArrayList<>();
        stringList.add("yana");
        stringList.add("cool");
        stringList.add("tester");

        boolean result = com.yana.lab4.Main.swapGeneric(0, 4, integerList);
        boolean result2 = com.yana.lab4.Main.swapGeneric(5, 1, stringList);
        assertFalse(result);
        assertFalse(result2);
    }

    @Test
    @DisplayName("check swap method with the same index")
    void swapTheSameIndex(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        List<String> stringList = new ArrayList<>();
        stringList.add("yana");
        stringList.add("cool");
        stringList.add("tester");

        boolean result = com.yana.lab4.Main.swapGeneric(0, 0, integerList);
        boolean result2 = com.yana.lab4.Main.swapGeneric(0, 0, stringList);
        assertTrue(result);
        assertTrue(result2);
    }

    @Test
    @DisplayName("check swap method with null")
    void swapNull(){
        assertThrows(NullPointerException.class, () -> com.yana.lab4.Main.swapGeneric(0, 0, null));
    }

}