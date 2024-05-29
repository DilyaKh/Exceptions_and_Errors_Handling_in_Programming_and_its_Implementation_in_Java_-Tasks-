/* ЗАДАНИЕ 3


ЧАСТНОЕ ДВУХ МАССИВОВ

Реализуйте метод divArrays, принимающий в качестве аргументов
два целочисленных массива a и b, и возвращающий новый массив с,
каждый элемент которого равен частному элементов
двух входящих массивов в той же ячейке.

Если длины массивов не равны - верните нулевой массив длины 1.

Важно: При выполнении метода единственное исключение,
которое пользователь может увидеть - RuntimeException, т.е. ваше.

Метод divArrays принимает на вход два параметра:

int[] a - первый массив
int[] b - второй массив


Пример

a = new int[]{12, 8, 16};
b = new int[]{4, 2, 4};

Вывод: [3, 4, 4]


a = new int[]{12, 8, 16, 25};
b = new int[]{4, 2, 4};

Вывод: [0]
*/


package ru.gb.exceptions.tasks.task1;

import java.util.Arrays;

public class Task1_3 {
    public static void main(String[] args) {
        int[] a = new int[] {12, 8, 16};
        int[] b = new int[] {4, 2, 4};

//        Вариант для входных значений:
//        int[] a = new int[] {12, 8, 16, 25};
//        int[] b = new int[] {4, 2, 4};

//        Вариант для входных значений:
//        int[] a = new int[] {12, 8, 16, 25};
//        int[] b = new int[] {4, 2, 4, 0};


        int[] result = divArrays(a, b);
        System.out.println(Arrays.toString(result));
    }

    public static int[] divArrays(int[] a, int[] b) throws RuntimeException {
        if (a.length != b.length) {
            return new int[1];
        }

        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            try {
                result[i] = a[i] / b[i];
            } catch (ArithmeticException e) {
                throw new RuntimeException("Деление на ноль в позиции по индексу: " + i);
            }
        }
        return result;
    }

}


//------------------------------------------------------------------------------
