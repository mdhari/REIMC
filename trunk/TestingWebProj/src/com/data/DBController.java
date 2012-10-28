package com.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {

	Connection con;
	Statement stmt;
	
	public DBController()
	{
		DBConnector dbConn = new DBConnector();
		con = dbConn.getConnection();
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String addLogEntry(String appName, String logType, String logValue, String phoneNum, String time)
	{
		System.out.println("adding: " + appName + "," + logType + "," + logValue + "," + time + "," + phoneNum );
		int recordNum = 0;
		PreparedStatement preparedStatement = null;
				  
		try {
		      preparedStatement = con.prepareStatement("insert into  `reimc`.`logentry` (`logEntryId`, `appName`, `logType`, `logValue`, `logPhoneNum`, `logDateTime` ) " +
		      		"values (default, ?, ?, ?, ?, ?)");

		      preparedStatement.setString(1, appName);
		      preparedStatement.setString(2, logType);
		      preparedStatement.setString(3, logValue);
		      preparedStatement.setString(4, phoneNum);
		      preparedStatement.setString(5, time);
		      recordNum = preparedStatement.executeUpdate();
		      
		      
		      		      
		      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(recordNum == 1)
			return "SUCCESS";
		else
			return "FAILURE";
			
			
	}
}
