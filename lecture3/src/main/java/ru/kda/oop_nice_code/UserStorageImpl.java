package ru.kda.oop_nice_code;

import java.util.List;
//TODO Напишите класс UserStorageImpl, который реализует интерфейс UserStorage.
public class UserStorageImpl implements UserStorage{
    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public User put(User user) {
        return null;
    }

    @Override
    public User remove(String login) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public List<User> getAllUsersByGender(Gender gender) {
        return List.of();
    }

    @Override
    public int getUserAge(String login) {
        return 0;
    }
}
