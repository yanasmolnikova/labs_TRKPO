package com.yana.tests;

import com.yana.lab1.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("check swap method")
    void swap() {
        int[] array = {1, 2, 3, 4, 5, 6};
        boolean result = Main.swap(3, 4, array);
        assertTrue(result);
        assertEquals(array[3], 5);
        assertEquals(array[4], 4);
    }

    @Test
    @DisplayName("check swap method with incorrect index")
    void swapIndexOutOfBounds(){
        int[] array = {1, 2, 3, 4, 5, 6};
        boolean result = Main.swap(6, 7, array);
        assertFalse(result);
    }

    @Test
    @DisplayName("check swap method with the same index")
    void swapTheSameIndex(){
        int[] array = {1, 2, 3, 4, 5, 6};
        boolean result = Main.swap(3, 3, array);
        assertTrue(result);
    }

    @Test
    @DisplayName("check swap method with null")
    void swapNull(){
        assertThrows(NullPointerException.class, () -> com.yana.lab1.Main.swap(0, 0, null));
    }
}