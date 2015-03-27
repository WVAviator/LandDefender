package com.wvaviator.LandDefender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;

public class Database {
	
	public static Connection c = null;
	public static boolean madeConnection = false;

	public final static String DRIVER = "sqlite-jdbc-3.8.7.jar";
	public static String driverDirectory = LandDefender.dataDirectory + DRIVER;
	
	
	public static Connection getConnection() throws SQLException {
		
		if ((madeConnection) && (!(c.isClosed()))) {
			return c;
		}
		
		File databaseDriver = new File(LandDefender.dataDirectory + DRIVER);
		
		if (!(databaseDriver.exists())) {
			copyDriverFromJar();
		}
		
		try {
			
			((ModClassLoader) Loader.instance().getModClassLoader()).addFile(databaseDriver);
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + LandDefender.dataDirectory + "chunks.db");
			return c;
			
		} catch (Exception e) {
			
			System.out.println("Exception in establishing database connection");
			return null;
		}

	}
	
	public static void copyDriverFromJar() {
		FileOutputStream output = null;
		InputStream input = null;
		
		try {
			output = new FileOutputStream(driverDirectory + DRIVER);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		input = Database.class.getResourceAsStream("/" + DRIVER);
		
		byte[] buffer = new byte[4096];
		int bytesRead = 0;
			
		try {
			
			bytesRead = input.read(buffer);
			
			while (bytesRead != -1) { 
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
				
				
			}
			
			output.close();
			input.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}

	}
	
	public static void establishTables() throws SQLException {
		
		String newTable = "CREATE TABLE chunkdata (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(40), chunkx INT NOT NULL, chunkz INT NOT NULL, isowner BOOL NOT NULL DEFAULT 'FALSE', name VARCHAR(20))";
		String newTable2 = "CREATE TABLE players (uuid VARCHAR(40), name VARCHAR(20), PRIMARY KEY (uuid))";
		Statement stmt = null;
		stmt = getConnection().createStatement();
		
		try {
		stmt.executeUpdate(newTable);
		} finally {
			stmt.close();
			c.close();
		}
		
		stmt = getConnection().createStatement();
		
		try {
			stmt.executeUpdate(newTable2);
		} finally {
			stmt.close();
			c.close();
		}
		
		
		
	}
	
	public static int getSize() throws SQLException {
		
		String query = "SELECT id FROM chunkdata";
		Statement stmt = null;
		stmt = getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if (!rs.next()) {
			return 0;
		}
		

		int size = 0;
		while(rs.next()) {
			size++;
		}
		
		size++;
		
		return size;
		
	}

	public static void closeConnection() throws SQLException {
		c.close();	
	}

}
