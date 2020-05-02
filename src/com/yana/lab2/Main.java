//реализуйте метод int min(int a, int b, int c), находящий минимальный из трех аргументов

package com.yana.lab2;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        System.out.println(min(1,2,3));
    }

    public static int min(int a, int b, int c) {
        ArrayList<Integer> items = new ArrayList<>();
        items.add(a);
        items.add(b);
        items.add(c);

        return Collections.min(items);
    }
}