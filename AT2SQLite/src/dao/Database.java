package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
//	private static final String DB_URL = "jdbc:sqlite:databases/application.db";

	private static final String DB_URL = "jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db";
	
	
	
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL);
	}
}
