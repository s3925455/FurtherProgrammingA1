package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;
import exception.CustomException;

public class UserDaoImpl implements UserDao {
    private final String TABLE_NAME = "users";

    public UserDaoImpl() {
    }

    @Override
    public void setup() throws SQLException {
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (name VARCHAR(50) NOT NULL,"
                    + "surname VARCHAR(50) NOT NULL," + "username VARCHAR(50) NOT NULL,"
                    + "password VARCHAR(50) NOT NULL," + "PRIMARY KEY (username))";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public User getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
                return null;
            }
        }
    }

    @Override
    public User createUser(String name, String surname, String username, String password) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " (name, surname, username, password) VALUES (?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, username);
            stmt.setString(4, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return new User(name, surname, username, password); // Return the created user object
            } else {
                return null; // Return null if no rows were inserted (user creation failed)
            }
        } catch (SQLException e) {
            // Handle any SQL exception here
            e.printStackTrace();
            throw e; // Rethrow the exception to handle it in the calling method
        }
    }

    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    public void updateUser(String name, String surname, String username, String password) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET name = ?, surname = ?, password = ? WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, password);
            stmt.setString(4, username);
            stmt.executeUpdate();
        }
    }


    @Override
    public User getUserExist(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name")); // Fetch the name from the database
                    user.setSurname(rs.getString("surname")); // Fetch the surname from the database
                    return user;
                }
                return null;
            }
        }
    }

    public boolean userExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM " + TABLE_NAME + " WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Returns true if count is greater than 0 (user exists), false otherwise
                }
            }
        }
        return false; // Default return false if an error occurs or no result is found
    }

    public String exportUserData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User createUser(String username, String password) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }


}
