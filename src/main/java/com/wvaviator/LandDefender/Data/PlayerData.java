package com.wvaviator.LandDefender.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wvaviator.LandDefender.LDConfiguration;
import com.wvaviator.LandDefender.LandDefender;
import com.wvaviator.LandDefender.Reference.Chat;
import com.wvaviator.LandDefender.Reference.UUIDManager;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;

public class PlayerData {
	
	public static int getPlayerTotalOwned(EntityPlayerMP player) throws SQLException {
		
		String uuid = player.getUniqueID().toString();
		
		String query = "SELECT id FROM chunkdata WHERE uuid = '" + uuid + "' AND isowner ='TRUE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		int count = 0;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				return 0;
			}
			
			do {
				
				count++;
				
			} while(rs.next());
			
			return count;
			
		} finally {
			stmt.close();
			c.close();
		}
		
	}
	
	public static int getPlayerTotalShared(EntityPlayerMP player) throws SQLException {
		
		String uuid = player.getUniqueID().toString();
		
		String query = "SELECT id FROM chunkdata WHERE uuid = '" + uuid + "' AND isowner ='FALSE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		int count = 0;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				return 0;
			}
			
			do {
				
				count++;
				
			} while(rs.next());
			
			return count;
			
		} finally {
			stmt.close();
			c.close();
		}
		
	}
	
	public static void listAllOwned(String uuid, EntityPlayerMP querier) throws SQLException {
		
		if (uuid == null) {
			Chat.toChat(querier, Chat.playerNotFound);
		}
		
		String query = "SELECT id, chunkx, chunkz, dimension FROM chunkdata WHERE uuid = '" + uuid + "' AND isowner ='TRUE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				Chat.toChat(querier, EnumChatFormatting.AQUA + "No chunks owned");
				return;
			}
			
			int allowed = getAllowedChunks(uuid);
			
			Chat.toChat(querier, EnumChatFormatting.AQUA + "Maximum Allowed: " + allowed);
			Chat.toChat(querier, Chat.followingChunks + UUIDManager.getUsernameFromStringUUID(uuid));
			
			do {
				
				int chunkX = rs.getInt("chunkx");
				int chunkZ = rs.getInt("chunkz");
				int dimension = rs.getInt("dimension");
				
				Chat.toChat(querier, EnumChatFormatting.AQUA + "Chunk: " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ + EnumChatFormatting.AQUA + ", Dim " + dimension);
				
			} while(rs.next());
			
			return;
			
		} finally {
			stmt.close();
			c.close();
		}	
		
	}
	
	public static void listAllShared(EntityPlayerMP player, EntityPlayerMP querier) throws SQLException {
		
		String uuid = player.getUniqueID().toString();
		
		if (uuid == null) {
			Chat.toChat(querier, Chat.playerNotFound);
		}
		
		String query = "SELECT chunkx, chunkz FROM chunkdata WHERE uuid = '" + uuid + "' AND isowner ='FALSE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if(!rs.next()) {
				Chat.toChat(querier, EnumChatFormatting.AQUA + "Not invited to any other chunks");
			}
			
			Chat.toChat(querier, Chat.followingChunks + player.getName());
			
			do {
				
				int chunkX = rs.getInt("chunkx");
				int chunkZ = rs.getInt("chunkz");
				
				Chat.toChat(querier, EnumChatFormatting.AQUA + "Chunk: " + EnumChatFormatting.GOLD + chunkX + EnumChatFormatting.AQUA + ", " + EnumChatFormatting.GOLD + chunkZ);
				
			} while(rs.next());
			
			return;
			
		} finally {
			stmt.close();
			c.close();
		}	
		
	}

	public static void listSharedForChunk(EntityPlayerMP player, int chunkX, int chunkZ) throws SQLException {
		
		String query = "SELECT id, name FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if (!rs.next()) {
				return;
			}
			
			do {
				
				String name = rs.getString("name");
				Chat.toChat(player, EnumChatFormatting.GOLD + name);
				
			} while (rs.next());
			
		} finally {
			
			stmt.close();
			c.close();
			
		}
		
	}
	
public static int getTotalSharedForChunk(int chunkX, int chunkZ) throws SQLException {
		
		String query = "SELECT id FROM chunkdata WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ + " AND isowner = 'FALSE'";
		
		Connection c = Database.getConnection();
		Statement stmt = null;
		
		try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			if (!rs.next()) {
				return 0;
			}
			
			int count = 0;
			
			do {
				
				count++;
				
			} while (rs.next());
			
			return count;
			
		} finally {
			
			stmt.close();
			c.close();
			
		}
		
	}

public static int getAllowedChunks(String uuid) throws SQLException {

	int allowed = 0;
	
	String query = "SELECT * FROM players WHERE uuid = '" + uuid + "'";
	Connection c = Database.getConnection();
	Statement stmt = null;
	
	try {
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		allowed = rs.getInt("protections");
		
		return allowed;
		
	} finally {
		stmt.close();
		c.close();
	}
	
}

public static void updateAllowedChunks(String uuid, int newAllowed) throws SQLException {
	
	String update = "UPDATE players SET protections = " + newAllowed + " WHERE uuid = '" + uuid + "'";
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
