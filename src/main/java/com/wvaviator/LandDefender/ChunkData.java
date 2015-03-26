package com.wvaviator.LandDefender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;

public class ChunkData {
	
	public static boolean isChunkOwned(int chunkX, int chunkZ) throws SQLException {
		
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ;
		
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
	
	public static boolean doesPlayerOwnChunk(EntityPlayerMP player, int chunkX, int chunkZ) throws SQLException {
		
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE'";
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		String uuid = rs.getString("uuid");
		UUID playerUUID = Reference.getOnlineUUID(player.getName());
		
		if (uuid.equals(playerUUID.toString())) {
			return true;
		}

		return false;
		
		} finally {
			
			stmt.close();
			c.close();
			
		}
	}
		
		
	
	public static boolean doesPlayerShareChunk(EntityPlayerMP player, int chunkX, int chunkZ) throws SQLException {
		
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if (!rs.next()) {
			
			return false;
		}
		
		String uuid = rs.getString("uuid");
		UUID playerUUID = Reference.getOnlineUUID(player.getName());
		
		
		
		if (uuid.equals(playerUUID.toString())) {

			return true;
		}

		return false;
		} finally {
			stmt.close();
			c.close();
		}
	}
	
	public static String whichPlayerOwnsChunk(int chunkX, int chunkZ) throws SQLException {
		String query = "SELECT uuid FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE'";
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		String uuid = rs.getString("uuid");
		
		stmt.close();
		c.close();
		
		return uuid;
	
		} finally {
			stmt.close();
			c.close();
		}
		

	}
	
	public static String whichPlayerOwnsChunkByName(int chunkX, int chunkZ) throws SQLException {
		String query = "SELECT name FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE'";
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
	
	public static void addChunk(EntityPlayerMP player, int chunkX, int chunkZ) throws SQLException {
		
		UUID uuidO = Reference.getOnlineUUID(player.getName());
		String uuid = uuidO.toString();
		//int nextId = Database.getSize() + 1;
		
		String update = "INSERT INTO chunkdata (uuid, chunkx, chunkz, isowner, name) VALUES ('" + uuid + "', " + chunkX + ", " + chunkZ + ", 'TRUE', '" + player.getName() + "')";
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
	
	public static void removeChunk(EntityPlayerMP player, int chunkX, int chunkZ) throws SQLException {
		
		UUID uuidO = Reference.getOnlineUUID(player.getName());
		String uuid = uuidO.toString();
		
		String update = "DELETE FROM chunkdata WHERE uuid = '" + uuid + "' AND chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'TRUE'";
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
	
	public static void addShared(EntityPlayerMP player, String trustee, int chunkX, int chunkZ) throws SQLException {
		
		UUID uuidO = Reference.getOnlineUUID(trustee);
		
		if (uuidO == null) {
			Chat.toChat(player, Chat.playerNotFound);
			return;
		}
		
		String uuid = uuidO.toString();
		//int nextId = Database.getSize() + 1;
		
		String update = "INSERT INTO chunkdata (uuid, chunkx, chunkz, isowner, name) VALUES ('" + uuid + "', " + chunkX + ", " + chunkZ + ", 'FALSE', '" + player.getName() + "')";
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
	
	public static void removeShared(EntityPlayerMP player, String trustee, int chunkX, int chunkZ) throws SQLException {
		
		UUID uuidO = Reference.getOnlineUUID(trustee);
		
		if (uuidO == null) {
			Chat.toChat(player, Chat.playerNotFound);
			return;
		}
		
		String uuid = uuidO.toString();
		
		String update = "DELETE FROM chunkdata WHERE uuid = '" + uuid + "' AND chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE'";
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
