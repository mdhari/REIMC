package com.data;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class DBConnector {
	static Connection connection;

	public static void prepareConnection()
	{
	
		try {

			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e1) {

			System.out.println("Where is your MySQL JDBC Driver?");
			e1.printStackTrace();
			return;

		}

		System.out.println("MySQL JDBC Driver Registered!");

		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/reimc",
							"root", "wingzero01");

		} catch (SQLException e2) {
			System.out.println("Connection Failed! Check output console");
			e2.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}

	}
	
	public Connection getConnection()
	{
		if(connection != null)
		{
			return connection;
			
		}else{
			prepareConnection();
			return getConnection();
		}
	}
	
/*	public static void main(String[] args) {
		Statement stmt = null;
		
		prepareConnection();
		
		if(connection != null)
		{
			 try {
				stmt = connection.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			 //stmt.executeUpdate(INSERT INTO `reimc`.`app` (`appId`, `appName`)VALUES ( <{appId: }>, <{appName: }> ) )
			 try {
				stmt.executeUpdate("INSERT INTO reimc.app (appId, appName) VALUES(1, 'bookreader')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}
*/
}
