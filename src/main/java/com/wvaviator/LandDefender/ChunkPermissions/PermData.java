package com.wvaviator.LandDefender.ChunkPermissions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wvaviator.LandDefender.Data.ChunkData;
import com.wvaviator.LandDefender.Data.Database;

public class PermData {
	
	/* Reference Purposes: Tables are
	 * chests, doors, furnaces, brewingstands,
	 * dispensers, droppers, hoppers, jukeboxes,
	 * signs, gates, enchantingtables, anvils, banners,
	 * beds, trapdoors, buttons, levers, beacons
	 */

	public static boolean canUseChests(int chunkX, int chunkZ) throws SQLException {	
		return checkData(chunkX, chunkZ, "chests");
	}

	public static boolean canUseDoors(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "doors");
	}
	
	public static boolean canUseFurnaces(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "furnaces");
	}
	
	public static boolean canUseBrew(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "brewingstands");
	}
	
	public static boolean canUseDispensers(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "dispensers");
	}
	
	public static boolean canUseDroppers(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "droppers");
	}
	
	public static boolean canUseHoppers(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "hoppers");
	}
	
	public static boolean canUseJukeboxes(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "jukeboxes");
	}
	
	public static boolean canUseSigns(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "signs");
	}
	
	public static boolean canUseGates(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "gates");
	}
	
	public static boolean canEnchant(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "enchantingtables");
	}
	
	public static boolean canUseAnvils(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "anvils");
	}
	
	public static boolean canUseBanners(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "banners");
	}
	
	public static boolean canUseBeds(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "beds");
	}
	
	public static boolean canUseTrapdoors(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "trapdoors");
	}
	
	public static boolean canUseButtons(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "buttons");
	}
	
	public static boolean canUseLevers(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "levers");
	}
	
	public static boolean canUseBeacons(int chunkX, int chunkZ) {
		return checkData(chunkX, chunkZ, "beacons");
	}
	
	public static void populateDefaultValues(int chunkX, int chunkZ) throws SQLException {
		
		String update = "INSERT INTO permissions (chunkx, chunkz) VALUES (" + chunkX + ", " + chunkZ + ")";
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
	
	private static boolean checkData(int chunkX, int chunkZ, String column) {
		
		String query = "SELECT * FROM permissions WHERE chunkx = " + chunkX + " AND chunkz = " + chunkZ;
		Connection c;
		try {
			c = Database.getConnection();
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if ((!rs.next()) && (ChunkData.isChunkOwned(chunkX, chunkZ) == true)) {
				populateDefaultValues(chunkX, chunkZ);
				return false;
			}
			
			if (!rs.next()) {
				return true;
			}
			
			return rs.getBoolean(column);		
		} finally {
				stmt.close();
				c.close();
			}
		} catch (SQLException exception){
			exception.printStackTrace();
			return false;

		}
		
	}
}
