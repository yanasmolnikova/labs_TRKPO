package com.yana.tests;

import com.yana.lab2.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest2 {
    @Test
    @DisplayName("check min method")
    void min() {
        assertEquals(Main.min(1, 2, 3), 1);
        assertEquals(Main.min(0, 0, 0), 0);
    }
}