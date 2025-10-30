package ru.kda.oop_nice_code;

import java.util.List;

/**
 * User storage.
 */
public interface UserStorage {

    /**
     * Get user by login. Notice that anton & anToN are same logins.
     *
     * @throws RuntimeException         if user not found
     * @throws IllegalArgumentException if login is null, empty or blank.
     */
    User getUserByLogin(String login);

    /**
     * Put new user into storage.
     *
     * @param user - user for store
     * @throws RuntimeException if user has wrong data, e.g. null or empty login, names, birthdate or gender.
     */
    User put(User user);

    /**
     * Remove user by login.
     *
     * @param login - login
     * @throws RuntimeException         if user not found
     * @throws IllegalArgumentException if login is null, empty or blank.
     */
    User remove(String login);

    /**
     * Get all users.
     */
    List<User> getAllUsers();

    /**
     * Get all users by gender.
     *
     * @param gender - gender
     * @throws IllegalArgumentException if gender is null
     */
    List<User> getAllUsersByGender(Gender gender);

    /**
     * Get user age.
     *
     * @param login - login
     * @throws RuntimeException         if user not found
     * @throws IllegalArgumentException if login is null, empty or blank.
     */
    int getUserAge(String login);
}