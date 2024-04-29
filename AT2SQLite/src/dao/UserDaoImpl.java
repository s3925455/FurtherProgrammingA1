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
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL," + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		}catch(Exception ex) {
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
	public User createUser(String username, String password) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);

			stmt.executeUpdate();
			return new User(username, password);
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

	public void updateUser(String username, String password) throws SQLException {
	    String sql = "UPDATE " + TABLE_NAME + " SET password = ? WHERE username = ?";
	    try (Connection connection = Database.getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, password);
	        stmt.setString(2, username);
	        stmt.executeUpdate();
	    }
	}



	@Override
	public User getUserExist(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			
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

//	public boolean userExists(String username) {
//		// TODO Auto-generated method stub
//		return false;
//	}

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

	

}
