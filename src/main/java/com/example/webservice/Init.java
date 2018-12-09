package com.example.webservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used to initialize files and arraylists for products and orders
 * 
 * @author Domenico Anastasio - 2018 ©
 */
public class Init {

	public static Connection c = null;
	private Statement s = null;
	private String query = "";
	
	public Init() throws ClassNotFoundException, SQLException {
		
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
		
		s = c.createStatement();
		query = "CREATE TABLE IF NOT EXISTS products"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name VARCHAR(255) NOT NULL,"
				+ "price DOUBLE NOT NULL);";
		s.execute(query);
		
		s = c.createStatement();
		query = "CREATE TABLE IF NOT EXISTS orders"
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "email VARCHAR(255) NOT NULL,"
				+ "time VARCHAR(255) NOT NULL);";
		s.execute(query);
		
		s = c.createStatement();
		query = "CREATE TABLE IF NOT EXISTS ordered_products"
				+ "(id_order INTEGER NOT NULL,"
				+ "id_product INTEGER NOT NULL,"
				+ "name VARCHAR(255) NOT NULL,"
				+ "price DOUBLE NOT NULL);";
		s.execute(query);
	}
	
	public static Connection startConnection() throws ClassNotFoundException, SQLException {
		
		Connection c = null;
		
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
		
		return c;
	}
}