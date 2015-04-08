package com.wvaviator.LandDefender.ChunkPermissions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.wvaviator.LandDefender.Data.Database;

public class PermModify {
	
	public static void permUpdate(String perm, boolean value, int chunkX, int chunkZ) throws SQLException {
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		String update = "UPDATE permissions SET " + perm + " = '" + value + "' WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ;
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(update);
		} finally {
			stmt.close();
			c.close();
		}
		
	}
	
	public static void removePerms(int chunkX, int chunkZ) throws SQLException {

		Connection c = Database.getConnection();
		Statement stmt = null;
		
		String update = "DELETE FROM permissions WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ;
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(update);
		} finally {
			stmt.close();
			c.close();
		}
		
	}

}
