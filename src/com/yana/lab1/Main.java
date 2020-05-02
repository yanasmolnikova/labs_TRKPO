//реализуйте метод boolean swap(int i, int j, int[] anArray),
// меняющий местами элементы по индексам i и j в массиве anArray
// и возвращающий false, если индексы выходят за пределы массива
package com.yana.lab1;

public class Main {
    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4, 5, 6};
        boolean result = swap(2, 1, array);
        System.out.println("\n" + result);
    }

    public static boolean swap(int i, int j, int[] anArray) {
        if (i >= anArray.length || j >= anArray.length) {
            return false;
        }

        int r = anArray[i];
        anArray[i] = anArray[j];
        anArray[j] = r;

        for (int v : anArray)
            System.out.print(v + " ");

        return true;

    }
}

