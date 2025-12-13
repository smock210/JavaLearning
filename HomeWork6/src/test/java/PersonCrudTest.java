import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kda.Person.Person;
import ru.kda.Person.PersonCrud;

import static org.junit.jupiter.api.Assertions.*;

public class PersonCrudTest {

    private PersonCrud personCrud;

    @BeforeEach
    public void setUp() {
        personCrud = new PersonCrud(); // Используем in-memory H2 базу
    }

    @Test
    public void testCreateAndReadPerson() {
        Person person = new Person("Alice", "Moscow", 30);
        int id = personCrud.create(person);

        // Проверяем, что ID корректно возвращён
        assertTrue(id > 0);

        // Читаем созданного человека
        Person retrieved = personCrud.read(id);
        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getName());
        assertEquals("Moscow", retrieved.getCity());
        assertEquals(30, retrieved.getAge());
    }

    @Test
    public void testReadNonExistentPerson() {
        Person person = personCrud.read(999); // ID, которого нет
        assertNull(person);
    }

    @Test
    public void testUpdatePerson() {
        // Сначала создаём
        Person person = new Person("Bob", "Kazan", 25);
        int id = personCrud.create(person);

        // Обновляем
        Person updatedPerson = new Person("Bob Smith", "Saint Petersburg", 26);
        boolean isUpdated = personCrud.update(id, updatedPerson);

        assertTrue(isUpdated);

        // Проверяем
        Person retrieved = personCrud.read(id);
        assertNotNull(retrieved);
        assertEquals("Bob Smith", retrieved.getName());
        assertEquals("Saint Petersburg", retrieved.getCity());
        assertEquals(26, retrieved.getAge());
    }

    @Test
    public void testUpdateNonExistentPerson() {
        Person person = new Person("NoOne", "Nowhere", 100);
        boolean result = personCrud.update(999, person); // ID не существует
        assertFalse(result);
    }

    @Test
    public void testDeletePerson() {
        // Создаём
        Person person = new Person("Charlie", "Sochi", 35);
        int id = personCrud.create(person);

        // Удаляем
        boolean isDeleted = personCrud.delete(id);
        assertTrue(isDeleted);

        // Проверяем, что больше не существует
        Person retrieved = personCrud.read(id);
        assertNull(retrieved);
    }

    @Test
    public void testDeletePersonByName() {
        // Создаём
        Person person = new Person("Charlie", "Sochi", 35);
        int id = personCrud.create(person);

        // Удаляем
        boolean isDeleted = personCrud.delete(person.getName());
        assertTrue(isDeleted);

        // Проверяем, что больше не существует
        Person retrieved = personCrud.read(person.getName());
        assertNull(retrieved);
    }

    @Test
    public void testDeleteNonExistentPerson() {
        boolean result = personCrud.delete(999); // Удаление несуществующего
        assertFalse(result);
    }
}