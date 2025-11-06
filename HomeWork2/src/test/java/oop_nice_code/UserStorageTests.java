package oop_nice_code;

import org.junit.Test;
import ru.kda.oop_nice_code.Gender;
import ru.kda.oop_nice_code.User;
import ru.kda.oop_nice_code.UserStorage;
import ru.kda.oop_nice_code.UserStorageImpl;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserStorageTests {

    //private UserStorage userStorage = null;
    private UserStorage userStorage = new UserStorageImpl();

    private static final String LOGIN = "anton";
    private static final String NAME = "Anton";
    private static final String SURNAME = "Ivanov";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 5, 20);
    private static final Gender GENDER = Gender.MALE;


    @Test
    public void userStorageTest() {
        User user = new User(LOGIN, NAME, SURNAME, BIRTH_DATE, GENDER);
        userStorage.put(user);

        User retrieved = userStorage.getUserByLogin(LOGIN);
        assertEquals(LOGIN, retrieved.getLogin());
        assertEquals(NAME, retrieved.getFirstName());
        assertEquals(SURNAME, retrieved.getLastName());
        assertEquals(BIRTH_DATE, retrieved.getBirthDate());
        assertEquals(GENDER, retrieved.getGender());
    }
    @Test
    public void testGetUserByLoginIgnoreCase() {
        User user = new User("Anton", NAME, SURNAME, BIRTH_DATE, GENDER);
        userStorage.put(user);

        User retrieved = userStorage.getUserByLogin("aNTON");
        assertNotNull(retrieved);
        assertEquals("anton", retrieved.getLogin().toLowerCase());
    }
}