package ru.kda.oop_nice_code;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//класс UserStorageImpl, который реализует интерфейс UserStorage.
public class UserStorageImpl implements UserStorage{

    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public User getUserByLogin(String login) {
        validateLogin(login);

        String normalizedLogin = normalizeLogin(login);
        User user = users.get(normalizedLogin);

        if (user == null) {
            throw new RuntimeException("Пользователь с логином '" + login + "' не найден");
        }

        return user;
    }

    @Override
    public User put(User user) {
        validateUser(user);

        String normalizedLogin = normalizeLogin(user.getLogin());

        // Проверяем, существует ли уже пользователь с таким логином
        if (users.containsKey(normalizedLogin)) {
            throw new RuntimeException("Пользователь с логином '" + user.getLogin() + "' уже существует");
        }

        users.put(normalizedLogin, user);
        return user;
    }

    @Override
    public User remove(String login) {
        validateLogin(login);

        String normalizedLogin = normalizeLogin(login);
        User removedUser = users.remove(normalizedLogin);

        if (removedUser == null) {
            throw new RuntimeException("Пользователь с логином '" + login + "' не найден");
        }

        return removedUser;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> getAllUsersByGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Пол не может быть равен null");
        }

        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (gender.equals(user.getGender())) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public int getUserAge(String login) {
        validateLogin(login);

        User user = getUserByLogin(login);
        LocalDate birthDate = user.getBirthDate();

        if (birthDate == null) {
            throw new RuntimeException("У пользователя с логином '" + login + "' не указана дата рождения");
        }

        LocalDate now = LocalDate.now();
        if (birthDate.isAfter(now)) {
            throw new RuntimeException("У пользователя с логином '" + login + "' дата рождения указана в будущем");
        }

        return Period.between(birthDate, now).getYears();
    }

    /**
     * Валидация логина
     */
    private void validateLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Логин не может быть пустым, null или состоять только из пробелов");
        }
    }

    /**
     * Валидация пользователя
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("Пользователь не может быть равен null");
        }

        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new RuntimeException("User login cannot be null or empty");
        }

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
    throw new RuntimeException("Имя пользователя не может быть null или пустым");
}

if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
    throw new RuntimeException("Фамилия пользователя не может быть null или пустой");
}

if (user.getGender() == null) {
    throw new RuntimeException("Пол пользователя не может быть null");
}

if (user.getBirthDate() == null) {
    throw new RuntimeException("Дата рождения пользователя не может быть null");
}

// Дополнительная проверка: дата рождения не должна быть в будущем
if (user.getBirthDate().isAfter(LocalDate.now())) {
    throw new RuntimeException("Дата рождения пользователя не может быть в будущем");
}
    }

    /**
     * Нормализация логина (приведение к нижнему регистру)
     * для обеспечения поиска
     */
    private String normalizeLogin(String login) {
        return login.trim().toLowerCase();
    }

    /**
     * Метод для получения количества пользователей в хранилище
     */
    public int getSize() {
        return users.size();
    }

    /**
     * Метод для проверки существования пользователя
     */
    public boolean containsUser(String login) {
        try {
            validateLogin(login);
            return users.containsKey(normalizeLogin(login));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
