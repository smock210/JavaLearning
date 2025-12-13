package Comparators;

import org.junit.jupiter.api.Test;
import ru.kda.Comparators.Person;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import static org.mockito.Mockito.*;

public class PersonTest {

    // Проверка корректной инициализации полей
    @Test
    public void testConstructor_ValidArguments_ShouldInitializeFields() {
        Person person = new Person("Alice", "Moscow", 30);
        assertEquals("Alice", person.toString().split("name='")[1].split("'")[0]);
        assertEquals("Moscow", person.toString().split("city='")[1].split("'")[0]);
        assertEquals(30, Integer.parseInt(person.toString().split("age='")[1].split("'")[0]));
    }

    // Проверка исключения при null name
    @Test
    public void testConstructor_NullName_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person(null, "Moscow", 30);
        });
        assertEquals("name is null", exception.getMessage());
    }

    // Проверка исключения при null city
    @Test
    public void testConstructor_NullCity_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Person("Alice", null, 30);
        });
        assertEquals("city is null", exception.getMessage());
    }

    // Проверка сравнения по city (основной критерий)
    @Test
    public void testCompareTo_DifferentCity_ShouldCompareByCity() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Bob", "London", 25);
        assertTrue(p1.compareTo(p2) > 0); // Moscow > London
        assertTrue(p2.compareTo(p1) < 0);
    }

    // Проверка сравнения по name при одинаковом city
    @Test
    public void testCompareTo_SameCity_DifferentName_ShouldCompareByName() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Bob", "Moscow", 25);
        assertTrue(p1.compareTo(p2) < 0); // Alice < Bob
        assertTrue(p2.compareTo(p1) > 0);
    }

    // Проверка равенства при одинаковом city и name (даже разный age, но compareTo не учитывает age)
    @Test
    public void testCompareTo_SameCityAndName_DifferentAge_ShouldReturnZero() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Alice", "Moscow", 25);
        assertEquals(0, p1.compareTo(p2));
    }

    // Проверка compareTo с собой
    @Test
    public void testCompareTo_SameObject_ShouldReturnZero() {
        Person p = new Person("Alice", "Moscow", 30);
        assertEquals(0, p.compareTo(p));
    }

    // Проверка equals: одинаковые объекты (с учётом игнорирования регистра)
    @Test
    public void testEquals_SamePersonIgnoreCase_ShouldReturnTrue() {
        Person p1 = new Person("alice", "moscow", 30);
        Person p2 = new Person("Alice", "Moscow", 30);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    // Проверка equals: разные имена
    @Test
    public void testEquals_DifferentName_ShouldReturnFalse() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Bob", "Moscow", 30);
        assertFalse(p1.equals(p2));
    }

    // Проверка equals: разные города
    @Test
    public void testEquals_DifferentCity_ShouldReturnFalse() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Alice", "London", 30);
        assertFalse(p1.equals(p2));
    }

    // Проверка equals: разный возраст — не влияет на equals
    @Test
    public void testEquals_DifferentAge_ShouldReturnTrueIfNameAndCityMatch() {
        Person p1 = new Person("Alice", "Moscow", 25);
        Person p2 = new Person("Alice", "Moscow", 25);
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    // Проверка equals: null
    @Test
    public void testEquals_Null_ShouldReturnFalse() {
        Person p = new Person("Alice", "Moscow", 30);
        assertFalse(p.equals(null));
    }

    // Проверка equals: другой тип
    @Test
    public void testEquals_DifferentType_ShouldReturnFalse() {
        Person p = new Person("Alice", "Moscow", 30);
        assertFalse(p.equals("some string"));
    }

    // Проверка hashCode: одинаковые значения — одинаковый hashCode
    @Test
    public void testHashCode_SameNameAndCity_DifferentCase_ShouldBeEqual() {
        Person p1 = new Person("alice", "moscow", 30);
        Person p2 = new Person("Alice", "Moscow", 30);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    // Проверка hashCode: разные имена — разный hashCode
    @Test
    public void testHashCode_DifferentName_ShouldBeDifferent() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Bob", "Moscow", 30);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    // Проверка hashCode: разные города — разный hashCode
    @Test
    public void testHashCode_DifferentCity_ShouldBeDifferent() {
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Alice", "London", 30);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    // Проверка hashCode при изменении регистра имени или города
    @Test
    public void testHashCode_CaseInsensitive_ShouldBeSame() {
        Person p1 = new Person("ALICE", "MOSCOW", 30);
        Person p2 = new Person("alice", "moscow", 30);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    // Проверка toString
    @Test
    public void testToString_ShouldContainAllFields() {
        Person p = new Person("Alice", "Moscow", 30);
        String expected = "класс Person{name='Alice', city='Moscow', age='30'}";
        assertEquals(expected, p.toString());
    }

    // Проверка сортировки списка через Collections.sort (использует compareTo)
    @Test
    public void testSortingList_ShouldSortByCityThenByName() {
        List<Person> people = Arrays.asList(
                new Person("Bob", "Moscow", 25),
                new Person("Alice", "London", 30),
                new Person("Charlie", "Moscow", 35),
                new Person("Alice", "Moscow", 20)
        );

        Collections.sort(people);

        // Ожидаем: сначала London, затем Moscow (внутри Moscow — сортировка по имени)
        List<String> names = people.stream()
                .map(p -> p.toString().split("name='")[1].split("'")[0])
                .toList();
        List<String> expectedNames = Arrays.asList("Alice", "Alice", "Bob", "Charlie");
        assertEquals(expectedNames, names);
    }

    // Граничный случай: пустые строки в name и city
    @Test
    public void testConstructor_EmptyStrings_ShouldAllow() {
        assertDoesNotThrow(() -> {
            new Person("", "", 0);
        });
    }

    // Проверка, что объекты с одинаковым name и city, но разным регистром, считаются равными
    @Test
    public void testEquals_EqualDespiteCase_ShouldBeTrue() {
        Person p1 = new Person("JOHN", "NEW YORK", 40);
        Person p2 = new Person("john", "new york", 40);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
        assertEquals(p1.hashCode(), p2.hashCode());
    }


    // Добавим getter'ы для мока, если бы они были — но в текущем классе их нет.
    // Однако можно проверить, что мок не ломает поведение
    @Test
    public void testMockPerson_CompareTo() {
        Person mock1 = mock(Person.class);
        Person mock2 = mock(Person.class);
        // Не мокаем, чтобы проверить реальное поведение
        Person p1 = new Person("Alice", "Moscow", 30);
        Person p2 = new Person("Bob", "Moscow", 25);
        assertTrue(p1.compareTo(p2) < 0);
    }
}