/* ЗАДАНИЕ 2


РАЗНОСТЬ ДВУХ МАССИВОВ

Реализуйте метод subArrays, принимающий в качестве аргументов
два целочисленных массива a и b, и возвращающий новый массив c,
каждый элемент которого равен разности элементов
двух входящих массивов в той же ячейке.

Если длины массивов не равны - верните нулевой массив длины 1.

Метод subArrays принимает на вход два параметра:

int[] a - первый массив
int[] b - второй массив


Пример

a = new int[]{4, 5, 6};
b = new int[]{1, 2, 3};

Вывод: [3, 3, 3]


a = new int[]{4, 5, 6};
b = new int[]{1, 2, 3, 5};

Вывод: [0]
*/


package ru.gb.exceptions.tasks.task1;

import java.util.Arrays;


public class Task1_2 {
    public static void main(String[] args) {
        int[] a = new int[] {4, 5, 6};
        int[] b = new int[] {1, 2, 3};

//        Вариант для входных значений:
//        int[] a = new int[] {4, 5, 6};
//        int[] b = new int[] {1, 2, 3, 5};


        int[] result = subArrays(a, b);
        System.out.println(Arrays.toString(result));
    }

    public static int[] subArrays(int[] a, int[] b) {
        if (a.length != b.length) {
            return new int[1];
        }

        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

}


//---------------------------------------------------------------------------
