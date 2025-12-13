package Equals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import ru.kda.Equals.Person;

@DisplayName("Тест класса Person")
public class PersonTest {

    @Test
    @DisplayName("Конструктор выбрасывает исключение при null name")
    public void constructorThrowsExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(null, "Москва", 25);
        });
        assertEquals("name is null", exception.getMessage());
    }

    @Test
    @DisplayName("Конструктор выбрасывает исключение при null city")
    public void constructorThrowsExceptionWhenCityIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("Иван", null, 25);
        });
        assertEquals("city is null", exception.getMessage());
    }

    @Test
    @DisplayName("equals: объект равен самому себе")
    public void equalsReturnsTrueForSameObject() {
        Person person = new Person("Иван", "Москва", 30);
        assertTrue(person.equals(person));
    }

    @Test
    @DisplayName("equals: объект не равен null")
    public void equalsReturnsFalseForNull() {
        Person person = new Person("Иван", "Москва", 30);
        assertFalse(person.equals(null));
    }

    @Test
    @DisplayName("equals: объект не равен объекту другого класса")
    public void equalsReturnsFalseForDifferentClass() {
        Person person = new Person("Иван", "Москва", 30);
        Object other = new Object();
        assertFalse(person.equals(other));
    }

    @Test
    @DisplayName("equals: два объекта с одинаковыми полями (с учётом ignoreCase)")
    public void equalsReturnsTrueForEqualFieldsIgnoreCase() {
        Person person1 = new Person("Иван", "Москва", 30);
        Person person2 = new Person("иван", "москва", 30);
        assertTrue(person1.equals(person2));
        assertTrue(person2.equals(person1));
    }

    @Test
    @DisplayName("equals: разные имена (даже с разным регистром)")
    public void equalsReturnsFalseForDifferentNames() {
        Person person1 = new Person("Иван", "Москва", 30);
        Person person2 = new Person("Петр", "Москва", 30);
        assertFalse(person1.equals(person2));
    }

    @Test
    @DisplayName("equals: разные города")
    public void equalsReturnsFalseForDifferentCities() {
        Person person1 = new Person("Иван", "Москва", 30);
        Person person2 = new Person("Иван", "СПб", 30);
        assertFalse(person1.equals(person2));
    }

    @Test
    @DisplayName("equals: разный возраст")
    public void equalsReturnsFalseForDifferentAge() {
        Person person1 = new Person("Иван", "Москва", 30);
        Person person2 = new Person("Иван", "Москва", 31);
        assertFalse(person1.equals(person2));
    }

    @Test
    @DisplayName("hashCode: одинаковые объекты имеют одинаковый hashCode")
    public void hashCodeIsEqualForEqualObjects() {
        Person person1 = new Person("Иван", "Москва", 30);
        Person person2 = new Person("иван", "москва", 30);
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    @DisplayName("hashCode: разные объекты могут иметь разный hashCode")
    public void hashCodeDiffersForDifferentObjects() {
        Person person1 = new Person("Иван", "Москва", 30);
        Person person2 = new Person("Петр", "СПб", 25);
        // Хотя коллизии возможны, в данном случае хеш должен отличаться
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    @DisplayName("hashCode стабилен при повторных вызовах")
    public void hashCodeIsConsistent() {
        Person person = new Person("Иван", "Москва", 30);
        int first = person.hashCode();
        int second = person.hashCode();
        assertEquals(first, second);
    }

    @Test
    @DisplayName("toString содержит ожидаемые поля")
    public void toStringReturnsExpectedFormat() {
        Person person = new Person("Иван", "Москва", 30);
        String expectedStart = "класс Person{name='Иван', city='Москва', age='30'";
        assertTrue(person.toString().startsWith(expectedStart));
        assertTrue(person.toString().endsWith("'}"));
    }
}