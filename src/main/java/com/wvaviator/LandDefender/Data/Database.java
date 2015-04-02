package com.wvaviator.LandDefender.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wvaviator.LandDefender.LandDefender;

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
			output = new FileOutputStream(driverDirectory);
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
	
	public static void createPermissionsTable() throws SQLException {
		
		String update = "CREATE TABLE permissions "
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "chunkx INT NOT NULL, chunkz INT NOT NULL, "
				+ "chests BOOL NOT NULL DEFAULT 'FALSE', "
				+ "doors BOOL NOT NULL DEFAULT 'TRUE', "
				+ "furnaces BOOL NOT NULL DEFAULT 'FALSE', "
				+ "brewingstands BOOL NOT NULL DEFAULT 'FALSE', "
				+ "dispensers BOOL NOT NULL DEFAULT 'FALSE', "
				+ "droppers BOOL NOT NULL DEFAULT 'FALSE', "
				+ "hoppers BOOL NOT NULL DEFAULT 'FALSE', "
				+ "jukeboxes BOOL NOT NULL DEFAULT 'FALSE', "
				+ "signs BOOL NOT NULL DEFAULT 'FALSE',"
				+ "gates BOOL NOT NULL DEFAULT 'FALSE',"
				+ "enchantingtables BOOL NOT NULL DEFAULT 'TRUE',"
				+ "anvils BOOL NOT NULL DEFAULT 'FALSE',"
				+ "banners BOOL NOT NULL DEFAULT 'FALSE',"
				+ "beds BOOL NOT NULL DEFAULT 'FALSE',"
				+ "trapdoors BOOL NOT NULL DEFAULT 'TRUE',"
				+ "buttons BOOL NOT NULL DEFAULT 'TRUE',"
				+ "levers BOOL NOT NULL DEFAULT 'TRUE',"
				+ "beacons BOOL NOT NULL DEFAULT 'FALSE')";
		
		Connection c = getConnection();
		Statement stmt = c.createStatement();
		
		try {
			stmt.executeUpdate(update);
		} finally {
			stmt.close();
			c.close();
		}
		
	}
	
	public static void addProtectionsColumn() throws SQLException {
		
		String update = "ALTER TABLE players ADD protections INT NOT NULL DEFAULT 0";
		Connection c = getConnection();
		Statement stmt = c.createStatement();
		
		try {
			stmt.executeUpdate(update);
		} finally {
			stmt.close();
			c.close();
		}
		
	}
	
	public static void databaseUpdate() throws SQLException {
		
		Connection c = Database.getConnection();
		
		try {
		DatabaseMetaData dbm = c.getMetaData();
		ResultSet rs = dbm.getTables(null, null, "permissions", new String[] {"TABLE"});
		
		if (!rs.next()) {
			System.out.println("Permissions table not found! LandDefender must've updated! Creating new table...");
			
			Database.createPermissionsTable();
			Database.addProtectionsColumn();
			
		}
		
		} finally {
			c.close();
		}
	}
}
