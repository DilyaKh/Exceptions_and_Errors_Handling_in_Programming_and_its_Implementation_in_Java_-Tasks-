package ru.gb.exceptions.tasks.task3_Attestation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



//Класс, который содержит данные о человеке:
class Person {
    private String surname;
    private String name;
    private String patronymic;
    private String dateOfBirth;
    private String phoneNumber;
    private String gender;


    public Person(String surname, String name, String patronymic, String dateOfBirth, String phoneNumber, String gender) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }


    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }


    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic + " " + dateOfBirth + " " + phoneNumber + " " + gender;
    }
}



//----------------------------------

//КЛАССЫ ПОЛЬЗОВАТЕЛЬСКИХ ИСКЛЮЧЕНИЙ


class InputException extends RuntimeException {
    public InputException(String message) {
        super(message);
    }
}


class InvalidComponentsNumberException extends InputException {
    public InvalidComponentsNumberException(String message) {
        super(message);
    }
}

class ComponentsNotFoundException extends InputException {
    public ComponentsNotFoundException(String message) {
        super(message);
    }
}


class InvalidDateException extends InputException {
    public InvalidDateException(String message) {
        super(message);
    }
}


class UnknownInputFormatException extends InputException {
    private final List<String> unknownFormats;

    public UnknownInputFormatException(List<String> unknownFormats) {
        super("Введены компоненты, не соответствующие ни одному из требуемых форматов:\n" + unknownFormats);
        this.unknownFormats = new ArrayList<>(unknownFormats);
    }

    public List<String> getUnknownFormats() {
        return unknownFormats;
    }
}


class FileException extends Exception {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}

//----------------------------------



class UserInterface {

