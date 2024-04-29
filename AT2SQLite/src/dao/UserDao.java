package dao;

import java.sql.SQLException;

import model.User;

import dao.UserDao;
import exception.CustomException;



public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String username, String password) throws SQLException;
	User getUserExist(String username, String password) throws SQLException;
	
//	User createUser(String username, String password) throws CustomException;
	
}
