package ru.kda.Equals;

public class Person {
    private final String name;
    private final String city;
    private final int age;


    public Person(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        if (city == null) {
            throw new IllegalArgumentException("city is null");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return name.equalsIgnoreCase(person.name) && city.equalsIgnoreCase(person.city) && age==person.age;

    }
    @Override
    public int hashCode() {
        int total = 19;
        return total + name.toLowerCase().hashCode() + city.toLowerCase().hashCode() + Integer.hashCode(age);

    }


    @Override
    public String toString() {
        return "класс Person{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
