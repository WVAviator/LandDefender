package com.wvaviator.LandDefender.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.LandDefender;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.Reference.UUIDManager;

import net.minecraft.entity.player.EntityPlayerMP;

public class ChunkData {
	
	public static boolean isChunkOwned(int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND dimension = " + dimension;
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if (!rs.next()) {
		
			return false;
		} else {
			
			return true;
		}
		} finally {
			stmt.close();
			c.close();
		}

	}
	
	public static boolean doesPlayerOwnChunk(EntityPlayerMP player, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE' AND dimension = " + dimension;
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if (!rs.next()) return false;
		
		String uuid = rs.getString("uuid");
		UUID playerUUID = player.getUniqueID();
		
		if (uuid.equals(playerUUID.toString())) {
			return true;
		}
		
		if (uuid.equalsIgnoreCase("PROTECTION") && player.canUseCommand(LDConfiguration.useProtectPerm, "protect")) {
			return true;
		}

		return false;
		
		} finally {
			
			stmt.close();
			c.close();
			
		}
	}
		
		
	
	public static boolean doesPlayerShareChunk(EntityPlayerMP player, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE' AND dimension = " + dimension;
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if (!rs.next()) {
			
			return false;
		}
		
		do {
		
		String uuid = rs.getString("uuid");
		UUID playerUUID = player.getUniqueID();
		
		if (uuid.equals(playerUUID.toString())) {
			return true;
		}
		
		} while (rs.next());

		return false;
		
		} finally {
			stmt.close();
			c.close();
		}
	}
	
	public static String whichPlayerOwnsChunk(int chunkX, int chunkZ, int dimension) throws SQLException {
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE' AND dimension = " + dimension;
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		String uuid = rs.getString("uuid");
		
		return uuid;
	
		} finally {
			stmt.close();
			c.close();
		}
		

	}
	
	public static String whichPlayerOwnsChunkByName(int chunkX, int chunkZ, int dimension) throws SQLException {
		String query = "SELECT name FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE' AND dimension = " + dimension;
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		String uuid = rs.getString("name");
		return uuid;
		
		} finally {
			
			stmt.close();
			c.close();
			
		}

	}
	
	public static void addChunk(EntityPlayerMP player, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String uuid = player.getUniqueID().toString();
		//int nextId = Database.getSize() + 1;
		
		String update = "INSERT INTO chunkdata (uuid, chunkx, chunkz, isowner, name, dimension) VALUES ('" + uuid + "', " + chunkX + ", " + chunkZ + ", 'TRUE', '" + player.getName() + "', " + dimension + ")";
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		stmt.executeUpdate(update);

		} finally {
		stmt.close();
		c.close();
		}
		
	}
	
	public static void removeChunk(EntityPlayerMP player, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String uuid = player.getUniqueID().toString();
		
		String update = "DELETE FROM chunkdata WHERE uuid = '" + uuid + "' AND chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE' AND dimension = " + dimension;
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		stmt.executeUpdate(update);
		
		} finally {
		
		stmt.close();
		c.close();
		
		}
		
	}
	
	public static void addShared(EntityPlayerMP player, String trustee, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String uuid = UUIDManager.getStringUUIDFromName(trustee);
		
		if (uuid == null) {
			Chat.toChat(player, Chat.playerNotFound);
			return;
		}
		
		//int nextId = Database.getSize() + 1;
		
		String update = "INSERT INTO chunkdata (uuid, chunkx, chunkz, isowner, name, dimension) VALUES ('" + uuid + "', " + chunkX + ", " + chunkZ + ", 'FALSE', '" + trustee + "', " + dimension + ")";
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		stmt.executeUpdate(update);
		Chat.toChat(player, Chat.addedShared + trustee);
		
		} finally {
		
		stmt.close();
		c.close();
		
		}
		
	}
	
	public static void removeShared(EntityPlayerMP player, String trustee, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String uuid = UUIDManager.getStringUUIDFromName(trustee);
		
		if (uuid == null) {
			Chat.toChat(player, Chat.playerNotFound);
			return;
		}
		
		
		String update = "DELETE FROM chunkdata WHERE uuid = '" + uuid + "' AND chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE' AND dimension = " + dimension;
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		stmt.executeUpdate(update);
		Chat.toChat(player, Chat.removeShared + trustee);
		
		} finally {
		
		stmt.close();
		c.close();
		
		}
	}
	
	public static void removeAllShared(int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String update = "DELETE FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE' AND dimension = " + dimension;
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			
			stmt = c.createStatement();
			stmt.executeUpdate(update);
			
		} finally {
			
			stmt.close();
			c.close();
			
		}
		
	}

	public static void protectChunk(EntityPlayerMP player, String name, int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String uuid = "PROTECTION";
		
		
		String update = "INSERT INTO chunkdata (uuid, chunkx, chunkz, isowner, name, dimension) VALUES ('" + uuid + "', " + chunkX + ", " + chunkZ + ", 'TRUE', '" + name + "', " + dimension + ")";
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		stmt.executeUpdate(update);

		} finally {
		stmt.close();
		c.close();
		}
		
	}
	
	public static void unprotectChunk(int chunkX, int chunkZ, int dimension) throws SQLException {
		
		String update = null;
		if (LDConfiguration.canOverride == true) {
			update = "DELETE FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND dimension = " + dimension;
		} else {
			update = "DELETE FROM chunkdata WHERE uuid = 'PROTECTION' AND chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND dimension = " + dimension;
		}
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(update);
		} finally {
			stmt.close();
			c.close();
		}
	}

}
