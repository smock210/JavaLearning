package ru.kda.Person;

import lombok.Getter;

public class Person implements Comparable<Person> {

    @Getter
    private final String name;
    @Getter
    private final String city;
    @Getter
    private final int age;

    public Person(String name, String city, int age) {
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }
        if (city == null) {
            throw new IllegalArgumentException("city is null");
        }
        this.name = name;
        this.city = city;
        this.age = age;
    }

    @Override
    public int compareTo(Person o) {
        int result = this.city.compareTo(o.city);
        if (result != 0) {
            return result;
        }
        return this.name.compareTo(o.name);
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

    public String toJson() {
        return String.format(
                "{\"name\":\"%s\",\"city\":\"%s\",\"age\":%d}",
                this.name, this.city, this.age
        );
    }
}
