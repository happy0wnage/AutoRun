package com.DataBase.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
	public static Connection getConnection() {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost/cursachDB";
			String username = "root";
			String password = "1234";
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			Connection con = DriverManager.getConnection(url, username,
					password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}
