//Напишите обобщенный метод для перестановки двух элементов
// в списке по индексам. Напишите junit-тесты.

package com.yana.lab4;

import java.util.ArrayList;
import java.util.List;

public class Main<T> {

    public static <T> void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

        List<String> stringList = new ArrayList<>();
        stringList.add("yana");
        stringList.add("cool");
        stringList.add("tester");

        boolean result1 = swapGeneric(2, 1, integerList);
        boolean result2 = swapGeneric(2, 1, stringList);
        System.out.println("\n" + result1);
        System.out.println("\n" + result2);

    }

    public static <T> boolean swapGeneric(int i, int j, List<T> anArray){
        if (i >= anArray.size() || j >= anArray.size()) {
            return false;
        }

        var temp = anArray.get(i);
        anArray.set(i, anArray.get(j));
        anArray.set(j, temp);

        for (T v : anArray)
            System.out.print(v + " ");

        return true;
    }
}
