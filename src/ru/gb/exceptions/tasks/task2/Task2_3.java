/* ЗАДАНИЕ 3


ДЕЛЕНИЕ

Напишите программу для выполнения арифметической операции
деления двух целых чисел a и b.

При этом программа должна проверить, что делитель b не равен нулю,
и выполнить деление только в этом случае.

Если b равен нулю, программа должна вернуть результат равный нулю.
После выполнения операции деления, программа также должна
вывести сумму чисел a и b с помощью метода printSum.

Если аргументы не переданы через командную строку,
используйте значения по умолчанию.


Пример

На входе:
 '12'
 '5'

На выходе:
 17
 2.4
*/


package ru.gb.exceptions.tasks.task2;


public class Task2_3 {
    public static void main(String[] args) {
        System.out.println(expr());

//        Вариант для вызова методов:
//        int a = 12;
//        int b = 5;
//        System.out.println(expr(a, b));

//        Вариант для вызова методов:
//        int a =  12;
//        int b = 0;
//        System.out.println(expr(a, b));

    }


    public static double expr() {
        // Аргументы, которые передаются по умолчанию:
        int a = 90;
        int b = 3;
        return expr(a, b);
    }

    public static double expr(int a, int b) {
        printSum(a, b);

        if (b == 0) {
            return 0.0;
        }
        else {
            return (double) a / b;
        }
    }


    public static void printSum(int a, int b) {
        int sum_result = a + b;
        System.out.println(sum_result);
    }

}


//-----------------------------------------------------------------------------------
