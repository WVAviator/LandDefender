package com.wvaviator.LandDefender.Reference;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.wvaviator.LandDefender.LandDefender;
import com.wvaviator.LandDefender.Data.Database;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class UUIDManager {

	public static String getStringUUIDFromPlayer(EntityPlayerMP player) {
		
		UUID uuid = player.getUniqueID();
		String uuidString = uuid.toString();
		return uuidString;
		
	}
	
	public static EntityPlayerMP getPlayerFromStringUUID(String uuidString) throws SQLException {
		
		String query = "SELECT name FROM players WHERE uuid = '" + uuidString + "'";
		Statement stmt = null;
		Connection c = Database.getConnection();
		
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			String username = rs.getString("name");
			
			EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username);
			
			return player;
			
		} finally {
			stmt.close();
			c.close();
			
		}
		
		
	}
	
	public static String getUsernameFromStringUUID(String uuidString) throws SQLException {
		
		String query = "SELECT name FROM players WHERE uuid = '" + uuidString + "'";
		Statement stmt = null;
		Connection c = Database.getConnection();
		
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			String username = rs.getString("name").toUpperCase();

			return username;
			
		} finally {
			stmt.close();
			c.close();
			
		}
	}
	
	public static boolean didUsernameChange(EntityPlayerMP player) throws SQLException {
		
		String uuidString = getStringUUIDFromPlayer(player);
		String username = getUsernameFromStringUUID(uuidString);
		
		String nameToCheck = player.getName().toUpperCase();
		
		if (username.equalsIgnoreCase(nameToCheck)) {
			return false;
		} else {
			return true;
		}
		
		
	}
	
	public static void updateUsername(EntityPlayerMP player) throws SQLException {
		
		String uuid = getStringUUIDFromPlayer(player);
		String username = player.getName().toUpperCase();
		String update = "UPDATE players SET name = '" + username + "' WHERE  uuid = '" + uuid + "'";
		
		Statement stmt = null;
		Connection c = Database.getConnection();
		
		
		try {
			
			stmt = c.createStatement();
			stmt.executeUpdate(update);
			
		} finally {
			stmt.close();
			c.close();
			
		}
	}
	
	public static boolean isPlayerInDatabase(EntityPlayerMP player) throws SQLException {
		
		String uuid = getStringUUIDFromPlayer(player);
		String query = "SELECT uuid FROM players WHERE uuid = '" + uuid + "'";
		
		Statement stmt = null;
		Connection c = Database.getConnection();
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				return false;
			} else {
				return true;
			}
			
		} finally {
			stmt.close();
			c.close();
		}
		
		
	}
	
	public static void addUsername(EntityPlayerMP player) throws SQLException {
		
		String uuid = getStringUUIDFromPlayer(player);
		String username = player.getName().toUpperCase();
		
		String update = "INSERT INTO players VALUES ('" + uuid + "', '" + username + "', " + LandDefender.allowedChunks + ")";
		
		Statement stmt = null;
		Connection c = Database.getConnection();
		
		
		try {
			
			stmt = c.createStatement();
			stmt.executeUpdate(update);
			
		} finally {
			stmt.close();
			c.close();
			
		}
	
	}
	
	public static String getStringUUIDFromName(String username) throws SQLException {
		
		String name = username.toUpperCase();
		
		String query = "SELECT uuid, name FROM players WHERE name = '" + name + "'";
		Statement stmt = null;
		Connection c = Database.getConnection();
		String uuid = null;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				return null;
			}
			
			uuid = rs.getString("uuid");
			return uuid;
			
		} finally {
			stmt.close();
			c.close();
		}
		
		
	}
	
}