    private static final Pattern FIO_PATTERN = Pattern.compile("(?<=\\s|^)([А-Яа-яA-Za-z]+)(?=\\s|$)");
    private static final Pattern DATE_OF_BIRTH_PATTERN = Pattern.compile("(0[1-9]|[1-2][0-9]|[3][0-1])\\.(0[1-9]|1[0-2])\\.(19[0-9][0-9]|20[0-9][0-9])");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d+");

//    Учитываем, что пользователь может случайно ввести для обозначения пола
//    M как на кириллице, так и на латинице:
    private static final Pattern GENDER_PATTERN = Pattern.compile("[fmмFMМ]");
    private static final Set<String> REQUIRED_KEYS = Set.of("surname", "name", "patronymic", "dateOfBirth", "phoneNumber", "gender");



//    Метод, осуществляющий получение данных о человеке.
//    Возвращает объект класса Person.
    public static Person getUserInput() throws InvalidComponentsNumberException, InvalidDateException {

//        -----------------------------------------------------------------
//        Вариант, при котором входные данные запрашиваются у пользователя:

        Scanner scanner = new Scanner(System.in);
        System.out.print("Нужно ввести Фамилию, Имя, Отчество, дату рождения, номер телефона, пол (разделяя пробелами).\n" +
                        "(можно в ПРОИЗВОЛЬНОМ ПОРЯДКЕ):\n" +
                        "-------------------------\n" +
                        "Форматы данных для ввода:\n\n" +
                        "Фамилия, Имя, Отчество - строки\n" +
                        "дата рождения - строка формата dd.mm.yyyy\n" +
                        "номер телефона - целое беззнаковое число без форматирования\n" +
                        "пол - символ латиницей f или m\n\n" +
                        "ВВЕСТИ ЗДЕСЬ --->: ");
        String input = scanner.nextLine();
        scanner.close();
//        -----------------------------------------------------------------


//        -----------------------------------------------------------------
//        Варианты, при которых входные данные заданы заранее
//        (для тестирования работы программы при различных входных данных):

//        String input = "Мартынов Игорь Михайлович 22.08.1998 89047552301 m";
//        String input = "19.08.1998 Иванов Алексей Петрович m 89047552301";
//        String input = "89047552323 f Маркова 06.08.1998 Анна Георгиевна";
//        String input = "89047442323 Иванов m А П 07.08.1998";
//        String input = "m Говоров 89047442323 07.08.1998 А П ";
//        String input = "m    Weber     Mike   89047442323    Stone     07.08.1998";
//        String input = "андреева f 89047442355 анна 07.08.1997 о";
//        String input = "АНДРЕЕВА f 89047442355 Анна 07.08.1997 олеговна";
//        String input = "f alekseeva darya 89047442355 k 07.08.1997";
//        String input = "f  Alekseeva      89047442355       Mariya L 11.12.2023";
//        String input = "  AleksEEva F 89047442355 MariyA l 27.05.2024  ";
//        String input = "  Мур F     89047442355 MariyA 27.05.2024 ол";
//        -----------------------------------------------------------------


//        ВАЖНОЕ допущение:
//        Исходим из того, что Фамилия, Имя и Отчество - это непрерывные последовательности
//        буквенных символов, отделяемые от других компонентов входящей строки пробелами.
//        При этом предполагаем, что первое вхождение, удовлетворяющее таким условиям, -
//        это Фамилия, второе - это Имя, третье - это Отчество.

//        При этом Фамилия, Имя, Отчество могут быть введены в любом регистре.
//        В программе предусмотрено, что при записи в файл Фамилия, Имя, Отчество
//        будут приведены к формату, когда первая буква каждого слова - заглавная,
//        а оcтальные - строчные.



        validateComponentsNumber(input);

        Map<String, String> userData = new HashMap<>();


        List<String> snp_found  = new ArrayList<>();
        List<String> unknown_formats_found  = new ArrayList<>();


//        Разбивая на компоненты исходную строку, поданную на вход,
//        учитываем случаи, когда компоненты исходной строке на входе
//        могут быть разделены более чем одним пробелом:
        for (String part : input.strip().split("\\s+")) {
            if (matchesPattern(FIO_PATTERN, part) && !matchesPattern(GENDER_PATTERN, part)) {
                snp_found.add(part);
            } else if (matchesPattern(DATE_OF_BIRTH_PATTERN, part)) {
                try {
                    isDateNotAfterToday(part);
                    userData.put("dateOfBirth", part);
                } catch (DateTimeParseException e) {
                }
            } else if (matchesPattern(PHONE_NUMBER_PATTERN, part)) {
                userData.put("phoneNumber", part);
            } else if (matchesPattern(GENDER_PATTERN, part)) {
//                Приводим к нижнему регистру для обеспечения
//                единообразия записи в файл обозначения пола:
                userData.put("gender", part.toLowerCase());
            } else {
                unknown_formats_found.add(part);
            }
        }


        if (!snp_found.isEmpty()) {
            userData.put("surname", capitalizeFirstLetter(snp_found.getFirst()));
        }
        if (snp_found.size() >= 2) {
            userData.put("name", capitalizeFirstLetter(snp_found.get(1)));
        }
        if (snp_found.size() >= 3) {
            userData.put("patronymic", capitalizeFirstLetter(snp_found.get(2)));
        }


        checkForUnknownFormats(unknown_formats_found);

        validateComponentsNotFound(userData);



        String surname = userData.get("surname");
        String name = userData.get("name");
        String patronymic = userData.get("patronymic");
        String dateOfBirth = userData.get("dateOfBirth");
        String phoneNumber = userData.get("phoneNumber");
        String gender = userData.get("gender");


        return new Person(surname, name, patronymic, dateOfBirth, phoneNumber, gender);
    }



//    Метод проверяет, соответствует ли строка паттерну регулярного выражения.

//    На входе - первый аргумент - паттерн, второй аргумент - строка.
//    На выходе - true, строка соответствует, false - если не соответствует.
    private static boolean matchesPattern(Pattern pattern, String part) {
        Matcher matcher = pattern.matcher(part);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }



//    Метод проверяет, были ли введено на входе корректное количество компонентов.
//    Если количество меньше или больше требуемого, выбрасывается
//    соотвествующее иселючение с сообщением.
//    Если количество компонентов корректное, то пользователю
//    выводится соотвествующее сообщение.

//    На входе - строка с исходными данными, введенная на входе.
    public static void validateComponentsNumber(String input) throws InvalidComponentsNumberException {
        int components_num_code = 0;
        int correct_components_number = REQUIRED_KEYS.size();

//    Учитываем случаи, когда компоненты  исходной строке на входе
//    могут быть разделены более чем одним пробелом:
        String[] components = input.strip().split("\\s+");

        if (components.length < correct_components_number) {
            components_num_code = -1;
        } else if (components.length > correct_components_number) {
            components_num_code = 1;
        }


        if (components_num_code == -1) {
            System.out.println("Код ошибки равен: " + components_num_code);
            throw new InvalidComponentsNumberException("Введено меньше требуемых " + correct_components_number +
                    " компонентов: было введено " + components.length);
        } else if (components_num_code == 1) {
            System.out.println("Код ошибки равен: " + components_num_code);
            throw new InvalidComponentsNumberException("Введено больше требуемых " + correct_components_number +
                    " компонентов: было введено " + components.length);
        } else {
            System.out.println("Введено корректное количество компонентов: " + correct_components_number);
        }
    }



//    Метод проверяет, были ли обнаружены среди компонентов на входе
//    такие компоненты, которые не соответстуют ни одному из требуемых форматов.

//    На входе - список List<String>, в который ранее сохранялись строковые компоненты,
//    введенные на входе, которые не соответствуют требуемым форматам.
//    Если такой список не будет пуст, то будет выбрасываться исключение,
//    в котором предусмотрен вывод таких компонентов с нераспознанными форматами.
    public static void checkForUnknownFormats(List<String> input) throws UnknownInputFormatException {
        if (!input.isEmpty()) {
            throw new UnknownInputFormatException(input);
        }
    }



//    Метод проверяет, были ли введены на входе все требуемые компоненты.
//    Если были введены не все требуемые компоненты, то выбрасывается
//    соотвествующее исключение с сообщением,
//    в т.ч. выводится, какие требуемые компоненты не были введены.

//    На входе - Map<String, String>, содержащий данные, введенные на входе,
//    в формате ключ(название параметра)-значение.
    public static void validateComponentsNotFound(Map<String, String> userData) throws ComponentsNotFoundException {
        if (userData.size() != REQUIRED_KEYS.size()) {
            List<String> components_not_found = new ArrayList<>();
            for (String key : REQUIRED_KEYS) {
                if (!userData.containsKey(key)) {
                    components_not_found.add(key);
                }
            }
            throw new ComponentsNotFoundException("Не введены данные для параметров: " + components_not_found);
        }
    }



//    Метод проверяет, не является ли дата более поздней, чем сегодняшний день.
//    Если дата является более поздней, чем сегодняшний день,
//    будет вызывано исключение.

//    На входе - дата, представленная как строка
//    (внутри метода она далее преобразуется в формат даты).
    public static void isDateNotAfterToday(String dateString) throws InvalidDateException {
        LocalDate dateToCheck = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        if (!dateToCheck.isBefore(LocalDate.now())) {
            throw new InvalidDateException("Некорректный ввод даты " + dateString +
                    " (дата рождения не может быть позже сегодняшнего дня)");
        }
    }



//    Метод приводит строку к формату, когда первая буква каждого слова - заглавная,
//    а оcтальные - строчные.
//    Это нужно, т.к. на входе Фамилия, Имя, Отчество могут быть введены в любом регистре.
//    И программа должна предусмотреть, что при записи в файл Фамилия, Имя, Отчество
//    будут приводиться к формату, когда первая буква каждого слова - заглавная,
//    а оcтальные - строчные.

//    На входе - подается строка.
//    На выходе - строка с примененным изменением формата.
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        char firstChar = str.charAt(0);
        String remainingChars = str.substring(1);

