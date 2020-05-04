//Найдите среднее арифметическое чисел 9_000_000_000_000_000_000 8_444_444_444_444_444_444
package com.yana.lab18;

import java.math.BigInteger;

public class Main {

        public static void main(String[] args) {
            BigInteger x = new BigInteger("9000000000000000000");
            BigInteger y = new BigInteger("8444444444444444444");

            BigInteger result = x.add(y).divide(BigInteger.valueOf(2));

            System.out.println("average of 9000000000000000000 and 8444444444444444444 = " + result);
    }
}
