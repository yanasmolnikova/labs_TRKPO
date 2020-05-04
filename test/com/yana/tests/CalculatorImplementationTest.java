package com.yana.tests;

import com.yana.lab6.CalculatorImplementation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorImplementationTest {

    @Test
    @DisplayName("check summ method")
    void summ() {
        CalculatorImplementation result = new CalculatorImplementation();
        assertEquals(result.summ(1, 2), 3);
        assertEquals(result.summ(60, 12), 72);
    }

    @Test
    @DisplayName("check summ method with zero")
    void summWithZero() {
        CalculatorImplementation result = new CalculatorImplementation();
        assertEquals(result.summ(0, 0), 0);
    }

}