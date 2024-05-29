/* ЗАДАНИЕ 1


FLOAT

Напишите программу, которая проверяет, является ли введенная текстовая строка
числом с плавающей точкой (float).

Программа пытается преобразовать введенную строку в число float,
и если это успешно, она выводит полученное число.

Если преобразование не удалось, программа выдаёт сообщение об ошибке
Your input is not a float number. Please, try again.
и возвращает специальное значение Float.NaN, чтобы обозначить ошибку.


Пример

На входе:
'3.14'
'Ivan'

На выходе:
3.14

*/


package ru.gb.exceptions.tasks.task2;


public class Task2_1 {
    public static void main(String[] args) {
//        String input = "3.14"; // Задаем строку для проверки

//        Вариант для входных значений:
        String input = "Ivan"; // Задаем строку для проверки

        System.out.println(isFloat(input));
    }


    public static float isFloat(String input) {
        try {
            return Float.parseFloat(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Your input is not a float number. Please, try again.");
            return Float.NaN;
        }
    }

}


//-------------------------------------------------------------------------------
