package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

//	Database configuration
	private static final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/sneakerhead";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	private Database() {
	}

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
			return conn;
		} catch (Exception e) {
			System.out.println("Connect failed!");
			e.printStackTrace();
			return null;
		}
	}
}
