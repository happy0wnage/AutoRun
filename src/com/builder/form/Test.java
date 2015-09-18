package com.builder.form;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DataBase.MySQL.MySqlConnection;

public class Test {

	private static Statement stm = null;
	private static ResultSet rs = null;
	private static Connection con = MySqlConnection.getConnection();

	public static void main(String[] args) {

		try {
			stm = con.createStatement();
			rs = stm.executeQuery("SELECT * FROM Program;");

			rs.relative(1);

			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				System.out.print(rs.getMetaData().getColumnName(i) + "\t");
				System.out.print(rs.getString(i) + "\n");
			}

			con.close();
			stm.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
