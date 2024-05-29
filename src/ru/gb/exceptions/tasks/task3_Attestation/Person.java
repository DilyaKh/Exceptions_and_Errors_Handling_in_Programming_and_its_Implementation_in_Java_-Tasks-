package ru.gb.exceptions.tasks.task3_Attestation;



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


//-----------------------------------------------------------------------------