        return String.valueOf(firstChar).toUpperCase() + remainingChars.toLowerCase();
    }



//    Метод осуществляет запись данных о человеке в файл с названием, равным фамилии.
//    В одну строку записываются данные вида
//    <Фамилия> <Имя> <Отчество> <дата рождения> <номертелефона> <пол>

//    Однофамильцы записываются в один и тот же файл, в отдельные строки.
//    При возникновении проблемы с чтением-записью в файл, пробрасывается исключение,
//    также пользователь увидит стектрейс ошибки.

//    На входе - объект класса Person.
    public static void saveToFile(Person person) throws FileException {
        String fileName = person.getSurname() + ".txt";
        File file = new File(fileName);

        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write((person.toString() + "\n").getBytes());
        } catch (IOException e) {
            throw new FileException("Ошибка записи в файл: " + fileName, e);
        }
    }



    public static void main(String[] args) {
        try {
            Person person = UserInterface.getUserInput();
            UserInterface.saveToFile(person);
            System.out.println("Данные успешно сохранены в файл!");
        } catch (InputException e) { // Ловим все исключения, наследующиеся от InputException
            System.out.println(e.getClass().getName());
            System.out.println("Ошибка ввода: " + e.getMessage());
            e.printStackTrace();;
        } catch (FileException e) {
            System.out.println(e.getClass().getName());
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
            e.printStackTrace();
        }
    }

}


//-----------------------------------------------------------------------------------------------
