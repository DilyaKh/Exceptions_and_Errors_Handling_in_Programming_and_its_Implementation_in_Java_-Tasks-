/* ЗАДАНИЕ 4


СИМВОЛ `a`

Напишите программу, которая принимает символ 'a' в качестве аргумента
и выполняет следующую проверку:

если символ a равен пробелу ' ', программа должна
выбрасывать исключение с сообщением "Empty string has been input.".

В противном случае программа должна возвращать сообщение
"Your input was - [символ]", где [символ] заменяется на введенный символ a.


Пример

На входе:
'0'

На выходе:
Result: Your input was - 0
*/


package ru.gb.exceptions.tasks.task2;


public class Task2_4 {
    public static void main(String[] args) {
        try {
            System.out.println(expr('0'));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        Вариант для вызова метода:
//        try {
//            System.out.println(expr(' '));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    }


    public static String expr(char a) throws Exception {
        if (a == ' ') {
            throw new Exception("Empty string has been input.");
        } else {
            return String.format("Your input was - %c", a);
        }
    }

}


//-------------------------------------------------------------------------------
