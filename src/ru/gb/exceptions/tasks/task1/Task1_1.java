/* ЗАДАНИЕ 1


МЕТОДЫ С ИСКЛЮЧЕНИЯМИ

Реализуйте 3 метода, чтобы в каждом из них получить разные исключения.

Метод arrayOutOfBoundsException - Ошибка, связанная с выходом за пределы массива

Метод divisionByZero - Деление на 0

Метод numberFormatException - Ошибка преобразования строки в число

Важно: они не должны принимать никаких аргументов
*/


package ru.gb.exceptions.tasks.task1;


public class Task1_1 {
   public static void main(String[] args) {
       arrayOutOfBoundsException();
//        divisionByZero();
//        numberFormatException();
   }


   public static void arrayOutOfBoundsException() {
       int[] arr1 = {1, 2, 3, 4};
       System.out.println(arr1[8]); // Выход за пределы массива
   }


   public static void divisionByZero() {
       int result = 10 / 0; // Деление на ноль
   }


   public static void numberFormatException() {
       String string = "Hello";
       int number = Integer.parseInt(string); // Ошибка преобразования строки в число
   }

}


//-----------------------------------------------------------------------------------------