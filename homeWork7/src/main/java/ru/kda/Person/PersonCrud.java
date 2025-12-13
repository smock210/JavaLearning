package ru.kda.Person;

import java.sql.*;

public class PersonCrud {



        private final String jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        private final String username = "sa";
        private final String password = "";

        public PersonCrud() {
            createTableIfNotExists();
        }

        private void createTableIfNotExists() {
            String sql = "CREATE TABLE IF NOT EXISTS person_table (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "city VARCHAR(100) NOT NULL, " +
                    "age INT NOT NULL" +
                    ")";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // CREATE
        public int create(Person person) {
            String sql = "INSERT INTO person_table (name, city, age) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, person.getName());
                pstmt.setString(2, person.getCity());
                pstmt.setInt(3, person.getAge());
                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // возвращаем сгенерированный ID
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        }

        // Чтение по ID
        public Person read(int id) {
            String sql = "SELECT name, city, age FROM person_table WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String city = rs.getString("city");
                        int age = rs.getInt("age");
                        return new Person(name, city, age);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

    // Чтение по ID
    public Person read(String nameUser) {
        String sql = "SELECT name, city, age FROM person_table WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nameUser);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String city = rs.getString("city");
                    int age = rs.getInt("age");
                    return new Person(name, city, age);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        // UPDATE — пересоздаём объект, т.к. поля final
        public boolean update(int id, Person person) {
            String sql = "UPDATE person_table SET name = ?, city = ?, age = ? WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, person.getName());
                pstmt.setString(2, person.getCity());
                pstmt.setInt(3, person.getAge());
                pstmt.setInt(4, id);

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Удаление по ID
        public boolean delete(int id) {
            String sql = "DELETE FROM person_table WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    public boolean delete(String name) {
        String sql = "DELETE FROM person_table WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
