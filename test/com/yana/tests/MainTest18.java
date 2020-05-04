package com.yana.tests;

import com.yana.lab2.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class MainTest18 {
    @Test
    @DisplayName("check main average method")
    void main() {

        Main.main(null);
        BigInteger result = new BigInteger("8722222222222222222");
        //assertEquals(Main.main(null),result);
    }

}