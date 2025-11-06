package ru.kda.oop_nice_code;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Contains user info.
 */
public class User {

    private String login;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    public User(String login, String firstName, String lastName, LocalDate birthDate, Gender gender) {
        if (login == null) {
            throw new IllegalArgumentException("Login must not be null");
        }
        if (firstName == null) {
            throw new IllegalArgumentException("First name must not be null");
        }
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;


    }
    public String getLogin() {
        return login;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public Gender getGender() {
        return gender;
    }
    //переопределяем метод toString
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            return "User{" +
                    "login='" + login + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", birthDate=" + (birthDate != null ? birthDate.format(formatter) : "null") +
                    '}';

    }

    // Может пригодиться а может нет: equals() и hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                gender == user.gender &&
                Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, lastName, gender, birthDate);
    }

}
